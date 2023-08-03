import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents the vending machine
 */

public class RegularVendingMachine {
    protected Owner owner;
    protected String name;
    protected ArrayList<Slot> itemSlots;
    protected ArrayList<Transaction> transactions;
    protected ArrayList<Slot> lastRestockSlots;
    protected long lastRestockDate;
    protected int collectedMoney = 0;
    protected final static int[] validDenominations = { 1000, 500, 200, 100, 50, 20, 10, 5, 1 };
    protected Money[] bills = { new Money(1000, 0), new Money(500, 0), new Money(200, 0), new Money(100, 0),
            new Money(50, 0), new Money(20, 0), new Money(10, 0), new Money(5, 0), new Money(1, 0) };

    /**
     * This constructor initializes the owner of the vending machine, the slot
     * capacity and the item
     * capacity.
     * 
     * @param owner            which is the owner of the vending machine.
     * @param slotCapacity     which is the maximum number of slots the vending
     *                         machine can hold
     * @param slotItemCapacity which is the maximum number of items a slot can hold
     */

    public RegularVendingMachine(Owner owner, String name, int slotCapacity, int slotItemCapacity) {
        // Assign owner to attribute
        this.owner = owner;
        this.name = name;
        // Assign an array with the size slotCapacity to itemSlots
        // slotCapacity should be > 8, itemCapacity should be > 10
        this.itemSlots = new ArrayList<Slot>(slotCapacity);
        // Initializes an array for transactions variable
        this.transactions = new ArrayList<Transaction>();
        this.lastRestockSlots = new ArrayList<Slot>(slotCapacity);
        this.lastRestockDate = new Date().getTime();

        // Loops over the array and create new Slot
        for (int i = 0; i < slotCapacity; i++) {
            itemSlots.add(new Slot(slotItemCapacity));
        }

        // Loops over the array and create new Slot
        for (int i = 0; i < slotCapacity; i++) {
            lastRestockSlots.add(new Slot(slotItemCapacity));
        }
    }

    /**
     * This method gets the quantity of an item from the vending machine by taking
     * the itemName, quantity, and payment as its parameters.
     * 
     * @param itemName which is the name of the item in the vending machine
     * @param quantity which is how many items will be bought by the user
     * @param payment  which is the amount entered by the user
     *
     */

    public Item dispenseItem(String itemName, int quantity, int payment) {
        Item item = null;
        Slot itemSlot = null;
        int itemPrice = 0;
        int itemQuantity = 0;
        int change = 0;
        // Search through the slots to know which slot the item belong
        // If item doesn't belong to any slot, print error, return null.
        // If it does, get the price of the item
        for (Slot slot : this.itemSlots) {
            if (slot.getSlotItemType().getName().equals(itemName)) {
                itemSlot = slot;
                itemPrice = slot.getPrice();
                itemQuantity = slot.getItems().size();
                item = slot.getSlotItemType();
                break;
            }
        }

        if (itemQuantity < quantity) {
            System.out.println("Quantity exceeds the stock for the item.");
            return null;
        }

        if (payment < (itemPrice * quantity)) {
            System.out.println("Your payment is not enough for this item.");
            return null;
        }

        change = payment - (itemPrice * quantity);

        // Validate if amount is less or equal to the quantity of the slot where the
        // item is
        // If it is greater, than means that you can't get any more than what is in the
        // slot
        // This cannot accept < 0 values

        // When payment is valid (greater than 0), verify if price of item times the
        // amount is less or equal to payment.
        // Execute receivePayment to distribute the money to respective denominations.
        // Execute dispenseChange to dispense change (if any) from respective
        // denominations.
        // Create a new Transaction and put the necessary information in the Constructor
        int amountCost = quantity * itemPrice;
        boolean dispenseChangeSuccess = dispenseChange(payment, amountCost);

        if (dispenseChangeSuccess) {
            // Push the new Transaction in the transactions array
            Transaction newTransaction = new Transaction(item, quantity, amountCost);
            newTransaction.displayTransaction();
            transactions.add(newTransaction);
            // If everything checks out and everything is done, return the Item
            // Remove the item(s) from their slot
            itemSlot.removeStock(quantity);
            return item;
        }

        return null;
    }

    /**
     * This method gets the total number of bills or coins the payment
     * contains
     * 
     * @param payment which is the amount entered by the user
     *
     */

    public void receivePayment(int payment) {
        // Starts from the highest denomination to the lowest.
        // After modulo operation, subtract the result times the denomination value.
        for (Money money : bills) {
            int addedValue = payment / money.value;
            money.addAmount(addedValue);
            payment -= (addedValue) * money.value;
        }
    }

    /**
     * This method gets the amount of change to be given back to the
     * user
     * 
     * @param amountPaid which is the amount paid by the user
     * @param amountCost which is the cost of the item bought.
     *
     */

    public boolean dispenseChange(int amountPaid, int amountCost) {
        int changeRemaining = amountPaid - amountCost;
        int tempChange = amountPaid - amountCost;
        int changeDispensed = 0;

        for (Money money : bills) {
            int subtractedValue = changeRemaining / money.value;
            boolean success = money.subtractAmount(subtractedValue);

            if (success) {
                changeDispensed += subtractedValue * money.value;
                tempChange -= subtractedValue * money.value;
            }
        }

        if (changeDispensed < changeRemaining) {
            System.out.println("Vending machine money is not enough to dispense change for your transaction.");
            return false;
        }

        System.out.println("Total change dispensed: " + changeDispensed);
        return true;
    }

    /**
     * This method informs the user of the number of calories an item
     * contains.
     * 
     * @param name which represents the item name in the vending machine
     * @return calories of the item
     *
     */

    public double getCalories(String name) {
        for (int i = 0; i < itemSlots.size(); ++i) {
            Item currSlotItemType = itemSlots.get(i).getSlotItemType();

            if (currSlotItemType.getName().equals(name))
                return currSlotItemType.getCalories();
        }

        return (double) 0;
    }

    /**
     * This method gets the summary of transactions.
     *
     */

    public void getTransactionSummary() {
        int totalSales = 0;
        System.out.println("-------------------------------------------------");
        System.out.println("TRANSACTION SUMMARY");
        System.out.println("-------------------------------------------------");

        if (this.transactions.size() == 0) {
            System.out.println("No transactions found.");
        }

        for (Transaction currTransaction : this.transactions) {
            if (currTransaction.getTransactionDate() > lastRestockDate) {
                String itemPurchased = currTransaction.getItemPurchased().getName();
                int amountPurchased = currTransaction.getPurchaseAmount();
                int totalSale = currTransaction.getTotalSales();

                Date date = new Date(this.lastRestockDate);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = formatter.format(date);

                System.out
                        .println(itemPurchased + " x" + amountPurchased + " - P" + totalSale + " Date: "
                                + formattedDate);
                totalSales += currTransaction.getTotalSales();
            }
        }

        System.out.println("-------------------------------------------------");
        System.out.println("Total sales: P" + totalSales);
        System.out.println("-------------------------------------------------\n\n");
    }

    /**
     * This method saves the array for inventory in a variable whenever there is a
     * restock. This also displays the starting inventory and the current inventory
     * by displaying all of the slots
     *
     */

    public void displayInventory() {
        Date date = new Date(this.lastRestockDate);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = formatter.format(date);

        System.out.println("-------------------------------------------------");
        System.out.println("Inventory from last restock");
        System.out.println("Last restock date: " + formattedDate);

        for (Slot slot : lastRestockSlots) {
            slot.display();
        }

        System.out.println("-------------------------------------------------");

        System.out.println("Inventory from current stock");
        this.displayStock();

        System.out.println("-------------------------------------------------");
    }

    /**
     * This method displays the current stock of the vending machine.
     */
    public void displayStock() {
        for (Slot itemSlot : itemSlots) {
            itemSlot.display();
        }
    }

    /**
     * This method gets the slots of the item
     * 
     * @return itemSlots
     *
     */

    public ArrayList<Slot> getItemSlots() {
        return this.itemSlots;
    }

    /**
     * This method gets the transactions from the Transaction arraylist
     * 
     * @return transactions
     *
     */

    public ArrayList<Transaction> getTransactions() {
        return this.transactions;
    }

    /**
     * This method gets the total amount of collected money
     * 
     * @return the last restock date in long form.
     *
     */

    public long getLastRestockDate() {
        return this.lastRestockDate;
    }

    /**
     * This method gets the total amount of collected money
     * 
     * @return collected
     *
     */

    public int getCollectedMoney() {
        int collected = 0;
        for (Money money : bills) {
            collected += money.collectValue();
        }
        return collected;
    }

    /**
     * This method gets money stock of the vending machine
     *
     */
    public int getStockMoney() {
        int totalStockMoney = 0;
        for (Money money : bills) {
            totalStockMoney += money.getValue() * money.getAmount();
        }
        return totalStockMoney;
    }

    public Money[] getMoneyArray() {
        return this.bills;
    }

    public Money getMoney(int denomination) {
        for (Money money : bills) {
            if (money.getValue() == denomination) {
                return money;
            }
        }

        return null;
    }

    /**
     * This method checks if a denomination is valid. If the denomination is valid,
     * it adds the denomination to the amount
     * 
     * @param amount       which will be inserted/entered to the vending
     *                     machine.
     * @param denomination which are the money denomination
     *
     */

    public void setMoney(int amount, int denomination) {
        for (Money money : bills) {
            if (money.getValue() == denomination) {
                money.addAmount(amount);
                return;
            }
        }
        System.out.println("No such denomination exists.");
    }

    /**
     * This method updates the last restock date and the restock slots of the last
     * restock.
     *
     */
    public void updateLastRestock() {
        this.lastRestockDate = new Date().getTime();
        for (int i = 0; i < itemSlots.size(); ++i) {
            if (itemSlots.get(i).getSlotItemType() != null) {
                int currSlotSize = this.itemSlots.get(i).getItemQuantity();
                int currLastRestockSlotSize = this.lastRestockSlots.get(i).getItemQuantity();

                if (lastRestockSlots.get(i).getSlotItemType() == null) {
                    lastRestockSlots.get(i).stockSlot(this.itemSlots.get(i).getSlotItemType(), currSlotSize);
                }

                if (currSlotSize > currLastRestockSlotSize) {
                    lastRestockSlots.get(i).restockSlot(currSlotSize - currLastRestockSlotSize);
                } else if (currSlotSize < currLastRestockSlotSize) {
                    lastRestockSlots.get(i).removeStock(currLastRestockSlotSize - currSlotSize);
                }

            }
        }
    }

    /**
     * This method uses a loop to check if a denomination is valid. If it is valid,
     * it returns true. Otherwise, it returns false
     * 
     * @param denomination which are the money denomination
     *
     */

    public boolean denominationIsValid(int denomination) {
        for (int i = 0; i < validDenominations.length; ++i) {
            if (validDenominations[i] == denomination)
                return true;
        }
        return false;
    }

    /**
     * This method gets the owner of the vending machine
     *
     */
    public Owner getOwner() {
        return this.owner;
    }

    /**
     * This method gets the name of the vending machine
     *
     */
    public String getName() {
        return this.name;
    }

    /**
     * This method gets the item for a slot
     * 
     * @param name which is the name of the item
     * @return slot of the item
     */
    public Item getItem(String name) {
        for (Slot itemSlot : this.itemSlots) {
            Item slotItem = itemSlot.getSlotItemType();
            if (slotItem != null) {
                if (slotItem.getName().equals(name)) {
                    return slotItem;
                }
            }
        }
        return null;
    }

    /**
     * This method displays the money of the vending machine
     * 
     * @param denomination which is the denomination of the money in the vending
     *                     machine
     *
     */
    public void displayMoney(int denomination) {
        for (Money money : bills) {
            if (money.getValue() == denomination) {
                money.display();
                return;
            }
        }
        System.out.println("No such denomination exists.");
    }

    /**
     * This method checks each slot in the vending machine & checks whether the 
     * items inside the slot has a name equal to the itemName parameter. If it does, then
     * the slot will be restocked and the slot will be updated.
     * 
     * Restocking is only possible if the slot was not empty in the first place. 
     * 
     * @param itemName which is the name of the item in the vending machine 
     * @param amount which is the quantity of the item 
     * @return boolean 
     */
    public boolean restockSlot(String itemName, int amount) {
        for (Slot slot : this.getItemSlots()) {
            if (!slot.isEmpty()) {
                if (slot.getSlotItemType().getName().equals(itemName)) {
                    boolean status = slot.restockSlot(amount);
                    if (status == true) // If successful
                    {
                        this.updateLastRestock();
                        return true;
                    }
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * This method adds a stock to the vending machine by finding the first 
     * empty slot and puts the new item there. 
     * 
     * It also updates the last restock slots. 
     * @param item which is the item to be added  
     * @param amount which is the quantity of the item
     * @return boolean 
     */
    public boolean stock(Item item, int amount) {
        for (Slot slot : this.getItemSlots()) {
            if (slot.isEmpty()) {
                boolean status = slot.stockSlot(item, amount);
                if (status == true) // If successful
                {
                    this.updateLastRestock();
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    /**
     * This method sets the price of the item in the vending machine 
     * @param itemName which is the name of the item in the vending machine 
     * @param itemPrice which is the price of the item
     * @return boolean
     */
    public boolean setPrice(String itemName, int itemPrice) {
        for (Slot slot : this.getItemSlots()) {
            if (!slot.isEmpty()) {
                if (slot.getSlotItemType().getName().equals(itemName)) {
                    slot.setPrice(itemPrice);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "\n================================\n" + this.name + "\nRegular Vending Machine\n"
                + "================================";
    }
}