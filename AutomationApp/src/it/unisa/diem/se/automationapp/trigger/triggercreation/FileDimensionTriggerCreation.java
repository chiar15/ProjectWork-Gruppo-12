/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger.triggercreation;

import it.unisa.diem.se.automationapp.trigger.FileDimensionTrigger;
import it.unisa.diem.se.automationapp.trigger.TriggerInterface;
import java.util.Map;

/**
 * The FileDimensionTriggerCreation class implements the TriggerCreationStrategy interface to create FileDimensionTrigger instances.
 * It reads file path and dimension information from the provided trigger data map and generates a FileDimensionTrigger object based on that information.
 * This class is part of a strategy pattern for creating different types of triggers.
 * 
 */
public class FileDimensionTriggerCreation implements TriggerCreationStrategy{

    /**
     * Default constructor for the FileDimensionTriggerCreation class.
     */
    public FileDimensionTriggerCreation() {
    }

    /**
     * Creates a FileDimensionTrigger object based on the provided trigger data.
     *
     * @param triggerData A map containing data related to the trigger creation, where "filePath" key represents the file path and "dimension" key represents the dimension information.
     * @return FileDimensionTrigger An instance of the FileDimensionTrigger class created with the provided file path and dimension information.
     */
    @Override
    public TriggerInterface createTrigger(Map<String, String> triggerData) {
        String filePath = triggerData.get("filePath");
        long dimension = Long.parseLong(triggerData.get("dimension"));
        
        return new FileDimensionTrigger(filePath, dimension);
    }
}