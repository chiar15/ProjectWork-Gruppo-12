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
 * The FileExistsTrigger class implements the TriggerInterface to define a trigger based on the existence of a file.
 * It checks whether a specified file exists in a given directory.
 * 
 */
public class FileExistsTrigger implements TriggerInterface {
    private String fileName;
    private String folderPath;

    /**
     * Default constructor for FileExistsTrigger.
     */
    public FileExistsTrigger() {
    }

    /**
     * Constructor for FileExistsTrigger with specified file name and folder path.
     *
     * @param fileName    The name of the file to check for existence.
     * @param folderPath  The directory path where the file is expected to exist.
     */
    public FileExistsTrigger(String fileName, String folderPath) {
        this.fileName = fileName;
        this.folderPath = folderPath;
    }

    /**
     * Get the file name associated with the FileExistsTrigger.
     *
     * @return The name of the file to check existence.
     */
    public String getFilePath() {
        return fileName;
    }

    /**
     * Set the file name associated with the FileExistsTrigger.
     *
     * @param fileName The name of the file to check existence.
     */
    public void setFilePath(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Get the folder path associated with the FileExistsTrigger.
     *
     * @return The directory path where the file is expected to exist.
     */
    public String getFolderPath() {
        return folderPath;
    }

    /**
     * Set the folder path associated with the FileExistsTrigger.
     *
     * @param folderPath The directory path where the file is expected to exist.
     */
    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }
    
    /**
     * Check if the trigger is activated based on the existence of the specified file in the directory.
     *
     * @return True if the file exists in the specified directory, otherwise false.
     */
    @JsonIgnore
    @Override
    public boolean isTriggered() {
        if (folderPath == null || folderPath.trim().isEmpty() || fileName == null || fileName.trim().isEmpty()) {
            return false;
        }
        
        Path filePath = Paths.get(folderPath, fileName);
        return Files.exists(filePath);
    }

    /**
     * Get a string representation of the file name and folder path being checked for existence.
     *
     * @return A string containing the file name and the directory path.
     */
    @Override
    public String toString() {
        return "File name to check existence: " + fileName + " in the directory: '" + folderPath + "'";
    }
}