package vendingmachine;

import java.util.ArrayList;

/**
 * This class represents the special items of the special vending machine
 * @author Andrea Dulin and Darryl Javier 
 */
public abstract class SpecialItem {

    private String name;
    private String[] ingredients;

    /**
     * This constructor initializes the name of the special item and the ingredients it requires  
     * @param name which is the name of the item    
     * @param ingredients which are the items needed to build the special item
     */
    public SpecialItem(String name, String[] ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }

    /**
     * This compares the inredients of the items 
     * @param givenIngredients 
     * @return the count of ingredients 
     */
    public boolean compareIngredients(ArrayList<Item> givenIngredients) {
        System.out.println(name + ": " + ingredients.length + " ingredients"); // for mco1
        int ingredientCount = 0;
        for (Item ingredient : givenIngredients) {
            System.out.println("CURRENT INGREDIENT NAME: " + ingredient.getName()); // for mco1
            if (ingredientExists(ingredient.getName())) {
                ingredientCount++;
            }
        }

        // for mco1
        System.out.println("Ingredient count: " + ingredientCount);

        return ingredientCount >= ingredients.length;
    }

    /**
     * This checks if an ingredient already exists 
     * @param ingredient which is the ingredient we are looking for 
     * @return true if ingredient exists, false is not 
     */
    public boolean ingredientExists(String ingredient) {
        for (int i = 0; i < ingredients.length; ++i) {
            if (ingredients[i].equals(ingredient)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This gets the name of the special item 
     * @return the name of the special item 
     */
    public String getName() {
        return this.name;
    }

    /**
     * This gets the ingredients needed by the special item 
     * @return the required ingredients
     */
    public String[] getIngredients() {
        return this.ingredients;
    }

    abstract public void displayProcess();

    // for MCO1
    @Override
    public String toString() {
        String displayString = "\n==============================\n"
                + this.name + "\n==============================\n"
                + "INGREDIENTS:\n\n";

        for (String ingredient : ingredients) {
            displayString += ingredient + "\n";
        }

        return displayString + "==============================\n\n";
    }
}
