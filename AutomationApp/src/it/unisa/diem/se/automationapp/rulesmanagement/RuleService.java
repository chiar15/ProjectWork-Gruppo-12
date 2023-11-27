/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import it.unisa.diem.se.automationapp.action.ActionFactory;
import it.unisa.diem.se.automationapp.action.ActionInterface;
import it.unisa.diem.se.automationapp.trigger.TriggerFactory;
import it.unisa.diem.se.automationapp.trigger.TriggerInterface;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class RuleService {
    private static RuleService instance;
    private CopyOnWriteArrayList<Rule> ruleList;

    private RuleService() {
        this.ruleList = new CopyOnWriteArrayList();
    }

    public static RuleService getInstance() {
        if (instance == null) {
            synchronized (RuleService.class) {
                if (instance == null) {
                    instance = new RuleService();
                }
            }
        }
        return instance;
    }
    
    public Rule createRule(String name, Map<String,String> triggerData, Map<String,String> actionData){
        TriggerInterface trigger = TriggerFactory.createTrigger(triggerData);
        ActionInterface action = ActionFactory.createAction(actionData);
        Rule rule = new Rule(name, trigger, action);
        ruleList.add(rule);
        return rule;
    }
    
    public CopyOnWriteArrayList<Rule> getRuleList() {
        return ruleList;
    }
    
    
}
