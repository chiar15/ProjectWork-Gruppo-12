/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.event;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * MessageEvent represents an event containing a message in the application.
 * It implements the EventInterface and provides methods to retrieve and set the message associated with the event.
 */
@JsonTypeName("message")
public class MessageEvent implements EventInterface {
    private String message;

    public MessageEvent() {
    }

    /**
     * Constructs a MessageEvent with a specific message.
     * @param message The message associated with the event.
     */
    public MessageEvent(String message) {
        this.message = message;
    }

    /**
     * Retrieves the message associated with the event.
     * @return The message of the event.
     */
    @Override
    public String getMessage() {
        return this.message;
    }

    /**
     * Sets the message associated with the event.
     * @param message The message to be set for the event.
     */
    public void setMessage(String message) {
        this.message = message;
    }
}