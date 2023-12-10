/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.action.actioncreation;

import it.unisa.diem.se.automationapp.action.ActionInterface;
import it.unisa.diem.se.automationapp.action.CopyFileAction;
import java.util.Map;

/**
 *
 * @author chiar
 */
public class CopyFileActionCreation implements ActionCreationStrategy{

    public CopyFileActionCreation() {
    }

    @Override
    public ActionInterface createAction(Map<String, String> actionData) {
        String sourceFile = actionData.get("copySourcePath");
        String destinationFolder = actionData.get("copyDestPath");
        
        return new CopyFileAction(sourceFile, destinationFolder);
    }
    
}
