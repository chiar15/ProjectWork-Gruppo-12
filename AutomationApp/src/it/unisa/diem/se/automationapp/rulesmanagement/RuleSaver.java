/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import it.unisa.diem.se.automationapp.event.ErrorEvent;
import it.unisa.diem.se.automationapp.eventsmanagement.EventBus;
import it.unisa.diem.se.automationapp.event.ErrorEventType;
import java.io.IOException;

/**
 * The RuleSaver class implements the Runnable interface for automated rule saving functionality.
 * It periodically saves rules managed by RuleManager to a file using a separate thread.
 * In case of any I/O exceptions during saving, it communicates the errors through an event bus.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
    @JsonSubTypes.Type(value = SuspendedRuleDecorator.class, name = "suspended")
})
public class RuleSaver implements Runnable {
    private RuleManager ruleManager; // Manages rules in the application.
    private EventBus eventBus; // The event bus used for error communication.

    /**
     * Constructs a RuleSaver object initializing ruleManager and eventBus.
     * It obtains instances of RuleManager and EventBus for managing rules and error communication.
     */
    public RuleSaver() {
        this.ruleManager = RuleManager.getInstance(); // Retrieves the instance of RuleManager.
        this.eventBus = EventBus.getInstance(); // Retrieves the instance of EventBus.
    }
    
    /**
     * Executes the automated rule saving functionality in a separate thread.
     * Periodically saves rules managed by RuleManager to a file.
     * Communicates errors through an event bus in case of I/O exceptions or interruptions.
     */
    @Override
    public void run() {
        while(true) {
            try {
                ruleManager.saveRulesToFile(); // Saves rules to a file managed by RuleManager.
            } catch (IOException e) {
                // Publishes an error event through the event bus in case of an IO error during saving.
                eventBus.publish(new ErrorEvent("Error during autosave, automations were not saved. It is recommended to restart the application.", ErrorEventType.NORMAL));
            }
            
            try {
                Thread.sleep(20000); // Sleeps the thread for 20 seconds (autosave interval).
            } catch (InterruptedException e) {
                // Publishes an error event if thread sleep is interrupted.
                eventBus.publish(new ErrorEvent("Autosaving is no longer working, It is recommended to restart the application.", ErrorEventType.NORMAL));
            }
        }
    } 
}