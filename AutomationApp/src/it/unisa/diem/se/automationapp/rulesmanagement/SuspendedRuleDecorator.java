/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * The SuspendedRuleDecorator class represents a decorator for Rule objects with suspension functionality.
 * It extends the Rule class and adds suspension-related attributes and methods.
 */
@JsonTypeName("suspendedRule")
public class SuspendedRuleDecorator extends Rule {
    private long suspensionPeriod; // The suspension period in seconds
    private long lastExecutionTime; // The time of the last execution in milliseconds

    /**
     * Default constructor for SuspendedRuleDecorator.
     */
    public SuspendedRuleDecorator() {
    }
    
    /**
     * Constructor for SuspendedRuleDecorator.
     * Initializes a SuspendedRuleDecorator object with suspension settings based on the provided rule.
     *
     * @param rule            The Rule object to be decorated.
     * @param suspensionPeriod The suspension period in seconds.
     */
    public SuspendedRuleDecorator(Rule rule, long suspensionPeriod) {
        super(rule.getName(), rule.getTrigger(), rule.getAction());
        this.suspensionPeriod = suspensionPeriod;
        this.lastExecutionTime = System.currentTimeMillis();
    }
    
    /**
     * Sets the suspension period for the rule.
     *
     * @param suspensionPeriod The suspension period in seconds to be set.
     */
    public void setSuspensionPeriod(long suspensionPeriod) {
        this.suspensionPeriod = suspensionPeriod;
    }

    /**
     * Retrieves the suspension period of the rule.
     *
     * @return The suspension period in seconds.
     */
    public long getSuspensionPeriod() {
        return suspensionPeriod;
    }

    /**
     * Retrieves the last execution time of the rule.
     *
     * @return The time of the last execution in milliseconds.
     */
    public long getLastExecutionTime() {
        return lastExecutionTime;
    }

    /**
     * Sets the last execution time of the rule.
     *
     * @param lastExecutionTime The time of the last execution in milliseconds to be set.
     */
    public void setLastExecutionTime(long lastExecutionTime) {
        this.lastExecutionTime = lastExecutionTime;
    }
    
    /**
     * Retrieves a simplified representation of the suspension period.
     * Returns a string containing the suspension period in days, hours, and minutes.
     *
     * @return A string representation of the suspension period.
     */
    @JsonIgnore
    public String getSimpleSuspensionPeriod() {
        String simpleSuspension = "";
        
        long seconds = suspensionPeriod;
        
        long days =  seconds / 86400L;
        seconds %= 86400L;

        long hours = seconds / 3600L;
        seconds %= 3600L;

        long minutes = seconds / 60L;
        
        if(days != 0){
            simpleSuspension += days + " days ";
        }
        
        if(hours != 0){
            simpleSuspension += hours + " hours ";
        }
        
        if(minutes != 0){
            simpleSuspension += minutes + " minutes ";
        }
        
        return simpleSuspension;
    }
    
    /**
     * Checks if the rule is ready to execute based on the suspension period.
     * Returns true if the elapsed time since the last execution is greater than or equal to the suspension period.
     *
     * @return True if the rule is ready to execute, otherwise false.
     */
    @JsonIgnore
    public boolean isReadyToExecute() {
        long currentTime = System.currentTimeMillis();
        long elapsedSeconds = (currentTime - lastExecutionTime) / 1000;
        return elapsedSeconds >= suspensionPeriod;
    }
}