/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.observer;

import it.unisa.diem.se.automationapp.observer.ErrorEvent;
import it.unisa.diem.se.automationapp.observer.EventType;
import org.junit.Test;
import static org.junit.Assert.*;

public class ErrorEventTest {

    @Test
    public void testErrorEventWithErrorType() {
        String testErrorMessage = "Test error message";
        ErrorEvent errorEvent = new ErrorEvent(testErrorMessage, EventType.ERROR);

        assertEquals(testErrorMessage, errorEvent.getErrorMessage());
        assertEquals(EventType.ERROR, errorEvent.getType());
    }

    @Test
    public void testErrorEventWithCriticalErrorType() {
        String testErrorMessage = "Critical test error message";
        ErrorEvent errorEvent = new ErrorEvent(testErrorMessage, EventType.CRITICAL_ERROR);

        assertEquals(testErrorMessage, errorEvent.getErrorMessage());
        assertEquals(EventType.CRITICAL_ERROR, errorEvent.getType());
    }
}

