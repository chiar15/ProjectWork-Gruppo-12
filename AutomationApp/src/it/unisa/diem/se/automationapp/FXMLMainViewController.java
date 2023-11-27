/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package it.unisa.diem.se.automationapp;

import it.unisa.diem.se.automationapp.observer.ErrorEvent;
import it.unisa.diem.se.automationapp.observer.EventBus;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleEngine;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleService;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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
public class FXMLMainViewController implements Initializable {

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
    
    private RuleService ruleService;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        eventBus = new EventBus();
        ruleService = new RuleService();
        CopyOnWriteArrayList<Rule> ruleList = ruleService.getRuleList();
        
        ruleNameClm.setCellValueFactory(new PropertyValueFactory("name"));
        ruleTriggerClm.setCellValueFactory(new PropertyValueFactory("trigger"));
        ruleActionClm.setCellValueFactory(new PropertyValueFactory("action"));
        
        ruleListTable.setItems(FXCollections.observableList(ruleList));
        
        eventBus.subscribe(ErrorEvent.class, this::onErrorEvent);
        startRuleEngine();
    }    

    @FXML
    private void addRuleButtonAction(ActionEvent event) {
        try {
            Image icon = new Image(getClass().getResourceAsStream("/icon/icona.png"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLCreationView.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            
            stage.getIcons().add(icon);
            stage.setScene(new Scene(root));
            stage.setTitle("Rule Creation");
            FXMLCreationViewController controller = loader.getController();
            controller.initialize(ruleService);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void onErrorEvent(ErrorEvent event) {
        Platform.runLater(() -> {
            showAlert(event.getErrorMessage());
            if (event.isCritical()) {
                closeApplication();
            }
        });
    }


    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle("Errore");
        alert.showAndWait();
    }
    
    public void startRuleEngine() {
        ruleEngine = new RuleEngine(ruleService, eventBus);

        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(ruleEngine::executeRules, 0, 5, TimeUnit.SECONDS);  // Eseguito ogni 5 secondi
    }

    private void closeApplication() {
        if (executorService != null) {
            executorService.shutdownNow();
        }
        Platform.exit();
    }
}
