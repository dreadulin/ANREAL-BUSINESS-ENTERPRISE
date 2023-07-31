package vendingmachine;
public class UltimateHaloHalo extends SpecialItem {
    private final static String[] ingredients = { "Ice", "Milk", "Banana", "Kamote", "Sugar", "Halaya", "Nata",
            "LecheFlan" };

    public UltimateHaloHalo() {
        super("Ultimate Halo Halo", ingredients);
    }

    public void displayProcess() {
        System.out.println("Crushing Ice...");
        System.out.println("Slicing Banana...");
        System.out.println("Slicing Kamote...");
        System.out.println("Sprinkling Nata, Kaong, Monggo Beans, and Pinipig...");
        System.out.println("Adding Sago and Gulaman...");
        System.out.println("Adding Langka");
        System.out.println("Adding Crushed Ice...");
        System.out.println("Pouring Milk...");
        System.out.println("Adding Sugar...");
        System.out.println("Topping Banana, Kamote, Halaya, and Leche Flan...");
        System.out.println("Placing Ube ice cream and Vanilla ice cream....");
        System.out.println("Ultimate Halo Halo Done!");
    }
}
