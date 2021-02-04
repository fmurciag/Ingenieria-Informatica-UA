package model.persistence;

import model.Location;

// TODO: Auto-generated Javadoc
/**
 * The Interface IPlayer.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public interface IPlayer {
	
	/**
	 * Gets the health.
	 *
	 * @return the health
	 */
	public double getHealth();
	
	/**
	 * Gets the inventory.
	 *
	 * @return the inventory
	 */
	public IInventory getInventory();
	
	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	public Location getLocation();
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName();
	
}
