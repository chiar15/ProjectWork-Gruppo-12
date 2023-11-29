/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.observer;

import it.unisa.diem.se.automationapp.observer.MessageEvent;
import it.unisa.diem.se.automationapp.observer.MessageEventType;
import org.junit.Test;
import static org.junit.Assert.*;

public class MessageEventTest {

    @Test
    public void testErrorEventWithErrorType() {
        String testErrorMessage = "Test error message";
        MessageEvent errorEvent = new MessageEvent(testErrorMessage, MessageEventType.ERROR);

        assertEquals(testErrorMessage, errorEvent.getMessage());
        assertEquals(MessageEventType.ERROR, errorEvent.getType());
    }

    @Test
    public void testErrorEventWithCriticalErrorType() {
        String testErrorMessage = "Critical test error message";
        MessageEvent errorEvent = new MessageEvent(testErrorMessage, MessageEventType.CRITICAL_ERROR);

        assertEquals(testErrorMessage, errorEvent.getMessage());
        assertEquals(MessageEventType.CRITICAL_ERROR, errorEvent.getType());
    }
}

