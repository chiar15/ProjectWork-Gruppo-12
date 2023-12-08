/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger;

import java.time.LocalTime;
import java.util.Map;

public class TimeTrigger implements TriggerInterface{
    private String time;
    
    public TimeTrigger(){
    }
    
    public TimeTrigger(Map<String, String> triggerData){
        this.time = triggerData.get("time");
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    @Override
    public boolean isTriggered() {
        return (!(LocalTime.now().isBefore(LocalTime.parse(time))));
    }

    @Override
    public String toString() {
        return time;
    }

    @Override
    public String getType() {
        return TriggerEnum.TIMETRIGGER.name();
    }
}