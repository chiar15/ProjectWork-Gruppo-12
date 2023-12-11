/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger.triggercreation;

import it.unisa.diem.se.automationapp.trigger.DayOfMonthTrigger;
import it.unisa.diem.se.automationapp.trigger.TriggerInterface;
import java.util.Map;

/**
 * The DayOfMonthTriggerCreation class implements the TriggerCreationStrategy interface and is responsible for creating DayOfMonthTrigger instances.
 * It reads the day of the month information from the provided trigger data map and generates a DayOfMonthTrigger object based on that information.
 * This class is part of a strategy pattern for creating different types of triggers.
 * 
 */
public class DayOfMonthTriggerCreation implements TriggerCreationStrategy {

    /**
     * Default constructor for the DayOfMonthTriggerCreation class.
     */
    public DayOfMonthTriggerCreation() {
    }

    /**
     * Creates a DayOfMonthTrigger object based on the provided trigger data.
     *
     * @param triggerData A map containing data related to the trigger creation, where "dayOfMonth" key represents the day of the month information.
     * @return DayOfMonthTrigger An instance of the DayOfMonthTrigger class created with the parsed day of the month information.
     */
    @Override
    public TriggerInterface createTrigger(Map<String, String> triggerData) {
        int dayOfMonth = Integer.parseInt(triggerData.get("dayOfMonth"));
        
        return new DayOfMonthTrigger(dayOfMonth);
    }
}