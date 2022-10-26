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
    @FXML private TableColumn<Item, Boolean> availableColumn = new TableColumn<>("Available");

    @FXML
    public void onAddItemButtonClick(ActionEvent actionEvent) {
        mainController.loadScene("/fxml/add-edit-item-view.fxml", new AddItemController(mainController, database));
    }

    @FXML
    public void onEditItemButtonClick(ActionEvent actionEvent) {
        Item item = itemsTableView.getSelectionModel().getSelectedItem();
        if (item == null) {
            unselectedItemAlert("edit");
            return;
        }
        mainController.loadScene("/fxml/add-edit-item-view.fxml", new EditItemController(mainController, database, item));
    }

    @FXML
    public void onDeleteItemButtonClick(ActionEvent actionEvent) {
        Item item = itemsTableView.getSelectionModel().getSelectedItem();
        if (item == null) {
            unselectedItemAlert("delete");
            return;
        }

        Alert deleteAlert = new Alert(Alert.AlertType.CONFIRMATION, "The following item will be deleted: " + item.getTitle() + " by " + item.getAuthor() + ". Deleting an item cannot be reverted!", ButtonType.YES, ButtonType.CANCEL);
        deleteAlert.setHeaderText("Are you sure you wish to delete this item?");
        if (!(deleteAlert.showAndWait().get() == ButtonType.YES))
            return;

        database.deleteItem(item);
        items = FXCollections.observableArrayList(database.getItems());
        itemsTableView.setItems(items);
    }

    private void unselectedItemAlert(String purpose) {
        Alert unselectedItemAlert = new Alert(Alert.AlertType.ERROR, "No item was selected! Please select an item to " + purpose + ".");
        unselectedItemAlert.setHeaderText("No item selected!");
        unselectedItemAlert.showAndWait();
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
