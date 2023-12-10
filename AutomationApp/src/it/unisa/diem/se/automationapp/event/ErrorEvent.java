/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.event;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * The ErrorEvent class represents an event related to an error occurring within the application.
 * It implements the EventInterface.
 */
@JsonTypeName("error")
public class ErrorEvent implements EventInterface {
    private String message;
    private ErrorEventType eventType;

    /**
     * Default constructor for the ErrorEvent class.
     */
    public ErrorEvent() {
    }

    /**
     * Constructs an ErrorEvent object with a message and an error event type.
     * @param message The message associated with the error event.
     * @param eventType The type of error event (ErrorEventType).
     */
    public ErrorEvent(String message, ErrorEventType eventType) {
        this.message = message;
        this.eventType = eventType;
    }
    
    /**
     * Retrieves the message associated with the error event.
     * @return The message of the error event.
     */
    @Override
    public String getMessage() {
        return this.message;
    }

    /**
     * Retrieves the error event type associated with the error event.
     * @return The error event type.
     */
    public ErrorEventType getEventType() {
        return eventType;
    }

    /**
     * Sets the message for the error event.
     * @param message The message to be set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Sets the error event type for the error event.
     * @param eventType The error event type to be set.
     */
    public void setEventType(ErrorEventType eventType) {
        this.eventType = eventType;
    }
}
