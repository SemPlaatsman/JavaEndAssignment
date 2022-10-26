package nl.inholland.javaendassignment.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import nl.inholland.javaendassignment.data.Database;
import nl.inholland.javaendassignment.model.User;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.ResourceBundle;

public class EditMemberController implements Initializable {
    private MainController mainController;

    @FXML private Label addEditLabel;
    @FXML private Button confirmButton;
    @FXML private Button cancelButton;

    @FXML
    protected void onConfirmButtonClick(ActionEvent actionEvent) {
        System.out.println("test");
    }

    @FXML
    protected void onCancelButtonClick(ActionEvent actionEvent) {
        mainController.loadScene("/fxml/members-view.fxml", new MembersController(mainController));
    }

    protected void test(Method method) {

    }

    public EditMemberController(MainController mainController) {
        this.mainController = mainController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addEditLabel.setText("Edit" + addEditLabel.getText());
    }
}
