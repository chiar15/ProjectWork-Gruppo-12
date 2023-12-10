/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.event;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * FileEvent represents an event related to file operations in the application.
 * It implements the EventInterface and provides methods to retrieve and set the message associated with the file event.
 */
@JsonTypeName("file")
public class FileEvent implements EventInterface {
    private String message;

    public FileEvent() {
    }

    /**
     * Constructs a FileEvent with a specific message.
     * @param message The message associated with the file event.
     */
    public FileEvent(String message) {
        this.message = message;
    }

    /**
     * Sets the message associated with the file event.
     * @param message The message to be set for the file event.
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * Retrieves the message associated with the file event.
     * @return The message of the file event.
     */
    @Override
    public String getMessage() {
        return message;
    }
}