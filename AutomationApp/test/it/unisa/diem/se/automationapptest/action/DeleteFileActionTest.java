package it.unisa.diem.se.automationapptest.action;

import it.unisa.diem.se.automationapp.action.DeleteFileAction;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;

public class DeleteFileActionTest {

    private DeleteFileAction deleteFileAction;
    private Path testFilePath;

    @Before
    public void setUp() throws IOException {
        // Set up a test file to be deleted
        testFilePath = Paths.get(System.getProperty("java.io.tmpdir"), "testFileToDelete.txt");

        // Check if the file already exists and delete if it does
        if (Files.exists(testFilePath)) {
            Files.delete(testFilePath);
        }
        Files.createFile(testFilePath);

        Map<String, String> actionData = new HashMap<>();
        actionData.put("deleteFilePath", testFilePath.toString());

        deleteFileAction = new DeleteFileAction(actionData);
    }

    @Test
    public void testExecute() throws IOException {
        deleteFileAction.execute();

        assertFalse("The file should be deleted", Files.exists(testFilePath));
    }

    @Test(expected = NoSuchFileException.class)
    public void testExecuteWithNonExistentFile() throws IOException {
        deleteFileAction.setFilePath("nonExistentFile.txt");
        deleteFileAction.execute();
    }

    @Test
    public void testSettersAndGetters() {
        String newFilePath = "newFilePathForDelete";
        deleteFileAction.setFilePath(newFilePath);

        assertEquals("The file path should be updated", newFilePath, deleteFileAction.getFilePath());
    }
    
    @After
    public void tearDown() throws IOException {
        // Clean up the test file, in case it was not deleted during the test
        Files.deleteIfExists(testFilePath);
    }
}
