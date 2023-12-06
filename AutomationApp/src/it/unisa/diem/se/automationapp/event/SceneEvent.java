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
@JsonTypeName("scene")
public class SceneEvent implements EventInterface{
    private String message;
    private SceneEventType eventType;

    public SceneEvent() {
    }

    
    public SceneEvent(String message, SceneEventType eventType) {
        this.message = message;
        this.eventType = eventType;
    }

    public SceneEventType getEventType() {
        return eventType;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setEventType(SceneEventType eventType) {
        this.eventType = eventType;
    }
    
}
