/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger.triggercreation;

import it.unisa.diem.se.automationapp.trigger.DateTrigger;
import it.unisa.diem.se.automationapp.trigger.TriggerInterface;
import java.time.LocalDate;
import java.util.Map;

/**
 * The DateTriggerCreation class implements the TriggerCreationStrategy interface and is responsible for creating DateTrigger instances.
 * It reads the date information from the provided trigger data map and generates a DateTrigger object based on that information.
 * This class is part of a strategy pattern for creating different types of triggers.
 * 
 */
public class DateTriggerCreation implements TriggerCreationStrategy {

    /**
     * Default constructor for the DateTriggerCreation class.
     */
    public DateTriggerCreation() {
    }

    /**
     * Creates a DateTrigger object based on the provided trigger data.
     *
     * @param triggerData A map containing data related to the trigger creation, where "date" key represents the date information.
     * @return DateTrigger An instance of the DateTrigger class created with the parsed date information.
     */
    @Override
    public TriggerInterface createTrigger(Map<String, String> triggerData) {
        String dateString = triggerData.get("date");
        
        LocalDate date = LocalDate.parse(dateString);
        
        return new DateTrigger(date);
    }
}