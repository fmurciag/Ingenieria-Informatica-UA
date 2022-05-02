package model;

import model.exceptions.WrongMaterialException;

/**
 * The Class Block.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public abstract class Block {
	
	
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "[" + type + "]";
	}
 
	

	
	/** el tipo. */
	private Material type;
	
	
	
	/**
	 * constructor block.
	 *
	 * @param type un tipo
	 * @throws WrongMaterialException the wrong material exception
	 */
	public Block(Material type) throws WrongMaterialException {
		if(type.isBlock()) {
			this.type=type;
		
		}else {
			throw new WrongMaterialException(type) ;
		}
		
	}
	
	/**
	 * constructor copia block.
	 *
	 * @param b un bloque
	 */
	
		
	protected Block(Block b) {
		this.type=b.type;
		
	}
	
	/**
	 * obtiene el tipo.
	 *
	 * @return el tipo
	 */
	public Material getType() {
		return type;
	}
	
	
	/**
	 * crea un clon.
	 *
	 * @return the block
	 */
	public abstract Block clone();
	
	
	

	
	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Block other = (Block) obj;
		if (type != other.type)
			return false;
		return true;
	}

	
	
	
}
