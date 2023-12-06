/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import it.unisa.diem.se.automationapp.action.ActionInterface;
import it.unisa.diem.se.automationapp.trigger.TriggerInterface;

public class Rule {
    private String name;
    private TriggerInterface trigger;
    private ActionInterface action;
    private boolean wasExecuted;
    
    public Rule(){
        
    }
    
    public Rule(String name, TriggerInterface trigger, ActionInterface action) {
        this.name = name;
        this.trigger = trigger;
        this.action = action;
        this.wasExecuted = false;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setTrigger(TriggerInterface trigger) {
        this.trigger = trigger;
    }

    public void setAction(ActionInterface action) {
        this.action = action;
    }

    public boolean getWasExecuted() {
        return wasExecuted;
    }

    public void setWasExecuted(boolean wasExecuted) {
        this.wasExecuted = wasExecuted;
    }
    
    public boolean isTriggered(){
        return this.trigger.isTriggered();
    }
    
    public void execute()throws Exception{
        this.action.execute();
    }
   
}

