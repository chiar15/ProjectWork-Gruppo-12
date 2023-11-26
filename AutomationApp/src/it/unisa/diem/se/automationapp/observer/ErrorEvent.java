/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.observer;

public class ErrorEvent {
    private final String errorMessage;
    private final boolean isCritical;

    public ErrorEvent(String errorMessage, boolean isCritical) {
        this.errorMessage = errorMessage;
        this.isCritical = isCritical;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isCritical() {
        return isCritical;
    }
}

