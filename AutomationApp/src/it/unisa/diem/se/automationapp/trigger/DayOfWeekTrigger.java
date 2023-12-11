package it.unisa.diem.se.automationapp.trigger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * The DayOfWeekTrigger class implements the TriggerInterface to define a trigger based on a specific day of the week.
 */
public class DayOfWeekTrigger implements TriggerInterface {
    private String dayOfWeek;

    /**
     * Default constructor for DayOfWeekTrigger.
     */
    public DayOfWeekTrigger() {
    }

    /**
     * Constructor for DayOfWeekTrigger with a specified day of the week.
     *
     * @param dayOfWeek The day of the week to set as a trigger (e.g., "MONDAY").
     */
    public DayOfWeekTrigger(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Get the day of the week associated with the DayOfWeekTrigger.
     *
     * @return The day of the week set as a trigger.
     */
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    /**
     * Set the day of the week associated with the DayOfWeekTrigger.
     *
     * @param dayOfWeek The day of the week to set as a trigger (e.g., "MONDAY").
     */
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    /**
     * Check if the trigger is activated based on the current day of the week.
     *
     * @return True if the current day of the week matches the trigger day, otherwise false.
     */
    @JsonIgnore
    @Override
    public boolean isTriggered() {
        if (dayOfWeek == null) {
            return false;
        }

        DayOfWeek currentDay = LocalDate.now().getDayOfWeek();
        return currentDay.name().equalsIgnoreCase(dayOfWeek);
    }

    /**
     * Get a string representation of the trigger day of the week.
     *
     * @return A string representing the day of the week set as a trigger.
     */
    @Override
    public String toString() {
        return dayOfWeek;
    }
}