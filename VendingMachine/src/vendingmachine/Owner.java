package vendingmachine;
import java.util.ArrayList;

/**
 * This class represents the owner of the vending machine
 *  @author Andrea Dulin and Darryl Javier
 */
public class Owner {
  private int balance;
  private final String name;
  private String password;
  private ArrayList<RegularVendingMachine> regularVendingMachines = new ArrayList<RegularVendingMachine>();
  private ArrayList<SpecialVendingMachine> specialVendingMachines = new ArrayList<SpecialVendingMachine>();

  /**
   * This constructor takes various parameters: name, balance and password.
   * Then, it maintains the vending machine.
   * 
   * @param name    which is the name of the owner
   * @param balance which is the balance of the owner
   * @param password which is the password of the owner
   *
   */
  public Owner(String name, int balance, String password) {
    this.balance = balance;
    this.name = name;
    this.password = password;
  }

  /**
   * This method restocks an item in a regular vending machine
   * 
   * @param vendingMachine      which is the regular vending machine 
   * @param itemName            which is the item of the vending machine
   * @param amount              which is the amount of the item to restock in the vending machine
   */
  public boolean restock(RegularVendingMachine vendingMachine, String itemName, int amount) {
    return vendingMachine.restockSlot(itemName, amount);
  }

  /**
   * This method restocks an item in a special vending machine
   * 
   * @param vendingMachine      which is the special vending machine 
   * @param itemName            which is the item of the vending machine
   * @param amount              which is the amount of the item to restock in the vending machine
   */
  public boolean restock(SpecialVendingMachine vendingMachine, String itemName, int amount) {
    return vendingMachine.restockSlot(itemName, amount);
  }

  /**
   * This method adds a stock of an item in a regular vending machine
   * 
   * @param vendingMachine      which is the regular vending machine 
   * @param item                which is the item of the vending machine
   * @param amount              which is the amount of the item to stock
   */
  public boolean stock(RegularVendingMachine vendingMachine, Item item, int amount) {
    return vendingMachine.stock(item, amount);
  }

   /**
   * This method adds a stock of an item in a special vending machine
   * 
   * @param vendingMachine      which is the special vending machine 
   * @param item                which is the item of the vending machine
   * @param amount              which is the amount of the item to stock
   */
  public boolean stock(SpecialVendingMachine vendingMachine, Item item, int amount) {
    return vendingMachine.stock(item, amount);
  }

  /**
   * This method sets the price of the item in the regular vending machine
   * 
   * @param vendingMachine      which is the regular vending machine 
   * @param itemName            which is the item name in the vending machine
   * @param newPrice            which is the new price of the item of the vending machine
   */

  public boolean setPrice(RegularVendingMachine vendingMachine, String itemName, int newPrice) {
    return vendingMachine.setPrice(itemName, newPrice);
  }

  /**
   * This method sets the price of the item in the special vending machine
   * 
   * @param vendingMachine      which is the special vending machine 
   * @param itemName            which is the item name in the vending machine
   * @param newPrice            which is the new price of the item of the vending machine
   */
  public boolean setPrice(SpecialVendingMachine vendingMachine, String itemName, int newPrice) {
    return vendingMachine.setPrice(itemName, newPrice);
  }


  /**
   * This method gets the amount of collectedMoney in the vending machine
   * 
   * @param vendingMachine which is the regular vending machine
   *
   */
  public void collectMoney(RegularVendingMachine vendingMachine) {
    this.balance += vendingMachine.getCollectedMoney();
  }

   /**
   * This method gets the amount of collectedMoney in thespecial vending machine
   * 
   * @param vendingMachine which is the special vending machine
   *
   */
  public void collectMoney(SpecialVendingMachine vendingMachine) {
    this.balance += vendingMachine.getCollectedMoney();
  }

 
  // for MCO1
  public void replenishMoney(RegularVendingMachine vendingMachine, int amount, int denomination) {
    if (this.balance - (denomination * amount) >= 0) {
      this.balance -= (denomination * amount);
      vendingMachine.setMoney(amount, denomination);
    } else {
      System.out.println("You don't have enough money to replenish that amount");
    }
  }

  // for MCO1
  public void replenishMoney(SpecialVendingMachine vendingMachine, int amount, int denomination) {
    if (this.balance - (denomination * amount) >= 0) {
      this.balance -= (denomination * amount);
      vendingMachine.setMoney(amount, denomination);
    } else {
      System.out.println("You don't have enough money to replenish that amount");
    }
  }

  /**
   * This method returns the name of the owner of the vending machine.
   */
  public String getName() {
    return this.name;
  }

  /**
   * This method returns the password of the owner of the vending machine.
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * This method is for setting the user's updated password 
   * @param newPassword which is the updated password set by the user after a successful change of password
   */
  public void setPassword(String newPassword) {
    this.password = newPassword;
  }

  /**
   * This method returns the balance of the owner of the vending machine.
   */
  public int getBalance() {
    return this.balance;
  }

  /**
   * This method gets the list of regular vending machine the user has
   */
  public ArrayList<RegularVendingMachine> getRegularMachines() {
    return this.regularVendingMachines;
  }

  /**
   * This method gets the list of special vending machine the user has
   */
  public ArrayList<SpecialVendingMachine> getSpecialMachines() {
    return this.specialVendingMachines;
  }

  /**
   * This method gets a specific special vending machine
   * @param name which is the name of the special vending machine 
   * @return the special vending machine 
   */
  public SpecialVendingMachine getSpecialMachine(String name) {
    for (SpecialVendingMachine vendingMachine : specialVendingMachines) {
      if (vendingMachine != null) {
        if (vendingMachine.getName().equals(name)) {
          return vendingMachine;
        }
      }
    }
    return null;
  }

  /**
   * This method gets a specific regular vending machine
   * @param which is the name of the regular vending machine
   * @return the regular vending machine
   */
  public RegularVendingMachine getRegularMachine(String name) {
    for (RegularVendingMachine vendingMachine : regularVendingMachines) {
      if (vendingMachine != null) {
        if (vendingMachine.getName().equals(name)) {
          return vendingMachine;
        }
      }
    }
    return null;
  }

/**
  * This method is for adding a regular vending machine 
  * @param vendingMachine which is the regular vending machine 
  */
  public void addMachine(RegularVendingMachine vendingMachine) {
    this.regularVendingMachines.add(vendingMachine);
  }

/**
  * This method is for adding a special vending machine 
  * @param vendingMachine which is the special vending machine 
  */

  public void addMachine(SpecialVendingMachine vendingMachine) {
    this.specialVendingMachines.add(vendingMachine);
  }
}