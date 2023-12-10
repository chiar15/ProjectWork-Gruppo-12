package it.unisa.diem.se.automationapptest.eventsmanagement;

import it.unisa.diem.se.automationapp.event.ErrorEvent;
import it.unisa.diem.se.automationapp.eventsmanagement.EventBus;
import it.unisa.diem.se.automationapp.event.MessageEvent;
import it.unisa.diem.se.automationapp.event.ErrorEventType;
import org.junit.Test;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.function.Consumer;
import static org.junit.Assert.assertSame;

public class EventBusTest {

    @Mock
    private Consumer<MessageEvent> mockMessageListener;
    @Mock
    private Consumer<ErrorEvent> mockErrorListener;

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

        eventBus.subscribe(MessageEvent.class, mockMessageListener);
        eventBus.publish(messageEvent);

        verify(mockMessageListener).accept(messageEvent);

        eventBus.unsubscribe(MessageEvent.class, mockMessageListener);
    }
    
    @Test
    public void testPublishEventWithoutSubscribers() {
        ErrorEvent errorEvent = new ErrorEvent("Error event", ErrorEventType.CRITICAL);
        eventBus.publish(errorEvent);

        verifyZeroInteractions(mockErrorListener);
    }

    @Test
    public void testSubscribeAndUnsubscribeDifferentEventTypes() {
        eventBus.subscribe(MessageEvent.class, mockMessageListener);
        eventBus.subscribe(ErrorEvent.class, mockErrorListener);

        eventBus.unsubscribe(MessageEvent.class, mockMessageListener);
        eventBus.unsubscribe(ErrorEvent.class, mockErrorListener);

        String testMessage = "Test event message";
        MessageEvent messageEvent = new MessageEvent(testMessage);
        eventBus.publish(messageEvent);

        ErrorEvent errorEvent = new ErrorEvent("Error event", ErrorEventType.CRITICAL);
        eventBus.publish(errorEvent);

        verifyZeroInteractions(mockMessageListener, mockErrorListener);
    }

    @Test
    public void testClearSubscribers() {
        eventBus.subscribe(MessageEvent.class, mockMessageListener);
        eventBus.clearSubscribers(MessageEvent.class);
        
        String testMessage = "Test event message";
        MessageEvent messageEvent = new MessageEvent(testMessage);
        eventBus.publish(messageEvent);

        verifyZeroInteractions(mockMessageListener);
    }

    @Test
    public void testSingletonInstance() {
        EventBus anotherInstance = EventBus.getInstance();
        assertSame("Both instances should be the same", eventBus, anotherInstance);
    }
}
