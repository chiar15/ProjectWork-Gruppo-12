/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.event;

import com.fasterxml.jackson.annotation.JsonTypeName;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;

/**
 * The ActiveEvent class represents an event indicating an active state change related to a rule.
 * It implements the EventInterface.
 */
@JsonTypeName("active")
public class ActiveEvent implements EventInterface {
    private String message;
    private Rule rule;

    /**
     * Default constructor for the ActiveEvent class.
     */
    public ActiveEvent() {
    }

    /**
     * Constructs an ActiveEvent object with a message and associated rule.
     * @param message The message associated with the active event.
     * @param rule The rule linked to the active event.
     */
    public ActiveEvent(String message, Rule rule) {
        this.message = message;
        this.rule = rule;
    }
    
    /**
     * Retrieves the message associated with the active event.
     * @return The message of the active event.
     */
    @Override
    public String getMessage() {
        return this.message;
    }

    /**
     * Sets the message for the active event.
     * @param message The message to be set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Retrieves the rule linked to the active event.
     * @return The rule associated with the active event.
     */
    public Rule getRule() {
        return rule;
    }

    /**
     * Sets the rule for the active event.
     * @param rule The rule to be set.
     */
    public void setRule(Rule rule) {
        this.rule = rule;
    }
}