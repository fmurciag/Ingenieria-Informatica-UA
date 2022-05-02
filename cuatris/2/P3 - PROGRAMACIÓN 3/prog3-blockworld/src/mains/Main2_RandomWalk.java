/**
 * 
 */
package mains;

import java.util.Random;

import model.BlockWorld;
import model.World;
import model.entities.Player;
import model.exceptions.BadInventoryPositionException;
import model.exceptions.BadLocationException;
import model.exceptions.EntityIsDeadException;

/**
 * @author pierre
 *
 */
public class Main2_RandomWalk {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BlockWorld game = BlockWorld.getInstance();	
		
		System.out.println("-> game.createWorld(123, 100, \"test\")");
		World world = game.createWorld(1234, 100, "test");
		Random rng = new Random(world.getSeed());

		Player player = world.getPlayer();
		System.out.println(game.showPlayerInfo(player));
		int x,y,z;
		for (int i=0; i<450; i++) // intentamos realizar 450 movimientos
		try {
			x = rng.nextInt(3) - 1; // genera -1, 0 o 1 aleatoriamente
			y = rng.nextInt(3) - 1; 
			// Para que no se vaya 'por las nubes', si sale y==1, lo cambiamos el 50% de las veces por 0 o -1.
			// controlamos que  x, z no sean cero a la vez, para no generar demasadas veces (0,0,0)
			z = rng.nextInt(3) - 1;
			if (y==1 && ((x!=0) || (z!=0))) if (rng.nextDouble() > 0.5) y = - rng.nextInt(2);
			System.out.println("-> game.movePlayer(player, "+x+", "+y+", "+z+")");
			game.movePlayer(player, x,y,z);
			if (rng.nextDouble() < 0.02) {// intentar ingerir alimento el 2% de las veces
				System.out.println("-> game.useItem(player, 1)");
				game.useItem(player, 1);
				System.out.println(game.showPlayerInfo(player));
			}
			if (i % 20 == 0) { // cada 20 movimientos 
				if (player.getInventorySize() > 0) {
					int pos = rng.nextInt(player.getInventorySize());
					System.out.println("-> player.selectItem("+pos+")");
					player.selectItem(pos);
				}
				System.out.println(game.showPlayerInfo(player));
			}
		} catch (BadLocationException | EntityIsDeadException | BadInventoryPositionException  e) {
			System.err.println(e.getMessage());
			System.out.println(game.showPlayerInfo(player));
		}
		System.out.println(game.showPlayerInfo(player));


	}

}
