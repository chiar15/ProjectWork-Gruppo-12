/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import it.unisa.diem.se.automationapp.action.ActionFactory;
import it.unisa.diem.se.automationapp.action.ActionInterface;
import it.unisa.diem.se.automationapp.observer.EventBus;
import it.unisa.diem.se.automationapp.observer.SaveEvent;
import it.unisa.diem.se.automationapp.observer.SaveEventType;
import it.unisa.diem.se.automationapp.trigger.TriggerFactory;
import it.unisa.diem.se.automationapp.trigger.TriggerInterface;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

public class RuleManager {
    private static RuleManager instance;
    private EventBus eventBus;
    private RulePersistence rulePersistence;
    private CopyOnWriteArrayList<Rule> ruleList;
    private ConcurrentLinkedQueue<Rule> executionQueue;

    private RuleManager() {
        this.ruleList = new CopyOnWriteArrayList();
        this.executionQueue = new ConcurrentLinkedQueue<>();
        this.rulePersistence = new RulePersistence();
        this.eventBus = EventBus.getInstance();
        eventBus.subscribe(SaveEvent.class, this::onSaveBeforeClosing);
    }

    public static RuleManager getInstance() {
        if (instance == null) {
            synchronized (RuleManager.class) {
                if (instance == null) {
                    instance = new RuleManager();
                }
            }
        }
        return instance;
    }
   
    public CopyOnWriteArrayList<Rule> getRuleList() {
        return ruleList;
    }
    
    public ConcurrentLinkedQueue<Rule> getExecutionQueue() {
        return executionQueue;
    }
    
    public void queueOffer(Rule rule){
        this.executionQueue.offer(rule);
    }
    
    public Rule queuePoll(){
        return this.executionQueue.poll();
    }
    
    public boolean queueContainsRule(Rule rule){
        return this.executionQueue.contains(rule);
    }
    
    public Rule createRule(String name, Map<String,String> triggerData, Map<String,String> actionData, long suspensionPeriod){
        TriggerInterface trigger = TriggerFactory.createTrigger(triggerData);
        ActionInterface action = ActionFactory.createAction(actionData);
        Rule rule = new Rule(name, trigger, action);
        if(suspensionPeriod != 0){
            rule = new SuspendedRuleDecorator(rule, suspensionPeriod);
        }
        
        ruleList.add(rule);
        return rule;
    }
    
    public void deleteRule(Rule selectedRule){
        ruleList.remove(selectedRule);
        executionQueue.remove(selectedRule);
    }
    
    public boolean doesRuleNameExist(String ruleName) {
        String trimmedRuleName = ruleName.replaceAll("\\s+", ""); // Rimuove tutti gli spazi

        return getRuleList().stream()
            .anyMatch(rule -> rule.getName().replaceAll("\\s+", "").equals(trimmedRuleName));
    }
    
    public void saveRulesToFile() throws IOException{
        rulePersistence.saveRulesToFile(ruleList);
    }
    
    public List<Rule> loadRulesFromFile(){
       List<Rule> list = rulePersistence.loadRulesFromFile();
       ruleList.clear();
       ruleList.addAll(list);
       return list;
    }
    
    public void onSaveBeforeClosing(SaveEvent event){
        if(event.getType() == SaveEventType.REQUEST){
            try{
                this.saveRulesToFile();
            } catch (IOException e){
                eventBus.publish(new SaveEvent("Error while saving rules, the application will be terminated anyway", SaveEventType.FAILURE));
            } finally {
                eventBus.publish(new SaveEvent("Saving completed", SaveEventType.SUCCESS));
            }
        }
    }
    
}
