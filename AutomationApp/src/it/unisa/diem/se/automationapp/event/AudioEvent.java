/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.event;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * The AudioEvent class represents an event related to audio functionalities.
 * It implements the EventInterface.
 */
@JsonTypeName("audio")
public class AudioEvent implements EventInterface {
    private String message;
    private AudioEventType eventType;

    /**
     * Default constructor for the AudioEvent class.
     */
    public AudioEvent() {
    }

    /**
     * Constructs an AudioEvent object with a message and audio event type.
     * @param message The message associated with the audio event.
     * @param eventType The type of audio event (e.g., STARTED, STOPPED).
     */
    public AudioEvent(String message, AudioEventType eventType) {
        this.message = message;
        this.eventType = eventType;
    }

    /**
     * Sets the message for the audio event.
     * @param message The message to be set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Sets the audio event type.
     * @param eventType The audio event type to be set.
     */
    public void setEventType(AudioEventType eventType) {
        this.eventType = eventType;
    }

    /**
     * Retrieves the audio event type.
     * @return The audio event type.
     */
    public AudioEventType getEventType() {
        return eventType;
    }
    
    /**
     * Retrieves the message associated with the audio event.
     * @return The message of the audio event.
     */
    @Override
    public String getMessage() {
       return this.message;
    }
}