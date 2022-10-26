package nl.inholland.javaendassignment.controllers;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AddMemberController implements Initializable {
    private MainController mainController;

    @FXML private Label addEditLabel;
    @FXML private Button confirmButton;
    @FXML private Button cancelButton;

    @FXML
    protected void onConfirmButtonClick(ActionEvent actionEvent) {

    }

    @FXML
    protected void onCancelButtonClick(ActionEvent actionEvent) {
        mainController.loadScene("/fxml/members-view.fxml", new MembersController(mainController));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addEditLabel.setText("Add" + addEditLabel.getText());
    }

    public AddMemberController(MainController mainController) {
        this.mainController = mainController;
    }
}
