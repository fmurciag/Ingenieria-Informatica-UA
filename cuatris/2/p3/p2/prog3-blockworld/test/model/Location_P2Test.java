package model;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import model.exceptions.BadLocationException;

/**
 * 
 * @author paco
 *
 */

public class Location_P2Test {

    World earth,mars, mercury;
    Location le,lm;

    /* Este metodo se ejecuta antes de cada Test,
       de manera que los objetos 'earth', 'mars', 'mercury', 'le' y 'lm' son distintos en cada test  */
    @Before
    public void setUp() throws Exception {
        earth = new World(1, 100, "Earth", "Steve");
        mars = new World(2, 100, "Mars", "Steve");
        mercury = new World(3,2,"Mercury", "Steve"); 
        
        le = new Location(earth,1.1,2.2,3.3);
        lm = new Location(mars, 10.01, 20.02, 30.03);
    }

 
    
    /* 
     * Comprueba que para check, las localizaciones en los límites del mundo son válidas
     */
    @Test
    public final void testCheckLimits() throws RuntimeException
    {
    	World world = new World(3,7,"Mercury", "Steve");
    	
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
     * Comprueba que para check, las localizaciones fuera los límites del mundo no son válidas
     */
    @Test
    public final void testCheckOutOfLimits() throws RuntimeException
    {
    	mercury = new World(3,7,"Mercury", "Steve");
    	
    	Location loc1 = new Location(mercury,3.0001,Location.UPPER_Y_VALUE, 3.0);
    	Location loc2 = new Location(mercury,-3.0,Location.SEA_LEVEL,3.00001);
    	Location loc3 = new Location(mercury, 3.0,Location.UPPER_Y_VALUE,-3.00001);
    	Location loc4 = new Location(mercury,-3.00001,Location.SEA_LEVEL,-3.0);
    	assertFalse(Location.check(loc1));
       	assertFalse(Location.check(loc2.getWorld(),loc2.getX(),loc2.getY(),loc2.getZ()));
    	assertFalse(Location.check(loc3));
       	assertFalse(Location.check(loc4.getWorld(),loc4.getX(),loc4.getY(),loc4.getZ()));	
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
     * Comprueba isFree en un mundo. Todas las posiciones con bloques y donde está player debe
     * devolver false y las vacías true.
     */
    
    @Test
    public final void testIsFree() {
    	
    	
    	Location loc= null; 
    	/* Hay bloques en
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
    			for (int y=0; y<=Location.UPPER_Y_VALUE; y++) {
    				loc = new Location(mercury,x,y,z);
    				if (y<=70) {
    					if ( (x==1) && (y==70 ) && (z==1) ) //No hay bloque
    						assertTrue("Libre "+loc.toString(),loc.isFree());
    					else 
    						assertFalse("No Libre "+loc.toString(), loc.isFree()); 
    				}
    				else {
    					if ( (x==0) && (y==71) && (z==0) ) //Está Player
    						assertFalse("No Libre "+loc.toString(), loc.isFree());
    					else 
    						assertTrue("Libre "+loc.toString(),loc.isFree());
    				}
    			    				
    			}			
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
    @Test 
    public final void testBelowBadLocationException() {
    	Location loc,locBelow, locResult=null;
    	for (int x=0; x<2; x++)
    		for (int z=0; z<2; z++) {
    				loc = new Location(mercury,x,0,z);
    				locBelow = new Location(mercury,x,-1,z);
    				//System.out.println(locBelow.toString());
    				try {
    					locResult = loc.below();
						fail ("Error aquí nunca debió llegar");
					} catch (BadLocationException e) {
						assertEquals("below = "+locBelow.toString(), null, locResult);
					} catch (Exception f) {
						fail ("Error, la excepción "+f.toString()+" nunca tuvo que ocurrir");
					}
    		}			
    }
    
    /*
     * Prueba método above() para y [0..UPPER_Y_VALUE-1] con mundo. 
     * Para y=UPPER_Y_VALUE sin mundo
     */
    @Test
    public final void testAbove() {
    	World world;
    	Location loc,locAbove;
    	for (int x=0; x<2; x++)
    		for (int z=0; z<2; z++) 
    			for (int y=0; y<=Location.UPPER_Y_VALUE; y++) {
    				world = (y!=Location.UPPER_Y_VALUE ? mercury : null);
    				loc = new Location(world,x,y,z);
    				locAbove = new Location(world,x,y+1,z);				
    				try {
						assertEquals("above = "+locAbove.toString(), locAbove, loc.above());
					} catch (BadLocationException e) {
						fail("La excepción "+e.toString()+" no debió producirse");
					}  				
    			}			
    }
    
    /*
     * Prueba método above() con lanzamiento de excepción BadLocationException 
     * para todos x,z e y=UPPER_Y_VALUE con mundo
     */
    @Test 
    public final void testAboveBadLocationException() {
    	Location loc,locAbove, locResult=null;
    	for (int x=0; x<2; x++)
    		for (int z=0; z<2; z++) {
    				loc = new Location(mercury,x,Location.UPPER_Y_VALUE,z);
    				locAbove = new Location(mercury,x,Location.UPPER_Y_VALUE+1,z);
    				//System.out.println(locAbove.toString());
    				try {
    					locResult = loc.above();
						fail ("Error aquí nunca debió llegar");
					} catch (BadLocationException e) {
						assertEquals("below = "+locAbove.toString(), null, locResult);
					} catch (Exception f) {
						fail ("Error, la excepción "+f.toString()+" nunca tuvo que ocurrir");
					}
    		}			
    }
    
    /*
     * Prueba de las 26 posiciones adyacentes totales posibles.
     */
    @Test
    public final void testGetNeighborhood() {
    	
    	Location center = new Location(mars, 20.0,150.0, -20.0);
    	Location vecino;
    	Set<Location> adyacentes = center.getNeighborhood(); 
    	
    	for (int x=-24; x<=25; x++) 
    		for (int y=0; y<=Location.UPPER_Y_VALUE; y++)
    			for (int z=-24; z<=25; z++) {
    				vecino = new Location (mars,x,y,z);
    				if ((x>=19) && (x<=21) && (y>=149) && (y<=151) && (z>=-21) && (z<=-19) 
    						 && (!vecino.equals(center)) )
    					assertTrue ("Adyacente "+vecino.toString(), adyacentes.contains(vecino));
    				else
    					assertFalse ("Adyacente "+vecino.toString(), adyacentes.contains(vecino));
    			}
    				
    }
    
    /*
     * Prueba de las posiciones adyacentes en un mundo de size 2 con posiciones adyacentes fuera
     * del mundo (Mercury).
     */
    @Test
    public final void testGetNeighborhoodInMercury() {
    	
    	Location center = new Location(mercury, 0.0, Location.UPPER_Y_VALUE, 0.0);
    	Location vecino;
    	Set<Location> adyacentes = center.getNeighborhood(); 
    	
    	for (int x=-1; x<=1; x++) 
    		for (int y=0; y<=Location.UPPER_Y_VALUE; y++)
    			for (int z=-1; z<=1; z++) {
    				vecino = new Location (mercury,x,y,z);
    				if ((x>=0) && (x<=1) && (y>=Location.UPPER_Y_VALUE-1) && (y<=Location.UPPER_Y_VALUE+1) && (z>=0) && (z<=1) 
    						 && (!vecino.equals(center)) )
    					assertTrue ("Adyacente "+vecino.toString(), adyacentes.contains(vecino));
    				else
    					assertFalse ("Adyacente "+vecino.toString(), adyacentes.contains(vecino));
    			}
    				
    }
    
    /*
     * Prueba de las posiciones adyacentes en posiciones que no pertenecen a un mundo.
     */
    @Test
    public final void testGetNeighborhoodInNullWorld() {
    	
    	Location center = new Location(null, 0.0, Location.UPPER_Y_VALUE, 0.0);
    	Location vecino;
    	Set<Location> adyacentes = center.getNeighborhood(); 
    	
    	for (int x=-1; x<=1; x++) 
    		for (int y=0; y<=Location.UPPER_Y_VALUE; y++)
    			for (int z=-1; z<=1; z++) {
    				vecino = new Location (null,x,y,z);
    				if ((x>=-1) && (x<=1) && (y>=Location.UPPER_Y_VALUE-1) && (y<=Location.UPPER_Y_VALUE+1) && (z>=-1) && (z<=1) 
    						 && (!vecino.equals(center)) )
    					assertTrue ("Adyacente "+vecino.toString(), adyacentes.contains(vecino));
    				else
    					assertFalse ("Adyacente "+vecino.toString(), adyacentes.contains(vecino));
    			}
    				
    }
    
}
