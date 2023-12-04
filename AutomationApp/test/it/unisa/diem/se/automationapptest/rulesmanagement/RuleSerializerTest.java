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

import com.google.gson.JsonObject;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;
import it.unisa.diem.se.automationapp.jsonUtility.RuleSerializer;
import it.unisa.diem.se.automationapp.rulesmanagement.SuspendedRuleDecorator;
import it.unisa.diem.se.automationapp.trigger.TimeTrigger;
import it.unisa.diem.se.automationapp.action.AudioAction;
import com.google.gson.JsonSerializationContext;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class RuleSerializerTest {

    @Mock
    private JsonSerializationContext jsonSerializationContext;

    private RuleSerializer serializer;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        serializer = new RuleSerializer();

        // Configurazione mock di JsonSerializationContext
        when(jsonSerializationContext.serialize(any(Rule.class))).thenAnswer(invocation -> new JsonObject());
    }

    private Rule createTestRule() {
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("time", "12:00");
        TimeTrigger timeTrigger = new TimeTrigger(triggerData);

        Map<String, String> actionData = new HashMap<>();
        actionData.put("filePath", "/path/to/audio/file");
        AudioAction audioAction = new AudioAction(actionData);

        return new Rule("testRule", timeTrigger, audioAction);
    }

    @Test
    public void testSerializeRule() {
        Rule rule = createTestRule();
        JsonObject jsonObject = serializer.serialize(rule, Rule.class, jsonSerializationContext).getAsJsonObject();

        assertNotNull(jsonObject);
        verify(jsonSerializationContext).serialize(rule);
    }

    @Test
    public void testSerializeSuspendedRuleDecorator() {
        Rule rule = createTestRule();
        SuspendedRuleDecorator suspendedRule = new SuspendedRuleDecorator(rule, 60);

        JsonObject jsonObject = serializer.serialize(suspendedRule, Rule.class, jsonSerializationContext).getAsJsonObject();

        assertNotNull(jsonObject);
        assertTrue(jsonObject.has("suspensionPeriod"));
        assertTrue(jsonObject.has("lastExecutionTime"));
        assertTrue(jsonObject.has("isDecorator"));
        verify(jsonSerializationContext, atLeastOnce()).serialize(any(Rule.class));
    }
}


