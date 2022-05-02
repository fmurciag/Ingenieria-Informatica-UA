package model;

import java.util.Random;

/**
 * enum de los materiales.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H 
 */
public enum Material {
	/** la bedrock. */
	BEDROCK(-1,'*'),
	/** el chest. */
	CHEST(0.1,'C'),
	/** la sand. */
	SAND(0.5,'n'),
	/** la dirt. */
	DIRT(0.5,'d'),
	/** la grass. */
	GRASS(0.6,'g'),
	/** la stone. */
	STONE(1.5,'s'),
	/** el granite. */
	GRANITE(1.5,'r'),
	/** la obsidian. */
	OBSIDIAN(5,'o'),
	/** el water bucker. */
	WATER_BUCKET(1,'W'),
	/** la apple. */
	APPLE(4,'A'),
	/** el bread. */
	BREAD(5,'B'),
	/** el beef. */
	BEEF(8,'F'),
	/** el iron sholvel. */
	IRON_SHOVEL(0.2,'>'),
	/** el iron pickaxe. */
	IRON_PICKAXE(0.5,'^'),
	/** la wood sword. */
	WOOD_SWORD(1,'i'),
	/** la iron sword. */
	IRON_SWORD(2,'I'),
	/** la lava. */
	LAVA(1,'#'),
	/** el water. */
	WATER(0,'@'),
	/** EL AIRE. */
	AIR(0,' ');

	/** el valor. */
	private double value;
	
	/** el sombolo del material. */
	private char symbol;
	
	/** The rng. */
	static Random rng = new Random(1L);
	
	
	/**
	 * instancias de un material.
	 *
	 * @param value the value
	 * @param symbol the symbol
	 */
	private Material(double value,char symbol) {
		this.value=value;
		this.symbol = symbol;
		
	}
	
	/**
	 * mira si es un bloque.
	 *
	 * @return true, si es un bloque
	 */
	public boolean isBlock() {
		Material materiales = this;
		
		if(materiales==BEDROCK||materiales==CHEST||materiales==SAND||materiales==DIRT
				||materiales==GRASS||materiales==STONE||materiales==GRANITE
				||materiales==OBSIDIAN||materiales==WATER||materiales==LAVA||materiales==AIR) {
			return true;
		}else {
			return false;
		}	
	}
	
	/**
	 * mira si es comible.
	 *
	 * @return true, si es comible
	 */
	public boolean isEdible() {
		Material materiales = this;
		
		if(materiales==WATER_BUCKET||materiales==APPLE||materiales==BREAD||materiales==BEEF) {
			return true;
		}else {
			return false;
		}	
	}
	
	/**
	 * mira si es un arma.
	 *
	 * @return true, si es un arma
	 */
	public boolean isWeapon() {
		Material materiales = this;
		
		if(materiales==WOOD_SWORD||materiales==IRON_SWORD) {
			return true;
		}else {
			return false;
		}	
	}
	
	/**
	 * mira si es una herramienta.
	 *
	 * @return true, si es una herramienta
	 */
	public boolean isTool() {
		Material materiales = this;
		if(materiales==IRON_SHOVEL||materiales==IRON_PICKAXE) {
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * mira si es liquido.
	 *
	 * @return true, si es liquido
	 */
	public boolean isLiquid() {
		Material materiales = this;
		if(materiales==WATER||materiales==LAVA||materiales==AIR) {
			return true;
		}else {
			return false;
		}
	}

	/**
	 * obtiene el value.
	 *
	 * @return el value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * obtiene el symbol.
	 *
	 * @return el symbol
	 */
	public char getSymbol() {
		return symbol;
	}
	 
 	/**
 	 * obtiene un inem aleatorio.
 	 *
 	 * @param first the first
 	 * @param last the last
 	 * @return el random item
 	 */
	public static Material getRandomItem(int first, int last) {
        int i = rng.nextInt(last-first+1)+first;
        return values()[i];
    }
}


