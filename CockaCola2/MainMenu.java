import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {
    static Owner owner = new Owner("AnReal Enterprises", 0);

    public static void render() {
        ArrayList<RegularVendingMachine> vendingMachines = owner.getVendingMachines();
        RegularVendingMachine authenticatedMachine = null;
        ArrayList<Slot> machineSlots = null;

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

                System.out.println(newVendingMachine.getName());
                owner.addVendingMachine(newVendingMachine);
                System.out.println("Vending Machine successfully created! Going back to the main menu.\n");

                System.out.println(owner.getVendingMachine("Darryl").getName());

                MainMenu.render();
                break;
        }
    }
}
