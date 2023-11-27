/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import it.unisa.diem.se.automationapp.action.exception.AudioExecutionException;
import it.unisa.diem.se.automationapp.observer.ErrorEvent;
import it.unisa.diem.se.automationapp.observer.EventBus;
import it.unisa.diem.se.automationapp.observer.EventType;

public class RuleEngine {
    private EventBus eventBus;

    public RuleEngine(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void executeRules() {
        RuleService ruleService = RuleService.getInstance();
        for (Rule rule : ruleService.getRuleList()) {
            try {
                if (rule.isTriggered()) {
                    rule.execute();
                    break;
                }
            } catch (AudioExecutionException e) {
                eventBus.publish(new ErrorEvent(rule.getName() + e.getMessage(), EventType.CRITICAL_ERROR));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                eventBus.publish(new ErrorEvent("Errore nel thread di controllo delle regole, l'applicazione verrà terminata", EventType.ERROR));
            } catch (Exception e) {
                eventBus.publish(new ErrorEvent(e.getMessage(), EventType.ERROR));
            }
        } 
    }
}
