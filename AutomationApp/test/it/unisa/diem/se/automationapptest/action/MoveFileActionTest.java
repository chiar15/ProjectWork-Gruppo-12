package it.unisa.diem.se.automationapptest.action;

import it.unisa.diem.se.automationapp.action.MoveFileAction;
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

public class MoveFileActionTest {

    private MoveFileAction moveFileAction;
    private Path sourceFilePath;
    private Path destinationFolderPath;

    @Before
    public void setUp() throws IOException {
        // Set up a test file and destination directory
        sourceFilePath = Paths.get(System.getProperty("java.io.tmpdir"), "testFileToMove.txt");
        // Check if the file already exists and delete if it does
        if (Files.exists(sourceFilePath)) {
            Files.delete(sourceFilePath);
        }
        Files.createFile(sourceFilePath);

        destinationFolderPath = Paths.get(System.getProperty("java.io.tmpdir"), "testDestForMove");
        if (!Files.exists(destinationFolderPath)) {
            Files.createDirectory(destinationFolderPath);
        }

        Map<String, String> actionData = new HashMap<>();
        actionData.put("moveSourcePath", sourceFilePath.toString());
        actionData.put("moveDestPath", destinationFolderPath.toString());

        moveFileAction = new MoveFileAction(actionData);
    }

    @Test
    public void testExecute() throws IOException {
        moveFileAction.execute();

        Path movedFilePath = destinationFolderPath.resolve(sourceFilePath.getFileName());
        assertTrue("The file should be moved to the destination folder", Files.exists(movedFilePath));
        assertFalse("The source file should no longer exist", Files.exists(sourceFilePath));
    }

    @Test
    public void testSettersAndGetters() {
        String newSourcePath = "newSourcePathForMove";
        String newDestinationPath = "newDestinationPathForMove";
        moveFileAction.setSourceFile(newSourcePath);
        moveFileAction.setDestinationFolder(newDestinationPath);

        assertEquals("The source file path should be updated", newSourcePath, moveFileAction.getSourceFile());
        assertEquals("The destination folder path should be updated", newDestinationPath, moveFileAction.getDestinationFolder());
    }

    @Test
    public void testGetType() {
        assertEquals("The action type should be MOVEFILEACTION", "MOVEFILEACTION", moveFileAction.getType());
    }

    @After
    public void tearDown() throws IOException {
        // Clean up destination directory and moved file
        Files.deleteIfExists(destinationFolderPath.resolve(sourceFilePath.getFileName()));
        Files.deleteIfExists(destinationFolderPath);
    }
}
