package vendingmachine;

/**
 * This class represents a CremaDeLeche special item which contains ingredients and a 
 * displayProcess method to display its specific process.
 * @author Andrea Dulin and Darryl Javier
 */
public class CremaDeLeche extends SpecialItem {
    private final static String[] ingredients = { "Ice", "Milk", "Banana", "Sugar", "LecheFlan" };

    public CremaDeLeche() {
        super("Crema De Leche", ingredients);
    }

    public void displayProcess() {
        System.out.println("Crushing Ice...");
        System.out.println("Slicing Banana...");
        System.out.println("Adding Crushed Ice...");
        System.out.println("Pouring Milk...");
        System.out.println("Adding Sugar...");
        System.out.println("Topping Banana, Macapuno, and Leche Flan...");
        System.out.println("Placing Vanilla ice cream....");
        System.out.println("Crema De Leche Done!");
    }
}
