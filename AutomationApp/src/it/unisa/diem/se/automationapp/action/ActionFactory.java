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
 * The ActionFactory class implements the Factory design pattern to create various types of actions
 * based on the provided action data.
 * It maintains a collection of strategies to instantiate different types of actions.
 */
public class ActionFactory {

    // A map that associates action types with their respective creation strategies
    private static Map<String, ActionCreationStrategy> strategies = new HashMap<>();

    // Static initialization block to populate the strategies map
    static {
        strategies.put(ActionType.AUDIO.toString(), new AudioActionCreation());
        strategies.put(ActionType.MESSAGE.toString(), new MessageActionCreation());
        strategies.put(ActionType.COPYFILE.toString(), new CopyFileActionCreation());
        strategies.put(ActionType.MOVEFILE.toString(), new MoveFileActionCreation());
        strategies.put(ActionType.DELETEFILE.toString(), new DeleteFileActionCreation());
        strategies.put(ActionType.STRING.toString(), new StringActionCreation());
    }

    /**
     * Creates an action based on the provided action data.
     *
     * @param actionData A Map containing data required for action creation, including the 'type' of action.
     * @return An instance of ActionInterface representing the created action.
     * @throws IllegalArgumentException if the provided action type is invalid or not supported.
     */
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