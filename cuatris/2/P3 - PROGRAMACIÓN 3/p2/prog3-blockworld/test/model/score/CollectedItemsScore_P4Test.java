package model.score;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.ItemStack;
import model.Material;
import model.exceptions.StackSizeException;

public class CollectedItemsScore_P4Test {

	CollectedItemsScore scLaura;
	CollectedItemsScore scPeter;
	
	@Before
	public void setUp() throws Exception {
		scLaura = new CollectedItemsScore("Laura");
		scPeter = new CollectedItemsScore("Peter");
	}

	/* Comprueba que los nombres de los jugadores de los dos
	 * Score son correctos
	 */
	@Test
	public void testCollectedItemsScoreAndGetName() {
		Score<ItemStack> score = scLaura; //Implementan la herencia ???
		assertEquals("Laura", score.getName());
		
		assertEquals("Laura", scLaura.getName());
		assertEquals("Peter", scPeter.getName());
	}

	/*Crea algunos ItemStack. Inicialmente aplica score sobre los Scores scLaura y 
	 * scPeter con el mismo ItemStack. Comprueba que compareTo da 0.
	 * Aplica sobre uno de ellos con score, un ItemStack que incremente su puntuación.
	 * Comprueba ahora que el que ha aumentado, si es el que invoca a compareTo da un
	 * valor negativo y si es el menor el que lo invoca, da un valor positivo.
	 */
	@Test
	public void testCompareTo() {
		assertTrue(scPeter.compareTo(scLaura)==0);
		try {
			scPeter.score(new ItemStack(Material.STONE,15));
			scLaura.score(new ItemStack(Material.STONE,15));
			assertTrue(scPeter.compareTo(scLaura)==0);
			scPeter.score(new ItemStack(Material.GRASS,7));
			assertTrue(scPeter.compareTo(scLaura)<0);
			assertTrue(scLaura.compareTo(scPeter)>0);
		} catch (StackSizeException e) {
			fail("Error. No debió lanzar la excepción "+e.getClass().getName());
		}
	}

	/*Crea varios ItemStack. Con cada uno, crea las puntuaciones (score) sobre el 
	 * CollectedItemsScore scLaura y comprueba con getScoring() que  los valores que se van
	 * obteniendo se van acumulando sucesivamente. Por ejemplo:
	 * ItemStack(Material.APPLE,5) --> 20
	 * ItemStack(Material.DIRT,2) -->21
	 * ItemStack(Material.BEEF,50) -->421
	 * ...
	 */
	@Test
	public void testScoreItemStack() {
		try {
			assertEquals(0,scLaura.getScoring(),0.01);
			scLaura.score(new ItemStack(Material.APPLE,5));
			assertEquals(20,scLaura.getScoring(),0.01);
			scLaura.score(new ItemStack(Material.DIRT,2));
			assertEquals(21,scLaura.getScoring(),0.01);
			scLaura.score(new ItemStack(Material.BEEF,50));
			assertEquals(421,scLaura.getScoring(),0.01);
			scLaura.score(new ItemStack(Material.IRON_PICKAXE,1));
			assertEquals(421.5,scLaura.getScoring(),0.01);
			scLaura.score(new ItemStack(Material.LAVA,30));
			assertEquals(451.5,scLaura.getScoring(),0.01);
			scLaura.score(new ItemStack(Material.AIR,ItemStack.MAX_STACK_SIZE));
			assertEquals(451.5,scLaura.getScoring(),0.01);
			
		} catch (Exception e) {
			fail("Error. No debió lanzar la excepción "+e.getClass().getName());
		}
	}

	/* Comprueba toString sobre scPeter y comprueba que inicialmente es: "Peter:0.0"
	 * Crea varios items y aplica el método score sobre scPeter. Comprueba que
	 * la salida va cambiando de valor (puedes hacerlo con los mismos items
	 * del test anterior)
	 */
	@Test
	public void testToString() {
		try {
			//assertEquals("Peter:0.0",formatToString(scPeter.toString(),2));
			compareScores ("Peter:0.0",scPeter.toString());
			scPeter.score(new ItemStack(Material.BREAD,5));
			//assertEquals("Peter:25.0",formatToString(scPeter.toString(),2));
			compareScores ("Peter:25.0",scPeter.toString());
			scPeter.score(new ItemStack(Material.WOOD_SWORD,1));
			//assertEquals("Peter:26.0",formatToString(scPeter.toString(),2));
			compareScores ("Peter:26.0",scPeter.toString());
			scPeter.score(new ItemStack(Material.IRON_SWORD,1));
			//assertEquals("Peter:28.0",formatToString(scPeter.toString(),2));
			compareScores ("Peter:28.0",scPeter.toString());
		
		} catch (StackSizeException e) {
			fail("Error. No debió lanzar la excepción "+e.getClass().getName());
		}
	}

	/**********************************************/
	//FUNCIONES DE APOYO

	/* Para las salidas Score.toString() compara los valores impresos
	 * de los Scores hasta una precisión de 0.01
	 * 
	 */
		void compareScores(String expected, String result ) {
			String ex[]= expected.split(":");
			String re[]= result.split(":");
			if (ex.length!=re.length) fail("Lineas distintas");
			if (ex.length==2) {
				if (ex[0].trim().equals(re[0].trim())) {
					double ed = Double.parseDouble(ex[1]);
					double rd = Double.parseDouble(re[1]);
			
					assertEquals(ex[0],ed,rd,0.01);
				}
				else fail("Nombres jugadores distintos: esperado=<"+ex[0].trim()+"> obtenido=<"+re[0].trim()+">");
			}
			else
				assertEquals(expected.trim(),result.trim());	
				
		}	
	
}
