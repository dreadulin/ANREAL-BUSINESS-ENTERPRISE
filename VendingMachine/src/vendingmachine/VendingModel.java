/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

/**
 *
 * @author Darryl
 */
public class VendingModel {

    Owner authorizedOwner;
    RegularVendingMachine authenticatedRegularMachine;
    SpecialVendingMachine authenticatedSpecialMachine;

    public VendingModel(Owner owner, RegularVendingMachine regularMachine, SpecialVendingMachine specialMachine) {
        authorizedOwner = owner;
        authenticatedRegularMachine = regularMachine;
        authenticatedSpecialMachine = specialMachine;
    }

    public Owner getAuthOwner() {
        return this.authorizedOwner;
    }

    public RegularVendingMachine getAuthRegularMachine() {
        return this.authenticatedRegularMachine;
    }

    public SpecialVendingMachine getAuthSpecialMachine() {
        return this.authenticatedSpecialMachine;
    }

    public void authenticateRegularMachine(String machineName) {
        authenticatedRegularMachine = authorizedOwner.getRegularMachine(machineName);
    }

    public void authenticateSpecialMachine(String machineName) {
        authenticatedSpecialMachine = authorizedOwner.getSpecialMachine(machineName);
    }

}
