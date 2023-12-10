/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.event;

import it.unisa.diem.se.automationapp.event.AudioEvent;
import it.unisa.diem.se.automationapp.event.AudioEventType;
import org.junit.Test;
import static org.junit.Assert.*;

public class AudioEventTest {

    @Test
    public void testCreateAndRetrieveProperties() {
        String testMessage = "Test message";
        AudioEventType testEventType = AudioEventType.STARTED;

        AudioEvent audioEvent = new AudioEvent(testMessage, testEventType);

        assertEquals("The message should match the one set in constructor", testMessage, audioEvent.getMessage());
        assertEquals("The event type should match the one set in constructor", testEventType, audioEvent.getEventType());
    }

    @Test
    public void testSetters() {
        String initialMessage = "Initial message";
        AudioEventType initialEventType = AudioEventType.STARTED;
        AudioEvent audioEvent = new AudioEvent(initialMessage, initialEventType);

        String newMessage = "New message";
        AudioEventType newEventType = AudioEventType.STOPPED;
        audioEvent.setMessage(newMessage);
        audioEvent.setEventType(newEventType);

        assertEquals("The message should be updated", newMessage, audioEvent.getMessage());
        assertEquals("The event type should be updated", newEventType, audioEvent.getEventType());
    }
    
    @Test
    public void testWithNullValues() {
        AudioEvent audioEvent = new AudioEvent(null, null);

        assertNull("The message should be null", audioEvent.getMessage());
        assertNull("The event type should be null", audioEvent.getEventType());
    }

    @Test
    public void testWithEmptyString() {
        AudioEvent audioEvent = new AudioEvent("", AudioEventType.STARTED);

        assertEquals("The message should be an empty string", "", audioEvent.getMessage());
        assertNotNull("The event type should not be null", audioEvent.getEventType());
    }

    @Test
    public void testAllAudioEventTypes() {
        for (AudioEventType type : AudioEventType.values()) {
            AudioEvent audioEvent = new AudioEvent("Test", type);
            assertEquals("The event type should be " + type, type, audioEvent.getEventType());
        }
    }
}

