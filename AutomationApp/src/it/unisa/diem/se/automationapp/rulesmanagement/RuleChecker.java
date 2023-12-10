/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import it.unisa.diem.se.automationapp.event.ErrorEvent;
import it.unisa.diem.se.automationapp.eventsmanagement.EventBus;
import it.unisa.diem.se.automationapp.event.ErrorEventType;

/**
 * The RuleChecker class implements the Runnable interface and is responsible for checking rules based on specific conditions.
 * It continuously checks the rules, determines if they are triggered, and offers them to the execution queue if conditions are met.
 */
public class RuleChecker implements Runnable {
    private EventBus eventBus;
    private RuleManager ruleManager;

    /**
     * Default constructor for RuleChecker initializing the EventBus and RuleManager.
     */
    public RuleChecker() {
        this.eventBus = EventBus.getInstance();
        this.ruleManager = RuleManager.getInstance();
    }

    /**
     * The run method continuously checks the rules, their execution status, and triggers, then offers eligible rules to the execution queue.
     */
    @Override
    public void run() {
        while(true){
            try{
                for (Rule rule : ruleManager.getRuleList()) {
                    if(rule!=null){
                        if(rule instanceof SuspendedRuleDecorator && rule.getWasExecuted()){
                            SuspendedRuleDecorator suspendedRule = (SuspendedRuleDecorator) rule;
                            if(suspendedRule.isReadyToExecute()){
                                rule.setWasExecuted(false);
                            }
                        }
                        if (rule.isTriggered() && !rule.getWasExecuted() && !ruleManager.queueContainsRule(rule) && rule.getIsActive()) {
                            ruleManager.queueOffer(rule);
                        }
                    } 
                }
                Thread.sleep(6000);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
                eventBus.publish(new ErrorEvent("Error in the rule control thread, application will be terminated.", ErrorEventType.CRITICAL));   
            }catch (Exception e) {
                eventBus.publish(new ErrorEvent(e.getMessage(), ErrorEventType.NORMAL));
            }
        }
    }
}