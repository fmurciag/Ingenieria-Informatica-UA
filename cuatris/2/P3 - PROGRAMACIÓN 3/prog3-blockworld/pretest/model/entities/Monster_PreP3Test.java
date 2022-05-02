package model.entities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Location;
import model.World;

public class Monster_PreP3Test {
	Monster monster;
	World world = new World("EARTH");
	Location loc = new Location(world,0,10,0);
	final String outM1 = "Monster [location=Location{world=EARTH,x=0.0,y=10.0,z=0.0}, health=20.0]";
	final String outM2 = "Monster [location=Location{world=EARTH,x=10.0,y=20.5,z=-3.2}, health=-0.25]";
	
	@Before
	public void setUp() throws Exception {
		 monster = new Monster(loc, LivingEntity.MAX_HEALTH);
	}

/* Probamos que loc y la posición del monster son iguales. Comprobamos también
 * la relación de composición entre Monster y Location
 * Comprobamos que la salud del monster es MAX_HEALTH.
 * Se cambia la salud del monster a 10 y se comprueba que es así.
 * Se comprueba que el símbolo de monster es 'M'
*/
	@Test
	public void testMonsterAndGetters() {
	//TODO
		assertEquals(loc, monster.getLocation());
		
		assertEquals(Monster.MAX_HEALTH,monster.getHealth(),0.01);
		monster.setHealth(10.0);
		assertEquals(10.0, monster.getHealth(),0.01);
		assertEquals('M',monster.getSymbol());
		//fail("realiza el test");
	}


	/* Asigna la dirección del monster a una variable Creature.
	 * Asigna la variable Creature anterior a una variable LivingEntity
	 * Crea un monster con posición 'loc' y salud MAX_HEALTH y asígnalo a
	 * la variable Creature.
	 * Comprueba que el monster de creature es igual al de entity
	*/
	//TODO
	@Test
	public void testLivingEntity() {
		Creature creature = monster;
		//TODO
		LivingEntity livingEnt= creature;
		Monster monster2= new Monster(loc, Monster.MAX_HEALTH);
		creature= monster2;
		assertEquals(livingEnt, monster2);
		//fail("Termina el test");
	}

	
	/* Probamos isDead() implementado en LivingEntity */
	@Test
	public void testIsDead() {
		//Inicialmente no está muerto
		assertFalse(monster.isDead());
			
		//Está al límite
		monster.setHealth(0.001);
		assertFalse(monster.isDead());
		
		//Lo matamos
		monster.setHealth(0.0);
		assertTrue(monster.isDead());
		monster.setHealth(-0.001);
		assertTrue(monster.isDead());
	}

	/* Aplica a monster un daño de 15.5 y comprueba que su salud se queda en 4.5
	 * Aplica a monster un daño de 4.5 y comprueba que su salud queda a 0.
	 * Aplica a monster un daño de 10000.5 y comprueba que su salud quea a -10000.5
	 */
	//TODO
	@Test
	public void testDamage() {
		//TODO
		monster.damage( 15.5);
		assertEquals(4.5,monster.getHealth(),0.01);
		monster.damage(4.5);
		assertEquals(0, monster.getHealth(),0.01);
		monster.damage(10000.5);
		assertEquals(-10000.5, monster.getHealth(),0.01);
		//fail ("Realiza el test");
	}

	@Test
	public void testSetHealth() {
		monster.setHealth(5.5);
		assertEquals("Health = 5.5", 5.5, monster.getHealth(),0.01);
		monster.setHealth(-10.01);
		assertEquals("Health = -10.01", -10.01, monster.getHealth(),0.01);
		monster.setHealth(LivingEntity.MAX_HEALTH-0.01);
		assertEquals("Health = 19.99", 19.99, monster.getHealth(),0.01);
		monster.setHealth(LivingEntity.MAX_HEALTH+0.01);
		assertEquals("Health = 20.00", LivingEntity.MAX_HEALTH, monster.getHealth(),0.01);
	}
	
	/* Comprueba que toString sobre monster crea una cadena igual a outM1.
	 * Crea un monster con una posicion en el mundo world y coordenadas 10.0, 
	 * 20.5 y -3.2 para x, y, z respectivamente y salud -0.25
	 * Comprueba que al aplicar toString sobre este monster se genera una
	 * cadena igual a outM2
	 */
	//TODO
	@Test
	public void testToString() {
		assertEquals(outM1, monster.toString().trim());
		//TODO
		Monster monster2 = new Monster(new Location(world,10.0, 20.5,-3.2),-0.25);
		assertEquals(outM2, monster2.toString());
		//fail("Termina el test");
		
	}

	/* Prueba equals de LivingEntity con Monster */
	@Test
	public void testEqualsObject() {
		Monster monster2 = new Monster(loc,LivingEntity.MAX_HEALTH);
		assertTrue(monster.equals(monster2));
	}
	
	/* Prueba hashCode de LivingEntity con Monster */
	@Test
	public void testHashCode() {
		Monster monster2 = new Monster(loc,LivingEntity.MAX_HEALTH);
		assertEquals(monster.hashCode(), monster2.hashCode());
	}

}
