package it.unisa.diem.se.automationapptest.trigger;

import it.unisa.diem.se.automationapp.trigger.DayOfMonthTrigger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class DayOfMonthTriggerTest {

    private DayOfMonthTrigger dayOfMonthTrigger;
    private int currentDayOfMonth;

    @Before
    public void setUp() {
        currentDayOfMonth = LocalDate.now().getDayOfMonth();
        dayOfMonthTrigger = new DayOfMonthTrigger(currentDayOfMonth);
    }

    @Test
    public void testConstructorAndGetter() {
        assertEquals("The day of the month should match the one set in constructor", currentDayOfMonth, dayOfMonthTrigger.getDayOfMonth());
    }

    @Test
    public void testSetDayOfMonth() {
        int newDayOfMonth = 15; // Assicurati che questo giorno sia diverso dal giorno corrente
        dayOfMonthTrigger.setDayOfMonth(newDayOfMonth);
        assertEquals("The day of the month should be updated", newDayOfMonth, dayOfMonthTrigger.getDayOfMonth());
    }
    
    @Test
    public void testIsTriggered() {
        assertTrue("The trigger should be activated on the current day of the month", dayOfMonthTrigger.isTriggered());

        // Test with a different day of the month
        int differentDayOfMonth = (currentDayOfMonth % 28) + 1; // Assicurati che sia un giorno diverso
        dayOfMonthTrigger.setDayOfMonth(differentDayOfMonth);
        assertFalse("The trigger should not be activated on a different day of the month", dayOfMonthTrigger.isTriggered());
    }
    
    @Test
    public void testTriggerWithInvalidDayOfMonth() {
        dayOfMonthTrigger.setDayOfMonth(32); // Impostare un giorno non valido
        assertFalse("The trigger should not activate with an invalid day of the month", dayOfMonthTrigger.isTriggered());
    }
}
