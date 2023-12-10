/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger.triggercreation;

import it.unisa.diem.se.automationapp.trigger.DateTrigger;
import it.unisa.diem.se.automationapp.trigger.TriggerInterface;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import javax.swing.text.DateFormatter;

/**
 *
 * @author chiar
 */
public class DateTriggerCreation implements TriggerCreationStrategy{

    public DateTriggerCreation() {
    }

    @Override
    public TriggerInterface createTrigger(Map<String, String> triggerData) {
        String dateString = triggerData.get("date");
        
        LocalDate date = LocalDate.parse(dateString);
        
        return new DateTrigger(date);
    }
    
}
