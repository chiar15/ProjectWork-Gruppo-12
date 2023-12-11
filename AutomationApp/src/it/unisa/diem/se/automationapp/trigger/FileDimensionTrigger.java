/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * The FileDimensionTrigger class implements the TriggerInterface to define a trigger based on the size of a file.
 * It checks whether a file's size meets a specified dimension limit.
 * 
 */
public class FileDimensionTrigger implements TriggerInterface {
    private String filePath;
    private long dimension;

    /**
     * Default constructor for FileDimensionTrigger.
     */
    public FileDimensionTrigger() {
    }

    /**
     * Constructor for FileDimensionTrigger with a specified file path and dimension.
     *
     * @param filePath  The path of the file to check size.
     * @param dimension The size limit in bytes.
     */
    public FileDimensionTrigger(String filePath, long dimension) {
        this.filePath = filePath;
        this.dimension = dimension;
    }

    /**
     * Get the file path associated with the FileDimensionTrigger.
     *
     * @return The path of the file to check size.
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * Set the file path associated with the FileDimensionTrigger.
     *
     * @param filePath The path of the file to check size.
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Get the size limit associated with the FileDimensionTrigger.
     *
     * @return The size limit in bytes.
     */
    public long getDimension() {
        return dimension;
    }

    /**
     * Set the size limit associated with the FileDimensionTrigger.
     *
     * @param dimension The size limit in bytes.
     */
    public void setDimension(long dimension) {
        this.dimension = dimension;
    }

    /**
     * Check if the trigger is activated based on the file's size meeting the specified dimension limit.
     *
     * @return True if the file's size meets or exceeds the specified dimension, otherwise false.
     */
    @JsonIgnore
    @Override
    public boolean isTriggered() {
        if (filePath == null || filePath.trim().isEmpty()) {
            return false;
        }

        if (dimension < 0) {
            return false;
        }

        Path path = Paths.get(filePath);
        long fileDimension;

        try {
            fileDimension = Files.size(path);
        } catch (IOException e) {
            return false;
        }

        return fileDimension >= dimension;
    }

    /**
     * Get a string representation of the file path and size limit.
     *
     * @return A string containing the file path and the size limit.
     */
    @Override
    public String toString() {
        return "Path of the file to check size: '" + filePath + "' with size limit: " + dimension + " Byte";
    }
}