package it.unisa.diem.se.automationapptest.eventsmanagement;

import it.unisa.diem.se.automationapp.eventsmanagement.EventBus;
import it.unisa.diem.se.automationapp.event.MessageEvent;
import it.unisa.diem.se.automationapp.event.ErrorEventType;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.function.Consumer;

public class EventBusTest {

    @Mock
    private Consumer<MessageEvent> mockListener;

    private EventBus eventBus;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        eventBus = EventBus.getInstance();
    }

    @Test
    public void testPublishEvent() {
        String testMessage = "Test event message";
        MessageEvent messageEvent = new MessageEvent(testMessage);

        eventBus.subscribe(MessageEvent.class, mockListener);
        eventBus.publish(messageEvent);

        verify(mockListener).accept(messageEvent);

        eventBus.unsubscribe(MessageEvent.class, mockListener);
    }
}
