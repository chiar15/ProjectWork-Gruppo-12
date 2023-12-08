/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.trigger;

import it.unisa.diem.se.automationapp.trigger.*;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.Assert.*;

public class TriggerFactoryTest {

    @Test
    public void testCreateTimeTrigger() {
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("type", TriggerEnum.TIMETRIGGER.name());
        triggerData.put("time", "10:00");

        TriggerInterface trigger = TriggerFactory.createTrigger(triggerData);
        assertTrue(trigger instanceof TimeTrigger);
    }

    @Test
    public void testCreateDayOfWeekTrigger() {
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("type", TriggerEnum.DAYOFWEEKTRIGGER.name());
        triggerData.put("day_of_week", "MONDAY");

        TriggerInterface trigger = TriggerFactory.createTrigger(triggerData);
        assertTrue(trigger instanceof DayOfWeekTrigger);
    }

    @Test
    public void testCreateDayOfMonthTrigger() {
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("type", TriggerEnum.DAYOFMONTHTRIGGER.name());
        triggerData.put("day_of_month", "15");

        TriggerInterface trigger = TriggerFactory.createTrigger(triggerData);
        assertTrue(trigger instanceof DayOfMonthTrigger);
    }

    @Test
    public void testCreateDateTrigger() {
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("type", TriggerEnum.DATETRIGGER.name());
        triggerData.put("date", "01/01/2023");

        TriggerInterface trigger = TriggerFactory.createTrigger(triggerData);
        assertTrue(trigger instanceof DateTrigger);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalidTrigger() {
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("type", "INVALID_TYPE");

        TriggerFactory.createTrigger(triggerData);
    }
}