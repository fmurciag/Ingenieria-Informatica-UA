package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.entities.Player;
import model.exceptions.BadInventoryPositionException;
import model.exceptions.BadLocationException;
import model.exceptions.EntityIsDeadException;
import model.exceptions.StackSizeException;
import model.exceptions.WrongMaterialException;
import model.score.CollectedItemsScore;
import model.score.MiningScore;
import model.score.PlayerMovementScore;

/**
 * The Class BlockWorld.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public class BlockWorld {

	/** la instancia genetal. */
	private static BlockWorld instance=null;
	
		
	/** el mundo. */
	private World world;
	
	/** los puntos de los items. */
	private CollectedItemsScore itemsScore;
	
	/** los puntos de picar. */
	private MiningScore miningScore;
	
	/** los puntos al mover. */
	private PlayerMovementScore movementScore;
	
	
	
	/**
	 * constructor de block world.
	 */
	private BlockWorld() {
		this.world=null;
		itemsScore=null;
		miningScore=null;
		movementScore=null;
		
	}

		
	

	/**
	 * obtiene la instancia de  BlockWorld.
	 *
	 * @return la instancia de  BlockWorld
	 */
	public static BlockWorld getInstance() {
		if(instance==null) {
			instance=new BlockWorld();
		}
		return instance;
	}
	
	
	
	/**
	 * crea un mundo con las condiciones dadas.
	 *
	 * @param seed la semilla
	 * @param size el tamaño
	 * @param name el nombre
	 * @param playerName the player name
	 * @return the world
	 */
	public World createWorld(long seed, int size, String name,String playerName) {
		World mundo = new World(seed, size, name,playerName);
		this.world=mundo;
		itemsScore=new CollectedItemsScore(playerName);
		miningScore=new MiningScore(playerName);
		movementScore=new PlayerMovementScore(playerName);
		
		return mundo;

	}
	
	/**
	 * muestra la informacion del jugador
	 * nombre, salud, comida, inventario y posiciones vecinas.
	 *
	 * @param player the player
	 * @return the string
	 */
	public String showPlayerInfo(Player player){
		try {
			return player.toString()+"\n"+player.getLocation().getWorld().getNeighbourhoodString(player.getLocation())+"Scores: [items: "+
					getItemsScore().getScoring()+", blocks: "+getMiningScore().getScoring()+", movements: "+getMovementScore().getScoring()+"]\n";
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * mueve el jugaror a la posicion dada.
	 *
	 * @param p the p
	 * @param dx the dx
	 * @param dy the dy
	 * @param dz the dz
	 * @throws EntityIsDeadException the entity is dead exception
	 * @throws BadLocationException the bad location exception
	 */
	public void movePlayer(Player p, int dx, int dy, int dz) throws EntityIsDeadException, BadLocationException  {
		Location loc=p.move(dx, dy, dz);//sumar 1
		movementScore.score(loc);
		if (world.getBlockAt(loc)!=null) {
			if(world.getBlockAt(loc).getType().isLiquid())p.damage(world.getBlockAt(loc).getType().getValue());
		}
			try {
				if(world.getItemsAt(p.getLocation())!=null) {
					p.addItemsToInventory(world.getItemsAt(p.getLocation()));
					
					itemsScore.score(world.getItemsAt(p.getLocation()));
					
					world.removeItemsAt(p.getLocation());
				}
			} catch (BadLocationException e) {
				e.printStackTrace();
			}

	}
	
	/**
	 *coge el item de la posicion dada.
	 *
	 * @param player the player
	 * @param pos the pos
	 * @throws BadInventoryPositionException the bad inventory position exception
	 */
	public void selectItem(Player player, int pos)throws BadInventoryPositionException {
			player.selectItem(pos);
	}
	
	
	/**
	 * usa el item de la mano.
	 *
	 * @param p the p
	 * @param times the times
	 * @throws EntityIsDeadException the entity is dead exception
	 * @throws IllegalArgumentException the illegal argument exception
	 */
	public void useItem(Player p, int times) throws EntityIsDeadException , IllegalArgumentException{
			ItemStack item=p.useItemInHand(times);
			double dano;
			if(item!=null) {
				try {
					 if(p.getLocation().getWorld().getCreatureAt(p.getOrientation())!=null){//criaturas
						if(item.getType().isBlock()) {
							dano=0.1*times;
						}else {
							dano=item.getType().getValue()*times;
						}
						p.getLocation().getWorld().getCreatureAt(p.getOrientation()).damage(dano);
						if(p.getLocation().getWorld().getCreatureAt(p.getOrientation()).isDead()
								&&p.getLocation().getWorld().getCreatureAt(p.getOrientation()).getSymbol()=='L') {//animal
							ItemStack i;
							
								world.killCreature(p.getOrientation());
								try {
									i = new ItemStack(Material.BEEF, 1);
									world.addItems(p.getOrientation(),i );
								} catch (StackSizeException e) {
									e.printStackTrace();
								}
								
								
							
						}else if(p.getLocation().getWorld().getCreatureAt(p.getOrientation()).getSymbol()=='M') {
							
							if(p.getLocation().getWorld().getCreatureAt(p.getOrientation()).isDead()) {
								world.killCreature(p.getOrientation());
							}else {
								p.damage(times*0.5);
							}
							
						}
						
					}else if(p.getLocation().getWorld().getBlockAt(p.getOrientation())!=null
							&&!p.getLocation().getWorld().getBlockAt(p.getOrientation()).getType().isLiquid()) {//bloques
						if(item.getType().isBlock()) {
							dano=0.1*times;
						}else {
							dano=item.getType().getValue()*times;
						}
						Block b=(SolidBlock)p.getLocation().getWorld().getBlockAt(p.getOrientation());
						if(b instanceof SolidBlock) {
							SolidBlock bloque=(SolidBlock)b;
							
							if(bloque.breaks(dano)) {
							
							p.getLocation().getWorld().destroyBlockAt(p.getOrientation());
							miningScore.score(bloque);
							
							}
						}
						
					
					}else{//poner bloque
						if(!item.getType().isLiquid()) {
							 SolidBlock s;
							try {
								s = new SolidBlock(item.getType());
								world.addBlock(p.getOrientation(), s);
							} catch (WrongMaterialException e) {
								e.printStackTrace();
							}
						}
						
					}
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			
		}
		
		
	}

	
	
	/**
	 * Orientate player.
	 *
	 * @param p the p
	 * @param dx the dx
	 * @param dy the dy
	 * @param dz the dz
	 * @throws EntityIsDeadException the entity is dead exception
	 * @throws BadLocationException the bad location exception
	 */
	public void orientatePlayer(Player p, int dx, int dy, int dz) throws EntityIsDeadException, BadLocationException {
		
		p.orientate(dx, dy, dz);
	}
	
	/**
	 * juega con un fichero.
	 *
	 * @param path the fichero
	 * @throws FileNotFoundException the file not found exception
	 */
	public void playFile(String path) throws FileNotFoundException {
		Scanner archivo =new Scanner(new File(path));
		play(archivo);
		archivo.close();
	}
	
	/**
	 * ejecuta instruciones del fichero.
	 *
	 * @param sc the fichero
	 */
	public void play(Scanner sc) {
		long semilla=sc.nextLong();
		int tamño= sc.nextInt();
		String jugador=sc.next();
		this.world=createWorld(semilla, tamño,sc.nextLine(),jugador );
		
		while (sc.hasNext() &&!world.getPlayer().isDead()) {
			
			String   comando = sc. next();
			
			if(comando.equals("move")) {//movemos
					
				try {
					movePlayer(world.getPlayer(), sc.nextInt(), sc.nextInt(), sc.nextInt());
				} catch (NumberFormatException | EntityIsDeadException | BadLocationException e) {
					e.printStackTrace();
				}
				
			}else if(comando.equals("orientate")) {//orientamos
				try {
					orientatePlayer(world.getPlayer(),  sc.nextInt(), sc.nextInt(), sc.nextInt());
				} catch (NumberFormatException | EntityIsDeadException | BadLocationException e) {
					e.printStackTrace();
				}
				
			}else if(comando.equals("useItem")) {//usamos item
				try {
					useItem(world.getPlayer(), sc.nextInt());
				} catch (IllegalArgumentException | EntityIsDeadException  e) {
					e.printStackTrace();
				}
			}else if(comando.equals("show")) {//mostramos jugador
				System.out.println(showPlayerInfo(world.getPlayer()));
				
			}else if(comando.equals("selectItem")) {//seleccionamos item
				try {
					selectItem(world.getPlayer(), sc.nextInt());
				} catch (NumberFormatException | BadInventoryPositionException e) {
					e.printStackTrace();
				}
			}else {
				System.err.println("ERROR comandos al leer fichero");
				
			}
	
		}
	}
	
	/**
	 * juega por consola.
	 */
	public void playFromConsole() {
		Scanner s = new Scanner(System.in);
		play(s);
		
		
	}
	
	/**
	 * obtiene los puntos de los items.
	 *
	 * @return the puntos de los items
	 */
	public CollectedItemsScore getItemsScore() {
		return itemsScore;
		
	}
	
	/**
	 * obtiene los puntos de picar.
	 *
	 * @return the puntos de picar
	 */
	public MiningScore getMiningScore() {
		return miningScore;
		
	}
	
	/**
	 * obtiene los puntos al mover.
	 *
	 * @return the puntos al mover
	 */
	public PlayerMovementScore getMovementScore() {
		return movementScore;
	}
	
		
		
		
		
}
	
	
	
	
	
	
	
	


