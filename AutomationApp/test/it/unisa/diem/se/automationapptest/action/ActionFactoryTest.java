/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.action;

import it.unisa.diem.se.automationapp.action.ActionEnum;
import it.unisa.diem.se.automationapp.action.ActionFactory;
import it.unisa.diem.se.automationapp.action.ActionInterface;
import it.unisa.diem.se.automationapp.action.AudioAction;
import it.unisa.diem.se.automationapp.action.MessageAction;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class ActionFactoryTest {

    @Test
    public void testCreateAudioAction() {
        Map<String, String> actionData = new HashMap<>();
        actionData.put("type", ActionEnum.AUDIOACTION.name());
        actionData.put("filePath", "path/to/audio/file.wav");

        ActionInterface action = ActionFactory.createAction(actionData);

        assertNotNull(action);
        assertTrue(action instanceof AudioAction);
    }

    @Test
    public void testCreateMessageAction() {
        Map<String, String> actionData = new HashMap<>();
        actionData.put("type", ActionEnum.MESSAGEACTION.name());
        actionData.put("message", "Test message");

        ActionInterface action = ActionFactory.createAction(actionData);

        assertNotNull(action);
        assertTrue(action instanceof MessageAction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateActionWithInvalidType() {
        Map<String, String> actionData = new HashMap<>();
        actionData.put("type", "InvalidActionType");
        actionData.put("someKey", "someValue");

        ActionFactory.createAction(actionData);
    }
}

