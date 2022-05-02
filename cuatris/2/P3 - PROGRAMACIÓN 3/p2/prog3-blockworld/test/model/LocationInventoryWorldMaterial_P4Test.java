package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LocationInventoryWorldMaterial_P4Test {
	World w;
	static ItemStack isApple, isGrass;
	
	@BeforeClass
	public static void  setUpBeforeClass() throws Exception {
		isApple= new ItemStack(Material.APPLE, ItemStack.MAX_STACK_SIZE);
		isGrass = new ItemStack(Material.GRASS, 5);
	}

	@Before
	public void setUp() throws Exception {
		w =  new World(5, 20, "World 10x10","Raul");
	}

	//TESTS P4 PARA Location
	@Test
	public void testCompareToDifferentX() {
		Location loc1 = new Location(w, 7, 5, -3);
		Location loc2 = new Location(w, 6.9, 7, -2);
		assertTrue (loc1.compareTo(loc2)>0);
		assertTrue (loc2.compareTo(loc1)<0);
	}

	@Test
	public void testCompareToSameXDifferentY() {
		Location loc1 = new Location(w, 7, 6.9, -3);
		Location loc2 = new Location(w, 7, 7, -5);
		assertTrue (loc1.compareTo(loc2)<0);
		assertTrue (loc2.compareTo(loc1)>0);
	}
	
	@Test
	public void testCompareToSameXYDifferentZ() {
		Location loc1 = new Location(w, 7, 20, -3);
		Location loc2 = new Location(w, 7, 20, -2.9);
		assertTrue (loc1.compareTo(loc2)<0);
		assertTrue (loc2.compareTo(loc1)>0);
	}
	
	@Test
	public void testCompareToSameXYZ() {
		Location loc1 = new Location(w, 7, 20, -2.9);
		Location loc2 = new Location(w, 7, 20, -2.9);
		assertTrue (loc1.compareTo(loc2)==0);
		assertTrue (loc2.compareTo(loc1)==0);
	}
	
	//TEST P4 PARA Inventory
	/* Comprueba que el constructor copia de un inventario vacío crea otro igual vacío */
	@Test
	public void testConstructorCopiaInventory1() {
		Inventory inv1 = new Inventory();
		Inventory inv2 = new Inventory(inv1);
		assertEquals(inv1,inv2);
		inv1.addItem(isApple);
		assertNotEquals(inv1,inv2);
	}
	
	/* Crea un inventario y añadele algunos items y pon uno en su mano. Crea un inventario 
	 * nuevo copia del anterior. Comprueba que ambos son iguales
	 */
	@Test
	public void testConstructorCopiaInventory2() {
		Inventory inv1 = new Inventory();
		
			inv1.addItem(isApple);
			inv1.addItem(isGrass);
			inv1.addItem(new ItemStack(isApple));
			inv1.addItem(new ItemStack(isGrass));
			inv1.setItemInHand(new ItemStack(isGrass));
			Inventory inv2 = new Inventory(inv1);
			assertEquals(inv1,inv2);	
	}
		
	//TEST P4 PARA World
	//Comprueba que el nombre del jugador del mundo w es "Raul".
	@Test
	public void testConstructorWorld() {
		
		assertEquals("Raul",w.getPlayer().getName());
	}
	
	//TEST P4 PARA Material
	/* Comprueba que el material AIR está en la posición 18 de los materiales. Que es un bloque 
	 * y es líquido nada más. Que su valor es 0 y su símbolo ' ' */
	@Test
	public void testAIR() {
		Material air = Material.AIR;
		assertEquals(18,air.ordinal());
		assertTrue(air.isBlock());
		assertTrue(air.isLiquid());
		assertFalse(air.isEdible());
		assertFalse(air.isTool());
		assertFalse(air.isWeapon());
		assertEquals(0,air.getValue(),0.01);
		assertEquals(' ',air.getSymbol());
	}
}
