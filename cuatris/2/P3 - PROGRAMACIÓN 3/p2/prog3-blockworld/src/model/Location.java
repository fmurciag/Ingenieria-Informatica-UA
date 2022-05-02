package model;

import java.util.HashSet;
import java.util.Set;

import model.exceptions.BadLocationException;

/**
 * The Class Location.
 *
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public class Location implements Comparable<Location> {
	
	
	
	
	/**
	 * es el operador de salida, da el nombre.
	 *
	 * @return location
	 */
	public String toString() {
		
		if (world == null){
			return "Location{world=NULL,x=" + x + ",y=" +y + ",z=" + z +"}";
		}else {
			return "Location{world=" + world + ",x=" + x + ",y=" + y + ",z=" + z +"}";
		}
	}
	
	
	

	/**  atributo mundo. */
	private World world;
	
	
	/**  atributo x. */
	private double x;
	
	
	/** atributo y. */
	private double y;
	
	
	/** atributo z. */
	private double z;

	
	/** The upper y value. */
	public static final double UPPER_Y_VALUE=255.0;
	
	/** nivel del mar. */
	public static final double SEA_LEVEL=63.0;
	
	/**
	 * constructor 1 location.
	 * 
	 * @param w the w
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 */
	public Location(World w,double x,double y, double z) {
		world=w;
		setX(x);
		setY(y);
		setZ(z);
		
	}
	
	
	/**
	 * constructor 2 location.
	 *
	 * @param loc the loc
	 */
	public Location(final Location loc) {
		this.world = loc.world;
		this.x = loc.x;
		this.y = loc.y;
		this.z = loc.z;
	}


	/**
	 * obtiene el mundo.
	 *
	 * @return  world
	 */
	public World getWorld() {
		return world;
	}
	
	/**
	 * poner world.
	 *
	 * @param world the new world
	 */
	public void setWorld(World world) {
		this.world = world;
	}
	
	/**
	 * obtiene la x.
	 *
	 * @return the x
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * poner la x.
	 *
	 * @param x the new x
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * obtiene la y.
	 *
	 * @return  y
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * poner la y.
	 *
	 * @param y the new y
	 */
	public void setY(double y) {
		this.y = y ;
	}
	
	/**
	 * obtiene la z.
	 *
	 * @return the z
	 */
	public double getZ() {
		return z; 
	}
	
	/**
	 * poner la z.
	 *
	 * @param z the new z
	 */
	public void setZ(double z) {
		this.z = z;
	}
	
   /**
    * añade coordenadas a la localizacion.
    *
    * @param loc the loc
    * @return  location
    */
   public Location add(final Location loc) {
	   if(loc.world != world) {
		   System.err.println("Cannot add Locations of differing worlds.");
	   }else {
		   x += loc.x;
	        setY(y + loc.y);
	        z += loc.z;
	   }
	   
	return this;
	   
   }
   
   /**
    * dastancia .
    *
    * @param loc the loc
    * @return distancia
    */
   public double distance(final Location loc) {
	   if(loc.getWorld()==null || getWorld()==null) {
		   System.err.println("Cannot measure distance to a null world");
		   return -1.0;
	   }else {
		   if(loc.getWorld() != getWorld()){
			   System.err.println("Cannot measure distance between " + loc.world.getName() + " and " + loc.world.getName());  
			   return -1.0;
		   }
		   
	   }
	    double dx = x - loc.x;
	    double dy = y - loc.y;
	    double dz = z - loc.z;
	    return Math.sqrt(dx*dx + dy*dy + dz*dz);
	   
   }
   
   /**
    * tamaño.
    *
    * @return tamaño
    */
   public double length() {
	   return Math.sqrt(x*x + y*y + z*z);
   }
   
   /**
    * multiplicar location.
    *
    * @param factor the factor
    * @return producto
    */
   public Location multiply(double  factor) {
	   x *= factor;
	   setY(y * factor);
	   z *= factor;
	   return this;
   }
   
   /**
    * restar localizacion.
    *
    * @param loc the loc
    * @return resta
    */
   public Location substract(final Location loc) {
	    if (loc.world != world) 
	    	System.err.println("Cannot substract Locations of differing worlds.");
	    else {
	        x -= loc.x;
	        setY(y - loc.y);
	        z -= loc.z;
	        }
	   
	   return this;
   }
   
   /**
    * localizacion=cero.
    *
    * @return  (0,0,0)
    */
   public final Location zero() {
	  x=0;
	  y=0;
	  z=0;
	  return this;
   }


	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((world == null) ? 0 : world.hashCode());
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(z);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (world == null) {
			if (other.world != null)
				return false;
		} else if (!world.equals(other.world))
			return false;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z))
			return false;
		return true;
	}
	
	/**
	 * vemos si esta en los limites del mundo.
	 *
	 * @param world the world
	 * @param x the x
	 * @param y the y
	 * @param z the z
	 * @return true, si esta en los limites del mundo
	 */
	public static boolean  check(World world,double x,double y,double z){
		if(world!=null) {
			if(world.getSize()%2!=0){
				if(x <= world.getSize()/2 && x >= -(world.getSize()/2)) {
					if(y <= UPPER_Y_VALUE && y>=0.0) {
						if(z<= world.getSize()/2 && z >= -(world.getSize()/2)) {
							return true;
						}
					}
				}
				return false;
			}else {
				if(x <= (world.getSize()/2)+1 && x >= -((world.getSize()/2)-1)-1) {
					if(y <= UPPER_Y_VALUE && y>=0.0) {
						if(z<= (world.getSize()/2)+1 && z >= -((world.getSize()/2)-1)){
							return true;
						}
					}
				}
				return false;
			}
		}else {
			return true;
		}
		
	}
	
	/**
	 * vemos si esta en los limites del mundo.
	 *
	 * @param loc the loc
	 * @return true, si esta en los limites del mundo
	 */
	public static boolean  check(Location loc) {
		double x=loc.getX();
		double y=loc.getY();
		double z=loc.getZ();
		 
		
		if(loc.world!=null) {
			if(loc.world.getSize()%2!=0){//impar
				if(x <= loc.world.getSize()/2 && x >= -(loc.world.getSize()/2)) {
					if(y <= UPPER_Y_VALUE && y>=0.0) {
						if(z<= loc.world.getSize()/2 && z >= -(loc.world.getSize()/2)) {
							return true;
						}
					}
				}
				return false;	
			}
			else {//pares
				if(x <= (loc.world.getSize()/2) && x >= -((loc.world.getSize()/2)-1)) {
					if(y <= UPPER_Y_VALUE && y>=0.0) {
						if(z<= (loc.world.getSize()/2) && z >= -((loc.world.getSize()/2)-1)){
							return true;
						}
					}
				}
				return false;
			}
		}else {
			return true;
		}
		
	}
	
	
	/**
	 * devuelve la posicion de abajo.
	 *
	 * @return la posicion de abajo
	 * @throws BadLocationException the bad location exception
	 */
	public Location below() throws BadLocationException {
		if(y>0 || world==null) {
			Location l=new Location(this.world, this.x, this.y-1, this.z);
			return l;
		}else {
			throw new BadLocationException("BadLocationException");
		}
	}
	
	/**
	 * devuelve la posicion de arriba.
	 *
	 * @return la posicion de arriba
	 * @throws BadLocationException the bad location exception
	 */
	public Location above() throws BadLocationException {
		if(y<UPPER_Y_VALUE || world==null) {
			Location l=new Location(this.world, this.x, this.y+1, this.z);
			return l;
		}else {
			throw new BadLocationException("BadLocationException");
		}
		

		
		
	}
	
	
	/**
	 * devuelve las localizaciones vecinas.
	 *
	 * @return las localizaciones vecinas
	 */
	public Set<Location>getNeighborhood(){
		Set<Location> vecinos=new HashSet<Location>();
		for(int i=-1;i<2;i++) {//X
			for(int j=-1;j<2;j++) {//Y
				for(int k=-1;k<2;k++) {//Z
					Location coordenada=new Location(this.world,x+i,y+j,z+k);
					if((i!=0||j!=0||k!=0 )&& check(coordenada)) {
						vecinos.add(coordenada);
					}
				}
			}
		}
		return vecinos;
	}
	
	/**
	 * mira si esa posicion esta libre.
	 *
	 * @return true, si esa posicion esta libre
	 */
	public boolean isFree()  {
		if(this.world==null) {
			return false;
		}
		try {
			return this.world.isFree(this);
		} catch (BadLocationException e) {
			
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Compare to.
	 *
	 * @param loc2 the loc 2
	 * @return the int
	 */
	public int compareTo(Location loc2){
		Location loc1=this;
		if((loc1.x < loc2.x)||(loc1.x == loc2.x && loc1.y < loc2.y)||(loc1.x == loc2.x && loc1.y == loc2.y && loc1.z < loc2.z)) {//loc1 < loc2
			return -1;
		}else if(loc1.x == loc2.x && loc1.y == loc2.y && loc1.z == loc2.z){//loc1 = loc2
			return 0;
		}else {//loc1 > loc2
			return 1;
		}
	}
   
	
}
