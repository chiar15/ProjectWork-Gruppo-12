/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.trigger;

import it.unisa.diem.se.automationapp.trigger.TimeTrigger;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class TimeTriggerTest {

    @Test
    public void testConstructorAndTime() {
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("time", "10:15");

        TimeTrigger timeTrigger = new TimeTrigger(triggerData);
        assertEquals("10:15", timeTrigger.getTime());
    }

    @Test
    public void testIsTriggeredBeforeTime() {
        Map<String, String> triggerData = new HashMap<>();
        LocalTime now = LocalTime.now();
        LocalTime afterNow = now.plusMinutes(2);
        triggerData.put("time", afterNow.toString());

        TimeTrigger timeTrigger = new TimeTrigger(triggerData);
        assertFalse(timeTrigger.isTriggered());
    }

    @Test
    public void testIsTriggeredAfterTime() {
        Map<String, String> triggerData = new HashMap<>();
        LocalTime now = LocalTime.now();
        LocalTime beforeNow = now.minusMinutes(1);
        triggerData.put("time", beforeNow.toString());

        TimeTrigger timeTrigger = new TimeTrigger(triggerData);
        assertTrue(timeTrigger.isTriggered());
    }
    
    @Test
    public void testGetType() {
        Map<String, String> triggerData = new HashMap<>();
        String projectDirectory = System.getProperty("user.dir");
        triggerData.put("filePath", projectDirectory + "\\test\\it\\unisa\\diem\\se\\automationapptest\\action\\data\\song01.wav");

        TimeTrigger timeTrigger = new TimeTrigger(triggerData);
        assertEquals("Il tipo di azione dovrebbe essere AUDIOACTION", "AUDIOACTION", timeTrigger.getType());
    }
}
