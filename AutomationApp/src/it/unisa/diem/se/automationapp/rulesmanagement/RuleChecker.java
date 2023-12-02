/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import it.unisa.diem.se.automationapp.observer.MessageEvent;
import it.unisa.diem.se.automationapp.observer.EventBus;
import it.unisa.diem.se.automationapp.observer.MessageEventType;

public class RuleChecker implements Runnable {
    private EventBus eventBus;
    private RuleManager ruleManager;

    public RuleChecker() {
        this.eventBus = EventBus.getInstance();
        this.ruleManager = RuleManager.getInstance();;
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
                        if (rule.isTriggered() && !rule.getWasExecuted()) {
                            rule.setWasExecuted(true);
                            ruleManager.queueOffer(rule);
                            break;
                        }
                    } 
                }
                Thread.sleep(10000);
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
                eventBus.publish(new MessageEvent("Error in the rule control thread, application will be terminated.", MessageEventType.CRITICAL_ERROR));   
            }catch (Exception e) {
                eventBus.publish(new MessageEvent(e.getMessage(), MessageEventType.ERROR));
            }
        }
    }
}


