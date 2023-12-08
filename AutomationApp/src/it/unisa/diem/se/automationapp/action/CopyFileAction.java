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
public class CopyFileAction implements ActionInterface{
    private String sourceFile;
    private String destinationFolder;

    public CopyFileAction() {
    }

    public CopyFileAction(Map<String, String> actionData) {
        this.sourceFile = actionData.get("copySourcePath");
        this.destinationFolder = actionData.get("copyDestPath");
    }

    public String getSourcePath() {
        return sourceFile;
    }

    public void setSourcePath(String sourcePath) {
        this.sourceFile = sourcePath;
    }

    public String getDestinationPath() {
        return destinationFolder;
    }

    public void setDestinationPath(String destinationPath) {
        this.destinationFolder = destinationPath;
    }
    
    @Override
    public void execute() throws IOException {
        Path sourcePath = Paths.get(sourceFile);
        String fileName = sourcePath.getFileName().toString();
        Path destinationPath = Paths.get(destinationFolder, fileName);
        Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
    }
}
