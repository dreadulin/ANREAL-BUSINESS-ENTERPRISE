package vendingmachine;

import java.util.ArrayList;

/**
 * This class represents the slots of the items.
 */
public class Slot {

    private ArrayList<Item> items;
    private int itemCapacity = 0;

    /**
     * This constructor takes the itemCapacity as its parameter
     *
     * @param itemCapacity which identifies how many slots an item can hold
     */
    public Slot(int itemCapacity) {
        this.items = new ArrayList<Item>(itemCapacity);
        this.itemCapacity = itemCapacity;
    }

    public boolean restockSlot(int amount) {
        if (this.isFull()) {
            System.out.println("Cannot insert any more items in this slot.");
            return false;
        }
        Item slotItem = this.getSlotItemType();
        while (amount > 0 && !this.isFull()) {
            this.items.add(slotItem);
            amount--;
        }
        return true;
    }

    /**
     * This method gets the capacity of the slots. If it is not full, it pushes
     * the item to the array. Otherwise, it notifies the owner that it is full.
     *
     * @param item which is the item of the vending machine
     * @amount which is the amount of items to put in the vending machine
     */
    public boolean stockSlot(Item item, int amount) {
        if (this.isFull()) {
            System.out.println("Cannot insert any more items in this slot.");
            return false;
        }
        while (amount > 0 && !this.isFull()) {
            this.items.add(item);
            amount--;
        }
        return true;
    }

    /**
     * This method removes a certain amount of items in the slot.
     *
     * @param item which is the item of the vending machine
     * @amount which is the amount of items to remove in the vending machine
     */
    public void removeStock(int amount) {
        Item slotItem = this.getSlotItemType();
        while (amount > 0 && !this.isEmpty()) {
            items.remove(slotItem);
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
        this.getSlotItemType().setPrice(newPrice);
    }

    /**
     * This method gets the price of the item.
     *
     * @return price which is how much the item costs
     *
     */
    public int getPrice() {
        return this.getSlotItemType().getPrice();
    }

    public int getItemQuantity() {
        int quantity = 0;
        for (Item item : this.items) {
            if (item != null) {
                quantity += 1;
            }
        }
        return quantity;
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
        if (this.isEmpty()) {
            return null;
        }
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

    public void display() {
        if (this.isEmpty()) {
            System.out.println("================");
            System.out.println("|| Empty Slot ||");
            System.out.println("================");
            return;
        }

        String itemName = this.getSlotItemType().getName();
        int itemQt = this.getItems().size();
        double itemCalories = this.getSlotItemType().getCalories();

        displayLines(itemName);

        System.out.println("|| " + itemName + " ||");

        displayLines(itemName);

        System.out.println(itemQt + "x");
        System.out.println(itemCalories + " cal");
        System.out.println("P" + this.getPrice());

        displayLines(itemName);
    }

    private void displayLines(String itemName) {
        int lineLength = itemName.length() + 6;
        while (lineLength > 0) {
            System.out.print("=");
            lineLength--;
        }
        System.out.println();
    }
}
