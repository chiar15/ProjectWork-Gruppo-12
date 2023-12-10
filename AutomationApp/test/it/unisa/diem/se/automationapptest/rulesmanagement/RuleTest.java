/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.rulesmanagement;

import it.unisa.diem.se.automationapp.action.ActionFactory;
import it.unisa.diem.se.automationapp.action.ActionInterface;
import it.unisa.diem.se.automationapp.action.ActionType;
import it.unisa.diem.se.automationapp.action.AudioAction;
import it.unisa.diem.se.automationapp.action.exception.AudioExecutionException;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;
import it.unisa.diem.se.automationapp.trigger.TimeTrigger;
import it.unisa.diem.se.automationapp.trigger.TriggerFactory;
import it.unisa.diem.se.automationapp.trigger.TriggerInterface;
import it.unisa.diem.se.automationapp.trigger.TriggerType;
import java.time.LocalTime;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;
import org.junit.Before;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RuleTest {

    private Rule rule;
    private TriggerInterface trigger;
    private ActionInterface action;
    
    @Before
    public void setUp() {
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("type", TriggerType.TIME.toString());
        triggerData.put("time", LocalTime.now().minusMinutes(1).toString());
        trigger = TriggerFactory.createTrigger(triggerData);

        String projectDirectory = System.getProperty("user.dir");
        Map<String, String> actionData = new HashMap<>();
        actionData.put("type", ActionType.AUDIO.toString());
        actionData.put("filePath", projectDirectory + "\\test\\it\\unisa\\diem\\se\\automationapptest\\action\\data\\song01.wav");
        action = ActionFactory.createAction(actionData);

        rule = new Rule("TestRule", trigger, action);
    }
    
    @Test
    public void testRuleConstructorAndGetters() {
        assertEquals("TestRule", rule.getName());
        assertSame(trigger, rule.getTrigger());
        assertSame(action, rule.getAction());
        assertFalse("wasExecuted should be false initially", rule.getWasExecuted());
    }

    @Test
    public void testIsTriggered() {
        assertTrue("The rule should be triggered", rule.isTriggered());
    }

    @Test
    public void testSetWasExecuted() {
        rule.setWasExecuted(true);
        assertTrue("wasExecuted should be true after being set", rule.getWasExecuted());
    }
    
    @Test
    public void testExecute() {
        try {
            rule.execute();
        } catch (Exception e) {
            fail("No exception should be thrown during execution");
        }
    }

    @Test
    public void testSetters() {
        String newName = "UpdatedRule";
        
        // Dati validi per TimeTrigger
        Map<String, String> newTriggerData = new HashMap<>();
        newTriggerData.put("type", TriggerType.TIME.toString());
        newTriggerData.put("time", "12:00");
        TriggerInterface newTrigger = TriggerFactory.createTrigger(newTriggerData);

        // Dati validi per AudioAction
        Map<String, String> newActionData = new HashMap<>();
        newActionData.put("type", ActionType.AUDIO.toString());
        newActionData.put("filePath", "path/to/audio/file.wav");
        ActionInterface newAction = ActionFactory.createAction(newActionData);

        rule.setName(newName);
        rule.setTrigger(newTrigger);
        rule.setAction(newAction);

        assertEquals("The name should be updated", newName, rule.getName());
        assertSame("The trigger should be updated", newTrigger, rule.getTrigger());
        assertSame("The action should be updated", newAction, rule.getAction());
    }
    
    @Test(expected = Exception.class)
    public void testExecuteWithException() throws Exception {
        // Assumiamo che l'azione lanci un'eccezione
        ActionInterface failingAction = mock(ActionInterface.class);
        doThrow(new Exception("Test exception")).when(failingAction).execute();

        rule.setAction(failingAction);
        rule.execute();
    }
    
    @Test
    public void testRuleWithNullValues() {
        Rule nullRule = new Rule("NullRule", null, null);

        assertNull("The trigger should be null", nullRule.getTrigger());
        assertNull("The action should be null", nullRule.getAction());
        assertFalse("isTriggered should return false for null trigger", nullRule.isTriggered());
    }

    @Test
    public void testActivateDeactivateRule() {
        rule.setIsActive(false);
        assertFalse("The rule should not be active", rule.getIsActive());

        rule.setIsActive(true);
        assertTrue("The rule should be active again", rule.getIsActive());
    }

    @Test
    public void testIsTriggeredWithInactiveTrigger() {
        // Creare un mock per TriggerInterface che restituisce sempre false
        TriggerInterface mockTrigger = mock(TriggerInterface.class);
        when(mockTrigger.isTriggered()).thenReturn(false);

        rule.setTrigger(mockTrigger);
        assertFalse("isTriggered should return false if the trigger is not active", rule.isTriggered());
    }
}
