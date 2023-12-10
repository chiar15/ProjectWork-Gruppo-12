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

/**
 * The Rule class represents a rule that contains a name, trigger, action, and status flags to track its execution state.
 * It utilizes Jackson annotations for serialization and deserialization purposes.
 */
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
    
    /**
     * Default constructor for Rule.
     */
    public Rule(){
        
    }
    
    /**
     * Constructs a Rule object with a name, trigger, and action.
     * @param name The name of the rule.
     * @param trigger The trigger interface associated with the rule.
     * @param action The action interface associated with the rule.
     */
    public Rule(String name, TriggerInterface trigger, ActionInterface action) {
        this.name = name;
        this.trigger = trigger;
        this.action = action;
        this.wasExecuted = false;
        this.isActive = true;
    }

    /**
     * Gets the name of the rule.
     * @return The name of the rule.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the trigger interface associated with the rule.
     * @return The trigger interface.
     */
    public TriggerInterface getTrigger() {
        return trigger;
    }
    
    /**
     * Gets the action interface associated with the rule.
     * @return The action interface.
     */
    public ActionInterface getAction() {
        return action;
    }

    /**
     * Gets the status of the rule's activity.
     * @return The status of the rule's activity.
     */
    public boolean getIsActive() {
        return isActive;
    }

    /**
     * Sets the status of the rule's activity.
     * @param isActive The status of the rule's activity.
     */
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Sets the name of the rule.
     * @param name The name to be set for the rule.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the trigger interface for the rule.
     * @param trigger The trigger interface to be set for the rule.
     */
    public void setTrigger(TriggerInterface trigger) {
        this.trigger = trigger;
    }

    /**
     * Sets the action interface for the rule.
     * @param action The action interface to be set for the rule.
     */
    public void setAction(ActionInterface action) {
        this.action = action;
    }

    /**
     * Gets the execution status of the rule.
     * @return The execution status of the rule.
     */
    public boolean getWasExecuted() {
        return wasExecuted;
    }

    /**
     * Sets the execution status of the rule.
     * @param wasExecuted The execution status of the rule.
     */
    public void setWasExecuted(boolean wasExecuted) {
        this.wasExecuted = wasExecuted;
    }
    
    /**
     * Checks if the rule is triggered based on its associated trigger.
     * @return A boolean indicating if the rule is triggered.
     */
    @JsonIgnore
    public boolean isTriggered(){
        return this.trigger.isTriggered();
    }
    
    /**
     * Executes the action associated with the rule.
     * @throws Exception If an error occurs during the execution of the action.
     */
    public void execute() throws Exception {
        this.action.execute();
    }
}