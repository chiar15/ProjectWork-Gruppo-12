package it.unisa.diem.se.automationapp.action;

import it.unisa.diem.se.automationapp.action.exception.FileException;
import it.unisa.diem.se.automationapp.action.exception.InvalidInputException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * The CopyFileAction class represents an action that copies a file to a specified destination folder.
 * It implements the ActionInterface and provides functionality to execute file copying.
 */
public class CopyFileAction implements ActionInterface {
    private String sourceFile;
    private String destinationFolder;

    /**
     * Default constructor for CopyFileAction.
     */
    public CopyFileAction() {
    }

    /**
     * Constructor for CopyFileAction that sets the source file and destination folder paths.
     *
     * @param sourceFile       The path of the source file to be copied.
     * @param destinationFolder The path of the destination folder where the file will be copied.
     */
    public CopyFileAction(String sourceFile, String destinationFolder) {
        this.sourceFile = sourceFile;
        this.destinationFolder = destinationFolder;
    }

    /**
     * Retrieves the source file path associated with this CopyFileAction.
     *
     * @return The source file path.
     */
    public String getSourceFile() {
        return sourceFile;
    }

    /**
     * Sets the source file path for copying.
     *
     * @param sourceFile The path of the source file.
     */
    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    /**
     * Retrieves the destination folder path associated with this CopyFileAction.
     *
     * @return The destination folder path.
     */
    public String getDestinationFolder() {
        return destinationFolder;
    }

    /**
     * Sets the destination folder path for copying the file.
     *
     * @param destinationFolder The path of the destination folder.
     */
    public void setDestinationFolder(String destinationFolder) {
        this.destinationFolder = destinationFolder;
    }

    /**
     * Executes the file copying action.
     *
     * @throws InvalidInputException If either the source file path or destination folder path is null or empty.
     * @throws FileException         If an error occurs during file copying, such as file not found or access issues.
     */
    @Override
    public void execute() throws InvalidInputException, FileException {
        if (sourceFile == null || sourceFile.trim().isEmpty()) {
            throw new InvalidInputException("The selected source path cannot be empty.");
        }

        if (destinationFolder == null || destinationFolder.trim().isEmpty()) {
            throw new InvalidInputException("The selected destination path cannot be empty.");
        }

        Path sourcePath = Paths.get(sourceFile);
        String fileName = sourcePath.getFileName().toString();
        Path destinationPath = Paths.get(destinationFolder, fileName);
        try {
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (NoSuchFileException e) {
            throw new FileException("One or both of the selected files were not found.");
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
        return "Copy the file " + sourceFile + " to the folder located at path: " + destinationFolder;
    }
}