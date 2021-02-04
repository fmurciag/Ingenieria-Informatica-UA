package model.score;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import model.Location;
import model.World;

public class PlayerMovementScore_PreP4Test {

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
	 * PlayerMovementScore son correctos
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
	//TODO
	@Test
	public void testCompareTo() {
		try {
			assertTrue(scPeter.compareTo(scLaura)==0);
			scPeter.score(initialPos);
			scLaura.score(initialPos);
			assertTrue(scPeter.compareTo(scLaura)==0);
			//fail("Completa el testCompareTo()");
			Location loc=new Location(initialPos);
			loc.setX(loc.getX()+1);
			scPeter.score(loc);
			assertTrue(scPeter.compareTo(scLaura)>0);
			
		} catch (Exception e) {
			fail("Error. No debió lanzar la excepción "+e.getClass().getName());
		}
	}

	/* Comprobamos inicialmente que el Score de un jugador es 0.
	 * Guardamos la posición inicial del jugador. Y vamos aplicando 
	 * sobre Score varias Locations y analizamos con getScoring()
	 * que los marcadores van aumentando sucesivamente.
	 * Por ejemplo:
	 * Location -- world.getPlayer().getLocation() -- 0.0
	 * Location -- above() -- 1.0
	 * Location  -- above() -- 2.0
	 * Location -- (0,90,0) -- 24.0
	 * ...
	 */
	//TODO
	@Test
	public void testScoreLocation() {
        try {
            //fail("Realiza el testScoreLocation() ");
            assertEquals(scPeter.getScoring(),0,0.01);
            scPeter.score(world.getPlayer().getLocation());
            assertEquals(scPeter.getScoring(),0,0.01);
            scPeter.score(world.getPlayer().getLocation().above());
            assertEquals(scPeter.getScoring(),1,0.01);
            scPeter.score(world.getPlayer().getLocation().above().above());
            assertEquals(scPeter.getScoring(),2,0.01);
            scPeter.score(new Location(world,0,90,0));
            assertEquals(scPeter.getScoring(),24,0.01);

        } catch (Exception e) {
            fail("Error. No debió lanzar la excepción "+e.getClass().getName());
        }
    }

	/* Aplica toString sobre scPeter y comprueba que inicialmente es: "Peter:0.0"
	 * Aplica el método score con varias Locations y analiza que  la salida va 
	 * cambiando de valor (puedes hacerlo con los mismos Locations del test anterior)
	 */
	//TODO
	@Test
	public void testToString() {
		try {
            compareScores("Peter:0.0", scPeter.toString());
            //fail ("Completa el testToString() ");
            scPeter.score(world.getPlayer().getLocation());
            compareScores("Peter:0.0", scPeter.toString());
            scPeter.score(world.getPlayer().getLocation().above());
            compareScores("Peter:1.0", scPeter.toString());
            scPeter.score(world.getPlayer().getLocation().above().above());
            compareScores("Peter:2.0", scPeter.toString());
            scPeter.score(new Location(world,0,90,0));
            compareScores("Peter:24.0", scPeter.toString());
        } catch (Exception e) {
            fail("Error. No debió lanzar la excepción "+e.getClass().getName());
        }
	}

	
	/**********************************************/
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
