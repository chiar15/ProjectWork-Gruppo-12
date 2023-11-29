/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.observer;

public class MessageEvent {
    private final String message;
    private final MessageEventType type;

    public MessageEvent(String message, MessageEventType type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }
    
    public MessageEventType getType() {
        return type;
    }
 
}
