/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import it.unisa.diem.se.automationapp.action.AudioAction;
import it.unisa.diem.se.automationapp.action.CopyFileAction;
import it.unisa.diem.se.automationapp.action.DeleteFileAction;
import it.unisa.diem.se.automationapp.action.MoveFileAction;
import it.unisa.diem.se.automationapp.action.StringAction;
import it.unisa.diem.se.automationapp.action.exception.AudioExecutionException;
import it.unisa.diem.se.automationapp.event.ActiveEvent;
import it.unisa.diem.se.automationapp.event.AudioEvent;
import it.unisa.diem.se.automationapp.event.AudioEventType;
import it.unisa.diem.se.automationapp.event.ErrorEvent;
import it.unisa.diem.se.automationapp.eventsmanagement.EventBus;
import it.unisa.diem.se.automationapp.event.ErrorEventType;
import it.unisa.diem.se.automationapp.event.FileEvent;
import it.unisa.diem.se.automationapp.event.SceneEvent;
import it.unisa.diem.se.automationapp.event.SceneEventType;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

/**
 * The RuleExecutor class handles the execution of queued rules.
 * It implements the Runnable interface to be executed as a separate thread.
 * 
 * @author chiar
 */
public class RuleExecutor implements Runnable {
    private EventBus eventBus;
    private RuleManager ruleManager;
    private boolean sceneBusy;
    
     /**
     * Default constructor for RuleExecutor.
     * Initializes the EventBus, RuleManager, and sceneBusy attribute.
     * Subscribes to SceneEvent for scene status updates.
     */
    public RuleExecutor() {
        this.eventBus = EventBus.getInstance();
        this.ruleManager = RuleManager.getInstance();
        this.sceneBusy = false;
        eventBus.subscribe(SceneEvent.class, this::onSceneEvent);
    }

    /**
     * The run method is the main execution logic of the RuleExecutor thread.
     * Executes queued rules based on certain conditions and handles errors.
     */
    @Override
    public void run() {
        while(true) {
            Rule rule = ruleManager.queuePeek();
            if(rule != null && !(rule.getAction() instanceof AudioAction && this.sceneBusy)){
                rule = ruleManager.queuePoll();
                try {
                    if(rule.getAction() instanceof AudioAction){
                        eventBus.publish(new AudioEvent("Playing selected audio", AudioEventType.STARTED));
                        rule.setWasExecuted(true);
                        rule.execute();
                        eventBus.publish(new AudioEvent("Audio stopped", AudioEventType.STOPPED));
                    } else {
                        rule.setWasExecuted(true);
                        rule.execute();
                    }
                    if(rule.getAction() instanceof StringAction || rule.getAction() instanceof CopyFileAction || rule.getAction() instanceof MoveFileAction || rule.getAction() instanceof DeleteFileAction){
                        eventBus.publish(new FileEvent(rule.getName() + " was successfully executed!"));
                    }
                    if(rule instanceof SuspendedRuleDecorator){
                        SuspendedRuleDecorator suspendedRule = (SuspendedRuleDecorator) rule;
                        suspendedRule.setLastExecutionTime(System.currentTimeMillis());
                    } else{
                        rule.setIsActive(false);
                        eventBus.publish(new ActiveEvent("Rule Deactivated", rule));
                    }
                    
                } catch (AudioExecutionException e) {
                    eventBus.publish(new AudioEvent("Audio stopped", AudioEventType.STOPPED));
                    rule.setIsActive(false);
                    eventBus.publish(new ActiveEvent("Rule Deactivated", rule));
                    eventBus.publish(new ErrorEvent("Error while executing rule " + rule.getName() + ": " + e.getMessage(), ErrorEventType.NORMAL));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    eventBus.publish(new ErrorEvent("Error in the rule execution thread, application will be terminated.", ErrorEventType.CRITICAL));
                } catch (NoSuchFileException e) {
                    rule.setIsActive(false);
                    eventBus.publish(new ActiveEvent("Rule Deactivated", rule));
                    eventBus.publish(new ErrorEvent("File error while executing rule: " + rule.getName() + ".The selected file was not found.", ErrorEventType.NORMAL));
                }catch (IOException e) {
                    rule.setIsActive(false);
                    eventBus.publish(new ActiveEvent("Rule Deactivated", rule));
                    eventBus.publish(new ErrorEvent("File error while executing rule: " + rule.getName() + ".There might some conflicts with system restrictions.", ErrorEventType.NORMAL));
                }catch (Exception e) {
                    rule.setIsActive(false);
                    eventBus.publish(new ActiveEvent("Rule Deactivated", rule));
                    eventBus.publish(new ErrorEvent("Generic error while executing rule: " + rule.getName(), ErrorEventType.NORMAL));
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
        this.sceneBusy = event.getEventType() == SceneEventType.BUSY;
    }
}
