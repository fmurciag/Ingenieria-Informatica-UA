package model.persistence;

import model.ItemStack;

/**
 * The Interface IInventory.
 * interfaz de inventario
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public interface IInventory {
	
	/**
	 * obtiene el inventario.
	 *
	 * @param i the inventario
	 * @return the item
	 */
	public ItemStack getItem(int i);
	
	/**
	 * obtiene el tamaño.
	 *
	 * @return the size
	 */
	public int getSize();
	
	/**
	 * el iten de la mano.
	 *
	 * @return the item stack
	 */
	public ItemStack inHandItem();
}
