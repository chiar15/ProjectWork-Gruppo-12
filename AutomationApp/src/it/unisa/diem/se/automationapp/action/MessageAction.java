/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.action;

import it.unisa.diem.se.automationapp.observer.EventBus;
import it.unisa.diem.se.automationapp.observer.MessageEvent;
import it.unisa.diem.se.automationapp.observer.ErrorEventType;
import java.util.Map;

/**
 *
 * @author chiar
 */
public class MessageAction implements ActionInterface{
    private String message;

    public MessageAction(Map<String, String> actionData) {
        this.message = actionData.get("message");
    }

    public String getMessage() {
        return message;
    }
    
    @Override
    public void execute() throws Exception {
        EventBus eventBus = EventBus.getInstance();
        
        eventBus.publish(new MessageEvent(message));
    }

    @Override
    public String toString() {
        return "POPUP MESSAGE - " + "Message: " + message;
    }

    @Override
    public String getType() {
        return ActionEnum.MESSAGEACTION.name();
    }
}
