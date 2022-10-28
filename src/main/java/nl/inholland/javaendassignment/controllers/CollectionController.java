package nl.inholland.javaendassignment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import nl.inholland.javaendassignment.data.Database;
import nl.inholland.javaendassignment.model.Item;

import java.net.URL;
import java.util.ResourceBundle;

public class CollectionController implements Initializable {
    private final MainController mainController;
    private final Database database;
    private ObservableList<Item> items;

    @FXML
    private TableView<Item> itemsTableView;
    @FXML
    private TableColumn<Item, Boolean> availableColumn;
    @FXML
    private Label errorLabel;

    public CollectionController(MainController mainController, Database database) {
        this.mainController = mainController;
        this.database = database;
        this.items = FXCollections.observableArrayList(database.getItems());
    }

    @FXML // open add item view
    private void onAddItemButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        mainController.loadScene("/fxml/add-edit-item-view.fxml", new AddItemController(mainController, database));
    }

    @FXML // open edit item view if an item was selected
    private void onEditItemButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        Item item = itemsTableView.getSelectionModel().getSelectedItem();
        if (item == null) {
            errorLabel.setText("Please select an item to edit!");
            return;
        }
        mainController.loadScene("/fxml/add-edit-item-view.fxml", new EditItemController(mainController, database, item));
    }

    @FXML // delete item if one was selected
    private void onDeleteItemButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        Item item = itemsTableView.getSelectionModel().getSelectedItem();
        if (item == null) {
            errorLabel.setText("Please select an item to delete!");
            return;
        }

        database.deleteItem(item);
        items = FXCollections.observableArrayList(database.getItems());
        itemsTableView.setItems(items);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // based on code in the following stackoverflow post: https://stackoverflow.com/questions/36436169/boolean-to-string-in-tableview-javafx
        availableColumn.setCellFactory(column -> new TableCell<Item, Boolean>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item ? "Yes" : "No");
            }
        });
        itemsTableView.setItems(items);
    }
}
