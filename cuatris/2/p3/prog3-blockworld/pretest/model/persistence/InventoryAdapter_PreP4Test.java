package model.persistence;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Inventory;
import model.ItemStack;
import model.Material;
import model.exceptions.StackSizeException;

public class InventoryAdapter_PreP4Test {
	ItemStack isApple, isGrass, isIronSword;
	
	@Before
	public void setUp() throws Exception {
		isApple= new ItemStack(Material.APPLE, ItemStack.MAX_STACK_SIZE);
		isGrass = new ItemStack(Material.GRASS, 5);
		isIronSword = new ItemStack(Material.IRON_SWORD, 1);
		
	}

	/* Crea un Inventory vacío y a partir de el un InventoryAdapter. 
	 * Comprueba que el InventoryAdapter tiene tamaño 0 y no tiene nada en la mano
	 */
	@Test
	public void testInventoryAdapter1() {
		Inventory inv = new Inventory();
		IInventory ia = new InventoryAdapter(inv);
		assertEquals (inv.getSize(), ia.getSize());
		assertEquals (inv.getItemInHand(), ia.inHandItem());
		assertNull (ia.inHandItem());
	}

	/* Crea un Inventory y añádele algunos items. Pon uno en la mano.
	 * Crea a partir de él un InventoryAdapter y comprueba que tienen el 
	 * mismo número de items.	
	 */
	//TODO
	@Test
	public void testGetSize1() {
		Inventory inv = new Inventory();
		inv.addItem(isApple);
		inv.setItemInHand(isApple);
		IInventory ia = new InventoryAdapter(inv);
		assertEquals(ia.getSize(), inv.getSize());
	}

	/* Crea dos Inventory con los mismos items. 
	 * Pon el mismo item en la mano a ambos. 
	 * Crea dos InventoryAdapter a partir de los dos Inventory. 
	 * Comprueba que los dos IInventory tienen el mismo número de items.	
	 * Añade un item nuevo a uno de los Inventory y comprueba que los 
	 * IInventory ya no tienen el mismo tamaño
	 */
	//TODO
	@Test
	public void testGetSize2() {
		Inventory inv = new Inventory();
		Inventory inv2 = new Inventory();
		inv.addItem(isGrass);
		inv2.addItem(isGrass);
		inv.addItem(isApple);
		inv2.addItem(isApple);
		inv.setItemInHand(isApple);
		inv2.setItemInHand(isApple);
		
		

		InventoryAdapter ia = new InventoryAdapter(inv);
		InventoryAdapter ia2 = new InventoryAdapter(inv2);
		
		assertEquals(ia.getSize(), ia2.getSize());
		inv.addItem(isIronSword);
		assertNotEquals(ia.getSize(), ia2.getSize());
		
	}
	
	/* Prueba de retorno null de IInventory.getItem(int) para
	 * int<0 o int>IInventory.getItem(IInventory.getSize()) 
	 */
	//TODO
	@Test
	public void testGetItemNull() {
		Inventory inv = new Inventory();
		inv.addItem(isApple);
		IInventory ia = new InventoryAdapter(inv);
		assertEquals(1,ia.getSize());
		assertNull(ia.getItem(2));
		assertNull(ia.getItem(-1));
		
				
	}
	
	/* Crea un Inventory y añádele algunos items.
	 * Crea a partir de él un InventoryAdapter y comprueba que tienen los 
	 * mismos items en las mismas posiciones. 
	 * Añade un item nuevo al Inventory y comprueba que también lo tiene
	 * IInventory	
	 */
	//TODO
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
		assertEquals(inv.getSize(), ia.getSize());
		inv.addItem(isApple);
		assertEquals(inv.getSize(), ia.getSize());
	
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
	//TODO
	@Test
	public void testGetItem2() {
		Inventory inv = new Inventory();
		Inventory inv2 = new Inventory();
		inv.addItem(isGrass);
		inv2.addItem(isGrass);
		inv.addItem(isApple);
		inv2.addItem(isApple);
		
		
		

		InventoryAdapter ia = new InventoryAdapter(inv);
		InventoryAdapter ia2 = new InventoryAdapter(inv2);
		
		assertEquals(ia.getItem(0), ia2.getItem(0));
		assertEquals(ia.getItem(1), ia2.getItem(1));
		inv.addItem(isIronSword);
		assertEquals(ia.getItem(2), isIronSword);
		assertEquals(ia2.getItem(2), null);
	
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

	/* Crea un Inventary y añádele algunos items. Pon uno en la mano.
	 * Crea a partir de él un InventoryAdapter y comprueba que tiene en la
	 * mano el mismo item que el del Inventory
	 */
	//TODO
	@Test
	public void testInHandItem1() {
		Inventory inv = new Inventory();
		inv.setItemInHand(isApple);
		InventoryAdapter ia = new InventoryAdapter(inv);
		assertEquals(inv.getItemInHand(),ia.inHandItem());

	}

	/* Crea dos Inventary y añádeles algunos items. Pon el mismo en la mano a ambos.
	 * Crea dos InventoryAdapter a partir de los dos Inventory. Comprueba que
	 * ambos IInventory tienen el mismo item en la mano. Cambia en uno de los Inventory el
	 * itemInHand. Comprueba que ahora los IInventory no tienen el mismo itemInHand.
	 */
	//TODO
	@Test
	public void testInHandItem2() {
		Inventory inv = new Inventory();
		Inventory inv2 = new Inventory();
		inv.setItemInHand(isApple);
		inv2.setItemInHand(isApple);
		inv.addItem(isGrass);
		inv2.addItem(isGrass);
		
		
		
		
		InventoryAdapter ia = new InventoryAdapter(inv);
		InventoryAdapter ia2 = new InventoryAdapter(inv2);
		assertEquals(ia2.inHandItem(),ia.inHandItem());
		inv.setItemInHand(isGrass);
		assertNotEquals(ia2.inHandItem(),ia.inHandItem());
		
	
	}
}
