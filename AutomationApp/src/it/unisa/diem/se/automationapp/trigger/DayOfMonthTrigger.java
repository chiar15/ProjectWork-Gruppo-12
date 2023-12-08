/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.util.Map;

/**
 *
 * @author chiar
 */
public class DayOfMonthTrigger implements TriggerInterface{
    private String dayOfMonth;

    public DayOfMonthTrigger() {
    }

    public DayOfMonthTrigger(Map<String, String> triggerData) {
        this.dayOfMonth = triggerData.get("dayOfMonth");
    }

    public String getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(String dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }
    
    @JsonIgnore
    @Override
    public boolean isTriggered() {
        int currentDay = LocalDate.now().getDayOfMonth();
        return Integer.toString(currentDay).equals(dayOfMonth);
    }

    @Override
    public String toString() {
        return dayOfMonth;
    }
}
