package model;
	
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.bukkit.util.noise.CombinedNoiseGenerator;
import org.bukkit.util.noise.OctaveGenerator;
import org.bukkit.util.noise.PerlinOctaveGenerator;

import model.entities.Animal;
import model.entities.Creature;
import model.entities.LivingEntity;
import model.entities.Monster;
import model.entities.Player;
import model.exceptions.BadLocationException;
import model.exceptions.StackSizeException;
import model.exceptions.WrongMaterialException;

/**
	 * class World.
	 *
	 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
	 */
	public class World {


		/** name of the world. */
	    private String name;
	    
	    /**
	     * Size of the world in the (x,z) plane.
	     */
	    private int worldSize;
	  
	    /** World seed for procedural world generation. */
		private long seed;

	    /** bloques de este mundo. */
	    private Map<Location, Block> blocks;	
	    
		/**
		 * Items depositados en algÃºn lugar de este mundo.
		 */ 
	    private Map<Location, ItemStack> items;
	    
    	/** The creatures. */
    	private Map<Location, Creature> creatures;

	    /** El jugador. */
	    private Player player;
	    
	    
		/** Esta clase interna representa un mapa de alturas bidimiensional
		 * que nos servirÃ¡ para guardar la altura del terreno (coordenada 'y')
		 * en un array bidimensional, e indexarlo con valores 'x' y 'z' positivos o negativos.
		 * 
		 * la localizaciÃ³n x=0,z=0 queda en el centro del mundo. 
		 * Por ejemplo, un mundo de tamaÃ±o 51 tiene su extremo noroeste a nivel del mar en la posiciÃ³n (-25,63,-25) 
		 * y su extremo sureste, tambiÃ©n a nivel del mar, en la posiciÃ³n (25,63,25). 
		 * Para un mundo de tamaÃ±o 50, estos extremos serÃ¡n (-24,63,-24) y (25,63,25), respectivamente.
		 * 
		 * Por ejemplo, para obtener la altura del terreno en estas posiciones, invocarÃ­amos al mÃ©todo get() de esta  clase:
		 *   get(-24,24) y get(25,25)
		 * 
		 * de forma anÃ¡loga, si queremos modificar el valor 'y' almacenado, haremos
		 *   set(-24,24,70)
		 *
		 */
		class HeightMap {
			
			/** The height map. */
			double[][] heightMap;
			
	    	/** The positive world limit. */
	    	int positiveWorldLimit; 
	    	
	    	/** The negative world limit. */
	    	int negativeWorldLimit;

			/**
			 * Instantiates a new height map.
			 *
			 * @param worldsize the worldsize
			 */
			HeightMap(int worldsize) {
				heightMap = new double[worldsize][worldsize];
				positiveWorldLimit  = worldsize/2;
				negativeWorldLimit = (worldsize % 2 == 0) ? -(positiveWorldLimit-1) : -positiveWorldLimit;
			}
			
			/**
			 * obtiene la atura del  terreno en la posiciÃ³n (x,z).
			 *
			 * @param x coordenada 'x' entre 'positiveWorldLimit' y 'negativeWorldLimit'
			 * @param z coordenada 'z' entre 'positiveWorldLimit' y 'negativeWorldLimit'
			 * @return the double
			 */
			double get(double x, double z) {
				return heightMap[(int)x - negativeWorldLimit][(int)z - negativeWorldLimit];
			}
			
			/**
			 * Sets the.
			 *
			 * @param x the x
			 * @param z the z
			 * @param y the y
			 */
			void set(double x, double z, double y) {
				heightMap[(int)x - negativeWorldLimit][(int)z - negativeWorldLimit] = y;
			}

		}
		
		
		/**
		 * Coordenadas 'y' de la superficie del mundo. Se inicializa en generate() y debe actualizarse
		 * cada vez que el jugador coloca un nuevo bloque en una posiciÃ³n vacÃ­a
		 * Puedes usarlo para localizar el bloque de la superficie de tu mundo.
		 */
		private HeightMap heightMap;
	  
	  
		/**
	     * Genera un mundo nuevo del tamaÃ±o size*size en el plano (x,z). Si existÃ­an elementos anteriores en el mundo,  
	     * serÃ¡n eliminados. Usando la misma semilla y el mismo tamaÃ±o podemos generar mundos iguales
	     * @param seed semilla para el algoritmo de generaciÃ³n. 
	     * @param size tamaÃ±o del mundo para las dimensiones x y z
	     */
	    private  void generate(long seed, int size) {
	    	
	    	Random rng = new Random(getSeed());

	    	blocks.clear();
	    	creatures.clear();
	    	items.clear();
	    	
	    	// Paso 1: generar nuevo mapa de alturas del terreno
	    	heightMap = new HeightMap(size);
	    	CombinedNoiseGenerator noise1 = new CombinedNoiseGenerator(this);
	    	CombinedNoiseGenerator noise2 = new CombinedNoiseGenerator(this);
	    	OctaveGenerator noise3 = new PerlinOctaveGenerator(this, 6);
	    	
	    	System.out.println("Generando superficie del mundo...");
	    	for (int x=0; x<size; x++) {
	    		for (int z=0; z<size; z++) {
	    	    	double heightLow = noise1.noise(x*1.3, z*1.3) / 6.0 - 4.0;
	    	    	double heightHigh = noise2.noise(x*1.3, z*1.3) / 5.0 + 6.0;
	    	    	double heightResult = 0.0;
	    	    	if (noise3.noise(x, z, 0.5, 2) / 8.0 > 0.0)
	    	    		heightResult = heightLow;
	    	    	else
	    	    		heightResult = Math.max(heightHigh, heightLow);
	    	    	heightResult /= 2.0;
	    	    	if (heightResult < 0.0)
	    	    		heightResult = heightResult * 8.0 / 10.0;
	    	    	heightMap.heightMap[x][z] = Math.floor(heightResult + Location.SEA_LEVEL);
	    		}
	    	}
	    	
	    	// Paso 2: generar estratos
	    	SolidBlock block = null;
	    	Location location = null;
	    	Material material = null;
	    	OctaveGenerator noise = new PerlinOctaveGenerator(this, 8);
	    	System.out.println("Generando terreno...");
	    	for (int x=0; x<size; x++) {
	    		for (int z=0; z<size; z++) {
	    	    	double dirtThickness = noise.noise(x, z, 0.5, 2.0) / 24 - 4;
	    	    	double dirtTransition = heightMap.heightMap[x][z];
	    	    	double stoneTransition = dirtTransition + dirtThickness;
	    	    	for (int y=0; y<= dirtTransition; y++) {
	    	    		if (y==0) material = Material.BEDROCK;
	    	    		else if (y <= stoneTransition) 
	    	    			material = Material.STONE;
	    	    		else // if (y <= dirtTransition)
	    	    			material = Material.DIRT;
						try {
							location = new Location(this,x+heightMap.negativeWorldLimit,y,z+heightMap.negativeWorldLimit);
							block = new SolidBlock(material);
							if (rng.nextDouble() < 0.5) // los bloques contendrÃ¡n item con un 50% de probabilidad
								block.setDrops(block.getType(), 1);
							blocks.put(location, block);
						} catch (WrongMaterialException | StackSizeException e) {
							// Should never happen
							e.printStackTrace();
						}
	    	    	}

	    		}
	    	}
	    	
	    	// Paso 3: Crear cuevas
	    	int numCuevas = size * size * 256 / 8192;
			double theta = 0.0;
			double deltaTheta = 0.0;
			double phi = 0.0;
			double deltaPhi = 0.0;

			System.out.print("Generando cuevas");
	    	for (int cueva=0; cueva<numCuevas; cueva++) {
	    		System.out.print("."); System.out.flush();
	    		Location cavePos = new Location(this,rng.nextInt(size),rng.nextInt((int)Location.UPPER_Y_VALUE), rng.nextInt(size));
	    		double caveLength = rng.nextDouble() * rng.nextDouble() * 200;
	    		//cave direction is given by two angles and corresponding rate of change in those angles,
	    		//spherical coordinates perhaps?
	    		theta = rng.nextDouble() * Math.PI * 2;
	    		deltaTheta = 0.0;
	    		phi = rng.nextDouble() * Math.PI * 2;
	    		deltaPhi = 0.0;
	    		double caveRadius = rng.nextDouble() * rng.nextDouble();

	    		for (int i=1; i <= (int)caveLength ; i++) {
	    			cavePos.setX(cavePos.getX()+ Math.sin(theta)*Math.cos(phi));
	    			cavePos.setY(cavePos.getY()+ Math.cos(theta)*Math.cos(phi));
	    			cavePos.setZ(cavePos.getZ()+ Math.sin(phi));
	    			theta += deltaTheta*0.2;
	    			deltaTheta *= 0.9;
	    			deltaTheta += rng.nextDouble();
	    			deltaTheta -= rng.nextDouble();
	    			phi /= 2.0;
	    			phi += deltaPhi/4.0;
	    			deltaPhi *= 0.75;
	    			deltaPhi += rng.nextDouble();
	    			deltaPhi -= rng.nextDouble();
	    			if (rng.nextDouble() >= 0.25) {
	    				Location centerPos = new Location(cavePos);
	    				centerPos.setX(centerPos.getX() + (rng.nextDouble()*4.0-2.0)*0.2);
	    				centerPos.setY(centerPos.getY() + (rng.nextDouble()*4.0-2.0)*0.2);
	    				centerPos.setZ(centerPos.getZ() + (rng.nextDouble()*4.0-2.0)*0.2);
	    				double radius = (Location.UPPER_Y_VALUE - centerPos.getY()) / Location.UPPER_Y_VALUE;
	    				radius = 1.2 + (radius * 3.5 + 1) * caveRadius;
	    				radius *= Math.sin(i * Math.PI / caveLength);
	    				try {
	    					fillOblateSpheroid( centerPos, radius, null);
	    				} catch (WrongMaterialException e) {
	    					// Should not occur
	    					e.printStackTrace();
	    				}
	    			}

	    		}
	    	}
	    	System.out.println();
	    	
	    	// Paso 4: crear vetas de minerales
	    	// Abundancia de cada mineral
	    	double abundance[] = new double[2];
	    	abundance[0] = 0.5; // GRANITE
	    	abundance[1] =  0.3; // OBSIDIAN
	    	int numVeins[] = new int[2];
	    	numVeins[0] = (int) (size * size * 256 * abundance[0]) / 16384; // GRANITE
	    	numVeins[1] =  (int) (size * size * 256 * abundance[1]) / 16384; // OBSIDIAN

	    	Material vein = Material.GRANITE;
	    	for (int numVein=0 ; numVein<2 ; numVein++, vein = Material.OBSIDIAN) { 
	    		System.out.print("Generando vetas de "+vein);
	    		for (int v=0; v<numVeins[numVein]; v++) {
	    			System.out.print(vein.getSymbol());
	    			Location veinPos = new Location(this,rng.nextInt(size),rng.nextInt((int)Location.UPPER_Y_VALUE), rng.nextInt(size));
	    			double veinLength = rng.nextDouble() * rng.nextDouble() * 75 * abundance[numVein];
	    			//cave direction is given by two angles and corresponding rate of change in those angles,
	    			//spherical coordinates perhaps?
	    			theta = rng.nextDouble() * Math.PI * 2;
	    			deltaTheta = 0.0;
	    			phi = rng.nextDouble() * Math.PI * 2;
	    			deltaPhi = 0.0;
	    			//double caveRadius = rng.nextDouble() * rng.nextDouble();
	    			for (int len=0; len<(int)veinLength; len++) {
	    				veinPos.setX(veinPos.getX()+ Math.sin(theta)*Math.cos(phi));
	    				veinPos.setY(veinPos.getY()+ Math.cos(theta)*Math.cos(phi));
	    				veinPos.setZ(veinPos.getZ()+ Math.sin(phi));
	    				theta += deltaTheta*0.2;
	    				deltaTheta *= 0.9;
	    				deltaTheta += rng.nextDouble();
	    				deltaTheta -= rng.nextDouble();
	    				phi /= 2.0;
	    				phi += deltaPhi/4.0;
	    				deltaPhi *= 0.9; // 0.9 for veins
	    				deltaPhi += rng.nextDouble();
	    				deltaPhi -= rng.nextDouble();
	    				double radius = abundance[numVein] * Math.sin(len * Math.PI / veinLength) + 1;

	    				try {
	    					fillOblateSpheroid(veinPos, radius, vein);
	    				} catch (WrongMaterialException ex) {
	    					// should not ocuur
	    					ex.printStackTrace();
	    				}
	    			}
	    		}
	    		System.out.println();
	    	}
	    	
	    	System.out.println();

	    	// flood-fill water     	
	    	char water= Material.WATER.getSymbol();

	    	int numWaterSources = size*size/800;
	    	
	    	System.out.print("Creando fuentes de agua subterráneas");
	    	int x = 0;
	    	int z = 0;
	    	int y = 0;
	    	for (int w=0; w<numWaterSources; w++) {
	    		System.out.print(water);
	    		x = rng.nextInt(size)+heightMap.negativeWorldLimit;
	    		z = rng.nextInt(size)+heightMap.negativeWorldLimit;
	    		y = (int)Location.SEA_LEVEL - 1 - rng.nextInt(2);
	    		try {
					floodFill(Material.WATER, new Location(this,x,y,z));
				} catch (WrongMaterialException | BadLocationException e) {
					// no debe suceder
					throw new RuntimeException(e);
				}
	    	}
	    	System.out.println();
	   
	    	System.out.print("Creando erupciones de lava");
	    	char lava = Material.LAVA.getSymbol();
	    	// flood-fill lava
	    	int numLavaSources = size*size/2000;
	    	for (int w=0; w<numLavaSources; w++) {
	    		System.out.print(lava);
	    		x = rng.nextInt(size)+heightMap.negativeWorldLimit;
	    		z = rng.nextInt(size)+heightMap.negativeWorldLimit;
	    		y = (int)((Location.SEA_LEVEL - 3) * rng.nextDouble()* rng.nextDouble());
	    		try {
					floodFill(Material.LAVA, new Location(this,x,y,z));
				} catch (WrongMaterialException  | BadLocationException e) {
					// no debe suceder
					throw new RuntimeException(e);			
				}
	    	}
	    	System.out.println();

	    	// Paso 5. crear superficie, criaturas e items
	    	// Las entidades aparecen sÃ³lo en superficie (no en cuevas, por ejemplo)

	    	OctaveGenerator onoise1 = new PerlinOctaveGenerator(this, 8);
	    	OctaveGenerator onoise2 = new PerlinOctaveGenerator(this, 8);
	    	boolean sandChance = false;
	    	double entitySpawnChance = 0.05;
	    	double itemsSpawnChance = 0.10;
	    	double foodChance = 0.8;
	    	double toolChance = 0.1;
	    	double weaponChance = 0.1;
	    	
	    	System.out.println("Generando superficie del terreno, entidades e items...");
	    	for (x=0; x<size; x++) {    		
	    		for (z=0; z<size; z++) {
	    			sandChance = onoise1.noise(x, z, 0.5, 2.0) > 8.0;
	    			y = (int)heightMap.heightMap[(int)x][(int)z];
	    			Location surface = new Location(this,x+heightMap.negativeWorldLimit,y,z+heightMap.negativeWorldLimit); // la posiciÃ³n (x,y+1,z) no estÃ¡ ocupada (es AIR)
	    			try {
		    			if (sandChance) {
		    				SolidBlock sand = new SolidBlock(Material.SAND);
		    				if (rng.nextDouble() < 0.5)
		    					sand.setDrops(Material.SAND, 1);
		    				blocks.put(surface, sand);
		    			}
		    			else {
		    				SolidBlock grass = new SolidBlock(Material.GRASS);
		    				if (rng.nextDouble() < 0.5)
		    					grass.setDrops(Material.GRASS, 1);
		    				blocks.put(surface, grass);
		    			}
	    			} catch (WrongMaterialException | StackSizeException ex) {
	    				// will never happen
	    				ex.printStackTrace();
	    			}
	    			// intenta crear una entidad en superficie
	    			try {
	    				Location aboveSurface = surface.above();
	    				
	    				if (rng.nextDouble() < entitySpawnChance) {
	    					Creature entity =null;
	    					double entityHealth = rng.nextInt((int)LivingEntity.MAX_HEALTH)+1;
	    					if (rng.nextDouble() < 0.75) // generamos Monster (75%) o Animal (25%) de las veces
	    						entity = new Monster(aboveSurface, entityHealth);
	    					else 
	    						entity = new Animal(aboveSurface, entityHealth);
	    					creatures.put(aboveSurface, entity);
	    				} else { 
	    					// si no, intentamos crear unos items de varios tipos (comida, armas, herramientas)
	    					// dentro de cofres
	    					Material itemMaterial = null;
	    					int amount = 1; // p. def. para herramientas y armas
	    					if (rng.nextDouble() < itemsSpawnChance) {
	    						double rand = rng.nextDouble();
	    						if (rand < foodChance) { // crear comida
	    							// hay cuatro tipos de item de comida, en las posiciones 8 a 11 del array 'materiales'
	    							itemMaterial = Material.getRandomItem(8, 11);
	    							amount = rng.nextInt(5)+1;
	    						}
	    						else if (rand < foodChance+toolChance)
	    							// hay dos tipos de item herramienta, en las posiciones 12 a 13 del array 'materiales'
	    							itemMaterial = Material.getRandomItem(12, 13);
	    						else
	    							// hay dos tipos de item arma, en las posiciones 14 a 15 del array 'materiales'
	    							itemMaterial = Material.getRandomItem(14, 15);
	    						
	    						items.put(aboveSurface, new ItemStack(itemMaterial, amount));
	    					}
	    				}
	    			} catch (BadLocationException | StackSizeException e) {
	    				// BadLocationException : no hay posiciones mÃ¡s arriba, ignoramos creaciÃ³n de entidad/item sin hacer nada 
	    				// StackSizeException : no se producirÃ¡
	    				throw new RuntimeException(e);    			}

	    		}
	    	}

	    	    	
	    	// Generar jugador
	    	player = new Player("Steve",this);
	    	// El jugador se crea en la superficie (posiciÃ³n (0,*,0)). AsegurÃ©monos de que no hay nada mÃ¡s ahÃ­
	    	Location playerLocation = player.getLocation();
	    	creatures.remove(playerLocation);
	    	items.remove(playerLocation);
	    	
	    }
	    
	    /**
	     * Where fillOblateSpheroid() is a method which takes a central point, a radius and a material to fill to use on the block array.
	     * @param centerPos central point
	     * @param radius radius around central point
	     * @param material material to fill with
	     * @throws WrongMaterialException if 'material' is not a block material
	     */
	    private void fillOblateSpheroid(Location centerPos, double radius, Material material) throws WrongMaterialException {
	    	
					for (double x=centerPos.getX() - radius; x< centerPos.getX() + radius; x += 1.0) {					
						for (double y=centerPos.getY() - radius; y< centerPos.getY() + radius; y += 1.0) {
							for (double z=centerPos.getZ() - radius; z< centerPos.getZ() + radius; z += 1.0) {
								double dx = x - centerPos.getX();
								double dy = y - centerPos.getY();
								double dz = z - centerPos.getZ();
								
								if ((dx*dx + 2*dy*dy + dz*dz) < radius*radius) {
									// point (x,y,z) falls within level bounds ?
									// we don't need to check it, just remove or replace that location from the blocks map.
									Location loc = new Location(this,Math.floor(x+heightMap.negativeWorldLimit),Math.floor(y),Math.floor(z+heightMap.negativeWorldLimit));
									if (material==null)
										blocks.remove(loc);
									else try { //if ((Math.abs(x) < worldSize/2.0-1.0) && (Math.abs(z) < worldSize/2.0-1.0) && y>0.0 && y<=Location.UPPER_Y_VALUE)
										SolidBlock veinBlock = new SolidBlock(material);
										// los bloques de veta siempre contienen material
										veinBlock.setDrops(material, 1);
										blocks.replace(loc, veinBlock);
									} catch  (StackSizeException ex) {
										// will never happen
										ex.printStackTrace();
									}
								}
							}
						}
					}
		}

	    /**
    	 * Flood fill.
    	 *
    	 * @param liquid the liquid
    	 * @param from the from
    	 * @throws WrongMaterialException the wrong material exception
    	 * @throws BadLocationException the bad location exception
    	 */
    	private void floodFill(Material liquid, Location from) throws WrongMaterialException, BadLocationException {
	    	if (!liquid.isLiquid())
	    		throw new WrongMaterialException(liquid);
	    	if (!blocks.containsKey(from))
	    	{
	    		blocks.put(from, BlockFactory.createBlock(liquid));
	    		items.remove(from);
	    		Set<Location> floodArea = getFloodNeighborhood(from);
	    		for (Location loc : floodArea) 
	    			floodFill(liquid, loc);
	    	}
	    }
	    
		/**
		 * Obtiene las posiciones adyacentes a esta que no estÃ¡n por encima y estÃ¡n libres .
		 *
		 * @param location the location
		 * @return si esta posiciÃ³n pertenece a un mundo, devuelve sÃ³lo aquellas posiciones adyacentes vÃ¡lidas para ese mundo,  si no, devuelve todas las posiciones adyacentes
		 * @throws BadLocationException cuando la posiciÃ³n es de otro mundo
		 */
		private Set<Location> getFloodNeighborhood(Location location) throws BadLocationException {
			if (location.getWorld() !=null && location.getWorld() != this)
				throw new BadLocationException("Esta posiciÃ³n no es de este mundo");
			Set<Location> neighborhood = location.getNeighborhood();
			Iterator<Location> iter = neighborhood.iterator();
			while (iter.hasNext()) {
				Location loc = iter.next();
				try {
					if ((loc.getY() > location.getY()) || getBlockAt(loc)!=null)
						iter.remove();
				} catch (BadLocationException e) {
					throw new RuntimeException(e);
					// no sucederÃ¡
				}
			}
			return neighborhood;
		}


	    
	    
	    /**
    	 * constructor de mundo.
    	 *
    	 * @param name the name
    	 * @deprecated
    	 */
    	public World(String name) {
	    	this.name=name;
	    }
	    
	     
	    
	    
	    /**
    	 * constructor de mundo.
    	 *
    	 * @param seed the seed
    	 * @param size the size
    	 * @param name the name
    	 * @param playerName the player name
    	 */
    	public World(long seed, int size, String name,String playerName)  {
	    	if(size<=0) {
	    		throw new IllegalArgumentException();
	    	}else {
		    	this.name=name;
		    	this.worldSize=size;
		    	this.blocks = new HashMap<Location, Block>();
	            this.items = new HashMap<Location, ItemStack>();
	            this.creatures=new HashMap<Location, Creature>();
		    	this.seed=seed;
		    	generate(seed,size);
		    	Player p=new Player(playerName, this);
		    	player=p;
	    	}
	    	
	    	
	    	
	    	
	    }

		/**
		 * obtiene el nombre.
		 *
		 * @return the name
		 */
		public String getName() {
			return name;
		}

		/**
		 * obtiene la semilla.
		 *
		 * @return the seed
		 */
		public long getSeed() {
			return seed;
		}

		/**
		 * obtiene el jugador.
		 *
		 * @return the player
		 */
		public Player getPlayer() {
			return player;
		}

		/**
		 * obtiene el tamaño.
		 *
		 * @return the size
		 */
		public int getSize() {
			return worldSize;
		}
		
		/**
		 * obtiene el bloque en la posicion dada.
		 *
		 * @param loc the loc
		 * @return bloque en la posicion dada
		 * @throws BadLocationException the bad location exception
		 */
		public Block getBlockAt(Location loc) throws BadLocationException{
			
			if(equals(loc.getWorld())) {
				if(blocks.containsKey(loc)) {
					Block bloque =  blocks.get(loc).clone();
					return bloque;
				}else {
					return null;
				}
				
			}else {
				throw new BadLocationException("BadLocationException");
			}
			
			
			
		}
		
		/**
		 * obtieneel item  en la posicion dada.
		 *
		 * @param loc the loc
		 * @return item  en la posicion dada
		 * @throws BadLocationException the bad location exception
		 */
		public ItemStack getItemsAt(Location loc) throws BadLocationException {
			if(equals(loc.getWorld())) {
				if(items.containsKey(loc)) {
					ItemStack item = items.get(loc);
					return item;
				}else {
					return null;
				}
			}else {
				throw new BadLocationException("BadLocationException");
			}
			
		}
		
		/**
		 * Obtiene la posición que representa el nivel del suelo.
		 *
		 * @param ground the ground
		 * @return nivel del suelo
		 * @throws BadLocationException the bad location exception
		 */
		public Location getHighestLocationAt(Location ground) throws BadLocationException{
			
			if(equals(ground.getWorld())) {
				Location l=new Location(ground);
				 l.setY(heightMap.get(ground.getX(), ground.getZ()));
				 return l;
				
			}else {
				 throw new BadLocationException("BadLocationException");
			}
			
			
		}
		
		
		
		/**
		 * obtiene una cadena de caractes con los elemntos vecinos
		 * X :fuera del mundo
		 * . :nada
		 * P :jugador
		 *simbolo ajeno : bloque o item
		 *
		 * @param loc the loc
		 * @return string con los vecinos
		 * @throws BadLocationException the bad location exception
		 */
		public String getNeighbourhoodString(Location loc) throws BadLocationException  {
			if(loc.getWorld().equals(this)) {
				String vecino="";
				for(int k=-1;k<2;k++) {//Z
					for(int j=1;j>-2;j--) {//Y
						for(int i=-1;i<2;i++) {//X
							Location coordenada=new Location(loc.getWorld(),loc.getX()+i,loc.getY()+j,loc.getZ()+k);
							if( Location.check(coordenada)) {
								if(!coordenada.equals(this.getPlayer().getLocation()) ) {
									if(!creatures.containsKey(coordenada)) {
										try {
											if(getBlockAt(coordenada)!=null) {
												if(getBlockAt(coordenada).getType()==Material.WATER) {
													if(getItemsAt(coordenada)!=null) {
														if(getItemsAt(coordenada).getType().isBlock()) {
															vecino+=Character.toUpperCase(getItemsAt(coordenada).getType().getSymbol());
														}else {
															vecino+=getItemsAt(coordenada).getType().getSymbol();
														}
													}else {
														vecino+=getBlockAt(coordenada).getType().getSymbol();
													}
												}else {
													vecino+=getBlockAt(coordenada).getType().getSymbol();
												}
												
												
											}else {
												if(getItemsAt(coordenada)!=null) {
													if(getItemsAt(coordenada).getType().isBlock()) {
														vecino+=Character.toUpperCase(getItemsAt(coordenada).getType().getSymbol());
													}else {
														vecino+=getItemsAt(coordenada).getType().getSymbol();
													}
													
													//Character.toUpperCase(texto.charAt(0))
												}else {
													vecino+=".";
												}
											}
										} catch (Exception e) {
											try {
												if(getItemsAt(coordenada)!=null) {
													if(getItemsAt(coordenada)!=null) {
														if(getItemsAt(coordenada).getType().isBlock()) {
															vecino+=Character.toUpperCase(getItemsAt(coordenada).getType().getSymbol());
														}else {
															vecino+=getItemsAt(coordenada).getType().getSymbol();
														}	
													}
												}
											} catch (Exception e2) {
												vecino+=".";
											}
										}
									}else {
										vecino+=getCreatureAt(coordenada).getSymbol();
									}
								}else {
									vecino+='P';
								}
							}else {
								vecino+="X";
							}
						}
						vecino+=" ";
					}
					vecino+="\n";
				}
				return vecino;
			}else {
				throw new BadLocationException("BadLocationException");
			}
			
		
			
		}
		
		
		
		
		
		/**
		 * elimina los items en la posicion dada.
		 *
		 * @param loc the loc
		 * @throws BadLocationException the bad location exception
		 */
		public void removeItemsAt(Location loc) throws BadLocationException {
			if(loc.getWorld().equals(this)&&items.containsKey(loc)) {
				items.remove(loc);
			}else {
				throw new BadLocationException("BadLocationException");
			}
			
		}
		
		
		
		/**
		 * mira si la posicion esta libre.
		 *
		 * @param loc the loc
		 * @return true, si esta libre
		 * @throws BadLocationException the bad location exception
		 */
		public boolean isFree(Location loc) throws BadLocationException {
			if(loc.getWorld().equals(this)) {
				if(blocks.containsKey(loc)) {
					if((!getBlockAt(loc).getType().isLiquid()||this.player.getLocation().equals(loc)||creatures.containsKey(loc))) {
							return false;
						}else {
							return true;
						}
				}else {
					if(this.player.getLocation().equals(loc)||creatures.containsKey(loc)) {
						return false;
					}else {
						return true;
					}
				}
						
			}else {
				throw new BadLocationException("BadLocationException");
			}
			
		}
		
		
		 
		/**
		 * añade bloques.
		 *
		 * @param loc the localizacion
		 * @param b the bloque
		 * @throws BadLocationException the bad location exception
		 */
		public void addBlock(Location loc, Block b) throws BadLocationException {
			if(!equals(loc.getWorld())||!Location.check(loc)||player.getLocation().equals(loc)) throw new BadLocationException("BadLocationException");
			
			if(blocks.containsKey(loc)) {
			
				blocks.remove(loc);
				blocks.put(loc, b);
				Location actualizar= new Location(loc);
				while(actualizar.getY() < getHighestLocationAt(loc).getY()) {
					actualizar.setY(loc.getY()+1);
                }
				 heightMap.set(actualizar.getX(),actualizar.getZ(), actualizar.getY());
				
			}else if(creatures.containsKey(loc)){
				
				creatures.remove(loc);
				blocks.put(loc, b);
				Location actualizar= new Location(loc);
				while(actualizar.getY() < getHighestLocationAt(loc).getY()) {
					actualizar.setY(loc.getY()+1);
                }
				 heightMap.set(actualizar.getX(),actualizar.getZ(), actualizar.getY());
			}else if(items.containsKey(loc)) {
			
				items.remove(loc);
				blocks.put(loc, b);
				Location actualizar= new Location(loc);
				while(actualizar.getY() < getHighestLocationAt(loc).getY()) {
					actualizar.setY(loc.getY()+1);
                }
				 heightMap.set(actualizar.getX(),actualizar.getZ(), actualizar.getY());
			}else {
			
				blocks.put(loc, b);
				Location actualizar= new Location(loc);
				while(actualizar.getY() < getHighestLocationAt(loc).getY()) {
					actualizar.setY(loc.getY()+1);
                }
				 heightMap.set(actualizar.getX(),actualizar.getZ(), actualizar.getY());
			}
		}
		
		/**
		 * añade una criatura.
		 *
		 * @param c the criatura
		 * @throws BadLocationException the bad location exception
		 */
		public void addCreature(Creature c) throws BadLocationException {
			if(!equals(c.getLocation().getWorld())||!Location.check(c.getLocation())||
					!isFree(c.getLocation())||
					this.player.getLocation().equals(c.getLocation())) throw new BadLocationException("BadLocationException");
			
			if(items.containsKey(c.getLocation())) items.remove(c.getLocation());
			
			creatures.put(c.getLocation(), c);
			
			
		}
		
		/**
		 * añade un item.
		 *
		 * @param loc the localizacion
		 * @param i the item
		 * @throws BadLocationException the bad location exception
		 */
		public void addItems(Location loc, ItemStack i) throws BadLocationException {
			boolean a=!equals(loc.getWorld());
			boolean b=!Location.check(loc);
			//boolean c=blocks.containsKey(loc);
			boolean d =!isFree(loc);
			boolean e =this.player.getLocation().equals(loc);
			
			
			if(a||b||d||e )throw new BadLocationException("BadLocationException");
			
			if(items.containsKey(loc)) {
				items.remove(loc);
				items.put(loc, i);
			
			}else {
				if(i!=null) {
					items.put(loc, i);
				}
				
			}		
		}
		
		/**
		 * elimina un bloque.
		 *
		 * @param loc the localizacion a romper
		 * @throws BadLocationException the bad location exception
		 */
		public void destroyBlockAt(Location loc) throws BadLocationException {
			if(loc==null)throw new BadLocationException("BadLocationException");
			if(!equals(loc.getWorld())||!blocks.containsKey(loc)||loc.getY()<=0) throw new BadLocationException("BadLocationException");
			
			if(getBlockAt(loc).getType().equals(Material.CHEST)) {
				//downcasting;
					
				Block b=(SolidBlock)blocks.get(loc);
				if(b instanceof SolidBlock) {
					SolidBlock s=(SolidBlock)b;
					
					blocks.remove(loc);
					items.put(loc, s.getDrops());
					Location actualizar= new Location(loc);
					while(actualizar.getY() >= getHighestLocationAt(actualizar).getY()||getBlockAt(actualizar)==null) {
						actualizar.setY(actualizar.getY()-1);
	                }
					
					 heightMap.set(actualizar.getX(),actualizar.getZ(), actualizar.getY());
				}		
			}else {
				Block b=(SolidBlock)blocks.get(loc);
				if(b instanceof SolidBlock) {
					SolidBlock s=(SolidBlock)b;
					
					ItemStack i=s.getDrops();
					
					blocks.remove(loc);
					addItems(loc, i);
					Location actualizar= new Location(loc);
				
					while(actualizar.getY() >= getHighestLocationAt(loc).getY()||getBlockAt(actualizar)==null) {
						actualizar.setY(actualizar.getY()-1);
					}
				
					heightMap.set(actualizar.getX(),actualizar.getZ(), actualizar.getY());
				}
			}
		
		}
		
		/**
		 * obtiene criaturas .
		 *
		 * @param loc the localizacion
		 * @return the creature at
		 * @throws BadLocationException the bad location exception
		 */
		public Creature getCreatureAt(Location loc) throws BadLocationException {
			if(loc==null)throw new BadLocationException("BadLocationException");
			if(!equals(loc.getWorld())||!Location.check(loc))throw new BadLocationException("BadLocationException");
			return creatures.get(loc);
		}
		
		
		
		/**
		 * obtiene las criaturas cercanas.
		 *
		 * @param loc the localizacion
		 * @return the nearby creatures
		 * @throws BadLocationException the bad location exception
		 */
		public Collection<Creature> getNearbyCreatures(Location loc) throws BadLocationException{
			if(!equals(loc.getWorld()))throw new BadLocationException("BadLocationException");
			Collection<Creature>bichos=new HashSet<Creature>();
			for(int i=-1;i<2;i++) {//X
				for(int j=-1;j<2;j++) {//Y
					for(int k=-1;k<2;k++) {//Z
						Location coordenada=new Location(loc.getWorld(),loc.getX()+i,loc.getY()+j,loc.getZ()+k);
						if((i!=0||j!=0||k!=0 )&& Location.check(coordenada)) {
							if(creatures.containsKey(coordenada)) {
								bichos.add(creatures.get(coordenada));
							}
						}
					}
				}
			}
			return bichos;
		}
		
		/**
		 * mata criatura.
		 *
		 * @param loc the localizacion
		 * @throws BadLocationException the bad location exception
		 */
		public void killCreature(Location loc) throws BadLocationException {
			if(loc==null)throw new BadLocationException("BadLocationException");
			if(!equals(loc.getWorld())||!creatures.containsKey(loc))throw new BadLocationException("BadLocationException");
			creatures.get(loc).damage(creatures.get(loc).getHealth());
			creatures.remove(loc);
		}
		
		
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			result = prime * result + (int) (seed ^ (seed >>> 32));
			result = prime * result + worldSize;
			return result;
		}

		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			World other = (World) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (seed != other.seed)
				return false;
			if (worldSize != other.worldSize)
				return false;
			return true;
		}

		/**
		 * To string.
		 *
		 * @return the string
		 */
		@Override
		public String toString() {
			return   getName() ;
		}
	
	    
 

	}	


	
	