package it.unisa.diem.se.automationapp.action.actioncreation;

import it.unisa.diem.se.automationapp.action.ActionInterface;
import it.unisa.diem.se.automationapp.action.StringAction;
import java.util.Map;

/**
 * The StringActionCreation class implements the ActionCreationStrategy interface
 * to create an instance of StringAction based on provided action data.
 */
public class StringActionCreation implements ActionCreationStrategy {

    /**
     * Default constructor for StringActionCreation.
     */
    public StringActionCreation() {
    }

    /**
     * Creates an instance of StringAction based on the provided action data.
     *
     * @param actionData A map containing key-value pairs of data required for creating a StringAction.
     * @return An instance of StringAction configured with the string content and file path obtained from actionData.
     */
    @Override
    public ActionInterface createAction(Map<String, String> actionData) {
        String string = actionData.get("string");
        String filePath = actionData.get("stringFilePath");

        return new StringAction(string, filePath);
    }
}