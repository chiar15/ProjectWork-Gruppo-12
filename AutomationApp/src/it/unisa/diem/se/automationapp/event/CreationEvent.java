/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.event;

import com.fasterxml.jackson.annotation.JsonTypeName;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;

/**
 * The CreationEvent class represents an event indicating the creation of a rule within the application.
 * It implements the EventInterface.
 */
@JsonTypeName("creation")
public class CreationEvent implements EventInterface {
    private String message;
    private Rule rule;

    /**
     * Default constructor for the CreationEvent class.
     */
    public CreationEvent() {
    }

    /**
     * Constructs a CreationEvent object with a message and a rule.
     * @param message The message associated with the creation event.
     * @param rule The rule associated with the creation event.
     */
    public CreationEvent(String message, Rule rule) {
        this.message = message;
        this.rule = rule;
    }
    
    /**
     * Retrieves the message associated with the creation event.
     * @return The message of the creation event.
     */
    @Override
    public String getMessage() {
        return this.message;
    }

    /**
     * Sets the message for the creation event.
     * @param message The message to be set.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Retrieves the rule associated with the creation event.
     * @return The rule of the creation event.
     */
    public Rule getRule() {
        return rule;
    }

    /**
     * Sets the rule for the creation event.
     * @param rule The rule to be set.
     */
    public void setRule(Rule rule) {
        this.rule = rule;
    }
}