/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.observer;

public class ErrorEvent {
    private final String errorMessage;
    private final EventType type;

    public ErrorEvent(String errorMessage, EventType type) {
        this.errorMessage = errorMessage;
        this.type = type;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    
    public EventType getType() {
        return type;
    }
 
}

