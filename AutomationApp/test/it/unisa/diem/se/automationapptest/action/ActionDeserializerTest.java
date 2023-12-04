/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.action;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.unisa.diem.se.automationapp.jsonUtility.ActionDeserializer;
import it.unisa.diem.se.automationapp.action.ActionInterface;
import it.unisa.diem.se.automationapp.action.MessageAction;
import it.unisa.diem.se.automationapp.action.AudioAction;
import org.junit.Test;
import static org.junit.Assert.*;

public class ActionDeserializerTest {

    @Test
    public void testDeserializeMessageAction() {
        String json = "{\"type\":\"MESSAGEACTION\",\"attributes\":{\"message\":\"Test Message\"}}";
        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        ActionDeserializer deserializer = new ActionDeserializer();
        ActionInterface action = deserializer.deserialize(jsonObject, ActionInterface.class, null);

        assertNotNull(action);
        assertTrue(action instanceof MessageAction);
        assertEquals("MESSAGEACTION", action.getType());
        assertEquals("Test Message", ((MessageAction)action).getMessage());
    }

    @Test
    public void testDeserializeAudioAction() {
        String json = "{\"type\":\"AUDIOACTION\",\"attributes\":{\"filePath\":\"/path/to/audio/file\"}}";
        JsonElement jsonElement = new JsonParser().parse(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        ActionDeserializer deserializer = new ActionDeserializer();
        ActionInterface action = deserializer.deserialize(jsonObject, ActionInterface.class, null);

        assertNotNull(action);
        assertTrue(action instanceof AudioAction);
        assertEquals("AUDIOACTION", action.getType());
        assertEquals("/path/to/audio/file", ((AudioAction)action).getFilePath());
    }
}


