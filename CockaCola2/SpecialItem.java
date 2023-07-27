import java.util.ArrayList;

public class SpecialItem {
    private String name;
    private ArrayList<String> ingredients;

    public SpecialItem(String name, ArrayList<String> ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    public boolean compareIngredients(ArrayList<Item> givenIngredients) {
        int ingredientCount = 0;
        for (Item ingredient : givenIngredients) {
            if (ingredients.indexOf(ingredient.getName()) >= 0) {
                ingredientCount++;
            }
        }

        return ingredientCount == givenIngredients.size();
    }

    public void displayProcess() {

    }

    public String getName() {
        return this.name;
    }

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
