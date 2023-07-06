/**
 * This class represents the Items of the vending machine
 */

public class Item {
  private String name;
  private double calories;

  /**
   * This constructor takes various parameters: name and calories.
   * 
   * @param name     which represents the name of the item
   * @param calories which represents the number of calories the
   *                 item contains
   */
  public Item(String name, double calories) {
    this.name = name;
    this.calories = calories;
  }

  /**
   * This method gets the name of the item
   * 
   * @return name which is the name of the item
   *
   */

  public String getName() {
    return this.name;
  }

  /**
   * This method gets the amount of calories an item has
   * 
   * @return calories
   *
   */

  public double getCalories() {
    return this.calories;
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
    return anotherItem.toString().equals(this.toString());
  }
}