package nl.inholland.javaendassignment.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nl.inholland.javaendassignment.MainApplication;
import nl.inholland.javaendassignment.data.Database;
import nl.inholland.javaendassignment.model.User;

import java.io.IOException;

public class LoginController {
    private final Database database;

    @FXML
    private GridPane loginGridPane;
    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    public LoginController(Database database) {
        this.database = database;
    }

    @FXML // login
    private void onLoginButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        if (!validateFields()) {
            errorLabel.setText("Please fill all fields!");
            return;
        }
        User enteredUser = validateUser(new User(usernameField.getText(), passwordField.getText(), "-"));
        if (enteredUser == null) {
            errorLabel.setText("Invalid username/password combination!");
            return;
        }

        loadLibraryScene(enteredUser);
    }

    private boolean validateFields() {
        if (usernameField.getText().isEmpty())
            return false;
        return !passwordField.getText().isEmpty();
    }

    private User validateUser(User enteredUser) {
        for (User user : database.getUsers()) {
            // custom equals method is used where just password and username are compared
            if (enteredUser.equals(user)) {
                return user;
            }
        }
        return null;
    }

    private void loadLibraryScene(User user) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/navigation-view.fxml"));
            fxmlLoader.setController(new MainController(database, user));
            Stage stage = (Stage) loginGridPane.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}