/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.action;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import it.unisa.diem.se.automationapp.action.ActionSerializer;
import it.unisa.diem.se.automationapp.action.MessageAction;
import it.unisa.diem.se.automationapp.action.AudioAction;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class ActionSerializerTest {

    @Mock
    private JsonSerializationContext jsonSerializationContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSerializeMessageAction() {
        Map<String, String> messageData = new HashMap<>();
        messageData.put("message", "Test Message");
        MessageAction messageAction = new MessageAction(messageData);

        ActionSerializer serializer = new ActionSerializer();

        when(jsonSerializationContext.serialize(any(MessageAction.class)))
            .thenReturn(new JsonObject());

        JsonElement result = serializer.serialize(messageAction, MessageAction.class, jsonSerializationContext);
        assertNotNull(result);
        assertTrue(result.isJsonObject());

        JsonObject jsonObject = result.getAsJsonObject();
        assertEquals("MESSAGEACTION", jsonObject.get("type").getAsString());
    }

    @Test
    public void testSerializeAudioAction() {
        Map<String, String> audioData = new HashMap<>();
        audioData.put("filePath", "/path/to/audio/file");
        AudioAction audioAction = new AudioAction(audioData);

        ActionSerializer serializer = new ActionSerializer();

        when(jsonSerializationContext.serialize(any(AudioAction.class)))
            .thenReturn(new JsonObject());

        JsonElement result = serializer.serialize(audioAction, AudioAction.class, jsonSerializationContext);
        assertNotNull(result);
        assertTrue(result.isJsonObject());

        JsonObject jsonObject = result.getAsJsonObject();
        assertEquals("AUDIOACTION", jsonObject.get("type").getAsString());
        // Aggiungi altre asserzioni specifiche per AudioAction qui
    }
}
