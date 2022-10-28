package nl.inholland.javaendassignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import nl.inholland.javaendassignment.controllers.LoginController;
import nl.inholland.javaendassignment.data.Database;

import java.io.IOException;

public class MainApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        Database database = new Database();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/Login-view.fxml"));
        fxmlLoader.setController(new LoginController(database));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Library system application");
        stage.setScene(scene);
        // on close serialize the database
        stage.setOnCloseRequest(onCloseEvent -> database.serialize());
        stage.show();
    }
}