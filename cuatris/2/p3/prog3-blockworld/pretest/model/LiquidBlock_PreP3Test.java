package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.exceptions.WrongMaterialException;

public class LiquidBlock_PreP3Test {
	
	Material obsidian=Material.OBSIDIAN;
	Material water=Material.WATER;
	Material lava = Material.LAVA;

/* Se crean bloques líquidos con materiales líquidos con el constructor.
 * Se comprueba que ambos tienen el tipo y los valores correctos
 */
@Test
public final void testConstructorAndGetters() {
	LiquidBlock bl;
	try {
		bl = new LiquidBlock(water);
		assertTrue(bl.getType() == water);
		assertTrue (bl.getDamage() == water.getValue());
		
		bl = new LiquidBlock(lava);
		assertTrue(bl.getType() == lava);
		assertTrue (bl.getDamage() == lava.getValue());
		
	} catch (Exception e) {
		fail("Error: excepción "+e.getClass().toString()+" inesperada");;
	} 
}	
	
/* Se intenta construir bloques líquidos con todos los materiales de bloques sólidos.
 * En todos los casos se produce la excepción WrongMaterialException */
//TODO
@Test
public final void testConstructorException() {
	Material []m = Material.values();
	LiquidBlock lb =null;
	for (int i =0; i<=7; i++) {
		try {
			lb = new LiquidBlock(m[i]);
			fail("Completar el test");
		} catch (WrongMaterialException e) {
			
		}
	}
		
}

/* Se va a comprobar el constructor copia. Para ello se crea un bloque de lava b1.
 * Se llama al constructor copia y se obtiene otro bloque de lava b2.
 * Se comprueba que ambos bloques son del mismo tipo y que el daño que proporcionan
 * es el mismo.*/
@Test
public final void testConstructorCopia() {
	LiquidBlock b1, b2;
	try {
		//TODO
		b1= new LiquidBlock(Material.LAVA);
		b2= new LiquidBlock(b1);
		 assertTrue(b1.getType() == lava);
	     assertTrue(b1.getDamage() == lava.getValue());
		assertTrue(b2.getDamage() == lava.getValue());
		assertTrue(b2.getType() == lava);
		assertEquals(b1, b2);
		
	} catch (Exception e) {
		fail("Error: excepción "+e.getClass().toString()+" inesperada");
	} 
}
	
/* Comprobación de clone():
 * Se crea un bloque líquido. Se clona y se comprueba que tanto el original como
 * el clon son iguales (mismo tipo y mismo item) y no tienen la misma dirección */
//TODO
@Test
public final void testClone(){
	LiquidBlock sb1=null;
	Block sb2=null;

	try {		
		sb1 = new LiquidBlock(water);			
		sb2=sb1.clone();
		//TODO
		assertEquals(sb1, sb2);
		assertNotSame(sb1, sb2);
		
	} catch (Exception e) {
		fail("Error: excepción "+e.getClass().toString()+" inesperada");
	} 
}
	
/* Test para equals probando con cada uno de los atributos que deben intervenir:
 *  type y drops
 */
@Test
public void testEqualsObject() {
	Block b1;
	try {
		b1 = new LiquidBlock(lava);
		assertFalse(b1.equals(null));
		assertTrue(b1.equals(b1));
		assertFalse(b1.equals(obsidian));
		//Distintos type
		Block b2=new LiquidBlock (water);
		assertFalse(b1.equals(b2));
	} catch (Exception e) {
		fail("Error: excepción "+e.getClass().toString()+" inesperada");;
	}
}

//Test para hasCode() probando los atributos que deben intervenir
@Test
public void testHashCode() {
  try {
	  //Mismo tipo de bloque
	Block b1 = new LiquidBlock(lava);
	Block b2 = new LiquidBlock(lava);
	assertEquals(b1.hashCode(),b2.hashCode());
	
	//Distinto tipo de bloque
	b1= new LiquidBlock(water);
	assertNotEquals(b1.hashCode(), b2.hashCode());
  } catch (Exception e) {
		fail("Error: excepción "+e.getClass().toString()+" inesperada");;
  }
}

}
