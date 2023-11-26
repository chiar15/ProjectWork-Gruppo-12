/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import it.unisa.diem.se.automationapp.action.ActionInterface;
import it.unisa.diem.se.automationapp.trigger.TriggerInterface;

/**
 *
 * @author chiar
 */
public class Rule {
    private String name;
    private TriggerInterface trigger;
    private ActionInterface action;
    
    public Rule(String name, TriggerInterface trigger, ActionInterface action) {
        this.name = name;
        this.trigger = trigger;
        this.action = action;
    }

    public String getName() {
        return name;
    }
    
    public TriggerInterface getTrigger() {
        return trigger;
    }
    
    public ActionInterface getAction() {
        return action;
    }
    
    public boolean isTriggered(){
        return this.trigger.isTriggered();
    }
    
    public void execute(){
        this.action.execute();
    }
}

