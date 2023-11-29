/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package it.unisa.diem.se.automationapp;

import it.unisa.diem.se.automationapp.observer.MessageEvent;
import it.unisa.diem.se.automationapp.observer.EventBus;
import it.unisa.diem.se.automationapp.observer.MessageEventType;
import it.unisa.diem.se.automationapp.observer.RuleCreationListener;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleChecker;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleExecutor;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleManager;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
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
    
    private RuleManager ruleManager;
    
    private boolean isRuleCreationOpen;
    
    private ObservableList <Rule> observableList;
    
    private Queue<MessageEvent> messageEventQueue;
    
    private boolean isPopupDisplayed;
    
    @FXML
    private Button deleteRuleButton;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        eventBus = EventBus.getInstance();
        observableList = FXCollections.observableArrayList();
        messageEventQueue = new LinkedList<>();
        isRuleCreationOpen = false;
        isPopupDisplayed = false;
        
        ruleNameClm.setCellValueFactory(new PropertyValueFactory("name"));
        ruleTriggerClm.setCellValueFactory(new PropertyValueFactory("trigger"));
        ruleActionClm.setCellValueFactory(new PropertyValueFactory("action"));
        
        ruleListTable.setItems(observableList);
        deleteRuleButton.disableProperty().bind(
            ruleListTable.getSelectionModel().selectedItemProperty().isNull()
        );
        
        eventBus.subscribe(MessageEvent.class, this::onMessageEvent);
        startRuleChecker();
        startRuleExecutor();
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
            observableList.remove(selectedRule);
            ruleManager.deleteRule(selectedRule);
        }
    }
    
    @Override
    public void onRuleCreated(Rule rule) {
        observableList.add(rule);
        isRuleCreationOpen = false;
        processQueuedPopups();
    }
    
    private void onMessageEvent(MessageEvent event) {
        if (isRuleCreationOpen || isPopupDisplayed) {
            messageEventQueue.add(event);
        } else{
            showAlert(event);
        }
    }
    
    private void processQueuedPopups() {
        while (!messageEventQueue.isEmpty()) {
            showAlert(messageEventQueue.poll());
        }
    }


    private void showAlert(MessageEvent event) {
        Platform.runLater(() -> {
            isPopupDisplayed = true;
            if(event.getType() == MessageEventType.MESSAGE){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, event.getMessage(), ButtonType.OK);
                alert.setTitle("Message");
                alert.showAndWait();
            } else{
                Alert alert = new Alert(Alert.AlertType.ERROR, event.getMessage(), ButtonType.OK);
                alert.setTitle("Error");
                alert.showAndWait();
                if (event.getType() == MessageEventType.CRITICAL_ERROR) {
                    closeApplication();
                }
            }
            isPopupDisplayed = false;
        });
        
    }
    
    public void startRuleChecker() {
        ruleChecker = new RuleChecker();

        ruleChecker.setPeriod(Duration.seconds(10));
        ruleChecker.start();
        
    }
    
    public void startRuleExecutor(){
        ruleExecutor = new RuleExecutor();
        
        ruleExecutor.setPeriod(Duration.millis(500));
        ruleExecutor.start();
    }
    
    public void closeApplication(){
        Stage stage = (Stage) addRuleButton.getScene().getWindow();
        stage.close();
    }
}
