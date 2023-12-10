package it.unisa.diem.se.automationapptest.action;

import it.unisa.diem.se.automationapp.action.AudioAction;
import it.unisa.diem.se.automationapp.action.exception.AudioExecutionException;
import it.unisa.diem.se.automationapp.action.exception.FileException;
import it.unisa.diem.se.automationapp.action.exception.InvalidInputException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AudioActionTest {

    private AudioAction audioAction;
    private String validFilePath;
    private String invalidFilePath;

    @Before
    public void setUp() {
        validFilePath = System.getProperty("user.dir") + "\\test\\it\\unisa\\diem\\se\\automationapptest\\action\\data\\song01.wav";
        invalidFilePath = System.getProperty("user.dir") + "\\test\\it\\unisa\\diem\\se\\automationapptest\\action\\data\\invalidfile.wav";

        // Utilizzo del costruttore aggiornato
        audioAction = new AudioAction(validFilePath);
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
    public void testExecuteWithValidFile() throws AudioExecutionException, InterruptedException, FileException, InvalidInputException {
        // Assumo che il file specificato esista ed è un file audio valido.
        audioAction.execute();
    }

    @Test(expected = InvalidInputException.class)
    public void testExecuteWithNullFilePath() throws AudioExecutionException, InterruptedException, InvalidInputException, FileException {
        audioAction.setFilePath(null);
        audioAction.execute();
        fail("Expected AudioExecutionException");
    }

    @Test(expected = FileException.class)
    public void testExecuteWithNonExistentFile() throws AudioExecutionException, InterruptedException, InvalidInputException, FileException {
        audioAction.setFilePath(invalidFilePath);
        audioAction.execute();
    }

    @Test(expected = AudioExecutionException.class)
    public void testExecuteWithUnsupportedAudioFormat() throws AudioExecutionException, InterruptedException, InvalidInputException, FileException {
        audioAction.setFilePath(System.getProperty("user.dir") + "\\test\\it\\unisa\\diem\\se\\automationapptest\\action\\data\\sample-6s.mp3");
        audioAction.execute();
    }
}
