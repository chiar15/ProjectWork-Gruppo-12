/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package it.unisa.diem.se.automationapp;

import it.unisa.diem.se.automationapp.action.AudioAction;
import it.unisa.diem.se.automationapp.action.CopyFileAction;
import it.unisa.diem.se.automationapp.action.DeleteFileAction;
import it.unisa.diem.se.automationapp.action.MessageAction;
import it.unisa.diem.se.automationapp.action.MoveFileAction;
import it.unisa.diem.se.automationapp.action.StringAction;
import it.unisa.diem.se.automationapp.event.CreationEvent;
import it.unisa.diem.se.automationapp.eventsmanagement.EventBus;
import it.unisa.diem.se.automationapp.event.CloseEvent;
import it.unisa.diem.se.automationapp.rulesmanagement.Rule;
import it.unisa.diem.se.automationapp.rulesmanagement.RuleManager;
import it.unisa.diem.se.automationapp.trigger.DateTrigger;
import it.unisa.diem.se.automationapp.trigger.DayOfMonthTrigger;
import it.unisa.diem.se.automationapp.trigger.DayOfWeekTrigger;
import it.unisa.diem.se.automationapp.trigger.FileDimensionTrigger;
import it.unisa.diem.se.automationapp.trigger.FileExistsTrigger;
import it.unisa.diem.se.automationapp.trigger.TimeTrigger;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author agost
 */
public class FXMLCreationViewController{
    
    @FXML
    private TextField ruleNameField;
    @FXML
    private ComboBox<String> comboBoxTrigger;
    @FXML
    private Label labelHours;
    @FXML
    private Spinner<Integer> spinnerHours;
    @FXML
    private Label labelMinutes;
    @FXML
    private Spinner<Integer> spinnerMinutes;
    @FXML
    private ComboBox<String> comboBoxActionRule;
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
    @FXML
    private AnchorPane dayOfTheMonthAnchor;
    @FXML
    private ComboBox<String> dayOfTheMonthBox;
    @FXML
    private AnchorPane datePickerAnchor;
    @FXML
    private DatePicker datePicker;
    @FXML
    private AnchorPane stringFileAnchor;
    @FXML
    private TextField stringFilePathField;
    @FXML
    private Button selectStringFilePathButton;
    @FXML
    private AnchorPane copyFileAnchor;
    @FXML
    private TextField copyFilePathField;
    @FXML
    private Button copyFilePathButton;
    @FXML
    private AnchorPane moveFileAnchor;
    @FXML
    private AnchorPane deleteFileAnchor;
    @FXML
    private TextField moveFilePathField;
    @FXML
    private Button moveFilePathButton;
    @FXML
    private TextField deleteFilePathField;
    @FXML
    private Button deleteFilePathButton;
    @FXML
    private TextArea stringAppendField;
    @FXML
    private TextField copyFileDestPathField;
    @FXML
    private Button copyFileDestPathButton;
    @FXML
    private TextField moveFileDestPathField;
    @FXML
    private Button moveFieldDestPathButton;
    @FXML
    private AnchorPane existFileAnchor;
    @FXML
    private TextField fileNameField;
    @FXML
    private TextField fileNamePathField;
    @FXML
    private Button existFileButton;
    @FXML
    private AnchorPane fileDimensionAnchor;
    @FXML
    private TextField fileDimensionField;
    @FXML
    private TextField fileDimensionPathField;
    @FXML
    private Button fileDimensionButton;
        
    private RuleManager ruleManager;
    
    private EventBus eventBus;
    
    private int minValue;
    
    private int maxValueHours;
    
    private int maxValueMinutes;
    
    private int maxValueDays;
    
    private static final String TIME_TRIGGER = "Choose Hour and Minute";
    
    private static final String DAY_OF_WEEK_TRIGGER = "Select Day of the Week";
    
    private static final String DAY_OF_MONTH_TRIGGER = "Pick a Day of the Month";
    
    private static final String DATE_TRIGGER = "Set Specific Date";
    
    private static final String FILE_EXISTENCE_TRIGGER = "Control File Existence";
    
    private static final String FILE_DIMENSION_TRIGGER = "Control File Dimension";
    
    private static final String AUDIO_ACTION = "Play Audio File";
    
    private static final String MESSAGE_ACTION = "Send a Message";
    
    private static final String STRING_ACTION = "Insert String in a .txt File";
    
    private static final String COPY_FILE_ACTION = "Copy a File";
    
    private static final String MOVE_FILE_ACTION = "Move a File";
    
    private static final String DELETE_FILE_ACTION = "Delete a File";
    
    public void initialize() {
        ruleManager = RuleManager.getInstance();
        eventBus = EventBus.getInstance();
        configureUIElements();
        setupListeners();
        initializeBindings();
        eventBus.subscribe(CloseEvent.class, this::onCloseEvent);
    }

    @FXML
    private void comboBoxTriggerAction(ActionEvent event) {
        String selectedTrigger = comboBoxTrigger.getValue();

        if (selectedTrigger != null) {
            switch (selectedTrigger) {
                case TIME_TRIGGER:
                    showTimeTriggerControls();
                    hideDayOfWeekControls();
                    hideDayOfMonthControls();
                    hideDatePickerControls();
                    hideFileExistenceControls();
                    hideFileDimensionControls();
                    break;
                case DAY_OF_WEEK_TRIGGER:
                    showDayOfWeekControls();
                    hideTimeTriggerControls();
                    hideDayOfMonthControls();
                    hideDatePickerControls();
                    hideFileExistenceControls();
                    hideFileDimensionControls();
                    break;
                case DAY_OF_MONTH_TRIGGER:
                    showDayOfMonthControls();
                    hideTimeTriggerControls();
                    hideDayOfWeekControls();
                    hideDatePickerControls();
                    hideFileExistenceControls();
                    hideFileDimensionControls();
                    break;
                case DATE_TRIGGER:
                    showDatePickerControls();
                    hideTimeTriggerControls();
                    hideDayOfWeekControls();
                    hideDayOfMonthControls();
                    hideFileExistenceControls();
                    hideFileDimensionControls();
                    break;
                case FILE_EXISTENCE_TRIGGER:
                    showFileExistenceControls();
                    hideTimeTriggerControls();
                    hideDayOfWeekControls();
                    hideDayOfMonthControls();
                    hideDatePickerControls();
                    hideFileDimensionControls();
                    break;
                case FILE_DIMENSION_TRIGGER:
                    showFileDimensionControls();
                    hideTimeTriggerControls();
                    hideDayOfWeekControls();
                    hideDayOfMonthControls();
                    hideDatePickerControls();
                    hideFileExistenceControls();
                    break;
            }
        }
    }

    @FXML
    private void comboBoxActionRule(ActionEvent event) {
        String selectedAction = comboBoxActionRule.getValue();

        if (selectedAction != null) {
            switch (selectedAction) {
                case AUDIO_ACTION:
                    hideMessageField();
                    hideStringFileControls();
                    hideCopyFileControls();
                    hideMoveFileControls();
                    hideDeleteFileControls();
                    showAudioActionControls();
                    break;
                case MESSAGE_ACTION:
                    hideAudioActionControls();
                    hideStringFileControls();
                    hideCopyFileControls();
                    hideMoveFileControls();
                    hideDeleteFileControls();
                    showMessageField();
                    break;
                case STRING_ACTION:
                    hideCopyFileControls();
                    hideAudioActionControls();
                    hideMessageField();
                    hideMoveFileControls();
                    hideDeleteFileControls();
                    showStringFileControls();
                    break;
                case COPY_FILE_ACTION:
                    hideStringFileControls();
                    hideMessageField();
                    hideMoveFileControls();
                    hideDeleteFileControls();
                    showCopyFileControls();
                    break;
                case MOVE_FILE_ACTION:
                    hideAudioActionControls();
                    hideMessageField();
                    hideCopyFileControls();
                    hideStringFileControls();
                    hideDeleteFileControls();
                    showMoveFileControls();
                    break;
                case DELETE_FILE_ACTION:
                    hideAudioActionControls();
                    hideMessageField();
                    hideCopyFileControls();
                    hideStringFileControls();
                    hideMoveFileControls();
                    showDeleteFileControls();
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
    private void selectStringFilePathAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the text file");
        
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            stringFilePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void copyFilePathAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the file for the copy");

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            copyFilePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    private void moveFilePathAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the file to move");

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            moveFilePathField.setText(selectedFile.getAbsolutePath());
        }
    }
    
    @FXML
    private void copyFileDestPathAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose destination directory for the copied file");

        File selectedDirectory = directoryChooser.showDialog(new Stage());

        if (selectedDirectory != null) {
            copyFileDestPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    private void moveFieldDestPathAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose destination directory for the moved file");

        File selectedDirectory = directoryChooser.showDialog(new Stage());

        if (selectedDirectory != null) {
            moveFileDestPathField.setText(selectedDirectory.getAbsolutePath());
        }
    }


    @FXML
    private void deleteFilePathAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the file to delete");

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            deleteFilePathField.setText(selectedFile.getAbsolutePath());
        }
    }
    
    @FXML
    private void existFileButtonAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose the directory for file");

        File selectedDirectory = directoryChooser.showDialog(new Stage());

        if (selectedDirectory != null) {
            fileNamePathField.setText(selectedDirectory.getAbsolutePath());
        }
    }

    @FXML
    private void fileDimensionButtonAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the file to control");

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Files", "*.*"));

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            fileDimensionPathField.setText(selectedFile.getAbsolutePath());
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
        String selectedTrigger = comboBoxTrigger.getValue();
        String selectedAction = comboBoxActionRule.getValue();

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
        configureDatePicker();
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
        hideDayOfMonthControls();
        hideDatePickerControls();
        hideCopyFileControls();
        hideStringFileControls();
        hideMoveFileControls();
        hideDeleteFileControls();
        hideFileExistenceControls();
        hideFileDimensionControls();
    }
    
    private void configureComboBoxes() {
        comboBoxTrigger.getItems().clear();
        comboBoxTrigger.getItems().addAll(
            TIME_TRIGGER, 
            DAY_OF_WEEK_TRIGGER, 
            DAY_OF_MONTH_TRIGGER, 
            DATE_TRIGGER,
            FILE_EXISTENCE_TRIGGER,
            FILE_DIMENSION_TRIGGER
        );
        comboBoxActionRule.getItems().clear();
        comboBoxActionRule.getItems().setAll(
                AUDIO_ACTION,
                MESSAGE_ACTION, 
                STRING_ACTION, 
                COPY_FILE_ACTION, 
                MOVE_FILE_ACTION, 
                DELETE_FILE_ACTION
        );
        configureDaysBox(suspensionDaysBox, minValue, maxValueDays, minValue);
        configureTimeBox(suspensionHoursBox, minValue, maxValueHours, minValue, "Hours");
        configureTimeBox(suspensionMinutesBox, minValue, maxValueMinutes, minValue, "Minutes");

        configureDayOfWeekComboBox();
        configureDayOfMonthComboBox();
    }
    
    private void configureSpinners() {
        minValue = 0;
        maxValueHours = 23;
        maxValueMinutes = 59;
        maxValueDays = 30;
        configureSpinner(spinnerHours, null, minValue, maxValueHours, java.time.LocalTime.now().getHour(), "Hours");
        configureSpinner(spinnerMinutes, null, minValue, maxValueMinutes, java.time.LocalTime.now().getMinute(), "Minutes");
    }
    
    private void configureDayOfWeekComboBox() {
        ObservableList<String> daysOfWeek = FXCollections.observableArrayList(
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
        );
        dayOfWeekComboBox.setItems(daysOfWeek);
    }
    
    private void configureDayOfMonthComboBox() {
        ObservableList<String> daysOfMonth = FXCollections.observableArrayList();

        for (int i = 1; i <= 31; i++) {
            daysOfMonth.add(String.valueOf(i));
        }

        dayOfTheMonthBox.setItems(daysOfMonth);
    }
    
    private void configureDatePicker() {

        datePicker.setShowWeekNumbers(false);
        datePicker.setEditable(false);
        
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);

                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });
    }
    
    private void setupListeners() {
        ruleNameField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    checkRuleNameValidity();
                }
            }
        });
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
        
        Tooltip warningTooltip = new Tooltip("Warning: Some months do not have days beyond 28, 29, 30, or 31.");
        dayOfTheMonthBox.setTooltip(warningTooltip);

        Timeline hideTooltipTimeline = new Timeline(new KeyFrame(Duration.seconds(3), ae -> warningTooltip.hide()));

        dayOfTheMonthBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int day = Integer.parseInt(newValue);
                if (day > 28) {
                    dayOfTheMonthBox.setTooltip(warningTooltip);
                    warningTooltip.show(dayOfTheMonthBox, 
                                        dayOfTheMonthBox.localToScreen(dayOfTheMonthBox.getBoundsInLocal()).getMinX(), 
                                        dayOfTheMonthBox.localToScreen(dayOfTheMonthBox.getBoundsInLocal()).getMaxY());
                    hideTooltipTimeline.playFromStart();
                } else {
                    warningTooltip.hide();
                    hideTooltipTimeline.stop();
                }
            }
        });
        
        fileDimensionField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                fileDimensionField.setText(oldValue);
            } else {
                try {
                    if (!newValue.isEmpty()) {
                        int intValue = Integer.parseInt(newValue);
                        if (intValue > Integer.MAX_VALUE) {
                            fileDimensionField.setText(oldValue);
                        }
                    }
                } catch (NumberFormatException e) {
                    fileDimensionField.setText(oldValue); 
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

    private Map<String, String> prepareActionData(String selectedAction) {
        Map<String, String> actionData = new HashMap<>();
        switch (selectedAction) {
            case AUDIO_ACTION:
                String audioFilePath = audioPathField.getText();
                actionData.put("type", AudioAction.class.getSimpleName());
                actionData.put("filePath", audioFilePath);
                break;
            case MESSAGE_ACTION:
                String message = messageField.getText();
                actionData.put("type", MessageAction.class.getSimpleName());
                actionData.put("message", message);
                break;
            case STRING_ACTION:
                String stringFilePath = stringFilePathField.getText();
                actionData.put("type", StringAction.class.getSimpleName());
                actionData.put("stringFilePath", stringFilePath);
                actionData.put("string", stringAppendField.getText());
                break;
            case COPY_FILE_ACTION:
                String copyFilePath = copyFilePathField.getText();
                String copyFileDestPath = copyFileDestPathField.getText();
                actionData.put("type", CopyFileAction.class.getSimpleName());
                actionData.put("copySourcePath", copyFilePath);
                actionData.put("copyDestPath", copyFileDestPath);
                break;
            case MOVE_FILE_ACTION:
                String moveFilePath = moveFilePathField.getText();
                String moveFileDestPath = moveFileDestPathField.getText();
                actionData.put("type", MoveFileAction.class.getSimpleName());
                actionData.put("moveSourcePath", moveFilePath);
                actionData.put("moveDestPath", moveFileDestPath);
                break;
            case DELETE_FILE_ACTION:
                String deleteFilePath = deleteFilePathField.getText();
                actionData.put("type", DeleteFileAction.class.getSimpleName());
                actionData.put("deleteFilePath", deleteFilePath);
                break;
            default:
                break;
        }
        return actionData;
    }

    private Map<String, String> prepareTriggerData(String selectedTrigger) {
        Map<String, String> triggerData = new HashMap<>();
        switch (selectedTrigger) {
            case TIME_TRIGGER:
                int hours = spinnerHours.getValue();
                int minutes = spinnerMinutes.getValue();
                String timeString = String.format(Locale.getDefault(), "%02d:%02d", hours, minutes);
                triggerData.put("type", TimeTrigger.class.getSimpleName());
                triggerData.put("time", timeString);
                break;
            case DAY_OF_WEEK_TRIGGER:
                String weekDay = dayOfWeekComboBox.getValue();
                triggerData.put("type", DayOfWeekTrigger.class.getSimpleName());
                triggerData.put("dayOfWeek", weekDay);
                break;
            case DAY_OF_MONTH_TRIGGER:
                String day = dayOfTheMonthBox.getValue();
                triggerData.put("type", DayOfMonthTrigger.class.getSimpleName());
                triggerData.put("dayOfMonth", day);
                break;
            case DATE_TRIGGER:
                LocalDate date = datePicker.getValue();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                triggerData.put("type", DateTrigger.class.getSimpleName());
                triggerData.put("date", dtf.format(date));
                break;
            case FILE_EXISTENCE_TRIGGER:
                String fileName = fileNameField.getText();
                String pathFileName = fileNamePathField.getText();
                triggerData.put("type", FileExistsTrigger.class.getSimpleName());
                triggerData.put("fileName", fileName);
                triggerData.put("fileDirectory", pathFileName);
                break;
            case FILE_DIMENSION_TRIGGER:
                String dimension = fileDimensionField.getText();
                String filePath = fileDimensionPathField.getText();
                triggerData.put("type", FileDimensionTrigger.class.getSimpleName());
                triggerData.put("dimension", dimension);
                triggerData.put("filePath", filePath);
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
            if (!newValue.matches("\\d*")) {
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

        spinner.setOnMouseEntered(event -> {
            if (!spinner.isFocused()) {
                tooltip.show(spinner, event.getScreenX(), event.getScreenY() + 20);
            }
        });

        spinner.setOnMouseExited(event -> {
            if (!spinner.isFocused()) {
                tooltip.hide();
            }
        });

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
        spinnerHours.getValueFactory().setValue(java.time.LocalTime.now().getHour());
        spinnerMinutes.getValueFactory().setValue(java.time.LocalTime.now().getMinute());
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
        dayOfWeekComboBox.getSelectionModel().clearSelection();
        dayOfTheWeekAnchor.setVisible(false);
    }
    
    private void showDayOfWeekControls() {
        dayOfTheWeekAnchor.setVisible(true);
    }

    private void hideDayOfMonthControls() {
        dayOfTheMonthAnchor.setVisible(false);
        dayOfTheMonthBox.getSelectionModel().clearSelection();
    }
    
    private void showDayOfMonthControls() {
        dayOfTheMonthAnchor.setVisible(true);
    }
    
    private void hideDatePickerControls() {
        datePicker.setValue(null);
        datePickerAnchor.setVisible(false);
    }
        
    private void showDatePickerControls() {
        datePickerAnchor.setVisible(true);
    }
    
    private void hideStringFileControls() {
        stringAppendField.clear();
        stringFilePathField.clear();
        stringFileAnchor.setVisible(false);
    }
    
    private void showStringFileControls() {
        stringFileAnchor.setVisible(true);
    }
    
    private void hideCopyFileControls() {
        copyFilePathField.clear();
        copyFileDestPathField.clear();
        copyFileAnchor.setVisible(false);
    }
    
    private void showCopyFileControls() {
        copyFileAnchor.setVisible(true);
    }
    
    private void hideMoveFileControls() {
        moveFilePathField.clear();
        moveFileDestPathField.clear();
        moveFileAnchor.setVisible(false);
    }
    
    private void showMoveFileControls() {
        moveFileAnchor.setVisible(true);
    }
    
    private void hideDeleteFileControls() {
        deleteFilePathField.clear();
        deleteFileAnchor.setVisible(false);
    }
    
    private void showDeleteFileControls() {
        deleteFileAnchor.setVisible(true);
    }
    
    private void showFileExistenceControls() {
        existFileAnchor.setVisible(true);
    }
    
    private void hideFileExistenceControls() {
        fileNameField.clear();
        fileNamePathField.clear();
        existFileAnchor.setVisible(false);
    }
    
    private void showFileDimensionControls() {
        fileDimensionAnchor.setVisible(true);
    }
    
    private void hideFileDimensionControls() {
        fileDimensionField.clear();
        fileDimensionPathField.clear();
        fileDimensionAnchor.setVisible(false);
    }

    private BooleanBinding isValidTriggerInput() {
        return Bindings.createBooleanBinding(() -> {
            String selectedTrigger = comboBoxTrigger.getValue();
            boolean triggerValid = selectedTrigger != null;

            if (selectedTrigger != null) {
                switch (selectedTrigger) {
                    case TIME_TRIGGER:
                        triggerValid = triggerValid &&
                                (spinnerHours.getValue() != null && spinnerMinutes.getValue() != null) && areTriggerTimeSpinnerValuesValid();
                        break;
                    case DAY_OF_WEEK_TRIGGER:
                        triggerValid = triggerValid && dayOfWeekComboBox.getValue() != null;
                        break;
                    case DAY_OF_MONTH_TRIGGER:
                        triggerValid = triggerValid && dayOfTheMonthBox.getValue() != null;
                        break;
                    case DATE_TRIGGER:
                        triggerValid = triggerValid && datePicker.getValue() != null;
                        break;
                    case FILE_EXISTENCE_TRIGGER:
                        triggerValid = triggerValid && (fileNameField.getText() != null && fileNamePathField.getText() != null);
                        break;
                    case FILE_DIMENSION_TRIGGER:
                        triggerValid = triggerValid && (fileDimensionField.getText() != null && fileDimensionPathField.getText() != null);
                        break;
                    default:
                        break;
                }
            }

            return triggerValid && isFieldsFilled();
        }, comboBoxTrigger.valueProperty(), spinnerHours.valueProperty(), spinnerMinutes.valueProperty(), ruleNameField.textProperty(), comboBoxActionRule.valueProperty(), audioPathField.textProperty(), messageField.textProperty(), singleExecutionCheckBox.selectedProperty(), multipleExecutionsCheckBox.selectedProperty(), dayOfTheMonthBox.valueProperty(), dayOfWeekComboBox.valueProperty(), datePicker.valueProperty());
    }

    private BooleanBinding isValidActionInput() {
        return Bindings.createBooleanBinding(() -> {
            String selectedAction = comboBoxActionRule.getValue();
            boolean actionValid = selectedAction != null;

            if (selectedAction != null) {
                switch (selectedAction) {
                    case AUDIO_ACTION:
                        actionValid = actionValid && !audioPathField.getText().isEmpty();
                        break;
                    case MESSAGE_ACTION:
                        actionValid = actionValid && !messageField.getText().isEmpty();
                        break;
                    case STRING_ACTION:
                        actionValid = actionValid && !stringFilePathField.getText().isEmpty();
                        break;
                    case COPY_FILE_ACTION:
                        actionValid = actionValid && !copyFilePathField.getText().isEmpty() && !copyFileDestPathField.getText().isEmpty();
                        break;
                    case MOVE_FILE_ACTION:
                        actionValid = actionValid && !moveFilePathField.getText().isEmpty() && !moveFileDestPathField.getText().isEmpty();
                        break;
                    case DELETE_FILE_ACTION:
                        actionValid = actionValid && !deleteFilePathField.getText().isEmpty();
                        break;
                    default:
                        break;
                }
            }

            return actionValid && isFieldsFilled();
        }, comboBoxActionRule.valueProperty(), ruleNameField.textProperty(), audioPathField.textProperty(), messageField.textProperty(), stringFilePathField.textProperty(), copyFilePathField.textProperty(), copyFileDestPathField.textProperty(), moveFilePathField.textProperty(), moveFileDestPathField.textProperty(), deleteFilePathField.textProperty(), spinnerHours.valueProperty(), spinnerMinutes.valueProperty());
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
    
    private void onCloseEvent(CloseEvent event){
        closeWindow();
    }
    
    private void closeWindow(){
        Stage stage = (Stage) createRuleButton.getScene().getWindow();
        stage.close();
    }
}