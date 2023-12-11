/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger.triggercreation;

import it.unisa.diem.se.automationapp.trigger.TriggerInterface;
import java.util.Map;

/**
 * The TriggerCreationStrategy interface defines a strategy pattern for creating triggers.
 * Implementing classes should provide specific implementations for creating triggers based on trigger data.
 */
public interface TriggerCreationStrategy {
    
    /**
     * Creates a trigger based on the provided trigger data.
     * 
     * @param triggerData A map containing data related to the trigger creation.
     * @return TriggerInterface A specific trigger instance based on the provided trigger data.
     */
    public TriggerInterface createTrigger(Map<String, String> triggerData);
}