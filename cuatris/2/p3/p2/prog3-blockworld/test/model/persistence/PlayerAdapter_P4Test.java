package model.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import model.Inventory;
import model.ItemStack;
import model.Material;
import model.World;
import model.entities.LivingEntity;
import model.entities.Player;

public class PlayerAdapter_P4Test {
	Player player1, player2;
	World mercury, venus;
	
	@Before
	public void setUp() throws Exception {
		mercury = new World(3,10, "Mercury","Carla");
		venus = new World(5,20, "Venus","Sofia");
		player1 = new Player("Peter",mercury);
		player2 = new Player("Mary",venus);
	}

	/* Crea dos PlayerAdapter a partir del player1 y player2 respectivamente.
	 * Comprueba que el nombre de los IPlayer es el mismo que el de los Player
	 * Y comprueba además que los nombres de ambos IPlayer son distintos
	 */
	@Test
	public void testGetName() {
		IPlayer ip1 = new PlayerAdapter(player1);
		IPlayer ip2 = new PlayerAdapter(player2);
		assertEquals("Peter",ip1.getName());
		assertSame(player1.getName(), ip1.getName());
		assertEquals("Mary",ip2.getName());
		assertSame(player2.getName(), ip2.getName());
		
		assertNotEquals(ip1.getName(),ip2.getName());
		
	}

	/* Crea dos PlayerAdapter a partir de player1 y player2. Comprueba que 
	 *  la localización de cada PlayerAdapter son iguales a las de sus respectivos
	 *  players pero no las mismas. 
	 *  Comprueba luego que las localizaciones de ambos IPlayer son distintas.
	 */
	@Test
	public void testGetLocation() {
		IPlayer ip1 = new PlayerAdapter(player1);
		assertEquals(player1.getLocation(),ip1.getLocation());
		assertNotSame(player1.getLocation(), ip1.getLocation());
		IPlayer ip2 = new PlayerAdapter(player2);
		assertEquals(player2.getLocation(),ip2.getLocation());
		assertNotSame(player2.getLocation(), ip2.getLocation());
		
		assertNotEquals(ip1.getLocation(),ip2.getLocation());	
	}

	/* Crea un PlayerAdapter a partir del player1 y comprueba que la salud del 
	 * PlayerAdapter es la misma que la del player. Modifica la salud del Player y
	 * comprueba que siguen siendo iguales.
	 * Crea otro PlayerAdapter a partir de player2. Modifica el health de player2 
	 * al mismo que player1. Comprueba que ambos PlayerAdapter tienen health iguales.
	 * Modifica el health de player2 al otro valor distinto del player1 y comprueba 
	 * que ambos IPlayer tienen el distinto health.
	 */
	@Test
	public void testGetHealth() {
		IPlayer ip1 = new PlayerAdapter(player1);
		IPlayer ip2 = new PlayerAdapter(player2);
		assertEquals(LivingEntity.MAX_HEALTH,ip1.getHealth(),0.01);	
		player1.setHealth(-5);
		assertEquals(player1.getHealth(),ip1.getHealth(),0.01);
		
		player2.setHealth(-5);
		assertEquals(ip1.getHealth(),ip2.getHealth(),0.01);
		player2.setHealth(7);
		assertNotEquals(ip1.getHealth(),ip2.getHealth(),0.01);
		
	}
	
	
	/* Añade items al inventario del player1. 
	 * A partir de player1 crea un PlayerAdapter.
	 * Comprueba que el inventario del Player es igual que el del PlayerAdapter
	 * usando el método de apoyo "equals"
	 * Añade un nuevo elemento al inventario del player1 y comprueba que ya no son iguales.
	 */
	@Test
	public void testGetInventory1() {
		try {
			player1.addItemsToInventory(new ItemStack(Material.APPLE, ItemStack.MAX_STACK_SIZE));
			player1.addItemsToInventory(new ItemStack(Material.IRON_SWORD, 1));
			IPlayer ip1 = new PlayerAdapter(player1);
			assertTrue(equals(player1.getInventory(),ip1.getInventory()));
			player1.addItemsToInventory(new ItemStack(Material.BREAD, 4));
			assertFalse(equals(player1.getInventory(),ip1.getInventory()));
			
		} catch (Exception e) {
			fail("Error: No debió lanzarse la excepción "+e.getClass().getName());
		}
	}
	
	/* Añade items al inventario del player1. 
	 * A partir de player1 crea un PlayerAdapter.
	 * Añade los mismos items a player2 y crea un nuevo PlayerAdapter a partir de player2
	 * Comprueba que los inventarios de ambos IPlayer son iguales con el método de ayuda "equals"
	 * Modifica algo de player2 (añade un item al inventario, cambia el itemInHand) 
	 * Comprueba que ambos IPlayer siguen siendo iguales.
	 * Crea de nuevo el PlayerAdapter asociado a player2
	 * Comprueba que ambos IPlayer son ahora distintos
	 */
	@Test
	public void testGetInventory2() {
		try {
			player1.addItemsToInventory(new ItemStack(Material.APPLE, ItemStack.MAX_STACK_SIZE));
			player1.addItemsToInventory(new ItemStack(Material.IRON_SWORD, 1));
			IPlayer ip1 = new PlayerAdapter(player1);
			player2.addItemsToInventory(new ItemStack(Material.APPLE, ItemStack.MAX_STACK_SIZE));
			player2.addItemsToInventory(new ItemStack(Material.IRON_SWORD, 1));
			IPlayer ip2 = new PlayerAdapter(player2);
			assertTrue(equals(ip2.getInventory(),ip1.getInventory()));
			player2.addItemsToInventory(new ItemStack(Material.BREAD, 4));
			assertTrue(equals(ip2.getInventory(),ip1.getInventory()));
			ip2 = new PlayerAdapter(player2);
			assertFalse(equals(ip1.getInventory(),ip2.getInventory()));
			
		} catch (Exception e) {
			fail("Error: No debió lanzarse la excepción "+e.getClass().getName());
		}
	}

	
	
	//METODOS DE APOYO
	/* Comprueba si un Inventory es igual a un IInventory */
	boolean equals(Inventory inv, IInventory iinv) {
		boolean eq1 = (inv.getSize() == iinv.getSize()) && (inv.getItemInHand().equals(iinv.inHandItem()));
		boolean eq2 = true;
		for (int i=0; i<inv.getSize() && eq2 && eq1; i++)
			eq2=(inv.getItem(i).equals(iinv.getItem(i)));
		return (eq1 && eq2);
	}
	
	/* Comprueba si dos IInventory son iguales */
	boolean equals(IInventory iinv1, IInventory iinv2) {
		boolean eq1 = (iinv1.getSize() == iinv2.getSize()) && (iinv1.inHandItem().equals(iinv2.inHandItem()));
		boolean eq2 = true;
		for (int i=0; i<iinv1.getSize() && eq2 && eq1; i++)
			eq2=(iinv1.getItem(i).equals(iinv2.getItem(i)));
		return (eq1 && eq2);
	}
	

}
