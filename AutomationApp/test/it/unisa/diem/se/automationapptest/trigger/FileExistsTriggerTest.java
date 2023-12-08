/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.trigger;

import it.unisa.diem.se.automationapp.trigger.FileExistsTrigger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;

public class FileExistsTriggerTest {

    private FileExistsTrigger fileExistsTrigger;
    private String testFileName;
    private Path testFilePath;

    @Before
    public void setUp() throws IOException {
        // Set up a test file
        String testDirectory = System.getProperty("java.io.tmpdir");
        testFileName = "testFileExists.txt";
        testFilePath = Paths.get(testDirectory, testFileName);
        Files.createFile(testFilePath);

        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("fileName", testFileName);
        triggerData.put("fileDirectory", testDirectory);

        fileExistsTrigger = new FileExistsTrigger(triggerData);
    }

    @Test
    public void testIsTriggeredForExistingFile() {
        assertTrue("The trigger should be activated for an existing file", fileExistsTrigger.isTriggered());
    }

    @Test
    public void testIsTriggeredForNonExistingFile() {
        fileExistsTrigger.setFilePath("nonExistentFile.txt");
        assertFalse("The trigger should not be activated for a non-existent file", fileExistsTrigger.isTriggered());
    }

    @Test
    public void testSettersAndGetters() {
        String newFileName = "newTestFile.txt";
        String newDirectory = "newTestDirectory";
        fileExistsTrigger.setFilePath(newFileName);
        fileExistsTrigger.setDirectory(newDirectory);

        assertEquals("The file name should be updated", newFileName, fileExistsTrigger.getFilePath());
        assertEquals("The directory should be updated", newDirectory, fileExistsTrigger.getDirectory());
    }

    @After
    public void tearDown() throws IOException {
        // Clean up the test file
        Files.deleteIfExists(testFilePath);
    }
}

