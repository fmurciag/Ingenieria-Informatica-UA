package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import model.entities.Player;
import model.exceptions.BadLocationException;
import model.exceptions.EntityIsDeadException;

public class BlockWorld_P2TestPlay {

	
	final static String STARTPLAY1 ="Name=Steve\n" + 
			"Location{world=World 5x5,x=0.0,y=66.0,z=0.0}\n" + 
			"Orientation=Location{world=World 5x5,x=0.0,y=0.0,z=1.0}\n"+
			"Health=20.0\n" + 
			"Food level=20.0\n" + 
			"Inventory=(inHand=(WOOD_SWORD,1),[])\n" + 
			"... ggg ddd\n" + 
			"... gP. dgg\n" + 
			"... M.. g..";
	final static String HALFPLAY1 ="Name=Steve\n" + 
			"Location{world=World 5x5,x=0.0,y=67.0,z=-1.0}\n" + 
			"Orientation=Location{world=World 5x5,x=0.0,y=0.0,z=1.0}\n"+
			"Health=2.8500000000000014\n" + 
			"Food level=0.0\n" + 
			"Inventory=(inHand=(WOOD_SWORD,1),[])\n" + 
			"... .M. gg.\n" + 
			"... .P. ggg\n" + 
			"... ... g..";
	final static String ENDPLAY1 ="Name=Steve\n" + 
			"Location{world=World 5x5,x=0.0,y=67.0,z=-1.0}\n" +
			"Orientation=Location{world=World 5x5,x=0.0,y=0.0,z=1.0}\n"+
			"Health=-3.1499999999999986\n" + 
			"Food level=0.0\n" + 
			"Inventory=(inHand=(WOOD_SWORD,1),[])\n" + 
			"... .M. gg.\n" + 
			"... .P. ggg\n" + 
			"... ... g..";
	
	final static String STARTPLAY2 ="Name=Steve\n" + 
			"Location{world=World 3x3,x=0.0,y=63.0,z=0.0}\n" + 
			"Orientation=Location{world=World 3x3,x=0.0,y=0.0,z=1.0}\n"+
			"Health=20.0\n" + 
			"Food level=20.0\n" + 
			"Inventory=(inHand=(WOOD_SWORD,1),[])\n" + 
			"... .B. gg.\n" + 
			"... .P. gg.\n" + 
			"... ... g..";
	final static String HALFPLAY2 ="Name=Steve\n" + 
			"Location{world=World 3x3,x=0.0,y=61.0,z=1.0}\n" + 
			"Orientation=Location{world=World 3x3,x=0.0,y=0.0,z=1.0}\n"+
			"Health=20.0\n" + 
			"Food level=9.749999999999996\n" + 
			"Inventory=(inHand=(WOOD_SWORD,1),[(BREAD,1)])\n" + 
			"gg. dd. dd.\n" + 
			"g.. dP. d..\n" + 
			"XXX XXX XXX";
	final static String ENDPLAY2 ="Name=Steve\n" + 
			"Location{world=World 3x3,x=0.0,y=60.0,z=1.0}\n" + 
			"Orientation=Location{world=World 3x3,x=0.0,y=0.0,z=1.0}\n"+
			"Health=-1.5000000000000042\n" + 
			"Food level=0.0\n" + 
			"Inventory=(inHand=(WOOD_SWORD,1),[])\n" + 
			"dd. dd. dd.\n" + 
			"d.. dP. dgg\n" + 
			"XXX XXX XXX";
	
	BlockWorld bw;
	World world5x5;
	World world3x3;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		Material.rng.setSeed(1L);
		bw = BlockWorld.getInstance();
		world3x3 =  bw.createWorld(1, 3, "World 3x3", "Steve");
	//	world5x5 =  bw.createWorld(4, 5, "World 5x5");
	}


	@Test
	public void testPlay1() throws RuntimeException {
		/* INICIO PARTIDA
		 Location{world=World 5x5,x=0.0,y=66.0,z=0.0}
		... ggg ddd
		... gP. dgg
		... M.. g.. 
		*/
		Material.rng.setSeed(1L);
		world5x5 =  bw.createWorld(5, 5, "World 5x5", "Steve");
		//bw.showPlayerInfo(world5x5.getPlayer());
		Player ply = world5x5.getPlayer();
		
			compareLines(STARTPLAY1,bw.showPlayerInfo(world5x5.getPlayer()));
			//Usa el WOOD_SWORD de inHand, 150 veces. Reduce 15 puntos el foodLevel
			try {
				bw.useItem(ply, 150);
			} catch (Exception e1) {
				fail("Excepción "+e1.getClass().getName()+" no esperada");
			}
			assertEquals(5.0,ply.getFoodLevel(),0.001);
			assertEquals(0, ply.getInventorySize()); 
			//Intenta mover hacia un bloque GRASS
			try {
			  
				bw.movePlayer(ply, -1,0,0);
				fail("Debió saltar la excepción BadLocationException");
			} catch (EntityIsDeadException e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			} catch (BadLocationException e) {
				 assertEquals(5.0,ply.getFoodLevel(),0.001);
			    
				 //No recoge nada
			     try {
					bw.movePlayer(ply, 1, 0, 0);
				 } catch (Exception e1) {
					fail("Excepción "+e1.getClass().getName()+" no esperada");
				 } 
			     assertEquals(4.95,ply.getFoodLevel(),0.001);
			     assertEquals(0, ply.getInventorySize()); 
			     
			     //Se mueve al adyacente con mismo x,z del plano superior
			     try {
					bw.movePlayer(ply, 0, 1, 0);
				 } catch (Exception e1) {
					fail("Excepción "+e1.getClass().getName()+" no esperada");
				 }
				 //Se mueve al adyacente 
			     try {
					bw.movePlayer(ply, -1, 0, -1);
				 } catch (Exception e1) {
					fail("Excepción "+e1.getClass().getName()+" no esperada");
				 }
			     assertEquals(4.85,ply.getFoodLevel(),0.001);
			     assertEquals(0, ply.getInventorySize());
			     assertEquals(20.0,ply.getHealth(),0.001);
			     //System.out.println(bw.showPlayerInfo(world5x5.getPlayer()));
			     
			   /*  //Cogemos (IRON_SHOVEL,1) y lo usamos 220 veces.
			     try {
					bw.selectItem(ply, 1);
				 } catch (BadInventoryPositionException e1) {
					fail("Excepción "+e1.getClass().getName()+" no esperada");
				 }*/
			     assertEquals(0, ply.getInventorySize());
			     try {
					bw.useItem(ply, 220);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			     assertEquals(0.0,ply.getFoodLevel(),0.001);
			     assertEquals(2.85,ply.getHealth(),0.001);
			    // System.out.println(bw.showPlayerInfo(world5x5.getPlayer()));
			     compareLines(HALFPLAY1,bw.showPlayerInfo(world5x5.getPlayer()));
			  
			    /* //Cogemos (WATER_BUCKET,1) y lo usamos 20 veces
			     try {
					bw.selectItem(ply, 0);
				 } catch (Exception e1) {
					 fail("Excepción "+e1.getClass().getName()+" no esperada");
				 }*/
			     assertEquals(0, ply.getInventorySize());
			     try {
					bw.useItem(ply, 20);
				 } catch (Exception e1) {
					 fail("Excepción "+e.getClass().getName()+" no esperada");
				 }
			     assertEquals(0.0,ply.getFoodLevel(),0.001);
			     assertEquals(0.85,ply.getHealth(),0.001);
			     
			     //Cogemos (IRON_SHOVEL,1) y lo usamos 40 veces. Player muere
			/*     try {
					bw.selectItem(ply, 0);
				 } catch (BadInventoryPositionException e1) {
					fail("Excepción "+e.getClass().getName()+" no esperada");
				 }*/
			     assertEquals(0, ply.getInventorySize());
			     try {
					bw.useItem(ply, 40);
				 } catch (Exception e1) {
					 fail("Excepción "+e.getClass().getName()+" no esperada");
				 }
			     assertTrue(ply.isDead());
			  
			     //System.out.println(bw.showPlayerInfo(world5x5.getPlayer()));
			     compareLines(ENDPLAY1,bw.showPlayerInfo(world5x5.getPlayer()));
			  } 
	}

	@Test
	public void testPlay2() throws RuntimeException{
		/* INICIO PARTIDA
		 Location{world=World 3x3,x=0.0,y=63.0,z=0.0}
		... I.. gg.
		... .P. gg.
		... W.. g.. 
		*/
		//world3x3 =  bw.createWorld(1, 3, "World 3x3");
		Player ply = world3x3.getPlayer();
		
			//assertEquals(STARTPLAY1,bw.showPlayerInfo(world3x3.getPlayer()));
			compareLines(STARTPLAY2,bw.showPlayerInfo(ply));
			//Usa el WOOD_SWORD 100 veces. Reduce 10 puntos el foodLevel
			try {
				bw.useItem(ply, 100);
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			}
			assertEquals(10,ply.getFoodLevel(),0.01);
			
			//Recoge Item BREAD
			try {
				bw.movePlayer(ply, 0, 0, -1);
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			} 
			assertEquals(9.95,ply.getFoodLevel(),0.01);
			try {
				bw.movePlayer(ply, -1, 0, 1);
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			}
			
			//Recoge nada. Total 1 elemento en Inventario
			try {
				bw.movePlayer(ply, 0, 0, 1);
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			} 
			assertEquals(1, ply.getInventorySize()); 
			
			//Nos movemos a los adyacentes inferiores
			try {
				bw.movePlayer(ply, 1, -1, 0);
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			}
			assertEquals(9.80,ply.getFoodLevel(),0.001);
			
			//Al adyacente inferior
			try {
				bw.movePlayer(ply, 0, -1, 0);
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			}
			//assertEquals(HALFPLAY1,bw.showPlayerInfo(world3x3.getPlayer()));
			compareLines(HALFPLAY2,bw.showPlayerInfo(world3x3.getPlayer()));
			//Selecciona el item de la posición 0 (BREAD,1) y lo pone en la mano.
			//En su posición del inventario pone WOOD_SWORD
			try {
				bw.selectItem(ply,0);
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			}			
			assertEquals(1, ply.getInventorySize()); 
			
			//Pone el item 0 WOOD_SWORD en la mano y BREAD en la posición 0
			try {
				bw.selectItem(ply, 0);
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			} 
			
			//Pone foodLevel a 0 y health a 19.80
			try {
				bw.useItem(ply, 100);
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			}
			assertEquals(0.0,ply.getFoodLevel(),0.001);
			assertEquals(19.75,ply.getHealth(),0.001);
			
			//Pone el item 0 (BREAD) en la mano y WOOD_SWORD en la posición 0
			try {
				bw.selectItem(ply, 0);
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			}
			
			
			//Usamos 2 veces BREAD y había 1. 
			try {
				bw.useItem(ply, 2);
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			}
			assertEquals(5.0,ply.getFoodLevel(),0.001);
			
			//Al adyacente inferior y recogemos (APPLE,1)
			try {
				bw.movePlayer(ply, 0, -1, 0);
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			}
			assertEquals(1, ply.getInventorySize());
			assertEquals(4.95,ply.getFoodLevel(),0.001);
			
			//Pone WOOD_SWORD en la mano 
			//Lo usamos 20 veces
			try {
				bw.selectItem(ply, 0);
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			}
			try {
				bw.useItem(ply, 20);
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			}
			assertEquals(2.95,ply.getFoodLevel(),0.001);
			assertEquals(19.75,ply.getHealth(),0.001);
			
			//Seleccionamos (APPLE,1) pero lo usamos 2 veces (solo 1 efectiva)
		/*	try {
				bw.selectItem(ply, 2);
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			}*/
			try {
				bw.useItem(ply, 2);
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			} 
			assertEquals(2.75,ply.getFoodLevel(),0.001);
			assertEquals(19.75,ply.getHealth(),0.001);
			
			try {
				bw.useItem(ply, 5);
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			}
			assertEquals(2.25,ply.getFoodLevel(),0.001);
			
		/*	//Seleccionamos WOOD_SWORD. Tenemos un item menos en items
			try {
				bw.selectItem(ply, 2);
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			}*/
			assertEquals(0, ply.getInventorySize());
			
			//Lo usamos 213 veces
			try {
				bw.useItem(ply,213);
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			}
			assertEquals(0.0,ply.getFoodLevel(),0.001);
			assertEquals(0.70,ply.getHealth(),0.001);
			
		/*	//Seleccionamos (WATER_BUCKET,1) y lo usamos 1 veces
			try {
				bw.selectItem(ply,1);
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			}*/
			try {
				bw.useItem(ply,1);
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			}
			assertEquals(0.0,ply.getFoodLevel(),0.001);
			assertEquals(0.6,ply.getHealth(),0.001);
			try {
				bw.useItem(ply,1); 
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			} 
			
			assertEquals(0, ply.getInventorySize());
			
			//Matamos a player
		/*	try {
				bw.selectItem(ply, 1);
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			}*/
			try {
				bw.useItem(ply, 20);
			} catch (Exception e) {
				fail("Excepción "+e.getClass().getName()+" no esperada");
			} 
			assertEquals(0.0,ply.getFoodLevel(),0.001);
			assertEquals(-1.50,ply.getHealth(),0.001);
			assertEquals(0, ply.getInventorySize());		
			assertTrue(ply.isDead());
			try {
				bw.movePlayer(ply, 0, 0, 0);
				fail("Error, intentamos mover a un player dead");
			} catch (EntityIsDeadException ex) {
				try {
					bw.useItem(ply, 10);
					fail("Error, intentamos usar un item con player dead");
				} catch (EntityIsDeadException ex1) {
					compareLines(ENDPLAY2,bw.showPlayerInfo(world3x3.getPlayer()));
				}
			} catch (Exception e1) {
				fail("Excepción "+e1.getClass().getName()+" no esperada");
			}
	}
	
        /******************************************************************/
        //FUNCION DE APOYO
        /* Se usa para comparar Strings que devuelven algunos métodos. Evita sacar error por los espacios
         * finales de cada línea y por los saltos de línea al final.
         * Los valores con decimales son comparados con un margen de error de 0.01
         */
        void compareLines(String expected, String result) {
                String exp[]=expected.split("\n");
                String res[]=result.split("\n");
                boolean iguales = true;
                for (int i=0; i<exp.length && iguales; i++) {

                        // si la línea es Health=1.2345  o Food level=1.2345  
                        // se extraen los números y se comparan con 0.01 de margen
                        String ee[]=exp[i].split("=");
                        if (ee[0].equals("Health") || ee[0].equals("Food level")) {
                                String rr[]=res[i].trim().split("=");
                                if (ee[0].equals(rr[0])) {
                                        double ed = Double.parseDouble(ee[1]);
                                        double rd = Double.parseDouble(rr[1]);

                                        assertEquals(ee[0],ed,rd,0.01);
                                }
                        }
                        else
                                assertEquals("linea "+i, exp[i],res[i].trim());
                }
        }
}

