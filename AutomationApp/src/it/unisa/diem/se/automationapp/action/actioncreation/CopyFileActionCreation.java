package it.unisa.diem.se.automationapp.action.actioncreation;

import it.unisa.diem.se.automationapp.action.ActionInterface;
import it.unisa.diem.se.automationapp.action.CopyFileAction;
import java.util.Map;

/**
 * The CopyFileActionCreation class implements the ActionCreationStrategy interface
 * to create an instance of CopyFileAction based on provided action data.
 */
public class CopyFileActionCreation implements ActionCreationStrategy {

    /**
     * Default constructor for CopyFileActionCreation.
     */
    public CopyFileActionCreation() {
    }

    /**
     * Creates an instance of CopyFileAction based on the provided action data.
     *
     * @param actionData A map containing key-value pairs of data required for creating a CopyFileAction.
     * @return An instance of CopyFileAction configured with the source file path and destination folder obtained from actionData.
     */
    @Override
    public ActionInterface createAction(Map<String, String> actionData) {
        String sourceFile = actionData.get("copySourcePath");
        String destinationFolder = actionData.get("copyDestPath");

        return new CopyFileAction(sourceFile, destinationFolder);
    }
}