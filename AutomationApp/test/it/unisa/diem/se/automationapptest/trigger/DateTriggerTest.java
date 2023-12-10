package it.unisa.diem.se.automationapptest.trigger;

import it.unisa.diem.se.automationapp.trigger.DateTrigger;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTriggerTest {

    private DateTrigger dateTrigger;
    private LocalDate currentDate;

    @Before
    public void setUp() {
        currentDate = LocalDate.now();
        dateTrigger = new DateTrigger(currentDate);
    }

    @Test
    public void testConstructorAndGetter() {
        assertEquals("The date should match the one set in constructor", currentDate, dateTrigger.getDate());
    }

    @Test
    public void testSetDate() {
        LocalDate newDate = LocalDate.of(2023, 1, 1);
        dateTrigger.setDate(newDate);
        assertEquals("The date should be updated", newDate, dateTrigger.getDate());
    }

    @Test
    public void testIsTriggered() {
        assertTrue("The trigger should be activated on the current date", dateTrigger.isTriggered());

        // Test with a future date
        LocalDate futureDate = currentDate.plusDays(1);
        dateTrigger.setDate(futureDate);
        assertFalse("The trigger should not be activated on a future date", dateTrigger.isTriggered());
    }
    
    @Test
    public void testIsTriggeredWithPastDate() {
        LocalDate pastDate = currentDate.minusDays(1);
        dateTrigger.setDate(pastDate);
        assertFalse("The trigger should not be activated on a past date", dateTrigger.isTriggered());
    }

    @Test
    public void testIsTriggeredWithNullDate() {
        dateTrigger.setDate(null);
        assertFalse("The trigger should not be activated with a null date", dateTrigger.isTriggered());
    }
    
    @Test
    public void testTriggerOnBoundaryDates() {
        LocalDate startOfYear = LocalDate.of(currentDate.getYear(), 1, 1);
        LocalDate endOfYear = LocalDate.of(currentDate.getYear(), 12, 31);

        dateTrigger.setDate(startOfYear);
        assertEquals("Trigger should activate on the first day of the year", startOfYear, dateTrigger.getDate());

        dateTrigger.setDate(endOfYear);
        assertEquals("Trigger should activate on the last day of the year", endOfYear, dateTrigger.getDate());
    }

    @Test(expected = DateTimeParseException.class)
    public void testWithInvalidDate() {
        dateTrigger.setDate(LocalDate.parse("invalid-date"));
    }

    @Test
    public void testTriggerOnLeapYear() {
        LocalDate leapDay = LocalDate.of(2024, 2, 29);
        dateTrigger.setDate(leapDay);
        assertEquals("Trigger should activate on leap day in a leap year", leapDay, dateTrigger.getDate());
    }
}
