/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

/**
 *
 * @author chiar
 */
public class DayOfWeekTrigger implements TriggerInterface{
    private String dayOfWeek;

    public DayOfWeekTrigger() {
    }

    public DayOfWeekTrigger(Map<String, String> triggerData) {
        this.dayOfWeek = triggerData.get("day_of_week");
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    
    
    @Override
    public boolean isTriggered() {
        DayOfWeek currentDay = LocalDate.now().getDayOfWeek();
        return currentDay.name().equalsIgnoreCase(dayOfWeek);
    }

    @Override
    public String getType() {
        return TriggerEnum.DAYOFWEEKTRIGGER.name();
    }
    
}
