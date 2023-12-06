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
@JsonTypeName("error")
public class ErrorEvent implements EventInterface{
    private String message;
    private ErrorEventType eventType;

    public ErrorEvent() {
    }

    
    public ErrorEvent(String message, ErrorEventType eventType) {
        this.message = message;
        this.eventType = eventType;
    }
    
    @Override
    public String getMessage() {
        return this.message;
    }

    public ErrorEventType getEventType() {
        return eventType;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setEventType(ErrorEventType eventType) {
        this.eventType = eventType;
    }
    
}
