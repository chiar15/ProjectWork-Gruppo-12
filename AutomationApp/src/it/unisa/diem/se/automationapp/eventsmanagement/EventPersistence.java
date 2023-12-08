/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.eventsmanagement;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisa.diem.se.automationapp.event.ErrorEvent;
import it.unisa.diem.se.automationapp.event.ErrorEventType;
import it.unisa.diem.se.automationapp.event.EventInterface;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;


public class EventPersistence {
    private final File file;
    private ObjectMapper objectMapper;
    private EventBus eventBus;

    public EventPersistence() {
        this.file = new File(System.getProperty("user.dir") + "\\data\\SaveEvents.json");
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.eventBus = EventBus.getInstance();
    }
    
    public void saveEventsToFile(Queue<EventInterface> queue)throws IOException{
        EventQueue eventQueue = new EventQueue();
        eventQueue.setEvents(queue);
        if(file.exists()){
            objectMapper.writeValue(file, eventQueue);
        }
    }
    
    public Queue<EventInterface> loadEventsFromFile(){
        EventQueue eventQueue = new EventQueue();
        
        try {
            file.createNewFile();
            eventQueue = objectMapper.readValue(file, EventQueue.class);
        } catch (IOException e){
            eventBus.publish(new ErrorEvent("Error loading waiting to be executed rules from file", ErrorEventType.NORMAL)); 
        }
        
        return (new LinkedList<>(eventQueue.getEvents()));
    }
}
