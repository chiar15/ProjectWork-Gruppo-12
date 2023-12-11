package it.unisa.diem.se.automationapp.action.actioncreation;

import it.unisa.diem.se.automationapp.action.ActionInterface;
import it.unisa.diem.se.automationapp.action.MoveFileAction;
import java.util.Map;

/**
 * The MoveFileActionCreation class implements the ActionCreationStrategy interface
 * to create an instance of MoveFileAction based on provided action data.
 */
public class MoveFileActionCreation implements ActionCreationStrategy {

    /**
     * Default constructor for MoveFileActionCreation.
     */
    public MoveFileActionCreation() {
    }

    /**
     * Creates an instance of MoveFileAction based on the provided action data.
     *
     * @param actionData A map containing key-value pairs of data required for creating a MoveFileAction.
     * @return An instance of MoveFileAction configured with the source file path and destination folder obtained from actionData.
     */
    @Override
    public ActionInterface createAction(Map<String, String> actionData) {
        String sourceFile = actionData.get("moveSourcePath");
        String destinationFolder = actionData.get("moveDestPath");

        return new MoveFileAction(sourceFile, destinationFolder);
    }
}