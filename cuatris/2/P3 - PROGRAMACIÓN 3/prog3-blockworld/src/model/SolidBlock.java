package model;

import model.exceptions.StackSizeException;
import model.exceptions.WrongMaterialException;



/**
 * The Class SolidBlock.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 * 
 */
public class SolidBlock extends Block{


	
	/** los drops. */
	private ItemStack drops;
	
	
	/**
	 * constructor block.
	 *
	 * @param type un tipo
	 * @throws WrongMaterialException the wrong material exception
	 */
	public SolidBlock(Material type) throws WrongMaterialException {
		super(type);
		if(type.isLiquid()) {
			throw new WrongMaterialException(type);
		}else {
			
		}
	}
	
	/**
	 * constructor copia block.
	 *
	 * @param s the s
	 */
	protected SolidBlock(SolidBlock s) {
		super(s);
		this.drops=s.drops;
	}
	
	/**
	 * obtiene los drops.
	 *
	 * @return los drops
	 */
	public ItemStack getDrops() {
		return drops;
	}
	
	
	
	/**
	 * pone los drops siguiendeo una serie de normas.
	 *
	 * @param type the type
	 * @param amount the amount
	 * @throws StackSizeException the stack size exception
	 */
	public void setDrops(Material type, int amount) throws StackSizeException {
		if(super.getType()==Material.CHEST && amount>=1 && amount <= ItemStack.MAX_STACK_SIZE) {
			this.drops= new ItemStack(type,amount);
		}else {
			if(amount==1) {
				this.drops= new ItemStack(type,amount);
			}else {
				throw new StackSizeException();
			}
				
		}
	}
	
	
	
	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((drops == null) ? 0 : drops.hashCode());
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SolidBlock other = (SolidBlock) obj;
		if (drops == null) {
			if (other.drops != null)
				return false;
		} else if (!drops.equals(other.drops))
			return false;
		return true;
	}
	/**
	 * obtiene el tipo.
	 *
	 * @return el tipo
	 */
	public Material getType() {
		return super.getType();
	}

	/**
	 * rompe el bloque.
	 *
	 * @param damage the damage
	 * @return true, if successful
	 */
	public boolean breaks(double damage){
		if(super.getType().getValue()<=damage)return true;
		return false;
		
	}

	/**
	 * crea un clon.
	 *
	 * @return the block
	 */
	@Override
	public Block clone() {
		SolidBlock a=new SolidBlock(this);
		return a;
	}

	
	
	
}
