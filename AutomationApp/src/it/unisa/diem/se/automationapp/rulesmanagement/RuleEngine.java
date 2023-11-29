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

public class RuleEngine extends ScheduledService<Void> {
    private EventBus eventBus;

    public RuleEngine(EventBus eventBus) {
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
                RuleManager ruleService = RuleManager.getInstance();
                for (Rule rule : ruleService.getRuleList()) {
                if (isCancelled()) {
                    break;
                }
                    try {
                        if (rule.isTriggered()) {
                            rule.execute();
                            break;
                        }
                    } catch (AudioExecutionException e) {
                        eventBus.publish(new ErrorEvent(rule.getName() + ": "+ e.getMessage(), EventType.ERROR));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (Exception e) {
                        eventBus.publish(new ErrorEvent(e.getMessage(), EventType.ERROR));
                    }
                }
                return null;
            }
        };
    }
}
