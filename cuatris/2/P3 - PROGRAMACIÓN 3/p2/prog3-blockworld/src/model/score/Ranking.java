package model.score;

import java.util.SortedSet;
import java.util.TreeSet;

import model.exceptions.score.EmptyRankingException;

/**
 * The Class Ranking.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 *
 * @param <ScoreType> the generic type
 */
public class Ranking<ScoreType extends Score<?>>{
	
	/** la puntuacion. */
	private SortedSet<ScoreType> scores;

	/**
	 * constructor del ranquin.
	 */
	public Ranking() {
		scores=new TreeSet<ScoreType>();
	}
	
	/**
	 * añadir puntuacion.
	 *
	 * @param s the score
	 */
	public void addScore(ScoreType s) {
		scores.add(s);
	}
	
	/**
	 * devuelve la puntuacion.
	 *
	 * @return the sorted ranking
	 */
	public SortedSet<ScoreType>getSortedRanking(){
		return scores;
		
	}
	
	/**
	 * devuelve la puntuacion ganadora.
	 *
	 * @return the winer
	 * @throws EmptyRankingException the empty ranking exception
	 */
	public ScoreType getWinner() throws EmptyRankingException {
		if(scores.size()<=0) throw new EmptyRankingException();
		return scores.first();
	}
}
	

