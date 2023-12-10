/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.event;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * The CloseEvent class represents an event indicating the closure of an application or a process.
 * It implements the EventInterface.
 */
@JsonTypeName("close")
public class CloseEvent implements EventInterface {
    private String message;

    /**
     * Default constructor for the CloseEvent class.
     */
    public CloseEvent() {
    }

    /**
     * Constructs a CloseEvent object with a message.
     * @param message The message associated with the close event.
     */
    public CloseEvent(String message) {
        this.message = message;
    }
    
    /**
     * Retrieves the message associated with the close event.
     * @return The message of the close event.
     */
    @Override
    public String getMessage() {
        return this.message;
    }

    /**
     * Sets the message for the close event.
     * @param message The message to be set.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}