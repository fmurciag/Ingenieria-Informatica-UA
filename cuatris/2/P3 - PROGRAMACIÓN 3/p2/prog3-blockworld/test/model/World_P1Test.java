package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author paco
 *
 */

public class World_P1Test {

	World w, other;

	/*
	 * Este metodo se ejecuta antes de cada Test, de manera que los objetos 'w' y
	 * 'other' son distintos en cada test
	 */
	@Before
	public void setUp() {
		w = new World(1,100,"Earth", "Steve");
		other = new World(2,100,"Mars", "Steve");

	}

	@Test
	public final void testGetters() {
		assertEquals("Name Earth", "Earth", w.getName());
		assertEquals("Name Mars", "Mars", other.getName());
		
		// NEW
		w = new World(1,100,null, "Steve");
		assertNull(w.getName());
	}

	@Test
	public final void testToString() {
		assertEquals("Name Earth", "Earth", w.toString());
		assertEquals("Name Mars", "Mars", other.toString());
		
		// NEW
		World other = new World(1,100,"A wonderful World", "Steve");
		assertNotEquals(w.toString(), other.toString());
	}

	@Test
	public final void testEquals() {
		assertFalse(w.equals(null));
		assertTrue(w.equals(w));
		assertFalse(w.equals(other));
		assertFalse(other.equals("Mars"));
		
		// NEW
		assertFalse(w.equals(null));
	}
}
