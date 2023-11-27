package it.unisa.diem.se.automationapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 *
 * @author agost
 */

public class AutomationApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Image icon = new Image(getClass().getResourceAsStream("/icon/icona.png"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMainView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        stage.getIcons().add(icon);
        stage.setTitle("Automation App");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(new EventHandler() {
            @Override
            public void handle(Event event) {
                Platform.exit();
                System.exit(0);
            }
  
        });
        stage.show();
    }


    
    public static void main(String[] args) {
        launch(args);
    }
}
