package model.score;

import java.util.SortedSet;
import java.util.TreeSet;

import model.exceptions.score.EmptyRankingException;

/**
 * The Class Ranking.
 *
 * @param <ScoreType> the generic type
 */
public class Ranking<ScoreType extends Score<?>>{
	
	/** The scores. */
	private SortedSet<ScoreType> scores;

	/**
	 * Instantiates a new ranking.
	 */
	public Ranking() {
		scores=new TreeSet<ScoreType>();
	}
	
	/**
	 * Adds the score.
	 *
	 * @param s the s
	 */
	public void addScore(ScoreType s) {
		scores.add(s);
	}
	
	/**
	 * Gets the sorted ranking.
	 *
	 * @return the sorted ranking
	 */
	public SortedSet<ScoreType>getSortedRanking(){
		return scores;
		
	}
	
	/**
	 * Gets the winer.
	 *
	 * @return the winer
	 * @throws EmptyRankingException the empty ranking exception
	 */
	public ScoreType getWinner() throws EmptyRankingException {
		if(scores.size()<=0) throw new EmptyRankingException();
		return scores.first();
	}
}
	

