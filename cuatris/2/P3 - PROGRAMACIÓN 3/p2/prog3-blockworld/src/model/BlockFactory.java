package model;

import model.exceptions.WrongMaterialException;

/**
 * fabrica de bloques.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public class BlockFactory {
	
	/**
	 * crea bloques.
	 *
	 * @param type the tipo
	 * @return the block
	 * @throws WrongMaterialException the wrong material exception
	 */
	public static Block createBlock(Material type) throws WrongMaterialException {
		if(type.isLiquid()) {
			try {
				LiquidBlock liquido= new LiquidBlock(type);
				return liquido;
			} catch (WrongMaterialException e) {
				e.printStackTrace();
			}
		}else if(type.isBlock()){
			try {
				SolidBlock solido = new  SolidBlock(type);
				return solido;
			} catch (WrongMaterialException e) {
				e.printStackTrace();
			}
		}else {
			throw new WrongMaterialException(type);
		}
		return null;
		
	}

}
