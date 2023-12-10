/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.event;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * SceneEvent represents an event related to a scene in the application.
 * It implements the EventInterface and provides methods to retrieve and set the message and event type associated with the scene event.
 */
@JsonTypeName("scene")
public class SceneEvent implements EventInterface {
    private String message;
    private SceneEventType eventType;

    public SceneEvent() {
    }

    /**
     * Constructs a SceneEvent with a message and scene event type.
     * @param message The message associated with the scene event.
     * @param eventType The type of the scene event.
     */
    public SceneEvent(String message, SceneEventType eventType) {
        this.message = message;
        this.eventType = eventType;
    }

    /**
     * Retrieves the type of the scene event.
     * @return The scene event type.
     */
    public SceneEventType getEventType() {
        return eventType;
    }

    /**
     * Retrieves the message associated with the scene event.
     * @return The message of the scene event.
     */
    @Override
    public String getMessage() {
        return this.message;
    }

    /**
     * Sets the message associated with the scene event.
     * @param message The message to be set for the scene event.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Sets the type of the scene event.
     * @param eventType The scene event type to be set.
     */
    public void setEventType(SceneEventType eventType) {
        this.eventType = eventType;
    }
}