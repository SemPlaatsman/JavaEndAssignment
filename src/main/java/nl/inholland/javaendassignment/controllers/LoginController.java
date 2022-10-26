package nl.inholland.javaendassignment.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nl.inholland.javaendassignment.MainApplication;

import java.io.IOException;

public class LoginController {
    @FXML private GridPane loginGridPane;
    @FXML private Button loginButton;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    public void onLoginButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        loadLibraryScene();
    }

    public void loadLibraryScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/navigation-view.fxml"));
            fxmlLoader.setController(new MainController());
            Stage stage = (Stage) loginGridPane.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setOnCloseRequest(((MainController)fxmlLoader.getController())::onClose);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}