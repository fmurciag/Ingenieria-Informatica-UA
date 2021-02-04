package model;

import model.exceptions.StackSizeException;

/**
 * The Class ItemStack.
 *
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public class ItemStack {
	
	/** constante el del maximo de tamaño por slot MAX_STACK_SIZE. */
	public static final int MAX_STACK_SIZE=64;
	
	
	
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "(" +getType() + "," + amount + ")";
	}

	/** la cantidad. */
	private int amount;
	
	/** el tipo. */
	private Material type;
	
	/**
	 * constructor item stack.
	 *
	 * @param type the type
	 * @param amount the amount
	 * @throws StackSizeException the stack size exception
	 */ 
	public ItemStack(Material type, int amount) throws StackSizeException {
		if((amount<=MAX_STACK_SIZE&&amount>=1)) {
			if((type.isTool()||type.isWeapon()) && amount!=1) {
				throw new StackSizeException();
			}
			this.amount=amount;
			this.type=type;
		}else {
			throw new StackSizeException();
		}
		
		
	} 
	
	/**
	 * constructor de copia item stack.
	 *
	 * @param items the items
	 */
	public ItemStack(ItemStack items)   {
			this.amount=items.amount;
			this.type=items.type;
		}
		
	
	
	/**
	 * obtiene el  tipo.
	 *
	 * @return el type
	 */
	public Material getType(){
		return type;
		
	}
	
	/**
	 * obtiene el amount.
	 *
	 * @return el amount
	 */
	public int getAmount() {
		return amount;
	}
	
	/**
	 * pone la cantidad.
	 *
	 * @param amount the new amount
	 * @throws StackSizeException the stack size exception
	 */
	public void setAmount(int amount) throws StackSizeException {
		if((amount<=MAX_STACK_SIZE&&amount>=1)) {
			
			if((type.isTool()||type.isWeapon()) && amount!=1) {
				throw new StackSizeException();
			}
			this.amount=amount;
		}else {
			throw new StackSizeException();
		}
	}

	
 
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		ItemStack other = (ItemStack) obj;
		if (amount != other.amount)
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	
	

}
