package it.unisa.diem.se.automationapptest.action;

import it.unisa.diem.se.automationapp.action.CopyFileAction;
import it.unisa.diem.se.automationapp.action.exception.FileException;
import it.unisa.diem.se.automationapp.action.exception.InvalidInputException;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import java.io.IOException;
import java.nio.file.*;
import static org.junit.Assert.*;

public class CopyFileActionTest {

    private CopyFileAction copyFileAction;
    private Path sourceFilePath;
    private Path destinationFolderPath;

    @Before
    public void setUp() throws IOException {
        // Creazione di un file di test e di una directory di destinazione
        sourceFilePath = Files.createTempFile("testFile_", ".txt");
        destinationFolderPath = Files.createTempDirectory("testDest");
        copyFileAction = new CopyFileAction(sourceFilePath.toString(), destinationFolderPath.toString());
    }

    @Test
    public void testExecute() throws InvalidInputException, FileException {
        copyFileAction.execute();
        Path copiedFilePath = destinationFolderPath.resolve(sourceFilePath.getFileName());
        assertTrue("Il file dovrebbe essere copiato nella cartella di destinazione", Files.exists(copiedFilePath));
    }

    @Test(expected = FileException.class)
    public void testExecuteWithNonExistentSourceFile() throws InvalidInputException, FileException {
        copyFileAction.setSourceFile("non_existent_file.txt");
        copyFileAction.execute();
    }

    @Test(expected = FileException.class)
    public void testExecuteWithNonExistentDestinationFolder() throws InvalidInputException, FileException {
        copyFileAction.setDestinationFolder("non_existent_folder");
        copyFileAction.execute();
    }

    @Test(expected = InvalidInputException.class)
    public void testExecuteWithEmptySourcePath() throws InvalidInputException, FileException {
        copyFileAction.setSourceFile("");
        copyFileAction.execute();
    }

    @Test(expected = InvalidInputException.class)
    public void testExecuteWithEmptyDestinationPath() throws InvalidInputException, FileException {
        copyFileAction.setDestinationFolder("");
        copyFileAction.execute();
    }

    @Test(expected = InvalidInputException.class)
    public void testExecuteWithNullSourcePath() throws InvalidInputException, FileException {
        copyFileAction.setSourceFile(null);
        copyFileAction.execute();
    }

    @Test(expected = InvalidInputException.class)
    public void testExecuteWithNullDestinationPath() throws InvalidInputException, FileException {
        copyFileAction.setDestinationFolder(null);
        copyFileAction.execute();
    }
    
    @Test
    public void testSettersAndGetters() {
        String newSourcePath = "newSourcePath";
        String newDestinationPath = "newDestinationPath";
        copyFileAction.setSourceFile(newSourcePath);
        copyFileAction.setDestinationFolder(newDestinationPath);

        assertEquals("Il percorso sorgente dovrebbe essere aggiornato", newSourcePath, copyFileAction.getSourceFile());
        assertEquals("Il percorso di destinazione dovrebbe essere aggiornato", newDestinationPath, copyFileAction.getDestinationFolder());
    }

    @After
    public void tearDown() throws IOException {
        // Pulizia dei file e delle directory di test
        Files.deleteIfExists(sourceFilePath);
        Files.deleteIfExists(destinationFolderPath.resolve(sourceFilePath.getFileName()));
        Files.deleteIfExists(destinationFolderPath);
    }
}
