/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.eventsmanagement;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.unisa.diem.se.automationapp.event.ErrorEvent;
import it.unisa.diem.se.automationapp.event.ErrorEventType;
import it.unisa.diem.se.automationapp.event.EventInterface;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The EventPersistence class manages the serialization and deserialization of events to and from a file.
 * It handles saving events to a file and loading events from a file using Jackson ObjectMapper.
 */
public class EventPersistence {
    private final File file;
    private ObjectMapper objectMapper;
    private EventBus eventBus;

    /**
     * Constructs an EventPersistence object, initializing the file path, ObjectMapper, and EventBus instance.
     */
    public EventPersistence() {
        this.file = new File(System.getProperty("user.dir") + "\\data\\SaveEvents.json");
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        this.eventBus = EventBus.getInstance();
    }
    
    /**
     * Saves the provided queue of events to a file.
     * @param queue The queue of events to be saved.
     * @throws IOException If an I/O error occurs during the saving process.
     */
    public void saveEventsToFile(Queue<EventInterface> queue) throws IOException {
        EventQueue eventQueue = new EventQueue();
        eventQueue.setEvents(queue);
        if(file.exists()){
            objectMapper.writeValue(file, eventQueue);
        }
    }
    
    /**
     * Loads events from a file and returns them as a queue.
     * @return A queue containing events loaded from the file.
     */
    public Queue<EventInterface> loadEventsFromFile() {
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