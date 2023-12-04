/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.observer;

/**
 *
 * @author chiar
 */
public class SaveEvent implements EventInterface{
    private final String message;
    private final SaveEventType type;

    public SaveEvent(String message, SaveEventType type) {
        this.message = message;
        this.type = type;
    }
    
    @Override
    public String getMessage() {
        return this.message;
    }

    public SaveEventType getType() {
        return type;
    }
    
}
