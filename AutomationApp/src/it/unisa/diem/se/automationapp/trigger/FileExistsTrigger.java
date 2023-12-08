/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 *
 * @author chiar
 */
public class FileExistsTrigger implements TriggerInterface{
    private String fileName;
    private String folder;

    public FileExistsTrigger() {
    }

    public FileExistsTrigger(Map<String, String> triggerData) {
        this.fileName = triggerData.get("fileName");
        this.folder = triggerData.get("fileDirectory");
    }

    public String getFilePath() {
        return fileName;
    }

    public void setFilePath(String fileName) {
        this.fileName = fileName;
    }

    public String getDirectory() {
        return folder;
    }

    public void setDirectory(String folder) {
        this.folder = folder;
    }
    
    @JsonIgnore
    @Override
    public boolean isTriggered() {
        Path filePath = Paths.get(folder, fileName);
        return Files.exists(filePath);
    }
    
}
