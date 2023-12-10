package it.unisa.diem.se.automationapptest.action;

import it.unisa.diem.se.automationapp.action.MoveFileAction;
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

public class MoveFileActionTest {

    private MoveFileAction moveFileAction;
    private Path sourceFilePath;
    private Path destinationFolderPath;

    @Before
    public void setUp() throws IOException {
        sourceFilePath = Paths.get(System.getProperty("java.io.tmpdir"), "testFileToMove.txt");
        if (Files.exists(sourceFilePath)) {
            Files.delete(sourceFilePath);
        }
        Files.createFile(sourceFilePath);

        destinationFolderPath = Paths.get(System.getProperty("java.io.tmpdir"), "testDestForMove");
        if (!Files.exists(destinationFolderPath)) {
            Files.createDirectory(destinationFolderPath);
        }

        moveFileAction = new MoveFileAction(sourceFilePath.toString(), destinationFolderPath.toString());
    }

    @Test
    public void testExecute() throws InvalidInputException, FileException {
        moveFileAction.execute();

        Path movedFilePath = destinationFolderPath.resolve(sourceFilePath.getFileName());
        assertTrue("The file should be moved to the destination folder", Files.exists(movedFilePath));
        assertFalse("The source file should no longer exist", Files.exists(sourceFilePath));
    }

    @Test(expected = InvalidInputException.class)
    public void testExecuteWithEmptySourcePath() throws InvalidInputException, FileException {
        moveFileAction.setSourceFile("");
        moveFileAction.setDestinationFolder(destinationFolderPath.toString());
        moveFileAction.execute();
    }

    @Test(expected = InvalidInputException.class)
    public void testExecuteWithEmptyDestinationPath() throws InvalidInputException, FileException {
        moveFileAction.setSourceFile(sourceFilePath.toString());
        moveFileAction.setDestinationFolder("");
        moveFileAction.execute();
    }
    
    @Test(expected = InvalidInputException.class)
    public void testExecuteWithNullSourcePath() throws InvalidInputException, FileException {
        moveFileAction.setSourceFile(null);
        moveFileAction.setDestinationFolder(destinationFolderPath.toString());
        moveFileAction.execute();
    }

    @Test(expected = InvalidInputException.class)
    public void testExecuteWithNullDestinationPath() throws InvalidInputException, FileException {
        moveFileAction.setSourceFile(sourceFilePath.toString());
        moveFileAction.setDestinationFolder(null);
        moveFileAction.execute();
    }

    @Test(expected = FileException.class)
    public void testExecuteWithNonExistentSourceFile() throws InvalidInputException, FileException {
        moveFileAction.setSourceFile("nonExistentFile.txt");
        moveFileAction.setDestinationFolder(destinationFolderPath.toString());
        moveFileAction.execute();
    }

    @Test(expected = FileException.class)
    public void testExecuteWithNonExistentDestinationFolder() throws InvalidInputException, FileException {
        moveFileAction.setSourceFile(sourceFilePath.toString());
        moveFileAction.setDestinationFolder(Paths.get(System.getProperty("java.io.tmpdir"), "nonExistentDestFolder").toString());
        moveFileAction.execute();
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

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(destinationFolderPath.resolve(sourceFilePath.getFileName()));
        Files.deleteIfExists(destinationFolderPath);
        Files.deleteIfExists(sourceFilePath); // In case the file wasn't moved
    }
}
