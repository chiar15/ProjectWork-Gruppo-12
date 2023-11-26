/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.action;

import it.unisa.diem.se.automationapp.action.ActionFactory;
import it.unisa.diem.se.automationapp.action.ActionInterface;
import it.unisa.diem.se.automationapp.action.AudioAction;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class ActionFactoryTest {

    @Test
    public void testCreateAudioAction() {
        Map<String, String> actionData = new HashMap<>();
        actionData.put("filePath", "path/to/audio/file.wav");

        ActionInterface action = ActionFactory.createAction("ActionEnum.AUDIOACTION", actionData);

        assertNotNull(action);
        assertTrue(action instanceof AudioAction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateActionWithInvalidType() {
        Map<String, String> actionData = new HashMap<>();
        actionData.put("someKey", "someValue");

        ActionFactory.createAction("InvalidActionType", actionData);
    }
}
