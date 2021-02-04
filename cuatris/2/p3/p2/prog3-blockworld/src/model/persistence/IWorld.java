package model.persistence;

import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

import model.Block;
import model.ItemStack;
import model.Location;
import model.entities.Creature;

/**
 * The Interface IWorld.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public interface IWorld {
	
	/**
	 * obtiene el mapa de bloques en un 16*16*16.
	 *
	 * @param loc the localizacion
	 * @return the map block
	 */
	public NavigableMap<Location,Block>getMapBlock(Location loc);
	
	/**
	 * obtiene el valor mas negativo.
	 *
	 * @return the negative limit
	 */
	public int getNegativeLimit();
	
	/**
	 * obtiene el jugardor
	 *
	 * @return the player
	 */
	public IPlayer getPlayer();
	
	/**
	 * obtiene el valor mas positivo.
	 *
	 * @return the positive limit
	 */
	public int getPositiveLimit();
	
	/**
	 * obtiene una lista de las criaturas colindantes en un 16*16*16.
	 *
	 * @param loc the localizacion
	 * @return the criatures
	 */
	public List<Creature>getCreatures(Location loc);
	
	/**
	 * obtiene el mapa de items en un 16*16*16.
	 *
	 * @param loc the localizacion
	 * @return the items
	 */
	public Map<Location, ItemStack>getItems(Location loc);
}
