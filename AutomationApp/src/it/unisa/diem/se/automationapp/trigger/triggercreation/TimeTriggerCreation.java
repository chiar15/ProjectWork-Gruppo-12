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
 *
 * @author chiar
 */
public class TimeTriggerCreation implements TriggerCreationStrategy{

    public TimeTriggerCreation() {
    }

    @Override
    public TriggerInterface createTrigger(Map<String, String> triggerData) {
        String timeString = triggerData.get("time");
        
        LocalTime time = LocalTime.parse(timeString);

        return new TimeTrigger(time.truncatedTo(ChronoUnit.MINUTES));
    }
    
}
