/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * The EventInterface represents the interface for different types of events in the application.
 * It defines a method to retrieve the message associated with the event.
 * Implementing classes should provide concrete implementations for this interface.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = AudioEvent.class, name = "audio"),
    @JsonSubTypes.Type(value = ErrorEvent.class, name = "error"),
    @JsonSubTypes.Type(value = MessageEvent.class, name = "message"),
    @JsonSubTypes.Type(value = CloseEvent.class, name = "close"),
    @JsonSubTypes.Type(value = SceneEvent.class, name = "scene"),
    @JsonSubTypes.Type(value = CreationEvent.class, name = "creation"),
    @JsonSubTypes.Type(value = ActiveEvent.class, name = "active"),
    @JsonSubTypes.Type(value = FileEvent.class, name = "file")
})
public interface EventInterface {
    /**
     * Retrieves the message associated with the event.
     * @return The message of the event.
     */
    String getMessage();
}