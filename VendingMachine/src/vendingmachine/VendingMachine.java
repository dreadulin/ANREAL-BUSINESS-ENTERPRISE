package vendingmachine;

import java.util.ArrayList;

/**
 * This class represents the whole vending machine program
 *
 * @author Andrea Dulin and Darryl Javier
 */
public class VendingMachine {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Owner> owners = new ArrayList<>();
        StartController startController = new StartController(owners);
    }
}
