 /**
 * Main package for the first assignment.
 */
package mains;

import java.util.ArrayList;
import java.util.List;

import model.Location;
import model.World;
import model.entities.Player;
import model.ItemStack;
import model.Material;
import model.Block;
import model.Inventory;
import model.BlockWorld;



/**
@author pierre 20190712
 **/
public class Main1 {

	/**
	 * Entry method.
	 * @param args Arguments from command line.
	 */
	public static void main(String[] args) {
		
		World world = new World("Block World");

		Location c0; // referencia que apunta a null (es decir, a ningún objeto) 
		Location c1 = new Location(world,0.0,0.0,0.0);
		Location c2 = new Location(world,1.0,1.0,1.0);
		Location c3 = new Location(c2);
		Location c4 = new Location(world,-5.0, -5.0, -5.0);

		// Comprueba cómo las siguientes sentencias invocan al método toString() de la clase Location
		System.out.println(c1);
		System.out.println(c2);
		System.out.println(c3);
		System.out.println(c4);

		c2.add(c3).add(c3);
		System.out.println(c2);
		c2.substract(c3).substract(c3);
		System.out.println(c2);
				
		Location [] v = new Location[5];
		for (int i=0; i<5; i++) {
			v[i] = new Location(world,0.0,0.0,0.0);
			System.out.println("" + v[i].getX() + "," + v[i].getY() + "," + v[i].getZ()); 
		}

		List<Location> v2 = new ArrayList<Location>();
		for (int i=0; i<8; i++) {
			v2.add(new Location(world, i,i,i));
			System.out.println(v2.get(i).toString());
		}
		
		
        System.out.println(c2.distance(c1));
        System.out.println(c1.distance(c2));

        System.out.println(c2.getWorld());

        // setters
        c2.setWorld(world);
        c2.setX(2.0);
        c2.setY(2.0);
        c2.setZ(2.0);
        System.out.println( "c2 : " + c2);
        System.out.println( "c2.length() : " + c2.length());

        c2.multiply(1.5).multiply(2.0); // c2 = (c2 * 1.5) * 2.0
        System.out.println( "c2 : "  + c2);

        c2.zero();
        System.out.println( "c2 : " + c2);

        System.out.println( "c1.equals(c2) : " + c1.equals(c2));
        System.out.println( "c2.equals(c1) : " + c2.equals(c1));
        System.out.println( "c2.equals(c3) : " + c2.equals(c3));
        System.out.println( "c2.hashCode() : " + c2.hashCode());


		System.out.println("Max. location elevation value: " + Location.UPPER_Y_VALUE);
		System.out.println("Sea level: " + Location.SEA_LEVEL);					
	}
}