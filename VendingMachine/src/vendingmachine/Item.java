package vendingmachine;
import java.util.Objects;

/**
 * This class represents the Items of the vending machine
 * @author Andrea Dulin and Darryl Javier
 */

  public class Item {
  private String name;
  private double calories;
  private int price = 100;

  /**
   * This constructor takes various parameters: name, price and calories.
   * 
   * @param name     which represents the name of the item
   * @param price    which is the price of the item
   * @param calories which represents the number of calories the
   *                 item contains
   */
  public Item(String name, int price, double calories) {
    this.name = name;
    this.price = price;
    this.calories = calories;
  }

 
  /**
   * This method gets the name of the item
   * @return the item name
   */
  public String getName() {
    return this.name;
  }

  /**
   * This method gets the amount of calories an item has
   * @return the number of item calories
   */
  public double getCalories() {
    return this.calories;
  }


  // terminal version for MCO1
  public void display() {
    displayLines(this.name);

    System.out.println("|| " + this.name + " ||");

    displayLines(this.name);

    System.out.println(this.calories + " cal");
    System.out.println("P" + this.price);

    displayLines(this.name);
  }

  // terminal version for MCO1
  private void displayLines(String itemName) {
    int lineLength = itemName.length() + 6;
    while (lineLength > 0) {
      System.out.print("=");
      lineLength--;
    }
    System.out.println();
  }

  /**
   * This method sets the price of the item.
   * 
   * @param newPrice which is the updated price of the time
   *
   */
  public void setPrice(int newPrice) {
    this.price = newPrice;
  }

  /**
   * This method gets the how much the item costs
   * @return price of the item 
   */
  public int getPrice() {
    return this.price;
  }

  
  @Override
  public boolean equals(Object object) {
    if (object == this) {
      return true;
    }

    if (!(object instanceof Item)) {
      return false;
    }

    Item anotherItem = (Item) object;
    return Objects.equals(this.name, anotherItem.name) &&
        Objects.equals(this.calories, anotherItem.calories) &&
        Objects.equals(this.price, anotherItem.price);
  }
}