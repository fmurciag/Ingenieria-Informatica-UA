package model.exceptions;
import model.Material;

/**
 * The Class WrongMaterialException.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
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

	
	

