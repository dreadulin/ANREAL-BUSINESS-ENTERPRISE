import java.util.ArrayList;

/**
 * This class represents the slots of the items.
 */

public class Slot {
    private ArrayList<Item> items;
    private int price = 100;
    private int itemCapacity = 0;

   /**
     * This constructor takes the itemCapacity as its parameter
     * 
     * @param itemCapacity which identifies how many slots an item
     *                     can hold
     */
  
  public Slot(int itemCapacity) {
        this.items = new ArrayList<Item>(itemCapacity);
        this.itemCapacity = itemCapacity;
    }
  
    /**
     * This constructor takes various parameters: price and
     * itemCapacity
     * 
     * @param price        which is the price of the item of the vending
     *                     machine
     * @param itemCapacity which identifies how many slots an item
     *                     can hold
     */

    public Slot(int price, int itemCapacity) {
        // Assign the price to the attribute.
        this.price = price;
        // Assign an array with an itemCapacity to items.
        this.items = new ArrayList<Item>(itemCapacity);
        this.itemCapacity = itemCapacity;
    }

    /**
     * This method gets the capacity of the slots. If it is not full, it pushes
     * the item to the array. Otherwise, it notifies the owner that it is full.
     * 
     * @param item which is the item of the vending machine
     * @amount which is the amount of items to put in the vending machine
     */

    public void restockSlot(Item item, int amount) {
        while (amount > 0 && !items.isEmpty()) {
            items.add(item);
            amount--;
        }
    }

    /**
     * This method removes a certain amount of items in the slot.
     * 
     * @param item which is the item of the vending machine
     * @amount which is the amount of items to remove in the vending machine
     */
    public void removeStock(Item item, int amount) {
        while (amount > 0 && !items.isEmpty()) {
            items.remove(item);
            amount--;
        }
    }

    /**
     * This method sets the price of the item.
     * 
     * @param newPrice which is the updated price of the time
     *
     */
    public void setPrice(int newPrice) {
        this.price = newPrice;
    }

    /**
     * This method gets the price of the item.
     * 
     * @return price which is how much the item costs
     *
     */

    public int getPrice() {
        return this.price;
    }

    /**
     * This method gets the item array of the slot
     * 
     * @return items
     *
     */

    public ArrayList<Item> getItems() {
        return this.items;
    }

    /**
     * This method gets the availability of the item
     * 
     * @return length of the item
     *
     */

    public boolean isAvailable() {
        return items.size() > 0;
    }

    /**
     * This method gets the item that is being sold in a slot.
     * 
     * @return items at index 0
     *
     */

    public Item getSlotItemType() {
        return this.items.get(0);
    }

    /**
     * This method determines if a slot if empty
     * 
     * @return the size of item which is equals to zero.
     *
     */
    public boolean isEmpty() {
        return items.size() == 0;
    }

    /**
     * This method determines if a slot is full 
     * 
     * @return item size which is the item's capacity
     *
     */
    public boolean isFull() {
        return items.size() == itemCapacity;
    }

    /**
     * Overrides toString method of object
     * 
     * @return itemName and length of items
     *
     */

    @Override
    public String toString() {
        String itemName = this.getSlotItemType().getName();
        return itemName + " - " + this.items.size();
    }
}
