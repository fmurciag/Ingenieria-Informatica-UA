package model.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.Location;
import model.World;

public class Monster_P3Test {
	Monster monster;
	World world = new World("EARTH");
	Location loc = new Location(world,0,10,0);
	final String outM1 = "Monster [location=Location{world=EARTH,x=0.0,y=10.0,z=0.0}, health=20.0]";
	final String outM2 = "Monster [location=Location{world=EARTH,x=10.0,y=20.5,z=-3.2}, health=-0.25]";
	
	@Before
	public void setUp() throws Exception {
		 monster = new Monster(loc, LivingEntity.MAX_HEALTH);
	}

	/* Comprueba los constructores de Monster y LivingEntity incluida la 
	 * composición con Location y los getters
	 */
	@Test
	public void testMonsterAndGetters() {
		assertEquals (loc,monster.getLocation());
		assertNotSame(loc,monster.getLocation());
		assertEquals (LivingEntity.MAX_HEALTH, monster.getHealth(), 0.01);
		monster.setHealth(10);
		assertEquals (10, monster.getHealth(), 0.01);
		assertEquals ('M',monster.getSymbol());
	}


	//Hay herencia LivingEntity - Creature - Monster???
	@Test
	public void testLivingEntity() {
		Creature creature = monster;
		LivingEntity entity = creature;
		creature = new Monster(loc,LivingEntity.MAX_HEALTH);
		assertTrue (creature.equals(entity));
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

	//Testamos Damage
	@Test
	public void testDamage() {
		monster.damage(15.5);
		assertEquals(4.5, monster.getHealth(),0.01);
		monster.damage(4.5);
		assertEquals(0.0, monster.getHealth(),0.01);
		monster.damage(10000.5);
		assertEquals(-10000.5, monster.getHealth(),0.01);
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
	
	/* Test toString */
	@Test
	public void testToString() {
		assertEquals(outM1, monster.toString().trim());
		monster = new Monster(new Location(world,10.0, 20.5, -3.2),-0.25);
		assertEquals(outM2, monster.toString().trim());
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
