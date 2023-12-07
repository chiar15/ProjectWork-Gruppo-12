/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package it.unisa.diem.se.automationapp;

import it.unisa.diem.se.automationapp.action.ActionEnum;
import it.unisa.diem.se.automationapp.event.CreationEvent;
import it.unisa.diem.se.automationapp.eventsmanagement.EventBus;
import it.unisa.diem.se.automationapp.event.SaveEvent;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleManager;
import it.unisa.diem.se.automationapp.trigger.TriggerEnum;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    
    @FXML
    private TextArea messageField;
    @FXML
    private CheckBox singleExecutionCheckBox;
    @FXML
    private CheckBox multipleExecutionsCheckBox;
    @FXML
    private Label suspensionTimeLabel;
    @FXML
    private ComboBox<String> suspensionDaysBox;
    @FXML
    private ComboBox<String> suspensionHoursBox;
    @FXML
    private ComboBox<String> suspensionMinutesBox;
    @FXML
    private AnchorPane timeTriggerAnchor;
    @FXML
    private AnchorPane dayOfTheWeekAnchor;
    @FXML
    private ComboBox<String> dayOfWeekComboBox;
    @FXML
    private AnchorPane audioActionAnchor;
    @FXML
    private AnchorPane messageActionAnchor;
        
    private RuleManager ruleManager;
    
    private EventBus eventBus;
    
    private int minValue;
    
    private int maxValueHours;
    
    private int maxValueMinutes;
    
    private int maxValueDays;
    
    public void initialize() {
        minValue = 0;
        maxValueHours = 23;
        maxValueMinutes = 59;
        maxValueDays = 30;
        ruleManager = RuleManager.getInstance();
        eventBus = EventBus.getInstance();
        configureUIElements();
        setupListeners();
        initializeBindings();
        eventBus.subscribe(SaveEvent.class, this::onSaveEvent);
    }

    @FXML
    private void comboBoxTriggerAction(ActionEvent event) {
        TriggerEnum selectedTrigger = comboBoxTrigger.getValue();

        if (selectedTrigger != null) {
            switch (selectedTrigger) {
                case TIMETRIGGER:
                    showTimeTriggerControls();
                    hideDayOfWeekControls();
                    break;
                case DAYOFWEEKTRIGGER:
                    showDayOfWeekControls();
                    hideTimeTriggerControls();
                    break;
                // Altri casi
                default:
                    hideTimeTriggerControls();
                    break;
            }
        }
    }

    @FXML
    private void comboBoxActionRule(ActionEvent event) {
        ActionEnum selectedAction = comboBoxActionRule.getValue();

        if (selectedAction != null) {
            switch (selectedAction) {
                case AUDIOACTION:
                    hideMessageField();
                    showAudioActionControls();
                    break;
                case MESSAGEACTION:
                    hideAudioActionControls();
                    showMessageField();
                    break;
                default:
                    hideAudioActionControls();
                    hideMessageField();
                    break;
            }
        }
    }
    
    @FXML
    private void audioPathButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the audio file");
        
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Audio file (*.mp3, *.wav)", "*.mp3", "*.wav");
        fileChooser.getExtensionFilters().add(extFilter);
        
        File selectedFile = fileChooser.showOpenDialog(new Stage());
        
        if (selectedFile != null) {
            audioPathField.setText(selectedFile.getAbsolutePath());
        }
    }
    
    @FXML
    private void singleExecutionAction(ActionEvent event) {
        if (singleExecutionCheckBox.isSelected()) {
            hideMultipleExecution();
            multipleExecutionsCheckBox.setSelected(false);
        }
    }

    @FXML
    private void multipleExecutionsAction(ActionEvent event) {
        if (multipleExecutionsCheckBox.isSelected()) {
            showMultipleExecution();
            singleExecutionCheckBox.setSelected(false);
        } else {
            hideMultipleExecution();
        }
    }

    @FXML
    private void createRuleButtonAction(ActionEvent event) {
        String ruleName = ruleNameField.getText();
        long suspensionPeriod = 0;
        TriggerEnum selectedTrigger = comboBoxTrigger.getValue();
        ActionEnum selectedAction = comboBoxActionRule.getValue();

        Map<String, String> triggerData = prepareTriggerData(selectedTrigger);
        Map<String, String> actionData = prepareActionData(selectedAction);
        if(multipleExecutionsCheckBox.isSelected()){
            suspensionPeriod = prepareSuspensionPeriod();
        }

        Rule rule = ruleManager.createRule(ruleName, triggerData, actionData, suspensionPeriod);

        resetFields();
        eventBus.publish(new CreationEvent("Rule Created", rule));

        closeWindow();
    }
    
    private void configureUIElements() {
        configureComboBoxes();
        configureSpinners();
        configureDayOfWeekComboBox();
        ruleNameField.setFocusTraversable(false);
        comboBoxTrigger.setFocusTraversable(false);
        comboBoxActionRule.setFocusTraversable(false);
        createRuleButton.requestFocus();
        singleExecutionCheckBox.setSelected(true);
        hideTimeTriggerControls();
        hideAudioActionControls();
        hideMessageField();
        hideMultipleExecution();
        hideDayOfWeekControls();
    }
    
    private void configureComboBoxes() {
        comboBoxTrigger.getItems().setAll(TriggerEnum.values());
        comboBoxActionRule.getItems().setAll(ActionEnum.values());
        configureDaysBox(suspensionDaysBox, minValue, maxValueDays, minValue);
        configureTimeBox(suspensionHoursBox, minValue, maxValueHours, minValue, "Hours");
        configureTimeBox(suspensionMinutesBox, minValue, maxValueMinutes, minValue, "Minutes");
    }
    
    private void configureSpinners() {
        configureSpinner(spinnerHours, null, minValue, maxValueHours, java.time.LocalTime.now().getHour(), "Hours");
        configureSpinner(spinnerMinutes, null, minValue, maxValueMinutes, java.time.LocalTime.now().getMinute(), "Minutes");
    }
    
    private void configureDayOfWeekComboBox() {
        ObservableList<String> daysOfWeek = FXCollections.observableArrayList(
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        );
        dayOfWeekComboBox.setItems(daysOfWeek);
    }
    
    //cambio nome
    private void setupListeners() {
        ruleNameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    checkRuleNameValidity();
                }
            }
        });
        // Listener per spinnerHours
        spinnerHours.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                spinnerHours.getEditor().setText(oldValue);
            } else {
                int value = Integer.parseInt(newValue);
                if (value < minValue || value > maxValueHours) {
                    spinnerHours.getEditor().setText(oldValue);
                }
            }
        });

        // Listener per spinnerMinutes
        spinnerMinutes.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                spinnerMinutes.getEditor().setText(oldValue);
            } else {
                int value = Integer.parseInt(newValue);
                if (value < minValue || value > maxValueMinutes) {
                    spinnerMinutes.getEditor().setText(oldValue);
                }
            }
        });
    }
    
    private void initializeBindings() {
        createRuleButton.disableProperty().bind(
                ruleNameField.textProperty().isEmpty()
                .or(Bindings.not(isValidTriggerInput().and(isValidActionInput()).and(isValidCheckBoxInput())))
                .or(isInvalidMultipleExecution())
            );
    }

    private Map<String, String> prepareActionData(ActionEnum selectedAction) {
        Map<String, String> actionData = new HashMap<>();

        switch (selectedAction) {
            case AUDIOACTION:
                String audioFilePath = audioPathField.getText();
                actionData.put("type", selectedAction.name());
                actionData.put("filePath", audioFilePath);
                break;
            case MESSAGEACTION:
                String message = messageField.getText();
                actionData.put("type", selectedAction.name());
                actionData.put("message", message);
                break;
            // Aggiungere altri casi se necessario
        }

        return actionData;
    }

    private Map<String, String> prepareTriggerData(TriggerEnum selectedTrigger) {
        Map<String, String> triggerData = new HashMap<>();

        switch (selectedTrigger) {
            case TIMETRIGGER:
                int hours = spinnerHours.getValue();
                int minutes = spinnerMinutes.getValue();
                String timeString = String.format(Locale.getDefault(), "%02d:%02d", hours, minutes);
                triggerData.put("type", selectedTrigger.name());
                triggerData.put("time", timeString);
                break;
        }

        return triggerData;
    }
    
    private void resetFields(){
        ruleNameField.clear();
        audioPathField.clear();
        comboBoxTrigger.getSelectionModel().clearSelection();
        comboBoxActionRule.getSelectionModel().clearSelection();
    }
    
    private long prepareSuspensionPeriod() {
        long days = Long.parseLong(suspensionDaysBox.getValue().split(" ")[0]); 
        long hours = Long.parseLong(suspensionHoursBox.getValue().split(" ")[0]); 
        long minutes = Long.parseLong(suspensionMinutesBox.getValue().split(" ")[0]);

        return (days * 86400L) + (hours * 3600L) + (minutes * 60L);
    }

    
    private void configureSpinner(Spinner<Integer> spinner, Integer initialValue, int minValue, int maxValue, int defaultValue, String unit) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(minValue, maxValue, initialValue != null ? initialValue : defaultValue);

        spinner.setValueFactory(valueFactory);

        TextField editor = spinner.getEditor();
        editor.textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { // Accetta solo numeri
                editor.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        spinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                spinner.getValueFactory().setValue(defaultValue);
            }
        });

        Tooltip tooltip = new Tooltip("Min: " + minValue + ", Max: " + maxValue + " " + unit);
        spinner.setTooltip(tooltip);

        // Mostra il tooltip quando il cursore passa sopra lo spinner
        spinner.setOnMouseEntered(event -> {
            if (!spinner.isFocused()) {
                tooltip.show(spinner, event.getScreenX(), event.getScreenY() + 20);
            }
        });

        // Nascondi il tooltip quando il cursore esce dallo spinner
        spinner.setOnMouseExited(event -> {
            if (!spinner.isFocused()) {
                tooltip.hide();
            }
        });

        // Nascondi il tooltip quando lo spinner riceve il focus
        spinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                tooltip.hide();
            }
        });
    }
    
    private boolean areTriggerTimeSpinnerValuesValid() {
        int hoursValue = spinnerHours.getValue();
        int minutesValue = spinnerMinutes.getValue();
        return (hoursValue >= minValue && hoursValue <= maxValueHours) && (minutesValue >= minValue && minutesValue <= maxValueMinutes);
    }
    
    private void showTimeTriggerControls() {
        timeTriggerAnchor.setVisible(true);
        dayOfWeekComboBox.getSelectionModel().clearSelection();
    }

    private void hideTimeTriggerControls() {
        timeTriggerAnchor.setVisible(false);
    }

    private void showAudioActionControls() {
        audioActionAnchor.setVisible(true);
    }

    private void hideAudioActionControls() {
        audioPathField.clear();
        audioActionAnchor.setVisible(false);
    }
    
    private void showMessageField() {
        messageActionAnchor.setVisible(true);
    }

    private void hideMessageField() {
        messageField.clear();
        messageActionAnchor.setVisible(false);
    }
    
    private void hideDayOfWeekControls() {
        dayOfTheWeekAnchor.setVisible(false);
    }
    
    private void showDayOfWeekControls() {
        dayOfTheWeekAnchor.setVisible(true);
        spinnerHours.getValueFactory().setValue(java.time.LocalTime.now().getHour());
        spinnerMinutes.getValueFactory().setValue(java.time.LocalTime.now().getMinute());
    }



    private BooleanBinding isValidTriggerInput() {
        return Bindings.createBooleanBinding(() -> {
            TriggerEnum selectedTrigger = comboBoxTrigger.getValue();
            boolean triggerValid = selectedTrigger != null;

            if (selectedTrigger != null) {
                switch (selectedTrigger) {
                    case TIMETRIGGER:
                        triggerValid = triggerValid &&
                                (spinnerHours.getValue() != null && spinnerMinutes.getValue() != null) && areTriggerTimeSpinnerValuesValid();
                        break;
                    // Aggiungere altri casi per altri tipi di trigger se necessario
                    default:
                        break;
                }
            }

            return triggerValid && isFieldsFilled();
        }, comboBoxTrigger.valueProperty(), spinnerHours.valueProperty(), spinnerMinutes.valueProperty(), ruleNameField.textProperty(), comboBoxActionRule.valueProperty(), audioPathField.textProperty(), messageField.textProperty(), singleExecutionCheckBox.selectedProperty(), multipleExecutionsCheckBox.selectedProperty());
    }

    private BooleanBinding isValidActionInput() {
        return Bindings.createBooleanBinding(() -> {
            ActionEnum selectedAction = comboBoxActionRule.getValue();
            boolean actionValid = selectedAction != null;

            if (selectedAction != null) {
                switch (selectedAction) {
                    case AUDIOACTION:
                        actionValid = actionValid && !audioPathField.getText().isEmpty();
                        break;
                    case MESSAGEACTION:
                        actionValid = actionValid && !messageField.getText().isEmpty();
                        break;
                    // Aggiungere altri casi per altri tipi di azione se necessario
                    default:
                        break;
                }
            }

            return actionValid && isFieldsFilled();
        }, comboBoxActionRule.valueProperty(), ruleNameField.textProperty(), audioPathField.textProperty(), messageField.textProperty(), spinnerHours.valueProperty(), spinnerMinutes.valueProperty());
    }
    
    private BooleanBinding isInvalidMultipleExecution() {
        BooleanBinding daysZero = suspensionDaysBox.valueProperty().isEqualTo("0 Days");
        BooleanBinding hoursZero = suspensionHoursBox.valueProperty().isEqualTo("0 Hours");
        BooleanBinding minutesZero = suspensionMinutesBox.valueProperty().isEqualTo("0 Minutes");

        return multipleExecutionsCheckBox.selectedProperty().and(
            daysZero.and(hoursZero).and(minutesZero)
        );
    }

    private boolean isFieldsFilled() {
        return !ruleNameField.getText().isEmpty();
    }
    
    private void showMultipleExecution() {
        suspensionTimeLabel.setVisible(true);
        suspensionDaysBox.setManaged(true);
        suspensionDaysBox.setVisible(true);
        suspensionHoursBox.setManaged(true);
        suspensionHoursBox.setVisible(true);
        suspensionMinutesBox.setManaged(true);
        suspensionMinutesBox.setVisible(true);
    }
    
    private void hideMultipleExecution() {
        suspensionTimeLabel.setVisible(false);
        suspensionDaysBox.setManaged(false);
        suspensionDaysBox.setVisible(false);
        suspensionDaysBox.setValue("0 Days");
        suspensionHoursBox.setManaged(false);
        suspensionHoursBox.setVisible(false);
        suspensionHoursBox.setValue("0 Hours");
        suspensionMinutesBox.setManaged(false);
        suspensionMinutesBox.setVisible(false);
        suspensionMinutesBox.setValue("0 Minutes");
    }
    
    private BooleanBinding isValidCheckBoxInput() {
        return singleExecutionCheckBox.selectedProperty().or(multipleExecutionsCheckBox.selectedProperty());
    }
    
    private void configureDaysBox(ComboBox<String> comboBox, int minValue, int maxValue, int defaultValue) {
        comboBox.getItems().clear();
        for (int i = minValue; i <= maxValue; i++) {
            if (i == 1) {
                comboBox.getItems().add(i + " Day");
            } else {
                comboBox.getItems().add(i + " Days");
            }
        }
        comboBox.setValue(defaultValue + (defaultValue == 1 ? " Day" : " Days"));
    }

    private void configureTimeBox(ComboBox<String> comboBox, int minValue, int maxValue, int defaultValue, String unit) {
        comboBox.getItems().clear();
        for (int i = minValue; i <= maxValue; i++) {
            if (i == 1) {
                comboBox.getItems().add(i + " " + unit.substring(0, unit.length() - 1));
            } else {
                comboBox.getItems().add(i + " " + unit);
            }
        }
        comboBox.setValue(defaultValue + (defaultValue == 1 ? " " + unit.substring(0, unit.length() - 1) : " " + unit));
    }
    
    private void checkRuleNameValidity() {
        String ruleName = ruleNameField.getText();
        if(!ruleName.isEmpty()){
            if (ruleManager != null && !ruleManager.getRuleList().isEmpty()) {
                if (ruleManager.doesRuleNameExist(ruleName)) {
                    showAlert("Duplicate Rule Name", "A rule with this name already exists. Please choose a different name.", AlertType.ERROR);
                    ruleNameField.clear();
                }
            }
        }
    }

    private void showAlert(String title, String content, AlertType type) {
        Platform.runLater(() -> {
            Alert alert = new Alert(type, content, ButtonType.OK);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.showAndWait();
        });
    }
    
    private void onSaveEvent(SaveEvent event){
        closeWindow();
    }
    
    private void closeWindow(){
        Stage stage = (Stage) createRuleButton.getScene().getWindow();
        stage.close();
    }
}
