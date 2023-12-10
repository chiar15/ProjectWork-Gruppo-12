package it.unisa.diem.se.automationapptest.action;

import it.unisa.diem.se.automationapp.action.*;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class ActionFactoryTest {

    @Test
    public void testCreateAudioAction() {
        Map<String, String> actionData = new HashMap<>();
        actionData.put("type", ActionType.AUDIO.toString());
        actionData.put("filePath", "path/to/audio/file.wav");

        ActionInterface action = ActionFactory.createAction(actionData);

        assertNotNull(action);
        assertTrue(action instanceof AudioAction);
    }

    @Test
    public void testCreateMessageAction() {
        Map<String, String> actionData = new HashMap<>();
        actionData.put("type", ActionType.MESSAGE.toString());
        actionData.put("message", "Test message");

        ActionInterface action = ActionFactory.createAction(actionData);

        assertNotNull(action);
        assertTrue(action instanceof MessageAction);
    }

    @Test
    public void testCreateStringAction() {
        Map<String, String> actionData = new HashMap<>();
        actionData.put("type", ActionType.STRING.toString());
        actionData.put("string", "Test string");

        ActionInterface action = ActionFactory.createAction(actionData);

        assertNotNull(action);
        assertTrue(action instanceof StringAction);
    }

    @Test
    public void testCreateCopyFileAction() {
        Map<String, String> actionData = new HashMap<>();
        actionData.put("type", ActionType.COPYFILE.toString());
        // Include additional required data for CopyFileAction

        ActionInterface action = ActionFactory.createAction(actionData);

        assertNotNull(action);
        assertTrue(action instanceof CopyFileAction);
    }

    @Test
    public void testCreateMoveFileAction() {
        Map<String, String> actionData = new HashMap<>();
        actionData.put("type", ActionType.MOVEFILE.toString());
        // Include additional required data for MoveFileAction

        ActionInterface action = ActionFactory.createAction(actionData);

        assertNotNull(action);
        assertTrue(action instanceof MoveFileAction);
    }

    @Test
    public void testCreateDeleteFileAction() {
        Map<String, String> actionData = new HashMap<>();
        actionData.put("type", ActionType.DELETEFILE.toString());
        // Include additional required data for DeleteFileAction

        ActionInterface action = ActionFactory.createAction(actionData);

        assertNotNull(action);
        assertTrue(action instanceof DeleteFileAction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateActionWithInvalidType() {
        Map<String, String> actionData = new HashMap<>();
        actionData.put("type", "InvalidActionType");
        actionData.put("someKey", "someValue");

        ActionFactory.createAction(actionData);
    }
}
