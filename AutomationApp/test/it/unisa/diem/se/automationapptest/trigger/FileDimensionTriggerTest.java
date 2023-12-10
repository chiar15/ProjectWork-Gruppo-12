package it.unisa.diem.se.automationapptest.trigger;

import it.unisa.diem.se.automationapp.trigger.FileDimensionTrigger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.After;

public class FileDimensionTriggerTest {

    private FileDimensionTrigger fileDimensionTrigger;
    private String testFilePath;
    private long testFileSize;

    @Before
    public void setUp() throws IOException {
        // Set up a test file
        testFilePath = Paths.get(System.getProperty("java.io.tmpdir"), "testFileDimension.txt").toString();
        Path file = Paths.get(testFilePath);
        Files.createFile(file);
        // Writing some content to increase file size
        Files.write(file, "Test content for file dimension".getBytes());
        testFileSize = Files.size(file);

        // Initialize FileDimensionTrigger with direct parameters
        fileDimensionTrigger = new FileDimensionTrigger(testFilePath, testFileSize - 5); // Set the dimension just below the actual file size
    }

    @Test
    public void testIsTriggeredWithLargerSize() {
        assertTrue("The trigger should be activated for a file larger than the specified size", fileDimensionTrigger.isTriggered());
    }

    @Test
    public void testIsTriggeredWithSmallerSize() {
        fileDimensionTrigger.setDimension(testFileSize + 5);
        assertFalse("The trigger should not be activated for a file smaller than the specified size", fileDimensionTrigger.isTriggered());
    }

    @Test
    public void testSettersAndGetters() {
        String newFilePath = "newTestFilePath.txt";
        long newDimension = 100;
        fileDimensionTrigger.setFilePath(newFilePath);
        fileDimensionTrigger.setDimension(newDimension);

        assertEquals("The file path should be updated", newFilePath, fileDimensionTrigger.getFilePath());
        assertEquals("The dimension should be updated", newDimension, fileDimensionTrigger.getDimension());
    }
    @Test
    public void testTriggerWithNonExistentFilePath() {
        fileDimensionTrigger.setFilePath("non_existent_file.txt");
        assertFalse("The trigger should not be activated for a non-existent file path", fileDimensionTrigger.isTriggered());
    }

    @Test
    public void testTriggerWithEmptyFile() throws IOException {
        Path emptyFile = Paths.get(System.getProperty("java.io.tmpdir"), "emptyTestFile.txt");
        Files.createFile(emptyFile);
        fileDimensionTrigger.setFilePath(emptyFile.toString());
        fileDimensionTrigger.setDimension(1);

        assertFalse("The trigger should not be activated for an empty file", fileDimensionTrigger.isTriggered());

        Files.deleteIfExists(emptyFile);
    }

    @Test
    public void testTriggerWithExactFileSize() {
        fileDimensionTrigger.setDimension(testFileSize);
        assertTrue("The trigger should be activated for a file with size equal to the limit", fileDimensionTrigger.isTriggered());
    }

    @Test
    public void testTriggerWithNegativeFileSize() {
        fileDimensionTrigger.setDimension(-1);
        assertFalse("The trigger should not be activated for negative file size limit", fileDimensionTrigger.isTriggered());
    }

    @After
    public void tearDown() throws IOException {
        // Clean up the test file
        Files.deleteIfExists(Paths.get(testFilePath));
    }
}
