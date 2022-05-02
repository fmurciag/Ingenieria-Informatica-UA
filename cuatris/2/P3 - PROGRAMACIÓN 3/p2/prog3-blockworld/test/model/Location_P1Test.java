package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author paco
 *
 */

public class Location_P1Test {

    World earth,mars;
    Location le,lm;

    /* Este metodo se ejecuta antes de cada Test,
       de manera que los bjetos 'earth', 'mars', 'le' y 'lm' son distintos en cada test  */
    @Before
    public void setUp() {
    	 earth = new World("Earth");   
         mars = new World("Mars");		
        
        le = new Location(earth,1.1,2.2,3.3);
        lm = new Location(mars, 10.01, 20.02, 30.03);
    }

    /**
     * Comprueba los getters
     */
    @Test
    public final void testGetters() {
        assertSame("getWorld", earth, le.getWorld());
        assertEquals("getX", 1.1, le.getX(),0.01);  // 0.01 is the maximum delta when comparing two doubles
        assertEquals("getY", 2.2, le.getY(),0.01);
        assertEquals("getZ", 3.3, le.getZ(),0.01);
    }

    @Test
    public final void testGetters_2() {
    	//  fail("add similar asserts for 'lm'");
        assertEquals("getWorld", mars, lm.getWorld());
        assertEquals("getX", 10.01, lm.getX(),0.01);
        assertEquals("getY", 20.02, lm.getY(),0.01);
        assertEquals("getZ", 30.03, lm.getZ(),0.01);
    }


    // NEW
    @Test
    public final void testGetters_3() {
        Location auxloc = new Location(earth, 20.5, Location.UPPER_Y_VALUE+0.0001, 41.1);
        
        assertSame (earth,auxloc.getWorld());
        assertEquals (20.5,auxloc.getX(),0.0);
        assertEquals (Location.UPPER_Y_VALUE+0.0001,auxloc.getY(),0.0); //modificado para P2
        assertEquals (41.1,auxloc.getZ(),0.0);
        assertFalse (Location.check(auxloc)); //añadido para P2
    }
       
    @Test
    public final void testGetters_4() {

        Location auxloc = new Location(earth, 20.5, -0.0001, 41.1);
        assertEquals (-0.0001,auxloc.getY(),0.0);   //modificado para la P2
        assertFalse (Location.check(auxloc)); //añadido para P2
    }

    /**
     * Comprueba los setters
     */
    @Test
    public final void testSetters() {
    	
    	le.setWorld(mars);
    	assertSame("setWorld",mars,le.getWorld());
    	
    	le.setX(1.5);
    	assertEquals("setX",1.5,le.getX(),0.01);
    	
    	le.setY(2.6);
    	assertEquals("setY",2.6,le.getY(),0.01);

    	le.setZ(3.7);
    	assertEquals("setZ",3.7,le.getZ(),0.01);
    }
    
    @Test
    public final void testSetters_2() {
        //  fail("add similar asserts for 'lm'");

    	lm.setWorld(earth);
    	assertSame("setWorld",earth,lm.getWorld());
    	
    	lm.setX(10.5);
    	assertEquals("setX",10.5,lm.getX(),0.01);
    	
    	lm.setY(20.6);
    	assertEquals("setY",20.6,lm.getY(),0.01);

    	lm.setZ(30.7);
    	assertEquals("setZ",30.7,lm.getZ(),0.01);

    	le.setY(Location.SEA_LEVEL+5);
    	assertEquals("setY",Location.SEA_LEVEL+5,le.getY(),0.01);
    }
    
    /**
     * Comprueba que un valor 'y' fuera de rango es acotado por el setter
     */
    @Test
    public final void testSettersIncorrectY() {
 	
    	le.setY(-0.06);
    	assertEquals("setY",-0.06,le.getY(),0.01);  //modificado para P2
    	assertFalse (Location.check(le.getWorld(),le.getX(),le.getY(),le.getZ())); //añadido a P2
    
   }

    @Test
    public final void testSettersIncorrectY_2() {
    	//  fail("test setting 'y' coordinate beyond UPPER_Y_VALUE");
 	
    	le.setY(Location.UPPER_Y_VALUE+0.06);
    	assertEquals("setY",Location.UPPER_Y_VALUE+0.06,le.getY(),0.01); //modificado para P2
    	assertFalse (Location.check(le.getWorld(),le.getX(),le.getY(),le.getZ())); //añadido a P2	    	
    }

    /**
     * Comprueba el constructor sobrecargado, con valor 'y' fuera de rango
     */
    @Test
    public final void testConstructor() {
    	Location ly;
    	
    	ly = new Location(earth,-15.7,-16.8,-18.9);
        assertSame("getWorld", earth, ly.getWorld());
        assertEquals("getX", -15.7, ly.getX(),0.01); 
        assertEquals("getY", -16.8, ly.getY(),0.01);   //Modificado para P2
        assertEquals("getZ", -18.9, ly.getZ(),0.01);  
        assertFalse (Location.check(ly.getWorld(),ly.getX(),ly.getY(),ly.getZ()) ); //añadido para P2
    } 
    
    @Test
    public final void testConstructor_2() {
    	// fail("test calling the overloaded constructor with 'y' value beyond UPPER_Y_VALUE");
    	Location lyy;
    	lyy = new Location(mars,15.7,12345.6,-18.9);
        assertSame("getWorld", mars, lyy.getWorld());
        assertEquals("getX", 15.7, lyy.getX(),0.01); 
        assertEquals("getY", 12345.6, lyy.getY(),0.01); //modificado para P2
        assertEquals("getZ", -18.9, lyy.getZ(),0.01);  	
        assertFalse (Location.check(lyy));   //Añadido para P2
    }

   
    @Test
    public final void testCopyConstructor() {
    	// fail("test the copy constructor");
    	Location lce = new Location(le);
    	
        assertSame("getWorld", earth, lce.getWorld());
        assertEquals("getX", 1.1, lce.getX(),0.01);
        assertEquals("getY", 2.2, lce.getY(),0.01);
        assertEquals("getZ", 3.3, lce.getZ(),0.01); 	
    }

    /**
     * Comprueba la operación add() con posiciones del mismo mundo
     */
    @Test
    public final void testAddSameWorlds() {
    	Location lie = new Location(earth,3.3,2.2,1.1);
        lie.add(le);    	
        assertSame("getWorld", earth, lie.getWorld());
        assertEquals("getX", 4.4, lie.getX(),0.01); 
        assertEquals("getY", 4.4, lie.getY(),0.01);
        assertEquals("getZ", 4.4, lie.getZ(),0.01);
    }

    /**
     * Comprueba la operación add() con posiciones en diferentes mundos
     */
    @Test
    public final void testAddDifferentWorlds() {
    	
    	le.add(lm);  // error
        assertSame("getWorld", earth, le.getWorld());
        assertEquals("getX", 1.1, le.getX(),0.01); 
        assertEquals("getY", 2.2, le.getY(),0.01);
        assertEquals("getZ", 3.3, le.getZ(),0.01);
    }
    
    /**
     * Comprueba que la operación add() siempre devuelve this.
     */
    @Test
    public final void testAddReturnsThis() {    	
    	Location le2 = le.add(lm);  // error
        assertSame("Add returns this?", le, le2);
    	
        le2 = lm.add(lm);  // ok
        assertSame("Add returns this?", lm, le2);

        le2 = lm.add(new Location(mars,1.0,Location.UPPER_Y_VALUE, 0.0));
        assertSame("Add returns this?", lm, le2);

    }
    
       
    @Test
    public final void testAdd_Y_OutOfRange() {
        // fail("Test adding 'le' to location [3.3,Location.UPPER_Y_VALUE,1.1] in the same world");
    	
      	Location lieee = new Location(earth,3.3,Location.UPPER_Y_VALUE+2.2,1.1);
        assertSame("getWorld", earth, lieee.getWorld());
        assertEquals("getX", 3.3, lieee.getX(),0.01); 
        assertEquals("getY", 257.2, lieee.getY(),0.01);  //modificado para P2
        assertEquals("getZ", 1.1, lieee.getZ(),0.01);
        assertFalse(Location.check(lieee));  //Añadido para la P2

    }
     
    /**
     * Comprueba el método que calcula la distancia entre dos posiciones
     */
    @Test
    public final void testDistance() {
    	assertEquals("distance (different worlds)",le.distance(lm),-1.0,0.01);
    	
    	Location ld = new Location(le.getWorld(),le.getX()+1,le.getY()+2,le.getZ()+2);
    	assertEquals("distance", 3.0, le.distance(ld), 0.01);

    	Location ldd = new Location(le.getWorld(),le.getX()+3,le.getY()+4,le.getZ()+0);
    	assertEquals("distance", 5.0, ldd.distance(le), 0.01);
    }
    
    /**
     * Comprueba el método length()
     */
    @Test
    public final void testLength() {
    	assertEquals("lenght", 4.1158, le.length (), 0.01);
    	
    	Location ld = new Location(mars,1,2,2);
    	assertEquals("lenght", 3.0, ld.length (), 0.01);
    }

    /**
     * Comprueba el método multiply()
     */
    @Test
    public final void testMultiply() {
  	
    	le.multiply(2.0);
        assertSame("getWorld", earth, le.getWorld());
        assertEquals("getX", 2.2, le.getX(),0.01);  // 0.01 is the maximum delta when comparing two doubles
        assertEquals("getY", 4.4, le.getY(),0.01);
        assertEquals("getZ", 6.6, le.getZ(),0.01);
    }
 
    @Test
    public final void testMultiply_Y_OutOfRange() {
    	// fail("Test multiplying object 'le' by UPPER_Y_VALUE");
    	
    	le.multiply(Location.UPPER_Y_VALUE);
        assertSame("getWorld", earth, le.getWorld());
        assertEquals("getY", 561.0, le.getY(),0.01);  //modificado para P2
        assertFalse (Location.check(le));  //añadido para P2
    }

    // NEW
    @Test
    public final void testMultiply_Y_BelowRange() {
    	
        le.multiply(-4.0);
        assertEquals("getX", -4.4, le.getX(),0.01);
        assertEquals("getY", -8.8, le.getY(), 0.01); //modificado para P2
        assertEquals("getZ", -13.2,le.getZ(), 0.01);
        assertFalse (Location.check(le));  //añadido para P2
    }
    
    /**
     * Comprueba la resta entre localizaciones de mundos diferentes
     */
    @Test
    public final void testSubstractDifferentWorlds() {

    	le.substract(lm); // mundos diferentes
        assertSame("getWorld", earth, le.getWorld());
        assertEquals("getX", 1.1, le.getX(),0.01); 
        assertEquals("getY", 2.2, le.getY(),0.01);
        assertEquals("getZ", 3.3, le.getZ(),0.01);    	
    }

    
    /**
     * Comprueba la resta entre localizaciones del mismo mundo
     */
    @Test
    public final void testSubstractSameWorlds() {
    
        Location lec = new Location(earth,1,3,3);
        le.substract(lec);
        assertSame("getWorld", earth, le.getWorld());
        assertEquals("getX", 0.1, le.getX(),0.01); 
        assertEquals("getY", -0.799999, le.getY(),0.01);  //modificado para P2
        assertEquals("getZ", 0.3, le.getZ(),0.01);
        assertFalse (Location.check(le)); //Añadido para P2
    }        
        
    @Test
    public final void testSubstractSameLocation() {
        // fail("test: le - le == (0,0,0)";
        
        le.substract(le);
        assertSame("getWorld", earth, le.getWorld());
        assertEquals("getX", 0.0, le.getX(),0.01); 
        assertEquals("getY", 0.0, le.getY(),0.01);
        assertEquals("getZ", 0.0, le.getZ(),0.01);
    }

    // NEW (era testChainedSubstracts)
    @Test
    public final void testSubstractReturnsThis() {
        
        Location lec = new Location(earth,0.1,0.2,0.3);
        Location lecc = le.substract(lec);
        assertSame("substract() returns this?", lecc, le);
    }

    /**
     * Comprueba el método zero()
     */
    @Test
    public final void testZero() {
        assertSame("getWorld", earth, le.getWorld());
        assertEquals("getX", 1.1, le.getX(),0.01); 
        assertEquals("getY", 2.2, le.getY(),0.01);
        assertEquals("getZ", 3.3, le.getZ(),0.01);
        
        le.zero();
    	
        assertSame("getWorld", earth, le.getWorld());
        assertEquals("getX", 0.0, le.getX(),0.01); 
        assertEquals("getY", 0.0, le.getY(),0.01);
        assertEquals("getZ", 0.0, le.getZ(),0.01);
    }

    /**
     * Comprueba toString()
     */
    @Test
    public final void testToString() {
        assertEquals("toString", "Location{world=Earth,x=1.1,y=2.2,z=3.3}", le.toString());        
        assertEquals("toString", "Location{world=Mars,x=10.01,y=20.02,z=30.03}", lm.toString());
    }
    
    @Test
    public final void testToStringNullWorld() {
        // fail("test the toString method when applied to a null world location");
    	lm.setWorld(null);
        assertEquals("toString", "Location{world=NULL,x=10.01,y=20.02,z=30.03}", lm.toString());
    }

    @Test
    public final void testEquals() {
		
        Location le2 = new Location(earth,le.getX(),le.getY(), le.getZ());
	Location lce = new Location(le);
	lce.setWorld(mars);
	assertFalse(le.equals(null));
	assertTrue(le.equals(le));
	assertTrue(le.equals(le2));
	assertFalse(le.equals(lm));
	assertFalse(lm.equals(le));
	assertFalse(le.equals(lce));
	assertFalse(lm.equals(lce));
	assertFalse(lm.equals(mars));
    }
    
    @Test
    public final void testEquals2() {

    	World planetB = new World(earth.getName());  
        Location le2 = new Location(planetB,le.getX(),le.getY(), le.getZ());
        assertTrue(earth.equals(planetB));
        assertTrue(le.equals(le2));
    }
}
