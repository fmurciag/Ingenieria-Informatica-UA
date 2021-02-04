package model.persistence;

import model.Inventory;
import model.Location;
import model.entities.Player;

/**
 * The Class PlayerAdapter.
 * 
 *  @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public class PlayerAdapter implements IPlayer {
	
	/** el jugadorr. */
	private Player player;
	
	/** el inventario del jugador. */
	private IInventory inventory;
	
	/**
	 * constructor.
	 *
	 * @param p the player
	 */
	public PlayerAdapter (Player p) {
		player=p;
		Inventory i=new Inventory(p.getInventory());
		inventory=new InventoryAdapter(i);
		
		
	}

	
	@Override
	public double getHealth() {
		return player.getHealth();
	}

	
	@Override
	public IInventory getInventory() {
		IInventory ii=this.inventory;
		return ii;
	}

	
	@Override
	public String getName() {
		return player.getName();
	}

	
	@Override
	public Location getLocation() {
		return player.getLocation();
	}

}
