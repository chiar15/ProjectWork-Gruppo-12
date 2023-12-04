/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.observer;

import it.unisa.diem.se.automationapp.observer.MessageEvent;
import it.unisa.diem.se.automationapp.observer.ErrorEventType;
import org.junit.Test;
import static org.junit.Assert.*;

public class MessageEventTest {

    @Test
    public void testMessageEvent() {
        String testMessage = "Test error message";
        MessageEvent messageEvent = new MessageEvent(testMessage);

        assertEquals(testMessage, messageEvent.getMessage());
    }
}

