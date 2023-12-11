package it.unisa.diem.se.automationapp.action;

import it.unisa.diem.se.automationapp.action.exception.FileException;
import it.unisa.diem.se.automationapp.action.exception.InvalidInputException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * The DeleteFileAction class represents an action that deletes a file.
 * It implements the ActionInterface and provides functionality to execute file deletion.
 */
public class DeleteFileAction implements ActionInterface {
    private String filePath;

    /**
     * Default constructor for DeleteFileAction.
     */
    public DeleteFileAction() {
    }

    /**
     * Constructor for DeleteFileAction that sets the file path to be deleted.
     *
     * @param filePath The path of the file to be deleted.
     */
    public DeleteFileAction(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Retrieves the file path associated with this DeleteFileAction.
     *
     * @return The file path to be deleted.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Sets the file path for deletion.
     *
     * @param filePath The path of the file to be deleted.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Executes the file deletion action.
     *
     * @throws InvalidInputException If the file path is null or empty.
     * @throws FileException         If an error occurs during file deletion, such as file not found or access issues.
     */
    @Override
    public void execute() throws InvalidInputException, FileException {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new InvalidInputException("The file path cannot be empty.");
        }

        Path deletePath = Paths.get(filePath);
        try {
            Files.delete(deletePath);
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
        return "Delete the file located in the folder at path: " + filePath;
    }
}