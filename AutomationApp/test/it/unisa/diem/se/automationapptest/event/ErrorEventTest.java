/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.event;

import it.unisa.diem.se.automationapp.event.ErrorEvent;
import it.unisa.diem.se.automationapp.event.ErrorEventType;
import org.junit.Test;
import static org.junit.Assert.*;

public class ErrorEventTest {

    @Test
    public void testCreateAndRetrieveProperties() {
        String testMessage = "Test error message";
        ErrorEventType testEventType = ErrorEventType.CRITICAL;

        ErrorEvent errorEvent = new ErrorEvent(testMessage, testEventType);

        assertEquals("The message should match the one set in constructor", testMessage, errorEvent.getMessage());
        assertEquals("The event type should match the one set in constructor", testEventType, errorEvent.getEventType());
    }

    @Test
    public void testSetters() {
        String initialMessage = "Initial error message";
        ErrorEventType initialEventType = ErrorEventType.CRITICAL;
        ErrorEvent errorEvent = new ErrorEvent(initialMessage, initialEventType);

        String newMessage = "New error message";
        ErrorEventType newEventType = ErrorEventType.NORMAL;
        errorEvent.setMessage(newMessage);
        errorEvent.setEventType(newEventType);

        assertEquals("The message should be updated", newMessage, errorEvent.getMessage());
        assertEquals("The event type should be updated", newEventType, errorEvent.getEventType());
    }
}

