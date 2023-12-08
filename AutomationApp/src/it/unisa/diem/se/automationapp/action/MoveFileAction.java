/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.action;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Map;

/**
 *
 * @author chiar
 */
public class MoveFileAction implements ActionInterface{
    private String sourceFile;
    private String destinationFolder;

    public MoveFileAction() {
    }

    public MoveFileAction(Map<String, String> actionData) {
        this.sourceFile = actionData.get("moveSourcePath");
        this.destinationFolder = actionData.get("moveDestPath");
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
    public void execute() throws IOException {
        Path sourcePath = Paths.get(sourceFile);
        String fileName = sourcePath.getFileName().toString();
        Path destinationPath = Paths.get(destinationFolder, fileName);
        Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Override
    public String toString() {
        return "Moving the file " + sourceFile + " to the folder at the path: " + destinationFolder;
    }
}
