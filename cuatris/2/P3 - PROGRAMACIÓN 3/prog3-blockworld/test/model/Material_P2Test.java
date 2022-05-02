package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class Material_P2Test {

	final  Material typesAlumno[]=Material.values();
	final  Material materias[] = { Material.BEDROCK, Material.CHEST, Material.SAND, Material.DIRT, Material.GRASS, 
			Material.STONE, Material.GRANITE, Material.OBSIDIAN, Material.WATER_BUCKET, 
			Material.APPLE, Material.BREAD, Material.BEEF, Material.IRON_SHOVEL,Material.IRON_PICKAXE, 
			Material.WOOD_SWORD, Material.IRON_SWORD, Material.LAVA, Material.WATER, Material.AIR };
	
	final double values[]= {-1, 0.1, 0.5, 0.5, 0.6, 1.5, 1.5, 5, 1, 4, 5, 8, 0.2, 0.5, 1, 2, 1.0, 0.0, 0.0};
	final char symbols[]= {'*','C','n','d','g','s','r','o','W','A','B','F','>','^','i','I', '#', '@', ' '};

	//Comprueba el orden de los tipos de materias del Enum del alumno
	@Test
	public void testTypes() {
		for (int i = 0; i<=15;i++) {
			  assertEquals (materias[i], typesAlumno[i]);  
		}
	}

	@Test
	public void testIsBlock() {
		for (int i = 0; i<=17;i++) {
		  if (( (i>=0) && (i<=7) )|| (i>15) )
			  assertTrue(materias[i].isBlock());
		  else 
			  assertFalse(materias[i].isBlock());
		}
	}

	@Test
	public void testIsEdible() {
		for (int i = 0; i<=17;i++) {
			  if ( (i>=8) && (i<=11) )
				  assertTrue(materias[i].isEdible());
			  else 
				  assertFalse(materias[i].isEdible());
		}
	}
	
	@Test
	public void testIsTool() {
		for (int i = 0; i<=17;i++) {
			  if ( (i>=12) && (i<=13) )
				  assertTrue(materias[i].isTool());
			  else 
				  assertFalse(materias[i].isTool());
		}
	}

	@Test
	public void testIsWeapon() {
		for (int i = 0; i<=17;i++) {
			  if ( (i>=14) && (i<=15) )
				  assertTrue(materias[i].isWeapon());
			  else 
				  assertFalse(materias[i].isWeapon());
		}
	}
	
	@Test
	public void testIsLiquid() {
		for (int i = 0; i<=17;i++) {
			  if ( (i>=16) && (i<=17) )
				  assertTrue(materias[i].isLiquid());
			  else 
				  assertFalse(materias[i].isLiquid());
		}
	}
	
	@Test
	public void testGetValue() {
		for (int i = 0; i<=17;i++) {
			  assertEquals ("Value["+i+"] == getValue()", values[i], materias[i].getValue(),0.01);
		}
	}

	@Test
	public void testGetSymbol() {
		for (int i = 0; i<=17;i++){
				  assertEquals ("symbol["+i+"] == getSymbol()",symbols[i], materias[i].getSymbol());
			}
	}

	@Test
	public void testGetRandomItem1() {
		for (int i = 0; i<=18;i++) {
			Material mat = Material.getRandomItem(i,i);
			assertTrue (materias[i]==mat);
		}
	}

	//Indices fuera del rango
	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testGetRandomItem2() {
			 Material.getRandomItem(19,20);
	}
	
	//Indices fuera del rango
		@Test(expected = ArrayIndexOutOfBoundsException.class)
		public void testGetRandomItem3() {
			
				 Material.getRandomItem(-5,-1);
		}
	
	//Indice superior menor que el inferior
	@Test(expected = IllegalArgumentException.class)
	public void TestGetRandomItem4() {
		 Material.getRandomItem(7,6);
	}

}
