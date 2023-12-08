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
        
        if(type.equalsIgnoreCase(AudioAction.class.getSimpleName())){
            return new AudioAction(actionData);
        } else if (type.equalsIgnoreCase(MessageAction.class.getSimpleName())){
            return new MessageAction(actionData);
        }   else if (type.equalsIgnoreCase(StringAction.class.getSimpleName())){
            return new StringAction(actionData);
        } else if (type.equalsIgnoreCase(CopyFileAction.class.getSimpleName())){
            return new CopyFileAction(actionData);
        } else if (type.equalsIgnoreCase(MoveFileAction.class.getSimpleName())){
            return new MoveFileAction(actionData);
        } else if (type.equalsIgnoreCase(DeleteFileAction.class.getSimpleName())){
            return new DeleteFileAction(actionData);
        }else{
            throw new IllegalArgumentException("Invalid action type: " + type);
        }
    }
}
