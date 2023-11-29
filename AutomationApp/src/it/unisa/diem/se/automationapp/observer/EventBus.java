package it.unisa.diem.se.automationapp.observer;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public class EventBus {
    private static EventBus instance = null;
    private final ConcurrentHashMap<Class<?>, CopyOnWriteArrayList<Consumer<?>>> subscribers;

    // Costruttore privato per impedire l'istanziazione esterna
    private EventBus() {
        subscribers = new ConcurrentHashMap<>();
    }

    // Metodo statico pubblico per ottenere l'istanza
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

    public <T> void subscribe(Class<T> eventType, Consumer<T> listener) {
        subscribers.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>()).add(listener);
    }

    public <T> void publish(T event) {
        Class<?> eventType = event.getClass();
        if (subscribers.containsKey(eventType)) {
            for (Consumer<?> listener : subscribers.get(eventType)) {
                ((Consumer<T>) listener).accept(event);
            }
        }
    }
    
    public <T> void unsubscribe(Class<T> eventType, Consumer<T> listener) {
        if (subscribers.containsKey(eventType)) {
            subscribers.get(eventType).remove(listener);
        }
    }
      
    public void clearSubscribers(Class<?> eventType) {
        subscribers.remove(eventType);
    }
}
