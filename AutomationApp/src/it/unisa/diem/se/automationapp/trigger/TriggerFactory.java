/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger;

import java.util.Map;


public class TriggerFactory {
    public static TriggerInterface createTrigger(String type, Map<String, String> triggerData){
        switch(type){
            case "TimeTrigger":
                return new TimeTrigger(triggerData);
            default:
                throw new IllegalArgumentException("Invalid trigger type: " + type);
        }
    }
}
