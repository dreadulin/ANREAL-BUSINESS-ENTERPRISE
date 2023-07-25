import java.util.ArrayList;

/**
 * This class represents the owner of the vending machine
 *
 */
public class Owner {
  private int balance;
  private final String name;
  private String password;
  private ArrayList<RegularVendingMachine> regularVendingMachines = new ArrayList<RegularVendingMachine>();
  private ArrayList<SpecialVendingMachine> specialVendingMachines = new ArrayList<SpecialVendingMachine>();

  /**
   * This constructor takes various parameters: name and balance.
   * Then, it maintains the vending machine.
   * 
   * @param name    which is the name of the owner
   * @param balance which is the balance of the owner
   *
   */

  public Owner(String name, int balance, String password) {
    this.balance = balance;
    this.name = name;
    this.password = password;
  }

  /**
   * This method restocks an item in a vending machine
   * 
   * @param vendingMachineIndex which is an index of the vending
   *                            machine array
   * @param item                which is the item of the vending machine
   * @param amount              which is the amount of the item in the vending
   *                            machine
   *
   */

  public boolean restock(RegularVendingMachine vendingMachine, String itemName, int amount) {
    return vendingMachine.restockSlot(itemName, amount);
  }

  public boolean restock(SpecialVendingMachine vendingMachine, String itemName, int amount) {
    return vendingMachine.restockSlot(itemName, amount);
  }

  public boolean stock(RegularVendingMachine vendingMachine, Item item, int amount) {
    return vendingMachine.stock(item, amount);
  }

  public boolean stock(SpecialVendingMachine vendingMachine, Item item, int amount) {
    return vendingMachine.stock(item, amount);
  }

  /**
   * This method gets the price of the item in the vending machine
   * 
   * @param vendingMachineIndex which is the vending machine’s index in its array
   * @param item                which is the item of the vending machine
   * @param newPrice            which is the new price of the item of the vending
   *                            machine
   */

  public boolean setPrice(RegularVendingMachine vendingMachine, String itemName, int newPrice) {
    return vendingMachine.setPrice(itemName, newPrice);
  }

  public boolean setPrice(SpecialVendingMachine vendingMachine, String itemName, int newPrice) {
    return vendingMachine.setPrice(itemName, newPrice);
  }

  /**
   * This method gets the amount of collectedMoney
   * 
   * @param vendingMachineIndex which is the vending machine’s index in its array
   *
   */

  public void collectMoney(RegularVendingMachine vendingMachine) {
    this.balance += vendingMachine.getCollectedMoney();
  }

  public void collectMoney(SpecialVendingMachine vendingMachine) {
    this.balance += vendingMachine.getCollectedMoney();
  }

  /**
   * This method gets the replenishes the money in the vending machine
   * 
   * @param vendingMachineIndex which is the vending machine’s index in its array
   * @param amount              which is the money to be set
   * @param denomination        which is the denomination of the money being set
   */

  public void replenishMoney(RegularVendingMachine vendingMachine, int amount, int denomination) {
    if (this.balance - (denomination * amount) >= 0) {
      this.balance -= (denomination * amount);
      vendingMachine.setMoney(amount, denomination);
    } else {
      System.out.println("You don't have enough money to replenish that amount");
    }
  }

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
   * This method gets the list of vending machine the user has
   *
   */
  public ArrayList<RegularVendingMachine> getRegularMachines() {
    return this.regularVendingMachines;
  }

  /**
   * This method gets the list of vending machine the user has
   *
   */
  public ArrayList<SpecialVendingMachine> getSpecialMachines() {
    return this.specialVendingMachines;
  }

  /**
   * This method gets a specific vending machine
   *
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
   * This method gets a specific vending machine
   *
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

  public void addMachine(RegularVendingMachine vendingMachine) {
    this.regularVendingMachines.add(vendingMachine);
  }

  public void addMachine(SpecialVendingMachine vendingMachine) {
    this.specialVendingMachines.add(vendingMachine);
  }
}