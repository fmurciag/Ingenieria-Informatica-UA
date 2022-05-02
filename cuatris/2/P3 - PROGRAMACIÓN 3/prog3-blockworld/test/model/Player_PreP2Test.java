package model;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import org.junit.Before;
import org.junit.Test;

import model.exceptions.BadInventoryPositionException;
import model.exceptions.BadLocationException;
import model.exceptions.EntityIsDeadException;
import model.exceptions.StackSizeException;

public class Player_PreP2Test {
	final String outInitialPlayer = "Name=Peter\n"+
			"Location{world=Mercury,x=0.0,y=71.0,z=0.0}\n"+
			"Health=20.0\n"+
			"Food level=20.0\n"+
			"Inventory=(inHand=(WOOD_SWORD,1),[])\n";
	final String outPlayer = "Name=Peter\n"+
			"Location{world=Mercury,x=1.0,y=71.0,z=1.0}\n"+
	"Health=10.0\n"+
	"Food level=3.95\n"+
	"Inventory=(inHand=(BREAD,5),[(BEDROCK,5), (CHEST,5), (SAND,5), (DIRT,5), "+
	"(GRASS,5), (STONE,5), (GRANITE,5), (OBSIDIAN,5), (WATER_BUCKET,5), (APPLE,5), "+
	"(WOOD_SWORD,1), (BEEF,5), (IRON_SHOVEL,1), (IRON_PICKAXE,1), (WOOD_SWORD,1), (IRON_SWORD,1)])";

	Player player, playerNull;
	static World mercury;
	Location locPlayer, newpos;
	Material materias[]=Inventory_PreP2Test.materias;
	static Field inventory;
	static Object jugador;
	
	/**
	 * @throws java.lang.Exception
	 */

	@Before
	public void setUp() throws Exception {
		Material.rng.setSeed(1L);
		mercury = new World(3,2,"Mercury");
		player = new Player("Peter",mercury);
		locPlayer = new Location(mercury, 0,71,0);
		newpos = null;
	}

	@Test
	public void testGettersPlayer() {
		assertEquals("getName()", "Peter",player.getName());
		Location locPlayer = new Location(mercury, 0,71,0);
		assertEquals("getLocation()", locPlayer ,player.getLocation());
		assertEquals("getHealth()", Player.MAX_HEALTH, player.getHealth(),0.01);
		assertEquals("getFoodLevel()", Player.MAX_FOODLEVEL, player.getFoodLevel(),0.01);
		assertEquals("getInventorySize()", 0, player.getInventorySize());
	}
	
	@Test
	public void testSetHealth() {
		player.setHealth(5.5);
		assertEquals("Health = 5.5", 5.5, player.getHealth(),0.01);
		player.setHealth(-10.01);
		assertEquals("Health = -10.01", -10.01, player.getHealth(),0.01);
		player.setHealth(Player.MAX_HEALTH-0.01);
		assertEquals("Health = 19.99", 19.99, player.getHealth(),0.01);
		
	}
	
	//SetHealth para valores fuera del límite
	@Test
	public void testSetHealthOutOfLimit() {
		player.setHealth(5.0);
		player.setHealth(100.01);
		assertEquals("Health = 20", Player.MAX_HEALTH, player.getHealth(),0.01);
		player.setHealth(20.1);
		assertEquals("Health = 20", Player.MAX_HEALTH, player.getHealth(),0.01);
	}

	//TODO
	//SetFood para valores dentro del límite
	@Test
	public void testSetFoodLevel() {
		fail("Comprueba SetFoodLevel() para valores dentro del límite");
	}

	//TODO
	//SetFoodLevel para valores fuera del límite
	@Test
	public void testSetFoodLevelOutOfLimit() {
		fail("Comprueba SetFoodLevel() para valores fuera del límite");
	}
		
	//Testamos isDead()
	@Test
	public void testIsDead() {
		//Inicialmente no está muerto
		assertFalse(player.isDead());
		
		//No es foodLevel quien decide si está muerto
		player.setFoodLevel(-10);
		assertFalse(player.isDead());
		
		//Está al límite
		player.setHealth(0.001);
		assertFalse(player.isDead());
		
		//Lo matamos
		player.setHealth(0.0);
		assertTrue(player.isDead());
		player.setHealth(-0.001);
		assertTrue(player.isDead());
	}

	//Movimientos posibles controlando nuevas posiciones y nuevos valores food/health
	
	//Se mueve player decrementado solo foodLevel
	@Test
	public void testMoveOk1() {
		
		try {
			
			Location loc = player.getLocation();
			newpos = player.move(0, 1, 0);
			assertEquals("Nueva posición",newpos, player.getLocation());
			assertNotSame("Composición Player - Location",loc,player.getLocation());
			assertEquals("Decrementa food en 0.05", 19.95, player.getFoodLevel(),0.01);
			assertEquals("Health sigue a 20", 20.0, player.getHealth(),0.01);
			
			newpos=player.move(0, 1, 1);
			assertEquals("Nueva posición",newpos, player.getLocation());
			assertEquals("Decrementa food en 0.05", 19.90, player.getFoodLevel(),0.01);
			assertEquals("Health sigue a 20", 20.0, player.getHealth(),0.01);
						
		} catch (Exception e) {
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
		}
	}
	
	//TODO
	//Vamos a ajustar foodLevel y mover el player para comprobar si baja en health
	@Test
	public void testMoveOk2() {
		fail("Ajusta el nivel de alimento del jugador a 0.05 y muévelo tres veces,\ncomprobando que cuando su nivel de alimento es cero se decrementa el nivel de salud.");
	}
	
	
	//Movimiento a una posición no adyacente
	@Test(expected=BadLocationException.class)
	public void testMoveError1() throws BadLocationException, EntityIsDeadException{
			
		//Movemos a posición no adyacente
		newpos = player.move(0, 73, 0);
	}

	//TODO
	//Movimiento de un Player muerto
	@Test(expected=EntityIsDeadException.class)
	public void testMoveError2() throws BadLocationException, EntityIsDeadException {
		fail("Comprueba que no se puede mover a un jugador muerto (se lanza EntityIsDeadException)");
	}
		
	//Movimiento a una posición ocupada por un bloque
	@Test(expected=BadLocationException.class)
	public void testMoveError3() throws BadLocationException, EntityIsDeadException {
			newpos=player.move(0, -1, 0); //En esa posición hay un bloque
	}
		
	//TODO
	//Movimiento a una posición fuera de los límtes del mundo
	@Test(expected=BadLocationException.class)
	public void testMoveError4() throws BadLocationException, EntityIsDeadException {
		fail("Comprueba que se lanza BadLocationException al intentar mover al jugador fuera de los límites del mundo.");
	}
 
	//TODO
	//Pruebas 1 con useItemInHand
	@Test
	public void testUseItemInHand1() {
		try {
			ItemStack food = new ItemStack(Material.BEEF,5);
			ItemStack tool = new ItemStack(Material.IRON_PICKAXE,1);
			ItemStack block = new ItemStack(Material.GRANITE, 3);
			player.addItemsToInventory(food);	
			player.addItemsToInventory(tool);	
			player.addItemsToInventory(block);	
			assertEquals(20.0,player.getFoodLevel(),0.001);
			assertEquals(20.0,player.getHealth(),0.001);
			
			//usamos WOOD_SWORD 210 veces y vemos que foodLevel queda a 0 y health a 19
			player.useItemInHand(210);
			assertEquals(0.0,player.getFoodLevel(),0.001);
			assertEquals(19.0,player.getHealth(),0.001);
			
			//Ponemos health a 1 y usamos (BEEF,5) 3 veces. Vemos como satura foodLevel en 20 
			// e incrementa la salud en 4
			fail("Pon health a 1 y usa (BEEF,5) 3 veces. Vemos como satura foodLevel en 20 e incrementa la salud en 4");
			
			//Seleccionamos material de granito y lo usamos 50 veces.
			fail("Seleccionamos material de granito y lo usamos 50 veces. El nivel de alimento queda en 15.0 y el de salud en 5.0 (si has hecho la prueba anterior)");

		} catch (Exception e) {
			fail("Error, se produjo la excepción: "+e.getClass().getName());
		}
		
		
	}
	
	
	//Pruebas de excepciones IllegalArgumentException 
		@Test
		public void testUseItemInHandExceptions1() {
			try {
				//Excepción por uso de -1 vez el WOOD_SWORD
				player.useItemInHand(-1);
				fail("Error, no se produjo la excepción IllegalArgumentException");
			} catch (IllegalArgumentException e1) {
				assertEquals(20.0,player.getFoodLevel(),0.001);
				assertEquals(20.0,player.getHealth(),0.001);
			} catch (Exception e) {
				fail("Error, se produjo la excepción: "+e.getClass().getName());
			}
		}
		
		//TODO
		//Pruebas de excepciones EntityIsDeadException
		@Test
		public void testUseItemInHandExceptions2() {
			fail("Comprueba que se lanza la excepción EntityIsDeadException cuando se intenta usar un item y el jugador está muerto.");
		}
		
	
		//TODO
	/* Hay un inventario con 1 (APPLE,5). Lo seleccionamos y lo usamos todo.
	 * Ahora en la mano no tiene nada y se selecciona el elemento 0 del inventario en la mano.
	 * Inventario queda con tamaño 0
	 */
	@Test
	public void testSelectItem() {
		fail("¡Implementa este test!");
	}
	
	// selecItem produce Exception BadInventoryPositionException 
	@Test
	public void testSelectItemException() {
	 try {
		try {
		  player.selectItem(0);
		  fail("Error, no se produjo la excepción BadInventoryPositionException");
		} catch (BadInventoryPositionException e1) {
		   ItemStack food = new ItemStack(Material.APPLE,5);
		   player.addItemsToInventory(food);
		   try {
			   player.selectItem(1);
			   fail("Error, no se produjo la excepción BadInventoryPositionException");
		   } catch (BadInventoryPositionException e2) {
			   assertEquals(1,player.getInventorySize());
		   }
		}
	 } catch (Exception e) {
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	 }	
	}


	
	//Player creado, sin elementos en el inventario.
	@Test
	public void testToStringInitialPlayer() {
		compareLines(outInitialPlayer, player.toString());
	}

	//TODO
	//Player con todos los atributos de player modificados. Algunos métodos deben
	//funcionar ya correctamente.
	@Test
	public void testToStringPlayer() {
		try {
		fail("Ajusta las propiedades del player para que al invocar al método toString() de como resultado la cadena 'outPlayer' definida arriba\n y la llamada a la función 'compareLines' no falle.");
		  compareLines(outPlayer, player.toString());
		} catch (Exception e){
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
		}
	}
	
	//Probamos todas las posibilidades de equals con los atributos que intervienen
	@Test
	public void testEqualsObject() {
		Player player2 = new Player("Peter",mercury);
		assertFalse(player.equals(null));
		assertTrue(player.equals(player));
		assertFalse(player.equals(mercury));
		assertEquals(player,player2);
		//Modificamos algún atributo:
		//name
		Player player3 = new Player("John",mercury);
		assertNotEquals(player2, player3);
		
		//foodLevel
		player2.setFoodLevel(Player.MAX_FOODLEVEL-1);
		assertNotEquals(player, player2);
		
		//health
		player2 = new Player("Peter",mercury);
		player2.setHealth(Player.MAX_HEALTH-1);
		assertNotEquals(player, player2);
		
		//location
		player2 = new Player("Peter",mercury);
		try {
			player2.move(1, 0, 1);
		
		} catch (Exception e) {
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
		}
		assertNotEquals(player, player2);
		
		//inventory iguales
		player2 = new Player("Peter",mercury);
		ItemStack is=null;
		try {
			is = new ItemStack(Material.APPLE,5);
			player2.addItemsToInventory(is);
			player.addItemsToInventory(new ItemStack(is));
			assertEquals(player, player2);
		} catch (Exception e) {
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
		}
		//Inventory distintos	
		try {
			player.addItemsToInventory(new ItemStack(is));
			assertNotEquals(player, player2);
		} catch (Exception e) {
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
		}		
	}
	
	/* Testamos hasCode() con los atributos que intervienen, comprobando que si modificamos alguno
	 * los codes son distintos */
	@Test
	public void testHashCode() {
		Player player2 = new Player("Peter",mercury);
		assertEquals("Codes iguales para players iguales",player.hashCode(),player2.hashCode());
		//Modificamos algún atributo:
		//name
		Player player3 = new Player("John",mercury);
		assertNotEquals("Codes distintos para players con name distintos", player3.hashCode(),player2.hashCode());
		
		//foodLevel
		player2.setFoodLevel(Player.MAX_FOODLEVEL-1);
		assertNotEquals("Codes distintos para players con foodLevel distintos",player.hashCode(),player2.hashCode());
		
		//health
		player2 = new Player("Peter",mercury);
		player2.setHealth(Player.MAX_HEALTH-1);
		assertNotEquals("Codes distintos para players con health distintos",player.hashCode(),player2.hashCode());
		
		//location
		player2 = new Player("Peter",mercury);
		try {
			player2.move(1, 0, 1);
		
		} catch (Exception e) {
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
		}
		assertNotEquals("Codes distintos para players con location distintos",player.hashCode(),player2.hashCode());
		
		//inventory iguales
		player2 = new Player("Peter",mercury);
		ItemStack is=null;
		try {
			is = new ItemStack(Material.APPLE,5);
			player2.addItemsToInventory(is);
			player.addItemsToInventory(new ItemStack(is));
			assertEquals("Codes iguales para players con inventory iguales",player.hashCode(),player2.hashCode());
		} catch (Exception e) {
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
		}
		//Inventory distintos	
		try {
			player.addItemsToInventory(new ItemStack(is));
			assertNotEquals("Codes distintos para players con inventory distintos",player.hashCode(),player2.hashCode());
		} catch (Exception e) {
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
		}		
	}
	
	/***********************************************************/
	//FUNCION DE APOYO
	/* Se usa para comparar Strings que devuelven algunos métodos. Evita sacar error 
	 * por los espacios finales de cada línea y por los saltos de línea al final
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
