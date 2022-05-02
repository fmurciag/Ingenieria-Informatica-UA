package model.score;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.BlockFactory;
import model.ItemStack;
import model.Location;
import model.Material;
import model.World;
import model.entities.Player;
import model.exceptions.score.ScoreException;

public class XPScore_P4Test {

	XPScore xpJulia, xpCharles;
	Player pJulia, pCharles;
	World world;
	CollectedItemsScore cis;
	MiningScore ms;
	PlayerMovementScore pms;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
		world = new World(3,10,"A Little World", "Joan");
		pJulia = new Player("Julia",world);
		pCharles = new Player("Charles",world);
		xpJulia = new XPScore(pJulia);
		xpCharles = new XPScore(pCharles);
	}

	@Test
	public void testXPScoreAndGetName() {
		Score<Player> score = xpJulia; //Implementas la herencia ???
		assertEquals("Julia",score.getName());	
		
		assertEquals("Julia",xpJulia.getName());
		assertEquals("Charles", xpCharles.getName());
	}

	/* Comparar las puntuaciones de xpJulia y de xpCharles modificando solo
	 * el health y el foodLevel
	 */	 
	@Test
	public void testCompareTo1() {
			//Inicialmente ambas puntuaciones son iguales 
			assertTrue(xpJulia.compareTo(xpCharles)==0);
			
			xpCharles.score(pCharles);
			assertTrue(xpJulia.compareTo(xpCharles)==0);
			
			//Modificamos el health de Julia y comprobamos
			pJulia.setHealth(10);
			xpJulia.getScoring();
			assertTrue(xpJulia.compareTo(xpCharles)>0);
			assertTrue(xpCharles.compareTo(xpJulia)<0);
			
			//Modificamos el foodLevel de Charles al mismo valor que
		    //el health de Julia y comprobamos que son iguales
			pCharles.setFoodLevel(10);
			xpCharles.getScoring();
			assertTrue(xpJulia.compareTo(xpCharles)==0);
	}
	
	/* Comparar los XPScore de xpJulia y de xpCharles sin modificar
	 * health y foodLevel pero sí añadiendo a la lista de XPScore de xpJulia 
	 * y xpCharles: un CollectedItemsScore, un MiningScore y unPlayerMovementScore 
	 * sucesivamente.
	 */
	@Test
	public void testCompareTo2() {
		try {
			//Añadimos un ItemScore al marcador de Julia
			cis = new CollectedItemsScore("Julia");
			cis.score(new ItemStack(Material.BREAD,5));
			xpJulia.addScore(cis);
			assertTrue(xpJulia.compareTo(xpCharles)<0);	
			//Añadimos el mismo ItemScore al marcador de Charles
			xpCharles.addScore(cis);
			assertTrue(xpCharles.compareTo(xpJulia)==0);
			
			//Añadimos un MiningScore al marcador de Charles
			ms = new MiningScore("Charles");
			ms.score(BlockFactory.createBlock(Material.GRANITE));
			xpCharles.addScore(ms);
			assertTrue(xpJulia.compareTo(xpCharles)<0);	
			//Añadimos el mismo MiningScore al marcador de Julia
			xpJulia.addScore(ms);
			assertTrue(xpJulia.compareTo(xpCharles)==0);
			
			//Añadimos un PlayerMovementScore al marcador de Julia
			pms = new PlayerMovementScore("Julia");
			pms.score(new Location(world,-3,80,4));
			xpJulia.addScore(pms);
			assertTrue(xpJulia.compareTo(xpCharles)>0);	
			//Añadimos el mismo ItemScore al marcador de Charles
			xpCharles.addScore(pms);
			assertTrue(xpCharles.compareTo(xpJulia)==0);
			
		} catch (Exception e) {
			fail ("Error, no debió lanzar la excepcion "+e.getClass().getName());
		}
	}

	/* Comprobar que inicialmente, sin calcular, xpJulia tiene el marcador a 0. 	 
	 * Calcular el Score inicial de xpJulia, y comprobar que es 40. Modificar
	 * el health y calcular y comprobar el nuevo resultado. Hacer lo mismo con
	 * foodLevel.
	 */
	@Test
	public void testScorePlayer() {
		assertEquals(0, xpJulia.score,0.01);
		
		xpJulia.score(pJulia);
		assertEquals(40, xpJulia.score,0.01);
				
		//Modificamos el health de Julia y comprobamos
		pJulia.setHealth(10);
		xpJulia.score(pJulia);
		assertEquals(30, xpJulia.score,0.01);
		
		pJulia.setFoodLevel(15);
		xpJulia.score(pJulia);
		assertEquals(25, xpJulia.score,0.01);
		
		pJulia.setHealth(-20);
		xpJulia.score(pJulia);
		assertEquals(-5, xpJulia.score,0.01);
	}	
	
	//Se comprueba la excepción ScoreException en el método score
	@Test(expected=ScoreException.class)
	public void testScorePlayerException() {
		Player p = new Player("Marta",world);
		xpJulia.score(p);
	}
	
	
	/* Añadir un CollectedIntemsScore a xpJulia, y comprobar
	 * que lo que obteneis es lo esperado.
	 * Hacer lo mismo con un MininScore y un PlayerMovementScore
	 */
	@Test
	public void testAddScorePlayer() {
		//Añadimos un ItemScore al marcador de Julia
		try {
			cis = new CollectedItemsScore("Julia");
			cis.score(new ItemStack(Material.BREAD,5));
			xpJulia.addScore(cis);
			assertEquals(65, xpJulia.score,0.01);
			
			//Añadimos un MiningScore al marcador de Julia
			ms = new MiningScore("Julia");
			ms.score(BlockFactory.createBlock(Material.GRANITE));
			xpJulia.addScore(ms);
			assertEquals(53.25, xpJulia.score,0.01);
			
			//Añadimos un PlayerMovementScore al marcador de Julia
			pms = new PlayerMovementScore("Julia");
			pms.score(new Location(world,-3,80,4));
			xpJulia.addScore(pms);
			assertEquals(48.83, xpJulia.score,0.01);
			
		} catch (Exception e) {
			fail ("Error, no debió lanzar la excepcion "+e.getClass().getName());
		}
		
	}

	/* Comprobar lo mismo que en el testScorePlayer1 pero con 
	 * getScore()
	 */
	@Test
	public void testGetScoring() {
		assertEquals(40, xpJulia.getScoring(),0.01);
				
		pJulia.setHealth(10);
		assertEquals(30, xpJulia.getScoring(),0.01);
		
		pJulia.setFoodLevel(15);
		xpJulia.score(pJulia);
		assertEquals(25, xpJulia.getScoring(),0.01);
		
		pJulia.setHealth(-20);
		xpJulia.score(pJulia);
		assertEquals(-5, xpJulia.getScoring(),0.01);
	}
	
    /* Repite lo mismo que en testScorePlayer() y testAddScorePlayer pero usando
     * toString() en los asseretEquals
     */
	@Test
	public void testToString() {
		
		assertEquals("Julia:0.0", formatToString(xpJulia.toString(),2));
		
		xpJulia.score(pJulia);
		assertEquals("Julia:40.0", formatToString(xpJulia.toString(),2));
		
		pJulia.setHealth(2);
		xpJulia.score(pJulia);
		assertEquals("Julia:22.0", formatToString(xpJulia.toString(),2));
		
		pJulia.setFoodLevel(7);
		xpJulia.score(pJulia);
		assertEquals("Julia:9.0", formatToString(xpJulia.toString(),2));
		
		pJulia.setHealth(-20);
		xpJulia.score(pJulia);
		assertEquals("Julia:-13.0", formatToString(xpJulia.toString(),2));
		
		try {
			cis = new CollectedItemsScore("Julia");
			cis.score(new ItemStack(Material.BREAD,5));
			xpJulia.addScore(cis);
			assertEquals("Julia:12.0", formatToString(xpJulia.toString(),2));
			
			//Añadimos un MiningScore al marcador de Julia
			ms = new MiningScore("Julia");
			ms.score(BlockFactory.createBlock(Material.GRANITE));
			xpJulia.addScore(ms);
			assertEquals("Julia:0.25", formatToString(xpJulia.toString(),2));
			
			//Añadimos un PlayerMovementScore al marcador de Julia
			pms = new PlayerMovementScore("Julia");
			pms.score(new Location(world,-3,80,4));
			xpJulia.addScore(pms);
			assertEquals("Julia:-4.16", formatToString(xpJulia.toString(),2));
			
		} catch (Exception e) {
			fail ("Error, no debió lanzar la excepcion "+e.getClass().getName());
		}
		
	}

	
	//FUNCIONES DE APOYO

	/* Para las salidas Score.toString() formatea la salida para que
	 * la parte decimal trunque como máximo a 'n' decimales
	 */
	
	String formatToString(String str, int n) {
		String v[]= str.split(":");
		if (v.length==2) {
			String num[] = v[1].split("\\.");
			String dec=""; 
			String ent=num[1];
			n = (num[1].length()<n) ? num[1].length():n;
			if (num.length==2) {
				dec=num[1].substring(0, n);
				ent=num[0];
			}
			String newstr = v[0]+":"+ent+"."+dec;
			return newstr;
		}
		return str;
	}

}
