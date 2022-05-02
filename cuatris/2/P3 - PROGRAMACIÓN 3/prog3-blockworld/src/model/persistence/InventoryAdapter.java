package model.persistence;

import model.Inventory;
import model.ItemStack;

/**
 * The Class InventoryAdapter.
 * 
 *  @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public class InventoryAdapter implements IInventory {
	
	/** The inventory. */
	private Inventory inventory;
	
	/**
	 * Instantiates a new inventory adapter.
	 *
	 * @param i the i
	 */
	public  InventoryAdapter(Inventory i) {
		inventory=i;
		
	}

	/**
	 * Gets the item.
	 *
	 * @param i the i
	 * @return the item
	 */
	@Override
	public ItemStack getItem(int i) {
		return inventory.getItem(i);
	}

	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	@Override
	public int getSize() {
		return inventory.getSize();
	}

	/**
	 * In hand item.
	 *
	 * @return the item stack
	 */
	@Override
	public ItemStack inHandItem() {
		return inventory.getItemInHand();
	}
	


}
