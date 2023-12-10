/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author chiar
 */
public class DateTrigger implements TriggerInterface{
    @JsonFormat
      (shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;

    public DateTrigger() {
    }

    public DateTrigger(LocalDate date) { 
        this.date = date;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    
    @JsonIgnore
    @Override
    public boolean isTriggered() {
        if(date == null){
            return false;
        }
        
        LocalDate now = LocalDate.now(); 
        return now.isEqual(date); 
    }

    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dtf.format(date);
    } 
}
