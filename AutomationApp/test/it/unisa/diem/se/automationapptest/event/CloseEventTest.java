/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.event;

import it.unisa.diem.se.automationapp.event.CloseEvent;
import org.junit.Test;
import static org.junit.Assert.*;

public class CloseEventTest {

    @Test
    public void testCreateAndRetrieveProperties() {
        String testMessage = "Test save message";

        CloseEvent saveEvent = new CloseEvent(testMessage);

        assertEquals("The message should match the one set in constructor", testMessage, saveEvent.getMessage());
    }

    @Test
    public void testSetter() {
        String initialMessage = "Initial save message";
        CloseEvent saveEvent = new CloseEvent(initialMessage);

        String newMessage = "New save message";
        saveEvent.setMessage(newMessage);

        assertEquals("The message should be updated", newMessage, saveEvent.getMessage());
    }
    
    @Test
    public void testWithNullValue() {
        CloseEvent closeEvent = new CloseEvent(null);
        assertNull("The message should be null", closeEvent.getMessage());
    }

    @Test
    public void testWithEmptyString() {
        CloseEvent closeEvent = new CloseEvent("");
        assertEquals("The message should be an empty string", "", closeEvent.getMessage());
    }

}
