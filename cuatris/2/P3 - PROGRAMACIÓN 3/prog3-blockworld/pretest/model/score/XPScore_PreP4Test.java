package model.score;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.BlockFactory;
import model.ItemStack;
import model.Location;
import model.Material;
import model.World;
import model.entities.Player;
import model.exceptions.StackSizeException;
import model.exceptions.score.ScoreException;
import sun.awt.windows.WPrinterJob;

public class XPScore_PreP4Test {

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
	//TODO
	@Test
	public void testCompareTo1() {
			//Inicialmente ambas puntuaciones son iguales 
			assertTrue(xpJulia.compareTo(xpCharles)==0);
			xpJulia.score(pJulia);
			xpCharles.score(pCharles);
			assertTrue(xpJulia.compareTo(xpCharles)==0);
			
			/* Modifica el health de Julia y comprueba que
			 * si xpJulia invoca a compareTo, devuelve un valor >0
			 * al ser el score de xpJulia menor
			 */	
			pJulia.setHealth(10);
			xpJulia.score(pJulia);
			assertTrue(xpJulia.compareTo(xpCharles)>0);
			//fail("Realiza lo indicado");
			
			/* Modifica el foodLevel de Charles al mismo valor que
			 * el health de Julia y comprueba que compareTo devuelve 0
			 */
			pCharles.setHealth(10);
			xpCharles.score(pCharles);
			assertTrue(xpJulia.compareTo(xpCharles)==0);
			//fail("Realiza lo indicado");
	}
	
	/* Comparar los XPScore de xpJulia y de xpCharles sin modificar
	 * health y foodLevel pero sí añadiendo a la lista de XPScore de xpJulia 
	 * y xpCharles: un CollectedItemsScore, un MiningScore y un PlayerMovementScore 
	 * sucesivamente.
	 */
	//TODO
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
			
			ms=new MiningScore("Julia");
			ms.score(BlockFactory.createBlock(Material.OBSIDIAN) );
			xpJulia.addScore(ms);
			//assertTrue(xpJulia.compareTo(xpCharles)<0);	
			xpCharles.addScore(ms);
			assertTrue(xpCharles.compareTo(xpJulia)==0);
			
			pms=new PlayerMovementScore("Julia");
			Location l=new Location(pJulia.getLocation());
			l.setX(l.getX()+1);
			pms.score(l);
			xpJulia.addScore(pms);
			//assertTrue(xpJulia.compareTo(xpCharles)>0);	
			xpCharles.addScore(pms);
			assertTrue(xpCharles.compareTo(xpJulia)==0);
			
			//fail ("Realiza lo mismo pero con MiningScore y PlayerMovementScore");
			
		} catch (Exception e) {
			fail ("Error, no debió lanzar la excepcion "+e.getClass().getName());
		}
	}

	/* Comprobar que inicialmente, sin calcular, xpJulia tiene el marcador a 0. 	 
	 * Calcular el Score inicial de xpJulia, y comprobar que es 40. Modificar
	 * el health y calcular y comprobar el nuevo resultado. Hacer lo mismo con
	 * foodLevel.
	 */
	//TODO
	@Test
	public void testScorePlayer() {
		assertEquals(0, xpJulia.score,0.01);
		
		xpJulia.score(pJulia);
		assertEquals(40, xpJulia.score,0.01);
		
		pJulia.setHealth(5);
		xpJulia.score(pJulia);
		assertEquals(25, xpJulia.score,0.01);
		
		pJulia.setFoodLevel(10);
		xpJulia.score(pJulia);
		assertEquals(15, xpJulia.score,0.01);
				
		//fail("Terminal el testScorePlayer() ");
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
	//TODO
	@Test
	public void testAddScorePlayer() {
        //fail("Realizar testAddScorePlayer() ");
        try {
            cis= new CollectedItemsScore(pJulia.getName());
            cis.score(new ItemStack(Material.BREAD,1));
        } catch (StackSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        xpJulia.addScore(cis);
        assertEquals(45,xpJulia.score,0.01);
    }
	/* Comprobar que inicialmente, sin calcular, xpJulia tiene el marcador a 0. 	 
	 * Calcular el Score inicial de xpJulia, y comprobar que es 40. Modificar
	 * el health y calcular y comprobar el nuevo resultado. Hacer lo mismo con
	 * foodLevel.
	 *
	 * Comprobar lo mismo que en el testScorePlayer1 pero con 
	 * getScore()
	 */
	//TODO
	@Test
	public void testGetScoring() {
		assertEquals(0, xpJulia.score,0.01);
		
		xpJulia.score(pJulia);
		assertEquals(40, xpJulia.score,0.01);
		
		pJulia.setHealth(10);
		assertEquals(30, xpJulia.getScoring(),0.01);
		
		pJulia.setFoodLevel(10);
		xpJulia.score(pJulia);
		assertEquals(20, xpJulia.getScoring(),0.01);
	    //fail("Realiza el testGetScoring()");
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

	/* Repite lo mismo que en testScorePlayer() y testAddScorePlayer pero usando
	 * toString() en los asseretEquals
	 */
	//TODO
	@Test
	public void testToString() {

        compareScores("Julia:0.0", xpJulia.toString());

        xpJulia.score(pJulia);
        assertEquals("Julia:40.0", xpJulia.toString());

        //fail ("Completa modificando health y foodLevel");
        pJulia.setHealth(5);
        xpJulia.score(pJulia);
        assertEquals("Julia:25.0", xpJulia.toString());

        pJulia.setFoodLevel(12.5);
        xpJulia.score(pJulia);
        assertEquals("Julia:17.5", xpJulia.toString());

        try {
            cis= new CollectedItemsScore(pJulia.getName());
            cis.score(new ItemStack(Material.BREAD,1));
        } catch (Exception e) {
            fail ("Error, no debió lanzar la excepcion "+e.getClass().getName());
        }
        xpJulia.addScore(cis);
        assertEquals("Julia:22.5", xpJulia.toString());
    }
	
}
