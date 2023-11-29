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
    public static ActionInterface createAction(Map<String, String> actionData){
        String type = actionData.get("type");
        
        if(type.equalsIgnoreCase(ActionEnum.AUDIOACTION.toString())){
            return new AudioAction(actionData);
        } else{
            throw new IllegalArgumentException("Invalid action type: " + type);
        }
    }
}
