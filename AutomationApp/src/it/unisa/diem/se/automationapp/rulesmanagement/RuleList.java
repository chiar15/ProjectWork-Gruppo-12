/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author chiar
 */
public class RuleList {
    private List<Rule> ruleList;

    public RuleList() {
        this.ruleList = new LinkedList<>();
    }

    public List<Rule> getRules() {
        return ruleList;
    }

    public void setRules(List<Rule> ruleList) {
        this.ruleList = ruleList;
    }
    
    
}
