package it.unisa.diem.se.automationapp.action.actioncreation;

import it.unisa.diem.se.automationapp.action.ActionInterface;
import it.unisa.diem.se.automationapp.action.MessageAction;
import java.util.Map;

/**
 * The MessageActionCreation class implements the ActionCreationStrategy interface
 * to create an instance of MessageAction based on provided action data.
 */
public class MessageActionCreation implements ActionCreationStrategy {

    /**
     * Default constructor for MessageActionCreation.
     */
    public MessageActionCreation() {
    }

    /**
     * Creates an instance of MessageAction based on the provided action data.
     *
     * @param actionData A map containing key-value pairs of data required for creating a MessageAction.
     * @return An instance of MessageAction configured with the message obtained from actionData.
     */
    @Override
    public ActionInterface createAction(Map<String, String> actionData) {
        String message = actionData.get("message");

        return new MessageAction(message);
    }
}