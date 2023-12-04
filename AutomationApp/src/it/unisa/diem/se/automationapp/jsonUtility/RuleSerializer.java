/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.jsonUtility;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;
import it.unisa.diem.se.automationapp.rulesmanagement.SuspendedRuleDecorator;
import java.lang.reflect.Type;

/**
 *
 * @author chiar
 */
public class RuleSerializer implements JsonSerializer<Rule>{

    @Override
    public JsonElement serialize(Rule t, Type type, JsonSerializationContext jsc) {
        JsonObject jsonObject = jsc.serialize(t).getAsJsonObject();
        
        if(t instanceof SuspendedRuleDecorator){
            SuspendedRuleDecorator rule = (SuspendedRuleDecorator) t;
            jsonObject.addProperty("suspensionPeriod", rule.getSuspensionPeriod());
            jsonObject.addProperty("lastExecutionTime", rule.getLastExecutionTime());
            jsonObject.addProperty("isDecorator", true);
        } else {
            jsonObject.addProperty("isDecorator", false);
        }
        
        return jsonObject;
    }
    
}
