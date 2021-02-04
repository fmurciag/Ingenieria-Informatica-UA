package model.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import model.ItemStack;
import model.Location;
import model.Material;
import model.World;

public class Animal_P3Test {
	Animal animal;
	World world = new World("EARTH");
	Location loc = new Location(world,0,10,0);
	final String outM1 = "Animal [location=Location{world=EARTH,x=0.0,y=10.0,z=0.0}, health=20.0]";
	final String outM2 = "Animal [location=Location{world=EARTH,x=10.0,y=20.5,z=-3.2}, health=-0.25]";
	
	@Before
	public void setUp() throws Exception {
		 animal = new Animal(loc, LivingEntity.MAX_HEALTH);
	}

/* Probamos que loc y la posición del animal son iguales. Comprobamos también
 * la relación de composición entre Animal y Location
 * Comprobamos que la salud del animal es MAX_HEALTH.
 * Se cambia la salud del animal a 10 y se comprueba que es así.
 * Se comprueba que el símbolo de animal es 'L'
*/
@Test
public void testAnimalAndGetters() {
	assertEquals (loc,animal.getLocation());
	assertNotSame(loc,animal.getLocation());
	assertEquals (LivingEntity.MAX_HEALTH, animal.getHealth(), 0.01);
	animal.setHealth(10);
	assertEquals (10, animal.getHealth(), 0.01);
	assertEquals ('L',animal.getSymbol());
}
	
/* Asigna la dirección del animal a una variable Creature.
 * Asigna la variable Creature anterior a una variable LivingEntity
 * Crea un animal con posición 'loc' y salud MAX_HEALTH y asígnalo a
 * la variable Creature.
 * Comprueba que el animal de creature es igual al de entity
*/
@Test
public void testLivingEntity() {
	Creature creature = animal;
	LivingEntity entity = creature;
	creature = new Animal(loc,LivingEntity.MAX_HEALTH);
	assertTrue (creature.equals(entity));
}

/* Probamos métodos implementados en LivingEntity 
   Testamos isDead */
@Test
public void testIsDead() {
	//Inicialmente no está muerto
	assertFalse(animal.isDead());
		
	//Está al límite
	animal.setHealth(0.001);
	assertFalse(animal.isDead());
	
	//Lo matamos
	animal.setHealth(0.0);
	assertTrue(animal.isDead());
	animal.setHealth(-0.001);
	assertTrue(animal.isDead());
}

/* Aplica a animal un daño de 15.5 y comprueba que su salud se queda en 4.5
 * Aplica a animal un daño de 4.5 y comprueba que su salud queda a 0.
 * Aplica a animal un daño de 10000.5 y comprueba que su salud quea a -10000.5
 */
@Test
public void testDamage() {
	animal.damage(15.5);
	assertEquals(4.5, animal.getHealth(),0.01);
	animal.damage(4.5);
	assertEquals(0.0, animal.getHealth(),0.01);
	animal.damage(10000.5);
	assertEquals(-10000.5, animal.getHealth(),0.01);
}

//Testamos setHealth
@Test
public void testSetHealth() {
	animal.setHealth(5.5);
	assertEquals("Health = 5.5", 5.5, animal.getHealth(),0.01);
	animal.setHealth(-10.01);
	assertEquals("Health = -10.01", -10.01, animal.getHealth(),0.01);
	animal.setHealth(LivingEntity.MAX_HEALTH-0.01);
	assertEquals("Health = 19.99", 19.99, animal.getHealth(),0.01);
	animal.setHealth(LivingEntity.MAX_HEALTH+0.01);
	assertEquals("Health = 20.00", LivingEntity.MAX_HEALTH, animal.getHealth(),0.01);
}

/* Comprueba que toString sobre animal crea una cadena igual a outM1.
 * Crea un Animal con una posicion en el mundo world y coordenadas 10.0, 
 * 20.5 y -3.2 para x, y, z respectivamente y salud -0.25
 * Comprueba que al aplicar toString sobre este animal se genera una
 * cadena igual a outM2
 */
@Test
public void testToString() {
	assertEquals(outM1, animal.toString().trim());
	animal = new Animal(new Location(world,10.0, 20.5, -3.2),-0.25);
	assertEquals(outM2, animal.toString().trim());
	System.out.println(animal.toString());
}

/* getDrops debe devolver 1 pieza de BEEF */
@Test
public void testGetDrops() {
	ItemStack beef;
	try {
		beef = new ItemStack(Material.BEEF, 1);
		assertEquals (beef, animal.getDrops());
	} catch (Exception e) {
		fail("Error: no debió lanzar la excepción "+e.getClass().getName());
	}
}

/* Prueba equals de LivingEntity con Animal */
@Test
public void testEqualsObject() {
	Animal animal2 = new Animal(loc,LivingEntity.MAX_HEALTH);
	assertTrue(animal.equals(animal2));
}

/* Prueba hashCode de LivingEntity con Animal */
@Test
public void testHashCode() {
	Animal animal2 = new Animal(loc,LivingEntity.MAX_HEALTH);
	assertEquals(animal.hashCode(), animal2.hashCode());
}
}
