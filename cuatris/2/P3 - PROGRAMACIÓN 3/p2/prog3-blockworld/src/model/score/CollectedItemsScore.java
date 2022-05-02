package model.score;

import model.ItemStack;

/**
 * The Class CollectedItemsScore.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public class CollectedItemsScore extends Score<ItemStack> {

	/**
	 * constructor de la puntuacion de los items.
	 *
	 * @param s the nombre del jugador
	 */
	public CollectedItemsScore(String s){
		super(s);
		
	}

	
	@Override
	public int compareTo(Score<ItemStack> o) {
		if (getScoring() < o.getScoring()) {
            return 1;
        }
        if ( getScoring()>o.getScoring() ) {
            return -1;
        }
        return 0;
	}

	
	@Override
	public void score(ItemStack i) {
		 score+=i.getAmount()*i.getType().getValue();

		
	}

	






	

}
