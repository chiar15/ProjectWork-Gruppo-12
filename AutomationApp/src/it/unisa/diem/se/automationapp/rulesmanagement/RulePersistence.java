/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import it.unisa.diem.se.automationapp.event.ErrorEvent;
import it.unisa.diem.se.automationapp.observer.EventBus;
import it.unisa.diem.se.automationapp.event.ErrorEventType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;

/**
 *
 * @author chiar
 */
public class RulePersistence {
    private File file;
    private ObjectMapper objectMapper;
    private EventBus eventbus;

    public RulePersistence() {
        this.file = new File(System.getProperty("user.dir") + "\\data\\SaveRules.json");
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.eventbus = EventBus.getInstance();
        // Configura ObjectMapper qui se necessario, come per il polimorfismo
    }

    // Metodo sincronizzato per scrivere le regole nel file
    public void saveRulesToFile(List<Rule> list) throws IOException{
        
        if(file.exists()){
            objectMapper.writeValue(file, list);
        }
    }

    // Metodo sincronizzato per leggere le regole dal file
    public List<Rule> loadRulesFromFile() {
        List<Rule> list = new ArrayList<>();
        
        
        try {
            file.createNewFile();
            list = objectMapper.readValue(file, new TypeReference<List<Rule>>() {});
        } catch (MismatchedInputException e){
            e.printStackTrace();
        } catch (IOException e){
            eventbus.publish(new ErrorEvent("Error loading automations from file", ErrorEventType.NORMAL));
        }
        
        return list;
    }

    // Altri metodi...
}