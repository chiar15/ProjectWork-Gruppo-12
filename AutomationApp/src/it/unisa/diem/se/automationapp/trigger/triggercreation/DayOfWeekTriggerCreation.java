/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger.triggercreation;

import it.unisa.diem.se.automationapp.trigger.DayOfWeekTrigger;
import it.unisa.diem.se.automationapp.trigger.TriggerInterface;
import java.util.Map;

/**
 * The DayOfWeekTriggerCreation class implements the TriggerCreationStrategy interface and is responsible for creating DayOfWeekTrigger instances.
 * It reads the day of the week information from the provided trigger data map and generates a DayOfWeekTrigger object based on that information.
 * This class is part of a strategy pattern for creating different types of triggers.
 * 
 */
public class DayOfWeekTriggerCreation implements TriggerCreationStrategy{

    /**
     * Creates a DayOfWeekTrigger object based on the provided trigger data.
     *
     * @param triggerData A map containing data related to the trigger creation, where "dayOfWeek" key represents the day of the week information.
     * @return DayOfWeekTrigger An instance of the DayOfWeekTrigger class created with the provided day of the week information.
     */
    @Override
    public TriggerInterface createTrigger(Map<String, String> triggerData) {
        String dayOfWeek = triggerData.get("dayOfWeek");
        
        return new DayOfWeekTrigger(dayOfWeek);
    }
}