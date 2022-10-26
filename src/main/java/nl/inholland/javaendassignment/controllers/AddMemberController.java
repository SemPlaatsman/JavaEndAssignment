package nl.inholland.javaendassignment.controllers;

import javafx.css.CssMetaData;
import javafx.css.Styleable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import nl.inholland.javaendassignment.data.Database;
import nl.inholland.javaendassignment.model.Member;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddMemberController implements Initializable {
    private MainController mainController;
    private Database database;

    @FXML private Label addEditLabel;

    @FXML private TextField firstNameTextField;
    @FXML private TextField lastNameTextField;
    @FXML private DatePicker birthDatePicker;

    @FXML private Button confirmButton;
    @FXML private Button cancelButton;

    @FXML
    protected void onConfirmButtonClick(ActionEvent actionEvent) {
        try {
            LocalDate datePickerValue = LocalDate.parse(((TextField)birthDatePicker.getChildrenUnmodifiable().get(1)).getText(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            birthDatePicker.setValue(datePickerValue);

            Member member = new Member(0, firstNameTextField.getText(), lastNameTextField.getText(), birthDatePicker.getValue());
            database.addMember(member);
            mainController.loadScene("/fxml/members-view.fxml", new MembersController(mainController, database));
        } catch (DateTimeParseException dtpex) {
            Alert invalidBirthDateAlert = new Alert(Alert.AlertType.ERROR, "Please provide a valid date in the following format: (dd-MM-yyyy) e.g. 26-10-2022.");
            invalidBirthDateAlert.setHeaderText("Invalid birth date entered!");
            invalidBirthDateAlert.showAndWait();
        }
    }

    @FXML
    protected void onCancelButtonClick(ActionEvent actionEvent) {
        mainController.loadScene("/fxml/members-view.fxml", new MembersController(mainController, database));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addEditLabel.setText("Add" + addEditLabel.getText());
    }

    public AddMemberController(MainController mainController, Database database) {
        this.mainController = mainController;
        this.database = database;
    }
}
