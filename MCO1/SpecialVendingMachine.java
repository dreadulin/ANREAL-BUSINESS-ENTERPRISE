import java.util.ArrayList;

public class SpecialVendingMachine extends RegularVendingMachine {
    ArrayList<Item> choiceItems;
    ArrayList<Integer> choiceQuantities;
    ArrayList<SpecialItem> specialItems = new ArrayList<SpecialItem>();

    public SpecialVendingMachine(Owner owner, String name, int slotCapacity, int slotItemCapacity) {
        super(owner, name, slotCapacity, slotItemCapacity);
        choiceItems = new ArrayList<Item>();
        choiceQuantities = new ArrayList<Integer>();
        UltimateHaloHalo halohalo = new UltimateHaloHalo();
        CremaDeLeche cremaDeLeche = new CremaDeLeche();
        CustomHaloHalo customHaloHalo = new CustomHaloHalo();
        specialItems.add(halohalo);
        specialItems.add(cremaDeLeche);
        specialItems.add(customHaloHalo);

        this.stock(new Item("Ice", 20, 0), 10);
        this.stock(new Item("Milk", 40, 60), 10);
        this.stock(new Item("Banana", 10, 16), 10);
        this.stock(new Item("Kamote", 15, 14), 10);
        this.stock(new Item("Sugar", 35, 100), 10);
        this.stock(new Item("Halaya", 80, 200), 10);
        this.stock(new Item("Nata", 40, 30), 10);
        this.stock(new Item("LecheFlan", 100, 160), 10);
    }

    public double getTotalCalories() {
        double totalCal = 0;

        for (int i = 0; i < choiceItems.size(); ++i) {
            totalCal += choiceItems.get(i).getCalories() * choiceQuantities.get(i);
        }

        return totalCal;
    }

    public int getChoiceTotalPrice() {
        int totalPrice = 0;
        for (int i = 0; i < choiceItems.size(); ++i) {
            totalPrice += choiceItems.get(i).getPrice() * choiceQuantities.get(i);
        }
        return totalPrice;
    }

    public ArrayList<SpecialItem> getSpecialItems() {
        return this.specialItems;
    }

    public void displayItemChoices() {
        for (int i = 0; i < choiceItems.size(); ++i) {
            System.out.print(choiceItems.get(i).getName());
            System.out.println(" " + choiceQuantities.get(i) + "x");
        }
    }

    public ArrayList<Item> getItemChoices() {
        return this.choiceItems;
    }

    public ArrayList<Integer> getChoicesQuantities() {
        return this.choiceQuantities;
    }

    public void addItemChoice(Item item, int quantity) {
        int existingChoiceIndex = choiceItems.indexOf(item);
        if (existingChoiceIndex >= 0) {
            int newQt = choiceQuantities.get(existingChoiceIndex);
            System.out.println("Existing choice found...");
            System.out.println("Adding quantity to the ingredient...");
            newQt += quantity;
            choiceQuantities.set(existingChoiceIndex, newQt);
            return;
        }

        choiceItems.add(item);
        choiceQuantities.add(quantity);
    }

    public void removeItemChoice(Item item, int quantity) {
        choiceItems.remove(item);
        choiceItems.remove(quantity);
    }

    public SpecialItem dispenseSpecialItem(int payment) {
        SpecialItem specialCombination = null;
        // TODO: LOOP THROUGH THE ITEMS ARRAY AND CHECK IF QUANTITY IS
        // VALID.

        for (SpecialItem special : specialItems) {
            if (special.compareIngredients(choiceItems)) {
                specialCombination = special;
                break;
            }
        }

        if (specialCombination == null) {
            System.out.println("The special item couldn't be made.");
            return null;
        }

        int totalPrice = this.getChoiceTotalPrice();

        for (int i = 0; i < choiceItems.size(); ++i) {
            Slot itemSlot = null;
            String itemName = choiceItems.get(i).getName();
            for (Slot slot : this.itemSlots) {
                if (slot.getSlotItemType().getName().equals(itemName)) {
                    itemSlot = slot;
                    break;
                }
            }

            if (itemSlot == null) {
                System.out.println("No such item exists in the vending machine.");
                return null;
            }
            int itemQuantity = itemSlot.getItemQuantity();

            if (itemQuantity < choiceQuantities.get(i)) {
                System.out.println("Quantity exceeds the stock for the item.");
                return null;
            }

        }

        // TODO: CHECK IF PAYMENT IS VALID
        // TODO: CALCULATE CHANGE IF IT IS ENOUGH
        if (payment < totalPrice) {
            System.out.println("Your payment is not enough for this item.");
            return null;
        }

        int change = payment - totalPrice;

        if (this.getStockMoney() - change < 0) {
            System.out.println("Vending Machine money is not enough to dispense change.");
            return null;
        }

        // TODO: DISPLAY THE PROCESS
        specialCombination.displayProcess();

        // TODO: DISPENSE CHANGE
        dispenseChange(payment, totalPrice);

        // TODO: LOOP THROUGH ALL THE CHOICES ARRAY AND REMOVE STOCK FOR EACH ONE
        for (int i = 0; i < choiceItems.size(); ++i) {
            Slot itemSlot = null;
            String itemName = choiceItems.get(i).getName();
            for (Slot slot : this.itemSlots) {
                if (slot.getSlotItemType().getName().equals(itemName)) {
                    itemSlot = slot;
                    break;
                }
            }

            if (itemSlot == null) {
                System.out.println("No such item exists in the vending machine.");
                return null;
            }

            itemSlot.removeStock(choiceQuantities.get(i));
            System.out.println("Removed stock for " + itemName);
        }

        // TODO: LOOP THROUGH ALL THE CHOICES ARRAY AND CREATE A TRANSACTION FOR EACH
        // ONE
        for (int i = 0; i < choiceItems.size(); ++i) {
            int totalItemCost = choiceItems.get(i).getPrice() * choiceQuantities.get(i);
            Transaction newTransaction = new Transaction(choiceItems.get(i), choiceQuantities.get(i), totalItemCost);
            transactions.add(newTransaction);
        }

        return specialCombination;
    }

    public void resetChoices() {
        choiceItems = null;
        choiceQuantities = null;
        choiceItems = new ArrayList<Item>();
        choiceQuantities = new ArrayList<Integer>();
    }

    @Override
    public String toString() {
        return "\n================================\n" + super.name + "\nSpecial Vending Machine\n"
                + "================================";
    }
}
