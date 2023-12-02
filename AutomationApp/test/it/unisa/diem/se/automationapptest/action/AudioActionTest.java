/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.action;

import it.unisa.diem.se.automationapp.action.AudioAction;
import it.unisa.diem.se.automationapp.action.exception.AudioExecutionException;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class AudioActionTest {
    
    @Test
    public void testConstructorAndFilePath() {
        Map<String, String> actionData = new HashMap<>();
        String projectDirectory = System.getProperty("user.dir");
        actionData.put("filePath", projectDirectory + "\\test\\it\\unisa\\diem\\se\\automationapptest\\action\\data\\song01.wav");

        AudioAction audioAction = new AudioAction(actionData);
        assertEquals(projectDirectory + "\\test\\it\\unisa\\diem\\se\\automationapptest\\action\\data\\song01.wav", audioAction.getFilePath());
    }

    @Test
    public void testExecuteWithValidFile() {
        // Questo test assumerà che il file specificato esista nel sistema.
        Map<String, String> actionData = new HashMap<>();
        String projectDirectory = System.getProperty("user.dir");
        actionData.put("filePath", projectDirectory + "\\test\\it\\unisa\\diem\\se\\automationapptest\\action\\data\\song01.wav");

        AudioAction audioAction = new AudioAction(actionData);

        try {
            audioAction.execute();
        } catch (Exception e) {
            fail("Execute method should not throw an exception with a valid file.");
        }
    }

    @Test(expected = AudioExecutionException.class)
    public void testExecuteWithInvalidFile() throws AudioExecutionException, InterruptedException{
        // Questo test fallirà se il file specificato non esiste.
        Map<String, String> actionData = new HashMap<>();
        String projectDirectory = System.getProperty("user.dir");
        actionData.put("filePath", projectDirectory + "\\test\\it\\unisa\\diem\\se\\automationapptest\\action\\data\\song02.wav");

        AudioAction audioAction = new AudioAction(actionData);
        // Si aspetta un'eccezione a causa del file non valido.
           audioAction.execute();
        
    }

        @Test
    public void testGetType() {
        Map<String, String> actionData = new HashMap<>();
        String projectDirectory = System.getProperty("user.dir");
        actionData.put("filePath", projectDirectory + "\\test\\it\\unisa\\diem\\se\\automationapptest\\action\\data\\song01.wav");

        AudioAction audioAction = new AudioAction(actionData);
        assertEquals("Il tipo di azione dovrebbe essere AUDIOACTION", "AUDIOACTION", audioAction.getType());
    }
}