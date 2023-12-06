/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.event;

import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 *
 * @author chiar
 */
@JsonTypeName("audio")
public class AudioEvent implements EventInterface{
    private String message;
    private AudioEventType eventType;

    public AudioEvent() {
    }

    public AudioEvent(String message, AudioEventType eventType) {
        this.message = message;
        this.eventType = eventType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setEventType(AudioEventType eventType) {
        this.eventType = eventType;
    }

    public AudioEventType getEventType() {
        return eventType;
    }
    
    
    @Override
    public String getMessage() {
       return this.message;
    }
    
}
