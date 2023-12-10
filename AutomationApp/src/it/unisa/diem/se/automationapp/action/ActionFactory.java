/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package it.unisa.diem.se.automationapp.action;

import it.unisa.diem.se.automationapp.action.actioncreation.ActionCreationStrategy;
import it.unisa.diem.se.automationapp.action.actioncreation.AudioActionCreation;
import it.unisa.diem.se.automationapp.action.actioncreation.CopyFileActionCreation;
import it.unisa.diem.se.automationapp.action.actioncreation.DeleteFileActionCreation;
import it.unisa.diem.se.automationapp.action.actioncreation.MessageActionCreation;
import it.unisa.diem.se.automationapp.action.actioncreation.MoveFileActionCreation;
import it.unisa.diem.se.automationapp.action.actioncreation.StringActionCreation;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author chiar
 */
public class ActionFactory {
    private static Map<String, ActionCreationStrategy> strategies = new HashMap<>();

    static {
        strategies.put(ActionType.AUDIO.toString(), new AudioActionCreation());
        strategies.put(ActionType.MESSAGE.toString(), new MessageActionCreation());
        strategies.put(ActionType.COPYFILE.toString(), new CopyFileActionCreation());
        strategies.put(ActionType.MOVEFILE.toString(), new MoveFileActionCreation());
        strategies.put(ActionType.DELETEFILE.toString(), new DeleteFileActionCreation());
        strategies.put(ActionType.STRING.toString(), new StringActionCreation());
        
    }

    public static ActionInterface createAction(Map<String, String> actionData) {
        String type = actionData.get("type");
        ActionCreationStrategy strategy = strategies.get(type);

        if (strategy != null) {
            return strategy.createAction(actionData);
        } else {
            throw new IllegalArgumentException("Invalid action type: " + type);
        }
    }
}