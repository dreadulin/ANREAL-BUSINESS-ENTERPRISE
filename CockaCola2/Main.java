import java.util.Scanner;

public class Main {
    private static Owner owner = new Owner("AnReal Enterprises", 100000);
    private static Scanner sc = new Scanner(System.in);

    // TODO: NEW JAVADOC FOR ALL FILES (ANDREA)

    private static void createMachine() {
        String machineName;
        int slotsQt = 0;
        int itemSlotsQt = 0;

        System.out.println("--------------------------\n");
        System.out.println("| CREATE VENDING MACHINE |\n");
        System.out.println("--------------------------\n");

        System.out.println("Enter name of vending machine: ");
        machineName = sc.next();

        System.out.println("Enter number of slots(minimum of 8): \n");
        slotsQt = sc.nextInt();

        System.out.println("Enter number of items per slot(minimum of 10): \n");
        itemSlotsQt = sc.nextInt();

        if (slotsQt < 8) {
            System.out.println("Invalid Slots Capacity. Must be atleast 8.");
            System.out.println("Going back to the main menu...");
            render();
        }

        if (itemSlotsQt < 10) {
            System.out.println("Invalid Slot Item Capacity. Must be atleast 10.");
            System.out.println("Going back to the main menu...");
            render();
        }

        RegularVendingMachine newVendingMachine = new RegularVendingMachine(owner, machineName, slotsQt,
                itemSlotsQt);

        owner.addVendingMachine(newVendingMachine);
        System.out.println("Vending Machine successfully created! Going back to the main menu.\n");

        render();
    }

    private static void authenticateMachine(String purpose) {
        if (purpose != "Maintenance" && purpose != "Testing") {
            System.out.println("Invalid purpose. Going back to the main menu...");
            render();
        }

        if (purpose.equals("Testing")) {
            System.out.println("--------------------------\n");
            System.out.println("| TEST A VENDING MACHINE |\n");
            System.out.println("--------------------------\n");
        } else {
            System.out.println("----------------------------\n");
            System.out.println("| TEST MACHINE MAINTENANCE |\n");
            System.out.println("----------------------------\n");
        }

        System.out.println("Enter machine name: ");

        RegularVendingMachine authenticatedMachine;
        String testMachineName = "";

        testMachineName = sc.next();
        authenticatedMachine = owner.getVendingMachine(testMachineName);

        if (authenticatedMachine == null) {
            System.out.println("Failed to authenticate. Going back to the main menu...");
            render();
        }

        if (purpose.equals("Testing")) {
            testVendingMachine(authenticatedMachine);
        } else
            testMaintenance(authenticatedMachine);
    }

    private static void testVendingMachine(RegularVendingMachine authenticatedMachine) {
        System.out.println("--------------------------\n");
        System.out.println("| TEST A VENDING MACHINE |\n");
        System.out.println("--------------------------\n");
        System.out.println("1 - Display Stock");
        System.out.println("2 - Dispense Item");
        System.out.println("3 - Exit");
        System.out.print("Enter choice: ");
        int userInput = sc.nextInt();
        switch (userInput) {
            case 1:
                System.out.println("-----------------\n");
                System.out.println("| MACHINE STOCK |\n");
                System.out.println("-----------------\n");
                authenticatedMachine.displayStock();
                testVendingMachine(authenticatedMachine);
                break;
            case 2:
                // TODO: DISPENSE ITEM (ANDREA)
                System.out.println("-----------------\n");
                System.out.println("| DISPENSE ITEM |\n");
                System.out.println("-----------------\n");

                /*
                 * TO ACCESS THE MACHINE WE'RE TRYING TO OPERATE ON, JUST USE THE
                 * authenticatedMachine VARIABLE
                 * 
                 * - Display the stock (Use Display Stock method of RegularVendingMachine)
                 * - Ask user for item name and for the amount he wants to dispense.
                 * - As user chooses an item, ask user if he wants to proceed to payment or
                 * cancel the transaction.
                 * - If cancel, return to the test vending machine menu.
                 * - Display total cost first
                 * - Prompt input for payment
                 * - Call dispenseItem
                 * - If dispenseItem returns null, then display an error saying that dispensing
                 * failed.
                 * - If not, display that dispensing was successful.
                 */

                testVendingMachine(authenticatedMachine);
                break;
            case 3:
                render();
                break;
        }
    }

    private static void testMaintenance(RegularVendingMachine authenticatedMachine) {
        System.out.println("----------------------------\n");
        System.out.println("| TEST MACHINE MAINTENANCE |\n");
        System.out.println("----------------------------\n");
        System.out.println("1 - Collect money");
        System.out.println("2 - Replenish money");
        System.out.println("3 - Add Stock");
        System.out.println("4 - Restock");
        System.out.println("5 - Set price");
        System.out.println("6 - Display Transaction Summary");
        System.out.println("7 - Exit");
        System.out.print("Enter choice: ");
        int userInput = sc.nextInt();
        switch (userInput) {
            case 1:
                System.out.println("-----------------\n");
                System.out.println("| COLLECT MONEY |\n");
                System.out.println("-----------------\n");
                System.out.println("Your current balance: " + owner.getBalance());
                System.out.println("Collecting money...");
                owner.collectMoney(authenticatedMachine);
                System.out.println("Your new balance: " + owner.getBalance());
                testMaintenance(authenticatedMachine);
                break;
            case 2:
                int denomination;
                int amount;

                System.out.println("-------------------\n");
                System.out.println("| REPLENISH MONEY |\n");
                System.out.println("-------------------\n");

                System.out.println("Valid Denominations: 1000, 500, 200, 100, 50, 20, 10, 5, 1");
                System.out.print("Enter denomination of money: ");
                denomination = sc.nextInt();

                System.out.print("Enter amount to input. (Whole number only): ");
                amount = sc.nextInt();

                if (authenticatedMachine.denominationIsValid(denomination)) {
                    System.out.print("Current " + denomination + " amount: ");
                    authenticatedMachine.displayMoney(denomination);

                    System.out.println("\nReplenishing money...");
                    owner.replenishMoney(authenticatedMachine, amount, denomination);

                    System.out.print("New " + denomination + " amount: ");
                    authenticatedMachine.displayMoney(denomination);

                    System.out.println("Going back to the main menu...");
                    testMaintenance(authenticatedMachine);
                }

                System.out.println("Input denomination is invalid. Going back to maintenance menu...");
                testMaintenance(authenticatedMachine);
                break;
            case 3:
                String stockName;
                double stockCalories;
                boolean success = false;

                System.out.println("-----------------\n");
                System.out.println("|   ADD STOCK   |\n");
                System.out.println("-----------------\n");

                System.out.println("Create an item to add to the vending machine.");

                System.out.print("Enter name: ");
                stockName = sc.next();

                System.out.print("Enter calories: ");
                stockCalories = sc.nextDouble();

                Item stockItem = new Item(stockName, stockCalories);

                System.out.println("Enter amount to put in: ");
                int stockItemAmount = sc.nextInt();

                System.out.println("Stocking...");
                success = owner.stock(authenticatedMachine, stockItem, stockItemAmount);

                if (success) {
                    System.out.println("Stocking successful. Going back to the maintenance menu...");
                } else {
                    System.out.println("Slots are full. Cannot insert item anymore.");
                    System.out.println("Going back to the maintenance menu...");
                }

                testMaintenance(authenticatedMachine);
                break;
            case 4:
                String name;
                boolean operationSuccessful;

                System.out.println("-----------------\n");
                System.out.println("|    RESTOCK    |\n");
                System.out.println("-----------------\n");

                System.out.print("Enter name of the item to restock: ");
                name = sc.next();

                System.out.println("Enter amount to put in: ");
                int itemAmount = sc.nextInt();

                System.out.println("Restocking slot...");
                operationSuccessful = owner.restock(authenticatedMachine, name, itemAmount);

                if (operationSuccessful) {
                    System.out.println("Restock successful. Going back to the maintenance menu...");
                } else {
                    System.out.println("There is no slot that matches the item name.");
                    System.out.println("Going back to the maintenance menu...");
                }

                testMaintenance(authenticatedMachine);
                break;
            case 5:
                String itemName;
                int newPrice;

                System.out.println("-----------------\n");
                System.out.println("| CHANGE PRICE  |\n");
                System.out.println("-----------------\n");

                System.out.println("Enter the Item Name to set price");
                itemName = sc.next();

                System.out.println("Enter the new price (cents not allowed).");
                newPrice = sc.nextInt();

                if (owner.setPrice(authenticatedMachine, itemName, newPrice)) {
                    System.out.println("Price changed successfully. Going back to maintenance menu...");
                } else
                    System.out.println("Price changing failed. Going back to the maintenance menu...");
                testMaintenance(authenticatedMachine);
                break;
            case 6:
                System.out.println("------------------------\n");
                System.out.println("| TRANSACTION SUMMARY  |\n");
                System.out.println("------------------------\n");

                /*
                 * - Use the displayInventory method of the RegularVendingMachine
                 * - Use the getTransactionSummary method of the RegularVendingMachine
                 */
                
                testVendingMachine(authenticatedMachine);
                break;
            case 7:
                render();
                break;
        }
    }

    private static void render() {
        int userInput = 0;

        System.out.println("----------------------------------------------\n");
        System.out.println("|  Welcome to AnReal's Business Enterprises! |\n");
        System.out.println("----------------------------------------------\n");

        System.out.println("1 - Create vending machine");
        System.out.println("2 - Test vending machine features");
        System.out.println("3 - Test maintenance features");
        System.out.println("4 - Exit");
        System.out.println("Enter choice: ");

        userInput = sc.nextInt();
        switch (userInput) {
            case 1:
                createMachine();
                break;
            case 2:
                authenticateMachine("Testing");
                break;
            case 3:
                authenticateMachine("Maintenance");
            case 4:
                System.exit(200);
            default:
                break;
        }
    }

    public static void main(String[] args) {
        render();
    }
}