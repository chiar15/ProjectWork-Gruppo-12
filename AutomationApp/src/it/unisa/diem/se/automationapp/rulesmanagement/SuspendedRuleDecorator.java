/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 *
 * @author chiar
 */
@JsonTypeName("suspendedRule")
public class SuspendedRuleDecorator extends Rule {
    private long suspensionPeriod; // Periodo di sospensione in secondi
    private long lastExecutionTime; // Tempo dell'ultima esecuzione in millisecondi

    public SuspendedRuleDecorator(){
    }
    
    public SuspendedRuleDecorator(Rule rule, long suspensionPeriod) {
        super(rule.getName(), rule.getTrigger(), rule.getAction());
        this.suspensionPeriod = suspensionPeriod;
        this.lastExecutionTime = System.currentTimeMillis();
    }
    
    public void setSuspensionPeriod(long suspensionPeriod) {
        this.suspensionPeriod = suspensionPeriod;
    }

    public long getSuspensionPeriod() {
        return suspensionPeriod;
    }

    public long getLastExecutionTime() {
        return lastExecutionTime;
    }

    public void setLastExecutionTime(long lastExecutionTime) {
        this.lastExecutionTime = lastExecutionTime;
    }
    
    @JsonIgnore
    public String getSimpleSuspensionPeriod(){
        String simpleSuspension = "";
        
        long seconds = suspensionPeriod;
        
        long days =  seconds / 86400L;
        seconds %= 86400L;

        long hours = seconds / 3600L;
        seconds %= 3600L;

        long minutes = seconds / 60L;
        
        if(days != 0){
            simpleSuspension += days + "days ";
        }
        
        if(hours != 0){
            simpleSuspension += hours + "hours ";
        }
        
        if(minutes != 0){
            simpleSuspension += minutes + " minutes ";
        }
        
        return simpleSuspension;
    }
    
    @JsonIgnore
    public boolean isReadyToExecute(){
        long currentTime = System.currentTimeMillis();
        long elapsedSeconds = (currentTime - lastExecutionTime) / 1000;
        return elapsedSeconds >= suspensionPeriod;
    }
}
