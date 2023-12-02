/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import it.unisa.diem.se.automationapp.action.exception.AudioExecutionException;
import it.unisa.diem.se.automationapp.observer.MessageEvent;
import it.unisa.diem.se.automationapp.observer.EventBus;
import it.unisa.diem.se.automationapp.observer.MessageEventType;

/**
 *
 * @author chiar
 */
public class RuleExecutor implements Runnable {
    private EventBus eventBus;
    private RuleManager ruleManager;
    
    public RuleExecutor() {
        this.eventBus = EventBus.getInstance();
        this.ruleManager = RuleManager.getInstance();
    }

    @Override
    public void run() {
        while(true) {
            Rule rule = ruleManager.queuePoll();
            try {
                if(rule!= null){
                    rule.execute();
                    if(rule instanceof SuspendedRuleDecorator){
                        SuspendedRuleDecorator suspendedRule = (SuspendedRuleDecorator) rule;
                        suspendedRule.setLastExecutionTime(System.currentTimeMillis());
                    }
                }
                Thread.sleep(5000);
            } catch (AudioExecutionException e) {
                eventBus.publish(new MessageEvent("Errore nell'esecuzione della regola " + rule.getName() + ": " + e.getMessage(), MessageEventType.ERROR));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                eventBus.publish(new MessageEvent("Error in the rule execution thread, application will be terminated.", MessageEventType.CRITICAL_ERROR));
            } catch (Exception e) {
                eventBus.publish(new MessageEvent("Errore generico nell'esecuzione della regola " + rule.getName() + ": " + e.getMessage(), MessageEventType.ERROR));
            }
        }
    }
}
