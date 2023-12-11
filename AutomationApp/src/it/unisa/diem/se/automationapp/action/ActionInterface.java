package it.unisa.diem.se.automationapp.action;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * The ActionInterface interface defines a contract for different types of actions in the automation application.
 * It utilizes Jackson annotations for polymorphic deserialization using JSON.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "actionType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = AudioAction.class, name = "audio"),
    @JsonSubTypes.Type(value = MessageAction.class, name = "message"),
    @JsonSubTypes.Type(value = StringAction.class, name = "string"),
    @JsonSubTypes.Type(value = CopyFileAction.class, name = "copyFile"),
    @JsonSubTypes.Type(value = MoveFileAction.class, name = "moveFile"),
    @JsonSubTypes.Type(value = DeleteFileAction.class, name = "deleteFile")
})

public interface ActionInterface {

    /**
     * Executes the action. Classes implementing this interface must provide
     * an implementation for this method.
     *
     * @throws Exception if any error occurs during the execution of the action.
     */
    void execute() throws Exception;
}