/**
 * 
 */
package mains;

import model.BlockWorld;

/**
 * @author pierre
 *
 */
public final class Main3_interactivo {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		BlockWorld game = BlockWorld.getInstance();	
		if (args.length > 0)
			game.playFile(args[0]);
		else
			game.playFromConsole();
	}

}
