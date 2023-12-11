package it.unisa.diem.se.automationapp.trigger;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The DateTrigger class implements the TriggerInterface to define a trigger based on a specific date.
 */
public class DateTrigger implements TriggerInterface {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;

    /**
     * Default constructor for DateTrigger.
     */
    public DateTrigger() {
    }

    /**
     * Constructor for DateTrigger with a specified date.
     *
     * @param date The LocalDate object representing the trigger date.
     */
    public DateTrigger(LocalDate date) {
        this.date = date;
    }

    /**
     * Get the date associated with the DateTrigger.
     *
     * @return The LocalDate object representing the trigger date.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Set the date associated with the DateTrigger.
     *
     * @param date The LocalDate object representing the trigger date.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Check if the trigger is activated based on the current date.
     *
     * @return True if the current date matches the trigger date, otherwise false.
     */
    @JsonIgnore
    @Override
    public boolean isTriggered() {
        if (date == null) {
            return false;
        }

        LocalDate now = LocalDate.now();
        return now.isEqual(date);
    }

    /**
     * Get a string representation of the DateTrigger object in the "dd/MM/yyyy" format.
     *
     * @return A string representing the trigger date in the "dd/MM/yyyy" format.
     */
    @Override
    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dtf.format(date);
    }
}