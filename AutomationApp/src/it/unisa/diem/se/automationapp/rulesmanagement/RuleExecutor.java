/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import it.unisa.diem.se.automationapp.action.AudioAction;
import it.unisa.diem.se.automationapp.action.exception.AudioExecutionException;
import it.unisa.diem.se.automationapp.event.ActiveEvent;
import it.unisa.diem.se.automationapp.event.AudioEvent;
import it.unisa.diem.se.automationapp.event.AudioEventType;
import it.unisa.diem.se.automationapp.event.ErrorEvent;
import it.unisa.diem.se.automationapp.event.MessageEvent;
import it.unisa.diem.se.automationapp.eventsmanagement.EventBus;
import it.unisa.diem.se.automationapp.event.ErrorEventType;
import it.unisa.diem.se.automationapp.event.SceneEvent;
import it.unisa.diem.se.automationapp.event.SceneEventType;

/**
 *
 * @author chiar
 */
public class RuleExecutor implements Runnable {
    private EventBus eventBus;
    private RuleManager ruleManager;
    private boolean sceneBusy;
    
    public RuleExecutor() {
        this.eventBus = EventBus.getInstance();
        this.ruleManager = RuleManager.getInstance();
        this.sceneBusy = false;
        eventBus.subscribe(SceneEvent.class, this::onSceneEvent);
    }

    @Override
    public void run() {
        while(true) {
            Rule rule = ruleManager.queuePeek();
            if(rule != null && !(rule.getAction() instanceof AudioAction && this.sceneBusy)){
                rule = ruleManager.queuePoll();
                try {
                    rule.setWasExecuted(true);
                    rule.execute();
                    if(rule instanceof SuspendedRuleDecorator){
                        SuspendedRuleDecorator suspendedRule = (SuspendedRuleDecorator) rule;
                        suspendedRule.setLastExecutionTime(System.currentTimeMillis());
                    } else{
                        rule.setIsActive(false);
                        eventBus.publish(new ActiveEvent("Rule Deactivated", rule));
                    }
                    
                } catch (AudioExecutionException e) {
                    eventBus.publish(new AudioEvent("Audio stopped", AudioEventType.STOPPED));
                    eventBus.publish(new ActiveEvent("Rule Deactivated", rule));
                    eventBus.publish(new ErrorEvent("Error while executing rule " + rule.getName() + ": " + e.getMessage(), ErrorEventType.NORMAL));
                    rule.setIsActive(false);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    eventBus.publish(new ErrorEvent("Error in the rule execution thread, application will be terminated.", ErrorEventType.CRITICAL));
                } catch (Exception e) {
                    eventBus.publish(new ActiveEvent("Rule Deactivated", rule));
                    eventBus.publish(new ErrorEvent("Errore generico nell'esecuzione della regola " + rule.getName(), ErrorEventType.NORMAL));
                    rule.setIsActive(false);
                }
            }
            try{
                Thread.sleep(5000);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                eventBus.publish(new ErrorEvent("Error in the rule execution thread, application will be terminated.", ErrorEventType.CRITICAL));
            }
        }
    }
    
    private void onSceneEvent(SceneEvent event){
        if(event.getEventType() == SceneEventType.BUSY){
            this.sceneBusy = true;
        } else{
            this.sceneBusy = false;
        }
    }
}
