package model.score;

import model.Block;

// TODO: Auto-generated Javadoc
/**
 * The Class MiningScore.
 */
public class MiningScore extends Score<Block> {

	/**
	 * Instantiates a new mining score.
	 *
	 * @param nombre the nombre
	 */
	public MiningScore(String nombre) {
		super(nombre);
		// TODO Auto-generated constructor stub
	}




	/**
	 * Score.
	 *
	 * @param b the b
	 */
	@Override
	public void score(Block b) {
		// TODO Auto-generated method stub
		score+=b.getType().getValue();
	}

	/**
	 * Compare to.
	 *
	 * @param o the o
	 * @return the int
	 */
	@Override
	public int compareTo(Score<Block> o) {
		if (score < o.score) {
            return 1;
        }
        if ( score>o.score ) {
            return -1;
        }
		return 0;
	}

}
