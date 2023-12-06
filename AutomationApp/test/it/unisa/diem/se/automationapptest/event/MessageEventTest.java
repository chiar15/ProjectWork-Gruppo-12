/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.event;

import it.unisa.diem.se.automationapp.event.MessageEvent;
import org.junit.Test;
import static org.junit.Assert.*;

public class MessageEventTest {

    @Test
    public void testCreateAndRetrieveMessage() {
        String testMessage = "Test error message";
        MessageEvent messageEvent = new MessageEvent(testMessage);

        assertEquals("The message should match the one set in constructor", testMessage, messageEvent.getMessage());
    }

    @Test
    public void testSetter() {
        String initialMessage = "Initial message";
        MessageEvent messageEvent = new MessageEvent(initialMessage);

        String newMessage = "New message";
        messageEvent.setMessage(newMessage);

        assertEquals("The message should be updated", newMessage, messageEvent.getMessage());
    }
}

