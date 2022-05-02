package model.score;

import model.Block;

/**
 * The Class MiningScore.
 * puntuacion al picar
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public class MiningScore extends Score<Block> {

	/**
	 * constructor de la puntuacion al picar.
	 *
	 * @param nombre the nombre del jugador
	 */
	public MiningScore(String nombre) {
		super(nombre);
	}




	
	@Override
	public void score(Block b) {
		score+=b.getType().getValue();
	}

	
	@Override
	public int compareTo(Score<Block> o) {
		if (getScoring() < o.getScoring()) {
            return 1;
        }
        if ( getScoring()>o.getScoring() ) {
            return -1;
        }
		return 0;
	}

}
