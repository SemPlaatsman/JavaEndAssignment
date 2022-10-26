package nl.inholland.javaendassignment.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.event.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import nl.inholland.javaendassignment.MainApplication;
import nl.inholland.javaendassignment.data.Database;
import nl.inholland.javaendassignment.model.User;

import java.io.IOException;
import java.util.List;

public class LoginController {
    private Database database;
    private List<User> users;

    @FXML private GridPane loginGridPane;
    @FXML private Button loginButton;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorLabel;

    @FXML
    public void onLoginButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        if (!validateFields()) {
            errorLabel.setText("Please fill all textfields!");
            return;
        }
        User enteredUser = new User(usernameField.getText(), passwordField.getText());
        if (!validateUser(enteredUser)) {
            errorLabel.setText("Invalid username/password combination!");
            return;
        }

        loadLibraryScene();
    }

    public boolean validateFields() {
        if (usernameField.getText().isEmpty())
            return false;
        return !passwordField.getText().isEmpty();
    }

    public boolean validateUser(User enteredUser) {
        for (User user : users) {
            if (enteredUser.equals(user)) {
                return true;
            }
        }
        return false;
    }

    public void loadLibraryScene() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("/fxml/navigation-view.fxml"));
            fxmlLoader.setController(new MainController(database));
            Stage stage = (Stage) loginGridPane.getScene().getWindow();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setScene(scene);
            stage.centerOnScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LoginController(Database database) {
        this.database = database;
        users = database.getUsers();
    }
}