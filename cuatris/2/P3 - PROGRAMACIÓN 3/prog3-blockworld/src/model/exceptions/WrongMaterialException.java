package model.exceptions;
import model.Material;

/**
 * The Class WrongMaterialException.
 */
public class WrongMaterialException extends Exception {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new wrong material exception.
	 *
	 * @param m the m
	 */
	public WrongMaterialException(Material m) {
		super("WrongMaterialException"+ m);
	}

	
	
	
}

	
	

