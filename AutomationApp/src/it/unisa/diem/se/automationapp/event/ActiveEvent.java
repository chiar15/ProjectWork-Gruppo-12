/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.event;

import com.fasterxml.jackson.annotation.JsonTypeName;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;

@JsonTypeName("active")
public class ActiveEvent implements EventInterface{
    private String message;
    private Rule rule;

    public ActiveEvent() {
    }

    public ActiveEvent(String message, Rule rule) {
        this.message = message;
        this.rule = rule;
    }
    
    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }
    
    
}
