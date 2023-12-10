/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package it.unisa.diem.se.automationapp;

import it.unisa.diem.se.automationapp.event.ActiveEvent;
import it.unisa.diem.se.automationapp.event.AudioEvent;
import it.unisa.diem.se.automationapp.event.AudioEventType;
import it.unisa.diem.se.automationapp.event.CreationEvent;
import it.unisa.diem.se.automationapp.event.ErrorEvent;
import it.unisa.diem.se.automationapp.event.MessageEvent;
import it.unisa.diem.se.automationapp.eventsmanagement.EventBus;
import it.unisa.diem.se.automationapp.event.ErrorEventType;
import it.unisa.diem.se.automationapp.event.EventInterface;
import it.unisa.diem.se.automationapp.event.FileEvent;
import it.unisa.diem.se.automationapp.eventsmanagement.EventPersistence;
import it.unisa.diem.se.automationapp.event.CloseEvent;
import it.unisa.diem.se.automationapp.event.SceneEvent;
import it.unisa.diem.se.automationapp.event.SceneEventType;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleChecker;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleExecutor;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleManager;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleSaver;
import it.unisa.diem.se.automationapp.rulesmanagement.SuspendedRuleDecorator;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * FXML Controller class
 * Controller managing the main user interface (table view containing the created rules).
 * @author agost
 */
public class FXMLMainViewController implements Initializable{

    @FXML
    private TableView<Rule> ruleListTable;
    @FXML
    private Button addRuleButton;
    @FXML
    private TableColumn<String, String> ruleNameClm;
    @FXML
    private TableColumn<String, String> ruleTriggerClm;
    @FXML
    private TableColumn<String, String> ruleActionClm;
    @FXML
    private Button deleteRuleButton;
    @FXML
    private TableColumn<Rule, Boolean> ruleStateClm;
    @FXML
    private TableColumn<Rule, String> executionClm;
    
    private EventBus eventBus;

    private RuleChecker ruleChecker;
    
    private RuleExecutor ruleExecutor;
    
    private RuleSaver ruleSaver;
    
    private RuleManager ruleManager;
    
    private boolean isRuleCreationOpen;
    
    private ObservableList <Rule> observableList;
    
    private Queue<EventInterface> eventQueue;
    
    private boolean isPopupDisplayed;
    
    private boolean isAudioPlaying;
    
    private Alert audioAlert;
    
    private boolean allowCloseAlert;
    
    private EventPersistence eventPersistence;
    
    private boolean isClosingCritical;
    
    /**
     * Initializes the controller class.
     * @param url The initialization URL (not used)
     * @param rb The initialization resource bundle (not used)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        initializeVariables();
        loadRulesFromFile();
        setupUI();
        configureEventsSubscription();
        loadEventsFromFile();
        startAllThreads();
    }
    
    /**
     * Button action to add a new rule.
     * Opens a window for creating a new rule.
     * @param event The button event (not used)
     */
    @FXML
    private void addRuleButtonAction(ActionEvent event) {
        if(!isRuleCreationOpen){
            try {
                Image icon = new Image(getClass().getResourceAsStream("/icon/icona.png"));
                FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLCreationView.fxml"));
                Parent root = loader.load();

                Stage stage = new Stage();

                stage.getIcons().add(icon);
                stage.setScene(new Scene(root));
                stage.setTitle("Rule Creation Menù");
                FXMLCreationViewController controller = loader.getController();
                controller.initialize();
                stage.setResizable(false);

                stage.setOnCloseRequest(windowEvent -> {
                    isRuleCreationOpen = false;
                    eventBus.publish(new SceneEvent("Free scene", SceneEventType.FREE));
                    processQueuedPopups();
                });


                stage.show();
                isRuleCreationOpen = true;
                eventBus.publish(new SceneEvent("Busy scene", SceneEventType.BUSY));
            } catch (IOException e) {
                showAlert(AlertType.ERROR, "Unable to open the creation window. The application will be terminated.", "Error");
                isClosingCritical = true;
                closeApplication();
            }
        }
    }
    
    /**
     * Button action to delete a selected rule.
     * Deletes one or more selected rules in the rules table.
     * @param event The button event (not used)
     */
    @FXML
    private void deleteRuleButtonAction(ActionEvent event) {
        ObservableList<Rule> selectedRules = ruleListTable.getSelectionModel().getSelectedItems();

        if (!selectedRules.isEmpty()) {
            Platform.runLater(() -> {
                isPopupDisplayed = true;
                eventBus.publish(new SceneEvent("Busy Scene", SceneEventType.BUSY));
                Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete the selected rule/rules?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                isPopupDisplayed = false;
                eventBus.publish(new SceneEvent("Free Scene", SceneEventType.FREE));

                if (alert.getResult() == ButtonType.YES) {
                    List<Rule> rulesToRemove = new ArrayList<>(selectedRules);

                    for (Rule rule : rulesToRemove) {
                        observableList.remove(rule);
                        ruleManager.deleteRule(rule);
                    }
                }
                ruleListTable.refresh();
                processQueuedPopups();
            });
        }
    }
    
    /**
     * Initializes the controller's variables.
     */
    private void initializeVariables() {
        eventBus = EventBus.getInstance();
        ruleManager = RuleManager.getInstance();
        eventPersistence = new EventPersistence();
        observableList = FXCollections.observableArrayList();
        eventQueue = new LinkedList<>();
        isRuleCreationOpen = false;
        isPopupDisplayed = false;
        isAudioPlaying = false;
        allowCloseAlert = false;
        audioAlert = null;
        isClosingCritical = false;
    }
    
    /**
     * Loads rules from file on application startup.
     */
    private void loadRulesFromFile(){
        List<Rule> list = ruleManager.loadRulesFromFile();
        if(list != null){
           observableList.addAll(list); 
        }
    }
    
    /**
     * Sets up the user interface (Table view, table columns, Tooltip, delete button, etc..).
     */
    private void setupUI() {
        setupRuleTable();
        configureInformationRow();
        deleteRuleButtonSetup();
    }
    
    /**
    * Sets up the rule table view, configuring various columns and their properties.
    */
    private void setupRuleTable(){
        ruleListTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ruleNameClm.setCellValueFactory(new PropertyValueFactory("name"));
        ruleTriggerClm.setCellValueFactory(new PropertyValueFactory("trigger"));
        ruleActionClm.setCellValueFactory(new PropertyValueFactory("action"));
        
        ruleListTable.setItems(observableList);
        setupRuleStateColumn();
        setupExecutionColumn();
    }
    
    /**
    * Sets up the execution column of the rule table.
    * Determines the displayed value based on the rule's execution type.
    */
    private void setupExecutionColumn() {
        executionClm.setCellValueFactory(cellData -> {
            Rule rule = cellData.getValue();
            if (rule instanceof SuspendedRuleDecorator) {
                SuspendedRuleDecorator suspendedRule = (SuspendedRuleDecorator) rule;
                return new SimpleStringProperty("Multiple Execution (Suspended for " + suspendedRule.getSuspensionPeriod() + " seconds)");
            } else {
                return new SimpleStringProperty("Single Execution");
            }
        });
    }
    
    /**
    * Sets up the rule state column with checkboxes for activation/deactivation.
    */
    private void setupRuleStateColumn() {
        ruleStateClm.setCellValueFactory(new PropertyValueFactory<>("isActive"));
        ruleStateClm.setCellFactory(column -> new TableCell<Rule, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null);
                    setText(null);
                    return;
                }

                CheckBox checkBox = new CheckBox();
                setText(item ? "active" : "deactive");

                checkBox.setSelected(item);

                Rule rule = getTableView().getItems().get(getIndex());

                checkBox.setOnAction(event -> {
                    ruleManager.changeRuleState(rule, checkBox.isSelected());
                    rule.setIsActive(checkBox.isSelected());
                    setText(checkBox.isSelected() ? "active" : "deactive");
                });


                setGraphic(checkBox);
            }
        });
    }
    
    /**
    * Configures the information display row for the rule list table.
    * Shows tooltip details when hovering over a row with rule information.
    */
    private void configureInformationRow() {
        ruleListTable.setRowFactory(tv -> {
            TableRow<Rule> row = new TableRow<>();
            Tooltip tooltip = new Tooltip();

            row.setOnMouseEntered(event -> {
                Rule rowData = row.getItem();
                if (rowData != null) {
                    StringBuilder details = new StringBuilder();
                    details.append("Name: ").append(rowData.getName()).append("\n");
                    details.append("Trigger: ").append(rowData.getTrigger()).append("\n");
                    details.append("Action: ").append(rowData.getAction()).append("\n");

                    if (rowData instanceof SuspendedRuleDecorator) {
                        SuspendedRuleDecorator suspendedRule = (SuspendedRuleDecorator) rowData;
                        details.append("Execution: Multiple Execution (Suspended for ")
                                .append(suspendedRule.getSimpleSuspensionPeriod() + ")");
                    } else{
                        details.append("Execution: Single Execution\n");
                    }

                    tooltip.setText(details.toString());

                    double mouseX = event.getScreenX();
                    double mouseY = event.getScreenY();

                    tooltip.show(row, mouseX + 10, mouseY + 10);
                }
            });

            row.setOnMouseExited(event -> {
                tooltip.hide();
            });

            return row;
        });
    }
    
    /**
    * Sets up the deletion rule button by enabling/disabling based on selection.
    */
    private void deleteRuleButtonSetup() {
        deleteRuleButton.disableProperty().bind(
            ruleListTable.getSelectionModel().selectedItemProperty().isNull()
        );
    }
    
    /**
    * Configures event subscriptions for different types of events.
    */
    private void configureEventsSubscription(){
        eventBus.subscribe(MessageEvent.class, this::onEvent);
        eventBus.subscribe(ErrorEvent.class, this::onEvent);
        eventBus.subscribe(FileEvent.class, this::onEvent);
        eventBus.subscribe(CloseEvent.class, this::onCloseEvent);
        eventBus.subscribe(AudioEvent.class,this::onAudioEvent );
        eventBus.subscribe(CreationEvent.class,this::onCreationEvent);
        eventBus.subscribe(ActiveEvent.class,this::onActiveEvent);
    }
    
    /**
    * Loads events from a file into the event queue if available.
    */
    private void loadEventsFromFile(){
        Queue<EventInterface> queue = eventPersistence.loadEventsFromFile();
        if(queue != null){
            eventQueue.addAll(queue);
            Platform.runLater(() -> {
                processQueuedPopups();
            });
        }
    }
    
    /**
    * Starts the threads responsible for checking rules, executing rules, and saving rules.
    */
    private void startAllThreads(){
        startRuleChecker();
        startRuleExecutor();
        startRuleSaver();
    }
    
    /**
    * Starts the thread responsible for rule checking.
    */
    private void startRuleChecker() {
        ruleChecker = new RuleChecker();

        Thread checkerThread = new Thread(ruleChecker);
        checkerThread.setDaemon(true);
        checkerThread.start();  
    }
    
    /**
    * Starts the thread responsible for rule execution.
    */
    private void startRuleExecutor(){
        ruleExecutor = new RuleExecutor();
        
        Thread executorThread = new Thread(ruleExecutor);
        executorThread.setDaemon(true);
        executorThread.start();
    }
    
    /**
    * Starts the thread responsible for rule saving.
    */
    private void startRuleSaver(){
        ruleSaver = new RuleSaver();
        
        Thread savingThread = new Thread(ruleSaver);
        savingThread.setDaemon(true);
        savingThread.start();
    }
    
    /**
    * Handles incoming events and processes them accordingly.
    * If certain conditions are met, adds events to the queue for later processing.
    * 
    * @param event The incoming event
    */
    private void onEvent(EventInterface event) {
        if (isRuleCreationOpen || isPopupDisplayed || isAudioPlaying) {
            eventQueue.add(event);
        } else{
            this.checkEvent(event);
        }
    }
    
    /**
    * Checks and processes the incoming event.
    * 
    * @param event The event to be checked and processed
    */
    private void checkEvent(EventInterface event){
        if(event instanceof MessageEvent){
            showAlert(AlertType.INFORMATION, event.getMessage(), "Message");
        } else if(event instanceof FileEvent){
            showAlert(AlertType.INFORMATION, event.getMessage(), "Feedback");
        } else if(event instanceof ErrorEvent){
            showAlert(AlertType.ERROR, event.getMessage(), "Error");
            ErrorEvent errorEvent = (ErrorEvent) event;
            if(errorEvent.getEventType() == ErrorEventType.CRITICAL){
                isClosingCritical = true;
                this.closeApplication();
            }
        }
        processQueuedPopups();
    }
    
    
    /**
    * Processes queued popups by handling events in the event queue.
    */
    private void processQueuedPopups() {
        EventInterface event;
        if (!eventQueue.isEmpty()) {
            event = eventQueue.poll();
            this.checkEvent(event);
        }
    }

    /**
    * Handles the event when the application is about to close.
    * 
    * @param event The close event triggered
    */
    private void onCloseEvent(CloseEvent event){
        String content;
        
        if(isClosingCritical){
            manageClose();
        } else{
            if(!eventQueue.isEmpty()){
               content = "Some rules are ready to be executed, if you close the application now they will be executed the next time you'll open the application. Are you sure you want to close the application?"; 
            } else {
                content = "Are you sure you want to close the application?";
            }

            Platform.runLater(() -> {
                isPopupDisplayed = true;
                eventBus.publish(new SceneEvent("Busy scene", SceneEventType.BUSY));
                Alert alert = new Alert(AlertType.CONFIRMATION, content, ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                isPopupDisplayed = false;
                eventBus.publish(new SceneEvent("Free scene", SceneEventType.FREE));
                if (alert.getResult() == ButtonType.YES) {
                    manageClose(); 
                } else{
                    ruleListTable.refresh();
                    processQueuedPopups();
                }
            });
        }
    }
    
    /**
    * Manages the closing operation of the application by saving rules and events to files,
    * and exits the application.
    */
    private void manageClose(){
        try{
            ruleManager.saveRulesToFile();
            eventPersistence.saveEventsToFile(eventQueue);
        } catch (IOException e){
            showAlert(AlertType.ERROR, "Error while saving, some changes may have not been saved", "Error");
            Platform.exit();
            System.exit(0);
        } finally{
            Platform.exit();
            System.exit(0);
        }
    }
    
    /**
    * Shows an alert dialog box with a specified type, message, and title.
    * Refreshes the rule list table after showing the alert.
    * 
    * @param type    The type of alert to display (e.g., INFORMATION, ERROR)
    * @param message The message content of the alert
    * @param title   The title of the alert dialog
    */
    private void showAlert(AlertType type, String message, String title) {
        Platform.runLater(() -> {
            isPopupDisplayed = true;
            eventBus.publish(new SceneEvent("Busy scene", SceneEventType.BUSY));
            Alert alert = new Alert(type, message, ButtonType.OK);
            alert.setTitle(title);
            alert.showAndWait();
            isPopupDisplayed = false;
            eventBus.publish(new SceneEvent("Free scene", SceneEventType.FREE));
        });
        ruleListTable.refresh();
    }
    
    /**
    * Handles audio events by displaying or closing an audio alert dialog accordingly.
    * Refreshes the rule list table and processes queued popups after handling the event.
    * 
    * @param event The audio event triggered
    */
    private void onAudioEvent(AudioEvent event){
        Platform.runLater(() -> {
            if(event.getEventType() == AudioEventType.STARTED){
                isAudioPlaying = true;
                isPopupDisplayed = true;
                allowCloseAlert = false;
                
                audioAlert = new Alert(Alert.AlertType.NONE);
                audioAlert.setTitle("Audio Playing");
                audioAlert.setContentText(event.getMessage());

                DialogPane dialogPane = audioAlert.getDialogPane();
                dialogPane.getButtonTypes().add(ButtonType.CANCEL);
                ButtonType cancelButtonType = dialogPane.getButtonTypes().get(0);
                dialogPane.lookupButton(cancelButtonType).setVisible(false);
                
                Stage alertStage = (Stage) audioAlert.getDialogPane().getScene().getWindow();
                
                alertStage.setOnCloseRequest(e ->{
                    if(!allowCloseAlert){
                        e.consume();
                    }
                });
                
                audioAlert.show();
            } else{
                isAudioPlaying = false;
                if(audioAlert != null){
                    allowCloseAlert = true;
                    audioAlert.close();
                    isPopupDisplayed = false;
                    audioAlert = null;
                }
                ruleListTable.refresh();
                processQueuedPopups();
            }
        });
    }
    
    /**
    * Handles the active event by updating the status of a rule in the observable list.
    * and refreshes the rule list table if no popup is displayed.
    * 
    * @param event The active event triggered
    */
    private void onActiveEvent(ActiveEvent event){
        int index= observableList.indexOf(event.getRule());
        observableList.get(index).setIsActive(false);
        if(!isPopupDisplayed){
            ruleListTable.refresh();
        }
        
    }
    
    /**
    * Handles the creation event by adding a rule to the observable list,
    * closing the rule creation window, and processing queued popups.
    * 
    * @param event The creation event triggered
    */
    public void onCreationEvent(CreationEvent event) {
        observableList.add(event.getRule());
        isRuleCreationOpen = false;
        eventBus.publish(new SceneEvent("Free scene", SceneEventType.FREE));
        processQueuedPopups();
    }
    
    /**
    * Closes the application by closing the current stage/window.
    */
    private void closeApplication(){
        Stage stage = (Stage) addRuleButton.getScene().getWindow();
        stage.close();
    }
}