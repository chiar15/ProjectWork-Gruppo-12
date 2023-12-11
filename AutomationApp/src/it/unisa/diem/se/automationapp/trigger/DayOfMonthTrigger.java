package it.unisa.diem.se.automationapp.trigger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;

/**
 * The DayOfMonthTrigger class implements the TriggerInterface to define a trigger based on a specific day of the month.
 */
public class DayOfMonthTrigger implements TriggerInterface {
    private int dayOfMonth;

    /**
     * Default constructor for DayOfMonthTrigger.
     */
    public DayOfMonthTrigger() {
    }

    /**
     * Constructor for DayOfMonthTrigger with a specified day of the month.
     *
     * @param dayOfMonth The day of the month to set as a trigger.
     */
    public DayOfMonthTrigger(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    /**
     * Get the day of the month associated with the DayOfMonthTrigger.
     *
     * @return The day of the month set as a trigger.
     */
    public int getDayOfMonth() {
        return dayOfMonth;
    }

    /**
     * Set the day of the month associated with the DayOfMonthTrigger.
     *
     * @param dayOfMonth The day of the month to set as a trigger.
     */
    public void setDayOfMonth(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    /**
     * Check if the trigger is activated based on the current day of the month.
     *
     * @return True if the current day of the month matches the trigger day, otherwise false.
     */
    @JsonIgnore
    @Override
    public boolean isTriggered() {
        int currentDay = LocalDate.now().getDayOfMonth();
        return (currentDay == dayOfMonth);
    }

    /**
     * Get a string representation of the trigger day of the month.
     *
     * @return A string representing the day of the month set as a trigger.
     */
    @Override
    public String toString() {
        return Integer.toString(dayOfMonth);
    }
}