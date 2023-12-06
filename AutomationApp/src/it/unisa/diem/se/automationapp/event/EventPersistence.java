/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.event;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;


public class EventPersistence {
    private File file;
    private ObjectMapper objectMapper;

    public EventPersistence() {
        this.file = new File(System.getProperty("user.dir") + "\\data\\SaveEvents.json");
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    
    public void saveEventsToFile(Queue<EventInterface> queue){
        EventQueue eventQueue = new EventQueue();
        eventQueue.setEvents(queue);
        if(file.exists()){
            try{
                objectMapper.writeValue(file, eventQueue);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    
    public Queue<EventInterface> loadEventsFromFile(){
        EventQueue eventQueue = new EventQueue();
        
        try {
            file.createNewFile();
            eventQueue = objectMapper.readValue(file, EventQueue.class);
            PrintWriter writer = new PrintWriter(file.getAbsolutePath());
            writer.print("");
            // other operations
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        } 
        
        return (new LinkedList<>(eventQueue.getEvents()));
    }
}
