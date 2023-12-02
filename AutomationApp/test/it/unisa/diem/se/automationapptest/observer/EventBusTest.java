package it.unisa.diem.se.automationapptest.observer;

import it.unisa.diem.se.automationapp.observer.EventBus;
import it.unisa.diem.se.automationapp.observer.MessageEvent;
import it.unisa.diem.se.automationapp.observer.MessageEventType;
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
        MessageEvent messageEvent = new MessageEvent(testMessage, MessageEventType.ERROR);

        eventBus.subscribe(MessageEvent.class, mockListener);
        eventBus.publish(messageEvent);

        verify(mockListener).accept(messageEvent);

        eventBus.unsubscribe(MessageEvent.class, mockListener);
    }
}
