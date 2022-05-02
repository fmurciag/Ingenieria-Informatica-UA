package model.exceptions.score;

/**
 * The Class ScoreException.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public class ScoreException extends RuntimeException {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new score exception.
	 *
	 * @param mensage the mensage
	 */
	public ScoreException(String mensage) {
		super(mensage);
	}

}
