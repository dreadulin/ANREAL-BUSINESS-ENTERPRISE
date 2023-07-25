import java.util.ArrayList;

public class SpecialVendingMachine extends RegularVendingMachine {
    ArrayList<Item> choiceItems;
    ArrayList<Integer> choiceQuantities;
    ArrayList<Item> specialItems;

    public SpecialVendingMachine(Owner owner, String name, int slotCapacity, int slotItemCapacity) {
        super(owner, name, slotCapacity, slotItemCapacity);
        choiceItems = new ArrayList<Item>();
        choiceQuantities = new ArrayList<Integer>();
        specialItems = new ArrayList<Item>();
    }

    public double getTotalCalories() {
        double totalCal = 0;

        for (Item chosenItem : choiceItems) {
            totalCal += chosenItem.getCalories();
        }

        return totalCal;
    }

    public int getChoiceTotalPrice() {
        int totalPrice = 0;
        for (Item item : choiceItems) {
            totalPrice += item.getPrice();
        }

        return totalPrice;
    }

    public void displayItemChoices() {
        for (int i = 0; i < choiceItems.size(); ++i) {
            System.out.print(choiceItems.get(i).getName());
            System.out.println(" " + choiceQuantities.get(i) + "x");
        }
    }

    public void addItemChoice(Item item, int quantity) {
        choiceItems.add(item);
        choiceQuantities.add(quantity);
    }

    public void removeItemChoice(Item item, int quantity) {
        choiceItems.remove(item);
        choiceItems.remove(quantity);
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
