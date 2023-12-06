package it.unisa.diem.se.automationapptest.action;

import it.unisa.diem.se.automationapp.action.AudioAction;
import it.unisa.diem.se.automationapp.action.exception.AudioExecutionException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class AudioActionTest {

    private AudioAction audioAction;
    private String validFilePath;
    private String invalidFilePath;

    @Before
    public void setUp() {
        validFilePath = System.getProperty("user.dir") + "\\test\\it\\unisa\\diem\\se\\automationapptest\\action\\data\\song01.wav";
        invalidFilePath = System.getProperty("user.dir") + "\\test\\it\\unisa\\diem\\se\\automationapptest\\action\\data\\invalidfile.wav";

        Map<String, String> actionData = new HashMap<>();
        actionData.put("filePath", validFilePath);
        audioAction = new AudioAction(actionData);
    }

    @Test
    public void testConstructorAndFilePath() {
        assertEquals("The file path should be correctly set by the constructor", validFilePath, audioAction.getFilePath());
    }

    @Test
    public void testSetFilePath() {
        audioAction.setFilePath(invalidFilePath);
        assertEquals("The file path should be updated by the setter", invalidFilePath, audioAction.getFilePath());
    }

    @Test
    public void testExecuteWithValidFile() throws AudioExecutionException, InterruptedException {
        // Assume that the file specified exists and is a valid audio file.
        audioAction.execute();
    }

    @Test(expected = AudioExecutionException.class)
    public void testExecuteWithInvalidFile() throws AudioExecutionException, InterruptedException {
        audioAction.setFilePath(invalidFilePath);
        audioAction.execute();
    }

    @Test
    public void testGetType() {
        assertEquals("The action type should be AUDIOACTION", "AUDIOACTION", audioAction.getType());
    }
}
