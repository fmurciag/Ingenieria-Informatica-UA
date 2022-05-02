package model.entities;

import model.ItemStack;
import model.Location;
import model.Material;
import model.exceptions.StackSizeException;

/**
 * The Class Animal.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public class Animal extends Creature {
	
	/** el simbolo. */
	private static char symbol='L';
	
	/**
	 * Instantiates del animal.
	 *
	 * @param loc the loc
	 * @param salud the salud
	 */
	public Animal(Location loc, double salud) {
		super(loc, salud);
	}

	/**
	 * obtiene los drops.
	 *
	 * @return the drops
	 */
	public ItemStack getDrops() {
		try {
			return new ItemStack(Material.BEEF, 1);
		} catch (StackSizeException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * obtiene el simbolo.
	 *
	 * @return the symbol
	 */
	@Override
	public char getSymbol() {
		return symbol;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Animal [location=" + getLocation() + ", health=" + getHealth() + "]";
	}

}
