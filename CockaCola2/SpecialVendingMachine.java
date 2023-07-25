import java.util.ArrayList;

public class SpecialVendingMachine extends RegularVendingMachine {
    ArrayList<String> choiceItems;
    ArrayList<Integer> choiceQuantities;
    ArrayList<Item> specialItems;

    public SpecialVendingMachine(Owner owner, String name, int slotCapacity, int slotItemCapacity) {
        super(owner, name, slotCapacity, slotItemCapacity);
    }

    public double getTotalCalories() {
        double totalCal = 0;

        for (String itemName : choiceItems) {
            Item chosenItem = super.getItem(itemName);
            totalCal += chosenItem.getCalories();
        }

        return totalCal;
    }

    public void addItemChoice(String itemName, int quantity) {

    }

    public Item dispenseSpecialItem() {
        return null;
    }

    @Override
    public String toString() {
        return "\n================================\n" + super.name + "\nSpecial Vending Machine\n"
                + "================================";
    }
}
