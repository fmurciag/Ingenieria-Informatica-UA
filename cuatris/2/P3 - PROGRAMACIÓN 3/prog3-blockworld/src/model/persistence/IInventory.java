package model.persistence;

import model.ItemStack;

// TODO: Auto-generated Javadoc
/**
 * The Interface IInventory.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public interface IInventory {
	
	/**
	 * Gets the item.
	 *
	 * @param i the i
	 * @return the item
	 */
	public ItemStack getItem(int i);
	
	/**
	 * Gets the size.
	 *
	 * @return the size
	 */
	public int getSize();
	
	/**
	 * In hand item.
	 *
	 * @return the item stack
	 */
	public ItemStack inHandItem();
}
