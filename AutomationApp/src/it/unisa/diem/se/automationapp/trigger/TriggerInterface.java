/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * The TriggerInterface is an interface representing a trigger that can be checked for its activation status.
 * It is annotated with Jackson annotations for polymorphic deserialization to support different trigger types.
 * Implementing classes should define the behavior of the isTriggered method based on their specific trigger logic.
 * 
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "triggerType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = TimeTrigger.class, name = "time"),
    @JsonSubTypes.Type(value = DayOfWeekTrigger.class, name = "dayOfWeek"),
    @JsonSubTypes.Type(value = DayOfMonthTrigger.class, name = "dayOfMonth"),
    @JsonSubTypes.Type(value = DateTrigger.class, name = "date"),
    @JsonSubTypes.Type(value = FileExistsTrigger.class, name = "fileExists"),
    @JsonSubTypes.Type(value = FileDimensionTrigger.class, name = "fileDimension")
})
public interface TriggerInterface {
    /**
     * Checks if the trigger is activated based on its specific logic.
     *
     * @return true if the trigger is activated, otherwise false.
     */
    boolean isTriggered();
}
