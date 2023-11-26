/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.trigger;

import it.unisa.diem.se.automationapp.trigger.TimeTrigger;
import it.unisa.diem.se.automationapp.trigger.TriggerFactory;
import it.unisa.diem.se.automationapp.trigger.TriggerInterface;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class TriggerFactoryTest {

    @Test
    public void testCreateTimeTrigger() {
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("time", "10:00");

        TriggerInterface trigger = TriggerFactory.createTrigger("TimeTrigger", triggerData);

        assertNotNull(trigger);
        assertTrue(trigger instanceof TimeTrigger);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateTriggerWithInvalidType() {
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("someKey", "someValue");

        TriggerFactory.createTrigger("InvalidTriggerType", triggerData);
    }
}

