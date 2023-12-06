/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package it.unisa.diem.se.automationapp;

import it.unisa.diem.se.automationapp.event.AudioEvent;
import it.unisa.diem.se.automationapp.event.AudioEventType;
import it.unisa.diem.se.automationapp.event.ErrorEvent;
import it.unisa.diem.se.automationapp.event.MessageEvent;
import it.unisa.diem.se.automationapp.observer.EventBus;
import it.unisa.diem.se.automationapp.event.ErrorEventType;
import it.unisa.diem.se.automationapp.event.EventInterface;
import it.unisa.diem.se.automationapp.event.EventPersistence;
import it.unisa.diem.se.automationapp.observer.RuleCreationListener;
import it.unisa.diem.se.automationapp.event.SaveEvent;
import it.unisa.diem.se.automationapp.event.SceneEvent;
import it.unisa.diem.se.automationapp.event.SceneEventType;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleChecker;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleExecutor;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleManager;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleSaver;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.application.Platform;
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
 *
 * @author agost
 */
public class FXMLMainViewController implements Initializable, RuleCreationListener {

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
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
        
        loadRulesFromFile();
        
        ruleListTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        
        ruleNameClm.setCellValueFactory(new PropertyValueFactory("name"));
        ruleTriggerClm.setCellValueFactory(new PropertyValueFactory("trigger"));
        ruleActionClm.setCellValueFactory(new PropertyValueFactory("action"));
        
        setupRuleStateColumn();
        
        deleteRuleButton.disableProperty().bind(
            ruleListTable.getSelectionModel().selectedItemProperty().isNull()
        );
        
        eventBus.subscribe(MessageEvent.class, this::onEvent);
        eventBus.subscribe(ErrorEvent.class, this::onEvent);
        eventBus.subscribe(SaveEvent.class, this::onSaveEvent);
        eventBus.subscribe(AudioEvent.class,this::onAudioEvent );
       
        loadEventsFromFile();

        Platform.runLater(() -> {
            processQueuedPopups();
        });
        
        startRuleChecker();
        startRuleExecutor();
        startRuleSaver();
        
    }    

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
            controller.setRuleCreationListener(this);
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
            e.printStackTrace();
        }
        }
    }
    
    @FXML
    private void deleteRuleButtonAction(ActionEvent event) {
        ObservableList<Rule> selectedRules = ruleListTable.getSelectionModel().getSelectedItems();

        if (!selectedRules.isEmpty()) {
            Platform.runLater(() -> {
                Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete the selected rules?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                    // Creare una copia della lista degli elementi selezionati per evitare ConcurrentModificationException
                    List<Rule> rulesToRemove = new ArrayList<>(selectedRules);

                    for (Rule rule : rulesToRemove) {
                        observableList.remove(rule);
                        ruleManager.deleteRule(rule);
                    }
                }
            });
        }
    }
    
    @Override
    public void onRuleCreated(Rule rule) {
        observableList.add(rule);
        isRuleCreationOpen = false;
        eventBus.publish(new SceneEvent("Free scene", SceneEventType.FREE));
        processQueuedPopups();
    }
    
    //cambiare nome per nuovi eventi
    private void onEvent(EventInterface event) {
        if (isRuleCreationOpen || isPopupDisplayed || isAudioPlaying) {
            eventQueue.add(event);
        } else{
            this.checkEvent(event);
        }
    }
    
    private void checkEvent(EventInterface event){
        if(event instanceof MessageEvent){
            showEventAlert(AlertType.INFORMATION, event.getMessage(), "Message");
        } else {
            showEventAlert(AlertType.ERROR, event.getMessage(), "Error");
            ErrorEvent errorEvent = (ErrorEvent) event;
            if(errorEvent.getEventType() == ErrorEventType.CRITICAL){
                isClosingCritical = true;
                this.closeApplication();
            }
        }
        processQueuedPopups();
    }
    
    
    private void processQueuedPopups() {
        EventInterface event;
        if (!eventQueue.isEmpty()) {
            event = eventQueue.poll();
            this.checkEvent(event);
        }
    }

    private void onSaveEvent(SaveEvent event){
        String content;
        
        if(isClosingCritical){
            try{
                ruleManager.saveRulesToFile();
            } catch (IOException e){
                showEventAlert(AlertType.ERROR, event.getMessage(), "Error");
            } finally{
                Platform.exit();
                System.exit(0);
            }
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
                    if(!eventQueue.isEmpty()){
                        eventPersistence.saveEventsToFile(eventQueue);
                    }
                    try{
                        ruleManager.saveRulesToFile();
                    } catch (IOException e){
                        showEventAlert(AlertType.ERROR, event.getMessage(), "Error");
                    }finally{
                        Platform.exit();
                        System.exit(0);
                    }
                    
                }
            });
        }
    }
    
    private void showEventAlert(AlertType type, String message, String title) {
        Platform.runLater(() -> {
            isPopupDisplayed = true;
            eventBus.publish(new SceneEvent("Busy scene", SceneEventType.BUSY));
            Alert alert = new Alert(type, message, ButtonType.OK);
            alert.setTitle(title);
            alert.showAndWait();
            isPopupDisplayed = false;
            eventBus.publish(new SceneEvent("Free scene", SceneEventType.FREE));
        });
    }
    
    private void onAudioEvent(AudioEvent event){
        Platform.runLater(() -> {
            if(event.getEventType() == AudioEventType.STARTED){
                isAudioPlaying = true;
                allowCloseAlert = false;
                audioAlert = new Alert(Alert.AlertType.NONE);
                audioAlert.setTitle("Audio Playing");
                audioAlert.setContentText(event.getMessage());

                // Rendi il dialogo non chiudibile dall'utente
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
                    audioAlert = null;
                }
            }
        });
    }
    
    private void startRuleChecker() {
        ruleChecker = new RuleChecker();

        Thread checkerThread = new Thread(ruleChecker);
        checkerThread.setDaemon(true);
        checkerThread.start();  
    }
    
    private void startRuleExecutor(){
        ruleExecutor = new RuleExecutor();
        
        Thread executorThread = new Thread(ruleExecutor);
        executorThread.setDaemon(true);
        executorThread.start();
    }
    
    private void startRuleSaver(){
        ruleSaver = new RuleSaver();
        
        Thread savingThread = new Thread(ruleSaver);
        savingThread.setDaemon(true);
        savingThread.start();
    }
    
    private void loadRulesFromFile(){
        List<Rule> list = ruleManager.loadRulesFromFile();
        if(list != null){
           observableList.addAll(list); 
        }
        
    }
    
    private void loadEventsFromFile(){
        Queue<EventInterface> queue = eventPersistence.loadEventsFromFile();
        if(queue != null){
            eventQueue.addAll(queue);
        }
    }
    
    private void closeApplication(){
        Stage stage = (Stage) addRuleButton.getScene().getWindow();
        stage.close();
    }
    
        private void setupRuleStateColumn() {
        ruleStateClm.setCellValueFactory(new PropertyValueFactory<>("wasExecuted"));
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
                    rule.setWasExecuted(!checkBox.isSelected());
                    setText(checkBox.isSelected() ? "active" : "deactive");
                });


                setGraphic(checkBox);
            }
        });
    }
    
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
}
