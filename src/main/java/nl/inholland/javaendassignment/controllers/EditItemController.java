package nl.inholland.javaendassignment.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import nl.inholland.javaendassignment.data.Database;
import nl.inholland.javaendassignment.model.Item;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditItemController implements Initializable {
    private MainController mainController;
    private Database database;
    private Item item;

    @FXML private Label addEditLabel;

    @FXML private TextField titleTextField;
    @FXML private TextField authorTextField;
    @FXML private CheckBox availableCheckBox;

    @FXML private Button confirmButton;
    @FXML private Button cancelButton;

    @FXML
    protected void onConfirmButtonClick(ActionEvent actionEvent) {
        item.setTitle(titleTextField.getText());
        item.setAuthor(authorTextField.getText());
        item.setAvailable(availableCheckBox.isSelected());
        database.editItem(item);
        mainController.loadScene("/fxml/collection-view.fxml", new CollectionController(mainController, database));
    }

    @FXML
    protected void onCancelButtonClick(ActionEvent actionEvent) {
        mainController.loadScene("/fxml/collection-view.fxml", new CollectionController(mainController, database));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addEditLabel.setText("Edit" + addEditLabel.getText());

        titleTextField.setText(item.getTitle());
        authorTextField.setText(item.getAuthor());
        availableCheckBox.setSelected(item.getAvailable());
    }

    public EditItemController(MainController mainController, Database database, Item item) {
        this.mainController = mainController;
        this.database = database;
        this.item = item;
    }
}
