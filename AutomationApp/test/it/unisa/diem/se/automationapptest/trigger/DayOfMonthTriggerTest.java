/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.trigger;

import it.unisa.diem.se.automationapp.trigger.DayOfMonthTrigger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class DayOfMonthTriggerTest {

    private DayOfMonthTrigger dayOfMonthTrigger;
    private String currentDayOfMonth;

    @Before
    public void setUp() {
        currentDayOfMonth = Integer.toString(LocalDate.now().getDayOfMonth());
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("day_of_month", currentDayOfMonth);

        dayOfMonthTrigger = new DayOfMonthTrigger(triggerData);
    }

    @Test
    public void testConstructorAndGetter() {
        assertEquals("The day of the month should match the one set in constructor", currentDayOfMonth, dayOfMonthTrigger.getDayOfMonth());
    }

    @Test
    public void testSetDayOfMonth() {
        String newDayOfMonth = "15";
        dayOfMonthTrigger.setDayOfMonth(newDayOfMonth);
        assertEquals("The day of the month should be updated", newDayOfMonth, dayOfMonthTrigger.getDayOfMonth());
    }

    @Test
    public void testIsTriggered() {
        assertTrue("The trigger should be activated on the current day of the month", dayOfMonthTrigger.isTriggered());
    }

    @Test
    public void testGetType() {
        assertEquals("The trigger type should be DAYOFMONTHTRIGGER", "DAYOFMONTHTRIGGER", dayOfMonthTrigger.getType());
    }
}

