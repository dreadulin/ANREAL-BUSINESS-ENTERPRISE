/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vendingmachine;

/**
 *
 * @author Darryl
 */
public class Response {

    private final boolean status;
    private final String message;
    private final Object response;

    public Response(boolean status, String message, Object response) {
        this.status = status;
        this.message = message;
        this.response = response;
    }

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getResponse() {
        return response;
    }
}
