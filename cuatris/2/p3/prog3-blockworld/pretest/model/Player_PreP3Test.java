package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import model.Inventory_P2Test;
import model.ItemStack;
import model.Location;
import model.Material;
import model.World;
import model.entities.Player;
import model.exceptions.BadLocationException;
import model.exceptions.EntityIsDeadException;

public class Player_PreP3Test {
	final String outInitialPlayer = "Name=Peter\n"+
			"Location{world=Mercury,x=0.0,y=71.0,z=0.0}\n"+
			"Orientation=Location{world=Mercury,x=0.0,y=0.0,z=1.0}\n"+
			"Health=20.0\n"+
			"Food level=20.0\n"+
			"Inventory=(inHand=(WOOD_SWORD,1),[])\n";
	final String outPlayer = "Name=Peter\n"+
			"Location{world=Mercury,x=1.0,y=71.0,z=1.0}\n"+ 
	"Orientation=Location{world=Mercury,x=1.0,y=-1.0,z=1.0}\n"+
	"Health=10.0\n"+
	"Food level=3.95\n"+
	"Inventory=(inHand=(BREAD,5),[(BEDROCK,5), (CHEST,5), (SAND,5), (DIRT,5), "+
	"(GRASS,5), (STONE,5), (GRANITE,5), (OBSIDIAN,5), (WATER_BUCKET,5), (APPLE,5), "+
	"(WOOD_SWORD,1), (BEEF,5), (IRON_SHOVEL,1), (IRON_PICKAXE,1), (WOOD_SWORD,1), (IRON_SWORD,1), "
	+ "(LAVA,5), (WATER,5)])";

	Player player, playerNull;
	static World mercury;
	Location orientationPlayer, newpos;
	Material materias[]=Inventory_P2Test.materias;

	
	/**
	 * @throws java.lang.Exception
	 */

	@Before
	public void setUp() throws Exception {
		Material.rng.setSeed(1L);
		mercury = new World(3,2,"Mercury");
		player = new Player("Peter",mercury);
		orientationPlayer = new Location(mercury, 0,71,1);
		newpos = null;
	}

	/*Orientación absoluta de getOrientation de player a partir de la localización de
	player en (0,71,0) 
	Comprueba la composición entre Player y Location*/
	//TODO
	@Test
	public void testGetOrientation() {
		
		assertEquals("getOrientation()", orientationPlayer ,player.getOrientation());
		//TODO
		
		
		
	
	}
	
	public void testGetSymbol() {
		assertTrue(player.getSymbol()=='P');
	}

	/* Pruebas  con useItemInHand comprobando que devuelve el item que lleva en la
		mano o null si no lleva nada.
		Añade food, tool y block al player. Usalos comprobando que devuelve
		el item que lleva en la mano. */
	//TODO
	@Test
	public void testUseItemInHand() {
		try {
			ItemStack food = new ItemStack(Material.BEEF,5);
			ItemStack tool = new ItemStack(Material.IRON_PICKAXE,1);
			ItemStack block = new ItemStack(Material.GRANITE, 3);
		
			player.addItemsToInventory(food);
            player.addItemsToInventory(tool);
            player.addItemsToInventory(block);
            player.selectItem(0);
            assertEquals(null, player.useItemInHand(5));
            player.selectItem(1);
            assertEquals(tool, player.useItemInHand(2));
			
		} catch (Exception e) {
			fail("Error, se produjo la excepción: "+e.getClass().getName());
		}
	}

	/*Test orientate y getOrientate para todas las posibles orientaciones de un
	player  */
	//TODO
	@Test
	public void testOrientate() {
		Location loc, orient;
		for (int dx=-1; dx<2; dx++) 
			for (int dy=-1; dy<2; dy++)
				for (int dz=-1; dz<2; dz++) {
					//TODO
					if(!(dx==0&&0==dz&&dy==0)) {
						try {
							orient=player.orientate(dx, dy, dz);
							loc=new Location(mercury, player.getLocation().getX()+dx, player.getLocation().getY()+dy, player.getLocation().getZ()+dz);
							assertEquals(loc,orient );
						} catch (EntityIsDeadException | BadLocationException e) {
							fail("Error, se produjo la excepción: "+e.getClass().getName());
						
						}
					}
					
					
				}
	}
	
	
	
	
	/*Test orientate en una orientación hacía el propio player o una posición no adyacente */
	@Test(expected = BadLocationException.class)
	public void testOrientateException2() throws BadLocationException, EntityIsDeadException  {
		//TODO
		player.orientate(0,0,0);
		player.orientate(0,2,0);
	}
	
	/*Test orientate en un jugador muerto. */
	@Test(expected = EntityIsDeadException.class)
	public void testOrientateException1() throws BadLocationException, EntityIsDeadException  {
		player.setHealth(0);
		player.orientate(0,1,0);
	}

	/* Test orientate() hacia posición adyacente no válida (fuera del mundo) */
	@Test
	public void testOrientateException3() throws BadLocationException, EntityIsDeadException  {
		try {
			player.move(1, 0, 0);
			Location orientation = player.orientate(1,0,0);
			assertEquals("Jugador orientado a posición adyacente fuera del mundo.",orientation,new Location(mercury,2,71,0));
			
		} catch (BadLocationException e) {
			fail("No debió lanzar la excepción "+e);
		}
	}

	//Player creado, sin elementos en el inventario.
	@Test
	public void testToStringInitialPlayer() {
		compareLines(outInitialPlayer, player.toString());
	}

	//Player con todos los atributos de player modificados. Algunos métodos deben
	//funcionar ya correctamente.
	@Test
	public void testToStringPlayer() {
		int quantity;
		//Inciamos el inventario con elementos y nuevos valores en health, foodLevel y location
		try {
		  for (Material m : Material.values()) {
			  quantity = (m.isBlock()|| m.isEdible()) ? 5 : 1;
			  player.addItemsToInventory(new ItemStack(m,quantity));	
		  }
		  player.setHealth(10.0);
		  player.orientate(1, -1, 1);
		  player.setFoodLevel(4.0);
		  player.move(1, 0, 1);
		  player.selectItem(Material.BREAD.ordinal());	  
		  compareLines(outPlayer, player.toString());
		} catch (Exception e){
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
		}
	}
	
	//Probamos todas las posibilidades de equals con los atributos que intervienen
	@Test
	public void testEqualsObject() throws RuntimeException {
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
		
		//Orientation
		player2 = new Player("Peter",mercury);
		try {
			player2.orientate(1, 0, 0);
		} catch (Exception e1) {
			fail("Error: no debió lanzar la excepción "+e1.getClass().toString());
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
	public void testHashCode() throws RuntimeException{
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
		
		//Orientation
		player2 = new Player("Peter",mercury);
		try {
			player2.orientate(1, 0, 0);
		} catch (Exception e1) {
			fail("Error: no debió lanzar la excepción "+e1.getClass().toString());
		}
		assertNotEquals("Codes distintos para players con orientate distintos",player.hashCode(),player2.hashCode());
				
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
			
			// si la línea es Health=1.2345  o Food level=1.2345  
			// se extraen los números y se comparan con 0.01 de margen
			String ee[]=exp[i].split("=");
			if (ee[0].equals("Health") || ee[0].equals("Food level")) {
				String rr[]=res[i].trim().split("=");
				if (ee[0].equals(rr[0])) {
					double ed = Double.parseDouble(ee[1]);
					double rd = Double.parseDouble(rr[1]);
					
					assertEquals(ee[0],ed,rd,0.01);
				}
			}
			else
				assertEquals("linea "+i, exp[i],res[i].trim());
		}
	}

	

}
