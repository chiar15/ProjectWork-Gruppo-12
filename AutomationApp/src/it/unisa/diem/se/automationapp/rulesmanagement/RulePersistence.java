/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.rulesmanagement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.unisa.diem.se.automationapp.action.ActionDeserializer;
import it.unisa.diem.se.automationapp.action.ActionInterface;
import it.unisa.diem.se.automationapp.action.ActionSerializer;
import it.unisa.diem.se.automationapp.observer.EventBus;
import it.unisa.diem.se.automationapp.observer.MessageEvent;
import it.unisa.diem.se.automationapp.observer.MessageEventType;
import it.unisa.diem.se.automationapp.trigger.TriggerDeserializer;
import it.unisa.diem.se.automationapp.trigger.TriggerInterface;
import it.unisa.diem.se.automationapp.trigger.TriggerSerializer;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;

/**
 *
 * @author chiar
 */
public class RulePersistence {
    private File file;
    private Gson gson;
    private EventBus eventbus;

    public RulePersistence() {
        this.file = new File(System.getProperty("user.dir") + "\\data\\SaveRules.txt");
        this.gson = new GsonBuilder().registerTypeAdapter(TriggerInterface.class, new TriggerSerializer())
    .registerTypeAdapter(TriggerInterface.class, new TriggerDeserializer())
    .registerTypeAdapter(ActionInterface.class, new ActionSerializer())
    .registerTypeAdapter(ActionInterface.class, new ActionDeserializer())
    .create();
        this.eventbus = EventBus.getInstance();
    }
    
    public void saveRulesToFile(List<Rule> list) throws IOException{
        String json = gson.toJson(list);
        
        file.createNewFile();
        try(PrintWriter fw = new PrintWriter(new BufferedWriter(new FileWriter(file, false)))){
            fw.write(json);
        }
    }
    
    public List<Rule> loadRulesFromFile(){
        List<Rule> list = new ArrayList<>();
        if(file.exists()){
            try(BufferedReader br = new BufferedReader(new FileReader(file))){
                StringBuilder jsonBuilder = new StringBuilder();
                String line;
                while((line = br.readLine())!= null){
                    jsonBuilder.append(line);
                }
                Type ruleListType = new TypeToken<List<Rule>>(){}.getType();
                list = gson.fromJson(jsonBuilder.toString(), ruleListType);
            } catch (IOException e){
                eventbus.publish(new MessageEvent("Error loading automations from file", MessageEventType.ERROR));
            }
        }
        return list;
    }
}
