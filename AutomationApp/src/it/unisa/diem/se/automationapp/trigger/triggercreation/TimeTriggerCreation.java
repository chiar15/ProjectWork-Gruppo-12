/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger.triggercreation;

import it.unisa.diem.se.automationapp.trigger.TimeTrigger;
import it.unisa.diem.se.automationapp.trigger.TriggerInterface;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * The TimeTriggerCreation class implements the TriggerCreationStrategy interface to create TimeTrigger instances.
 * It reads time information from the provided trigger data map and generates a TimeTrigger object based on that information.
 * This class is part of a strategy pattern for creating different types of triggers.
 * 
 */
public class TimeTriggerCreation implements TriggerCreationStrategy{

    /**
     * Default constructor for the TimeTriggerCreation class.
     */
    public TimeTriggerCreation() {
    }

    /**
     * Creates a TimeTrigger object based on the provided trigger data.
     *
     * @param triggerData A map containing data related to the trigger creation, where the "time" key represents the time information in HH:mm format.
     * @return TimeTrigger An instance of the TimeTrigger class created with the provided time information.
     */
    @Override
    public TriggerInterface createTrigger(Map<String, String> triggerData) {
        String timeString = triggerData.get("time");
        
        // Parse the time string into LocalTime
        LocalTime time = LocalTime.parse(timeString);

        return new TimeTrigger(time.truncatedTo(ChronoUnit.MINUTES));
    }
}
