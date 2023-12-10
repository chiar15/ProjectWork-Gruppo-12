package it.unisa.diem.se.automationapptest.action;

import it.unisa.diem.se.automationapp.action.MessageAction;
import it.unisa.diem.se.automationapp.action.exception.InvalidInputException;
import it.unisa.diem.se.automationapp.eventsmanagement.EventBus;
import it.unisa.diem.se.automationapp.event.MessageEvent;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class MessageActionTest {

    private AtomicBoolean eventPublished;
    private String publishedMessage;

    @Before
    public void setUp() {
        eventPublished = new AtomicBoolean(false);
        publishedMessage = null;

        EventBus.getInstance().subscribe(MessageEvent.class, event -> {
            eventPublished.set(true);
            publishedMessage = event.getMessage();
        });
    }

    @Test
    public void testExecutePublishesMessage() {
        String testMessage = "Test message";
        MessageAction action = new MessageAction(testMessage); // Aggiornato per utilizzare il nuovo costruttore

        try {
            action.execute();
        } catch (Exception e) {
            fail("Non dovrebbe essere lanciata alcuna eccezione durante l'esecuzione");
        }

        assertTrue("Un evento messaggio dovrebbe essere pubblicato", eventPublished.get());
        assertEquals("Il messaggio pubblicato dovrebbe corrispondere", testMessage, publishedMessage);
    }
    
    @Test(expected = InvalidInputException.class)
    public void testExecuteWithEmptyMessage() throws InvalidInputException {
        MessageAction action = new MessageAction("");
        action.execute();
    }

    @Test(expected = InvalidInputException.class)
    public void testExecuteWithNullMessage() throws InvalidInputException {
        MessageAction action = new MessageAction(null);
        action.execute();
    }

}
