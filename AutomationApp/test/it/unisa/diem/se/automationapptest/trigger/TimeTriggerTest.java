/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.trigger;

import it.unisa.diem.se.automationapp.trigger.TimeTrigger;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class TimeTriggerTest {

    @Test
    public void testConstructorAndTime() {
        LocalTime setTime = LocalTime.of(10, 15);
        TimeTrigger timeTrigger = new TimeTrigger(setTime);
        assertEquals(setTime, timeTrigger.getTime());
    }

    @Test
    public void testIsTriggeredBeforeTime() {
        LocalTime now = LocalTime.now();
        LocalTime afterNow = now.plusMinutes(2);
        TimeTrigger timeTrigger = new TimeTrigger(afterNow);
        assertFalse("Trigger should not be activated before the set time", timeTrigger.isTriggered());
    }

    @Test
    public void testIsTriggeredAfterTime() {
        LocalTime now = LocalTime.now();
        LocalTime beforeNow = now.minusMinutes(1);
        TimeTrigger timeTrigger = new TimeTrigger(beforeNow);
        assertTrue("Trigger should be activated after the set time", timeTrigger.isTriggered());
    }
    
    @Test
    public void testTriggerWithNullTime() {
        TimeTrigger timeTrigger = new TimeTrigger(null);
        assertFalse("Trigger should not be activated with null time", timeTrigger.isTriggered());
    }

    @Test
    public void testTriggerAtDayLimits() {
        LocalTime currentTime = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

        TimeTrigger endOfDayTrigger = new TimeTrigger(LocalTime.of(23, 59));
        boolean endOfDayExpected = !currentTime.isBefore(LocalTime.of(23, 59));
        assertEquals("Trigger should match expected behavior at 23:59", endOfDayExpected, endOfDayTrigger.isTriggered());

        TimeTrigger startOfDayTrigger = new TimeTrigger(LocalTime.of(0, 0));
        boolean startOfDayExpected = !currentTime.isBefore(LocalTime.of(0, 0));
        assertEquals("Trigger should match expected behavior at 00:00", startOfDayExpected, startOfDayTrigger.isTriggered());
    }

    @Test
    public void testTriggerWithExactTime() {
        LocalTime now = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        TimeTrigger timeTrigger = new TimeTrigger(now);
        assertTrue("Trigger should be activated at the exact time", timeTrigger.isTriggered());
    }
}

