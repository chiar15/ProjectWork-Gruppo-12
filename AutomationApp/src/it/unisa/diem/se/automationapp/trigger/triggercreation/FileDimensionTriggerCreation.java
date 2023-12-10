/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger.triggercreation;

import it.unisa.diem.se.automationapp.trigger.FileDimensionTrigger;
import it.unisa.diem.se.automationapp.trigger.TriggerInterface;
import java.util.Map;

/**
 *
 * @author chiar
 */
public class FileDimensionTriggerCreation implements TriggerCreationStrategy{

    public FileDimensionTriggerCreation() {
    }

    @Override
    public TriggerInterface createTrigger(Map<String, String> triggerData) {
        String filePath = triggerData.get("filePath");
        long dimension = Long.parseLong(triggerData.get("dimension"));
        
        return new FileDimensionTrigger(filePath, dimension);
    }
    
}
