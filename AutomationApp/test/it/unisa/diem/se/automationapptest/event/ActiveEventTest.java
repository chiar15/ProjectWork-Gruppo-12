/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.event;

import it.unisa.diem.se.automationapp.event.ActiveEvent;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;
import org.junit.Test;
import static org.junit.Assert.*;

public class ActiveEventTest {

    @Test
    public void testConstructorAndAccessors() {
        String testMessage = "Test active message";
        Rule testRule = new Rule("TestRule", null, null);

        ActiveEvent activeEvent = new ActiveEvent(testMessage, testRule);

        assertEquals("The message should match the one set in constructor", testMessage, activeEvent.getMessage());
        assertSame("The rule should match the one set in constructor", testRule, activeEvent.getRule());
    }

    @Test
    public void testSetters() {
        String initialMessage = "Initial message";
        Rule initialRule = new Rule("InitialRule", null, null);
        ActiveEvent activeEvent = new ActiveEvent(initialMessage, initialRule);

        String newMessage = "New message";
        Rule newRule = new Rule("NewRule", null, null);
        activeEvent.setMessage(newMessage);
        activeEvent.setRule(newRule);

        assertEquals("The message should be updated", newMessage, activeEvent.getMessage());
        assertSame("The rule should be updated", newRule, activeEvent.getRule());
    }
}

