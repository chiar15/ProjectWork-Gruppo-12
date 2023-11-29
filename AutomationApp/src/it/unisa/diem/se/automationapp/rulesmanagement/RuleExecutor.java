/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import it.unisa.diem.se.automationapp.action.exception.AudioExecutionException;
import it.unisa.diem.se.automationapp.observer.ErrorEvent;
import it.unisa.diem.se.automationapp.observer.EventBus;
import it.unisa.diem.se.automationapp.observer.EventType;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

/**
 *
 * @author chiar
 */
public class RuleExecutor extends ScheduledService<Void> {
    private EventBus eventBus;
    
    public RuleExecutor(EventBus eventBus) {
        this.eventBus = eventBus;
        
        setOnFailed(e->{
            eventBus.publish(new ErrorEvent("Errore nel thread di esecuzione delle regole, l'applicazione verrà terminata", EventType.CRITICAL_ERROR));
        });
        
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() {
                RuleManager ruleManager = RuleManager.getInstance();
                Rule rule = ruleManager.queuePoll();
                if (rule != null && !isCancelled()) {
                    try {
                        rule.execute();
                    } catch (AudioExecutionException e) {
                        eventBus.publish(new ErrorEvent("Errore nell'esecuzione della regola " + rule.getName() + ": " + e.getMessage(), EventType.ERROR));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (Exception e) {
                        eventBus.publish(new ErrorEvent("Errore generico nell'esecuzione della regola " + rule.getName() + ": " + e.getMessage(), EventType.ERROR));
                    }
                }
                return null;
            }
        };
    }
}
