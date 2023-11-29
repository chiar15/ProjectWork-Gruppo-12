/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.observer;

import it.unisa.diem.se.automationapp.observer.EventBus;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import static org.junit.Assert.*;

public class EventBusTest {

    private EventBus eventBus;

    @Before
    public void setUp() {
        eventBus = EventBus.getInstance();
        // Pulisce i sottoscrittori per la classe String prima di ogni test
        eventBus.clearSubscribers(String.class);
    }

    @Test
    public void testSubscribeAndPublish() {
        AtomicBoolean eventReceived = new AtomicBoolean(false);

        eventBus.subscribe(String.class, event -> eventReceived.set(true));
        eventBus.publish("Test Event");

        assertTrue(eventReceived.get());
    }

    @Test
    public void testSubscribeAndPublishWithMultipleSubscribers() {
        AtomicBoolean firstSubscriberNotified = new AtomicBoolean(false);
        AtomicBoolean secondSubscriberNotified = new AtomicBoolean(false);

        eventBus.subscribe(String.class, event -> firstSubscriberNotified.set(true));
        eventBus.subscribe(String.class, event -> secondSubscriberNotified.set(true));
        eventBus.publish("Test Event");

        assertTrue(firstSubscriberNotified.get());
        assertTrue(secondSubscriberNotified.get());
    }

    @Test
    public void testPublishWithNoSubscribers() {
        AtomicBoolean eventReceived = new AtomicBoolean(false);

        // Nessuna sottoscrizione per la classe String in questo test
        eventBus.publish("Test Event");

        assertFalse(eventReceived.get());
    }

    @Test
    public void testUnsubscribe() {
        AtomicBoolean eventReceived = new AtomicBoolean(false);
        Consumer<String> listener = event -> eventReceived.set(true);

        eventBus.subscribe(String.class, listener);
        eventBus.unsubscribe(String.class, listener);
        eventBus.publish("Test Event");

        assertFalse("L'evento non dovrebbe essere ricevuto dopo la disiscrizione", eventReceived.get());
    }
}
