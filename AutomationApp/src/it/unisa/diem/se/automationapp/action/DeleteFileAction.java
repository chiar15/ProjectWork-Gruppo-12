/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.action;

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

    public DeleteFileAction(Map<String, String> actionData) {
        this.filePath = actionData.get("deleteFilePath");
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    @Override
    public void execute() throws NoSuchFileException, IOException {
        Path deletePath = Paths.get(filePath);
        Files.delete(deletePath);
    }

    @Override
    public String toString() {
        return "Delete the file located in the folder at path: " + filePath;
    }
}
