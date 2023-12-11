package it.unisa.diem.se.automationapp.action;

import it.unisa.diem.se.automationapp.action.exception.FileException;
import it.unisa.diem.se.automationapp.action.exception.InvalidInputException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.NoSuchFileException;

/**
 * The StringAction class represents an action that appends a string to a specified text file.
 * It implements the ActionInterface and provides functionality to execute the string appending action.
 */
public class StringAction implements ActionInterface {
    private String string;
    private String filePath;

    /**
     * Default constructor for StringAction.
     */
    public StringAction() {
    }

    /**
     * Constructor for StringAction that sets the string content and file path.
     *
     * @param string   The string content to be appended.
     * @param filePath The path of the text file where the string will be appended.
     */
    public StringAction(String string, String filePath) {
        this.string = string;
        this.filePath = filePath;
    }

    /**
     * Retrieves the string content associated with this StringAction.
     *
     * @return The string content.
     */
    public String getString() {
        return string;
    }

    /**
     * Sets the string content to be appended.
     *
     * @param string The content of the string.
     */
    public void setString(String string) {
        this.string = string;
    }

    /**
     * Retrieves the file path associated with this StringAction.
     *
     * @return The file path.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the file path where the string will be appended.
     *
     * @param filePath The path of the text file.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Executes the string appending action to a text file.
     *
     * @throws InvalidInputException If the string content or file path is null or empty.
     * @throws FileException         If an error occurs during string appending to the file, such as file not found or access issues.
     */
    @Override
    public void execute() throws InvalidInputException, FileException {

        if (string == null || string.trim().isEmpty()) {
            throw new InvalidInputException("The string to write cannot be empty.");
        }

        if (filePath == null || filePath.trim().isEmpty()) {
            throw new InvalidInputException("The file path cannot be empty.");
        }

        File file = new File(filePath);

        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))) {
            pw.append(string + " ");
        } catch (NoSuchFileException e) {
            throw new FileException("The selected file was not found.");
        } catch (IOException e) {
            throw new FileException("Cannot access the selected file. There might be some conflicts with system restrictions.");
        }
    }

    /**
     * Provides a string representation of the action being performed.
     *
     * @return A string describing the action.
     */
    @Override
    public String toString() {
        return "Appending the string '" + string + "' to the end of the txt file at the path: " + filePath;
    }
}