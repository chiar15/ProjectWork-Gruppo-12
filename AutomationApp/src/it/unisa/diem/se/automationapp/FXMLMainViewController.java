/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package it.unisa.diem.se.automationapp;

import it.unisa.diem.se.automationapp.observer.ErrorEvent;
import it.unisa.diem.se.automationapp.observer.EventBus;
import it.unisa.diem.se.automationapp.observer.EventType;
import it.unisa.diem.se.automationapp.observer.RuleCreationListener;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleEngine;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleService;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
    private ScheduledExecutorService executorService;
    private RuleEngine ruleEngine;
    
    private boolean isRuleCreationOpen;
    
    private ObservableList <Rule> observableList;
    
    private Queue<ErrorEvent> errorEventQueue;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        eventBus = new EventBus();
        observableList = FXCollections.observableArrayList();
        errorEventQueue = new LinkedList<>();
        isRuleCreationOpen = false;
        
        ruleNameClm.setCellValueFactory(new PropertyValueFactory("name"));
        ruleTriggerClm.setCellValueFactory(new PropertyValueFactory("trigger"));
        ruleActionClm.setCellValueFactory(new PropertyValueFactory("action"));
        
        ruleListTable.setItems(observableList);
        
        eventBus.subscribe(ErrorEvent.class, this::onErrorEvent);
        startRuleEngine();
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
            stage.setTitle("Rule Creation");
            FXMLCreationViewController controller = loader.getController();
            controller.initialize();
            controller.setRuleCreationListener(this);
            stage.setResizable(false);
            stage.show();
            isRuleCreationOpen = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }
    
    @Override
    public void onRuleCreated(Rule rule) {
        //popolare observablelist
        observableList.add(rule);
        isRuleCreationOpen = false;
        processQueuedPopups();
    }
        
    private void onErrorEvent(ErrorEvent event) {
        if (isRuleCreationOpen) {
            errorEventQueue.add(event);
        } else{
            showAlert(event);
        }
    }
    
    private void processQueuedPopups() {
        while (!errorEventQueue.isEmpty()) {
            showAlert(errorEventQueue.poll());
        }
    }


    private void showAlert(ErrorEvent event) {
        Alert alert = new Alert(Alert.AlertType.ERROR, event.getErrorMessage(), ButtonType.OK);
        alert.setTitle("Error");
        alert.showAndWait();
        if (event.getType() == EventType.CRITICAL_ERROR) {
            closeApplication();
        }
    }
    
    public void startRuleEngine() {
        ruleEngine = new RuleEngine(eventBus);

        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(ruleEngine::executeRules, 0, 5, TimeUnit.SECONDS);  // Eseguito ogni 5 secondi
    }

    private void closeApplication() {
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
        Platform.exit();
    }
}
