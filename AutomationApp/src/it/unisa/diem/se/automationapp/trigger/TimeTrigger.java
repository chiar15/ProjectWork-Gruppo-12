/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TimeTrigger implements TriggerInterface{
    @JsonFormat
      (shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime time;
    
    public TimeTrigger(){
    }
    
    public TimeTrigger(LocalTime time){
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time.truncatedTo(ChronoUnit.MINUTES);;
    }

    @JsonIgnore
    @Override
    public boolean isTriggered() {
        if(time == null){
            return false;
        }
        
        LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        return !(now.isBefore(time));
    }

    @Override
    public String toString() {
        return time.toString();
    }
}