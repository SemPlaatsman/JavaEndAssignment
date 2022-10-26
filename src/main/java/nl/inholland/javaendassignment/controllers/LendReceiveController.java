package nl.inholland.javaendassignment.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import nl.inholland.javaendassignment.data.Database;
import nl.inholland.javaendassignment.model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class LendReceiveController implements Initializable {
    private Database database;
    private User user;

    @FXML private GridPane lendReceiveGridPane;
    @FXML private Label welcomeLabel;

    @FXML
    private void onLendButtonClick(ActionEvent actionEvent) {

    }

    @FXML
    private void onReceiveButtonClick(ActionEvent actionEvent) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeLabel.setText("Welcome " + user.getUsername());
    }

    public LendReceiveController(Database database, User user) {
        this.database = database;
        this.user = user;
    }
}
