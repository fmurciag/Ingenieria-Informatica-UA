package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.exceptions.StackSizeException;

public class ItemStack_PreP2Test {

	ItemStack is;
	
	@Before
	public void setUp() throws Exception {
		 is = new ItemStack(Material.APPLE, ItemStack.MAX_STACK_SIZE);
	}

	
	//Prueba el constructor y los getters
	@Test
	public void testConstructorItemStackAndGetters() {
		try {
			assertEquals ("is.type = APPLE", Material.APPLE, is.getType());
			assertEquals ("is.amount = 10", 64, is.getAmount());
		} catch (Exception e) {
			fail ("No debía haber lanzado la excepción "+e.getClass().getName());
		}
		
	}
	
	//TODO
	//Prueba la excepcion StackSizeException para amount=0
	@Test (expected = StackSizeException.class)
	public void testConstructorItemStackthrowException1() throws StackSizeException {
				
					is = new ItemStack(Material.APPLE, 0);
						
				

					
		 }
	
	
	//TODO
	//Prueba la excepcion StackSizeException para amount>MAX_STACK_SIZE
	@Test (expected = StackSizeException.class)
	public void testConstructorItemStackthrowException2() throws StackSizeException {
		is = new ItemStack(Material.APPLE, 6999);
			}

	
	/* comprueba: 
	  Si amount>1: lanza excepción si type no es comida o bloque */ 
	@Test 
	public void testConstructorItemStackthrowException3()  {
		 for (Material type : Material.values()) { 
				try {
					is = null;
					is = new ItemStack(type, 2);
					if (type.ordinal()>=12) {
						fail ("Error: "+type.name()+" en tu enumerado es una herramienta o arma y amount=2. "
								+ " Debía haber lanzado la excepción StackSizeException");
					}
				} catch (StackSizeException e) {
					// OK!
				} catch (Exception e)  {
					fail ("Error: Debía haber lanzado la excepción StackSizeException y ha lanzado "+e.getClass().getName());
				}		
		 }
	}
	
	//TODO
	/* Comprueba: 
	  Si amount>1: El ctor no lanza excepción si el material es de tipo comida o bloque.*/ 
	@Test 
	public void testConstructorItemStackDoesNotThrows()  {
		 for (Material type : Material.values()) 
		 { 
				try {
						
						if (type.ordinal()<=11) {
							is=new ItemStack(type,2);
						
						}
						
					}
				catch (StackSizeException e) {
					// OK!
					fail("Comprueba si amount>1, que el ctor no lanza excepción si el material es de tipo comida o bloque.");
			}	}
		 
		
	}
	
	//TODO
	@Test
	public void testConstructorCopiaItemStack() {
		ItemStack copia;
		try {
			copia = new ItemStack(Material.APPLE,5);
			is=new ItemStack(copia);
		} catch (StackSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
 	}

	//TODO
	/* Comprueba que setAmount acepta en amount valores > 1 para todos los materiales que son
	   material de bloque o comida y no lanza ninguna excepción */
	@Test
	public void testSetAmountOk() {
		 for (Material type : Material.values()) 
		 { 
				try {
						if(type.ordinal()<=11)
							is=new ItemStack(type,2);
						
					}
				catch (StackSizeException e) {
					// OK!
					fail("Comprueba si amount>1, que el ctor no lanza excepción si el material es de tipo comida o bloque.");
			}	}
		
	}

	//TODO
	/* Comprueba que setAmount acpeta en amount valores == 1 para herramientas o armas y no lanza ninguna excepción */
	@Test
	public void testSetAmountOk2() {
		try {
			is.setAmount(2);
			
		} catch (StackSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Comprueba que setAmount acpeta en amount valores == 1 para herramientas o armas y no lanza ninguna excepción.");

		}
	}

	//TODO
	// Prueba la excepción StackSizeException de setAmount para amount <1
	@Test (expected = StackSizeException.class)
	public void testSetAmountException1() throws StackSizeException {
		is = new ItemStack(Material.APPLE, 3);
		is.setAmount(0);
		fail("Prueba la excepción StackSizeException de setAmount para amount <1");
	}
	
	//Prueba la excepcion StackSizeException de setAmount para amount>MAX_STACK_SIZE
	//TODO
	@Test (expected = StackSizeException.class)
	public void testSetAmountException2() throws StackSizeException {
		is = new ItemStack(Material.APPLE, 3);
		is.setAmount(65);
		fail("Prueba la excepción StackSizeException de setAmount para amount >MAX_STACK_SIZE");
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
	
	//TODO
	//Test para toString()
	@Test
	public void testToString() {
		fail("Crea algún ItemStack, por ejemplo, uno de 50 manzanas y comprueba que el método toString() te devuelve la cadena \"(APPLE,50)\".");
	}

}
