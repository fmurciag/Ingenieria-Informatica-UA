package model.score;

/**
 * The Class Score.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 *
 * @param <T> the generic type
 */
public abstract class Score<T> implements Comparable<Score<T>> {
	
	/** el nombe del jugador. */
	private String playerName;
	
	/** la puntuacion. */
	protected double score;
	
	/**
	 * constructor de la puntuacion.
	 *
	 * @param nombre the nombre del jugador
	 */
	public Score(String nombre) {
		playerName=nombre;
		score=0;
	}

	
	@Override
	public String toString() {
		return   playerName + ":" + score ;
	}

	/**
	 * obtiene el nombre del jugador.
	 *
	 * @return the name
	 */
	public String getName() {
		return playerName;
	}

	/**
	 * obtiene la puntuacion
	 *
	 * @return the score
	 */
	public double getScoring() {
		return score;
	}
	
	/**
	 * puntuacion.
	 *
	 * @param t the tipo
	 */
	public abstract void score(T t);

	



	

}
