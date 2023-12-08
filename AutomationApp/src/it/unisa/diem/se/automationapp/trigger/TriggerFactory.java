/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger;

import java.util.Map;


public class TriggerFactory {
    public static TriggerInterface createTrigger(Map<String, String> triggerData){
        String type = triggerData.get("type");
        
        if(type.equalsIgnoreCase(TimeTrigger.class.getSimpleName())){
            return new TimeTrigger(triggerData);
        } else if (type.equalsIgnoreCase(DayOfWeekTrigger.class.getSimpleName())) {
            return new DayOfWeekTrigger(triggerData);
        } else if (type.equalsIgnoreCase(DayOfMonthTrigger.class.getSimpleName())) {
            return new DayOfMonthTrigger(triggerData);
        } else if (type.equalsIgnoreCase(DateTrigger.class.getSimpleName())) {
            return new DateTrigger(triggerData);
        } else {
            throw new IllegalArgumentException("Invalid trigger type: " + type);
        }
    }
}
