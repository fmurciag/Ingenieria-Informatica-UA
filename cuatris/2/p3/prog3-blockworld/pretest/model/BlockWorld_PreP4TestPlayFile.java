package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import model.entities.Player;
import model.score.CollectedItemsScore;
import model.score.MiningScore;
import model.score.PlayerMovementScore;
import model.score.XPScore;

public class BlockWorld_PreP4TestPlayFile {
	

	XPScore xpScore;
	
	CollectedItemsScore itemScore;
	MiningScore miningScore;
	PlayerMovementScore movementScore;
	Player player; 
	World world;
	
	@Before
	public void setUp() throws Exception {

		Material.rng.setSeed(1L);
	}


	@Test
	public void testPlayFileDangerousPlanet1() {
		BlockWorld game = BlockWorld.getInstance();
		PrintStream ps = standardIO2File("pretest/files/DangerousPlanet1.student");
		try {
			game.playFile("pretest/files/DangerousPlanet1.input");
			
		} catch (Exception e) {
			fail("Error: no debió lanzar la excepción "+e.getClass().toString());
		}
		
		//Volvemos a poner la consola como salida standard
		ps.close();
		System.setOut(System.out);
		
		StringBuilder sbAlu = readFromFile("pretest/files/DangerousPlanet1.student");
		StringBuilder sbSol = readFromFile("pretest/files/DangerousPlanet1.solution");
		System.out.println(sbSol.toString());
		System.out.println(sbAlu.toString());
		compareLines(sbSol.toString(),sbAlu.toString());
	}
	
	
	
	/******************************************************************/
	//FUNCION DE APOYO
	/* Se usa para comparar Strings que devuelven algunos métodos. Evita sacar error por los espacios
	 * finales de cada línea y por los saltos de línea al final
	 */
	void compareLines(String expected, String result) {
		String exp[]=expected.split("\n");
		String res[]=result.split("\n");
		boolean iguales = true;
		if (exp.length!=res.length) 
			fail("Cadena esperada de tamaño ("+exp.length+") distinto a la resultante ("+res.length+")");
		for (int i=0; i<exp.length && iguales; i++) {
			
			// si la línea es Health=1.2345  o Food level=1.2345  
			// se extraen los números y se comparan con 0.01 de margen
			
			if ((exp[i].length()>8) && (exp[i].substring(0, 8).trim().equals("Scores:"))) { //Analiza la línea de los Scores
				String sexp = exp[i].substring(8);
				String sres = res[i].substring(8);
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
	private static PrintStream standardIO2File(String fileName){
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

