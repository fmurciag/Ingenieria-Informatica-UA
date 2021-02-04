/**
 * 
 */
package model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.exceptions.BadLocationException;

/**
 * @author pierre
 *
 */
public class World_PreP2Test {

	World world5x5;
	World world3x3;
	final String NEIGHBOURHOOD3X3 = 
			"gg. dd. dd.\n" + 
			"gg. dd. dd.\n" + 
			"g.. d.. dW.";
	final String NEIGHBOURHOOD5X5_1 = 
			".P. ggg ddd\n" + 
			"... ggg ddd\n" + 
			"... ggg ddd";
	final String NEIGHBOURHOOD5X5_2 =
			"sss *** XXX\n" + 
			"sss *** XXX\n" + 
			"sss *** XXX";
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		Material.rng.setSeed(1L);
		world5x5 =  new World(0, 5, "World 5x5");
		world3x3 =  new World(1, 3, "World 3x3");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/*
	 * Accedemos a elementos adyacentes a player buscando bloques (Block)
	 */
	//TODO
	@Test
	public final void testGetBlockAt() {
		/* 
		   ... i.. gg. 
		   ... .P. gg. 
		   ... F.. g.. 
		*/
		Block blk=null;
		try {
		 for (double z=-1.0; z<2.0; z++) { //Adyacentes en el plano inferior x e y fijas
		   blk = world3x3.getBlockAt(new Location(world3x3,-1.0, 62.0, z));
		   assertNotNull(blk);
		   assertEquals(Material.GRASS,blk.getType());
		   for (double x=-1.0; x<2.0; x++) { //Todos los adyacentes plano superior NO HAY NADA
			   blk = world3x3.getBlockAt(new Location(world3x3,-1.0, 64.0, z));
			   assertNull(blk);
		   }
		 }
		 fail("Comprueba que no hay bloques donde están los items y el jugador");
		} catch (Exception e) {
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
		}
		
	}

	//TODO
	//Excepción por mundos distintos de World y Location
	@Test(expected=BadLocationException.class)
	public final void testGetBlockAtException1() throws BadLocationException {
		fail("Comprueba que getBlockAt() lanza BadLocationException por mundos distintos de World y Location");
	}

	//TODO
	//Excepción por Location no pertenece a mundo alguno
	@Test(expected=BadLocationException.class)
	public final void testGetBlockAtException2() throws BadLocationException {
		fail("Comprueba que getBlockAt() lanza BadLocationException por mundo null en Location");
	}
	

	//Buscamos las alturas en dos puntos x,z del mundo world5x5
	@Test
	public final void testGetHighestLocationAt() {
		//Las posiciones solución
		Location loc1 = new Location(world5x5,-2.0,59.0,-2.0);
		Location loc2 = new Location(world5x5,0.0,58.0,1.0);
		
		Location loc =null;
		try {
			
			 loc = world5x5.getHighestLocationAt(new Location(world5x5,-2.0,100,-2.0));
		     assertEquals (loc1, loc);
		     loc = world5x5.getHighestLocationAt(new Location(world5x5,0.0,63,1.0));
		     assertEquals (loc2, loc);
			
		} catch (Exception e) {
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
		}
	}

	//TODO
	//Excepción BadLocationException por mundos distintos entre World y Location o por Location no 
	// pertenecer a mundo alguno.
	@Test
	public final void testGetHighestLocationAtException() {
		fail("Comprueba que getHighestLocationAt() lanza BadLocationException por mundos distintos o mundo null en el argumento Location.");
		
	}

	//Comprueba getNeighbourhoodString para mundos y posiciones distintos
	@Test
	public final void testGetNeighbourhoodString() throws BadLocationException {
		
		System.out.println(world5x5.getNeighbourhoodString(new Location(world5x5,0, 58,1)));
		System.out.println("-----------------");
	    compareLines(NEIGHBOURHOOD5X5_1, world5x5.getNeighbourhoodString(new Location(world5x5,0, 58,1)));
	    compareLines(NEIGHBOURHOOD5X5_2, world5x5.getNeighbourhoodString(new Location(world5x5,1, 0,1)));
	    compareLines(NEIGHBOURHOOD3X3, world3x3.getNeighbourhoodString(new Location(world3x3,0, 61,0)));
	   
	}

	

	//Prueba para getter de size
	@Test
	public final void testGetSize() {
		assertEquals (3,world3x3.getSize());
		assertEquals (5,world5x5.getSize());
	}

	//TODO
	//Prueba isFree() para los adyacentes a player en el mundo world3x3
	@Test
	public final void testIsFree() {
		try {
		 for (double z=-1.0; z<2.0; z++) { //Adyacentes en el plano inferior x e y fijas
		   assertFalse( world3x3.isFree(new Location(world3x3,-1.0, 62.0, z)) );
		   for (double x=-1.0; x<2.0; x++) { //Todos los adyacentes plano superior NO HAY NADA
			   assertTrue(world3x3.isFree(new Location(world3x3,-1.0, 64.0, z)));
		   }
		 }
		 fail("Comprueba las posiciones donde hay items");
		} catch (Exception e) {
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
		}
	}

	//TODO
	//Busca los items en los adyacentes a player en el mundo world3x3
	@Test
	public final void testGetItemsAt() {
		fail("Comprueba que getItmesAt() devuelve los items en las posiciones adyacentes a player en el mundo world3x3");
	}

	//Prueba para getter de seed
	@Test
	public final void testGetSeed() {
		assertEquals (1,world3x3.getSeed());
		assertEquals (0,world5x5.getSeed());
	}
	
	//TODO
	//Borramos los items del mundo world5x5 con removeItemsAt y luego comprobamos que ya no están
	@Test
	public void testRemoveItemsAtWorld5x5() {
		
		  
		Location loc;
		try {
			/* 
			   Location{world=World 5x5,x=0.0,y=60.0,z=-2.0} 
			 	(BREAD,2)
			*/
			loc = new Location(world5x5,0.0,60.0,-2.0);
			ItemStack is =  new ItemStack(Material.BREAD,2);
			assertEquals(is, world5x5.getItemsAt(loc));
			world5x5.removeItemsAt(loc);
			is = world5x5.getItemsAt(loc);
			assertNull(is);
			
			 /* Location{world=World 5x5,x=2.0,y=59.0,z=0.0} 
			 	(BEEF,3)
			 */
			fail("Impementa esta prueba de forma similar a la de arriba");
			
		} catch (Exception e) {
			fail("Error no debió lanzar la excepcion "+e.getClass().getName());
		}
	}
	
	//Borramos los items del mundo world3x3 con removeItemsAt y luego comprobamos que ya no están
	@Test
	public void testRemoveItemsAtWorld3x3() {
		fail("Implementa esta prueba de  forma similaral testanterior.");

	}
	
	//Probamos todas las posibilidades de equals con los campos size, seed y name
	@Test
	public final void testEqualsObject() {
		try {
			
			
			assertFalse(world5x5.equals(null));
			
			//El mismo mundo
			
			assertTrue(world5x5.equals(world5x5));
			
			//Dos mundos con todos los parámetros iguales.
			World world5x5bis =  new World(0, 5, "World 5x5");
			assertTrue(world5x5.equals(world5x5bis));
			
			//Distinto seed
			world5x5bis =  new World(1, 5, "World 5x5");
			assertFalse(world5x5.equals(world5x5bis));
			
			//Distinto tamaño
			world5x5bis =  new World(0, 4, "World 5x5");
			assertFalse(world5x5.equals(world5x5bis));
			
			//Distinto nombre
			world5x5bis =  new World(0, 5, "World 5x5bis");
			assertFalse(world5x5.equals(world5x5bis));
			
			//Parámetro de otra clase
			assertFalse(world5x5.equals(NEIGHBOURHOOD5X5_1));;
			
		} catch (Exception e) {
			fail ("Error: No debió lanzarse la excepción "+e.getClass().getName());
		}
	}
	
	//Prueba que hashCode se creó con los campos seed, size y name
	@Test
	public void testHashCode() {
		World world5x5bis =  new World(0, 5, "World 5x5");
		assertEquals("same worlds, same codes",world5x5.hashCode(), world5x5bis.hashCode());
		
		world5x5bis =  new World(1, 5, "World 5x5");
		assertNotEquals ("different seed, different codes", world5x5.hashCode(), world5x5bis.hashCode());
		
		world5x5bis =  new World(0, 4, "World 5x5");
		assertNotEquals("different size, different codes", world5x5.hashCode(), world5x5bis.hashCode());
		
		world5x5bis =  new World(0, 5, "World 5x5bis");
		assertNotEquals("different name, different codes", world5x5.hashCode(), world5x5bis.hashCode());
	
	}
	
	/**********************************************************/
	//FUNCION DE APOYO
	/* Se usa para comparar Strings que devuelven algunos métodos. Evita sacar error por los espacios
	 * finales de cada línea y por los saltos de línea al final
	 */
		void compareLines(String expected, String result) {
			String exp[]=expected.split("\n");
			String res[]=result.split("\n");
			boolean iguales = true;
			for (int i=0; i<exp.length && iguales; i++) {
				assertEquals("linea "+i, exp[i],res[i].trim());
			}
		}

}
