/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.eventsmanagement;

import it.unisa.diem.se.automationapp.eventsmanagement.EventQueue;
import it.unisa.diem.se.automationapp.event.EventInterface;
import it.unisa.diem.se.automationapp.event.MessageEvent;
import java.util.LinkedList;
import org.junit.Before;
import org.junit.Test;
import java.util.Queue;
import static org.junit.Assert.*;

public class EventQueueTest {

    private EventQueue eventQueue;
    private EventInterface testEvent;

    @Before
    public void setUp() {
        eventQueue = new EventQueue();
        testEvent = new MessageEvent("Test message");
    }

    @Test
    public void testEnqueueAndDequeueEvent() {
        assertTrue("The event queue should be initially empty", eventQueue.getEvents().isEmpty());

        // Test adding an event
        eventQueue.getEvents().add(testEvent);
        assertFalse("The event queue should not be empty after adding an event", eventQueue.getEvents().isEmpty());

        // Test retrieving an event
        EventInterface retrievedEvent = eventQueue.getEvents().poll();
        assertEquals("The retrieved event should be the one that was added", testEvent, retrievedEvent);
        assertTrue("The event queue should be empty after removing the event", eventQueue.getEvents().isEmpty());
    }

    @Test
    public void testSetAndGetEvents() {
        Queue<EventInterface> newEvents = new LinkedList<>();
        newEvents.add(testEvent);

        eventQueue.setEvents(newEvents);
        assertSame("The events queue should be the one that was set", newEvents, eventQueue.getEvents());
    }
}
