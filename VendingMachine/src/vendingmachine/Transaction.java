package vendingmachine;
import java.util.Date;

/**
 * This class represents the transactions of the vending machine 
 * @author Andrea Dulin and Darryl Javier
 */

public class Transaction {
  private Item itemPurchased;
  private int amount;
  private int totalSales;
  private long transactionDate;

  /**
   * This constructor takes various parameters: itemPurchased, amount and
   * totalSales then assigns them to this object.
   * 
   * @param itemPurchased which is the item bought by the
   *                      customer.
   * @param amount        which is the amount of the item bought
   * @param totalSales    which is the total amount of sales.
   */

  public Transaction(Item itemPurchased, int amount, int totalSales) {
    this.itemPurchased = itemPurchased;
    this.amount = amount;
    this.totalSales = totalSales;
    this.transactionDate = new Date().getTime();
  }

  /**
   * This method gets the total amount of sales.
   * @return the total sales of the machine
   *
   */

  public int getTotalSales() {
    return this.totalSales;
  }

  /**
   * This method gets the details of the item bought.
   * @return the items purchased by the user 
   *
   */

  public Item getItemPurchased() {
    return this.itemPurchased;
  }

  /**
   * This method gets the purchase amount 
   * @return the amounts of items purchased
   *
   */
  public int getPurchaseAmount() {
    return this.amount;
  }

  /**
   * This method gets the date when a transaction was made
   * 
   * @return date of the transaction
   *
   */

  public long getTransactionDate() {
    return this.transactionDate;
  }

  /** FOR MCO1
   * This method displays the Transaction that has occured when dispensing an
   * item.
   * 
   */
  public void displayTransaction() {
    System.out.println("-------------------------------------------------");
    System.out.println("TRANSACTION INFORMATION");
    System.out.println("-------------------------------------------------");
    System.out.println(this.transactionDate);
    System.out.println("Item: " + this.itemPurchased.getName());
    System.out.println("Amount: " + this.amount);
    System.out.println("Total profit: " + this.totalSales);
    System.out.println("-------------------------------------------------\n\n");
  }
}