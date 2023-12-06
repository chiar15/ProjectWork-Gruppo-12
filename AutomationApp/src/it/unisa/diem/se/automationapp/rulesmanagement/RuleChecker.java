/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import it.unisa.diem.se.automationapp.action.AudioAction;
import it.unisa.diem.se.automationapp.event.ErrorEvent;
import it.unisa.diem.se.automationapp.event.MessageEvent;
import it.unisa.diem.se.automationapp.observer.EventBus;
import it.unisa.diem.se.automationapp.event.ErrorEventType;

public class RuleChecker implements Runnable {
    private EventBus eventBus;
    private RuleManager ruleManager;

    public RuleChecker() {
        this.eventBus = EventBus.getInstance();
        this.ruleManager = RuleManager.getInstance();
    }

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
                        if (rule.isTriggered() && !rule.getWasExecuted() && !ruleManager.queueContainsRule(rule)) {
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


