package model;

import model.exceptions.BadInventoryPositionException;
import model.exceptions.BadLocationException;
import model.exceptions.EntityIsDeadException;
import model.exceptions.StackSizeException;

// TODO: Auto-generated Javadoc
/**
 * The Class Player.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public class Player {
	
	
	/** el name. */
	private String name;
	
	/** la health. */
	private double health;
	
	/** el food level. */
	private double foodLevel;
	
	/** la Constant MAX_FOODLEVEL. */
	public static final double MAX_FOODLEVEL=20;
	
	/** la Constant MAX_HEALTH. */
	public static final double MAX_HEALTH=20;
	
	/** la location. */
	private Location location;
	
	/** el inventory. */
	private Inventory inventory;
	
	/**
	 * decrementa el comida/vida X`s unidades dadas.
	 *
	 * @param d the d
	 */
	private void decreaseFoodLevel(double d){
			if(foodLevel-d<=0) {
				health+=(foodLevel-d);//+ porque es negativo
				foodLevel=0;
				
			}
			else {
				foodLevel-=d;
			}
		
	}
	 
	/**
	 * incrementa comida/vida X`s unidades dadas.
	 *
	 * @param i the i
	 */
	private void increseFoodLevel(double i) {
		if(foodLevel+i<=MAX_FOODLEVEL) {
			foodLevel+=i;
		}else {
			foodLevel+=i;
			health+=foodLevel-MAX_FOODLEVEL;
			if(health>MAX_HEALTH) {
				health=MAX_HEALTH;
			}
			foodLevel=MAX_FOODLEVEL;
		}
		
		
		
		
	}
	
	/**
	 * constructor de player.
	 *
	 * @param name the name
	 * @param world the world
	 */ 
	public Player(String name,World world){
		Location l=new Location(world, 0,0 , 0);
		try {
			 l=new Location (world.getHighestLocationAt(l));
			 this.location=l.above();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		this.foodLevel=Player.MAX_FOODLEVEL;
		this.health=Player.MAX_HEALTH;
		this.name =name;
		
		Inventory inventario= new Inventory();
		
		try {
			ItemStack item = new ItemStack( Material.WOOD_SWORD,  1);
			inventario.setItemInHand(item);
			this.inventory=inventario;
		} catch (StackSizeException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	/**
	 * obtiene la vida.
	 *
	 * @return la health
	 */
	public double getHealth() {
		return health;
	}
	
	/**
	 * obtiene la localizacion.
	 *
	 * @return la location
	 */
	public Location getLocation() {
		Location l= new Location(this.location.getWorld(),
				this.location.getX(), this.location.getY(), this.location.getZ());
		return l;
	}

	/**
	 * pone la vida dada, saturando el maximo.
	 *
	 * @param health the new health
	 */
	public void setHealth(double health) {
		
		if(health<=MAX_HEALTH) {
			this.health=health;
		}else {
			this.health=MAX_HEALTH;
		}
	}
	
	/**
	 * obtiene  la comida.
	 *
	 * @return the food level
	 */
	public double getFoodLevel() {
		return foodLevel;
	}
	
	/**
	 * pone la comida dada, saturando el maximo.
	 *
	 * @param foodLevel the new food level
	 */
	public void setFoodLevel(double foodLevel) {
		if(foodLevel<=MAX_FOODLEVEL) {
			this.foodLevel=foodLevel;
		}else {
			this.foodLevel=MAX_FOODLEVEL;
		}
	}
	
	/**
	 * obtiene el nombre.
	 *
	 * @return el name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * mira si ha fallecido .
	 *
	 * @return true, si esta muerto
	 */
	public boolean isDead() {
		if(this.getHealth()<=0) {
			return true;
		}
		return false;
		
	}
	
	/**
	 * obtiene el tamaño del inventario .
	 *
	 * @return tamaño
	 */
	public int getInventorySize() {
		return this.inventory.getSize();
		
	}
	
	
	/**
	 * mueve el jugador de un sitio a su posicion adyacente.
	 *
	 * @param dx the dx
	 * @param dy the dy
	 * @param dz the dz
	 * @return the location
	 * @throws EntityIsDeadException the entity is dead exception
	 * @throws BadLocationException the bad location exception
	 */
	public Location move(int dx,int dy,int dz) throws EntityIsDeadException, BadLocationException {
		if(!this.isDead()) {
			Location loc=new Location(this.location.getWorld(),this.location.getX()+dx,this.location.getY()+dy,this.location.getZ()+dz);
			
			if(this.location.getNeighborhood().contains(loc)&&loc.isFree()){
				decreaseFoodLevel(0.05);
				this.location=loc;
				return loc;
			}else {
				throw new BadLocationException("BadLocationException");
			}
		}else {
			throw new EntityIsDeadException();
		}
	
		
	}
	
	
	/**
	 * usa el item de la mano.
	 *
	 * @param time the time
	 * @throws EntityIsDeadException the entity is dead exception
	 */
	public void useItemInHand(int time) throws EntityIsDeadException  {
		if(time<=0) {
			throw new IllegalArgumentException();
		}
		if(isDead()) {
			throw new EntityIsDeadException();
		}
		if(this.inventory.getItemInHand()!=null) {
			if(this.inventory.getItemInHand().getType().isEdible()) {
				
				if(time>=this.inventory.getItemInHand().getAmount()) {
					int veces=time;
					if(time>this.inventory.getItemInHand().getAmount()) {
						veces=this.inventory.getItemInHand().getAmount();
					}
					increseFoodLevel(this.inventory.getItemInHand().getType().getValue()*veces);
					this.inventory.setItemInHand(null);
				}else {
					try {
						increseFoodLevel(time*this.inventory.getItemInHand().getType().getValue());
						this.inventory.getItemInHand().setAmount(this.inventory.getItemInHand().getAmount()-time);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}else {
					decreaseFoodLevel(0.1*time);
				
		}
		
			
			
		}

		
	}
	
	
	/**
	 * coge el item de la posicion del inventario.
	 *
	 * @param pos the pos
	 * @throws BadInventoryPositionException the bad inventory position exception
	 */
	public void selectItem(int pos) throws BadInventoryPositionException  {
		if(pos<0||pos>=this.inventory.getSize()) {
			throw new BadInventoryPositionException(pos);
		}
		ItemStack mochila,mano;
		mochila=this.inventory.getItem(pos);
		mano=this.inventory.getItemInHand();
		
		if(mano==null) {
			this.inventory.setItemInHand(mochila);
			try {
				this.inventory.clear(pos);
			} catch (BadInventoryPositionException e) {
				e.printStackTrace();
			}
		}else {
			this.inventory.setItemInHand(mochila);
			try {
				this.inventory.setItem(pos, mano);
			} catch (BadInventoryPositionException e) {
				e.printStackTrace();
			}
		}	
	}
	
	/**
	 * añade un item al inventario.
	 *
	 * @param items the items
	 */
	public void addItemsToInventory(ItemStack items) {
		this.inventory.addItem(items);
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(foodLevel);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(health);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((inventory == null) ? 0 : inventory.hashCode());
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (Double.doubleToLongBits(foodLevel) != Double.doubleToLongBits(other.foodLevel))
			return false;
		if (Double.doubleToLongBits(health) != Double.doubleToLongBits(other.health))
			return false;
		if (inventory == null) {
			if (other.inventory != null)
				return false;
		} else if (!inventory.equals(other.inventory))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Name="+getName()+"\n"+location.toString()+"\n"+"Health="
				+getHealth()+"\n"+"Food level="+getFoodLevel()+"\n"+"Inventory="+inventory.toString() ;
	}
	
	
	
	
	
	
	

}
