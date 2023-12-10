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
        triggerData.put("type", TriggerType.TIME.toString());
        triggerData.put("time", "10:00");

        TriggerInterface trigger = TriggerFactory.createTrigger(triggerData);
        assertTrue(trigger instanceof TimeTrigger);
    }

    @Test
    public void testCreateDayOfWeekTrigger() {
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("type", TriggerType.DAYOFWEEK.toString());
        triggerData.put("dayOfWeek", "MONDAY");

        TriggerInterface trigger = TriggerFactory.createTrigger(triggerData);
        assertTrue(trigger instanceof DayOfWeekTrigger);
    }

    @Test
    public void testCreateDayOfMonthTrigger() {
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("type", TriggerType.DAYOFMONTH.toString());
        triggerData.put("dayOfMonth", "15");

        TriggerInterface trigger = TriggerFactory.createTrigger(triggerData);
        assertTrue(trigger instanceof DayOfMonthTrigger);
    }

    @Test
    public void testCreateDateTrigger() {
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("type", TriggerType.DATE.toString());
        triggerData.put("date", "2023-01-01");

        TriggerInterface trigger = TriggerFactory.createTrigger(triggerData);
        assertTrue(trigger instanceof DateTrigger);
    }

    @Test
    public void testCreateFileExistsTrigger() {
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("type", TriggerType.FILEEXISTS.toString());
        triggerData.put("fileName", "testFile.txt");
        triggerData.put("fileDirectory", "path/to/directory");

        TriggerInterface trigger = TriggerFactory.createTrigger(triggerData);
        assertTrue(trigger instanceof FileExistsTrigger);
    }

    @Test
    public void testCreateFileDimensionTrigger() {
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("type", TriggerType.FILEDIMENSION.toString());
        triggerData.put("filePath", "path/to/testFile.txt");
        triggerData.put("dimension", "1024");

        TriggerInterface trigger = TriggerFactory.createTrigger(triggerData);
        assertTrue(trigger instanceof FileDimensionTrigger);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testCreateInvalidTrigger() {
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("type", "INVALID_TYPE");

        TriggerFactory.createTrigger(triggerData);
    }
}