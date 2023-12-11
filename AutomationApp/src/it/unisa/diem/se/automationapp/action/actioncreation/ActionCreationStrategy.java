package it.unisa.diem.se.automationapp.action.actioncreation;

import it.unisa.diem.se.automationapp.action.ActionInterface;
import java.util.Map;

/**
 * The ActionCreationStrategy interface defines a strategy pattern for creating actions.
 * Implementing classes should provide specific implementations for creating actions based on action data.
 */
public interface ActionCreationStrategy {

    /**
     * Creates an ActionInterface object based on the provided action data.
     *
     * @param actionData A map containing key-value pairs of data required for creating an action.
     * @return An instance of ActionInterface representing the created action.
     */
    public ActionInterface createAction(Map<String, String> actionData);
}
