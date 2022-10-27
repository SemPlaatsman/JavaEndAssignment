package nl.inholland.javaendassignment.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import nl.inholland.javaendassignment.data.Database;
import nl.inholland.javaendassignment.model.Member;
import nl.inholland.javaendassignment.model.User;

import java.lang.reflect.Method;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class EditMemberController implements Initializable {
    private MainController mainController;
    private Database database;
    private Member member;

    @FXML private Label addEditLabel;

    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private DatePicker birthDatePicker;

    @FXML private Button confirmButton;
    @FXML private Button cancelButton;
    @FXML private Label errorLabel;

    @FXML
    protected void onConfirmButtonClick(ActionEvent actionEvent) {
        try {
            LocalDate datePickerValue = LocalDate.parse(((TextField)birthDatePicker.getChildrenUnmodifiable().get(1)).getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            birthDatePicker.setValue(datePickerValue);

            member.setFirstName(firstNameTextField.getText());
            member.setLastName(lastNameTextField.getText());
            member.setBirthDate(birthDatePicker.getValue());
            database.editMember(member);
            mainController.loadScene("/fxml/members-view.fxml", new MembersController(mainController, database));
        } catch (DateTimeParseException dtpex) {
            errorLabel.setText("Please provide a valid date in the following format: 'dd-MM-yyyy'!");
        }
    }

    @FXML
    protected void onCancelButtonClick(ActionEvent actionEvent) {
        mainController.loadScene("/fxml/members-view.fxml", new MembersController(mainController, database));
    }

    public EditMemberController(MainController mainController, Database database, Member member) {
        this.mainController = mainController;
        this.database = database;
        this.member = member;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addEditLabel.setText("Edit" + addEditLabel.getText());

        firstNameTextField.setText(member.getFirstName());
        lastNameTextField.setText(member.getLastName());
        birthDatePicker.setValue(member.getBirthDate());
    }
}
