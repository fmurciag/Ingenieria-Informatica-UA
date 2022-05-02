package model.entities;

import model.Location;

/**
 * The Class Monster.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public class Monster extends Creature {
	
	
	
	/** The simbolo del monstruo. */
	private static char symbol='M';
	/**
	 * constructor de monstruos.
	 *
	 * @param loc the loc
	 * @param salud the salud
	 */
	public Monster(Location loc, double salud) {
		super(loc, salud);
	}

	/**
	 * coge el simbolo.
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
		return "Monster [location=" + getLocation() + ", health=" + getHealth() + "]";
	}


}
