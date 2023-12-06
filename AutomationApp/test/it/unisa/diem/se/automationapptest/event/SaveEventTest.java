/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.event;

import it.unisa.diem.se.automationapp.event.SaveEvent;
import org.junit.Test;
import static org.junit.Assert.*;

public class SaveEventTest {

    @Test
    public void testCreateAndRetrieveProperties() {
        String testMessage = "Test save message";

        SaveEvent saveEvent = new SaveEvent(testMessage);

        assertEquals("The message should match the one set in constructor", testMessage, saveEvent.getMessage());
    }

    @Test
    public void testSetter() {
        String initialMessage = "Initial save message";
        SaveEvent saveEvent = new SaveEvent(initialMessage);

        String newMessage = "New save message";
        saveEvent.setMessage(newMessage);

        assertEquals("The message should be updated", newMessage, saveEvent.getMessage());
    }
}
