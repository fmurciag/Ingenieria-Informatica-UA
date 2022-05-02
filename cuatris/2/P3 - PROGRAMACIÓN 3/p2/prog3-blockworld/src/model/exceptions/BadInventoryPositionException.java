package model.exceptions;

/**
 * The Class BadInventoryPositionException.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public class BadInventoryPositionException extends Exception {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	
	/**
	 * Instantiates a new bad inventory position exception.
	 *
	 * @param pos the pos
	 */
	public BadInventoryPositionException(int pos) {
		super("BadInventoryPositionException"+pos);
	}

}
