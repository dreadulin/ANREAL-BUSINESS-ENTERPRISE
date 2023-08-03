public class CustomHaloHalo extends SpecialItem {
    private final static String[] ingredients = { "Ice", "Milk", "Sugar", "LecheFlan", "Halaya" };

    public CustomHaloHalo() {
        super("Halo Halo", ingredients);
    }

    public void displayProcess() {
        System.out.println("Crushing Ice...");
        System.out.println("Sprinkling Pinipig, Kaong, and Monggo...");
        System.out.println("Adding Crushed Ice...");
        System.out.println("Pouring Milk...");
        System.out.println("Adding Sugar...");
        System.out.println("Topping Langka, Halaya, and Leche Flan...");
        System.out.println("Placing Ube ice cream....");
        System.out.println("Halo Halo Done!");
    }
}
