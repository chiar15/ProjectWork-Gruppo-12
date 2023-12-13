/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.eventsmanagement;

import it.unisa.diem.se.automationapp.event.EventInterface;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The EventQueue class represents a queue of events.
 * It encapsulates a queue data structure containing objects implementing the EventInterface.
 * This class is intended for supporting event serialization and deserialization and nothing else.
 *
 * @author chiar
 */
public class EventQueue {
    private Queue<EventInterface> events;

    /**
     * Constructs an EventQueue object, initializing the internal queue as a LinkedList.
     */
    public EventQueue() {
        this.events = new LinkedList<>();
    }

    /**
     * Retrieves the queue of events.
     * @return The queue of events encapsulated by this EventQueue object.
     */
    public Queue<EventInterface> getEvents() {
        return events;
    }

    /**
     * Sets the queue of events.
     * @param events The queue of events to be set for this EventQueue object.
     */
    public void setEvents(Queue<EventInterface> events) {
        this.events = events;
    }
}
