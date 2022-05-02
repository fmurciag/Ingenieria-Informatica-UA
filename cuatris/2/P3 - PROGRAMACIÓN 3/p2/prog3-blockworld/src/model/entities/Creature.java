package model.entities;

import model.Location;

/**
 * The Class Creature.
 */
public abstract class Creature extends LivingEntity{

	/**
	 * nueva criatura.
	 *
	 * @param loc the loc
	 * @param salud the salud
	 */
	public Creature(Location loc, double salud) {
		super(loc, salud);
	}
}
