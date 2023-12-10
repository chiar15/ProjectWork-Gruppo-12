package it.unisa.diem.se.automationapptest.trigger;

import it.unisa.diem.se.automationapp.trigger.FileExistsTrigger;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class FileExistsTriggerTest {

    private FileExistsTrigger fileExistsTrigger;
    private String testFileName;
    private String testDirectory;
    private Path testFilePath;

    @Before
    public void setUp() throws IOException {
        // Set up a test file
        testDirectory = System.getProperty("java.io.tmpdir");
        testFileName = "testFileExists.txt";
        testFilePath = Paths.get(testDirectory, testFileName);
        
        // Create the file if it doesn't exist
        if (!Files.exists(testFilePath)) {
            Files.createFile(testFilePath);
        }

        fileExistsTrigger = new FileExistsTrigger(testFileName, testDirectory);
    }

    @Test
    public void testIsTriggeredForExistingFile() {
        assertTrue("The trigger should be activated for an existing file", fileExistsTrigger.isTriggered());
    }

    @Test
    public void testIsTriggeredForNonExistingFile() {
        String nonExistentFileName = "nonExistentFile.txt";
        fileExistsTrigger.setFilePath(nonExistentFileName);
        assertFalse("The trigger should not be activated for a non-existent file", fileExistsTrigger.isTriggered());
    }

    @Test
    public void testTriggerWithNullAndEmptyValues() {
        fileExistsTrigger.setFilePath(null);
        fileExistsTrigger.setFolderPath(null);
        assertFalse("The trigger should not be activated with null values", fileExistsTrigger.isTriggered());

        fileExistsTrigger.setFilePath("");
        fileExistsTrigger.setFolderPath("");
        assertFalse("The trigger should not be activated with empty values", fileExistsTrigger.isTriggered());
    }

    @Test
    public void testTriggerWithInvalidFolderPath() {
        fileExistsTrigger.setFolderPath("invalidFolderPath");
        assertFalse("The trigger should not be activated with an invalid folder path", fileExistsTrigger.isTriggered());
    }

    @Test
    public void testTriggerWithFileRemoved() throws IOException {
        Files.deleteIfExists(testFilePath);
        assertFalse("The trigger should not be activated for a removed file", fileExistsTrigger.isTriggered());
    }
    @Test
    public void testSettersAndGetters() {
        String newFileName = "newTestFile.txt";
        String newFolderPath = "newTestDirectory";
        fileExistsTrigger.setFilePath(newFileName);
        fileExistsTrigger.setFolderPath(newFolderPath);

        assertEquals("The file name should be updated", newFileName, fileExistsTrigger.getFilePath());
        assertEquals("The directory should be updated", newFolderPath, fileExistsTrigger.getFolderPath());
    }

    @After
    public void tearDown() throws IOException {
        // Clean up the test file
        Files.deleteIfExists(testFilePath);
    }
}
