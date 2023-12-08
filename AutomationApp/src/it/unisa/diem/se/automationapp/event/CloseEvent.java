/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.event;

import com.fasterxml.jackson.annotation.JsonTypeName;
import it.unisa.diem.se.automationapp.event.EventInterface;

/**
 *
 * @author chiar
 */
@JsonTypeName("close")
public class CloseEvent implements EventInterface{
    private String message;

    public CloseEvent() {
    }

    
    public CloseEvent(String message) {
        this.message = message;
    }
    
    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}