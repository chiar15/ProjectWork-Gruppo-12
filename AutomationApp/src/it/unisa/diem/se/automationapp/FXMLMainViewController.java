/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package it.unisa.diem.se.automationapp;

import it.unisa.diem.se.automationapp.observer.ErrorEvent;
import it.unisa.diem.se.automationapp.observer.MessageEvent;
import it.unisa.diem.se.automationapp.observer.EventBus;
import it.unisa.diem.se.automationapp.observer.ErrorEventType;
import it.unisa.diem.se.automationapp.observer.EventInterface;
import it.unisa.diem.se.automationapp.observer.RuleCreationListener;
import it.unisa.diem.se.automationapp.observer.SaveEvent;
import it.unisa.diem.se.automationapp.observer.SaveEventType;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleChecker;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleExecutor;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleManager;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleSaver;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    
    private EventBus eventBus;

    private RuleChecker ruleChecker;
    
    private RuleExecutor ruleExecutor;
    
    private RuleSaver ruleSaver;
    
    private RuleManager ruleManager;
    
    private boolean isRuleCreationOpen;
    
    private ObservableList <Rule> observableList;
    
    private Queue<EventInterface> eventQueue;
    
    private boolean isPopupDisplayed;
    
    @FXML
    private Button deleteRuleButton;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        eventBus = EventBus.getInstance();
        ruleManager = RuleManager.getInstance();
        observableList = FXCollections.observableArrayList();
        eventQueue = new LinkedList<>();
        isRuleCreationOpen = false;
        isPopupDisplayed = false;
        
        loadRulesFromFile();
        
        ruleNameClm.setCellValueFactory(new PropertyValueFactory("name"));
        ruleTriggerClm.setCellValueFactory(new PropertyValueFactory("trigger"));
        ruleActionClm.setCellValueFactory(new PropertyValueFactory("action"));
        
        ruleListTable.setItems(observableList);
        deleteRuleButton.disableProperty().bind(
            ruleListTable.getSelectionModel().selectedItemProperty().isNull()
        );
        
        eventBus.subscribe(MessageEvent.class, this::onEvent);
        eventBus.subscribe(ErrorEvent.class, this::onEvent);
        eventBus.subscribe(SaveEvent.class, this::onSaveEvent);

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
                processQueuedPopups();
            });
                        
            stage.show();
            isRuleCreationOpen = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }
    
    @FXML
    private void deleteRuleButtonAction(ActionEvent event) {
        Rule selectedRule = ruleListTable.getSelectionModel().getSelectedItem();
        
        if (selectedRule != null) {
            Platform.runLater(() -> {
                isPopupDisplayed = true;
                Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to delete this rule?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                isPopupDisplayed = false;
                if (alert.getResult() == ButtonType.YES) {
                    observableList.remove(selectedRule);
                    ruleManager.deleteRule(selectedRule);
                }
                processQueuedPopups();
            });
        }
    }
    
    @Override
    public void onRuleCreated(Rule rule) {
        observableList.add(rule);
        isRuleCreationOpen = false;
        processQueuedPopups();
    }
    
    //cambiare nome per nuovi eventi
    private void onEvent(EventInterface event) {
        if (isRuleCreationOpen || isPopupDisplayed) {
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
            if(errorEvent.getType() == ErrorEventType.CRITICAL){
                this.closeApplication();
            }
        }
    }
    
    
    private void processQueuedPopups() {
        EventInterface event;
        if (!eventQueue.isEmpty()) {
            event = eventQueue.poll();
            this.checkEvent(event);
        }
    }

    private void onSaveEvent(SaveEvent event){
        if(event.getType() != SaveEventType.REQUEST){
            if(event.getType() == SaveEventType.FAILURE){
                showEventAlert(AlertType.ERROR, event.getMessage(), "Error");
            }
            Platform.exit();
            System.exit(0);
        }
    }
    
    private void showEventAlert(AlertType type, String message, String title) {
        Platform.runLater(() -> {
            isPopupDisplayed = true;
            Alert alert = new Alert(type, message, ButtonType.OK);
            alert.setTitle(title);
            alert.showAndWait();
            isPopupDisplayed = false;
            processQueuedPopups();
        });
    }
    
    public void startRuleChecker() {
        ruleChecker = new RuleChecker();

        Thread checkerThread = new Thread(ruleChecker);
        checkerThread.setDaemon(true);
        checkerThread.start();  
    }
    
    public void startRuleExecutor(){
        ruleExecutor = new RuleExecutor();
        
        Thread executorThread = new Thread(ruleExecutor);
        executorThread.setDaemon(true);
        executorThread.start();
    }
    
    public void startRuleSaver(){
        ruleSaver = new RuleSaver();
        
        Thread savingThread = new Thread(ruleSaver);
        savingThread.setDaemon(true);
        savingThread.start();
    }
    
    public void loadRulesFromFile(){
        List<Rule> list = ruleManager.loadRulesFromFile();
        observableList.addAll(list);
    }
    
    private void closeApplication(){
        Stage stage = (Stage) addRuleButton.getScene().getWindow();
        stage.close();
    }
}
