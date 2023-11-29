/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package it.unisa.diem.se.automationapp;

import it.unisa.diem.se.automationapp.action.ActionEnum;
import it.unisa.diem.se.automationapp.observer.RuleCreationListener;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleManager;
import it.unisa.diem.se.automationapp.trigger.TriggerEnum;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author agost
 */
public class FXMLCreationViewController{
    
    @FXML
    private TextField ruleNameField;
    @FXML
    private ComboBox<TriggerEnum> comboBoxTrigger;
    @FXML
    private Label labelHours;
    @FXML
    private Spinner<Integer> spinnerHours;
    @FXML
    private Label labelMinutes;
    @FXML
    private Spinner<Integer> spinnerMinutes;
    @FXML
    private ComboBox<ActionEnum> comboBoxActionRule;
    @FXML
    private TextField audioPathField;
    @FXML
    private Button audioPathButton;
    @FXML
    private Button createRuleButton;
    
    private RuleCreationListener listener;
    

    public void initialize() {
        // TODO
        
        comboBoxTrigger.getItems().setAll(TriggerEnum.values());
        comboBoxActionRule.getItems().setAll(ActionEnum.values());
        
        configureSpinner(spinnerHours, 0, 23);
        configureSpinner(spinnerMinutes, 0, 59);
        
        BooleanBinding isInvalidInput = Bindings.createBooleanBinding(
            () -> ruleNameField.getText().isEmpty() ||
                  comboBoxTrigger.getValue() == null ||
                  comboBoxActionRule.getValue() == null,
            ruleNameField.textProperty(),
            comboBoxTrigger.valueProperty(),
            comboBoxActionRule.valueProperty()
        );
        
        createRuleButton.disableProperty().bind(isInvalidInput);
    }
    
    
    public void setRuleCreationListener(RuleCreationListener listener) {
        this.listener = listener;
    } 

    @FXML
    private void ruleNameFieldAciton(ActionEvent event) {
    }

    @FXML
    private void comboBoxTriggerAction(ActionEvent event) {
    }

    @FXML
    private void spinnerHoursAciton(MouseEvent event) {
    }

    @FXML
    private void spinnerMinutesAction(MouseEvent event) {
    }

    @FXML
    private void comboBoxActionRule(ActionEvent event) {
    }

    @FXML
    private void audioPathFieldAction(ActionEvent event) {
    }

    @FXML
    private void audioPathButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the audio file");
        
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("File audio (*.mp3, *.wav)", "*.mp3", "*.wav");
        fileChooser.getExtensionFilters().add(extFilter);
        
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        
        if (selectedFile != null) {
            audioPathField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void createRuleButtonAction(ActionEvent event) {
        String ruleName = ruleNameField.getText();
        TriggerEnum selectedTrigger = comboBoxTrigger.getValue();
        ActionEnum selectedAction = comboBoxActionRule.getValue();
        String audioFilePath = audioPathField.getText();
        
        int hours = spinnerHours.getValue();
        int minutes = spinnerMinutes.getValue();
        String timeString = String.format(Locale.getDefault(), "%02d:%02d", hours, minutes);
        Map<String, String> triggerData = new HashMap<>();
        triggerData.put("type", selectedTrigger.name());
        triggerData.put("time", timeString);
        Map<String, String> actionData = new HashMap<>();
        actionData.put("type", selectedAction.name());
        actionData.put("filePath", audioFilePath);

        RuleManager ruleService = RuleManager.getInstance();
        Rule rule = ruleService.createRule(ruleName, triggerData, actionData);
        
        resetFields();
        actionData.clear();
        triggerData.clear();
        if (listener != null) {
            listener.onRuleCreated(rule);
        }
        
        closeWindow();
    }
    
    private void resetFields(){
        ruleNameField.clear();
        audioPathField.clear();
        comboBoxTrigger.getSelectionModel().clearSelection();
        comboBoxActionRule.getSelectionModel().clearSelection();
        
        spinnerHours.getValueFactory().setValue(Integer.MIN_VALUE);
        spinnerMinutes.getValueFactory().setValue(Integer.MIN_VALUE);
    }
    
    private void configureSpinner(Spinner<Integer> spinner, int minValue, int maxValue) {
        // Imposta il valore iniziale e i limiti dell'intervallo di valori ammissibili
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(minValue, maxValue, 0);

        // Imposta il valore di default
        spinner.setValueFactory(valueFactory);

        // Imposta il TextField editor per accettare solo input numerico
        TextField editor = spinner.getEditor();
        editor.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { // Accetta solo numeri
                editor.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
    
    public void closeWindow(){
        Stage stage = (Stage) createRuleButton.getScene().getWindow();
        stage.close();
    }
}
