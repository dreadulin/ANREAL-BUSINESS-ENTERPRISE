import java.util.ArrayList;
import java.util.Date;

/**
 * This class represents the vending machine
 */

public class RegularVendingMachine {
    private Owner owner;
    private String name;
    private ArrayList<Slot> itemSlots;
    private ArrayList<Transaction> transactions;
    private ArrayList<Slot> lastRestockSlots;
    private long lastRestockDate;
    private int collectedMoney = 0;
    private int itemCapacity = 0;
    private int slotCapacity = 0;

    // Valid denominations
    private final static int[] validDenominations = { 1000, 500, 200, 100, 50, 20, 10, 5, 1 };

    // Valid denominations were set to have a default value of 0 at the start of the
    // program.
    private int thousandPesos = 0;
    private int fiveHundredPesos = 0;
    private int twoHundredPesos = 0;
    private int oneHundredPesos = 0;
    private int fiftyPesos = 0;
    private int twentyPesos = 0;
    private int tenPesos = 0;
    private int fivePesos = 0;
    private int onePesos = 0;

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
        this.lastRestockSlots = itemSlots;

        this.itemCapacity = slotItemCapacity;
        this.slotCapacity = slotCapacity;

        // Loops over the array and create new Slot
        for (int i = 0; i < slotCapacity; i++) {
            itemSlots.add(new Slot(slotItemCapacity));
        }
    }

    /**
     * This method gets the quantity of an item from the vending machine by taking
     * the
     * itemName, quantity, and payment as its parameters.
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

        if (payment < itemPrice) {
            System.out.println("Your payment is not enough for this item.");
            return null;
        }

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
        System.out.println("Receiving payment...");
        receivePayment(payment);
        dispenseChange(payment, amountCost);

        // Push the new Transaction in the transactions array
        Transaction newTransaction = new Transaction(item, amountCost, amountCost);
        transactions.add(newTransaction);
        // If everything checks out and everything is done, return the Item
        // Remove the item(s) from their slot
        itemSlot.removeStock(item, quantity);
        return item;
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
        thousandPesos += (payment / 1000);
        System.out.println("Number of 1K bills: " + thousandPesos);

        payment -= thousandPesos * 1000;
        fiveHundredPesos += (payment / 500);
        System.out.println("Number of 500 bills: " + fiveHundredPesos);

        payment -= fiveHundredPesos * 500;
        twoHundredPesos += (payment / 200);
        System.out.println("Number of 200 bills: " + twoHundredPesos);

        payment -= twoHundredPesos * 200;
        oneHundredPesos += (payment / 100);
        System.out.println("Number of 100 bills: " + oneHundredPesos);

        payment -= oneHundredPesos * 100;
        fiftyPesos += (payment / 50);
        System.out.println("Number of 50 bills: " + fiftyPesos);

        payment -= fiftyPesos * 50;
        twentyPesos += (payment / 20);
        System.out.println("Number of 20 bills: " + twentyPesos);

        payment -= twentyPesos * 20;
        tenPesos += (payment / 10);
        System.out.println("Number of 10 coins: " + tenPesos);

        payment -= tenPesos * 10;
        fivePesos += (payment / 5);
        System.out.println("Number of 5 coins: " + fivePesos);

        payment -= fivePesos * 5;
        onePesos += (payment / 1);
        System.out.println("Number of 1 coins: " + onePesos);
        payment -= fivePesos * 1;
    }

    /**
     * This method gets the amount of change to be given back to the
     * user
     * 
     * @param amountPaid which is the amount paid by the user
     * @param amountCost which is the cost of the item bought.
     *
     */

    public int dispenseChange(int amountPaid, int amountCost) {
        int change = amountCost - amountPaid;

        thousandPesos -= change / 1000;
        System.out.println(change / 1000 != 0 ? "Dispensing " + change / 1000 + "1k bills as change..." : "");

        change -= thousandPesos * 1000;
        fiveHundredPesos -= (change / 500);
        System.out.println(change / 500 != 0 ? "Dispensing " + change / 500 + " 500 bills as change..." : "");

        change -= fiveHundredPesos * 500;
        twoHundredPesos -= (change / 200);
        System.out.println(change / 200 != 0 ? "Dispensing " + change / 200 + " 200 bills as change..." : "");

        change -= twoHundredPesos * 200;
        oneHundredPesos -= (change / 100);
        System.out.println(change / 100 != 0 ? "Dispensing " + change / 100 + " 100 bills as change..." : "");

        change -= oneHundredPesos * 100;
        fiftyPesos -= (change / 50);
        System.out.println("Number of 50 bills: " + fiftyPesos);
        System.out.println(change / 50 != 0 ? "Dispensing " + change / 50 + " 50 bills as change..." : "");

        change -= fiftyPesos * 50;
        twentyPesos -= (change / 20);
        System.out.println("Number of 20 bills: " + twentyPesos);
        System.out.println(change / 20 != 0 ? "Dispensing " + change / 20 + " 20 bills as change..." : "");

        change -= twentyPesos * 20;
        tenPesos -= (change / 10);
        System.out.println("Number of 10 coins: " + tenPesos);
        System.out.println(change / 10 != 0 ? "Dispensing " + change / 10 + " 10 peso coins as change..." : "");

        change -= tenPesos * 10;
        fivePesos -= (change / 5);
        System.out.println("Number of 5 coins: " + fivePesos);
        System.out.println(change / 5 != 0 ? "Dispensing " + change / 5 + " 5 peso coins as change..." : "");

        change -= fivePesos * 5;
        onePesos -= change;
        System.out.println("Number of 1 coins: " + onePesos);
        System.out.println(change != 0 ? "Dispensing " + change + " 1 peso coins as change..." : "");
        change -= fivePesos * 1;

        System.out.println("Total change dispensed: " + change);
        return change;
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

        for (Transaction currTransaction : this.transactions) {
            if (currTransaction.getTransactionDate() > lastRestockDate) {
                String itemPurchased = currTransaction.getItemPurchased().getName();
                int amountPurchased = currTransaction.getPurchaseAmount();
                System.out.println(itemPurchased + " x" + amountPurchased);
                totalSales += currTransaction.getTotalSales();
            }
        }

        System.out.println("-------------------------------------------------");
        System.out.println("Total sales: " + totalSales);
        System.out.println("-------------------------------------------------\n\n");
    }

    /**
     * This method saves the array for inventory in a variable whenever there is a
     * restock. This also displays the starting inventory and the current inventory
     * by displaying all of the slots
     *
     */

    public void displayInventory() {
        System.out.println("-------------------------------------------------");
        System.out.println("Inventory from last restock");

        for (int i = 0; i < lastRestockSlots.size(); ++i) {
            System.out.println(lastRestockSlots.get(i).toString());
        }

        System.out.println("-------------------------------------------------");

        System.out.println("Inventory from current stock");
        for (int i = 0; i < itemSlots.size(); ++i) {
            System.out.println(itemSlots.get(i).toString());
        }

        System.out.println("-------------------------------------------------");
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
        int collected = this.collectedMoney;
        this.collectedMoney = 0;
        return collected;
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
        boolean validDenom = this.denominationIsValid(denomination);
        if (validDenom) {
            switch (denomination) {
                case 1:
                    onePesos += amount;
                    break;
                case 5:
                    fivePesos += amount;
                    break;
                case 10:
                    tenPesos += amount;
                    break;
                case 20:
                    twentyPesos += amount;
                    break;
                case 50:
                    fiftyPesos += amount;
                    break;
                case 100:
                    oneHundredPesos += amount;
                    break;
                case 200:
                    twoHundredPesos += amount;
                    break;
                case 500:
                    fiveHundredPesos += amount;
                    break;
                case 1000:
                    thousandPesos += amount;
                    break;
                default:
                    return;
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
     * This method updates the last restock date and the restock slots of the last
     * restock.
     *
     */
    public void updateLastRestock() {
        this.lastRestockDate = new Date().getTime();
        this.lastRestockSlots = itemSlots;
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
        for (int i = 0; i < itemSlots.size(); ++i) {
            Item slotItem = itemSlots.get(i).getSlotItemType();
            if (slotItem.getName().equals(name)) {
                return slotItem;
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
        if (denominationIsValid(denomination)) {
            switch (denomination) {
                case 1:
                    System.out.println(onePesos + "One Pesos");
                    break;
                case 5:
                    System.out.println(fivePesos + "Five Pesos");
                    break;
                case 10:
                    System.out.println(tenPesos + "Ten Pesos");
                    break;
                case 20:
                    System.out.println(twentyPesos + "Twenty Pesos");
                    break;
                case 50:
                    System.out.println(fiftyPesos + "Fifty Pesos");
                    break;
                case 100:
                    System.out.println(oneHundredPesos + "100 Pesos");
                    break;
                case 200:
                    System.out.println(twoHundredPesos + "200 Pesos");
                    break;
                case 500:
                    System.out.println(fiveHundredPesos + "500 Pesos");
                    break;
                case 1000:
                    System.out.println(thousandPesos + "1000 Pesos");
                    break;
                default:
                    System.out.println("");
            }
        }
    }

    /**
     * This method checks if all slots are full
     * 
     * @return the item slot size which is the slot capacity
     */
    public boolean slotsAreFull() {
        return itemSlots.size() == slotCapacity;
    }

    /**
     * This method adds a slot to the vending machine.
     *
     */
    public void addSlot() {
        itemSlots.add(new Slot(itemCapacity));
    }

    /**
     * This method removes a slot to the vending machine.
     *
     */
    public void removeSlot(Item itemType) {
        for (int i = 0; i < itemSlots.size(); ++i) {
            if (itemSlots.get(i).getSlotItemType() == itemType) {
                itemSlots.remove(itemSlots.get(i));
            }
        }
    }

}