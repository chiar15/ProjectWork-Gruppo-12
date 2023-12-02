/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger;

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
public class TriggerDeserializer implements JsonDeserializer<TriggerInterface>{

    @Override
    public TriggerInterface deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonObject jsonObject = je.getAsJsonObject();
        String kind = jsonObject.get("type").getAsString();
        JsonObject attributes = jsonObject.getAsJsonObject("attributes");
        
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("type", kind);

        for(Map.Entry<String, JsonElement> entry : attributes.entrySet()){
            triggerData.put(entry.getKey(), entry.getValue().getAsString());
        }
        
        return TriggerFactory.createTrigger(triggerData);
    }
    
}
