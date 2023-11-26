/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.action;

import java.util.Map;

/**
 *
 * @author chiar
 */
public class ActionFactory {
    public static ActionInterface createAction(String type, Map<String, String> actionData){
        switch(type){
            case "ActionEnum.AUDIOACTION":
            return new AudioAction(actionData);
        default:
            throw new IllegalArgumentException("Invalid action type: " + type);
}
    }
}
