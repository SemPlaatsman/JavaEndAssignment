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

public class EditItemController implements Initializable {
    private final MainController mainController;
    private final Database database;
    private final Item item;

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

    public EditItemController(MainController mainController, Database database, Item item) {
        this.mainController = mainController;
        this.database = database;
        this.item = item;
    }

    @FXML
    private void onConfirmButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        // get values of fields
        item.setTitle(titleTextField.getText());
        item.setAuthor(authorTextField.getText());
        item.setAvailable(availableCheckBox.isSelected());
        // write the edited item back to the database
        database.editItem(item);
        // load item collection view
        mainController.loadScene("/fxml/collection-view.fxml", new CollectionController(mainController, database));
    }

    @FXML // cancel and go back to item collection view
    private void onCancelButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        mainController.loadScene("/fxml/collection-view.fxml", new CollectionController(mainController, database));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addEditLabel.setText("Edit" + addEditLabel.getText());

        titleTextField.setText(item.getTitle());
        authorTextField.setText(item.getAuthor());
        availableCheckBox.setSelected(item.getAvailable());
    }
}
