package it.unisa.diem.se.automationapptest.action;

import it.unisa.diem.se.automationapp.action.CopyFileAction;
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

public class CopyFileActionTest {

    private CopyFileAction copyFileAction;
    private Path sourceFilePath;
    private Path destinationFolderPath;

    @Before
    public void setUp() throws IOException {
        // Set up a test file and destination directory
        sourceFilePath = Paths.get(System.getProperty("java.io.tmpdir"), "testFile.txt");
        Files.createFile(sourceFilePath);

        destinationFolderPath = Paths.get(System.getProperty("java.io.tmpdir"), "testDest");
        Files.createDirectory(destinationFolderPath);

        Map<String, String> actionData = new HashMap<>();
        actionData.put("copySourcePath", sourceFilePath.toString());
        actionData.put("copyDestPath", destinationFolderPath.toString());

        copyFileAction = new CopyFileAction(actionData);
    }

    @Test
    public void testExecute() throws IOException {
        copyFileAction.execute();

        Path copiedFilePath = destinationFolderPath.resolve(sourceFilePath.getFileName());
        assertTrue("The file should be copied to the destination folder", Files.exists(copiedFilePath));
    }

    @Test
    public void testSettersAndGetters() {
        String newSourcePath = "newSourcePath";
        String newDestinationPath = "newDestinationPath";
        copyFileAction.setSourcePath(newSourcePath);
        copyFileAction.setDestinationPath(newDestinationPath);

        assertEquals("The source path should be updated", newSourcePath, copyFileAction.getSourcePath());
        assertEquals("The destination path should be updated", newDestinationPath, copyFileAction.getDestinationPath());
    }

    @Test
    public void testGetType() {
        assertEquals("The action type should be COPYFILEACTION", "COPYFILEACTION", copyFileAction.getType());
    }

    @After
    public void tearDown() throws IOException {
        // Clean up test file and directory
        Files.deleteIfExists(sourceFilePath);
        Files.deleteIfExists(destinationFolderPath.resolve(sourceFilePath.getFileName()));
        Files.deleteIfExists(destinationFolderPath);
    }
}
