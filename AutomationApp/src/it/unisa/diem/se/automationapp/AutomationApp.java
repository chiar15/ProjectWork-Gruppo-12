package it.unisa.diem.se.automationapp;

import it.unisa.diem.se.automationapp.observer.EventBus;
import it.unisa.diem.se.automationapp.observer.MessageEvent;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


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
 
        stage.setOnCloseRequest(e->{
            EventBus.getInstance().clearSubscribers(MessageEvent.class);
            Platform.exit();
            System.exit(0);
        });
            
        stage.show();
    }


    
    public static void main(String[] args) {
        launch(args);
    }
}
