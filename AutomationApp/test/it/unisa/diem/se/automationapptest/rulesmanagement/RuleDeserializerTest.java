/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.rulesmanagement;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonDeserializationContext;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;
import it.unisa.diem.se.automationapp.jsonUtility.RuleDeserializer;
import it.unisa.diem.se.automationapp.rulesmanagement.SuspendedRuleDecorator;

import static org.junit.Assert.*;

public class RuleDeserializerTest {

    @Mock
    private JsonDeserializationContext jsonDeserializationContext;

    private RuleDeserializer deserializer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        deserializer = new RuleDeserializer();

        // Configura il mock jsonDeserializationContext
        when(jsonDeserializationContext.deserialize(any(JsonElement.class), eq(Rule.class)))
            .thenAnswer(invocation -> new Rule("testRule", null, null));
    }

    @Test
    public void testDeserializeRule() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("isDecorator", false);

        Rule result = deserializer.deserialize(jsonObject, Rule.class, jsonDeserializationContext);

        assertNotNull(result);
        assertFalse(result instanceof SuspendedRuleDecorator);
        // Aggiungi ulteriori asserzioni per verificare le proprietà di Rule se necessario
    }

    @Test
    public void testDeserializeSuspendedRuleDecorator() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("isDecorator", true);
        jsonObject.addProperty("suspensionPeriod", 60L);
        jsonObject.addProperty("lastExecutionTime", System.currentTimeMillis());

        Rule result = deserializer.deserialize(jsonObject, Rule.class, jsonDeserializationContext);

        assertNotNull(result);
        assertTrue(result instanceof SuspendedRuleDecorator);
        SuspendedRuleDecorator decorator = (SuspendedRuleDecorator) result;
        assertEquals(60L, decorator.getSuspensionPeriod());
        // Aggiungi ulteriori asserzioni per verificare le proprietà di SuspendedRuleDecorator
    }
}

