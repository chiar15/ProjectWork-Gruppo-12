/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import it.unisa.diem.se.automationapp.observer.ErrorEvent;
import it.unisa.diem.se.automationapp.observer.EventBus;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class RuleEngine {
    private RuleService ruleService;
    private EventBus eventBus;

    public RuleEngine(RuleService ruleService, EventBus eventBus) {
        this.ruleService = ruleService;
        this.eventBus = eventBus;
    }

    public void executeRules() {
        try {
            for (Rule rule : ruleService.getRuleList()) {
                if (rule.isTriggered()) {
                    rule.execute();
                }
             }
        } catch (UnsupportedAudioFileException e) {
            eventBus.publish(new ErrorEvent(e.getMessage(), false));
        } catch (IOException e) {
            eventBus.publish(new ErrorEvent(e.getMessage(), false));
        } catch (LineUnavailableException e) {
            eventBus.publish(new ErrorEvent(e.getMessage(), false));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            eventBus.publish(new ErrorEvent("Riproduzione audio interrotta.", true));
        } catch (Exception e) {
            eventBus.publish(new ErrorEvent(e.getMessage(), false));
        }
    }
}
