package model.score;

import java.util.ArrayList;

import model.entities.Player;
import model.exceptions.score.ScoreException;

/**
 * The Class XPScore.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public class XPScore extends Score<Player> {
	
	/** el jugador. */
	private Player 	player;
	
	/** las puntuaciones. */
	private ArrayList<Score<?>> scores;

	/**
	 * constructor de XP.
	 *
	 * @param p the player
	 */
	public XPScore(Player p) {
		super(p.getName());
		player=p;
		scores=  new ArrayList<Score<?>>();
	}
	
	
	@Override
	public int compareTo(Score<Player> o) {
		if (getScoring() < o.getScoring()) {
            return 1;
        }
        if ( getScoring()>o.getScoring() ) {
            return -1;
        }
		return 0;
	}

	
	@Override
	public void score(Player p) {
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
	 * obtiene la puntuacion y la actualiza.
	 *
	 * @return the scoring
	 */
	public double getScoring() {
		score(player);
		return score;
		
	}
	
	/**
	 * añade puntuacion y la actualiza.
	 *
	 * @param s the score
	 */
	public void addScore(Score<?> s) {
		scores.add(s);
		score(player);
		
	}
	
}
