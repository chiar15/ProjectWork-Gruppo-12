package it.unisa.diem.se.automationapptest.event;

import it.unisa.diem.se.automationapp.event.FileEvent;
import org.junit.Test;
import static org.junit.Assert.*;

public class FileEventTest {

    @Test
    public void testConstructorAndAccessor() {
        String testMessage = "Test file event message";
        FileEvent fileEvent = new FileEvent(testMessage);

        assertEquals("The message should match the one set in constructor", testMessage, fileEvent.getMessage());
    }

    @Test
    public void testSetter() {
        String initialMessage = "Initial message";
        FileEvent fileEvent = new FileEvent(initialMessage);

        String newMessage = "New message";
        fileEvent.setMessage(newMessage);

        assertEquals("The message should be updated", newMessage, fileEvent.getMessage());
    }

    @Test
    public void testWithNullValue() {
        FileEvent fileEvent = new FileEvent(null);

        assertNull("The message should be null", fileEvent.getMessage());
    }

    @Test
    public void testWithEmptyString() {
        FileEvent fileEvent = new FileEvent("");

        assertEquals("The message should be an empty string", "", fileEvent.getMessage());
    }
}