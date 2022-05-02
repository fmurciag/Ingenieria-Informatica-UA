package model.entities;

import model.Location;

/**
 * The Class LivingEntity.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public abstract class LivingEntity {

	/** The localizacion. */
	protected Location location;
	
	/** The vida. */
	private double health;
	
	/** The constante MAX_HEALTH. */
	public static final double MAX_HEALTH =20;
	
	/**
	 * Iconstructur de entidad.
	 *
	 * @param loc the loc
	 * @param salud the salud
	 */
	public LivingEntity(Location loc,double salud) {
		if(salud>MAX_HEALTH) salud=MAX_HEALTH;
		this.health=salud;
		this.location=loc;
	}
	
	/**
	 * daño.
	 *
	 * @param amount the amount
	 */
	public void damage (double amount) {
		this.health=this.health-amount;
		
	}
	
	/**
	 * mira si esta muerto.
	 *
	 * @return true, if is dead
	 */
	public boolean isDead() {
		if(health<=0) return true;
		return false;
	}
	
	
	/**
	 * coge el simbolo.
	 *
	 * @return the symbol
	 */
	public abstract  char getSymbol();
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "LivingEntity [location=" + location + ", health=" + health + "]";
	}
	
	/**
	 * pone la salud.
	 *
	 * @param health the new health
	 */
	public void setHealth(double health) {
		
		if(health<=MAX_HEALTH) {
			this.health=health;
		}else {
			this.health=MAX_HEALTH;
		}
	}
	
	/**
	 * obtiene la vida.
	 *
	 * @return la health
	 */
	public double getHealth() {
		return health;
	}

	/**
	 * obtiene la localizacion.
	 *
	 * @return la location
	 */
	public Location getLocation() {
		Location l= new Location(this.location.getWorld(),
				this.location.getX(), this.location.getY(), this.location.getZ());
		return l;
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(health);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		return result;
	}

	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LivingEntity other = (LivingEntity) obj;
		if (Double.doubleToLongBits(health) != Double.doubleToLongBits(other.health))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}
	
}
		
		
	

