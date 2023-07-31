package vendingmachine;
import java.util.ArrayList;

public abstract class SpecialItem {
    private String name;
    private String[] ingredients;

    public SpecialItem(String name, String[] ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public boolean compareIngredients(ArrayList<Item> givenIngredients) {
        System.out.println(name + ": " + ingredients.length + " ingredients");
        int ingredientCount = 0;
        for (Item ingredient : givenIngredients) {
            System.out.println("CURRENT INGREDIENT NAME: " + ingredient.getName());
            if (ingredientExists(ingredient.getName())) {
                ingredientCount++;
            }
        }

        System.out.println("Ingredient count: " + ingredientCount);

        return ingredientCount >= ingredients.length;
    }

    public boolean ingredientExists(String ingredient) {
        for (int i = 0; i < ingredients.length; ++i) {
            if (ingredients[i].equals(ingredient))
                return true;
        }
        return false;
    }

    public String getName() {
        return this.name;
    }

    abstract public void displayProcess();

    @Override
    public String toString() {
        String displayString = "\n==============================\n" +
                this.name + "\n==============================\n" +
                "INGREDIENTS:\n\n";

        for (String ingredient : ingredients) {
            displayString += ingredient + "\n";
        }

        return displayString + "==============================\n\n";
    }
}
