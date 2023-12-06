/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package it.unisa.diem.se.automationapp.action;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "actionType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = AudioAction.class, name = "audio"),
    @JsonSubTypes.Type(value = MessageAction.class, name = "message")
    // Aggiungi altre sottoclassi qui
})

public interface ActionInterface {
    void execute() throws Exception;
    String getType();
}
