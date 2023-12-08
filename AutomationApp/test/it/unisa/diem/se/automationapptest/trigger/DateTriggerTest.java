/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.trigger;

import it.unisa.diem.se.automationapp.trigger.DateTrigger;
import it.unisa.diem.se.automationapp.trigger.TimeTrigger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class DateTriggerTest {

    private DateTrigger dateTrigger;
    private String currentDate;

    @Before
    public void setUp() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        currentDate = dtf.format(LocalDateTime.now());
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("date", currentDate);

        dateTrigger = new DateTrigger(triggerData);
    }

    @Test
    public void testConstructorAndGetter() {
        assertEquals("The date should match the one set in constructor", currentDate, dateTrigger.getDate());
    }

    @Test
    public void testSetDate() {
        String newDate = "01/01/2023";
        dateTrigger.setDate(newDate);
        assertEquals("The date should be updated", newDate, dateTrigger.getDate());
    }

    @Test
    public void testIsTriggered() {
        assertTrue("The trigger should be activated on the current date", dateTrigger.isTriggered());
    }
}

