/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.jsonUtility;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;
import it.unisa.diem.se.automationapp.rulesmanagement.SuspendedRuleDecorator;
import java.lang.reflect.Type;

/**
 *
 * @author chiar
 */
public class RuleDeserializer implements JsonDeserializer<Rule>{

    @Override
    public Rule deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonObject jsonObject = je.getAsJsonObject();
        Rule rule = jdc.deserialize(je, Rule.class);
        
        if(jsonObject.get("isDecorator").getAsBoolean()){
            long suspensionPeriod = jsonObject.get("suspensionPeriod").getAsLong();
            long lastExecutionTime = jsonObject.get("lastExecutionTime").getAsLong();
            SuspendedRuleDecorator suspendedRule = new SuspendedRuleDecorator(rule, suspensionPeriod);
            suspendedRule.setLastExecutionTime(lastExecutionTime);
            return suspendedRule;
        } else {
            return rule;
        }
    }
    
}
