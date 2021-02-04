package model.persistence;

import model.Inventory;
import model.Location;
import model.entities.Player;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayerAdapter.
 * 
 *  @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public class PlayerAdapter implements IPlayer {
	
	/** The player. */
	private Player player;
	
	/** The inventory. */
	private IInventory inventory;
	
	/**
	 * Instantiates a new player adapter.
	 *
	 * @param p the p
	 */
	public PlayerAdapter (Player p) {
		player=p;
		Inventory i=new Inventory(p.getInventory());
		inventory=new InventoryAdapter(i);
		
		
	}

	/**
	 * Gets the health.
	 *
	 * @return the health
	 */
	@Override
	public double getHealth() {
		return player.getHealth();
	}

	/**
	 * Gets the inventory.
	 *
	 * @return the inventory
	 */
	@Override
	public IInventory getInventory() {
		IInventory ii=this.inventory;
		return ii;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	@Override
	public String getName() {
		return player.getName();
	}

	/**
	 * Gets the location.
	 *
	 * @return the location
	 */
	@Override
	public Location getLocation() {
		return player.getLocation();
	}

}
