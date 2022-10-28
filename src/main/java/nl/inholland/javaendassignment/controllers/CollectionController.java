package nl.inholland.javaendassignment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import nl.inholland.javaendassignment.data.Database;
import nl.inholland.javaendassignment.model.Item;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class CollectionController implements Initializable {
    private final MainController mainController;
    private final Database database;
    private ObservableList<Item> items;
    private FilteredList<Item> filteredItems;
    private SortedList<Item> sortedItems;

    @FXML
    private TextField filterTextField;
    @FXML
    private TableView<Item> itemsTableView;
    @FXML
    private TableColumn<Item, Boolean> availableColumn;
    @FXML
    private Label errorLabel;

    public CollectionController(MainController mainController, Database database) {
        this.mainController = mainController;
        this.database = database;
        items = FXCollections.observableArrayList(database.getItems());
        filteredItems = new FilteredList<>(items, predicate -> true);
        sortedItems = new SortedList<>(filteredItems);
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
        filteredItems = new FilteredList<>(items, predicate -> true);
        sortedItems = new SortedList<>(filteredItems);
        itemsTableView.setItems(sortedItems);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // based on code in the following stackoverflow post: https://stackoverflow.com/questions/36436169/boolean-to-string-in-tableview-javafx
        availableColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item ? "Yes" : "No");
            }
        });

        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredItems.setPredicate(item -> {
                if (newValue == null || newValue.isEmpty())
                    return true;
                String filter = newValue.toLowerCase();
                if (item.getTitle().toLowerCase().contains(filter) || item.getAuthor().toLowerCase().contains(filter))
                    return true;
                return false;
            });
        });

        sortedItems.comparatorProperty().bind(itemsTableView.comparatorProperty());
        itemsTableView.setItems(sortedItems);
    }
}
