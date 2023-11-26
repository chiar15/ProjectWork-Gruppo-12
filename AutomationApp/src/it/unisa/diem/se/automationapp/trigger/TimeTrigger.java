/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger;

import java.time.LocalTime;
import java.util.Map;

public class TimeTrigger implements TriggerInterface{
    private LocalTime time;
    
    public TimeTrigger(Map<String, String> triggerData){
        this.time = LocalTime.parse(triggerData.get("time"));
    }

    public LocalTime getTime() {
        return time;
    }

    @Override
    public boolean isTriggered() {
        return (!(LocalTime.now().isBefore(time)));
    }
    
    
}
