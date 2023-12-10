/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger.triggercreation;

import it.unisa.diem.se.automationapp.trigger.DayOfWeekTrigger;
import it.unisa.diem.se.automationapp.trigger.TriggerInterface;
import java.util.Map;

/**
 *
 * @author chiar
 */
public class DayOfWeekTriggerCreation implements TriggerCreationStrategy{

    @Override
    public TriggerInterface createTrigger(Map<String, String> triggerData) {
        String dayOfWeek = triggerData.get("dayOfWeek");
        
        return new DayOfWeekTrigger(dayOfWeek);
    }
    
}
