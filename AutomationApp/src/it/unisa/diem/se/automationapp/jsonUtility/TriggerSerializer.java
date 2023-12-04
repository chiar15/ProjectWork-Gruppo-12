/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.jsonUtility;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import it.unisa.diem.se.automationapp.trigger.TriggerInterface;
import java.lang.reflect.Type;

/**
 *
 * @author chiar
 */
public class TriggerSerializer implements JsonSerializer<TriggerInterface>{

    @Override
    public JsonElement serialize(TriggerInterface t, Type type, JsonSerializationContext jsc) {
        JsonObject jsonObject = new JsonObject();
        
        jsonObject.addProperty("type", t.getType());
        
        JsonElement attributes = jsc.serialize(t);
        jsonObject.add("attributes", attributes);
        
        return jsonObject;
    }
    
}
