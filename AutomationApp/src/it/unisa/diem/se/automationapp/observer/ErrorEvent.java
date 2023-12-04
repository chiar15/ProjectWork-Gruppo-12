/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.observer;

/**
 *
 * @author chiar
 */
public class ErrorEvent implements EventInterface{
    private final String message;
    private final ErrorEventType type;

    public ErrorEvent(String message, ErrorEventType type) {
        this.message = message;
        this.type = type;
    }
    
    @Override
    public String getMessage() {
        return this.message;
    }

    public ErrorEventType getType() {
        return type;
    }
    
}