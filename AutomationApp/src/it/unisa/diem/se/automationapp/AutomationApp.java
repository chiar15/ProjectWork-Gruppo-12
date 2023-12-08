package it.unisa.diem.se.automationapp;

import it.unisa.diem.se.automationapp.eventsmanagement.EventBus;
import it.unisa.diem.se.automationapp.event.CloseEvent;
import javafx.application.Application;
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
 
        EventBus eventBus = EventBus.getInstance();
        
        stage.setOnCloseRequest(e->{
            e.consume();
            eventBus.publish(new CloseEvent("Close request"));
        });
            
        stage.show();
    }


    
    public static void main(String[] args) {
        launch(args);
    }
}
