package model;

import static org.junit.Assert.*;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import model.entities.Animal;
import model.entities.Creature;
import model.entities.LivingEntity;
import model.entities.Monster;
import model.entities.Player;
import model.exceptions.BadLocationException;
import model.exceptions.EntityIsDeadException;
import model.exceptions.StackSizeException;
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

public class BlockWorld_PreP3Test {

	BlockWorld bw;
	World world10x10;
	Player player;
	
	@Before
	public void setUp() throws Exception {
		Material.rng.setSeed(1L);
		bw = BlockWorld.getInstance();
		world10x10 =  bw.createWorld(5, 10, "World 10x10");
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
 su lugar 1 item de BEEF */
@Test
 public void testUseItemAgainstAnimal() {
	try {
        ItemStack is = new ItemStack(Material.BEEF,1);
        addItemsAndCreatures(new ItemStack(Material.GRANITE,1), 'A');
        bw.orientatePlayer(player, 0, 1, 0);
        Location loc = player.getOrientation();
        Animal a = new Animal(loc,20);
        assertNotNull(world10x10.getCreatureAt(loc));
        //player.selectItem(0);
        bw.useItem(player, 10);
        assertEquals("Health = 10.0",10, world10x10.getCreatureAt(loc).getHealth(),0.01);
        bw.useItem(player, 10);
        //assertEquals("Health = 10.0",0, world10x10.getCreatureAt(loc).getHealth(),0.01);
        assertNull(world10x10.getCreatureAt(loc));
        ItemStack item2 = world10x10.getItemsAt(loc);
        if(item2!=null) {
            assertEquals(is, item2);
        }else {
            fail("Eres un parguelas ");
        }
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
//TODO
@Test
public void testUseItemAgainstBlock2() {
	try {
        ItemStack is = new ItemStack(Material.OBSIDIAN,1);
        SolidBlock block = (SolidBlock)BlockFactory.createBlock(Material.OBSIDIAN);
        block.setDrops(Material.OBSIDIAN, 1);
        //TODO
        Location loc = player.getOrientation();
        world10x10.addBlock(loc, block);
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
//TODO
@Test
	public void testUseItemAgainstMonster() {
	 try {
		 bw.orientatePlayer(player, -1, -1, 1);
         Location loc = player.getOrientation();
         Creature monster = world10x10.getCreatureAt(loc);
         monster.setHealth(10);
         bw.useItem(player, 5);
         assertEquals("Player Health() = 17.5", 17.5, player.getHealth(), 0.01);
         assertEquals("Monster Health() = 5", 5, world10x10.getCreatureAt(loc).getHealth(),0.01);
         bw.useItem(player, 5);
         assertNull(world10x10.getCreatureAt(loc));
       

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
        Location loc = player.getOrientation();
        System.out.println(player.getOrientation());
        System.out.println(player.getLocation());
        world10x10.addItems(loc, is);
        assertEquals(is, world10x10.getItemsAt(loc));
        bw.movePlayer(player, 0, 0, 1);
        System.out.println(player.getLocation());
        assertEquals(player.getInventorySize(), 1);
        bw.orientatePlayer(player, 0, 1, 0);
        System.out.println(player.getLocation());
        System.out.println(player.getOrientation());
        player.selectItem(0);
        assertEquals(Material.SAND, player.useItemInHand(1).getType());
        bw.useItem(player, 1);
        SolidBlock block = (SolidBlock)BlockFactory.createBlock(Material.SAND);
        //TODO
        //Comprobación que se ha creado un bloque SAND en la posición loc
        assertEquals(block,world10x10.getBlockAt(player.getOrientation()));
        block = (SolidBlock)world10x10.getBlockAt(player.getOrientation());
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
//TODO
@Test
public void testUseItemSolidBlockAgainstBlock() {
		
	try {
		ItemStack is = new ItemStack(Material.STONE,1);
        SolidBlock block = (SolidBlock)BlockFactory.createBlock(Material.STONE);
        player.addItemsToInventory(is);
        bw.orientatePlayer(player, 0, -1, -1);
        player.selectItem(0);
        assertEquals(Material.GRASS, world10x10.getBlockAt(player.getOrientation()).getType());
        bw.useItem(player,5);
        assertNotNull( world10x10.getBlockAt(player.getOrientation()));
        bw.useItem(player,7);
        assertNull( world10x10.getBlockAt(player.getOrientation()));
        bw.useItem(player,1);
        assertNotNull( world10x10.getBlockAt(player.getOrientation()));
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
}
	
 
	
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
	bw.playFile("files/NoFileExist.txt");		
}

/* Test de lectura de datos y órdenes de un mundo, tanto válidos como no
 * válidos. Se compara finalmente la salida de la ejecución del método playFile
 * de alumno con la solución final. 
 */
@Test
public void testPlayFileEjemplo() {
	BlockWorld bword = BlockWorld.getInstance();
	standardIO2File("preTest/files/testPlayFileEjemplo.alu");
	try {
		bword.playFile("preTest/files/testPlayFileEjemplo.txt");
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().toString());
	}
	//Volvemos a poner la consola como salida standard
	System.setOut(System.out);
	StringBuilder sbAlu = readFromFile("preTest/files/testPlayFileEjemplo.alu");
	StringBuilder sbSol = readFromFile("preTest/files/testPlayFileEjemplo.sol");
	compareLines(sbAlu.toString(),sbSol.toString());
}	
	
/**********************************************************/
//FUNCIONES DE APOYO

/* Se usa para comparar Strings que devuelven algunos métodos. Evita sacar error por los espacios
 * finales de cada línea y por los saltos de línea al final
 */
void compareLines(String expected, String result) {
	String exp[]=expected.split("\n");
	String res[]=result.split("\n");
	boolean iguales = true;
	for (int i=0; i<exp.length && iguales; i++) {
		assertEquals("linea "+i, exp[i].trim(),res[i].trim());
	}
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
public static void standardIO2File(String fileName){
  if(fileName.equals("")){//Si viene vacío usamos este por defecto
      fileName="PreTest/files/testPlayFileStandard.txt";
  }
  try {
      //Creamos un printstream sobre el archivo permitiendo añadir al
      //final para no sobreescribir.
      PrintStream ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(new File(fileName),false)),true);
      //Redirigimos entrada 
      System.setOut(ps);
     // System.setErr(ps);
  } catch (FileNotFoundException ex) {
      System.err.println("Se ha producido una excepción FileNotFoundException");
  }
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
