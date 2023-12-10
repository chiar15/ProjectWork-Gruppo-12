/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.unisa.diem.se.automationapp.event.ErrorEvent;
import it.unisa.diem.se.automationapp.eventsmanagement.EventBus;
import it.unisa.diem.se.automationapp.event.ErrorEventType;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * The RulePersistence class manages the persistence of rules to and from a file.
 * It provides functionality to save and load rules using JSON serialization.
 * This class ensures the integrity of rule data during file operations.
 * Additionally, it communicates errors through an event bus when encountered during file operations.
 * 
 * @author chiar
 */
public class RulePersistence {
    private File file; // The file used for storing rule data.
    private ObjectMapper objectMapper; // The object mapper for JSON serialization/deserialization.
    private EventBus eventBus; // The event bus used for error communication.

    /**
     * Constructs a RulePersistence object initializing file, objectMapper, and eventBus.
     * The file path is set to SaveRules.json in the current working directory.
     * ObjectMapper is configured for proper JSON handling.
     * EventBus instance is obtained for error event communication.
     */
    public RulePersistence() {
        this.file = new File(System.getProperty("user.dir") + "\\data\\SaveRules.json");
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        this.eventBus = EventBus.getInstance();
    }

    /**
     * Saves the list of rules to a file in JSON format.
     * Uses synchronized method to ensure thread safety during file write operation.
     * 
     * @param list The list of rules to be saved.
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public void saveRulesToFile(List<Rule> list) throws IOException{
        RuleList ruleList = new RuleList();
        ruleList.setRules(list);
        if(file.exists()){
            objectMapper.writeValue(file, ruleList);
        }
    }

    /**
     * Loads the list of rules from a JSON file.
     * Uses synchronized method to ensure thread safety during file read operation.
     * 
     * @return The list of loaded rules.
     */
    public List<Rule> loadRulesFromFile() {
        
        RuleList ruleList = new RuleList();
        try {
            file.createNewFile();
            ruleList = objectMapper.readValue(file, RuleList.class);
        }catch (IOException e){
            eventBus.publish(new ErrorEvent("Error loading rules from file", ErrorEventType.NORMAL)); 
        }
        
        return (new LinkedList<>(ruleList.getRules()));
    }
}