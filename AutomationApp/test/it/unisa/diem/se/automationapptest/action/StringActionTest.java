/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.action;

import it.unisa.diem.se.automationapp.action.StringAction;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class StringActionTest {

    private StringAction stringAction;
    private String testFilePath;
    private String testString;

    @Before
    public void setUp() {
        testFilePath = "testFile.txt"; // Usa un file temporaneo per il test
        testString = "Test string";
        stringAction = new StringAction();
        stringAction.setFilePath(testFilePath);
        stringAction.setString(testString);
    }

    @Test
    public void testExecute() throws IOException {
        stringAction.execute();

        // Legge il contenuto del file
        StringBuilder fileContents = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(testFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContents.append(line);
            }
        }

        assertEquals("The content of the file should match the string", testString, fileContents.toString());
    }

    @Test
    public void testSettersAndGetters() {
        String newString = "New test string";
        String newFilePath = "newTestFile.txt";
        stringAction.setString(newString);
        stringAction.setFilePath(newFilePath);

        assertEquals("The string should be updated", newString, stringAction.getString());
        assertEquals("The file path should be updated", newFilePath, stringAction.getFilePath());
    }
}
