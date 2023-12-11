/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger.triggercreation;

import it.unisa.diem.se.automationapp.trigger.FileExistsTrigger;
import it.unisa.diem.se.automationapp.trigger.TriggerInterface;
import java.util.Map;

/**
 * The FileExistsTriggerCreation class implements the TriggerCreationStrategy interface to create FileExistsTrigger instances.
 * It reads file name and directory path information from the provided trigger data map and generates a FileExistsTrigger object based on that information.
 * This class is part of a strategy pattern for creating different types of triggers.
 * 
 */
public class FileExistsTriggerCreation implements TriggerCreationStrategy{

    /**
     * Default constructor for the FileExistsTriggerCreation class.
     */
    public FileExistsTriggerCreation() {
    }

    /**
     * Creates a FileExistsTrigger object based on the provided trigger data.
     *
     * @param triggerData A map containing data related to the trigger creation, where "fileName" key represents the file name and "fileDirectory" key represents the directory path.
     * @return FileExistsTrigger An instance of the FileExistsTrigger class created with the provided file name and directory path.
     */
    @Override
    public TriggerInterface createTrigger(Map<String, String> triggerData) {
        String fileName = triggerData.get("fileName");
        String folderPath = triggerData.get("fileDirectory");
        
        return new FileExistsTrigger(fileName, folderPath);
    }
}
