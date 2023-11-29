/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleManager;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;
import it.unisa.diem.se.automationapp.action.exception.AudioExecutionException;
import it.unisa.diem.se.automationapp.observer.ErrorEvent;
import it.unisa.diem.se.automationapp.observer.EventBus;
import it.unisa.diem.se.automationapp.observer.EventType;

public class RuleChecker extends ScheduledService<Void> {
    private EventBus eventBus;

    public RuleChecker(EventBus eventBus) {
        this.eventBus = eventBus;
        
        setOnFailed(e->{
            eventBus.publish(new ErrorEvent("Errore nel thread di controllo delle regole, l'applicazione verrà terminata", EventType.CRITICAL_ERROR));
        });
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() {
                RuleManager ruleManager = RuleManager.getInstance();
                for (Rule rule : ruleManager.getRuleList()) {
                if (isCancelled()) {
                    break;
                }
                    try {
                        if (rule.isTriggered() && !rule.getWasExecuted()) {
                            rule.setWasExecuted(true);
                            ruleManager.queueOffer(rule);
                            break;
                        }
                    } catch (Exception e) {
                        eventBus.publish(new ErrorEvent(e.getMessage(), EventType.ERROR));
                    }
                }
                return null;
            }
        };
    }
}
