package model.entities;

import model.Inventory;
import model.ItemStack;
import model.Location;
import model.Material;
import model.World;
import model.exceptions.BadInventoryPositionException;
import model.exceptions.BadLocationException;
import model.exceptions.EntityIsDeadException;
import model.exceptions.StackSizeException;

/**
 * The Class Player.
 * 
 * @author FRANNCISCO JOAQUN MURCIA GOMEZ 48734281H
 */
public class Player extends LivingEntity {
	
	
	/** el name. */
	private String name;
	
	
	/** el food level. */
	private double foodLevel;
	
	/** la Constant MAX_FOODLEVEL. */
	public static final double MAX_FOODLEVEL=20;
	
	/** el inventory. */
	private Inventory inventory;
	
	/** The symbol. */
	private static char symbol='P';
	
	/** The orientation. */
	private Location orientation;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(foodLevel);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((inventory == null) ? 0 : inventory.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((orientation == null) ? 0 : orientation.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (Double.doubleToLongBits(foodLevel) != Double.doubleToLongBits(other.foodLevel))
			return false;
		if (inventory == null) {
			if (other.inventory != null)
				return false;
		} else if (!inventory.equals(other.inventory))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orientation == null) {
			if (other.orientation != null)
				return false;
		} else if (!orientation.equals(other.orientation))
			return false;
		return true;
	}

	/**
	 * decrementa el comida/vida X`s unidades dadas.
	 *
	 * @param d the d
	 */
	private void decreaseFoodLevel(double d){
			if(foodLevel-d<=0) {
				setHealth(getHealth()+(foodLevel-d));//+ porque es negativo
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
			setHealth(getHealth()+foodLevel-MAX_HEALTH);
			if(getHealth()>MAX_HEALTH) {
				setHealth(MAX_FOODLEVEL);
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
		super(new Location(world,0,0,0),LivingEntity.MAX_HEALTH);
		try {
			 super.location=new Location (world.getHighestLocationAt(super.location));
			 super.location=this.getLocation().above();
			 this.orientation=new Location(world, 0, 0, 1);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		this.foodLevel=Player.MAX_FOODLEVEL;
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
		if(!super.isDead()) {
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
	 * @return the item stack
	 * @throws EntityIsDeadException the entity is dead exception
	 */
	public ItemStack useItemInHand(int time) throws EntityIsDeadException  {
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
					//se la come toda la comida
					increseFoodLevel(this.inventory.getItemInHand().getType().getValue()*veces);
					this.inventory.setItemInHand(null);
					return this.inventory.getItemInHand();
				}else {
					try {
						//se come parte
						increseFoodLevel(time*this.inventory.getItemInHand().getType().getValue());
						this.inventory.getItemInHand().setAmount(this.inventory.getItemInHand().getAmount()-time);
						return this.inventory.getItemInHand();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}else {
				//no comida
				decreaseFoodLevel(0.1*time);
				return this.inventory.getItemInHand();
			}
		}
		return null;
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
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		
		return "Name="+getName()+"\n"+getLocation().toString()+"\nOrientation="+orientation.toString()+"\n"+"Health="
				+getHealth()+"\n"+"Food level="+getFoodLevel()+"\n"+"Inventory="+inventory.toString() ;
	}
	
	
	/**
	 * coge el simbolo.
	 *
	 * @return the symbol
	 */
	public char getSymbol(){
		return symbol;
		
	}
	
	
	/**
	 * te orienta.
	 *
	 * @param dx the dx
	 * @param dy the dy
	 * @param dz the dz
	 * @return the location
	 * @throws EntityIsDeadException the entity is dead exception
	 * @throws BadLocationException the bad location exception
	 */
	public Location orientate(int dx, int dy, int dz) throws EntityIsDeadException, BadLocationException {
		if(isDead())throw new EntityIsDeadException();
		Location loc=new Location(location.getWorld(), dx, dy, dz);
		
		if((dx==0&&dy==0&&dz==0) || !(dx>=-1&&dx<=1) || !(dy>=-1&&dy<=1) || !(dz>=-1&&dz<=1)) throw new BadLocationException("BadLocationException");
		this.orientation=loc;
		Location l = new Location(location.getWorld(), location.getX()+dx, location.getY()+dy, location.getZ()+dz);
		return l;	
	}

	/**
	 * obtiene la orientacion
	 *
	 * @return the orientation
	 */
	public Location getOrientation() {
		Location loc = new Location(location.getWorld(), location.getX()+orientation.getX(), location.getY()+orientation.getY(), location.getZ()+orientation.getZ());
		return loc;
		
	}
	
	/**
	 * obtiene el inventario.
	 *
	 * @return the inventario
	 */
	public Inventory getInventory() {
		return this.inventory;
	}
	
	

}
