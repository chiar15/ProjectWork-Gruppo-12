/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.unisa.diem.se.automationapp.event.ErrorEvent;
import it.unisa.diem.se.automationapp.eventsmanagement.EventBus;
import it.unisa.diem.se.automationapp.event.MessageEvent;
import it.unisa.diem.se.automationapp.event.ErrorEventType;
import java.io.IOException;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
    @JsonSubTypes.Type(value = SuspendedRuleDecorator.class, name = "suspended")
    // Altre sottoclassi o decorator
})

public class RuleSaver implements Runnable{
    private RuleManager ruleManager;
    private EventBus eventBus;

    public RuleSaver() {
        this.ruleManager = RuleManager.getInstance();
        this.eventBus = EventBus.getInstance();
    }
    
    @Override
    public void run() {
        while(true){
            try{
                ruleManager.saveRulesToFile();
            } catch (IOException e){
                eventBus.publish(new ErrorEvent("Error during autosave, automations were not saved. It is recommended to restart the application.", ErrorEventType.NORMAL));
            }
            
            try{
                Thread.sleep(20000);
            } catch (InterruptedException e){
                eventBus.publish(new ErrorEvent("Autosaving is no longer working, It is recommended to restart the application.", ErrorEventType.NORMAL));
            }
        }
    }
    
}
