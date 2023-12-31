/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import java.util.LinkedList;
import java.util.List;

/**
 * The RuleList class represents a list of Rule objects.
 * It encapsulates the functionality to manage and retrieve a list of rules.
 * This class is intended for supporting rule serialization and deserialization and nothing else.
 * 
 * @author chiar
 */
public class RuleList {
    private List<Rule> ruleList;

    /**
     * Default constructor for RuleList initializing an empty LinkedList of rules.
     */
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
