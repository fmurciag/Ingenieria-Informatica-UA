package model.persistence;

import model.Inventory;
import model.ItemStack;

/**
 * The Class InventoryAdapter.
 * adaptador a la interfaz IInventory
 * 
 *  @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public class InventoryAdapter implements IInventory {
	
	/** el inventario. */
	private Inventory inventory;
	
	/**
	 * constructor.
	 *
	 * @param i the inventario
	 */
	public  InventoryAdapter(Inventory i) {
		inventory=i;
		
	}

	/**
	 * obtiene el item.
	 *
	 * @param i the posicion
	 * @return the item
	 */
	@Override
	public ItemStack getItem(int i) {
		return inventory.getItem(i);
	}

	/**
	 * obtiene el tamaño.
	 *
	 * @return the size
	 */
	@Override
	public int getSize() {
		return inventory.getSize();
	}

	/**
	 * obtiene el de la mano.
	 *
	 * @return the item stack
	 */
	@Override
	public ItemStack inHandItem() {
		return inventory.getItemInHand();
	}
	


}
