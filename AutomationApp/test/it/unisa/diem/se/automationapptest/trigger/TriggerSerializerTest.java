/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.trigger;

import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonElement;
import it.unisa.diem.se.automationapp.trigger.TimeTrigger;
import it.unisa.diem.se.automationapp.trigger.TriggerSerializer;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Before;

public class TriggerSerializerTest {

    @Mock
    private JsonSerializationContext jsonSerializationContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSerialize() {
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("time", "12:00");
        TimeTrigger timeTrigger = new TimeTrigger(triggerData);

        TriggerSerializer serializer = new TriggerSerializer();

        // Mocking del comportamento di jsonSerializationContext
        when(jsonSerializationContext.serialize(timeTrigger)).thenReturn(new JsonObject());

        // Test effettivo
        JsonElement result = serializer.serialize(timeTrigger, TimeTrigger.class, jsonSerializationContext);
        assertNotNull(result);
        assertTrue(result.isJsonObject());

        JsonObject jsonObject = result.getAsJsonObject();
        assertEquals("TIMETRIGGER", jsonObject.get("type").getAsString());
        // Altre asserzioni possono essere aggiunte qui per verificare il contenuto di "attributes"
    }
}
