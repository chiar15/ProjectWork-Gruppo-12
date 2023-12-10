package it.unisa.diem.se.automationapptest.action;

import it.unisa.diem.se.automationapp.action.DeleteFileAction;
import it.unisa.diem.se.automationapp.action.exception.FileException;
import it.unisa.diem.se.automationapp.action.exception.InvalidInputException;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeleteFileActionTest {

    private DeleteFileAction deleteFileAction;
    private Path testFilePath;

    @Before
    public void setUp() throws IOException {
        testFilePath = Paths.get(System.getProperty("java.io.tmpdir"), "testFileToDelete.txt");

        if (Files.exists(testFilePath)) {
            Files.delete(testFilePath);
        }
        Files.createFile(testFilePath);

        deleteFileAction = new DeleteFileAction(testFilePath.toString());
    }

    @Test
    public void testExecute() throws InvalidInputException, FileException {
        deleteFileAction.execute();
        assertFalse("The file should be deleted", Files.exists(testFilePath));
    }

    @Test(expected = FileException.class)
    public void testExecuteWithNonExistentFile() throws InvalidInputException, FileException {
        deleteFileAction.setFilePath("nonExistentFile.txt");
        deleteFileAction.execute();
    }

    @Test(expected = InvalidInputException.class)
    public void testExecuteWithEmptyFilePath() throws InvalidInputException, FileException {
        deleteFileAction.setFilePath("");
        deleteFileAction.execute();
    }

    @Test(expected = InvalidInputException.class)
    public void testExecuteWithNullFilePath() throws InvalidInputException, FileException {
        deleteFileAction.setFilePath(null);
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
        Files.deleteIfExists(testFilePath);
    }
}
