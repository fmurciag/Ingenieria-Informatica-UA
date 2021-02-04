package model;

import static org.junit.Assert.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.entities.Animal;
import model.entities.Player;
import model.exceptions.BadInventoryPositionException;
import model.exceptions.BadLocationException;
import model.exceptions.EntityIsDeadException;
import model.exceptions.StackSizeException;
import model.exceptions.WrongMaterialException;
import model.score.CollectedItemsScore;
import model.score.MiningScore;
import model.score.PlayerMovementScore;
import model.score.Ranking;
import model.score.XPScore;

public class BlockWorld_PreP4TestPlay {

	
	final static String STARTPLAY1 ="Name=Steve\n" + 
			"Location{world=World 5x5,x=0.0,y=66.0,z=0.0}\n" + 
			"Orientation=Location{world=World 5x5,x=0.0,y=0.0,z=1.0}\n"+
			"Health=20.0\n" + 
			"Food level=20.0\n" + 
			"Inventory=(inHand=(WOOD_SWORD,1),[])\n" + 
			".#. ggg ddd\n" + 
			"... gP@ dgg\n" + 
			"... M.. g..\n"+
			"Scores: [items: 0.0, blocks: 0.0, movements: 0.0]";
	
	final static String HALFPLAY1 ="Name=Steve\n" + 
			"Location{world=World 5x5,x=0.0,y=67.0,z=-1.0}\n" + 
			"Orientation=Location{world=World 5x5,x=0.0,y=0.0,z=1.0}\n"+
			"Health=19.0\n" + 
			"Food level=19.9\n" + 
			"Inventory=(inHand=(WOOD_SWORD,1),[])\n" + 
			"... .M. gg.\n" + 
			"... .P. ggg\n" + 
			"... ... g.@\n" +
			"Scores: [items: 0.0, blocks: 0.0, movements: 1.7320508075688772]";
	
	final static String ENDPLAY1 ="Name=Steve\n" + 
			"Location{world=World 5x5,x=0.0,y=66.0,z=0.0}\n" +
			"Orientation=Location{world=World 5x5,x=0.0,y=0.0,z=1.0}\n"+
			"Health=19.0\n" + 
			"Food level=19.85\n" + 
			"Inventory=(inHand=(WOOD_SWORD,1),[])\n" + 
			".#. ggg ddd\n" + 
			"... gP@ dgg\n" + 
			"... M.. g..\n" +
			"Scores: [items: 0.0, blocks: 0.0, movements: 3.1462643699419726]";
			
	final static String STARTPLAY2 ="Name=Miriam\n" + 
			"Location{world=World 5x5,x=0.0,y=66.0,z=0.0}\n" + 
			"Orientation=Location{world=World 5x5,x=0.0,y=0.0,z=1.0}\n"+
			"Health=20.0\n" + 
			"Food level=20.0\n" + 
			"Inventory=(inHand=(WOOD_SWORD,1),[])\n" + 
			"... ggg ddd\n" + 
			"L.. gP. dgg\n" + 
			"... M.. g..\n" +
			"Scores: [items: 0.0, blocks: 0.0, movements: 0.0]";
			
	final static String HALF1PLAY2 ="Name=Miriam\n" + 
			"Location{world=World 5x5,x=0.0,y=66.0,z=0.0}\n" + 
			"Orientation=Location{world=World 5x5,x=-1.0,y=1.0,z=0.0}\n"+
			"Health=20.0\n" + 
			"Food level=18.0\n" + 
			"Inventory=(inHand=(WOOD_SWORD,1),[])\n" + 
			"... ggg ddd\n" + 
			"F.. gP. dgg\n" + 
			"... M.. g..\n" +
			"Scores: [items: 0.0, blocks: 0.0, movements: 0.0]";
	
	final static String HALF2PLAY2 ="Name=Miriam\n" + 
			"Location{world=World 5x5,x=-1.0,y=67.0,z=0.0}\n" + 
			"Orientation=Location{world=World 5x5,x=-1.0,y=1.0,z=0.0}\n"+
			"Health=20.0\n" + 
			"Food level=17.95\n" + 
			"Inventory=(inHand=(WOOD_SWORD,1),[(BEEF,1)])\n" + 
			"... ... .gg\n" + 
			"... .P. Bg.\n" + 
			"... ... .M.\n" +
			"Scores: [items: 8.0, blocks: 0.0, movements: 0.0]";
	
	final static String HALF3PLAY2 ="Name=Miriam\n" + 
			"Location{world=World 5x5,x=-1.0,y=67.0,z=0.0}\n" + 
			"Orientation=Location{world=World 5x5,x=-1.0,y=1.0,z=0.0}\n"+
			"Health=20.0\n" + 
			"Food level=20.0\n" + 
			"Inventory=(inHand=null,[(WOOD_SWORD,1)])\n" + 
			"... ... .gg\n" + 
			"... .P. Bg.\n" + 
			"... ... .M.\n" +
			"Scores: [items: 8.0, blocks: 0.0, movements: 0.0]";
	
	final static String HALF4PLAY2 ="Name=Miriam\n" + 
			"Location{world=World 5x5,x=-1.0,y=67.0,z=0.0}\n" + 
			"Orientation=Location{world=World 5x5,x=0.0,y=-1.0,z=1.0}\n"+
			"Health=19.5\n" + 
			"Food level=19.9\n" + 
			"Inventory=(inHand=(WOOD_SWORD,1),[])\n" + 
			"... ... .gg\n" + 
			"... .P. Bg.\n" + 
			"... ... .M.\n"
			+ "Scores: [items: 8.0, blocks: 0.0, movements: 0.0]";
	
	final static String ENDPLAY2 ="Name=Miriam\n" + 
			"Location{world=World 5x5,x=-1.0,y=67.0,z=0.0}\n" + 
			"Orientation=Location{world=World 5x5,x=0.0,y=-1.0,z=1.0}\n"+
			"Health=19.5\n" + 
			"Food level=18.0\n" + 
			"Inventory=(inHand=(WOOD_SWORD,1),[])\n" + 
			"... ... .gg\n" + 
			"... .P. Bg.\n" + 
			"... ... ...\n"
			+ "Scores: [items: 8.0, blocks: 0.0, movements: 0.0]";

	final static String STARTPLAY3 ="Name=Carla\n" + 
			"Location{world=World 5x5,x=0.0,y=66.0,z=0.0}\n" + 
			"Orientation=Location{world=World 5x5,x=0.0,y=0.0,z=1.0}\n"+
			"Health=20.0\n" + 
			"Food level=20.0\n" + 
			"Inventory=(inHand=(WOOD_SWORD,1),[])\n" + 
			"... ggg ddd\n" + 
			"... gP. dgg\n" + 
			"... M.. g..\n"
			+ "Scores: [items: 0.0, blocks: 0.0, movements: 0.0]";
	
	final static String HALF1PLAY3 ="Name=Carla\n" + 
			"Location{world=World 5x5,x=0.0,y=66.0,z=0.0}\n" + 
			"Orientation=Location{world=World 5x5,x=0.0,y=0.0,z=1.0}\n"+
			"Health=20.0\n" + 
			"Food level=19.9\n" + 
			"Inventory=(inHand=(WOOD_SWORD,1),[])\n" + 
			"... ggg ddd\n" + 
			"... gP. dgg\n" + 
			"... MG. g..\n"
			+ "Scores: [items: 0.0, blocks: 0.6, movements: 0.0]";
	
	final static String HALF2PLAY3 ="Name=Carla\n" + 
			"Location{world=World 5x5,x=0.0,y=66.0,z=1.0}\n" + 
			"Orientation=Location{world=World 5x5,x=0.0,y=0.0,z=1.0}\n"+
			"Health=20.0\n" + 
			"Food level=19.85\n" + 
			"Inventory=(inHand=(WOOD_SWORD,1),[(GRASS,1)])\n" + 
			"... g.. dgg\n" + 
			"... MP. g..\n" +
			"... ... ...\n"
			+ "Scores: [items: 0.6, blocks: 0.6, movements: 0.0]";

	final static String HALF3PLAY3 ="Name=Carla\n" + 
			"Location{world=World 5x5,x=0.0,y=66.0,z=1.0}\n" + 
			"Orientation=Location{world=World 5x5,x=-1.0,y=0.0,z=1.0}\n"+
			"Health=20.0\n" + 
			"Food level=18.85\n" + 
			"Inventory=(inHand=(GRASS,1),[(WOOD_SWORD,1)])\n" + 
			"... g.. dgg\n" + 
			"... MP. g..\n" +
			"... ggg ...\n"
			+ "Scores: [items: 0.6, blocks: 0.6, movements: 0.0]";

	final static String ENDPLAY3 ="Name=Carla\n" + 
			"Location{world=World 5x5,x=0.0,y=67.0,z=2.0}\n" + 
			"Orientation=Location{world=World 5x5,x=-1.0,y=0.0,z=1.0}\n"+
			"Health=20.0\n" + 
			"Food level=18.30\n" + 
			"Inventory=(inHand=(GRASS,1),[(WOOD_SWORD,1)])\n" + 
			"... ... M..\n" + 
			"... .P. ggg\n" +
			"XXX XXX XXX\n"
			+ "Scores: [items: 0.6, blocks: 0.6, movements: 1.4142135623730951]";
	
	final String kRANKINGPLAYS = "Collected items ranking = [Miriam:8.0, Carla:0.6, Steve:0.0]\n" + 
			"Mining ranking = [Carla:0.6, Steve:0.0]\n" + 
			"Movement ranking = [Miriam:0.0, Carla:1.4142135623730951, Steve:3.1462643699419726]\n" + 
			"Experience ranking = [Miriam:40.166666666666664, Steve:39.89875478998065, Carla:39.17140452079103]";
	
	
	
	Ranking<CollectedItemsScore> itemsRanking;
	Ranking<MiningScore> miningRanking;
	Ranking<PlayerMovementScore> movementRanking;
	Ranking<XPScore> xpRanking;
	XPScore xpScore;	
	CollectedItemsScore itemScore;
	MiningScore miningScore;
	PlayerMovementScore movementScore;
	
	BlockWorld bw;
	World world5x5;
	World world75x75;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Material.rng.setSeed(1L);
		bw = BlockWorld.getInstance();
		itemsRanking = new Ranking<CollectedItemsScore>();
		miningRanking = new Ranking<MiningScore>();
		movementRanking = new Ranking<PlayerMovementScore>();
		xpRanking = new Ranking<XPScore>();
	}
	
	@Test
	public void testPlayers() {
		
		Player player = play1();
		xpScore = new XPScore(player);
		addScoresToRanking(bw.getItemsScore(),bw.getMiningScore(),bw.getMovementScore());
		
		player = play2();
		xpScore = new XPScore(player);
		addScoresToRanking(bw.getItemsScore(),bw.getMiningScore(),bw.getMovementScore());
		
		player = play3();
		xpScore = new XPScore(player);
		addScoresToRanking(bw.getItemsScore(),bw.getMiningScore(),bw.getMovementScore());
		
		System.out.println(rankingToString ());
		compareRankings(kRANKINGPLAYS, rankingToString());
	}
	
	

/* TRAS ESTE TEST, QUE QUEDA COMO EJERCICIO, PUEDES ENCONTRAR 
 * PARTE DE LA SUPERFICIE DONDE APARECE EL PLAYER EN EL MUNDO 
 * "A DANGEROUS WORLD". A PARTIR DE AHI PUEDES ELABORAR PARTIDAS 
 * PARA LOS DISTINTOS PLAYERS, AÑADIR SUS SCORES A LOS RANKINGS Y 
 * COMPROBAR QUE OS FUNCIONA. ESAS PARTIDAS LAS PUEDES CREAR EN 
 * LOS DISTINTOS MÉTODOS QUE ESTÁN TAMBIÉN A CONTINUACIÓN.
 */
	@Test
	public void testRankigInDangerousPlanet() throws RuntimeException {
		/* Fíjate en los test anteriores */
		//TODO
		fail("Realiza el testRankigInDangerousPlanet() ");
		
	}	
	
	


/******************************************************************************
 *                   GAMES
 *****************************************************************************/
/* INFORMACIÓN SOBRE EL MUNDO "A DANGEROUS WORLD" DE 75x75
 * SUPERFICIE REPRESENTADA: [x,62,z] para  (-10 <= x <= 10) (-10 <= z <= 10)
 * LAS LETRAS DE LOS BLOQUES EN MAYUSCULAS REPRESENTAN BLOQUES CON ITEMS: 
 * 	'G' es un bloque GRASS con 1 Item GRASS
 *  'g' es un bloque GRASS sin item
 *  Player en (0,62,0)
 
[x,62,z]
@@@A@GGGgggggGDDddDDd
@@@F@@gGgggGGGGddDddD
@@@@@@@GgG@GgGggDDDdD
@@@@@@@GGg@@@@@GGgddD
@@@@@@@@AGG@@@MGgGDDd
@@@@@@@@@@@@@@LM@gdDD
@@@@@@@@@@@@@@@@@@GDD
@@@@@@@@@@B@@@@@M@ggD
@@@@@@@@@>@@@@@B>LGGD
@@@@@@@@@@@@LF@@@GGgG
@@@@@@@@@@PM@@@@MgGGG
@@@@@@@@M@B@@@@@@@@gg
@@@@@@@@@@@@@@@@@@@gG
@@@@@@@@@@@@@@@@@@@@G
@@@@@@@@@@@@@@@M@@@gG
@@@@@@@@@@@@@@@@@@Wgg
@@@@@@@@@@@@@@@@@@GgG
@@@@@@@@@@@@@@@@@ggGg
@@@@@@@@@@@@@@@AGggGd
@@@@@@@@@@@@@@@@gGGgD
@@@@@@@@@@@@@@@@gGGDd

*/
private Player playRobert() {
	world75x75 =  bw.createWorld(5, 75, "A Dangerous World","Robert");
	Player ply = world75x75.getPlayer();
	//TODO
	/* CREA UNA PARTIDA PARA ROBERT */
	fail("Implementa una partida para Robert");
	
	return ply;
}

	private Player playSandra() {
		world75x75 =  bw.createWorld(5, 75, "A Dangerous World","Sandra");
		Player ply = world75x75.getPlayer();
		//TODO
		fail("Implementa una partida para Sandra ");
		return ply;
	}
	
	private Player playLucas() {
		world75x75 =  bw.createWorld(5, 75, "A Dangerous World","Lucas");
		Player ply = world75x75.getPlayer();
		
		//TODO 
		fail("Implementa una partida para Lucas");
		return (ply);
	}
	
	private Player playHelen() {
		//Material.rng.setSeed(1L);
		world75x75 =  bw.createWorld(5, 75, "A Dangerous World","Helen");
		Player ply = world75x75.getPlayer();
		//TODO
		fail("Implementa una partida para Helen");
		return (ply);
	}
	
/***************************************************	
	PARTIDAS YA IMPLEMENTADAS
****************************************************/

private Player play1() throws RuntimeException {
	//  - añadir agua, lava
	//  - meterse en el charco de agua y ver que no pasa nada
	//  - meterse en el charco de lava y ver que hace pupita

	
	/* INICIO PARTIDA (mundo generado)
	 Location{world=World 5x5,x=0.0,y=66.0,z=0.0}
	... ggg ddd
	... gP. dgg
	... M.. g.. 

	después de añadir agua y lava
	 .#. ggg ddd 
	 ... gP@ dgg 
	 ... M.. g..
	
	*/
	
	
	Material.rng.setSeed(1L);
	world5x5 =  bw.createWorld(5, 5, "World 5x5","Steve");
	//bw.showPlayerInfo(world5x5.getPlayer());
	Player ply = world5x5.getPlayer();
	
	//  - añadir agua, lava
	Location plyloc = ply.getLocation();
	Location agua = new Location(plyloc);
	agua.setX(agua.getX()+1);  // poner agua a la derecha del jugador

	Location lava = new Location(plyloc);
	lava.setZ(lava.getZ()-1);  // poner lava arriba
	lava.setY(lava.getY()+1);  // subiendo una posición porque hay grass

	try {
		world5x5.addBlock(agua, BlockFactory.createBlock(Material.WATER));
		world5x5.addBlock(lava, BlockFactory.createBlock(Material.LAVA));
	} catch (BadLocationException e2) {
		fail("Excepción "+e2.getClass().getName()+" no esperada");
	} catch (WrongMaterialException e2) {
		fail("Excepción "+e2.getClass().getName()+" no esperada");
	}
	System.out.println(bw.showPlayerInfo(world5x5.getPlayer()));
	
	
	compareLines(STARTPLAY1,bw.showPlayerInfo(world5x5.getPlayer()));

	try {
		// chapuzón en el agua
		bw.movePlayer(ply, 1, 0, 0);
		assertEquals("al agua patos",20.0,ply.getHealth(),0.001);
		// chapuzón en lava (pupita)
		bw.movePlayer(ply,-1,1,-1);
		assertEquals("lava (pupita)",19.0,ply.getHealth(),0.001);
		compareLines(HALFPLAY1,bw.showPlayerInfo(world5x5.getPlayer()));

		// volvemos a posicion inicial
		bw.movePlayer(ply,0,-1,1);
		
		compareLines(ENDPLAY1,bw.showPlayerInfo(world5x5.getPlayer()));
		
		
	} catch (EntityIsDeadException e) {
		fail("Excepción "+e.getClass().getName()+" no esperada");
	} catch (BadLocationException e) {
		fail("Excepción "+e.getClass().getName()+" no esperada");
	}
	return ply;
}


private Player play2() throws RuntimeException {
	//  - matar animal y coger BEEF
	//  - hacer pupita al monstruo y aguantar el contraataque
	//  - rematar al monstruo

	/* INICIO PARTIDA (mundo generado)
	 Location{world=World 5x5,x=0.0,y=66.0,z=0.0}
	... ggg ddd
	... gP. dgg
	... M.. g.. 
	
	Después de añadir el animal
	... ggg ddd
	L.. gP. dgg
	... M.. g.. 
	*/
	Material.rng.setSeed(1L);
	world5x5 =  bw.createWorld(5, 5, "World 5x5","Miriam");
	Player ply = world5x5.getPlayer();
	
	//  - añadir animal
	Location plyloc = ply.getLocation();
	Location animal = new Location(plyloc);
	animal.setX(animal.getX()-1);  // animal a la izquierda 
	animal.setY(animal.getY()+1);  // subiendo
	
	try {
		world5x5.addCreature(new Animal(animal, 2));
		
		compareLines(STARTPLAY2,bw.showPlayerInfo(world5x5.getPlayer()));
		
		// orientar el jugador hacia el animal
		bw.orientatePlayer(ply, -1, 1, 0);
		bw.useItem(ply, 20);   // muerto matao  
	
		compareLines(HALF1PLAY2,bw.showPlayerInfo(world5x5.getPlayer()));
		
		// cogemos la carne, que para eso lo hemos matao
		bw.movePlayer(ply, -1, 1, 0);
		
		compareLines(HALF2PLAY2,bw.showPlayerInfo(world5x5.getPlayer()));

		// a comerrrr
		bw.selectItem(ply, 0);
		bw.useItem(ply, 1);
		
		compareLines(HALF3PLAY2,bw.showPlayerInfo(world5x5.getPlayer()));
		
		// cogemos la espada, que si no va a ser complicao matar al bisho
		bw.selectItem(ply, 0);
		// reorientamos al jugador
		bw.orientatePlayer(ply, 0, -1, 1);
		
		// le damos un toque al bisho pa'ver si está vivo (y él responde)
		bw.useItem(ply, 1);
	
		compareLines(HALF4PLAY2,bw.showPlayerInfo(world5x5.getPlayer()));
		
		// rematamos, pero lo justo, sin saña
		bw.useItem(ply,19);
		
		compareLines(ENDPLAY2,bw.showPlayerInfo(world5x5.getPlayer()));
		
	} catch (BadLocationException | EntityIsDeadException e2) {
		fail("Excepción "+e2.getClass().getName()+" no esperada");
	} catch (BadInventoryPositionException e) {
		fail("Excepción "+e.getClass().getName()+" no esperada");
	}
	return ply;
	//System.out.println(" test 2:"+bw.showPlayerInfo(ply));

}


private Player play3() throws RuntimeException {
	
	//  - useItem  con bloque (GRASS) y romperlo, y coger item (G)
	//  - useItem con el G cogido para añadir un nuevo bloque
	//  - intentar crear el nuevo bloque fuera del mundo

	/* INICIO PARTIDA
	 Location{world=World 5x5,x=0.0,y=66.0,z=0.0}
	... ggg ddd
	... gP. dgg
	... M.. g.. 
	*/
	Material.rng.setSeed(1L);
	world5x5 =  bw.createWorld(5, 5, "World 5x5","Carla");
	Player ply = world5x5.getPlayer();
	
	compareLines(STARTPLAY3,bw.showPlayerInfo(world5x5.getPlayer()));
	
	try {
		// creamos un bloque y le ponemos un 'drops' para que el bloque suelte hierba
		Location locgrass = new Location(ply.getLocation());
		locgrass.setZ(locgrass.getZ()+1);
		SolidBlock sb = (SolidBlock)BlockFactory.createBlock(Material.GRASS);
		sb.setDrops(Material.GRASS,1);
		world5x5.addBlock(locgrass, sb);
			
		// orientamos al sur para cortar hierba con la espada de madera
		// bw.orientatePlayer(ply, 0, 0, 1);  // no hace falta orientar al jugador, ya debe estar orientado al sur
		bw.useItem(ply,1);  // romper el bloque de hierba
		
		compareLines(HALF1PLAY3,bw.showPlayerInfo(world5x5.getPlayer()));
		
		bw.movePlayer(ply,0,0,1); // pillamos la hierba (G)
		
		compareLines(HALF2PLAY3,bw.showPlayerInfo(world5x5.getPlayer()));
		
		bw.selectItem(ply, 0);  // seleccionamos la hierba para empezar a plantar
		bw.useItem(ply,7);      // plantamos tres bloques debajo (sur)  (ojo, consume (7+2+1)*0.1 = 1 comida)
		bw.orientatePlayer(ply,1,0,1);
		bw.useItem(ply,2);      // plantamos un bloque
		bw.orientatePlayer(ply,-1,0,1);
		bw.useItem(ply,1);      // plantamos un bloque
		
		compareLines(HALF3PLAY3,bw.showPlayerInfo(world5x5.getPlayer()));
		
		bw.movePlayer(ply,0,1,1); // nos movemos encima de la hierba para llegar al límite del mundo
		bw.useItem(ply,5);  // intentamos plantar fuera del mundo, sólo debe llamar a Player.useItemInHand y quitar comida
		
		compareLines(ENDPLAY3,bw.showPlayerInfo(world5x5.getPlayer()));
		
		
	} catch (EntityIsDeadException e) {
		fail("Excepción "+e.getClass().getName()+" no esperada");
	} catch (BadLocationException e) {
		fail("Excepción "+e.getClass().getName()+" no esperada");
	} catch (WrongMaterialException e) {
		fail("Excepción "+e.getClass().getName()+" no esperada");
	} catch (StackSizeException e) {
		fail("Excepción "+e.getClass().getName()+" no esperada");
	} catch (BadInventoryPositionException e) {
		fail("Excepción "+e.getClass().getName()+" no esperada");
	}
	return ply;
	//System.out.println(" test 3:"+bw.showPlayerInfo(ply));
}	

/******************************************************************/
//FUNCIONES DE APOYO
/* Se usa para comparar Strings que devuelven algunos métodos. Evita sacar error por los espacios
* finales de cada línea y por los saltos de línea al final
*/
private void  compareLines(String expected, String result) {
	String exp[]=expected.split("\n");
	String res[]=result.split("\n");
	boolean iguales = true;
	if (exp.length!=res.length) 
		fail("Cadena esperada de tamaño ("+exp.length+") distinto a la resultante ("+res.length+")");
	for (int i=0; i<exp.length && iguales; i++) {
		
		// si la línea es Health=1.2345  o Food level=1.2345  
		// se extraen los números y se comparan con 0.01 de margen
		
		if (i == exp.length-1) { //Analiza la última línea de los Scores
			String sexp = exp[i].substring(7);
			String sres = res[i].substring(7);
			compareRankings(sexp,sres);
		}
		else {
			String ee[]=exp[i].split("=");
			if (ee[0].equals("Health") || ee[0].equals("Food level")) {
				String rr[]=res[i].trim().split("=");
				if (ee[0].equals(rr[0])) {
					double ed = Double.parseDouble(ee[1]);
					double rd = Double.parseDouble(rr[1]);
				
					assertEquals(ee[0],ed,rd,0.01);
				}
			}
			else {
            String myexp=exp[i].trim().replaceAll("world= ", "world=");
            String myres=res[i].trim().replaceAll("world= ", "world=");
            assertEquals("linea "+i, myexp,myres);
         }
		}
	}
}
	
/* Se usa para comparar los valores impresos de los rankings con una precisión 
* de 0.01
* [Sara:1.4142135623730951, Julia:14.866068747318506]"
*/
private void compareRankings(String expected, String result) {
	String exp[]=expected.split("\n");
	String res[]=result.split("\n");
	boolean iguales = true;
	for (int i=0; i<exp.length && iguales; i++) {
		
		String exp1[]=exp[i].split(", ");
		String res1[]=res[i].split(", ");
		if (exp1.length!=res1.length) 
			fail("Error: cantidad de jugadores distintos en el ranking "+res[i]);
		for (int k=0; k<exp1.length; k++) { //Para cada Score
			if (k==exp1.length-1) //Es el último Score
				compareScores(exp1[k].substring(0, exp1[k].indexOf(']')).trim(),res1[k].substring(0, res1[k].indexOf(']')).trim());
			else 
				compareScores(exp1[k].trim(),res1[k].trim());
			
		}
	}
}

/* Para las salidas Score.toString() compara los valores impresos
* de los Scores hasta una precisión de 0.01
* 
*/
private void compareScores(String expected, String result ) {
	String ex[]= expected.split(":");
	String re[]= result.split(":");
	if (ex.length!=re.length) fail("Lineas distintas");
	if (ex.length==2) {
		if (ex[0].trim().equals(re[0].trim())) {
			double ed = Double.parseDouble(ex[1]);
			double rd = Double.parseDouble(re[1]);
	
			assertEquals(ex[0],ed,rd,0.01);
		}
		else fail("Nombres jugadores distintos");
	}
	else
		assertEquals(expected.trim(),result.trim());		
}

/* Función que cambia la salida standard (out) a un fichero*/
public static PrintStream standardIO2File(String fileName){
  if(fileName.equals("")){//Si viene vacío usamos este por defecto
      fileName="pretest/files/testPlayFileStandard.txt";
  }
  PrintStream ps=null;
  try {
      //Creamos un printstream sobre el archivo permitiendo añadir al
      //final para no sobreescribir.
      ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File(fileName),false)),true);
      //Redirigimos entrada 
      System.setOut(ps);
     // System.setErr(ps);
  } catch (FileNotFoundException ex) {
      System.err.println("Se ha producido una excepción FileNotFoundException");
  }
  return ps;
}



/* Añade los distintos valores de los Scores a los Ranking*/
private void addScoresToRanking (CollectedItemsScore is, MiningScore ms, PlayerMovementScore pms) {
xpScore.addScore(is); 
xpScore.addScore(ms); 
xpScore.addScore(pms);
itemsRanking.addScore(is);
miningRanking.addScore(ms);
movementRanking.addScore(pms);
xpRanking.addScore(xpScore);
}

/* Construye la salida de los distintos Rankigs a String*/
private String rankingToString () {
	StringBuilder ps = new StringBuilder();
	ps.append("Collected items ranking = "+itemsRanking.getSortedRanking()+"\n");
	ps.append("Mining ranking = "+miningRanking.getSortedRanking()+"\n");
	ps.append("Movement ranking = "+movementRanking.getSortedRanking()+"\n");
	ps.append("Experience ranking = "+xpRanking.getSortedRanking());
	return ps.toString();
}


}

