package nl.inholland.javaendassignment;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nl.inholland.javaendassignment.controllers.LoginController;
import nl.inholland.javaendassignment.controllers.MainController;
import nl.inholland.javaendassignment.data.Database;
import nl.inholland.javaendassignment.model.User;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Database database = new Database();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/navigation-view.fxml"));
        fxmlLoader.setController(new MainController(database, new User("sempl", "semjava")));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Library system application");
        stage.setScene(scene);
        stage.setOnCloseRequest(onCloseEvent -> {
            database.serialize();
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}