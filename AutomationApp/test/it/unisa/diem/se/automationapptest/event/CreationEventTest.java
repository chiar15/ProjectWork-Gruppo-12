/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.event;

import it.unisa.diem.se.automationapp.event.CreationEvent;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

public class CreationEventTest {

    @Test
    public void testConstructorAndAccessors() {
        String testMessage = "Test creation message";
        Rule testRule = new Rule("TestRule", null, null);

        CreationEvent creationEvent = new CreationEvent(testMessage, testRule);

        assertEquals("The message should match the one set in constructor", testMessage, creationEvent.getMessage());
        assertSame("The rule should match the one set in constructor", testRule, creationEvent.getRule());
    }

    @Test
    public void testSetters() {
        String initialMessage = "Initial message";
        Rule initialRule = new Rule("InitialRule", null, null);
        CreationEvent creationEvent = new CreationEvent(initialMessage, initialRule);

        String newMessage = "New message";
        Rule newRule = new Rule("NewRule", null, null);
        creationEvent.setMessage(newMessage);
        creationEvent.setRule(newRule);

        assertEquals("The message should be updated", newMessage, creationEvent.getMessage());
        assertSame("The rule should be updated", newRule, creationEvent.getRule());
    }
}
