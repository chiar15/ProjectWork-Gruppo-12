/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.observer;

public class MessageEvent implements EventInterface{
    private final String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}

