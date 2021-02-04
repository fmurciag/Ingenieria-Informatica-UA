package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.exceptions.StackSizeException;

public class ItemStack_P2Test {

	ItemStack is;
	
	@Before
	public void setUp() throws Exception {
		 is = new ItemStack(Material.APPLE, ItemStack.MAX_STACK_SIZE);
	}

	
	//Prueba el constructor y los getters
	@Test
	public void testConstructorItemStackAndGetters() {
		try {
			is = new ItemStack(Material.APPLE, ItemStack.MAX_STACK_SIZE);
			assertEquals ("is.type = APPLE", Material.APPLE, is.getType());
			assertEquals ("is.amount = 10", 64, is.getAmount());
		} catch (Exception e) {
			fail ("No debía haber lanzado la excepción "+e.getClass().getName());
		}
		
	}
	
	//Prueba la excepcion StackSizeException para amount=0
	@Test (expected = StackSizeException.class)
	public void testConstructorItemStackthrowException1() throws StackSizeException {
		 new ItemStack(Material.GRASS, 0);	
	}
	
	//Prueba la excepcion StackSizeException para amount>MAX_STACK_SIZE
	@Test (expected = StackSizeException.class)
	public void testConstructorItemStackthrowException2() throws StackSizeException {
		 new ItemStack(Material.BREAD, ItemStack.MAX_STACK_SIZE+1);	
	}

	/*Para todas las materias comprueba: 
	  Si amount>1: lanza excepción si type no es comida o bloque y no debe lanzarla si lo es.*/ 
	@Test 
	public void testConstructorItemStackthrowException3()  {
		 for (Material type : Material.values()) { 
				try {
					is = null;
					is = new ItemStack(type, 2);
					if ( (type.ordinal()>=12) && (type.ordinal()<=15) ) {
						fail ("Error: "+type.name()+" en tu enumerado es una herramienta o arma y amount=2. "
								+ " Debía haber lanzado la excepción StackSizeException");
					}
					else { //Es una materia de bloque o comida
						assertNotNull(is);
						assertEquals ("is.type = type", type, is.getType());
						assertEquals ("is.amount = 2", 2, is.getAmount());
						assertTrue("Es bloque o comida", type.isBlock()||type.isEdible());
					}
				} catch (StackSizeException e) {
					if (type.ordinal()>=12) {
					   assertNull(is);
					}
					else {
						fail ("Error: "+ type.name()+" NO es una herramienta o arma en tu enumerado y amount=2. No debía haber "
								+ "lanzado la excepción StackSizeException");
					}
				} catch (Exception e)  {
					fail ("Error: Debía haber lanzado la excepción StackSizeException y ha lanzado "+e.getClass().getName());
				}		
		 }
	}
	
	@Test (expected = StackSizeException.class)
	public void testConstructorItemStackthrowException4() throws StackSizeException {
		 new ItemStack(Material.BEEF, ItemStack.MAX_STACK_SIZE+1);	
	}
	
	@Test
	public void testConstructorCopiaItemStack() {
		try {
			is = new ItemStack(Material.APPLE, ItemStack.MAX_STACK_SIZE);
		} catch (Exception e) {
			fail ("No debía haber lanzado la excepción "+e.getClass().getName());
		}
		ItemStack newIs = new ItemStack(is);
		assertEquals ("newIs.type = APPLE", Material.APPLE, newIs.getType());
		assertEquals ("newIs.amount = MAX_STACK_SIZE", ItemStack.MAX_STACK_SIZE, newIs.getAmount());
	}

	/* Comprueba que setAmount pone en amount valores > 1 para todos los materiales que son
	   material de bloque o comida y no lanza ninguna excepción */
	@Test
	public void testSetAmountOk() {
		int ordinal=0;
		for (Material type: Material.values()) {
			try {
				ordinal = type.ordinal();
				is = new ItemStack(type,1);
				if (ordinal<12) { //Material de bloque o comida
					is.setAmount(ordinal+2);
					assertEquals ("is.amount > 1",ordinal+2,is.getAmount());
				}
				else //arma o herramienta
				  assertEquals ("is.amount = 1",1,is.getAmount());
			} catch (Exception e) {
				fail("No debía haber lanzado la excepción "+e.getClass().getName()+ "\n Material "+type+" amount = "+(ordinal+2));
			}
		}
	}
	
	// Prueba la excepción StackSizeException de setAmount para amount <1
	@Test (expected = StackSizeException.class)
	public void testSetAmountException1() throws StackSizeException {
		 is = new ItemStack(Material.GRASS, 1);	
		 is.setAmount(0);
	}
	
	//Prueba la excepcion StackSizeException de setAmount para amount>MAX_STACK_SIZE
	@Test (expected = StackSizeException.class)
	public void testSetAmountException2() throws StackSizeException {
		 is=new ItemStack(Material.BREAD, 1);
		 is.setAmount(ItemStack.MAX_STACK_SIZE+1);
	}
	
	/* Comprueba que setAmount lanza excepción StackSizeException para valores > 1 cuando los materiales no son
	   material de bloque ni comida y se comprueba que no asigna el valor */
	@Test
	public void testSetAmountThrowException() {
		int ordinal=0;
		for (Material type: Material.values()) {
			try {
				ordinal = type.ordinal();
				is = new ItemStack(type,1);
				if ( (ordinal>=12) && (ordinal<=15) ) { //Herramienta o arma
					is.setAmount(ordinal-10);
					fail("Error: Debía haber lanzado la excepción StackSizeException \n"+type+" amount = "+(ordinal-10));
				}
				else {
					assertEquals(1,is.getAmount());
				}
				
				
			} catch (StackSizeException e) {
				assertEquals ("is.amount > 1",1,is.getAmount());
			
			} catch (Exception f) {
				fail ("Error: Debía haber lanzado la excepción StackSizeException pero lanzó "+f.getClass().getName());
			}
		}
	}

	// Test para equals
	@Test
	public void testEqualsObject() {

			try {
				ItemStack isApple = new ItemStack(Material.APPLE, 50);
				ItemStack isOtherApple = new ItemStack(Material.APPLE, 50);
				Material type = Material.BEDROCK;
			
				assertFalse(isApple.equals(null));
				assertTrue(isApple.equals(isApple));
				assertTrue(isApple.equals(isOtherApple));
				
				isApple.setAmount (40);
				assertFalse(isApple.equals(isOtherApple));  // amount distintos
				assertFalse(isApple.equals(type));
			} catch (Exception e) {
				fail ("Error: No debió lanzarse la excepción "+e.getClass().getName());
			}
	}
	
	//Test para hasCode()
	@Test
	public void testHashCode() {
		try {
			ItemStack isApple = new ItemStack(Material.APPLE, 50);
			ItemStack isOtherApple = new ItemStack(Material.APPLE, 50);
			ItemStack isDirt = new ItemStack(Material.DIRT, 50);
		
			assertEquals("codes iguales",isApple.hashCode(), isOtherApple.hashCode());
			assertNotEquals ("codes distintos por types distintos", isApple.hashCode(),isDirt.hashCode());
			isOtherApple.setAmount(40);
			assertNotEquals ("codes distintos por values distintos", isApple.hashCode(),isOtherApple.hashCode());
		} catch (Exception e) {
			fail ("Error: No debió lanzarse la excepción "+e.getClass().getName());
		}
	}
	
	//Test para toString()
	@Test
	public void testToString() {
		try {
			
			ItemStack isApple = new ItemStack(Material.APPLE, 50);
			ItemStack isDirt = new ItemStack(Material.DIRT, ItemStack.MAX_STACK_SIZE);
			
			assertEquals("isApple = (APPLE,50)","(APPLE,50)", isApple.toString());
			assertEquals("isDirt = (DIRT,64)","(DIRT,64)", isDirt.toString());
			
		} catch (Exception e) {
			fail ("Error: No debió lanzarse la excepción "+e.getClass().getName());
		}
		
	}

}
