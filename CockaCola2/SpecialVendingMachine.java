import java.util.ArrayList;

public class SpecialVendingMachine extends RegularVendingMachine {
    ArrayList<Item> userChoices;
    ArrayList<Item> specialItems;

    public SpecialVendingMachine(Owner owner, String name, int slotCapacity, int slotItemCapacity) {
        super(owner, name, slotCapacity, slotItemCapacity);
    }

    public double getTotalCalories() {
        double totalCal = 0;

        for (Item item : userChoices) {
            totalCal += item.getCalories();
        }

        return totalCal;
    }

    public Item dispenseSpecialItem() {
        return null;
    }
}
