package nl.inholland.javaendassignment.controllers;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import nl.inholland.javaendassignment.data.Database;
import nl.inholland.javaendassignment.model.Item;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.input.KeyCode.T;

public class CollectionController implements Initializable {
    private MainController mainController;
    private Database database;
    private ObservableList<Item> items;

    @FXML private TableView<Item> itemsTableView;
    @FXML private TableColumn<Item, Boolean> availableColumn;
    @FXML private Label errorLabel;

    @FXML
    public void onAddItemButtonClick(ActionEvent actionEvent) {
        mainController.loadScene("/fxml/add-edit-item-view.fxml", new AddItemController(mainController, database));
    }

    @FXML
    public void onEditItemButtonClick(ActionEvent actionEvent) {
        Item item = itemsTableView.getSelectionModel().getSelectedItem();
        if (item == null) {
            errorLabel.setText("Please select an item to edit!");
            return;
        }
        mainController.loadScene("/fxml/add-edit-item-view.fxml", new EditItemController(mainController, database, item));
    }

    @FXML
    public void onDeleteItemButtonClick(ActionEvent actionEvent) {
        Item item = itemsTableView.getSelectionModel().getSelectedItem();
        if (item == null) {
            errorLabel.setText("Please select an item to delete!");
            return;
        }

        database.deleteItem(item);
        items = FXCollections.observableArrayList(database.getItems());
        itemsTableView.setItems(items);
    }

    public CollectionController(MainController mainController, Database database) {
        this.mainController = mainController;
        this.database = database;
        this.items = FXCollections.observableArrayList(database.getItems());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //https://stackoverflow.com/questions/36436169/boolean-to-string-in-tableview-javafx
        availableColumn.setCellFactory(column -> new TableCell<Item, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty) ;
                setText(empty ? null : item ? "Yes" : "No" );
            }
        });
        itemsTableView.setItems(items);
    }
}
