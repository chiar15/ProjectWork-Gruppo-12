package it.unisa.diem.se.automationapp.eventsmanagement;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * The EventBus class manages the communication between components in a publish-subscribe manner.
 * It allows subscription to specific event types and their corresponding listeners.
 */
public class EventBus {
    private static EventBus instance = null;
    private final ConcurrentHashMap<Class<?>, CopyOnWriteArrayList<Consumer<?>>> subscribers;

    // Private constructor to prevent external instantiation
    private EventBus() {
        subscribers = new ConcurrentHashMap<>();
    }

    /**
     * Retrieves the singleton instance of the EventBus.
     * @return The singleton instance of the EventBus.
     */
    public static EventBus getInstance() {
        if (instance == null) {
            synchronized (EventBus.class) {
                if (instance == null) {
                    instance = new EventBus();
                }
            }
        }
        return instance;
    }

    /**
     * Subscribes a listener to a specific event type.
     * @param eventType The type of event to which the listener subscribes.
     * @param listener The listener function to be executed when the event occurs.
     * @param <T> The event type.
     */
    public <T> void subscribe(Class<T> eventType, Consumer<T> listener) {
        subscribers.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>()).add(listener);
    }

    /**
     * Publishes an event to all subscribed listeners of its type.
     * @param event The event to be published.
     * @param <T> The event type.
     */
    public <T> void publish(T event) {
        Class<?> eventType = event.getClass();
        if (subscribers.containsKey(eventType)) {
            for (Consumer<?> listener : subscribers.get(eventType)) {
                ((Consumer<T>) listener).accept(event);
            }
        }
    }
    
    /**
     * Unsubscribes a listener from a specific event type.
     * @param eventType The type of event from which to unsubscribe the listener.
     * @param listener The listener to be unsubscribed.
     * @param <T> The event type.
     */
    public <T> void unsubscribe(Class<T> eventType, Consumer<T> listener) {
        if (subscribers.containsKey(eventType)) {
            subscribers.get(eventType).remove(listener);
        }
    }
      
    /**
     * Clears all subscribers for a specific event type.
     * @param eventType The type of event for which subscribers need to be cleared.
     */
    public void clearSubscribers(Class<?> eventType) {
        subscribers.remove(eventType);
    }
}