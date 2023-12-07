/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.event;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = AudioEvent.class, name = "audio"),
    @JsonSubTypes.Type(value = ErrorEvent.class, name = "error"),
    @JsonSubTypes.Type(value = MessageEvent.class, name = "message"),
    @JsonSubTypes.Type(value = SaveEvent.class, name = "save"),
    @JsonSubTypes.Type(value = SceneEvent.class, name = "scene"),
    @JsonSubTypes.Type(value = CreationEvent.class, name = "creation"),
    @JsonSubTypes.Type(value = CreationEvent.class, name = "active")
})

public interface EventInterface {
    String getMessage();
}
