/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package it.unisa.diem.se.automationapp.trigger;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "triggerType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = TimeTrigger.class, name = "time"),
    @JsonSubTypes.Type(value = DayOfWeekTrigger.class, name = "dayOfWeek"),
    @JsonSubTypes.Type(value = DayOfMonthTrigger.class, name = "dayOfMonth"),
    @JsonSubTypes.Type(value = DateTrigger.class, name = "date")
})

public interface TriggerInterface {
    boolean isTriggered();
    String getType();
}
