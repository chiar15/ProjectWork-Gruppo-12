/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.eventsmanagement;

import it.unisa.diem.se.automationapp.event.EventInterface;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author chiar
 */
public class EventQueue{
    private Queue<EventInterface> events;

    public EventQueue() {
        this.events = new LinkedList<>();
    }

    public Queue<EventInterface> getEvents() {
        return events;
    }

    public void setEvents(Queue<EventInterface> events) {
        this.events = events;
    }
}