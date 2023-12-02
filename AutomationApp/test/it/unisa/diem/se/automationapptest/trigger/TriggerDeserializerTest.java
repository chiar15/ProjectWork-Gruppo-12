/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.trigger;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.unisa.diem.se.automationapp.trigger.TimeTrigger;
import it.unisa.diem.se.automationapp.trigger.TriggerDeserializer;
import it.unisa.diem.se.automationapp.trigger.TriggerInterface;
import org.junit.Test;
import static org.junit.Assert.*;

public class TriggerDeserializerTest {

    @Test
    public void testDeserialize() {
        // Creazione del JSON di esempio
        String json = "{\"type\":\"TIMETRIGGER\",\"attributes\":{\"time\":\"12:00\"}}";
        
        // Parsing del JSON
        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        // Creazione del deserializzatore
        TriggerDeserializer deserializer = new TriggerDeserializer();

        // Deserializzazione
        TriggerInterface trigger = deserializer.deserialize(jsonObject, TriggerInterface.class, null);

        assertNotNull(trigger);
        assertTrue(trigger instanceof TimeTrigger);
        assertEquals("TIMETRIGGER", trigger.getType());
        assertEquals("12:00", ((TimeTrigger) trigger).getTime());
    }
}


