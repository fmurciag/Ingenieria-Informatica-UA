package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author paco
 *
 */

public class World_PreP1Test {

	World w, other;

	/*
	 * Este metodo se ejecuta antes de cada Test, de manera que los objetos 'w' y
	 * 'other' son distintos en cada test
	 */
	@Before
	public void setUp() {
		w = new World("Earth");
		other = new World("Mars");

	}

	@Test
	public final void testGetters() {
		assertEquals("Name Earth", "Earth", w.getName());
		assertEquals("Name Mars", "Mars", other.getName());
		
		// NEW
		w = new World(null);
		assertNull(w.getName());
	}

	@Test
	public final void testToString() {
		assertEquals("Name Earth", "Earth", w.toString());
		assertEquals("Name Mars", "Mars", other.toString());
		
		// NEW
		World other = new World("A wonderful World");
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
