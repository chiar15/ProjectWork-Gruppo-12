/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.action.actioncreation;

import it.unisa.diem.se.automationapp.action.ActionInterface;
import java.util.Map;

/**
 *
 * @author chiar
 */
public interface ActionCreationStrategy {
    public ActionInterface createAction(Map<String, String> actionData);
}
