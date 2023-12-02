/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.action;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author chiar
 */
public class ActionDeserializer implements JsonDeserializer<ActionInterface>{

    @Override
    public ActionInterface deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonObject jsonObject = je.getAsJsonObject();
        String kind = jsonObject.get("type").getAsString();
        JsonObject attributes = jsonObject.getAsJsonObject("attributes");
        
        Map<String, String> actionData = new HashMap<>();
        actionData.put("type", kind);
        for(Map.Entry<String, JsonElement> entry: attributes.entrySet()){
            actionData.put(entry.getKey(), entry.getValue().getAsString());
        }
        
        return ActionFactory.createAction(actionData);
    }
    
}
