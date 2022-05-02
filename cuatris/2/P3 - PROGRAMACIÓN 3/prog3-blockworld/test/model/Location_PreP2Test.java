package model;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.exceptions.BadLocationException;
import model.exceptions.StackSizeException;
 
/**
 * 
 * @author paco
 *
 */

public class Location_PreP2Test {


    static World mars, mercury;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
        mars = new World(2, 100, "Mars");
        mercury = new World(3,2,"Mercury"); 
	}

 
    
    /* 
     * Comprueba que para check, las localizaciones en los límites del mundo son válidas
     */
    @Test
    public final void testCheckLimits() throws StackSizeException
    {
    	World world = new World(3,7,"Mercury");
    	
    	Location loc1 = new Location(world,3.0,Location.UPPER_Y_VALUE, 3.0);
    	Location loc2 = new Location(world,-3.0,Location.UPPER_Y_VALUE,3.0);
    	Location loc3 = new Location(world, 3.0,Location.UPPER_Y_VALUE,-3.0);
    	Location loc4 = new Location(world,-3.0,Location.UPPER_Y_VALUE,-3.0);
    	assertTrue(Location.check(loc1));
    	assertTrue(Location.check(loc2.getWorld(),loc2.getX(),loc2.getY(),loc2.getZ()));
    	assertTrue(Location.check(loc3));
    	assertTrue(Location.check(loc4.getWorld(),loc4.getX(),loc4.getY(),loc4.getZ()));
    }
    
    /* 
     * Comprueba que para check, las localizaciones fuera de los límites del mundo no son válidas
     */
	//TODO
   @Test
    public final void testCheckOutOfLimits()
    {
    	fail("Comprueba que para check, las localizaciones fuera de los límites del mundo no son válidas");
    }
    
    /* 
     * Comprueba que isFree es false, cuando las posiciones no están asociadas a un mundo.
     */
    @Test
    public final void testNullIsNotFree() {
    	Location loc; 
    	for (int x=-10; x<10; x++)
    		for (int z=-10; z<10; z++) {
    			loc = new Location(null,x,Location.UPPER_Y_VALUE,z);
    			assertFalse(loc.isFree());
    		}			
    }
    
    /*
     * Comprueba isFree en un mundo. Todas las posiciones con bloques  debe
     * devolver false.
     */   
    @Test
    public final void testIsFreeFalse() {
    	
    	
    	Location loc= null; 
    	/* mercury: Hay bloques en
    	 *  X      Y     Z
    	 * ---    ---   ---
    	 *  0   [0..70]	 0
    	 *  0   [0..70]	 1
    	 *  1   [0..70]	 0
    	 *  1   [0..69]	 1
    	 *  Player en (0,71,0)
    	 */
    	//System.out.println(mercury.getPlayer().getLocation().toString());
    	for (int x=0; x<2; x++)
    		for (int z=0; z<2; z++) 
    			for (int y=0; y<=70; y++) {
    				loc = new Location(mercury,x,y,z);
    					if ( (x!=1) || (y!=70 ) || (z!=1) ) //No hay bloque
    						assertFalse("No Libre "+loc.toString(), loc.isFree());     			    				
    			}			
    }
 
    /*
     * Comprueba isFree en un mundo. Donde está player debe
     * devolver false.
     */   
	//TODO
    @Test
    public final void testIsFreeFalsePlayer() {
    	
    	
    	Location loc= null; 
    	/* mercury: Hay bloques en
    	 *  X      Y     Z
    	 * ---    ---   ---
    	 *  0   [0..70]	 0
    	 *  0   [0..70]	 1
    	 *  1   [0..70]	 0
    	 *  1   [0..69]	 1
    	 *  Player en (0,71,0)
    	 */
    	fail("Comprueba isFree en un mundo. Donde está player debe devolver false.");
    }
    
    /*
     * Comprueba isFree en un mundo. Todas las posiciones vacías deben devolver true.
     */   
	//TODO
    @Test
    public final void testIsFreeTrue() {
    	
    	Location loc= null; 
    	/* mercury: Hay bloques en
    	 *  X      Y     Z
    	 * ---    ---   ---
    	 *  0   [0..70]	 0
    	 *  0   [0..70]	 1
    	 *  1   [0..70]	 0
    	 *  1   [0..69]	 1
    	 *  Player en (0,71,0)
    	 */
    	fail("Comprueba isFree en un mundo. Todas las posiciones vacías deben devolver true.");
    }
 
    /*
     * Prueba método below() para y [1..UPPER_VALUE] con mundo. 
     * Para y=0 si no hay mundo
     */
    @Test
    public final void testBelow() {
    	World world;
    	Location loc,locBelow;
    	for (int x=0; x<2; x++)
    		for (int z=0; z<2; z++) 
    			for (int y=0; y<=Location.UPPER_Y_VALUE; y++) {
    				world = (y!=0 ? mercury : null);
    				loc = new Location(world,x,y,z);
    				locBelow = new Location(world,x,y-1,z);				
    				try {
						assertEquals("below = "+locBelow.toString(), locBelow, loc.below());
					} catch (BadLocationException e) {
						fail("La excepción "+e.toString()+" no debió producirse");
					}  				
    			}			
    }
    
    /*
     * Prueba método below() con lanzamiento de excepción BadLocationException 
     * para todos x,z e y=0 con mundo
     */
	//TODO
   @Test 
    public final void testBelowBadLocationException() {
    	fail("Prueba método below() con lanzamiento de excepción BadLocationException para todos x,z e y=0 con mundo.");
    }
    
    /*
     * Prueba método above() para y [0..UPPER_Y_VALUE-1] con mundo. 
     * Para y=UPPER_Y_VALUE sin mundo
     */
	//TODO
    @Test
    public final void testAbove() {
    	fail("Prueba método above() para y [0..UPPER_Y_VALUE-1] con mundo. Para y=UPPER_Y_VALUE sin mundo");
    }
    
    
    /*
     * Prueba de las 26 posiciones adyacentes totales posibles.
     */
    @Test
    public final void testGetNeighborhoodIn() {
    	
    	Location center = new Location(mars, 20.0,150.0, -20.0);
    	Location vecino;
    	Set<Location> adyacentes = center.getNeighborhood(); 
    	
    	for (int x=19; x<=21; x++) 
    		for (int y=149; y<=151; y++)
    			for (int z=-21; z<=-19; z++) {
    				vecino = new Location (mars,x,y,z);
    				if (!vecino.equals(center))
    					assertTrue ("Adyacente "+vecino.toString(), adyacentes.contains(vecino));
    			}
    				
    }
    
    /*
     * Prueba que las posiciones no adyacentes no estén incluídas en el vecindario.
     */
	//TODO
   @Test
    public final void testGetNeighborhoodOut() {
    	fail("Prueba que las posiciones no vecinas no estén incluídas en el vecindario.");
    }
    
    /*
     * Prueba de las posiciones adyacentes en un mundo de size 2 con posiciones adyacentes fuera
     * del mundo (Mercury).
     */
	//TODO
   @Test
    public final void testGetNeighborhoodInMercury() {
    	fail("Prueba de las posiciones adyacentes en un mundo de size 2 con posiciones adyacentes fuera del mundo (Mercury).");
    }
    
    /*
     * Prueba de las posiciones NO adyacentes en un mundo de size 2 con posiciones adyacentes fuera
     * del mundo (Mercury).
     */
	//TODO
   @Test
    public final void testGetNeighborhoodOutMercury() {
    	fail("Prueba de las posiciones NO adyacentes en un mundo de size 2 con posiciones adyacentes fuera del mundo (Mercury).");    				
    }
    
    /*
     * Prueba de las posiciones adyacentes en posiciones que no pertenecen a un mundo.
     */
	//TODO
    @Test
    public final void testGetNeighborhoodInNullWorld() {
    	
    	fail("Prueba de las posiciones adyacentes en posiciones que no pertenecen a un mundo.");
    }

    /*
     * Prueba de las posiciones NO adyacentes en posiciones que no pertenecen a un mundo.
     */
	//TODO
   @Test
    public final void testGetNeighborhoodOutNullWorld() {
    	fail("Prueba de las posiciones NO adyacentes en posiciones que no pertenecen a un mundo.");
    				
    }

}
