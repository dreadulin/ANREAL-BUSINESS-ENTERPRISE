/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

import java.util.ArrayList;

/**
 *
 * @author Darryl
 */
public class StartModel {

    ArrayList<Owner> owners = new ArrayList<>();

    /**
     * This initializes the owners which will be used all throughout the program
     *
     * @param ownerArray which is the array that stores the details of the user
     */
    public StartModel(ArrayList<Owner> ownerArray) {
        owners = ownerArray;
    }

    public ArrayList<Owner> getOwnersList() {
        return this.owners;
    }

    public Owner getOwner(String name) {
        for (Owner owner : owners) {
            if (owner.getName().equals(name)) {
                return owner;
            }
        }
        return null;
    }

    public boolean createOwner(String ownerName, String newPass, int balance) {
        Owner existingOwner = this.getOwner(ownerName);

        if (existingOwner != null) {
            return false;
        }

        Owner newOwner = new Owner(ownerName, balance, newPass);
        owners.add(newOwner);
        return true;
    }

    public boolean changeOwnerPassword(String ownerName, String newPass) {
        Owner changePassOwner = this.getOwner(ownerName);

        if (changePassOwner != null) {
            changePassOwner.setPassword(newPass);
            return true;
        } else {
            return false;
        }
    }

    public Owner loginOwner(String ownerName, String ownerPass) {
        for (Owner owner : owners) {
            String currOwnerName = owner.getName(); // gets the owner's name from the textfield 
            String currOwnerPass = owner.getPassword(); // gets the owner's password from the textfield 

            if (currOwnerName.equals(ownerName) && currOwnerPass.equals(ownerPass)) {
                return owner;
            } else {
                return null;
            }
        }

        return null;
    }
}
