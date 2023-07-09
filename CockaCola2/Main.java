import java.util.Scanner;

public class Main {
    private static Owner owner = new Owner("AnReal Enterprises", 0);
    private static Scanner sc = new Scanner(System.in);

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

        RegularVendingMachine newVendingMachine = new RegularVendingMachine(owner, machineName, slotsQt,
                itemSlotsQt);

        owner.addVendingMachine(newVendingMachine);
        System.out.println("Vending Machine successfully created! Going back to the main menu.\n");

        render();
    }

    private static void authenticateMachine(String purpose) {
        if (purpose != "Maintenance" || purpose != "Testing") {
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
        System.out.println("1 - Dispense Item");
        System.out.println("2 - Display Current Stock");
        System.out.println("3 - Display Transaction Summary ");
        System.out.println("4 - Display Inventory");
        System.out.println("5 - Exit");
        int userInput = sc.nextInt();
        switch (userInput) {
            case 1:
                String itemName;
                Slot slotSearched = null;

                int payment = 0;
                int amount = 0;

                System.out.println("-----------------\n");
                System.out.println("| DISPENSE ITEM |\n");
                System.out.println("-----------------\n");
                System.out.println("Enter item name: \n");
                render();
                break;
            case 4:
                authenticatedMachine.displayInventory();
                testVendingMachine(authenticatedMachine);
            case 5:
                render();
                break;
        }
    }

    private static void testMaintenance(RegularVendingMachine authenticatedMachine) {

    }

    private static void render() {
        int userInput = 0;

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