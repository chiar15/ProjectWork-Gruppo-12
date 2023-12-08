/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    public StringAction(Map<String, String> actionData) {
        this.string = actionData.get("string");
        this.filePath = actionData.get("stringFilePath");
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
    public void execute() throws IOException {
        File file = new File(filePath);
        
        if(file == null){
            file.createNewFile();
        }
        try(PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filePath, true)))){
            pw.append(string + " ");
        }
    }
}
