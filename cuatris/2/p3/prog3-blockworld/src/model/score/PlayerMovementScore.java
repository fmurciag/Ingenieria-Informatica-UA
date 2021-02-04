package model.score;

import model.Location;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayerMovementScore.
 */
public class PlayerMovementScore extends Score<Location> {
	
	/** The previous location. */
	private Location previousLocation;

	/**
	 * Instantiates a new player movement score.
	 *
	 * @param nombre the nombre
	 */
	public PlayerMovementScore(String nombre) {
		super(nombre);
		previousLocation=null;
	}

	/**
	 * Compare to.
	 *
	 * @param o the o
	 * @return the int
	 */
	@Override
	public int compareTo(Score<Location> o) {
		if (score < o.score) {
            return -1;
        }
        if ( score>o.score ) {
            return 1;
        }
		
		return 0;
	}

	/**
	 * Score.
	 *
	 * @param l the l
	 */
	@Override
	public void score(Location l) {
		if(previousLocation==null) {
			previousLocation=l;
		}else {
			score+=previousLocation.distance(l);
			previousLocation=l;
		}
		
	 
	}

}
