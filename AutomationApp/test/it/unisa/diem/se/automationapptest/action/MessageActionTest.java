package it.unisa.diem.se.automationapptest.action;

import it.unisa.diem.se.automationapp.action.MessageAction;
import it.unisa.diem.se.automationapp.eventsmanagement.EventBus;
import it.unisa.diem.se.automationapp.event.MessageEvent;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.junit.Assert.*;

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
        Map<String, String> actionData = new HashMap<>();
        actionData.put("message", testMessage);
        MessageAction action = new MessageAction(actionData);

        try {
            action.execute();
        } catch (Exception e) {
            fail("No exception should be thrown during execution");
        }

        assertTrue("A message event should be published", eventPublished.get());
        assertEquals("Published message should match", testMessage, publishedMessage);
    }
    
    @Test
    public void testGetType() {
        Map<String, String> actionData = new HashMap<>();
        actionData.put("message", "Test message");

        MessageAction messageAction = new MessageAction(actionData);
        assertEquals("Il tipo di azione dovrebbe essere MESSAGEACTION", "MESSAGEACTION", messageAction.getType());
    }
}
