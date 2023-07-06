import java.util.ArrayList;

/**
 * This class represents the owner of the vending machine
 *
 */
public class Owner {
  private int balance;
  private final String name;
  private ArrayList<RegularVendingMachine> vendingMachines = new ArrayList<RegularVendingMachine>();

  /**
   * This constructor takes various parameters: name and balance.
   * Then, it maintains the vending machine.
   * 
   * @param name    which is the name of the owner
   * @param balance which is the balance of the owner
   *
   */

  public Owner(String name, int balance) {
    this.balance = 0;
    this.name = name;
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

  public void restock(RegularVendingMachine vendingMachine, Item item, int amount) {
    for (Slot slot : vendingMachine.getItemSlots()) {
      if (slot.getSlotItemType().getName() == item.getName()) {
        slot.restockSlot(item, amount);
        vendingMachine.updateLastRestock();
      }
    }
  }

  /**
   * This method gets the price of the item in the vending machine
   * 
   * @param vendingMachineIndex which is the vending machine’s index in its array
   * @param item                which is the item of the vending machine
   * @param newPrice            which is the new price of the item of the vending
   *                            machine
   */

  public void setPrice(RegularVendingMachine vendingMachine, String itemName, int newPrice) {
    for (Slot slot : vendingMachine.getItemSlots()) {
      if (slot.getSlotItemType().getName().equals(itemName)) {
        slot.setPrice(newPrice);
        break;
      }
    }
  }

  /**
   * This method gets the amount of collectedMoney
   * 
   * @param vendingMachineIndex which is the vending machine’s index in its array
   *
   */

  public void collectMoney(int vendingMachineIndex) {
    RegularVendingMachine vendingMachine = vendingMachines.get(vendingMachineIndex);
    this.balance += vendingMachine.getCollectedMoney();
  }

  /**
   * This method gets the replenishes the money in the vending machine
   * 
   * @param vendingMachineIndex which is the vending machine’s index in its array
   * @param amount              which is the money to be set
   * @param denomination        which is the denomination of the money being set
   */

  public void replenishMoney(int vendingMachineIndex, int amount, int denomination) {
    RegularVendingMachine vendingMachine = vendingMachines.get(vendingMachineIndex);
    if (this.balance - amount >= 0) {
      this.balance -= amount;
      vendingMachine.setMoney(amount, denomination);
    }
  }

  /**
   * This method returns the name of the owner of the vending machine.
   */
  public String getName() {
    return this.name;
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
  public ArrayList<RegularVendingMachine> getVendingMachines() {
    return this.vendingMachines;
  }

  /**
   * This method gets a specific vending machine
   *
   */
  public RegularVendingMachine getVendingMachine(String name) {
    for (int i = 0; i < vendingMachines.size(); ++i) {
      if (vendingMachines.get(i).getName().equals(name)) {
        return vendingMachines.get(i);
      }
    }
    return null;
  }

  public void addVendingMachine(RegularVendingMachine vendingMachine) {
    this.vendingMachines.add(vendingMachine);
  }
}