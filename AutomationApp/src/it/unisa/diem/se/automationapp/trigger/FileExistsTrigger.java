/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author chiar
 */
public class FileExistsTrigger implements TriggerInterface{
    private String fileName;
    private String folderPath;

    public FileExistsTrigger() {
    }

    public FileExistsTrigger(String fileName, String folderPath) {
        this.fileName = fileName;
        this.folderPath = folderPath;
    }

    public String getFilePath() {
        return fileName;
    }

    public void setFilePath(String fileName) {
        this.fileName = fileName;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }
    
    @JsonIgnore
    @Override
    public boolean isTriggered() {
        if(folderPath == null || folderPath.trim().isEmpty() || fileName == null || fileName.trim().isEmpty()){
            return false;
        }
        
        Path filePath = Paths.get(folderPath, fileName);
        return Files.exists(filePath);
    }

    @Override
    public String toString() {
        return "File name to check existence: " + fileName + " in the directory: '" + folderPath + "'";
    }
}
