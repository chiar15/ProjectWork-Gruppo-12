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
 *
 * @author chiar
 */
public class FileDimensionTrigger implements TriggerInterface{
    private String filePath;
    private long dimension;

    public FileDimensionTrigger() {
    }

    public FileDimensionTrigger(String filePath, long dimension) {
        this.filePath = filePath;
        this.dimension = dimension;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getDimension() {
        return dimension;
    }

    public void setDimension(long dimension) {
        this.dimension = dimension;
    }
    
    @JsonIgnore
    @Override
    public boolean isTriggered() {
        if(filePath == null || filePath.trim().isEmpty()){
            return false;
        }
        
        if(dimension <0){
            return false;
        }
        
        Path path = Paths.get(filePath);
        long fileDimension;
        
        try{
           fileDimension = Files.size(path); 
        } catch(IOException e){
            return false;
        }
        
        return fileDimension >= dimension;
    }

    @Override
    public String toString() {
        return "Path of the file to check size: '" + filePath + "' with size limit: " + dimension + " Byte";
    }
}