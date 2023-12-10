/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.action;

import it.unisa.diem.se.automationapp.action.exception.FileException;
import it.unisa.diem.se.automationapp.action.exception.InvalidInputException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.NoSuchFileException;
import java.util.Map;

/**
 *
 * @author chiar
 */
public class StringAction implements ActionInterface{
    private String string;
    private String filePath;

    public StringAction() {
    }

    public StringAction(String string, String filePath) {
        this.string = string;
        this.filePath = filePath;
    }
        
    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    @Override
    public void execute() throws InvalidInputException, FileException {
        
        if(string == null || string.trim().isEmpty()){
            throw new InvalidInputException("The string to write cannot be empty.");
        }
        
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new InvalidInputException("The file path cannot be empty.");
        }
        
        File file = new File(filePath);

        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))){
            pw.append(string + " ");
        } catch(NoSuchFileException e){
            throw new FileException("The selected file was not found.");
        }catch(IOException e){
            throw new FileException("Cannot access the selected file. There might be some conflicts with system restrictions.");
        }
    }

    @Override
    public String toString() {
        return "Appending the string '" + string + "' to the end of the txt file at the path: " + filePath;
    }
}
