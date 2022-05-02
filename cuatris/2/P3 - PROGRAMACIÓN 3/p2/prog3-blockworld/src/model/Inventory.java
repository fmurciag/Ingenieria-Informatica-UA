package model;

import java.util.ArrayList;
import java.util.List;

import model.exceptions.BadInventoryPositionException;


/**
 * The Class Inventory.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public class Inventory {

	
	/** lista de  items. */
	private List<ItemStack> items;
	
	/** item en mano. */
	private ItemStack inHand;

	/**
	 * constructor de inventory.
	 */
	public Inventory() {
		this.items=new ArrayList<ItemStack>();
		
	}
	
	/**
	 * constructor de copia.
	 *
	 * @param i the inventario
	 */
	public Inventory(Inventory i) {
		this.items=new ArrayList<ItemStack>(i.items);
		
		this.inHand=i.inHand;
		
	}
	
	
	/**
	 * añade un item a la lista.
	 *
	 * @param item the item
	 * @return the int
	 */
	public int addItem(ItemStack item) {
		items.add(item);
		return item.getAmount();
	}
	
	/**
	 * borrar inventario.
	 */
	public void clear() {
		this.items.clear();
		
	}
	
	
	/**
	 * borramos un hueco del inventario.
	 *
	 * @param slot the slot
	 * @throws BadInventoryPositionException the bad inventory position exception
	 */
	public void clear(int slot) throws BadInventoryPositionException {
		if (slot>=0 && slot<this.items.size()) {
			this.items.remove(slot); 
		}else {
			throw new BadInventoryPositionException(slot);
		}
		
		
	}
	
	/**
	 * Obtiene el índice del primer espacio del inventario que contenga items de ese tipo.
	 *
	 * @param type the type
	 * @return the int
	 */
	public int first(Material type) {
		for(int i=0;i<items.size();i++) {
			if(items.get(i).getType()==type) {
				return i;
			}
		}
		return -1;
	}
	
	
	
	/**
	 * Obtiene el item del slot indicado.
	 *
	 * @param slot the slot
	 * @return the item
	 */
	public ItemStack getItem(int slot) {
		if(slot<items.size() && slot>-1) {
			
			return items.get(slot);
		}else {
			return null;
		}
	}
	
	/**
	 * Obtiene el item de la mano.
	 *
	 * @return item de la mano
	 */
	public ItemStack getItemInHand() {
		
		return inHand;
	}
	
	/**
	 * obtiene el tamaño.
	 *
	 * @return the size
	 */
	public int getSize() {
		
		return items.size();
		
	}
	
	/**
	 * Guarda los items en la posición dada del inventario, remplazando los que estaban alli.
	 *
	 * @param slot the slot
	 * @param item the item
	 * @throws BadInventoryPositionException the bad inventory position exception
	 */
	public void setItem(int slot,ItemStack item) throws BadInventoryPositionException {
		
		if(slot>=0 && slot<items.size()) {
		
			if(slot<items.size() && slot>-1) {
				if(items.get(slot)==null) {
					items.add(slot,item);
				}else {
					items.remove(slot);
					items.add(slot,item);
				}
			}
		
		}else {
			throw new BadInventoryPositionException(slot);
		}
		
		
		
	}
	
	
	/**
	 * obtiene el item de la mano.
	 *
	 * @param item the new item in hand
	 */
	public void setItemInHand(ItemStack item) {
		if(item!=null) {
			inHand=item;
		}else {
			inHand=null;
		}
		
		
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inHand == null) ? 0 : inHand.hashCode());
		result = prime * result + ((items == null) ? 0 : items.hashCode());
		return result;
	}

	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Inventory other = (Inventory) obj;
		if (inHand == null) {
			if (other.inHand != null)
				return false;
		} else if (!inHand.equals(other.inHand))
			return false;
		if (items == null) {
			if (other.items != null)
				return false;
		} else if (!items.equals(other.items))
			return false;
		return true;
	}

	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString() {
		String devolucion="";
		int i=0;
		if(this.getItemInHand()!=null) {
			devolucion+="(inHand="+getItemInHand()+",[";
		}else {
			devolucion+="(inHand=null,[";
			
		}
		if(this.items.size()!=0) {
			for(i=0;i<items.size()-1;i++) {
				devolucion+=getItem(i)+", ";
			}
			devolucion+=getItem(i);
		}
		devolucion+="])";
		return devolucion;
	}
	
	
	 
		
	
}


