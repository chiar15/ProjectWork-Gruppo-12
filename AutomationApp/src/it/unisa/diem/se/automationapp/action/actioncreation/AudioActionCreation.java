package it.unisa.diem.se.automationapp.action.actioncreation;

import it.unisa.diem.se.automationapp.action.ActionInterface;
import it.unisa.diem.se.automationapp.action.AudioAction;
import java.util.Map;

/**
 * The AudioActionCreation class implements the ActionCreationStrategy interface
 * to create an instance of AudioAction based on provided action data.
 */
public class AudioActionCreation implements ActionCreationStrategy {

    /**
     * Default constructor for AudioActionCreation.
     */
    public AudioActionCreation() {
    }

    /**
     * Creates an instance of AudioAction based on the provided action data.
     *
     * @param actionData A map containing key-value pairs of data required for creating an AudioAction.
     * @return An instance of AudioAction configured with the file path obtained from actionData.
     */
    @Override
    public ActionInterface createAction(Map<String, String> actionData) {
        String filePath = actionData.get("filePath");
        return new AudioAction(filePath);
    }
}
