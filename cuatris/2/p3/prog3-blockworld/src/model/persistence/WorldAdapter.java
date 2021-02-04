package model.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import model.Block;
import model.ItemStack;
import model.LiquidBlock;
import model.Location;
import model.Material;
import model.World;
import model.entities.Creature;
import model.exceptions.BadLocationException;
import model.exceptions.WrongMaterialException;

// TODO: Auto-generated Javadoc
/**
 * The Class WorldAdapter.
 */
public class WorldAdapter implements IWorld {
	
	/** The player. */
	private IPlayer player;
	
	/** The world. */
	private World world;
	
	/**
	 * Instantiates a new world adapter.
	 *
	 * @param w the w
	 */
	public WorldAdapter(World w) {
		world=w;
		player=new PlayerAdapter(w.getPlayer());
	}
	
	/**
	 * Gets the map block.
	 *
	 * @param loc the loc
	 * @return the map block
	 */
	@Override
	public NavigableMap<Location, Block> getMapBlock(Location loc) {
		NavigableMap<Location, Block> mapa = new TreeMap<Location, Block>();
		
		for (int i = 0; i<16 ; i++) {//x
			for (int j = 0; j<16 ; j++) {//y
				for (int k = 0; k<16 ; k++) {//z
					Location l =new Location(world, loc.getX()+i, loc.getY()+j, loc.getZ()+k);
					Location relativa=new Location(world,i,j,k);
					try {
						Block b=world.getBlockAt(l);
						if(b==null&&Location.check(l)) {
							LiquidBlock aire=new LiquidBlock(Material.AIR);
							mapa.put(relativa,aire);
						}else {
							mapa.put(relativa,b);
						}
					} catch (BadLocationException | WrongMaterialException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return mapa;//crear mapa nuevo y copiar del original al nuevo con tres bucles
	}

	/**
	 * Gets the negative limit.
	 *
	 * @return the negative limit
	 */
	@Override
	public int getNegativeLimit() {
		if(world.getSize()%2!=0){//impar
			return -(world.getSize()/2);
		}else {//par
			return -((world.getSize()/2)-1);
		}
		
		
	}

	/**
	 * Gets the player.
	 *
	 * @return the player
	 */
	@Override
	public IPlayer getPlayer() {
		return player;
	}

	/**
	 * Gets the positive limit.
	 *
	 * @return the positive limit
	 */
	@Override
	public int getPositiveLimit() {
			return (world.getSize()/2);
		
	}

	/**
	 * Gets the criatures.
	 *
	 * @param loc the loc
	 * @return the criatures
	 */
	@Override
	public List<Creature> getCreatures(Location loc) {
		List<Creature>criaturas = new ArrayList<Creature>();
		
		for (int i = 0; i<16 ; i++) {//x
			for (int j = 0; j<16 ; j++) {//y
				for (int k = 0; k<16 ; k++) {//z
					Location l =new Location(world, loc.getX()+i, loc.getY()+j, loc.getZ()+k);
					try {
						if(null!=world.getCreatureAt(l)&&Location.check(l)) {
							criaturas.add(world.getCreatureAt(l));
						}
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return criaturas;
		
		
		
		
	}

	/**
	 * Gets the items.
	 *
	 * @param loc the loc
	 * @return the items
	 */
	@Override
	public Map<Location, ItemStack> getItems(Location loc) {
		Map<Location, ItemStack>items=new TreeMap<Location, ItemStack>();
		for (int i = 0; i<16 ; i++) {//x
			for (int j = 0; j<16 ; j++) {//y
				for (int k = 0; k<16 ; k++) {//z
					Location l =new Location(world, loc.getX()+i, loc.getY()+j, loc.getZ()+k);
					try {
						if(world.getItemsAt(l)!=null){
							if(Location.check(l))items.put(l, world.getItemsAt(l));
						}
						
					} catch (BadLocationException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		return items;
	}

}
