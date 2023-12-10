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
import java.util.Map;

/**
 *
 * @author chiar
 */
public class DeleteFileAction implements ActionInterface{
    private String filePath;

    public DeleteFileAction() {
    }

    public DeleteFileAction(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    @Override
    public void execute() throws InvalidInputException, FileException {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new InvalidInputException("The file path cannot be empty.");
        }
        
        Path deletePath = Paths.get(filePath);
        try{
            Files.delete(deletePath);
        } catch(NoSuchFileException e){
            throw new FileException("The selected file was not found.");
        } catch(IOException e){
            throw new FileException("Cannot access the selected file. There might be some conflicts with system restrictions.");
        }
    }

    @Override
    public String toString() {
        return "Delete the file located in the folder at path: " + filePath;
    }
}
