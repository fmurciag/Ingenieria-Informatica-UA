package model.score;

import model.ItemStack;

// TODO: Auto-generated Javadoc
/**
 * The Class Score.
 *
 * @param <T> the generic type
 */
public abstract class Score<T> implements Comparable<Score<T>> {
	
	/** The player name. */
	private String playerName;
	
	/** The score. */
	protected double score;
	
	/**
	 * Instantiates a new score.
	 *
	 * @param nombre the nombre
	 */
	public Score(String nombre) {
		playerName=nombre;
		score=0;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return   playerName + ":" + score ;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return playerName;
	}

	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public double getScoring() {
		return score;
	}
	
	/**
	 * Score.
	 *
	 * @param t the t
	 */
	public abstract void score(T t);

	



	

}
