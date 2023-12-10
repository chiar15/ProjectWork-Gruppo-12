/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.action;

import it.unisa.diem.se.automationapp.action.exception.InvalidInputException;
import it.unisa.diem.se.automationapp.eventsmanagement.EventBus;
import it.unisa.diem.se.automationapp.event.MessageEvent;
/**
 *
 * @author chiar
 */
public class MessageAction implements ActionInterface{
    private String message;

    public MessageAction(){     
    }
    
    public MessageAction(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    @Override
    public void execute() throws InvalidInputException {
        EventBus eventBus = EventBus.getInstance();
        
        if(message == null || message.trim().isEmpty()){
            throw new InvalidInputException("The message to show cannot be empty.");
        }
        
        eventBus.publish(new MessageEvent(message));
    }

    @Override
    public String toString() {
        return "Visual reminder of the message: " + message;
    }
}
