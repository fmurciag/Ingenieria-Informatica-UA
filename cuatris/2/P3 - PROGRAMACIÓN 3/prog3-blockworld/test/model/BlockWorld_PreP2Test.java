package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.exceptions.BadInventoryPositionException;
import model.exceptions.BadLocationException;
import model.exceptions.EntityIsDeadException;

public class BlockWorld_PreP2Test {

	
	final static String STARTPLAY1 ="Name=Steve\n" + 
			"Location{world=World 5x5,x=0.0,y=66.0,z=0.0}\n" + 
			"Health=20.0\n" + 
			"Food level=20.0\n" + 
			"Inventory=(inHand=(WOOD_SWORD,1),[])\n" + 
			".>. ggg ddd\n" + 
			"... gPW dgg\n" + 
			"... ... g..";
	final static String HALFPLAY1 ="Name=Steve\n" + 
			"Location{world=World 5x5,x=0.0,y=67.0,z=-1.0}\n" + 
			"Health=2.8500000000000014\n" + 
			"Food level=0.0\n" + 
			"Inventory=(inHand=(IRON_SHOVEL,1),[(WATER_BUCKET,1), (WOOD_SWORD,1)])\n" + 
			"... ... gg.\n" + 
			"... .P. ggg\n" + 
			"... ... g..";
	final static String ENDPLAY1 ="Name=Steve\n" + 
			"Location{world=World 5x5,x=0.0,y=67.0,z=-1.0}\n" + 
			"Health=-0.14999999999999858\n" + 
			"Food level=0.0\n" + 
			"Inventory=(inHand=(IRON_SHOVEL,1),[(WOOD_SWORD,1)])\n" + 
			"... ... gg.\n" + 
			"... .P. ggg\n" + 
			"... ... g..";
	
	final static String STARTPLAY2 ="Name=Steve\n" + 
			"Location{world=World 3x3,x=0.0,y=63.0,z=0.0}\n" + 
			"Health=20.0\n" + 
			"Food level=20.0\n" + 
			"Inventory=(inHand=(WOOD_SWORD,1),[])\n" + 
			"... I.. gg.\n" + 
			"... .P. gg.\n" + 
			"... W.. g..";
	final static String HALFPLAY2 ="Name=Steve\n" + 
			"Location{world=World 3x3,x=0.0,y=61.0,z=1.0}\n" + 
			"Health=20.0\n" + 
			"Food level=9.749999999999996\n" + 
			"Inventory=(inHand=(WOOD_SWORD,1),[(IRON_SWORD,1), (WATER_BUCKET,3)])\n" + 
			"gg. dd. dd.\n" + 
			"g.. dP. dA.\n" + 
			"XXX XXX XXX";
	final static String ENDPLAY2 ="Name=Steve\n" + 
			"Location{world=World 3x3,x=0.0,y=60.0,z=1.0}\n" + 
			"Health=-0.30000000000000426\n" + 
			"Food level=0.0\n" + 
			"Inventory=(inHand=(WOOD_SWORD,1),[(IRON_SWORD,1)])\n" + 
			"dd. dd. dd.\n" + 
			"d.. dP. dgg\n" + 
			"XXX XXX XXX";
	
	BlockWorld bw;
	World world5x5;
	World world3x3;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Material.rng.setSeed(1L);
		bw = BlockWorld.getInstance();
		world3x3 =  bw.createWorld(1, 3, "World 3x3");
	//	world5x5 =  bw.createWorld(4, 5, "World 5x5");
	}

	@Test
	public void testGetInstance() {
		BlockWorld bw=BlockWorld.getInstance();
		assertNotNull(bw);
		assertSame (bw, BlockWorld.getInstance());
	}

	@Test
	public void testCreateWorld() {
		BlockWorld bw=BlockWorld.getInstance();
		World world = bw.createWorld(1, 3, "World 3x3");
		assertTrue("world == world3x3",world3x3.equals(world));
	}

	@Test
	public void testShowPlayerInfo() {
		compareLines(STARTPLAY2,bw.showPlayerInfo(world3x3.getPlayer()));
		//System.out.println(bw.showPlayerInfo(world3x3.getPlayer()));
	}
	
	//TODO
	/* Completa la partida */
	@Test
	public void testPlay1() {
		/* INICIO PARTIDA
		 Location{world=World 5x5,x=0.0,y=66.0,z=0.0}
		.>. ggg ddd
		... gPW dgg
		... ... g.. 
		*/
		Material.rng.setSeed(1L);
		world5x5 =  bw.createWorld(5, 5, "World 5x5");
		bw.showPlayerInfo(world5x5.getPlayer());
		Player ply = world5x5.getPlayer();
		//try {
			compareLines(STARTPLAY1,bw.showPlayerInfo(world5x5.getPlayer()));
			//Usa el WOOD_SWORD de inHand, 150 veces. Reduce 15 puntos el foodLevel
			try {
				bw.useItem(ply, 150);
			} catch (EntityIsDeadException e1) {
				fail("Excepción "+e1.getClass().getName()+" no esperada");
			}
			assertEquals(5.0,ply.getFoodLevel(),0.001);
			assertEquals(0, ply.getInventorySize()); 
			//Intenta mover hacia un bloque GRASS
			try {
			  
				bw.movePlayer(ply, -1,0,0);
				fail("Debió saltar la excepción BadLocationException");
			} catch (EntityIsDeadException e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			} catch (BadLocationException e) {
				 assertEquals(5.0,ply.getFoodLevel(),0.001);
		
				 //TODO CONTINUA LA PARTIDA realizando las comprobaciones necesarias.
				 // Debes comprobar todo aquello que cambia al realizar alguna acción:
				 // posicíon del jugador, nivel de alimento, nivel de salud, tamaño del inventario, item en la mano, etc...
				 
				 //Recoge Item WATER_BUCKET
				 
				 // movePlayer() + asserts ...
				 
				 //Se mueve a la posición adyacente con mismo x,z del plano superior
			     //Se mueve a la posición adyacente para coger el IRON_SHOVEL

				 // movePlayer() + asserts ...

			     //Seleccionamos (IRON_SHOVEL,1) y lo usamos 220 veces.

				 //TODO selectItem(), useItem() + asserts...
				 
				 // Hasta aquí llegamos a la situación descrita en la cadena HALFPLAY1
				 // System.out.println(bw.showPlayerInfo(world5x5.getPlayer()));
			     compareLines(HALFPLAY1,bw.showPlayerInfo(world5x5.getPlayer()));
			  
			     //Seleccionamos (WATER_BUCKET,1) y lo usamos 20 veces

				 //TODO selectItem(), useItem() + asserts...

			     //Cogemos (IRON_SHOVEL,1) y lo usamos 40 veces. Player muere
			     
				 //TODO selectItem(), useItem() + asserts...

			     // FIN de la partida, situación descrita en la cadena ENDPLAY1
			     //System.out.println(bw.showPlayerInfo(world5x5.getPlayer()));
			     compareLines(ENDPLAY1,bw.showPlayerInfo(world5x5.getPlayer()));
			  } 
	}

	
	/******************************************************************/
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

