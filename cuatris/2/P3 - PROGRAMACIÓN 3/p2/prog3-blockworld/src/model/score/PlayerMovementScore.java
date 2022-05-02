package model.score;

import model.Location;

/**
 * The Class PlayerMovementScore.
 * puntuacion al moverte
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public class PlayerMovementScore extends Score<Location> {
	
	/** la localizacion previa. */
	private Location previousLocation;

	/**
	 * constructor de la puntuacion al moverte.
	 *
	 * @param nombre the nombre del jugador
	 */
	public PlayerMovementScore(String nombre) {
		super(nombre);
		previousLocation=null;
	}

	
	@Override
	public int compareTo(Score<Location> o) {
		if (getScoring() < o.getScoring()) {
            return -1;
        }
        if ( getScoring()>o.getScoring() ) {
            return 1;
        }
		
		return 0;
	}

	
	@Override
	public void score(Location l) {
		if(previousLocation==null) {
			Location nwLoc= new Location(l.getWorld(), l.getX(), l.getY(), l.getZ());
			previousLocation=nwLoc;
		}else {
			score+=previousLocation.distance(l);
			Location nwLoc= new Location(l.getWorld(), l.getX(), l.getY(), l.getZ());
			previousLocation=nwLoc;
		}
		
	 
	}

}
