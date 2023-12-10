/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import it.unisa.diem.se.automationapp.action.ActionFactory;
import it.unisa.diem.se.automationapp.action.ActionInterface;
import it.unisa.diem.se.automationapp.trigger.TriggerFactory;
import it.unisa.diem.se.automationapp.trigger.TriggerInterface;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The RuleManager class is responsible for managing rules within the application.
 * It follows the Singleton design pattern for a single instance across the application.
 */
public class RuleManager {
    private static RuleManager instance;
    private RulePersistence rulePersistence;
    private CopyOnWriteArrayList<Rule> ruleList;
    private ConcurrentLinkedQueue<Rule> executionQueue;

    /**
     * Private constructor for RuleManager to enforce Singleton pattern.
     * Initializes the rule list, execution queue, and rule persistence.
     */
    private RuleManager() {
        this.ruleList = new CopyOnWriteArrayList();
        this.executionQueue = new ConcurrentLinkedQueue<>();
        this.rulePersistence = new RulePersistence();
    }

    /**
     * Retrieves the instance of RuleManager following the Singleton pattern.
     *
     * @return The instance of RuleManager.
     */
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
    
    public Rule queuePeek(){
        return this.executionQueue.peek();
    }
    
    /**
     * Creates a new rule based on provided data.
     *
     * @param name            The name of the rule.
     * @param triggerData     Data for trigger configuration.
     * @param actionData      Data for action configuration.
     * @param suspensionPeriod The suspension period for the rule (if any).
     * @return The created rule.
     */
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
        String trimmedRuleName = ruleName.replaceAll("\\s+", "");

        return getRuleList().stream()
            .anyMatch(rule -> rule.getName().replaceAll("\\s+", "").equals(trimmedRuleName));
    }
    
    /**
     * Saves rules to a file.
     *
     * @throws IOException If an error occurs while saving rules to the file.
     */
    public void saveRulesToFile() throws IOException{
        rulePersistence.saveRulesToFile(ruleList);
    }
    
     /**
     * Loads rules from a file and updates the rule list.
     *
     * @return The list of loaded rules.
     */
    public List<Rule> loadRulesFromFile(){
       List<Rule> list = rulePersistence.loadRulesFromFile();
       ruleList.clear();
       if(list != null){
           ruleList.addAll(list);
       }
       return list;
    }
    
    public void changeRuleState(Rule rule, boolean state){
        int index = ruleList.indexOf(rule, 0);
        ruleList.get(index).setIsActive(state);
        if(state && rule.getWasExecuted()){
            rule.setWasExecuted(false);
        }
        if(!state && queueContainsRule(rule)){
            executionQueue.remove(rule);
        }
    }
}
