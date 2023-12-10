package it.unisa.diem.se.automationapptest.action;

import it.unisa.diem.se.automationapp.action.StringAction;
import it.unisa.diem.se.automationapp.action.exception.FileException;
import it.unisa.diem.se.automationapp.action.exception.InvalidInputException;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;
import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class StringActionTest {

    private StringAction stringAction;
    private Path testFilePath;
    private String testString;

    @Before
    public void setUp() throws IOException {
        testString = "Test string";
        testFilePath = Paths.get(System.getProperty("java.io.tmpdir"), "testFile.txt");

        if (!Files.exists(testFilePath)) {
            Files.createFile(testFilePath);
        }

        stringAction = new StringAction(testString, testFilePath.toString());
    }

    @Test
    public void testExecute() throws InvalidInputException, FileException, IOException {
        stringAction.execute();

        StringBuilder fileContents = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath.toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContents.append(line);
            }
        }

        assertTrue("Il contenuto del file dovrebbe contenere la stringa", fileContents.toString().contains(testString));
    }

    @Test(expected = InvalidInputException.class)
    public void testExecuteWithEmptyString() throws InvalidInputException, FileException {
        StringAction emptyStringAction = new StringAction("", testFilePath.toString());
        emptyStringAction.execute();
    }

    @Test(expected = InvalidInputException.class)
    public void testExecuteWithNullString() throws InvalidInputException, FileException {
        StringAction nullStringAction = new StringAction(null, testFilePath.toString());
        nullStringAction.execute();
    }

    @Test(expected = InvalidInputException.class)
    public void testExecuteWithEmptyFilePath() throws InvalidInputException, FileException {
        StringAction emptyPathAction = new StringAction(testString, "");
        emptyPathAction.execute();
    }

    @Test(expected = InvalidInputException.class)
    public void testExecuteWithNullFilePath() throws InvalidInputException, FileException {
        StringAction nullPathAction = new StringAction(testString, null);
        nullPathAction.execute();
    }

    @Test
    public void testSettersAndGetters() {
        String newString = "New test string";
        String newFilePath = testFilePath.getParent().resolve("newTestFile.txt").toString();
        stringAction.setString(newString);
        stringAction.setFilePath(newFilePath);

        assertEquals("La stringa dovrebbe essere aggiornata", newString, stringAction.getString());
        assertEquals("Il percorso del file dovrebbe essere aggiornato", newFilePath, stringAction.getFilePath());
    }

    @After
    public void tearDown() throws IOException {
        Files.deleteIfExists(testFilePath);
    }
}
