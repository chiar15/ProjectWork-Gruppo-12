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
 * The MoveFileAction class represents an action that moves a file to a specified destination folder.
 * It implements the ActionInterface and provides functionality to execute file movement.
 */
public class MoveFileAction implements ActionInterface {
    private String sourceFile;
    private String destinationFolder;

    /**
     * Default constructor for MoveFileAction.
     */
    public MoveFileAction() {
    }

    /**
     * Constructor for MoveFileAction that sets the source file and destination folder paths.
     *
     * @param sourceFile       The path of the source file to be moved.
     * @param destinationFolder The path of the destination folder where the file will be moved.
     */
    public MoveFileAction(String sourceFile, String destinationFolder) {
        this.sourceFile = sourceFile;
        this.destinationFolder = destinationFolder;
    }

    /**
     * Retrieves the source file path associated with this MoveFileAction.
     *
     * @return The source file path.
     */
    public String getSourceFile() {
        return sourceFile;
    }

    /**
     * Sets the source file path for movement.
     *
     * @param sourceFile The path of the source file.
     */
    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    /**
     * Retrieves the destination folder path associated with this MoveFileAction.
     *
     * @return The destination folder path.
     */
    public String getDestinationFolder() {
        return destinationFolder;
    }

    /**
     * Sets the destination folder path for moving the file.
     *
     * @param destinationFolder The path of the destination folder.
     */
    public void setDestinationFolder(String destinationFolder) {
        this.destinationFolder = destinationFolder;
    }

    /**
     * Executes the file movement action.
     *
     * @throws InvalidInputException If either the source file path or destination folder path is null or empty.
     * @throws FileException         If an error occurs during file movement, such as file not found or access issues.
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
            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
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
        return "Moving the file " + sourceFile + " to the folder at the path: " + destinationFolder;
    }
}