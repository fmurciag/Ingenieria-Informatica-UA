package model.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import model.Inventory;
import model.ItemStack;
import model.Material;

public class InventoryAdapter_P4Test {
	ItemStack isApple, isGrass, isIronSword;
	
	@Before
	public void setUp() throws Exception {
		isApple= new ItemStack(Material.APPLE, ItemStack.MAX_STACK_SIZE);
		isGrass = new ItemStack(Material.GRASS, 5);
		isIronSword = new ItemStack(Material.IRON_SWORD, 1);
		
	}

	/* Crea un inventario vacío y a partir de el un InventoryAdapter. 
	 * Comprueba que el InventoryAdapter tiene tamaño 0 y no tiene nada en la mano
	 */
	@Test
	public void testInventoryAdapter() {
		Inventory inv = new Inventory();
		IInventory ia = new InventoryAdapter(inv);
		assertEquals (inv.getSize(), ia.getSize());
		assertEquals (inv.getItemInHand(), ia.inHandItem());
		assertNull (ia.inHandItem());
	}


	/* Crea un inventario y añádele algunos items. Pon uno en la mano.
	 * Crea a partir de él un InventoryAdapter y comprueba que tienen el 
	 * mismo número de items.	
	 */
	@Test
	public void testGetSize1() {
		Inventory inv = new Inventory();
		inv.addItem(isApple);
		inv.addItem(isGrass);
		inv.addItem(isIronSword);
		inv.addItem(new ItemStack(isApple));
		inv.addItem(new ItemStack(isGrass));
		inv.addItem(new ItemStack (isIronSword));
		inv.setItemInHand(new ItemStack(isIronSword));
		IInventory ia = new InventoryAdapter(inv);
		assertEquals (6, ia.getSize());
	}
	
	/* Crea dos Inventory con los mismos items. 
	 * Pon el mismo item en la mano a ambos. 
	 * Crea dos InventoryAdapter a partir de los dos Inventory. 
	 * Comprueba que los dos IInventory tienen el mismo número de items.	
	 * Añade un item nuevo a uno de los Inventory y comprueba que los 
	 * IInventory ya no tienen el mismo tamaño
	 */
	@Test
	public void testGetSize2() {
		Inventory inv1 = new Inventory();
		inv1.addItem(isApple);
		inv1.addItem(isGrass);
		inv1.addItem(isIronSword);
		inv1.addItem(new ItemStack(isApple));
		inv1.addItem(new ItemStack(isGrass));
		inv1.addItem(new ItemStack (isIronSword));
		inv1.setItemInHand(new ItemStack(isIronSword));
		Inventory inv2 = new Inventory(inv1);
		IInventory ia1 = new InventoryAdapter(inv1);
		IInventory ia2 = new InventoryAdapter(inv2);
		assertEquals (ia1.getSize(), ia2.getSize());
		inv1.addItem(new ItemStack(isGrass));
		assertNotEquals (ia1.getSize(), ia2.getSize());
		
	}

	/* Prueba de retorno null de IInventory.getItem(int) para
	 * int<0 o int>IInventory.getItem(IInventory.getSize()) 
	 */
	@Test
	public void testGetItemNull() {
		Inventory inv = new Inventory();
		IInventory ia = new InventoryAdapter(inv);
		assertNull(ia.getItem(0));
		assertNull(ia.getItem(-1));
		inv.addItem(new ItemStack(isApple));
		assertNotNull(ia.getItem(0));
		assertNull(ia.getItem(1));
	}
	
	/* Crea un inventario y añádele algunos items.
	 * Crea a partir de él un InventoryAdapter y comprueba que tienen los 
	 * mismos items en las mismas posiciones.	
	 */
	@Test
	public void testGetItem1() {
		Inventory inv = new Inventory();
		ItemStack isApple2 = new ItemStack(isApple);
		ItemStack isGrass2 = new ItemStack(isGrass);
		ItemStack isIronSword2 = new ItemStack (isIronSword);
		ItemStack vis[]= {isApple, isGrass, isIronSword, isApple2,isGrass2, isIronSword2};
		inv.addItem(isApple);
		inv.addItem(isGrass);
		inv.addItem(isIronSword);
		inv.addItem(isApple2);
		inv.addItem(isGrass2);
		inv.addItem(isIronSword2);
		inv.setItemInHand(new ItemStack(isIronSword));
		IInventory ia = new InventoryAdapter(inv);
		int i=0;
		for (ItemStack is: vis) {
			assertEquals(is,ia.getItem(i));
			assertSame(is, ia.getItem(i));
			i++;
		}
		inv.addItem(isGrass2);
		assertEquals(isGrass2,ia.getItem(6));
		
	}

	/* Crea dos Inventory con los mismos items. 
	 * Crea dos InventoryAdapter a partir de los dos Inventory. 
	 * Comprueba que los dos IInventory tienen los mismos elementos
	 * en las mismas posiciones.	
	 * Añade un item nuevo a uno de los Inventory y comprueba que su IInventory
	 * asociado tiene también el item añadido.
	 * Accede a esa posición con getItem sobre el otro IInventory y comprueba
	 * que devuelve null.
	 */
	@Test
	public void testGetItem2() {
		Inventory inv1 = new Inventory();
		ItemStack isApple2 = new ItemStack(isApple);
		ItemStack isGrass2 = new ItemStack(isGrass);
		ItemStack isIronSword2 = new ItemStack (isIronSword);

		inv1.addItem(isApple);
		inv1.addItem(isGrass);
		inv1.addItem(isIronSword);
		inv1.addItem(isApple2);
		inv1.addItem(isGrass2);
		inv1.addItem(isIronSword2);
		inv1.setItemInHand(new ItemStack(isIronSword));
		Inventory inv2 = new Inventory(inv1);
		IInventory iinv1 = new InventoryAdapter(inv1);
		IInventory iinv2 = new InventoryAdapter(inv2);
		for (int i=0; i<iinv1.getSize(); i++) {
			assertEquals(iinv1.getItem(i),iinv2.getItem(i));
			assertSame(iinv1.getItem(i),iinv2.getItem(i));
			i++;
		}
		inv2.addItem(new ItemStack(isGrass));
		assertNull(iinv1.getItem(6));
		assertEquals(isGrass, iinv2.getItem(6));
	}
	
	/* Crea un inventario y añádele algunos items. 
	 * Crea a partir de él un InventoryAdapter y comprueba que en la mano
	 * tiene null.
	 */
	@Test
	public void testInHandItemNull() {
		Inventory inv = new Inventory();
		inv.addItem(isApple);
		inv.addItem(isGrass);
		
		InventoryAdapter ia = new InventoryAdapter(inv);
		assertNull(ia.inHandItem());
	}
	
	
	/* Crea un inventario y añádele algunos items. Pon uno en la mano.
	 * Crea a partir de él un InventoryAdapter y comprueba que tiene en la
	 * mano el mismo item que el del inventario
	 */
	@Test
	public void testInHandItem1() {
		Inventory inv = new Inventory();
		inv.addItem(isApple);
		inv.addItem(isGrass);
		inv.setItemInHand(isIronSword);
		InventoryAdapter ia = new InventoryAdapter(inv);
		assertSame(isIronSword, ia.inHandItem());
	}
	

	/* Crea dos Inventary y añádeles algunos items. Pon el mismo en la mano a ambos.
	 * Crea dos InventoryAdapter a partir de los dos Inventory. Comprueba que
	 * ambos IInventory tienen el mismo item en la mano. Cambia en uno de los Inventory el
	 * itemInHand. Comprueba que ahora los IInventory no tienen el mismo itemInHand.
	 */
	@Test
	public void testInHandItem2() {
		Inventory inv1 = new Inventory();
		inv1.addItem(isApple);
		inv1.addItem(isGrass);
		inv1.setItemInHand(isIronSword);
		Inventory inv2 = new Inventory(inv1);
		InventoryAdapter iinv1 = new InventoryAdapter(inv1);
		InventoryAdapter iinv2 = new InventoryAdapter(inv2);
		assertEquals(iinv1.inHandItem(), iinv2.inHandItem());
		inv2.setItemInHand(new ItemStack(isGrass));
		assertNotEquals(iinv1.inHandItem(), iinv2.inHandItem());
	}
}
