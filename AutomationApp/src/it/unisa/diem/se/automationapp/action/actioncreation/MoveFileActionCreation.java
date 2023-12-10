/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.action.actioncreation;

import it.unisa.diem.se.automationapp.action.ActionInterface;
import it.unisa.diem.se.automationapp.action.MoveFileAction;
import java.util.Map;

/**
 *
 * @author chiar
 */
public class MoveFileActionCreation implements ActionCreationStrategy{

    public MoveFileActionCreation() {
    }

    @Override
    public ActionInterface createAction(Map<String, String> actionData) {
        String sourceFile = actionData.get("moveSourcePath");
        String destinationFolder = actionData.get("moveDestPath");
        
        return new MoveFileAction(sourceFile, destinationFolder);
    }
    
}
