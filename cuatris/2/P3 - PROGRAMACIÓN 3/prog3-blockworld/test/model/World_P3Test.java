package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import model.entities.Animal;
import model.entities.Creature;
import model.entities.Monster;
import model.entities.Player;
import model.exceptions.BadLocationException;
import model.exceptions.EntityIsDeadException;
import model.exceptions.StackSizeException;

/*INFORMACIÓN INICIAL DEL MUNDO WORLD10X10
 * 
 * world10x10 =  bw.createWorld(5, 10, "World 10x10")

Name=Steve
Location{world=World 10x10,x=0.0,y=65.0,z=0.0}
Orientation=Location{world=World 10x10,x=0.0,y=0.0,z=1.0}
Health=20.0
Food level=20.0
Inventory=(inHand=(WOOD_SWORD,1),[])
... ... ggg
... .P. ggg
... ... Mg.

*/
public class World_P3Test {
	
	final String NEIGHBOURHOOD1 = "... ... ggg\n" + 
			"... .P. ggg\n" + 
			"... ... Mg.";
	final String NEIGHBOURHOOD2 = "@#. ... ggg\n" + 
			"@#. .P. ggg\n" + 
			"@#. ... Mg.";
	final String NEIGHBOURHOOD3 = "RL. ... ggg\n" + 
			"RL. .P. ggg\n" + 
			"RL. ... Mg.";

	World world10x10;
	Block bs, bl;
	
	
@Before
public void setUp() throws Exception {
	Material.rng.setSeed(1L);
	world10x10 = new World(5, 10, "World 10x10", "Steve");	
	bl = BlockFactory.createBlock(Material.WATER);
	bs = BlockFactory.createBlock(Material.GRANITE);
}
	
/* Pone un bloque líquido en la posición (0,64,0) y donde hay un bloque GRASS
 * Se comprueba que hay un bloque.
 * Se comprueba que el Y máximo es 64 antes de poner el bloque. Se pone el bloque
 * y se comprueba que Y máximo sigue siendo 64
 */
@Test
public void testAddBlocksAndGetBlock1() {
	try {
		
		//Pone un bloque líquido en la posición (0,64,0) y donde hay un bloque GRASS
		Location loc = new Location(world10x10, 0,64,0);
		//Comprobamos que hay un bloque
		Block b = world10x10.getBlockAt(loc);
		assertNotNull(b);
		assertEquals(Material.GRASS, b.getType());
	
		//Comprobamos que el Y máximo es 64 antes de poner el bloque
		assertEquals(64.0,world10x10.getHighestLocationAt(loc).getY(),0.001);
		world10x10.addBlock(loc, bl);
		Block bloque =world10x10.getBlockAt(loc);
		assertEquals(bl,bloque);
		//Comprobamos que el Y máximo es 64 despues de poner el bloque en y=64
		assertEquals(64.0,world10x10.getHighestLocationAt(loc).getY(),0.001);
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}
			
///Pone 1 bloque sólido sobre una posición donde hay un Monster
/* En la posición (-1,64,1) hay un monster. Se comprueba que es así.
 * Se comprueba que el Y máximo es 63 antes de poner el bloque.
 * Se añade el bloque. Se comprueba que el monstruo ya no existe y que
 * el Y máximo ahora es 64
 */
@Test
	public void testAddBlocksAndGetBlock2() {	
		try {
			 
			Location loc = new Location(world10x10, -1,64,1);
			//Comprobamos que hay un Monster
			Creature creature = world10x10.getCreatureAt(loc);
			assertNotNull(creature);
			//Comprobamos que el Y máximo es 63 antes de poner el bloque
			assertEquals(63.0,world10x10.getHighestLocationAt(loc).getY(),0.001);
			world10x10.addBlock(loc, bs);
			assertEquals(bs, world10x10.getBlockAt(loc));
			//El monstruo ya no existe
			assertNull(world10x10.getCreatureAt(loc));
			//Comprobamos que el Y máximo es 64 después de poner el bloque
			assertEquals(64.0,world10x10.getHighestLocationAt(loc).getY(),0.001);
		
		} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
		}
	
}

//Pone 1 bloque líquido sobre una posición donde hay un animal (-1,66,-4)
/* Se comprueba que el Y máximo es 65 antes de poner el bloque.
 * Se añade el bloque. Se comprueba que el animal ya no existe y que
 * el Y máximo ahora es 66
 */	
@Test
public void testAddBlocksAndGetBlock3() {
	try {
		
		Location loc = new Location(world10x10, -1,66,-4);
		//Comprobamos que hay un animal	
		assertNotNull(world10x10.getCreatureAt(loc));
		
		//Comprobamos que el Y máximo es 65 antes de poner el bloque
		assertEquals(65.0,world10x10.getHighestLocationAt(loc).getY(),0.001);
		world10x10.addBlock(loc, bl);
		Block bloque =world10x10.getBlockAt(loc);
		assertEquals(bl,bloque);
		//El animal ya no existe
		assertNull(world10x10.getCreatureAt(loc));
		//Comprobamos que el Y máximo es 66 despues de poner el bloque en y=66
		assertEquals(66.0,world10x10.getHighestLocationAt(loc).getY(),0.001);
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}

//Pone 1 bloque sólido sobre una posición libre (1,67,1)
/* Comprobar que en esa posición no hay ni item, ni bloque, ni creature.
 * Se comprueba que el Y máximo es 63 antes de poner el bloque sólido
 * Se añade el bloque sólido y se comprueba que así ha sido.
 * Comprobamos que ahora el Y máximo es 67
 * 
 */
@Test
public void testAddBlocksAndGetBlock4() {
try {
	//Pone un bloque sólido
	Location loc = new Location(world10x10, 1,67,1);
	//Comprobamos que no hay nada
	assertNull(world10x10.getBlockAt(loc));
	assertNull(world10x10.getItemsAt(loc));
	assertNull(world10x10.getCreatureAt(loc));
	//Comprobamos que el Y máximo es 63 antes de poner el bloque
	assertEquals(63.0,world10x10.getHighestLocationAt(loc).getY(),0.001);
	world10x10.addBlock(loc, bs);
	assertEquals(bs, world10x10.getBlockAt(loc));
	//Comprobamos que el Y máximo es 67 después de poner el bloque
		assertEquals(67.0,world10x10.getHighestLocationAt(loc).getY(),0.001);
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}
	
	
/* Pone 1 bloque líquido sobre una posición por encima de la altura máxima de la x,z donde
   se encuentra el player. La altura máxima, por tanto debe de cambiar a la nueva posición del
   bloque añadido. 
   */
@Test
public void testAddBlocksAndGetBlock5() {
	try {
			
		//Pone un bloque líquido en la posición (0,64,0) y donde hay un bloque GRASS
		Location loc = new Location(world10x10, 0,70,0);
		
		//Comprobamos que no hay nada.
		assertNull(world10x10.getBlockAt(loc));
		assertNull(world10x10.getCreatureAt(loc));
		assertNull(world10x10.getItemsAt(loc));
		
		//Comprobamos que el Y máximo es 64 antes de poner el bloque
		assertEquals(64.0,world10x10.getHighestLocationAt(loc).getY(),0.001);
		world10x10.addBlock(loc, bl);
		assertEquals(bl, world10x10.getBlockAt(loc));
		//Comprobamos que el Y máximo es 70 después de poner el bloque
		assertEquals(70.0,world10x10.getHighestLocationAt(loc).getY(),0.001);
		
	} catch (Exception e) {
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}	

/* Movemos al player a la posición límite (-4,65,0) lo orientamos hacia el oeste 
 * e intentamos añadir un bloque en esa posición (-5,65,0). Debe saltar la excepción
 * BadLocationException
   */
@Test(expected=BadLocationException.class)
public void testAddBlocksOutOfWorldException() throws BadLocationException, EntityIsDeadException {
		Player player = world10x10.getPlayer();
		for (int i=0; i<4; i++)
			player.move(-1, 0, 0);
		player.orientate(-1, 0, 0);
		Location loc = player.getOrientation();
		world10x10.addBlock(loc, bl);
}	

//Intenta 1 bloque sólido sobre la posición del player.
@Test
public void testAddBlocksAndGetBlockInPlayerPositionException() throws RuntimeException{
	
	Location loc = new Location(world10x10, 0,65,0); //Está Player
		//Comprobamos que efectivamente está Player
		assertEquals(loc, world10x10.getPlayer().getLocation());
		try {
			//Intentamos poner el bloque
			world10x10.addBlock(loc, bs);
			fail("Error: debió lanzar la excepción BadLocationException");
		} catch (BadLocationException e) {
			//Comprobamos que no lo ha puesto
			try {
				assertNull(world10x10.getBlockAt(loc));
			} catch (BadLocationException e1) {
				throw new RuntimeException(e1);
			}
			assertEquals(loc, world10x10.getPlayer().getLocation());
		} catch (Exception e) {
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
		}
}


 
//Comprueba el método desde la posición inicial del player
@Test
public void testGetNeighbourhoodString1() {
	Location loc = new Location(world10x10,0,65,0);
	try {
		compareLines(NEIGHBOURHOOD1,world10x10.getNeighbourhoodString(loc));
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}
/* Comprueba el método desde la posición inicial del player después de añadir
Bloques de agua y lava en posiciones vacías */
@Test
public void testGetNeighbourhoodString2() {
	Location locplayer = new Location(world10x10,0,65,0);
	try {
		
		addLiquidBlocks();
		
		System.out.println(world10x10.getNeighbourhoodString(locplayer));
		compareLines(NEIGHBOURHOOD2,world10x10.getNeighbourhoodString(locplayer));
		} catch (Exception e) {
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
		}
}

/* Añade bloques de agua y lava en posiciones vacías y después de poner en las mismas
   posiciones items y creatures se comprueba que se imprimen los items (GRANITE) y creatures (Animal) y
   no los bloques.
*/
@Test
public void testGetNeighbourhoodString3() {
	Location locplayer = new Location(world10x10,0,65,0);
		try {
			
			addLiquidBlocks();
		    addItemsAndCreatures(new ItemStack(Material.GRANITE,1), 'A');
			
			System.out.println(world10x10.getNeighbourhoodString(locplayer));
			compareLines(NEIGHBOURHOOD3,world10x10.getNeighbourhoodString(locplayer));
			} catch (Exception e) {
				fail("Error: no debió lanzar la excepción "+e.getClass().toString());
			}
}	

/* Comprueba isFree en posiciones vacías 
 * ... ... ggg
   ... .P. ggg
   ... ... Mg.
*/	
@Test
public void testIsFreeTrue1() {
	//vacías
	for (int z=-1; z<2; z++ ) {
		Location loc = new Location (world10x10,-1,66,z);
		try {
			assertTrue(world10x10.isFree(loc));
		} catch (Exception e) {
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
		}
	}
}

/* Comprueba isFree en posiciones con items:
Location{world=World 10x10,x=-3.0,y=67.0,z=-2.0}=(APPLE,1)
Location{world=World 10x10,x=-1.0,y=67.0,z=-3.0}=(IRON_SHOVEL,1)
Location{world=World 10x10,x=-1.0,y=63.0,z=4.0}=(WATER_BUCKET,3)
Location{world=World 10x10,x=0.0,y=63.0,z=4.0}=(WATER_BUCKET,2)
Location{world=World 10x10,x=4.0,y=63.0,z=4.0}=(IRON_SWORD,1)
Location{world=World 10x10,x=4.0,y=66.0,z=-4.0}=(APPLE,2)
Location{world=World 10x10,x=-4.0,y=62.0,z=5.0}=(WATER_BUCKET,2)
Location{world=World 10x10,x=4.0,y=64.0,z=3.0}=(BREAD,4)
*/
@Test
public void testIsFreeTrue2() {
	
	//Con items
	try {
		assertTrue(world10x10.isFree(new Location(world10x10,-3.0,67.0,2.0)));
		assertTrue(world10x10.isFree(new Location(world10x10,-1.0,67.0,-3.0)));
		assertTrue(world10x10.isFree(new Location(world10x10,-1.0,63.0,4.0)));
		assertTrue(world10x10.isFree(new Location(world10x10,0.0,63.0,4.0)));
		assertTrue(world10x10.isFree(new Location(world10x10,4.0,63.0,4.0)));
		assertTrue(world10x10.isFree(new Location(world10x10,4.0,66.0,-4.0)));
		assertTrue(world10x10.isFree(new Location(world10x10,-4.0,62.0,5.0)));
		assertTrue(world10x10.isFree(new Location(world10x10,4.0,64.0,3.0)));
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}
	
/* Se añaden al mundo world10x10 los bloques líquidos tal como queda más
   abajo. Comprueba isFree en las posiciones con bloques líquidos
   @#. ... ggg\n 
   @#. .P. ggg\n 
   @#. ... Mg.
*/
@Test
public void testIsFreeTrue3() {
	Location loc;
	try {
		
		addLiquidBlocks();
		
		//Comprobamos en items con bloques de agua
		for (int z=-1; z<2; z++ ) {
			loc = new Location (world10x10,-1,66,z);
			assertTrue(world10x10.isFree(loc));
		}
		//Comprobamos en items con bloques de lava
		for (int z=-1; z<2; z++ ) {
			loc = new Location (world10x10,0,66,z);
			assertTrue(world10x10.isFree(loc));
		}
	} catch (Exception e) {
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}

/* Comprueba isFree en posiciones con bloques sólidos
   ... ... ggg
   ... .P. ggg
   ... ... Mg.
*/
@Test
public void testIsFreeFalse1() {
	Location loc;
	try {				
	//Comprobamos en items con bloques GRASS
		for (int x=-1; x<2; x++ ) {
			loc = new Location (world10x10,x,64,-1);
			assertFalse(world10x10.isFree(loc));
		}
	} catch (Exception e) {
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}

//Comprueba isFree en posicion del Player
@Test
public void testIsFreeFalse2() {
	try {		
		assertFalse(world10x10.isFree(new Location(world10x10,0,65,0)));
	} catch (Exception e) {
				fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}
	
/* Comprueba isFree en posicion de una criatura Animal o Monstruo
	Monster [location=Location{world=World 10x10,x=1.0,y=63.0,z=4.0}, health=2.0]
	Animal [location=Location{world=World 10x10,x=-1.0,y=66.0,z=-4.0}, health=20.0]
*/
@Test
public void testIsFreeFalse3() {

	try {		
		assertFalse(world10x10.isFree(new Location(world10x10,1,63,4)));
		assertFalse(world10x10.isFree(new Location(world10x10,-1,66,-4)));
	} catch (Exception e) {
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}


/*Añadimos varios Animal y Monster en posiciones donde no hay nada y comprobamos
 * que existen
	... MMM ggg
	... .P. ggg
	... LLL Mg.
*/
@Test
public void testAddCreature1() {
	Location loc;
	//Pone varios Monsters y comprueba
	try {
		for (int x=-1; x<2; x++) {
			loc = new Location(world10x10,x,65,-1);
			Creature creature = new Monster(loc,20.0);
			world10x10.addCreature(creature);
			assertEquals(creature, world10x10.getCreatureAt(loc));
		}
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
	
	//Pone Animals y comprueba
	try {
		for (int x=-1; x<2; x++) {
			loc = new Location(world10x10,x,65,1);
			Creature creature = new Animal(loc,20.0);
			world10x10.addCreature(creature);
			assertEquals(creature, world10x10.getCreatureAt(loc));
		}
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}

 /*Añadimos un Bloque de agua y Monster en la misma posición y comprobamos
  * que ambos deben de existir*/
@Test
public void testAddCreature2() {
	Location loc;
	try {
		//Añadimos un bloque de agua
		loc = new Location (world10x10,-1,66,-1); 
		world10x10.addBlock(loc, bl);
		//Añadimos un Monster en la misma posición y comprobamos
		Creature creature = new Monster(loc,20.0);
		world10x10.addCreature(creature);
		assertEquals(creature, world10x10.getCreatureAt(loc));
		assertEquals(bl, world10x10.getBlockAt(loc));
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}

/*Añadimos un Bloque de lava y Animal en la misma posición:*/
@Test
public void testAddCreature3() {
	Location loc;
	try {
		//Añadimos un bloque de agua
		loc = new Location (world10x10,-1,66,-1); 
		Block lava = BlockFactory.createBlock(Material.LAVA);
		world10x10.addBlock(loc, lava);
		//Añadimos un Monster en la misma posición y comprobamos
		Creature creature = new Animal(loc,20.0);
		world10x10.addCreature(creature);
		assertEquals(creature, world10x10.getCreatureAt(loc));
		assertEquals(lava, world10x10.getBlockAt(loc));
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}		
	
/*Añadimos un Monster en posición donde hay item. Comprobamos que
 * se ha puesto el monster y que el item ya no está
 * Location{world=World 10x10,x=-3.0,y=67.0,z=-2.0}=(APPLE,1) 
 */
@Test
public void testAddCreature4() {
	Location loc;
	try {
					
		loc = new Location(world10x10,-3.0,67.0,-2.0);
		assertNotNull(world10x10.getItemsAt(loc));
		
		Creature creature = new Monster(loc,20.0);
		world10x10.addCreature(creature);
		assertEquals(creature, world10x10.getCreatureAt(loc));
		assertNull(world10x10.getItemsAt(loc));
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}	

/*Intentamos añadir un Monster en posición donde está el player. Debe 
 * saltar la excepcion BadLocationException
 */
@Test(expected=BadLocationException.class)
public void testAddCreatureException1() throws BadLocationException {
	Location loc;
	//Intentamos añadir un Monster en la misma posición del player
	loc = new Location(world10x10,0,65.0,0);		
	Creature creature = new Monster(loc,20.0);
	world10x10.addCreature(creature);
}	
	
/*Intentamos añadir un Animal en posición donde hay un bloque sólido.
 * Debe saltar la excepcion BadLocationException 
 */
@Test(expected=BadLocationException.class)
public void testAddCreatureException2() throws BadLocationException {
	Location loc;
	//Intentamos añadir un Animal en la  posición donde hay un bloque GRASS
	loc = new Location(world10x10,0,64.0,0);		
	Creature creature = new Animal(loc,20.0);
	world10x10.addCreature(creature);
}	

/*Intentamos añadir un Animal en posición donde hay un Monster
 * Debe saltar la excepcion BadLocationException 
 * Monster [location=Location{world=World 10x10,x=-1.0,y=64.0,z=1.0}, health=2.0]
 */
@Test(expected=BadLocationException.class)
public void testAddCreatureException3() throws BadLocationException {
	Location loc;
	//Intentamos añadir un Animal en la  posición donde hay un Monster
	loc = new Location(world10x10,-1,64.0,1);		
	Creature creature = new Animal(loc,20.0);
	world10x10.addCreature(creature);
}	

/*Intentamos añadir un Animal en una posición que es null o no es de este mundo
 */
@Test(expected=BadLocationException.class)
public void testAddCreatureException4() throws BadLocationException {
	Location loc;
	World world = null;
	try {
		loc = new Location(world,1,67.0,1);		
		Creature creature = new Animal(loc,20.0);
		world10x10.addCreature(creature);
		fail("Error: Debió saltar la excepción BadLocationException");
	} catch ( BadLocationException e) {
			world = new World(2,20,"world", "Steve"); //De otro mundo
			loc = new Location(world,1,67.0,1);		
			Creature creature = new Animal(loc,20.0);
			world10x10.addCreature(creature);
	}
}	

//Intentamos leer un Monster de una posición que o es null o no es de este mundo
@Test(expected=BadLocationException.class)
public void testGetCreatureAtException() throws BadLocationException {
	Location loc;
	World world = null;
	try {
		loc = new Location(world,-1,64.0,1);		
		world10x10.getCreatureAt(loc);
		fail("Error: Debió saltar la excepción BadLocationException");
	} catch ( BadLocationException e) {
			world = new World(2,20,"world", "Steve"); //De otro mundo
			loc = new Location(world,1,67.0,1);		
			world10x10.getCreatureAt(loc);
	}
}

/* Movemos al player a la posición límite (5,65,0) lo orientamos hacia el este 
 * e intentamos añadir un animal en esa posición (6,65,0). Debe saltar la excepción
 * BadLocationException
   */
@Test(expected=BadLocationException.class)
public void testAddCreatureOutOfWorldException() throws BadLocationException, EntityIsDeadException {
		Player player = world10x10.getPlayer();
		Location loc = player.getLocation();
		for (int i=0; i<4; i++) 
		    	player.move(1, 0, 0);
		//Saltamos un bloque de tipo GRASS
		player.move(1, 1, 0);
		player.orientate(1, 0, 0);
		loc = player.getOrientation();
		world10x10.addCreature(new Animal(loc,20));
}	


/* Pone un item en una posición libre y se comprueba que la ha puesto */
@Test
public void testAddItems1() {
	Location loc = new Location(world10x10,1,65.0,0);
	ItemStack is;
	try {
		is = new ItemStack(Material.APPLE,3);
		world10x10.addItems(loc,is );
		assertNotNull(world10x10.getItemsAt(loc));
		assertEquals(is,world10x10.getItemsAt(loc));
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}

/* Pone un item en una posición ocupada por otro item y se comprueba 
 * la ha puesto en sustitución de la que había 
 * Location{world=World 10x10,x=4.0,y=63.0,z=4.0}=(IRON_SWORD,1)
 */
@Test
public void testAddItems2() {
	Location loc = new Location(world10x10,4,63.0,4);
	ItemStack newIs, oldIs;
	try {
		oldIs = world10x10.getItemsAt(loc);
		assertNotNull(oldIs);
		newIs = new ItemStack(Material.APPLE,3);
		assertNotEquals(oldIs,newIs);
		
		world10x10.addItems(loc,newIs );
		assertNotNull(world10x10.getItemsAt(loc));
		assertEquals(newIs,world10x10.getItemsAt(loc));
		
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}

/* Pone un item en una posición ocupada primero por un bloque y luego por un Monster
 * y se comprueba que salta la excepción BadLocationException en ambos casos */
@Test(expected=BadLocationException.class)
public void testAddItemsException1() throws BadLocationException {
	Location loc = new Location(world10x10,0,64.0,0); //Hay un bloque GRASS
	ItemStack is=null;
	try {
		is = new ItemStack(Material.APPLE,3);
		world10x10.addItems(loc,is);
		fail("Error: debió saltar la excepción BadLocationException");
	} catch (BadLocationException e) {
			world10x10.addItems(new Location(world10x10,-1,64,1),is);

	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}

//Intentamos leer un item de una posición que o es null o no es de este mundo
@Test(expected=BadLocationException.class)
public void testGetItemAtException2() throws BadLocationException {
	Location loc;
	World world = null;
	try {
		loc = new Location(world,0,63.0,4);		
		world10x10.getItemsAt(loc);
		fail("Error: Debió saltar la excepción BadLocationException");
	} catch ( BadLocationException e) {
			world = new World(2,20,"world", "Steve"); //De otro mundo
			loc = new Location(world,0,63.0,4);		
				world10x10.getItemsAt(loc);
	}
}

/* Movemos al player a la posición límite (0,255,0) lo orientamos hacia arriba 
 * e intentamos añadir un animal en esa posición (0,256,0). Debe saltar la excepción
 * BadLocationException
 */
@Test (expected=BadLocationException.class)
public void testAddItemOutOfWorldException() throws BadLocationException, EntityIsDeadException   {
	Player player = world10x10.getPlayer();
	for (int i=66; i<=255; i++)
		player.move(0, 1, 0);
	player.orientate(0, 1, 0);
	Location loc = player.getOrientation();
	try {
		world10x10.addItems(loc,new ItemStack(Material.IRON_PICKAXE,1));
	} catch (StackSizeException e) {
		fail("Error: no debió lanzar StackSizeException");
	}
}	
		
/* Destruye un bloque GRASS en la posición (0,64,0). Este bloque no tiene
	items. Es el bloque más alto (sobre él está el player). Comprobamos que
	después de destruirlo la altura máxima es de 63 */
@Test
public void testDestroyBlockAt1() {
	try {
		Location loc = new Location(world10x10,0,64,0);
		assertEquals(64.0,world10x10.getHighestLocationAt(loc).getY(),0.001);
		world10x10.destroyBlockAt(loc);
		assertEquals(63.0,world10x10.getHighestLocationAt(loc).getY(),0.001);
		assertNull(world10x10.getBlockAt(loc));
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}

/* Creamos un bloque CHEST y le asignamos  el item (Material.BREAD, 5). Ponemos el bloque 
 * en la posición (0,66,0). Pasa a ser el bloque más alto (debajo de él está el player). 
Comprobamos que	después de destruirlo la altura máxima es de 64 y que en 
la posición 66 están los items que contenía el bloque */
@Test
public void testDestroyBlockAt2() {
	try {
		Location loc = new Location(world10x10,0,66,0);
		//Construimos el bloque CHEST con drops (BREAD,5)
		SolidBlock block = new SolidBlock(Material.CHEST);
		ItemStack bread = new ItemStack(Material.BREAD, 5);
		block.setDrops(Material.BREAD, 5);
		assertEquals(64.0,world10x10.getHighestLocationAt(loc).getY(),0.001);
		//Lo ponemos en el mundo, en la posición (0,66,0)
		world10x10.addBlock(loc, block);
		assertEquals(66.0,world10x10.getHighestLocationAt(loc).getY(),0.001);
		//Lo destruimos y comprobamos altura máxima y que no está
		world10x10.destroyBlockAt(loc);
		assertNull(world10x10.getBlockAt(loc));
		assertEquals(64.0,world10x10.getHighestLocationAt(loc).getY(),0.001);
		//Pero deben estar los items que ha dejado.
		assertEquals(bread, world10x10.getItemsAt(loc));
		
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}
	
	
/* Intentamos destruir un bloque en la posición (0,0,0) */
@Test(expected=BadLocationException.class)
public void testDestroyBlockAtException1() throws BadLocationException {
	   /* Aunque el suelo del mundo (y=0) está lleno de bloques BEDROCK
	    * Si intentamos destruirlo debe saltar la excepción BadLocationException
	    */	   
		Location loc = new Location(world10x10,0,0,0);
		world10x10.destroyBlockAt(loc);
}

/* Intentamos destruir un bloque en una posición que o es null o no pertenece a este mundo */
@Test(expected=BadLocationException.class)
public void testDestroyBlockAtException2() throws BadLocationException {
	    World world = null;
	    Location loc = new Location(world,0,0,0);
	    try {
	    	world10x10.destroyBlockAt(loc);
	    	fail("Error: Debió saltar la excepción BadLocationException");
	    } catch (BadLocationException e) {
	    	world = new World(1,3, "Venus", "Steve");	   	   
	    	 loc = new Location(world,0,0,0);
	    	 world10x10.destroyBlockAt(loc);
	    }
}
	//No hay criaturas adyacentes a la posición (0,1,0)
@Test
public void testGetNearbyCreatures1() {
	try {
		Collection<Creature> creatures = world10x10.getNearbyCreatures(new Location(world10x10, 0,1,0));
		assertTrue(creatures.isEmpty());
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}

/* Hay 1 criatura adyacente a la posición inicial del player y es un Monster
 * Comprueba con getNearbyCreatures que es así.
  ... ... ggg
  ... .P. ggg
  ... ... Mg.
 */
@Test
public void testGetNearbyCreatures2() {
	try {
		Collection<Creature> creatures = world10x10.getNearbyCreatures(new Location(world10x10, 0,65,0));
		assertTrue(creatures.size()==1);
	Creature monster = world10x10.getCreatureAt(new Location(world10x10, -1,64,1));
		assertTrue(creatures.contains(monster));
	} catch (Exception e) {
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}
	
/* Ponemos criaturas (3 animals) adyacentes a la posición inicial del player.
 * Comprobamos con getNearbyCreatures que tenemos 4 criaturas adyacentes.
 * >L. ... ggg 
   >L. .P. ggg
   >L. ... Mg.
 */
@Test
public void testGetNearbyCreatures3() {
	try {
		addItemsAndCreatures(new ItemStack(Material.IRON_SHOVEL, 1),'A');
		Collection<Creature> creatures = world10x10.getNearbyCreatures(new Location(world10x10, 0,65,0));
		assertTrue(creatures.size()==4);
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}
	
/* La posición  o es null o no pertenece a este mundo */
@Test(expected=BadLocationException.class)
public void testGetNearbyCreaturesException() throws BadLocationException {
	    World world = null;
	    Location loc = new Location(world,0,65,0);
	    try {
	    	world10x10.getNearbyCreatures(loc);
	    	fail("Error: Debió saltar la excepción BadLocationException");
	    } catch (BadLocationException e) {
	    	world = new World(1,3, "Venus", "Steve");	   	   
	    	 loc = new Location(world,0,65,0);
	    	 world10x10.getNearbyCreatures(loc);
	    }
}	

/* Elimina la única criatura adyacente respecto a la posición del player. Se comprueba que
 * se ha eliminado */
@Test
public void testKillCreature1() {
	try {
		Location loc = new Location(world10x10, -1,64,1);
		world10x10.killCreature(loc);
		assertNull (world10x10.getCreatureAt(loc));
	} catch (Exception e) {
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}
	
/* Añadimos 3 creatures adyacentes a la posición del player y los eliminamos. 
 * Vamos comprobando que se van eliminando
 * >M. ... ggg 
   >M. .P. ggg
   >M. ... Mg.
 */
@Test
public void testKillCreature2() {
	try {
		Location loc;
		addItemsAndCreatures(new ItemStack(Material.IRON_SHOVEL, 1),'M');
	    for (int z=-1; z<2; z++ ) {
			loc = new Location (world10x10,0,66,z);
			assertNotNull(world10x10.getCreatureAt(loc));
			world10x10.killCreature(loc);
			assertNull(world10x10.getCreatureAt(loc));
	    } 
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}


/* Intenta eliminar una criatura en una posición que donde no hay criatura, o la posición es null 
 * o no pertenece a este mundo */
@Test(expected=BadLocationException.class)
public void testKillCreatureException() throws BadLocationException  {
	    World world = null;
	    Location loc = new Location(world,-1,64,1);
	    try {
	    	world10x10.killCreature(loc);
	    	fail("Error: Debió saltar la excepción BadLocationException");
	    } catch (BadLocationException e) {
	    	world = new World(1,3, "Venus", "Steve");	   	   
	    	loc = new Location(world,1,64,1);
	    	try {
	    	  world10x10.killCreature(loc);
	    	  fail("Error: Debió saltar la excepción BadLocationException");
	    	} catch (BadLocationException e1) {
	    		loc = new Location(world10x10,0,65,0); //está el player
	    		 world10x10.killCreature(loc);	
	    	}
	    }
}	

        /******************************************************************/
        //FUNCION DE APOYO
        /* Se usa para comparar Strings que devuelven algunos métodos. Evita sacar error por los espacios
         * finales de cada línea y por los saltos de línea al final.
         * Los valores con decimales son comparados con un margen de error de 0.01
         */
        void compareLines(String expected, String result) {
                String exp[]=expected.split("\n");
                String res[]=result.split("\n");
                boolean iguales = true;
                for (int i=0; i<exp.length && iguales; i++) {

                        // si la línea es Health=1.2345  o Food level=1.2345  
                        // se extraen los números y se comparan con 0.01 de margen
                        String ee[]=exp[i].split("=");
                        if (ee[0].equals("Health") || ee[0].equals("Food level")) {
                                String rr[]=res[i].trim().split("=");
                                if (ee[0].equals(rr[0])) {
                                        double ed = Double.parseDouble(ee[1]);
                                        double rd = Double.parseDouble(rr[1]);

                                        assertEquals(ee[0],ed,rd,0.01);
                                } else
                                    fail("linea "+i+": \""+ee[0]+"\" != \""+rr[0]+"\"");
                        }
                        else
                                assertEquals("linea "+i, exp[i],res[i].trim());
                }
        }

	
/* Añade bloques líquidos (WATER Y LAVA) en world10x10
 *  
   @#. ... ggg\n 
   @#. .P. ggg\n 
   @#. ... Mg.
*/	
	void addLiquidBlocks() {
		Location loc;
	   //Añadimos agua
	   try {
		for (int z=-1; z<2; z++ ) {
			loc = new Location (world10x10,-1,66,z);
			world10x10.addBlock(loc, new LiquidBlock(Material.WATER));
		}
	   //Añadimos lava
	    for (int z=-1; z<2; z++ ) {
	  	 loc = new Location (world10x10,0,66,z);
		 world10x10.addBlock(loc, new LiquidBlock(Material.LAVA));
	    }
	   } catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	   }
	}
	
	/* Añade Items y Creatures (Monster/Animal) en las mismas posiciones en que se añaden bloques
	 * de agua y lava en addLiquidblocks()
	 */
	void addItemsAndCreatures(ItemStack is, char cr) {
		
		Location loc;
		   //Añadimos Items 
		   try {
			for (int z=-1; z<2; z++ ) {
				loc = new Location (world10x10,-1,66,z);
				world10x10.addItems(loc, new ItemStack(is));
			}
		   //Añadimos Monsters o Animals
			Creature creature;
		    for (int z=-1; z<2; z++ ) {
		  	  loc = new Location (world10x10,0,66,z);
		  	  creature = ((cr=='M') ?  new Monster(loc,15) :  new Animal(loc,20));
		  	  world10x10.addCreature(creature);
		    }
		   } catch (Exception e) {
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
		   }
	}
	
}
