package nl.inholland.javaendassignment.controllers;

import javafx.beans.property.*;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private Database database;
    private ObservableList<Item> items;

    @FXML private TableView<Item> itemsTableView;
    @FXML private TableColumn<Item, Integer> itemCodeColumn;
    @FXML private TableColumn<Item, Boolean> availableColumn;
    @FXML private TableColumn<Item, String> titleColumn;
    @FXML private TableColumn<Item, String> authorColumn;

    @FXML
    public void onAddItemButtonClick(ActionEvent actionEvent) {

    }

    @FXML
    public void onEditItemButtonClick(ActionEvent actionEvent) {

    }

    @FXML
    public void onDeleteItemButtonClick(ActionEvent actionEvent) {

    }

    public CollectionController(Database database) {
        this.database = database;
        this.items = FXCollections.observableArrayList(database.getItems());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        itemCodeColumn.setCellValueFactory(new PropertyValueFactory<Item, Integer>("itemCode"));
//        availableColumn.setCellValueFactory(new PropertyValueFactory<Item, Boolean>("available"));
//        titleColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("title"));
//        authorColumn.setCellValueFactory(new PropertyValueFactory<Item, String>("author"));
//
        itemsTableView.setItems(items);
        //itemsTableView.getColumns().addAll(Arrays.asList(itemCodeColumn, availableColumn, titleColumn, authorColumn));
    }

    private void loadItems() {
//        List<Item> items = database.getItems();
//        System.out.println(items.size());
//        itemCodeColumn.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
//        availableColumn.setCellValueFactory(new PropertyValueFactory<>("available"));
//        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
//        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
//        itemsTableView.setItems(FXCollections.observableList(items));
        //itemsTableView.getColumns().addAll(itemCodeColumn, availableColumn, titleColumn, authorColumn);
    }
}
