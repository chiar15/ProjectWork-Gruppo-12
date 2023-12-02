/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import it.unisa.diem.se.automationapp.observer.EventBus;
import it.unisa.diem.se.automationapp.observer.MessageEvent;
import it.unisa.diem.se.automationapp.observer.MessageEventType;
import java.io.IOException;

/**
 *
 * @author chiar
 */
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
                eventBus.publish(new MessageEvent("Error during autosave, automations were not saved. It is recommended to restart the application.", MessageEventType.ERROR));
            }
            
            try{
                Thread.sleep(20000);
            } catch (InterruptedException e){
                eventBus.publish(new MessageEvent("Error in the rule saving thread, It is recommended to restart the application.", MessageEventType.ERROR));
            }
        }
    }
    
}
