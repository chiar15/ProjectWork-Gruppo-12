/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 *
 * @author chiar
 */
public class DateTrigger implements TriggerInterface{
    private String date;

    public DateTrigger() {
    }

    public DateTrigger(Map<String, String> triggerData) {
        this.date = triggerData.get("date");
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    @Override
    public boolean isTriggered() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
        LocalDateTime now = LocalDateTime.now(); 
        return dtf.format(now).equalsIgnoreCase(date); 
    }

    @Override
    public String getType() {
        return TriggerEnum.DATETRIGGER.name();
    }
    
}
