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
    private CopyOnWriteArrayList<Rule> ruleList;

    public RuleService() {
        this.ruleList = new CopyOnWriteArrayList();
    }

    public void createRule(String name, Map<String,String> triggerData, Map<String,String> actionData){
        TriggerInterface trigger = TriggerFactory.createTrigger(triggerData);
        ActionInterface action = ActionFactory.createAction(actionData);
        ruleList.add(new Rule(name, trigger, action));
    }
    
    public CopyOnWriteArrayList<Rule> getRuleList() {
        return ruleList;
    }
    
    
}
