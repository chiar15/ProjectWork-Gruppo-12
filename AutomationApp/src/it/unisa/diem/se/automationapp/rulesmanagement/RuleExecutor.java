/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import it.unisa.diem.se.automationapp.action.exception.AudioExecutionException;
import it.unisa.diem.se.automationapp.observer.MessageEvent;
import it.unisa.diem.se.automationapp.observer.EventBus;
import it.unisa.diem.se.automationapp.observer.MessageEventType;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

/**
 *
 * @author chiar
 */
public class RuleExecutor extends ScheduledService<Void> {
    private EventBus eventBus;
    private RuleManager ruleManager;
    
    public RuleExecutor() {
        this.eventBus = EventBus.getInstance();
        this.ruleManager = RuleManager.getInstance();
        
        setOnFailed(e->{
            eventBus.publish(new MessageEvent("Errore nel thread di esecuzione delle regole, l'applicazione verrà terminata", MessageEventType.CRITICAL_ERROR));
        });
        
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() { 
                Rule rule = ruleManager.queuePoll();
                if (rule != null && !isCancelled()) {
                    try {
                        rule.execute();
                        if(rule instanceof SuspendedRuleDecorator){
                            SuspendedRuleDecorator suspendedRule = (SuspendedRuleDecorator) rule;
                            suspendedRule.setLastExecutionTime(System.currentTimeMillis());
                        }
                    } catch (AudioExecutionException e) {
                        eventBus.publish(new MessageEvent("Errore nell'esecuzione della regola " + rule.getName() + ": " + e.getMessage(), MessageEventType.ERROR));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (Exception e) {
                        eventBus.publish(new MessageEvent("Errore generico nell'esecuzione della regola " + rule.getName() + ": " + e.getMessage(), MessageEventType.ERROR));
                    }
                }
                return null;
            }
        };
    }
}
