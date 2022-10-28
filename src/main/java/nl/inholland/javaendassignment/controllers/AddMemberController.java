package nl.inholland.javaendassignment.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import nl.inholland.javaendassignment.data.Database;
import nl.inholland.javaendassignment.model.Member;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class AddMemberController implements Initializable {
    private final MainController mainController;
    private final Database database;

    @FXML
    private Label addEditLabel;

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private DatePicker birthDatePicker;

    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label errorLabel;

    public AddMemberController(MainController mainController, Database database) {
        this.mainController = mainController;
        this.database = database;
    }

    @FXML
    private void onConfirmButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        try {
            // get text in birthDatePicker and parse that to a LocalDate
            // this is done because the value that the birthDatePicker has might not be the most current value
            // because if a user clicks on a date, changes the month to be 2 months higher in the text field and doesn't press enter,
            // the birthDatePicker will still think the current value is the one the user clicked on in the calendar and not the manually changed text
            LocalDate datePickerValue = LocalDate.parse(((TextField) birthDatePicker.getChildrenUnmodifiable().get(1)).getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            birthDatePicker.setValue(datePickerValue);
        } catch (DateTimeParseException dtpex) {
            // if unable to parse it means the provided date was in an invalid format
            errorLabel.setText("Please provide a valid date in the following format: 'dd-MM-yyyy'!");
        }
        // make new member and add it to the database
        Member member = new Member(0, firstNameTextField.getText(), lastNameTextField.getText(), birthDatePicker.getValue());
        database.addMember(member);
        // load members view
        mainController.loadScene("/fxml/members-view.fxml", new MembersController(mainController, database));
    }

    @FXML // cancel and go back to members view
    private void onCancelButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        mainController.loadScene("/fxml/members-view.fxml", new MembersController(mainController, database));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addEditLabel.setText("Add" + addEditLabel.getText());
    }
}
