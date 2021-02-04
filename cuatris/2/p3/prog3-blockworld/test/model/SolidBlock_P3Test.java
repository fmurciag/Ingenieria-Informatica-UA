package model;

import static org.junit.Assert.*;

import org.junit.Test;

import model.exceptions.StackSizeException;
import model.exceptions.WrongMaterialException;

public class SolidBlock_P3Test {

	Material bedrock=Material.BEDROCK;
	Material chest=Material.CHEST;
	Material grass=Material.GRASS;
	Material obsidian=Material.OBSIDIAN;
	
	Material water=Material.WATER;
	
	Material lava = Material.LAVA;
	
/* Prueba del constructor y getType con valores correctos. Se usa una variable Block para 
 * almacenar un objeto SolidBlock */
@Test
public final void testConstructorAndGetType() {
	
	Block bl;
	try {
		bl = new SolidBlock(grass);
		assertTrue(bl.getType()==grass);
		assertNull(((SolidBlock)bl).getDrops());
		
	} catch (Exception e) {
		fail("Error: excepción "+e.getClass().toString()+" inesperada");;
	} 
}
	
/* Prueba fallida del constructor con materiales líquidos */
@Test (expected=WrongMaterialException.class)
public final void testConstructorAndGetTypeException() throws WrongMaterialException {
	
	Block bl=null;
	try {
		bl = new SolidBlock(water);
		fail("Error: WrongMaterialException no se ha lanzado");
	} catch (WrongMaterialException e) {
		new SolidBlock(lava);
	} 				
}
	
/* Se va a comprobar el constructor copia. Para ello se crea un bloque de un material
 * para bloques sólidos, b1.
 * Se llama al constructor copia y se obtiene otro bloque b2.
 * Se comprueba que ambos bloques son del mismo tipo y que no tienen drops.
 * */
@Test
public final void testConstructorCopia() {
	
	SolidBlock b1, b2;
	try {
		b1 = new SolidBlock(bedrock);
		b2 = new SolidBlock(b1);
		assertEquals(bedrock, b2.getType());
		assertNull(b2.getDrops());
		assertNotSame(b1,b2);
		
	} catch (Exception e) {
		fail("Error: excepción "+e.getClass().toString()+" inesperada");
	} 
}
	
/* Comprobación de clone():
 * Se crea un bloque sólido y un item del mismo tipo. Se añade el item al bloque.
 * Se clona y se comprueba que tanto el original como el clon son iguales 
 * (mismo tipo y mismo item) y no tienen la misma dirección
 */
@Test
public final void testClone(){
	SolidBlock sb1=null;
	Block sb2=null;
	ItemStack item;
	//Clone se implementa correctamente en SolidBlock
	try {
		item = new ItemStack(chest,5);
		sb1 = new SolidBlock(chest);
		sb1.setDrops(chest,5);
		
		sb2=sb1.clone();
		assertNotNull(sb2);
		assertEquals(chest, sb2.getType());
		assertEquals(item,((SolidBlock)sb2).getDrops());
		assertNotSame(sb1, sb2);
		
	} catch (Exception e) {
		fail("Error: excepción "+e.getClass().toString()+" inesperada");
	} 
}

/* Comprueba que el método breaks es cierto si el daño producido es mayor o
 * igual que el valor de la dureza del material GRASS. Y que es falso si lo
 * supera.
 */	
@Test
public final void testBreaks() {
	SolidBlock bl;
	try {
		bl = new SolidBlock(grass);
		//daño==valor bloque
		assertTrue (bl.breaks(0.6));
		//daño > valor bloque
		assertTrue (bl.breaks(0.7));
		//daño < valor bloque
		assertFalse (bl.breaks(0.5));
	} catch (Exception e) {
		fail("Error: excepción "+e.getClass().toString()+" inesperada");
	}
}
	
/* Comprueba el correcto funcionamiento de seDrops. 
 * Este test ya estaba en Block_P2Test */
@Test
public final void testSetDrops() {
	SolidBlock b;
	try {
		b = new SolidBlock(grass);
		try {
		// amount != 1 para no CHEST (debe lanzar StackSizeException)
			b.setDrops(grass,5);
			fail("StackSizeException no se ha lanzado");
		} catch (StackSizeException e) {
				
			assertNull(b.getDrops());  // drops debe seguir siendo null
			try {
				b = new SolidBlock(chest);
				b.setDrops(water,4);  // correcto				
				ItemStack it=b.getDrops();
				assertTrue(it.getType()==water);
				assertTrue(it.getAmount()==4);
			} catch (StackSizeException e1) {
					fail("StackSizeException incorrectamente lanzada");
			}
			try {
				b.setDrops(chest,1200000); // incorrecto
				fail("StackSizeException no se ha lanzado");
			} catch (StackSizeException e1) {
				// OK, debe lanzar StackSizeException porque amount > 64
			}  
		}
	} catch (WrongMaterialException e) {
			fail("WrongMaterialException incorrectamente lanzada");
	}
}

/* Test para equals probando con cada uno de los atributos que deben 
*  intervenir: type y drops 
*/
@Test
public void testEqualsObject() {
	Block b1;
	try {
		b1 = new SolidBlock(grass);
		assertFalse(b1.equals(null));
		assertTrue(b1.equals(b1));
		assertFalse(b1.equals(obsidian));
		
		//Distintos type
		Block b2=new SolidBlock (bedrock);
		assertFalse(b1.equals(b2));
		
		//Igual type, distintos drops
		SolidBlock sb2 = new SolidBlock((SolidBlock)b2);
		sb2.setDrops(bedrock, 1);
		assertNotEquals (sb2, b2);
		
		//Igual type, igual drops (no null)
		SolidBlock sb1 = new SolidBlock(sb2); 
		assertTrue (sb2.equals(sb1));
		
	} catch (Exception e) {
		fail("Error: excepción "+e.getClass().toString()+" inesperada");;
	}
}
	
//Test para hasCode() probando los atributos que deben intervenir
@Test
public void testHashCode() {
  try {
	Block b1 = new SolidBlock(bedrock);
	Block b2 = new SolidBlock(bedrock);
	assertEquals(b1.hashCode(),b2.hashCode());
	
	b1= new SolidBlock(obsidian);
	assertNotEquals(b1.hashCode(), b2.hashCode());
	
	//Igual type, distintos Drops
	SolidBlock sb2 = new SolidBlock((SolidBlock)b2);
	sb2.setDrops(bedrock, 1);
	assertNotEquals(sb2.hashCode(), b2.hashCode());
	
	//Igual type, igual drops (no null)
	SolidBlock sb1 = new SolidBlock(sb2);
	assertEquals(sb2.hashCode(), sb1.hashCode());
	
  } catch (Exception e) {
		fail("Error: excepción "+e.getClass().toString()+" inesperada");;
  }
}

}
