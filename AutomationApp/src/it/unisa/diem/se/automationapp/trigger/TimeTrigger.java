/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * The TimeTrigger class implements the TriggerInterface to define a trigger based on specific time.
 * It checks if the current time has reached or passed a specified time.
 * 
 */
public class TimeTrigger implements TriggerInterface{
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime time;
    
    /**
     * Default constructor for TimeTrigger.
     */
    public TimeTrigger(){
    }
    
    /**
     * Constructor for TimeTrigger with specified time.
     *
     * @param time The specific time set for the trigger.
     */
    public TimeTrigger(LocalTime time){
        this.time = time;
    }

    /**
     * Get the specific time set for the trigger.
     *
     * @return The specific time set for the trigger.
     */
    public LocalTime getTime() {
        return time;
    }

    /**
     * Set the specific time for the trigger.
     *
     * @param time The specific time to set for the trigger.
     */
    public void setTime(LocalTime time) {
        this.time = time.truncatedTo(ChronoUnit.MINUTES);
    }

    /**
     * Check if the trigger is activated based on the specified time.
     *
     * @return True if the current time has reached or passed the specified time, otherwise false.
     */
    @JsonIgnore
    @Override
    public boolean isTriggered() {
        if(time == null){
            return false;
        }
        
        LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        return !(now.isBefore(time));
    }

    /**
     * Get a string representation of the specified time for the trigger.
     *
     * @return A string representing the specific time set for the trigger.
     */
    @Override
    public String toString() {
        return time.toString();
    }
}