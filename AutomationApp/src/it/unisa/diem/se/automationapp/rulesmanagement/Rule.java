/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.unisa.diem.se.automationapp.action.ActionInterface;
import it.unisa.diem.se.automationapp.trigger.TriggerInterface;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "type")
@JsonSubTypes({
  @JsonSubTypes.Type(value = Rule.class, name = "rule"),
  @JsonSubTypes.Type(value = SuspendedRuleDecorator.class, name = "suspendedRule")
})
public class Rule {
    private String name;
    private TriggerInterface trigger;
    private ActionInterface action;
    private boolean wasExecuted;
    private boolean isActive;
    
    public Rule(){
        
    }
    
    public Rule(String name, TriggerInterface trigger, ActionInterface action) {
        this.name = name;
        this.trigger = trigger;
        this.action = action;
        this.wasExecuted = false;
        this.isActive = true;
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

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
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
    
    @JsonIgnore
    public boolean isTriggered(){
        return this.trigger.isTriggered();
    }
    
    public void execute()throws Exception{
        this.action.execute();
    }
   
}

