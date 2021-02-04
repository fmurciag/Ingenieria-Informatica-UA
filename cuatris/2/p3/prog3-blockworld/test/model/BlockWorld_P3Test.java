package model;

import static org.junit.Assert.*;
import static org.junit.Assume.assumeTrue;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.entities.Animal;
import model.entities.Creature;
import model.entities.LivingEntity;
import model.entities.Monster;
import model.entities.Player;

/*INFORMACIÓN INICIAL DEL PLAYER EN EL MUNDO WORLD10X10
 * 
 * world10x10 =  bw.createWorld(5, 10, "World 10x10")

Name=Steve
Location{world=World 10x10,x=0.0,y=65.0,z=0.0}
Orientation=Location{world=World 10x10,x=0.0,y=0.0,z=1.0}
Health=20.0
Food level=20.0
Inventory=(inHand=(WOOD_SWORD,1),[])
... ... ggg
... .P. ggg
... ... Mg.
*/
public class BlockWorld_P3Test {

	BlockWorld bw;
	World world10x10;
	Player player;

	static String testPathPrefix = "pretest";
	
	static Class<?> UGEClass;
	
	@BeforeClass
	public static void setupClass() {
		// try to find UnknownGameException class at runtime
		try {
			UGEClass = Class.forName("model.exceptions.UnknownGameCommandException");
		} catch (ClassNotFoundException e) {
			// UnknownGameException not present in classpath, assume UGEClass == null
		}		
	}
	
@Before
public void setUp() throws Exception {
	Material.rng.setSeed(1L);
	bw = BlockWorld.getInstance();
	world10x10 =  bw.createWorld(5, 10, "World 10x10", "Steve");
	player = world10x10.getPlayer();
}

/*Se añaden bloques líquidos y se mueve el player atravesándolos todos
  @#. ... ggg\n 
  @#. .P. ggg\n 
  @#. ... Mg. 
  Se comprueba que el player tiene 17 puntos de vida y 19,7 de alimento
 */
 @Test
 public void testMovePlayer() {
	addLiquidBlocks();
	try {
		assertEquals(LivingEntity.MAX_HEALTH, player.getHealth(),0.001);
		assertEquals(Player.MAX_HEALTH, player.getFoodLevel(),0.001);
		bw.movePlayer(player, 0, 1, 0);
		bw.movePlayer(player, 0, 0, -1); 
		bw.movePlayer(player, -1, 0, 0); 
		bw.movePlayer(player, 0, 0, 1); 
		bw.movePlayer(player, 0, 0, 1); 
		bw.movePlayer(player, 1, 0, 0);
		assertEquals(LivingEntity.MAX_HEALTH-3, player.getHealth(),0.001);
		assertEquals(Player.MAX_FOODLEVEL-0.3, player.getFoodLevel(),0.001);
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	} 
}

 /* Se añaden items de Granito y Animales
 RL. ... ggg
 RL. .P. ggg
 RL. ... Mg.
 Se orienta al player justo al animal que tiene encima y el player usa
 el WOOD_SWORD 10 veces y se comprueba que el animal no ha muerto y que 
 tiene 10 puntos de vida. El player vuelve a usar otras 10 veces el arma
 matando al animal y se comprueba que el animal ya no está y que hay en 
 su lugar 1 item de BEEF 
 */
@Test
public void testUseItemAgainstAnimal() {
	try {
		ItemStack is = new ItemStack(Material.BEEF,1);
		addItemsAndCreatures(new ItemStack(Material.GRANITE,1), 'A');
		
		bw.orientatePlayer(player, 0, 1, 0);
		Location loc =player.getOrientation();
		bw.useItem(player, 10);
		assertEquals(10, world10x10.getCreatureAt(loc).getHealth(),0.001);
		bw.useItem(player, 10);
		assertNull(world10x10.getCreatureAt(loc));
		assertEquals(is,world10x10.getItemsAt(loc));
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}
	
/* 
... ... ggg
... .P. ggg
... ... Mg.
Se orienta al player justo al bloque de GRASS que tiene debajo y el player
actúa sobre él con el WOOD_SWORD 1 vez. Se comprueba que el bloque ya no está.
*/
 @Test
 public void testUseItemAgainstBlock1() {
	try {
			
			bw.orientatePlayer(player, 0, -1, 0);
			Location loc =player.getOrientation();
			assertNotNull(world10x10.getBlockAt(loc));
			bw.useItem(player, 1);
			assertNull(world10x10.getBlockAt(loc));
		
		} catch (Exception e) {
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
		}
	}
 /* 
 Se crea un bloque de OBSIDIAN con un item de OBSIDIAN y se pone dentro del mundo
 world10x10, justo en la posición a la que está orientado el player. Éste usa 
 el WOOD_SWORD 4 veces. Se comprueba que el bloque sigue estando.
 El player vuelve a actuar 5 veces y se comprueba que ya no está, pero que en su lugar
 está un item de OBSIDIAN.
*/
@Test
public void testUseItemAgainstBlock2() {
	try {
		ItemStack is = new ItemStack(Material.OBSIDIAN,1);
		SolidBlock block = (SolidBlock)BlockFactory.createBlock(Material.OBSIDIAN);
		block.setDrops(Material.OBSIDIAN, 1);			
		bw.orientatePlayer(player, 0, -1, 0);
		Location loc = player.getOrientation();
		world10x10.addBlock(loc, block);
		assertNotNull(world10x10.getBlockAt(loc));
		bw.useItem(player, 4);
		assertNotNull(world10x10.getBlockAt(loc));
		bw.useItem(player, 5);
		assertNull(world10x10.getBlockAt(loc));
		assertEquals(is, world10x10.getItemsAt(loc));
	
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}
		
/* 
 ... ... ggg
 ... .P. ggg
 ... ... Mg.
 El player se orienta hacia el suroeste del plano inferior frente al monster. Se le
 pone 10 puntos de vida. El player ataca con la WOOD_SWORD 5 veces. Como el monster
 no muere, deberá atacar al player 5 veces.
 Se comprueba que el player tiene 17.5 puntos de vida y el monster 5
 El player ataca de nuevo 5 veces y mata al monster. Se comprueba que es así.
*/
@Test
public void testUseItemAgainstMonster() {
	try {
		bw.orientatePlayer(player, -1, -1, 1);
		Location loc = player.getOrientation();
		Creature monster = world10x10.getCreatureAt(loc);
		assertNotNull(monster);
		assertTrue(monster.getSymbol()=='M');
		monster.setHealth(10);
		
		bw.useItem(player, 5);
		assertEquals(5,monster.getHealth(),0.01);
		assertEquals(17.5, player.getHealth(),0.01);
		
		bw.useItem(player, 5);
		assertNull(world10x10.getCreatureAt(loc));
		assertEquals(17.5, player.getHealth(),0.01);
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}

/* 
 Se crea un item SAND y se pone justo en la posición a la que está 
 orientado el player. El player se mueve a esa posición y coge el item SAND
 Se comprueba que es así. Se orienta el player hacia la posición que tiene encima
 y se pone en su mano el item SAND. Actua con él sobre la posición vacía. 
 Deberá crear un bloque de tipo SAND en dicha posición. Se comprueba que ha sido
 así.
*/
@Test
public void testUseItemWithMaterialBlock() {
	try {
		ItemStack is = new ItemStack(Material.SAND,1);
		SolidBlock block = (SolidBlock)BlockFactory.createBlock(Material.SAND);
		
		Location loc = player.getOrientation();
		world10x10.  addItems(loc, is);
		assertNotNull(world10x10.getItemsAt(loc));
		bw.movePlayer(player, 0, 0, 1);
		assertEquals(1, player.getInventorySize());
		player.selectItem(0);
		bw.orientatePlayer(player, 0, 1, 0);
		loc=player.getOrientation();
		bw.useItem(player, 3);
		assertEquals(block,world10x10.getBlockAt(loc));
		block = (SolidBlock)world10x10.getBlockAt(loc);
		assertNull(block.getDrops());
	
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}
	
/* 
 Se crea un item STONE y se añade al inventario del player. El player se orienta 
 hacia el norte sobre el plano inferior, donde hay un bloque de tipo  GRASS. 
 El player toma el item STONE sobre su mano y actúa con él 5 veces.
 Se comprueba que no ha eliminado el bloque. El player vuelve a actuar 7 veces y 
 se comprueba que ahora sí se ha eliminado el bloque GRASS. Si el player actúa
 de nuevo, pondrá un bloque STONE en esa posición.
*/	
@Test
public void testUseItemSolidBlockAgainstBlock() {
		
	try {
		ItemStack is = new ItemStack(Material.STONE,1);
		SolidBlock block = (SolidBlock)BlockFactory.createBlock(Material.STONE);
		player.addItemsToInventory(is);
		bw.orientatePlayer(player, 0, -1, -1);
		Location loc = player.getOrientation();
		
		player.selectItem(0);
		bw.useItem(player, 5);
		assertTrue(world10x10.getBlockAt(loc).getType()==Material.GRASS);
		bw.useItem(player, 7);
		assertNull(world10x10.getBlockAt(loc));
		bw.useItem(player, 1);
		assertEquals(block,world10x10.getBlockAt(loc));
	
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}
	
/* 
 Se crea un item WATER y lo se añade al inventario del player. Se orienta al 
 player hacia el norte sobre el plano inferior, donde hay un bloque de tipo
 GRASS. El player coge el item WATER y lo pone en su mano. Lo usa 5 veces.
 Se comprueba que no ha eliminado el bloque. Vuelve a actuar 7 veces y se comprueba
 que ahora sí se ha eliminado el bloque GRASS. El player usa el item de su mano 
 de nuevo, y pone un bloque STONE en esa posición.
*/
/* @Test
public void testUseItemLiquidBlockAgainstBlock() {
	try {
		ItemStack is = new ItemStack(Material.WATER,1);
		LiquidBlock block = (LiquidBlock)BlockFactory.createBlock(Material.WATER);
		player.addItemsToInventory(is);
		bw.orientatePlayer(player, 0, -1, -1);
		Location loc = player.getOrientation();
		
		player.selectItem(0);
		bw.useItem(player, 5);
		assertTrue(world10x10.getBlockAt(loc).getType()==Material.GRASS);
		bw.useItem(player, 7);
		assertNull(world10x10.getBlockAt(loc));
		bw.useItem(player, 1);
		assertEquals(block,world10x10.getBlockAt(loc));
	
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}
*/

/* 
El player se mueve al límite del mundo en la posición (-4,0,0)
Se orienta hacia el oeste, y usa el WOOD_SWORD que lleva  en la mano 10 veces. 
Se comprueba que no ha ocurrido nada, tan solo se han consumido 1.2 puntos de 
foodLevel (0.2 de los movimientos y 1 punto del uso de WOOD_SWORD)
*/
@Test
public void testUseItemInOutOfWorld() {
	try {
		assertEquals(Player.MAX_FOODLEVEL,player.getFoodLevel(),0.001);
		assertEquals(LivingEntity.MAX_HEALTH,player.getHealth(),0.001);
		for (int i=0; i<4; i++)
			bw.movePlayer(player, -1, 0, 0);
		bw.orientatePlayer(player, -1, 0, 0);
		bw.useItem(player, 10);
		assertEquals(Player.MAX_FOODLEVEL-1.2,player.getFoodLevel(),0.001);
		assertEquals(LivingEntity.MAX_HEALTH,player.getHealth(),0.001);
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}
	
/* Prueba de apertura de un fichero que no existe. Debe saltar la excepción
 * FileNotFoundException
 */
@Test(expected=FileNotFoundException.class)
public void testPlayFileException() throws FileNotFoundException {
	bw.playFile("files/NoFileExist.dat");		
}

/* Test de lectura de datos y órdenes de un mundo, tanto válidos como no
 * válidos. Se compara finalmente la salida de la ejecución del método playFile
 * de alumno con la solución final. 
 */
@Test
public void testPlayFileEjemplo() {
		// test sin UnknownGameException
		assumeTrue(UGEClass == null);
        BlockWorld bword = BlockWorld.getInstance();
        PrintStream ps=standardIO2File(testPathPrefix+"/files/testPlayFileEjemplo.alu");
        try {
                bword.playFile(testPathPrefix+"/files/testPlayFileEjemplo.txt");
        } catch (Exception e) {
                fail("Error: no debió lanzar la excepción "+e.getClass().toString());
        }
        //Volvemos a poner la consola como salida standard
        ps.close();
        System.setOut(System.out);
        StringBuilder sbAlu = readFromFile(testPathPrefix+"/files/testPlayFileEjemplo.alu");
        StringBuilder sbSol = readFromFile(testPathPrefix+"/files/testPlayFileEjemplo.sol");
        String strAlu = sbAlu.toString();
        String strSol = sbSol.toString();
        int linesMatch = compareLines(strSol,strAlu);
        if (linesMatch != strSol.split("\n").length) {
                // try alternative solution
                sbSol = readFromFile(testPathPrefix+"/files/testPlayFileEjemploBis.sol");
                strSol = sbSol.toString();
                linesMatch = compareLines(strSol,strAlu);
                if (linesMatch != strSol.split("\n").length) {
                        //System.out.println("linesMatch="+linesMatch+", strSol.length()="+strSol.length());
                        fail("La salida no coincide:\n\t"+testPathPrefix+"/files/testPlayFileEjemplo.alu != "+testPathPrefix+"/files/testPlayFileEjemplo.sol\n\ten línea "+(linesMatch+1));
                }
        }
}

/* Test de lectura de datos y órdenes de un mundo, tanto válidos como no
 * válidos. Se compara finalmente la salida de la ejecución del método playFile
 * de alumno con la solución final.
 * 
 *  Sólo para implementaciones con UnknownGameException
 */
@Test
public void testPlayFileEjemploBis() {
		// test con UnknownGameException
		assumeTrue(UGEClass != null);
        BlockWorld bword = BlockWorld.getInstance();
        PrintStream ps=standardIO2File(testPathPrefix+"/files/testPlayFileEjemplo.alu");
        try {
                bword.playFile(testPathPrefix+"/files/testPlayFileEjemplo.txt");
        } catch (Exception e) {
                if (! UGEClass.isInstance(e))
                    fail("Error: no debió lanzar la excepción "+e.getClass().toString());
        }
        //Volvemos a poner la consola como salida standard
        ps.close();
        System.setOut(System.out);
        StringBuilder sbAlu = readFromFile(testPathPrefix+"/files/testPlayFileEjemplo.alu");
        StringBuilder sbSol = readFromFile(testPathPrefix+"/files/testPlayFileEjemplo.sol");
        String strAlu = sbAlu.toString();
        String strSol = sbSol.toString();
        int linesMatch = compareLines(strSol,strAlu);
        if (linesMatch != strSol.split("\n").length) {
                // try alternative solution
                sbSol = readFromFile(testPathPrefix+"/files/testPlayFileEjemploBis.sol");
                strSol = sbSol.toString();
                linesMatch = compareLines(strSol,strAlu);
                if (linesMatch != strSol.split("\n").length) {
                        //System.out.println("linesMatch="+linesMatch+", strSol.length()="+strSol.length());
                        fail("La salida no coincide:\n\t"+testPathPrefix+"/files/testPlayFileEjemplo.alu != "+testPathPrefix+"/files/testPlayFileEjemplo.sol\n\ten línea "+(linesMatch+1));
                }
        }
}

/**********************************************************/
//FUNCIONES DE APOYO

/* Se usa para comparar Strings que devuelven algunos métodos. Evita sacar error por los espacios
* finales de cada línea y por los saltos de línea al final
* devuelve el numero de líneas que concuerdan desde la primera
*/
int compareLines(String expected, String result) {
              String exp[]=expected.split("\n");
              String res[]=result.split("\n");
              for (int i=0; i<exp.length; i++) {

                       // si la línea es Health=1.2345  o Food level=1.2345  
                        // se extraen los números y se comparan con 0.01 de margen
                        String ee[]=exp[i].split("=");
                        if (ee[0].equals("Health") || ee[0].equals("Food level")) {
                                String rr[]=res[i].trim().split("=");
                                if (ee[0].equals(rr[0])) {
                                        double ed = Double.parseDouble(ee[1]);
                                        double rd = Double.parseDouble(rr[1]);

                                        if ( Math.abs(ed - rd) > 0.01 )
                                          return i;
                                } else
                                  return i;
                        }
                        else
                            if (!exp[i].trim().equals(res[i].trim()))
                                  return i;
              }
              return exp.length;
}


/* Añade bloques líquidos (WATER Y LAVA) en world10x10
 *  
   @#. ... ggg\n 
   @#. .P. ggg\n 
   @#. ... Mg.
*/	
void addLiquidBlocks() {
	Location loc;
   //Añadimos agua
   try {
	for (int z=-1; z<2; z++ ) {
		loc = new Location (world10x10,-1,66,z);
		world10x10.addBlock(loc, new LiquidBlock(Material.WATER));
	}
   //Añadimos lava
    for (int z=-1; z<2; z++ ) {
  	 loc = new Location (world10x10,0,66,z);
	 world10x10.addBlock(loc, new LiquidBlock(Material.LAVA));
    }
   } catch (Exception e) {
	fail("Error: no debió lanzar la excepción "+e.getClass().toString());
   }
}

/* Añade Items y Creatures (Monster/Animal) en las mismas posiciones en que se añaden bloques
 * de agua y lava en addLiquidblocks()
 */
void addItemsAndCreatures(ItemStack is, char cr) {
	Location loc;
   //Añadimos Items 
   try {
	for (int z=-1; z<2; z++ ) {
		loc = new Location (world10x10,-1,66,z);
		world10x10.addItems(loc, new ItemStack(is));
	}
   //Añadimos Monsters o Animals
	Creature creature;
    for (int z=-1; z<2; z++ ) {
  	  loc = new Location (world10x10,0,66,z);
  	  creature = ((cr=='M') ?  new Monster(loc,15) :  new Animal(loc,20));
  	  world10x10.addCreature(creature);
    }
   } catch (Exception e) {
	fail("Error: no debió lanzar la excepción "+e.getClass().toString());
   }
}
		
//Función que cambia la salida standard (out) a un fichero
public static PrintStream standardIO2File(String fileName){
    if(fileName.equals("")){//Si viene vacío usamos este por defecto
        fileName=testPathPrefix+"/files/testPlayFileStandard.txt";
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


//Lee el contenido de un fichero y lo devuelve en un StringBuilder	
	private StringBuilder readFromFile(String file) {
		Scanner sc=null;
		try {
				sc = new Scanner(new File(file));
		} catch (FileNotFoundException e) {
				fail("Fichero "+file+" no existe");
		}
		StringBuilder sb = new StringBuilder();
		while (sc.hasNext()) 
			sb.append(sc.nextLine()+"\n");			
		sc.close();
		return (sb);
	}
}
