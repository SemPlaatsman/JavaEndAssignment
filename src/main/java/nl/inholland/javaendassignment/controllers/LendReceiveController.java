package nl.inholland.javaendassignment.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import nl.inholland.javaendassignment.data.Database;
import nl.inholland.javaendassignment.model.Item;
import nl.inholland.javaendassignment.model.User;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class LendReceiveController implements Initializable {
    private Database database;
    private User user;

    @FXML private GridPane lendReceiveGridPane;
    @FXML private Label welcomeLabel;

    @FXML private TextField lendItemCodeTextField;
    @FXML private TextField lendMemberIdTextField;
    @FXML private Label lendErrorLabel;
    @FXML private Label lendSuccessLabel;

    @FXML private TextField receiveItemCodeTextField;
    @FXML private Label receiveErrorLabel;
    @FXML private Label receiveSuccessLabel;

    @FXML
    private void onLendButtonClick(ActionEvent actionEvent) {
        if (!validateLendFields())
            return;
        Item lendingItem = database.getItemByCode(Integer.parseInt(lendItemCodeTextField.getText()));
        lendingItem.setAvailable(false);
        lendingItem.setLendingDate(LocalDate.now());
        lendingItem.setLendingMemberId(Integer.parseInt(lendMemberIdTextField.getText()));
        database.editItem(lendingItem);
        lendSuccessLabel.setText("Item has been lend out.");
    }

    @FXML
    private void onReceiveButtonClick(ActionEvent actionEvent) {
        if (!validateReceiveFields())
            return;
        Item receivedItem = database.getItemByCode(Integer.parseInt(receiveItemCodeTextField.getText()));
        LocalDate itemLendingDate = receivedItem.getLendingDate();
        String successLabelAppendage = "";
        receivedItem.setAvailable(true);
        receivedItem.setLendingMemberId(null);
        receivedItem.setLendingDate(null);
        database.editItem(receivedItem);
        if (itemLendingDate != null && itemLendingDate.isBefore(LocalDate.now().minusWeeks(3)))
            successLabelAppendage = " " + DAYS.between(itemLendingDate, LocalDate.now().minusWeeks(3)) + " days late";
        receiveSuccessLabel.setText("Item was returned" + successLabelAppendage + ".");
    }

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
        welcomeLabel.setText("Welcome " + user.getUsername());
    }

    public LendReceiveController(Database database, User user) {
        this.database = database;
        this.user = user;
    }
}
