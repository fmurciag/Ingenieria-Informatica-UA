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

/**
 * The Class WorldAdapter.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public class WorldAdapter implements IWorld {
	
	/** el jugador. */
	private IPlayer player;
	
	/** el mundo. */
	private World world;
	
	/**
	 * constructor de mundos.
	 *
	 * @param w the mundo
	 */
	public WorldAdapter(World w) {
		world=w;
		player=new PlayerAdapter(w.getPlayer());
	}
	
	
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
		return mapa;
	}

	
	@Override
	public int getNegativeLimit() {
		if(world.getSize()%2!=0){//impar
			return -(world.getSize()/2);
		}else {//par
			return -((world.getSize()/2)-1);
		}
		
		
	}

	
	@Override
	public IPlayer getPlayer() {
		return player;
	}

	
	@Override
	public int getPositiveLimit() {
			return (world.getSize()/2);
		
	}

	
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
