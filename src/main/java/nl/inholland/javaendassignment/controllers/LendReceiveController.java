package nl.inholland.javaendassignment.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import nl.inholland.javaendassignment.data.Database;
import nl.inholland.javaendassignment.model.Item;
import nl.inholland.javaendassignment.model.User;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static java.time.temporal.ChronoUnit.DAYS;

public class LendReceiveController implements Initializable {
    private final Database database;
    private final User user;

    @FXML
    private GridPane lendReceiveGridPane;
    @FXML
    private Label welcomeLabel;

    @FXML
    private TextField lendItemCodeTextField;
    @FXML
    private TextField lendMemberIdTextField;
    @FXML
    private Label lendErrorLabel;
    @FXML
    private Label lendSuccessLabel;

    @FXML
    private TextField receiveItemCodeTextField;
    @FXML
    private Label receiveErrorLabel;
    @FXML
    private Label receiveSuccessLabel;

    public LendReceiveController(Database database, User user) {
        this.database = database;
        this.user = user;
    }

    @FXML // lend item if all lending fields are valid
    private void onLendButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        if (!validateLendFields())
            return;
        // get lending item from database
        Item lendingItem = database.getItemByCode(Integer.parseInt(lendItemCodeTextField.getText()));
        // change lending item status
        lendingItem.setAvailable(false);
        lendingItem.setLendingDate(LocalDate.now());
        lendingItem.setLendingMemberId(Integer.parseInt(lendMemberIdTextField.getText()));
        // write the changed lending item back to the database
        database.editItem(lendingItem);
        lendSuccessLabel.setText("Item has been lend out.");
    }

    @FXML // receive item if all receive fields are valid
    private void onReceiveButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        if (!validateReceiveFields())
            return;
        // get received item from database
        Item receivedItem = database.getItemByCode(Integer.parseInt(receiveItemCodeTextField.getText()));
        // get lending date
        LocalDate itemLendingDate = receivedItem.getLendingDate();
        String successLabelAppendage = "";
        // change received item status
        receivedItem.setAvailable(true);
        receivedItem.setLendingMemberId(null);
        receivedItem.setLendingDate(null);
        // write the changed received item back to the database
        database.editItem(receivedItem);
        // check if the lending date is not below 3 weeks in the past
        if (itemLendingDate != null && itemLendingDate.isBefore(LocalDate.now().minusWeeks(3)))
            // if true check how many days it was returned late and append that to the success label
            successLabelAppendage = " " + DAYS.between(itemLendingDate, LocalDate.now().minusWeeks(3)) + " days late";
        receiveSuccessLabel.setText("Item was returned" + successLabelAppendage + ".");
    }

    // validate lending fields
    private boolean validateLendFields() {
        if (lendItemCodeTextField.getText().isEmpty() || lendMemberIdTextField.getText().isEmpty()) {
            lendErrorLabel.setText("Please fill all fields!");
            return false;
        } else if (!(isInt(lendItemCodeTextField.getText()) && isInt(lendMemberIdTextField.getText()))) {
            lendErrorLabel.setText("Please provide a number!");
            return false;
        } else if (database.getItemByCode(Integer.parseInt(lendItemCodeTextField.getText())) == null) {
            lendErrorLabel.setText("Invalid item code!");
            return false;
        } else if (!database.getItemByCode(Integer.parseInt(lendItemCodeTextField.getText())).getAvailable()) {
            lendErrorLabel.setText("Item is unavailable");
            return false;
        } else if (database.getMemberById(Integer.parseInt(lendMemberIdTextField.getText())) == null) {
            lendErrorLabel.setText("Invalid member code!");
            return false;
        }
        lendErrorLabel.setText("");
        return true;
    }

    // validate receive fields
    private boolean validateReceiveFields() {
        if (receiveItemCodeTextField.getText().isEmpty()) {
            receiveErrorLabel.setText("Please fill all fields!");
            return false;
        } else if (!isInt(receiveItemCodeTextField.getText())) {
            receiveErrorLabel.setText("Please provide a number!");
            return false;
        } else if (database.getItemByCode(Integer.parseInt(receiveItemCodeTextField.getText())) == null) {
            receiveErrorLabel.setText("Invalid item code!");
            return false;
        } else if (database.getItemByCode(Integer.parseInt(receiveItemCodeTextField.getText())).getAvailable()) {
            receiveErrorLabel.setText("Item isn't currently lend out!");
            return false;
        }
        receiveErrorLabel.setText("");
        return true;
    }

    // check if string is int
    private boolean isInt(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeLabel.setText("Welcome " + user.getFullName());
    }
}
