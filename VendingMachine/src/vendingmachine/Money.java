/**
 * This class represents the money of the vending machine
 * @author Andrea Dulin and Darryl Javier
 */

package vendingmachine;
public class Money {
    protected int value;
    protected int amount;

    /**
     * This constructor takes various parameters: value and amount 
     * @param value which is the denomination of the money 
     * @param amount which is the quantity per denomination 
    */
    public Money(int value, int amount) {
        this.value = value;
        this.amount = amount;
    }

    /**
     * This method is for subtracting the quantity of the denomination 
     * @param amount which is the quantity of the money 
     * @return the updated count of the money
     */
    public boolean subtractAmount(int amount) {
        if (this.amount - amount >= 0) {
            this.amount -= amount;
            return true;
        }
        return false;
    }

    /**
     * This method is for adding the quantity of the denomination 
     * @param amount which is the quantity of the money 
     */
    public void addAmount(int amount) {
        this.amount += amount;
    }

    /**
     * This method collects the total value of the money based on the quantity of the denomination   
     */
    public int collectValue() {
        int collected = this.value * this.amount;
        this.amount = 0;
        return collected;
    }

    /**
     * This method gets the value of denomination 
     * @return the value of the money 
     */
    public int getValue() {
        return this.value;
    }

    /**
     * This method gets the amount of denomination 
     * @return the amount of denomination 
     */
    public int getAmount() {
        return this.amount;
    }

    // for mco1
    public void display() {
        System.out.println("P" + this.value + " - " + this.amount + "x");
    }
}
