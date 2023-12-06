/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.event;

import it.unisa.diem.se.automationapp.event.SceneEvent;
import it.unisa.diem.se.automationapp.event.SceneEventType;
import org.junit.Test;
import static org.junit.Assert.*;

public class SceneEventTest {

    @Test
    public void testCreateAndRetrieveProperties() {
        String testMessage = "Test scene message";
        SceneEventType testEventType = SceneEventType.BUSY;

        SceneEvent sceneEvent = new SceneEvent(testMessage, testEventType);

        assertEquals("The message should match the one set in constructor", testMessage, sceneEvent.getMessage());
        assertEquals("The event type should match the one set in constructor", testEventType, sceneEvent.getEventType());
    }

    @Test
    public void testSetters() {
        String initialMessage = "Initial scene message";
        SceneEventType initialEventType = SceneEventType.BUSY;
        SceneEvent sceneEvent = new SceneEvent(initialMessage, initialEventType);

        String newMessage = "New scene message";
        SceneEventType newEventType = SceneEventType.FREE;
        sceneEvent.setMessage(newMessage);
        sceneEvent.setEventType(newEventType);

        assertEquals("The message should be updated", newMessage, sceneEvent.getMessage());
        assertEquals("The event type should be updated", newEventType, sceneEvent.getEventType());
    }
}

