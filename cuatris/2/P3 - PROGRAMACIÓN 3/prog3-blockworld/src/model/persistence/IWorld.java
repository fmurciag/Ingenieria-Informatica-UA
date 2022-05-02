package model.persistence;

import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

import model.Block;
import model.ItemStack;
import model.Location;
import model.entities.Creature;

// TODO: Auto-generated Javadoc
/**
 * The Interface IWorld.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public interface IWorld {
	
	/**
	 * Gets the map block.
	 *
	 * @param loc the loc
	 * @return the map block
	 */
	public NavigableMap<Location,Block>getMapBlock(Location loc);
	
	/**
	 * Gets the negative limit.
	 *
	 * @return the negative limit
	 */
	public int getNegativeLimit();
	
	/**
	 * Gets the player.
	 *
	 * @return the player
	 */
	public IPlayer getPlayer();
	
	/**
	 * Gets the positive limit.
	 *
	 * @return the positive limit
	 */
	public int getPositiveLimit();
	
	/**
	 * Gets the criatures.
	 *
	 * @param loc the loc
	 * @return the criatures
	 */
	public List<Creature>getCreatures(Location loc);
	
	/**
	 * Gets the items.
	 *
	 * @param loc the loc
	 * @return the items
	 */
	public Map<Location, ItemStack>getItems(Location loc);
}
