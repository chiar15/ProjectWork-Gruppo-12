/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.action.actioncreation;

import it.unisa.diem.se.automationapp.action.ActionInterface;
import it.unisa.diem.se.automationapp.action.AudioAction;
import java.util.Map;

/**
 *
 * @author chiar
 */
public class AudioActionCreation implements ActionCreationStrategy{

    public AudioActionCreation() {
    }

    @Override
    public ActionInterface createAction(Map<String, String> actionData) {
        String filePath = actionData.get("filePath");
        return new AudioAction(filePath);
    }
    
}
