/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.action.actioncreation;

import it.unisa.diem.se.automationapp.action.ActionInterface;
import it.unisa.diem.se.automationapp.action.StringAction;
import java.util.Map;

/**
 *
 * @author chiar
 */
public class StringActionCreation implements ActionCreationStrategy{

    public StringActionCreation() {
    }

    @Override
    public ActionInterface createAction(Map<String, String> actionData) {
        String string = actionData.get("string");
        String filePath = actionData.get("stringFilePath");
        
        return new StringAction(string, filePath);
    }
    
}
