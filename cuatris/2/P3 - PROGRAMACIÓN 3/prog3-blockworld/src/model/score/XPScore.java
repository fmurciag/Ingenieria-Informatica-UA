package model.score;

import java.util.ArrayList;

import model.entities.Player;
import model.exceptions.score.ScoreException;

// TODO: Auto-generated Javadoc
/**
 * The Class XPScore.
 */
public class XPScore extends Score<Player> {
	
	/** The player. */
	private Player 	player;
	
	/** The scores. */
	private ArrayList<Score<?>> scores;

	/**
	 * Instantiates a new XP score.
	 *
	 * @param p the p
	 */
	public XPScore(Player p) {
		super(p.getName());
		player=p;
		scores=  new ArrayList<Score<?>>();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Compare to.
	 *
	 * @param o the o
	 * @return the int
	 */
	@Override
	public int compareTo(Score<Player> o) {
		if (score < o.score) {
            return 1;
        }
        if ( score>o.score ) {
            return -1;
        }
		return 0;
	}

	/**
	 * Score.
	 *
	 * @param p the p
	 */
	@Override
	public void score(Player p) {
		// TODO Auto-generated method stub
		if(!player.equals(p))throw new ScoreException("ScoreException");
		double acumulado = 0;
		for (int i = 0; i < scores.size(); i++) {
			acumulado+=scores.get(i).getScoring();
		}
		if(scores.size()>0) {
			score=(acumulado/scores.size())+p.getFoodLevel()+p.getHealth();
		}else {
			score=p.getFoodLevel()+p.getHealth();
		}
		
		
		
	}
	
	/**
	 * Gets the scoring.
	 *
	 * @return the scoring
	 */
	public double getScoring() {
		score(player);
		return score;
		
	}
	
	/**
	 * Adds the score.
	 *
	 * @param s the s
	 */
	public void addScore(Score<?> s) {
		scores.add(s);
		score(player);
		
	}
	
}
