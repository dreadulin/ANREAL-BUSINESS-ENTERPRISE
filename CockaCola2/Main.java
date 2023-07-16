import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is a simulation of a regular vending machine which consists
 * of items and has maintenance features.
 * <p>
 * Authors: Andrea Eliza Dulin and Darryl Javier
 */

public class Main {
    private Owner owner;
    private Scanner sc = new Scanner(System.in);

    public Main(Owner owner) {
        this.owner = owner;
    }

    // Checks if the user's input is valid. if not, it asks again until the input is
    // valid.
    private int intScanner() {
        while (!sc.hasNextInt()) {
            System.out.print("Invalid input. Try again: ");
            sc.next();
        }
        return sc.nextInt();
    }

    private int choiceScanner(int range) {
        int choice = intScanner();

        if (choice < 1 || choice > range) {
            System.out.print("Invalid input. Try again: ");
            return choiceScanner(range);
        }

        return choice;
    }

    /**
     * This class represents the creation of a vending machine.
     * It asks the user to input the name of their machine, then proceeds to ask the
     * user to
     * input the number of slots and the number of items per slot.
     * 
     * If the vending machine already exists, it notifies the user and
     * goes back to the main menu.
     */

    private void createMachine() {
        String machineName;
        int slotsQt = 0;
        int itemSlotsQt = 0;

        System.out.println("--------------------------\n");
        System.out.println("| CREATE VENDING MACHINE |\n");
        System.out.println("--------------------------\n");

        System.out.print("Enter name of vending machine: ");
        machineName = sc.next();

        if (owner.getVendingMachine(machineName) != null) {
            System.out.println("Vending Machine already exists.");
            System.out.println("Failed to create vending machine. Going back to the main menu...");
            render();
        }

        sc.nextLine();
        System.out.print("Enter number of slots(minimum of 8): \n");
        slotsQt = intScanner();

        System.out.print("Enter number of items per slot(minimum of 10): \n");
        itemSlotsQt = intScanner();

        // Checks if the number of slots input by the user is valid. If not, it returns
        // to the main menu.
        if (slotsQt < 8) {
            System.out.println("Invalid Slots Capacity. Must be atleast 8.");
            System.out.println("Going back to the main menu...");
            render();
        }

        // Checks if the number of items per slot input by the user is valid. If not, it
        // returns to the main menu.
        if (itemSlotsQt < 10) {
            System.out.println("Invalid Slot Item Capacity. Must be atleast 10.");
            System.out.println("Going back to the main menu...");
            render();
        }

        /*
         * creates a new vending machine provided by the owner's name, vending machine
         * name,
         * number of slots and number of items per slot
         */
        RegularVendingMachine newVendingMachine = new RegularVendingMachine(owner, machineName, slotsQt, itemSlotsQt);

        owner.addVendingMachine(newVendingMachine);
        System.out.println("Vending Machine successfully created! Going back to the main menu.\n");

        render();
    }

    /**
     * This class is for testing the vending machine or for testing its maintenance
     * features.
     * 
     * @param purpose which is from the render class that when it matches the
     *                assigned string based from the user's input,
     *                it will proceed to display its corresponding detail (whether
     *                testing the machine or testing the maintenance features)
     */

    private void authenticateMachine(String purpose) {
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

        /*
         * Asks the machine name for authentication. It checks whether the vending
         * machine exists or not by
         * accessing the arraylist from the Owner class. If the machine exists, then it
         * proceeds to display the
         * details of their chosen testing feature
         */

        System.out.print("Enter machine name: ");

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

    /**
     * This class is for testing the vending machine.
     * It contains the following features: Display stock and display item.
     * 
     * @param authenticatedMachine which is for verifying whether the vending
     *                             machine exists or not
     */

    private void testVendingMachine(RegularVendingMachine authenticatedMachine) {

        System.out.println("--------------------------\n");
        System.out.println("| TEST A VENDING MACHINE |\n");
        System.out.println("--------------------------\n");
        System.out.println("1 - Display Stock");
        System.out.println("2 - Dispense Item");
        System.out.println("3 - Exit");
        authenticatedMachine.displayInventory();
        System.out.print("Enter choice: ");
        int userInput = choiceScanner(3);

        switch (userInput) {
            /*
             * This displays the stock of the vending machine
             */
            case 1:
                System.out.println("-----------------\n");
                System.out.println("| MACHINE STOCK |\n");
                System.out.println("-----------------\n");
                authenticatedMachine.displayStock();
                testVendingMachine(authenticatedMachine);
                break;
            /*
             * This works by displaying the stock, asking the user for item name and for the
             * quantity they want to dispense.
             * Then, the program asks if the user wants to proceed with the payment or
             * cancel the transaction.
             * 
             * If the user chooses to proceed with the payment, the total cost is displayed,
             * followed by their payment input.
             * After that, the machine dispenses the item upon completing a transaction with
             * the user.
             * 
             * If the user cancels the trasnaction, it goes back to the test vending machine
             * menu.
             */
            case 2:
                System.out.println("-----------------\n");
                System.out.println("| DISPENSE ITEM |\n");
                System.out.println("-----------------\n");

                authenticatedMachine.displayStock();

                String item;
                Item chosenItem;
                int quantity;
                int decision;
                int payment;

                System.out.print("Enter item name: ");
                item = sc.next();
                chosenItem = authenticatedMachine.getItem(item);

                if (chosenItem == null) {
                    System.out.println("Item does not exist in the vending machine.");
                    System.out.println("Going back to the vending machine menu...");
                    testVendingMachine(authenticatedMachine);
                }

                System.out.print("Enter quantity you want to dispense: ");
                quantity = intScanner();

                System.out.print("Enter payment (Whole number only): ");
                payment = intScanner();
                authenticatedMachine.receivePayment(payment);

                System.out.println("You have chosen: ");
                chosenItem.display();
                System.out.println("=============================");
                System.out.println("TOTAL COST: P" + (quantity * chosenItem.getPrice()));
                System.out.println("=============================");

                System.out.println("Do you want to: ");
                System.out.println("1. Dispense Item");
                System.out.println("2. Cancel Dispense");

                System.out.print("Enter number of choice: ");
                decision = choiceScanner(2);

                if (decision == 1) {
                    if (authenticatedMachine.dispenseItem(item, quantity, payment) == null) {
                        System.out.println("Error! Dispensing failed.");
                    } else {
                        System.out.println("Dispensing of item was successful!");
                    }
                }

                if (decision == 2) {
                    System.out.println("Dispensing back change...");
                    authenticatedMachine.dispenseChange(payment, 0);
                    System.out.println("Returning to test vending machine menu....");
                }

                testVendingMachine(authenticatedMachine);
                break;
            case 3:
                render();
                break;
        }
    }

    /**
     * This class is for testing the maintenance features of the vending machine
     * 
     * @param authenticatedMachine which is for verifying whether the vending
     *                             machine exists or not
     */
    private void testMaintenance(RegularVendingMachine authenticatedMachine) {
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

        authenticatedMachine.displayInventory();

        System.out.print("Enter choice: ");
        int userInput = choiceScanner(7);
        switch (userInput) {
            /*
             * This is for collecting the money from the user. This displays the owner's
             * current balance
             * and the new balance once the money has been collected.
             */
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

            /*
             * This is for replenishing the money with valid denominations.
             * It displays the current amount and the new amount once replenished.
             */
            case 2:
                int denomination;
                int amount;

                System.out.println("-------------------\n");
                System.out.println("| REPLENISH MONEY |\n");
                System.out.println("-------------------\n");

                System.out.println("Valid Denominations: 1000, 500, 200, 100, 50, 20, 10, 5, 1");
                System.out.print("Enter denomination of money: ");
                denomination = intScanner();

                System.out.print("Enter amount to input. (Whole number only): ");
                amount = intScanner();

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

            /*
             * This is for adding stocks to the vending machine
             */
            case 3:
                String stockName;
                double stockCalories;
                int stockItemAmount;
                int stockPrice;
                Item existingItem;
                boolean success = false;

                System.out.println("-----------------\n");
                System.out.println("|   ADD STOCK   |\n");
                System.out.println("-----------------\n");

                System.out.println("Create an item to add to the vending machine.");

                System.out.print("Enter name: ");
                stockName = sc.next();

                existingItem = authenticatedMachine.getItem(stockName);

                // ADDED A CHECK IN CASE THE USER IS GOING TO ADD STOCK TO AN EXISTING ITEM.
                if (existingItem != null) {
                    System.out.println("An existing item with the same name has been found. Proceeding to restock.");
                    System.out.print("Enter the amount to restock: ");
                    stockItemAmount = intScanner();
                    System.out.println("Restocking slot...");
                    success = owner.restock(authenticatedMachine, stockName, stockItemAmount);

                    if (success) {
                        System.out.println("Restock successful. Going back to the maintenance menu...");
                    } else {
                        System.out.println("Failed to restock. Going back to the maintenanve menu...");
                    }

                    testMaintenance(authenticatedMachine);
                }

                System.out.print("Enter calories: ");
                stockCalories = sc.nextDouble();

                System.out.print("Enter price: ");
                stockPrice = sc.nextInt();

                Item stockItem = new Item(stockName, stockPrice, stockCalories);

                System.out.print("Enter amount to put in: ");
                stockItemAmount = intScanner();

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
            /*
             * This is for restocking the items in the vending machine
             */
            case 4:
                String name;
                boolean operationSuccessful;

                System.out.println("-----------------\n");
                System.out.println("|    RESTOCK    |\n");
                System.out.println("-----------------\n");

                System.out.print("Enter name of the item to restock: ");
                name = sc.next();

                System.out.print("Enter quantity to put in: ");
                int itemAmount = intScanner();

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
            /*
             * This is for updating/changing the price of an item
             */
            case 5:
                String itemName;
                int newPrice;

                System.out.println("-----------------\n");
                System.out.println("| CHANGE PRICE  |\n");
                System.out.println("-----------------\n");

                System.out.print("Enter the Item Name to set price: ");
                itemName = sc.next();

                System.out.print("Enter the new price (cents not allowed): ");
                newPrice = intScanner();

                if (owner.setPrice(authenticatedMachine, itemName, newPrice)) {
                    System.out.println("Price changed successfully. Going back to maintenance menu...");
                } else
                    System.out.println("Price changing failed. Going back to the maintenance menu...");
                testMaintenance(authenticatedMachine);
                break;
            /*
             * This displays a summary of the transactions made by the user
             */
            case 6:
                System.out.println("------------------------\n");
                System.out.println("| TRANSACTION SUMMARY  |\n");
                System.out.println("------------------------\n");

                authenticatedMachine.displayInventory();
                authenticatedMachine.getTransactionSummary();

                testVendingMachine(authenticatedMachine);
                break;
            case 7:
                render();
                break;
        }
    }

    /*
     * This class is for displaying the main menu which allows the user to
     * choose the service they'd like to avail.
     */

    private void render() {
        int userInput = 0;

        System.out.println("----------------------------------------------\n");
        System.out.println("|  Welcome to AnReal's Business Enterprises! |\n");
        System.out.println("----------------------------------------------\n");

        System.out.println("1 - Create vending machine");
        System.out.println("2 - Test vending machine features");
        System.out.println("3 - Test maintenance features");
        System.out.println("4 - Exit");
        System.out.print("Enter choice: ");

        userInput = choiceScanner(4);
        System.out.println(userInput);
        switch (userInput) {
            case 1:
                // The user will be redirected to the creation of the machine
                createMachine();
                break;
            case 2:
                // The user will be redirected to the testing the machine
                authenticateMachine("Testing");
                break;
            case 3:
                // The user will be redirected to the testing of the machine's maintenance
                authenticateMachine("Maintenance");
            case 4:
                // This will terminate the program
                System.exit(200);
            default:
                break;
        }
    }

    public static void main(String[] args) {
        ArrayList<Owner> owners = new ArrayList<Owner>();
        int userChoice = 0;
        Scanner sc = new Scanner(System.in);
        Main mainMenu;

        while (true) {
            System.out.println("----------------------------------------------\n");
            System.out.println("|  Welcome to AnReal's Business Enterprises! |\n");
            System.out.println("----------------------------------------------\n");
            System.out.println("1 - Create an account");
            System.out.println("2 - Login");
            System.out.println("3 - Change Password");
            System.out.print("Enter number of choice: ");
            userChoice = sc.nextInt();

            switch (userChoice) {
                case 1:
                    String userName;
                    String password;
                    int balance;
                    boolean accountExists = false;
                    System.out.println("------------------------");
                    System.out.println("|   CREATE AN ACCOUNT  |");
                    System.out.println("------------------------");
                    System.out.print("Enter name: ");
                    userName = sc.next();

                    for (Owner owner : owners) {
                        String currOwnerName = owner.getName();
                        if (currOwnerName.equals(userName)) {
                            System.out.println("Account already exists. Going back to the start menu.");
                            accountExists = true;
                        }
                    }

                    if (!accountExists) {
                        System.out.print("Enter password: ");
                        password = sc.next();

                        System.out.print("Enter starting balance: ");
                        balance = sc.nextInt();

                        Owner newOwner = new Owner(userName, balance, password);
                        owners.add(newOwner);
                        System.out.println("Account created! Going back to start menu...");
                    }
                    break;
                case 2:
                    String loginName;
                    String loginPass;

                    System.out.println("------------------------");
                    System.out.println("|        LOG IN        |");
                    System.out.println("------------------------");

                    System.out.print("Enter name: ");
                    loginName = sc.next();
                    System.out.print("Enter password: ");
                    loginPass = sc.next();

                    for (Owner owner : owners) {
                        String currOwnerName = owner.getName();
                        String currOwnerPass = owner.getPassword();

                        if (currOwnerName.equals(loginName) && currOwnerPass.equals(loginPass)) {
                            System.out.println("Login success! Going to the main menu...");
                            mainMenu = new Main(owner);
                            mainMenu.render();
                        }
                    }

                    System.out.println("Wrong credentials. Going back to the start menu.");
                    break;

                case 3:
                    Owner changePassOwner = null;
                    String changePassName;
                    String newPass;

                    System.out.println("------------------------");
                    System.out.println("|    CHANGE PASSWORD   |");
                    System.out.println("------------------------");
                    System.out.print("Enter name: ");
                    changePassName = sc.next();

                    for (Owner owner : owners) {
                        String currOwnerName = owner.getName();

                        if (currOwnerName.equals(changePassName)) {
                            System.out.println("Account found!");
                            changePassOwner = owner;
                        }
                    }

                    if (changePassOwner != null) {
                        System.out.print("Enter new password: ");
                        newPass = sc.next();
                        changePassOwner.setPassword(newPass);

                        System.out.println("Password changed successfully. Going back to the start menu...");
                    } else {
                        System.out.println("Account not found. Going back to the start menu...");
                    }

                    break;
                default:
                    System.out.println("Input invalid. Assuming exit...");
                    System.exit(200);
                    break;
            }
        }

    }
}