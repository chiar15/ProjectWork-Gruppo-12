/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.trigger;

import it.unisa.diem.se.automationapp.trigger.DayOfWeekTrigger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class DayOfWeekTriggerTest {

    private DayOfWeekTrigger dayOfWeekTrigger;
    private String currentDayOfWeek;

    @Before
    public void setUp() {
        currentDayOfWeek =  LocalDate.now().getDayOfWeek().name();
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("day_of_week", currentDayOfWeek);

        dayOfWeekTrigger = new DayOfWeekTrigger(triggerData);
    }

    @Test
    public void testConstructorAndGetter() {
        assertEquals("The day of the week should match the one set in constructor", currentDayOfWeek, dayOfWeekTrigger.getDayOfWeek());
    }

    @Test
    public void testSetDayOfWeek() {
        String newDayOfWeek = "MONDAY";
        dayOfWeekTrigger.setDayOfWeek(newDayOfWeek);
        assertEquals("The day of the week should be updated", newDayOfWeek, dayOfWeekTrigger.getDayOfWeek());
    }

    @Test
    public void testIsTriggered() {
        assertTrue("The trigger should be activated on the current day of the week", dayOfWeekTrigger.isTriggered());
    }
}
