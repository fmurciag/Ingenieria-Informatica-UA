package model.score;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import model.Location;
import model.World;

public class PlayerMovementScore_P4Test {

	PlayerMovementScore scLaura;
	PlayerMovementScore scPeter;
	Location location, initialPos;
	World world;

	@Before
	public void setUp() throws Exception {
		scLaura = new PlayerMovementScore("Laura");
		scPeter = new PlayerMovementScore("Peter");
		world = new World(3,10,"A Little World", "Joan");
		location = world.getPlayer().getLocation();
		initialPos = new Location(location);
	}

	/* Comprueba que los nombres de los jugadores de los dos
	 * Score son correctos
	 */
	@Test
	public void testPlayerMovementScoreAndGetName() {
		Score<Location> score = scLaura; //Implementas la herencia ???
		assertEquals("Laura", score.getName());
		
		assertEquals("Laura", scLaura.getName());
		assertEquals("Peter", scPeter.getName());
	}

	/* Comprueba que inicialmente los Score de Peter y Laura son
	 * iguales. Inicia ambos jugadores a la misma posición previa y
	 * comprueba que las puntuaciones siguen siendo iguales. 
	 * Mueve ambos jugadores a mismas posiciones y las puntuaciones 
	 * deben seguir siendo iguales.
	 * Mueve alguno de ellos a otra posición y comprueba que si éste
	 * es el que invoca al método, devuelve un valor positivo, y negativo
	 * en caso contrario.
	 */
	@Test
	public void testCompareTo() {
		try {
			assertTrue(scPeter.compareTo(scLaura)==0);
			scPeter.score(initialPos);
			scLaura.score(initialPos);
			assertTrue(scPeter.compareTo(scLaura)==0);
			scPeter.score(location.above());
			scLaura.score(location.above());
			assertTrue(scPeter.compareTo(scLaura)==0);
			scPeter.score(location.above().above());
			assertTrue(scPeter.compareTo(scLaura)>0);
			assertTrue(scLaura.compareTo(scPeter)<0);
		} catch (Exception e) {
			fail("Error. No debió lanzar la excepción "+e.getClass().getName());
		}
	}

	/* Comprobamos inicialmente que el Score de un jugador es 0.
	 * Guardamos la posición inicial del jugador. Y vamos aplicando 
	 * sobre Score varias Locations y comprobamos con getScoring()
	 * que los marcadores van aumentando sucesivamente hasta volver
	 * de nuevo a la posición inicial. Por ejemplo:
	 * Location -- world.getPlayer().getLocation() -- 0.0
	 * Location -- above() -- 1.0
	 * Location  -- above() -- 2.0
	 * Location -- (0,90,0) -- 24.0
	 * ...
	 */
	@Test
	public void testScoreLocation() {
		try {
			assertEquals(0,scLaura.getScoring(),0.01);
			scLaura.score(initialPos);
			assertEquals(0,scLaura.getScoring(),0.01);
			scLaura.score(initialPos);
			assertEquals(0,scLaura.getScoring(),0.01);
			
			scLaura.score(location.above());
			assertEquals(1.0,scLaura.getScoring(),0.01);
			
			scLaura.score(location.above().above());
			assertEquals(2.0,scLaura.getScoring(),0.01);
			
			location.setY(90);
			scLaura.score(location);
			assertEquals(24.0,scLaura.getScoring(),0.01);
			
			location.setX(-4);
			scLaura.score(location);
			assertEquals(28.0,scLaura.getScoring(),0.01);
			
			location.setZ(5);
			scLaura.score(location);
			assertEquals(33.0,scLaura.getScoring(),0.01);
			
			scLaura.score(initialPos);
			assertEquals(57.839,scLaura.getScoring(),0.01);
			
		} catch (Exception e) {
			fail("Error. No debió lanzar la excepción "+e.getClass().getName());
		}
	}

	/* Comprueba toString sobre scPeter y comprueba que inicialmente es: "Peter:0.0"
	 * Crea varios items y aplica el método score sobre scPeter. Comprueba que
	 * la salida va cambiando de valor (puedes hacerlo con los mismos locations
	 * del test anterior)
	 */
	@Test
	public void testToString() {
		try {
			compareScores("Peter:0.0",scPeter.toString());
			
			scPeter.score(initialPos);
			compareScores("Peter:0.0",scPeter.toString());
			
			scPeter.score(location.below());
			compareScores("Peter:1.0",scPeter.toString());
			
			scPeter.score(location.below().below());
			compareScores("Peter:2.0",scPeter.toString());
			
			location.setY(0);
			scPeter.score(location);
			compareScores("Peter:66.0",scPeter.toString());
			
			location.setX(5);
			scPeter.score(location);
			compareScores("Peter:71.0",scPeter.toString());
			
			location.setZ(-4);
			scPeter.score(location);
			
			compareScores("Peter:75.0",scPeter.toString());
			
			scPeter.score(initialPos);
			compareScores("Peter:141.309",scPeter.toString());
		
		} catch (Exception e) {
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
