/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

/**
 *
 * @author Darryl
 */
public class DashboardModel {

    Owner authenticatedOwner;

    public DashboardModel(Owner owner) {
        authenticatedOwner = owner;
    }

    public Owner getAuthOwner() {
        return this.authenticatedOwner;
    }
}
