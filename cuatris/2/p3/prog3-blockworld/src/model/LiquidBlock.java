package model;

import model.exceptions.WrongMaterialException;

/**
 * The Class LiquidBlock.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public class LiquidBlock extends Block {

	/** The damage. */
	private double damage;
	
	
	 
	/**
	 * constructor erencia de bloque.
	 *
	 * @param type the type
	 * @throws WrongMaterialException the wrong material exception
	 */
	public LiquidBlock(Material type) throws WrongMaterialException {
		super(type);
		if(type.isLiquid()) {
			this.damage=type.getValue();
		}else {
			throw new WrongMaterialException(type);
		}
		
	}

	/**
	 * instancias de bloque
	 *
	 * @param b the b
	 */
	protected LiquidBlock(LiquidBlock b) {
		super(b);
		this.damage=b.damage;

	}

	/**
	 * clona el bloque.
	 *
	 * @return the block
	 */
	@Override
	public Block clone() {
		LiquidBlock a=new LiquidBlock( this);
		return a;
	}
	

	

	/**
	 * obtiene el daño
	 *
	 * @return the damage
	 */
	public double getDamage() {
		return this.damage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(damage);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		LiquidBlock other = (LiquidBlock) obj;
		if (Double.doubleToLongBits(damage) != Double.doubleToLongBits(other.damage))
			return false;
		return true;
	}

	
	
	
}
