package it.unisa.diem.se.automationapp.action.actioncreation;

import it.unisa.diem.se.automationapp.action.ActionInterface;
import it.unisa.diem.se.automationapp.action.DeleteFileAction;
import java.util.Map;

/**
 * The DeleteFileActionCreation class implements the ActionCreationStrategy interface
 * to create an instance of DeleteFileAction based on provided action data.
 */
public class DeleteFileActionCreation implements ActionCreationStrategy {

    /**
     * Default constructor for DeleteFileActionCreation.
     */
    public DeleteFileActionCreation() {
    }

    /**
     * Creates an instance of DeleteFileAction based on the provided action data.
     *
     * @param actionData A map containing key-value pairs of data required for creating a DeleteFileAction.
     * @return An instance of DeleteFileAction configured with the file path obtained from actionData.
     */
    @Override
    public ActionInterface createAction(Map<String, String> actionData) {
        String filePath = actionData.get("deleteFilePath");

        return new DeleteFileAction(filePath);
    }
}