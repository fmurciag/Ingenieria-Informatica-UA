package model.score;

import model.ItemStack;

// TODO: Auto-generated Javadoc
/**
 * The Class CollectedItemsScore.
 */
public class CollectedItemsScore extends Score<ItemStack> {

	/**
	 * Instantiates a new collected items score.
	 *
	 * @param s the s
	 */
	public CollectedItemsScore(String s){
		// TODO Auto-generated constructor stub
		super(s);
		
	}

	/**
	 * Compare to.
	 *
	 * @param o the o
	 * @return the int
	 */
	@Override
	public int compareTo(Score<ItemStack> o) {
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
	 * @param i the i
	 */
	@Override
	public void score(ItemStack i) {
		 score+=i.getAmount()*i.getType().getValue();

		// TODO Auto-generated method stub
		
	}

	






	

}
