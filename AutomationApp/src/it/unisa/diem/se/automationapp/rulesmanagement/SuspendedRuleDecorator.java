/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

/**
 *
 * @author chiar
 */
public class SuspendedRuleDecorator extends Rule {
    private long suspensionPeriod; // Periodo di sospensione in secondi
    private long lastExecutionTime; // Tempo dell'ultima esecuzione in millisecondi

    public SuspendedRuleDecorator(Rule rule, long suspensionPeriod) {
        super(rule.getName(), rule.getTrigger(), rule.getAction());
        this.suspensionPeriod = suspensionPeriod;
        this.lastExecutionTime = 0;
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
    
    
    public boolean isReadyToExecute(){
        long currentTime = System.currentTimeMillis();
        long elapsedSeconds = (currentTime - lastExecutionTime) / 1000;
        return elapsedSeconds >= suspensionPeriod;
    }
}
