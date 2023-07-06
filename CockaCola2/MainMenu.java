import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {
    static Owner owner = new Owner("AnReal Enterprises", 0);
    static ArrayList<RegularVendingMachine> vendingMachines = owner.getVendingMachines();
    static RegularVendingMachine authenticatedMachine = null;
    static ArrayList<Slot> machineSlots = null;

    public static void render() {

        /**
         * This is a simulation of a regular vending machine which consists
         * of items and has maintenance features.
         * <p>
         * Authors: Andrea Eliza Dulin and Darryl Javier
         */

        /*
         * - Simulate the program in like a main menu fashion.
         * - Navigate through different pages based on the functions of the program.
         * - Create a class for each page, and the only method there is in that class is
         * render() in which it will display the text.
         * - We are going to do this recursively.
         */
        int userInput = 0;

        Scanner sc = new Scanner(System.in);
        System.out.println("----------------------------------------------\n");
        System.out.println("|  Welcome to AnReal's Business Enterprises! |\n");
        System.out.println("----------------------------------------------\n");
        System.out.println("Menu:");
        System.out.println("(1). Create vending machine");
        System.out.println("(2). Test vending machine features");
        System.out.println("(3). Test maintenance features");
        System.out.println("(4). Exit");
        System.out.println("Enter the number of your choice: ");

        userInput = sc.nextInt();
        switch (userInput) {
            case 1:
                String machineName;
                int slotsQt = 0;
                int itemSlotsQt = 0;

                System.out.println("--------------------------\n");
                System.out.println("| CREATE VENDING MACHINE |\n");
                System.out.println("--------------------------\n");

                System.out.println("Enter name of vending machine: ");
                sc.next();
                machineName = sc.nextLine();

                System.out.println("Enter number of slots(minimum of 8): \n");
                slotsQt = sc.nextInt();

                System.out.println("Enter number of items per slot(minimum of 10): \n");
                itemSlotsQt = sc.nextInt();

                RegularVendingMachine newVendingMachine = new RegularVendingMachine(owner, machineName, slotsQt,
                        itemSlotsQt);

                owner.addVendingMachine(newVendingMachine);
                System.out.println("Vending Machine successfully created! Going back to the main menu.\n");

                MainMenu.render();
                break;
            case 2:
                String testMachineName = "";
                int testMachineUserInput = 1;
                System.out.println("--------------------------\n");
                System.out.println("| TEST A VENDING MACHINE |\n");
                System.out.println("--------------------------\n");
                System.out.println("Enter machine name: ");
                sc.next();
                testMachineName = sc.nextLine();

                authenticatedMachine = owner.getVendingMachine(testMachineName);
                machineSlots = authenticatedMachine.getItemSlots();

                for (int i = 0; i < vendingMachines.size(); ++i) {
                    System.out.println(vendingMachines.get(i).getName());
                    if (vendingMachines.get(i) != null && vendingMachines.get(i).getName().equals(testMachineName)) {
                        authenticatedMachine = vendingMachines.get(i);
                        machineSlots = authenticatedMachine.getItemSlots();
                    }
                }

                if (authenticatedMachine == null) {
                    System.out.println("Failed to authenticate. Going back to the main menu...");
                    MainMenu.render();
                }

                switch (testMachineUserInput) {
                    case 1:
                        String itemName;
                        Slot slotSearched = null;

                        int payment = 0;
                        int amount = 0;

                        System.out.println("-----------------\n");
                        System.out.println("| DISPENSE ITEM |\n");
                        System.out.println("-----------------\n");
                        System.out.println("Enter item name: \n");
                        itemName = sc.nextLine();

                        for (int i = 0; i < machineSlots.size(); ++i) {
                            Item slotItem = machineSlots.get(i).getSlotItemType();
                            if (slotItem.getName().equals(itemName)) {
                                slotSearched = machineSlots.get(i);
                            }
                        }

                        if (slotSearched == null) {
                            System.out.println(
                                    "Item is not located inside the vending machine. Going back to the main menu...");
                            MainMenu.render();
                        }

                        System.out.println("Enter amount: \n");
                        amount = sc.nextInt();

                        System.out.println("Enter payment: \n");
                        payment = sc.nextInt();

                        authenticatedMachine.receivePayment(payment);
                        System.out.println("Processing payment.....\n");

                        Item boughtItem = slotSearched.getSlotItemType();
                        int boughtItemPrice = slotSearched.getPrice();

                        new Transaction(boughtItem, amount, amount * boughtItemPrice).displayTransaction();
                        authenticatedMachine.dispenseChange(payment, amount * boughtItemPrice);
                        System.out.println("Change: ");

                        System.out.println("Dispensing item.....\n");
                        System.out.println("Enjoy! Returning to main menu.....\n");
                        MainMenu.render();
                        break;
                    case 2:
                        ArrayList<Slot> testVendingMachineSlots = authenticatedMachine.getItemSlots();
                        System.out.println("-----------------\n");
                        System.out.println("| MACHINE STOCK |\n");
                        System.out.println("-----------------\n");

                        for (int i = 0; i < testVendingMachineSlots.size(); ++i) {
                            System.out.println(testVendingMachineSlots.get(i).toString());
                            System.out.println("-------------------------------------");
                        }
                        MainMenu.render();
                        break;
                    case 3:
                        authenticatedMachine.getTransactionSummary();
                        MainMenu.render();
                        break;
                    case 4:
                        authenticatedMachine.displayInventory();
                        MainMenu.render();
                        break;
                }

                MainMenu.render();
                break;
            case 3:
                String maintenanceMachineName;
                int testMaintenanceUserInput = 1;
                System.out.println("--------------------\n");
                System.out.println("| TEST MAINTENANCE |\n");
                System.out.println("--------------------\n");
                System.out.println("Enter vending machine name: \n");

                sc.next();
                maintenanceMachineName = sc.nextLine();

                System.out.println("Enter the number of your choice: \n");
                System.out.println("(1). Collect money \n");
                System.out.println("(2). Replenish money \n");
                System.out.println("(3). Restock \n");
                System.out.println("(4). Set price \n");

                sc.nextLine();
                testMachineUserInput = sc.nextInt();

                for (int i = 0; i < vendingMachines.size(); ++i) {
                    if (vendingMachines.get(i).getName().equals(maintenanceMachineName)) {
                        authenticatedMachine = vendingMachines.get(i);
                        machineSlots = authenticatedMachine.getItemSlots();
                    }
                }

                if (authenticatedMachine == null) {
                    System.out.println("Failed to authenticate. Going back to the main menu...");
                    MainMenu.render();
                }

                switch (testMaintenanceUserInput) {
                    case 1:
                        System.out.println("Collecting money...");
                        System.out.println("Collected money: " + authenticatedMachine.getCollectedMoney());
                        System.out.println("Current balance: " + owner.getBalance());
                        break;
                    case 2:
                        int denomination;
                        int amount;

                        System.out.print("Enter denomination of money. ");
                        denomination = sc.nextInt();

                        System.out.print("Enter amount to input. (Cents not allowed): ");
                        amount = sc.nextInt();

                        if (authenticatedMachine.denominationIsValid(denomination)) {
                            owner.replenishMoney(0, amount, denomination);
                            System.out.println("Replenishing money...");
                            System.out.print("New " + denomination + " amount: ");
                            authenticatedMachine.displayMoney(denomination);
                            System.out.println("Replenish successful.");
                            System.out.println("Going back to the main menu...");
                            MainMenu.render();
                        }
                        break;
                    case 3:
                        String name;
                        double calories;
                        System.out.println("Create an item to add to the vending machine.");
                        System.out.println("Enter name");
                        name = sc.nextLine();
                        System.out.println("Enter calories");
                        calories = sc.nextDouble();

                        Item newItem = new Item(name, calories);

                        System.out.println("Enter amount to put in: ");
                        int itemAmount = sc.nextInt();

                        if (authenticatedMachine.slotsAreFull()) {
                            System.out.println("Slots are full. Cannot insert item anymore.");
                            System.out.println("Going back to the main menu...");
                            MainMenu.render();
                        }

                        for (int i = 0; i < machineSlots.size(); ++i) {
                            if (machineSlots.get(i).isEmpty()) {
                                System.out.println("Restocking slot...");
                                machineSlots.get(i).restockSlot(newItem, itemAmount);
                            }
                        }
                        System.out.println("Restock slot successful. Going back to the main menu.");
                        break;

                    case 4:
                        String itemName;
                        int newPrice;
                        System.out.println("Enter the Item Name to set price");
                        itemName = sc.nextLine();
                        System.out.println("Enter the new price (cents not allowed).");
                        newPrice = sc.nextInt();
                        for (int i = 0; i < machineSlots.size(); ++i) {
                            if (machineSlots.get(i).getSlotItemType().getName().equals(itemName)) {
                                System.out.println("Setting new price...");
                                owner.setPrice(authenticatedMachine, itemName, newPrice);
                            }
                        }
                        break;
                }
            case 4:
                System.exit(0);
                break;
            default:
                System.out.println("I love andrea <3");
                break;
        }
    }
}
