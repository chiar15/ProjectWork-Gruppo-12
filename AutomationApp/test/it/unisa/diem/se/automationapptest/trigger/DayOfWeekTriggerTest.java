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
        currentDayOfWeek = LocalDate.now().getDayOfWeek().name();
        dayOfWeekTrigger = new DayOfWeekTrigger(currentDayOfWeek);
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
    public void testTriggerForAllDaysOfWeek() {
        for (DayOfWeek day : DayOfWeek.values()) {
            dayOfWeekTrigger.setDayOfWeek(day.name());
            assertEquals(day.name(), dayOfWeekTrigger.getDayOfWeek());
            // Mockare LocalDate.now() per restituire il giorno corrispondente
            boolean expected = day.name().equalsIgnoreCase(currentDayOfWeek);
            assertEquals("The trigger should match the expected activation for " + day.name(), expected, dayOfWeekTrigger.isTriggered());
        }
    }

    @Test
    public void testTriggerWithNullAndEmpty() {
        dayOfWeekTrigger.setDayOfWeek(null);
        assertFalse("The trigger should not activate with null", dayOfWeekTrigger.isTriggered());

        dayOfWeekTrigger.setDayOfWeek("");
        assertFalse("The trigger should not activate with an empty string", dayOfWeekTrigger.isTriggered());
    }

    @Test
    public void testTriggerWithInvalidDay() {
        dayOfWeekTrigger.setDayOfWeek("Funday");
        assertFalse("The trigger should not activate with a non-existent day", dayOfWeekTrigger.isTriggered());
    }

    @Test
    public void testCaseSensitivity() {
        dayOfWeekTrigger.setDayOfWeek(currentDayOfWeek.toLowerCase());
        assertTrue("The trigger should be case insensitive", dayOfWeekTrigger.isTriggered());

        dayOfWeekTrigger.setDayOfWeek(currentDayOfWeek.toUpperCase());
        assertTrue("The trigger should be case insensitive", dayOfWeekTrigger.isTriggered());
    }

}
