package model.persistence;

import model.Location;

/**
 * The Interface IPlayer.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public interface IPlayer {
	
	/**
	 * obtiene la vida
	 *
	 * @return the health
	 */
	public double getHealth();
	
	/**
	 * obtiene el inventario.
	 *
	 * @return the inventory
	 */
	public IInventory getInventory();
	
	/**
	 * obtiene la localizacion.
	 *
	 * @return the location
	 */
	public Location getLocation();
	
	/**
	 * obtiene el nombre.
	 *
	 * @return the name
	 */
	public String getName();
	
}
