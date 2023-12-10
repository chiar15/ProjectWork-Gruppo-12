/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.action;

import it.unisa.diem.se.automationapp.action.exception.FileException;
import it.unisa.diem.se.automationapp.action.exception.InvalidInputException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

/**
 *
 * @author chiar
 */
public class CopyFileAction implements ActionInterface{
    private String sourceFile;
    private String destinationFolder;

    public CopyFileAction() {
    }

    public CopyFileAction(String sourceFile, String destinationFolder) {
        this.sourceFile = sourceFile;
        this.destinationFolder = destinationFolder;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public String getDestinationFolder() {
        return destinationFolder;
    }

    public void setDestinationFolder(String destinationFolder) {
        this.destinationFolder = destinationFolder;
    }
    
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
        try{
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
        } catch(NoSuchFileException e){
            throw new FileException("One or both of the selected files were not found.");
        } catch(IOException e){
            throw new FileException("Cannot access the selected file. There might be some conflicts with system restrictions.");
        }
    }

    @Override
    public String toString() {
        return "Copy the file " + sourceFile + " to the folder located at path: " + destinationFolder;
    }
}
