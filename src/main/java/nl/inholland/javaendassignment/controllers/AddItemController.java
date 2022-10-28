package nl.inholland.javaendassignment.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import nl.inholland.javaendassignment.data.Database;
import nl.inholland.javaendassignment.model.Item;

import java.net.URL;
import java.util.ResourceBundle;

public class AddItemController implements Initializable {
    private final MainController mainController;
    private final Database database;

    @FXML
    private Label addEditLabel;

    @FXML
    private TextField titleTextField;
    @FXML
    private TextField authorTextField;
    @FXML
    private CheckBox availableCheckBox;

    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;

    public AddItemController(MainController mainController, Database database) {
        this.mainController = mainController;
        this.database = database;
    }

    @FXML
    private void onConfirmButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        // make new item and add it to the database
        Item item = new Item(0, availableCheckBox.isSelected(), titleTextField.getText(), authorTextField.getText());
        database.addItem(item);
        // load item collection view
        mainController.loadScene("/fxml/collection-view.fxml", new CollectionController(mainController, database));
    }

    @FXML // cancel and go back to the item collection view
    private void onCancelButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        mainController.loadScene("/fxml/collection-view.fxml", new CollectionController(mainController, database));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addEditLabel.setText("Add" + addEditLabel.getText());
    }
}
