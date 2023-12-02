/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapptest.action;

import it.unisa.diem.se.automationapp.action.AudioAction;
import it.unisa.diem.se.automationapp.action.MessageAction;
import it.unisa.diem.se.automationapp.observer.EventBus;
import it.unisa.diem.se.automationapp.observer.MessageEvent;
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
        String projectDirectory = System.getProperty("user.dir");
        actionData.put("filePath", projectDirectory + "\\test\\it\\unisa\\diem\\se\\automationapptest\\action\\data\\song01.wav");

        MessageAction messageAction = new MessageAction(actionData);
        assertEquals("Il tipo di azione dovrebbe essere AUDIOACTION", "AUDIOACTION", messageAction.getType());
    }
}
