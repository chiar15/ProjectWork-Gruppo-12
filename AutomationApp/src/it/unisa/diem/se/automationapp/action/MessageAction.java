package it.unisa.diem.se.automationapp.action;

import it.unisa.diem.se.automationapp.action.exception.InvalidInputException;
import it.unisa.diem.se.automationapp.eventsmanagement.EventBus;
import it.unisa.diem.se.automationapp.event.MessageEvent;

/**
 * The MessageAction class represents an action that publishes a message event to the event bus.
 * It implements the ActionInterface and provides functionality to execute the message action.
 */
public class MessageAction implements ActionInterface {
    private String message;

    /**
     * Default constructor for MessageAction.
     */
    public MessageAction() {
    }

    /**
     * Constructor for MessageAction that sets the message content.
     *
     * @param message The content of the message to be displayed.
     */
    public MessageAction(String message) {
        this.message = message;
    }

    /**
     * Retrieves the message content associated with this MessageAction.
     *
     * @return The message content.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message content to be displayed.
     *
     * @param message The content of the message.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Executes the message action by publishing a message event to the event bus.
     *
     * @throws InvalidInputException If the message is null or empty.
     */
    @Override
    public void execute() throws InvalidInputException {
        EventBus eventBus = EventBus.getInstance();

        if (message == null || message.trim().isEmpty()) {
            throw new InvalidInputException("The message to show cannot be empty.");
        }

        eventBus.publish(new MessageEvent(message));
    }

    /**
     * Provides a string representation of the action being performed.
     *
     * @return A string describing the action.
     */
    @Override
    public String toString() {
        return "Visual reminder of the message: " + message;
    }
}