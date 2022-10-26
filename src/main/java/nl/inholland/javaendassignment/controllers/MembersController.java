package nl.inholland.javaendassignment.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import nl.inholland.javaendassignment.data.Database;
import nl.inholland.javaendassignment.model.Item;
import nl.inholland.javaendassignment.model.Member;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class MembersController implements Initializable {
    private MainController mainController;
    private Database database;
    private ObservableList<Member> members;

    @FXML private TableView<Member> membersTableView;
    @FXML private TableColumn<Member, LocalDate> birthDateColumn;

    @FXML
    public void onAddMemberButtonClick(ActionEvent actionEvent) {
        mainController.loadScene("/fxml/add-edit-member-view.fxml", new AddMemberController(mainController, database));
    }

    @FXML
    public void onEditMemberButtonClick(ActionEvent actionEvent) {
        Member member = membersTableView.getSelectionModel().getSelectedItem();
        if (member == null) {
            unselectedMemberAlert("edit");
            return;
        }
        mainController.loadScene("/fxml/add-edit-member-view.fxml", new EditMemberController(mainController, database, member));
    }

    @FXML
    public void onDeleteMemberButtonClick(ActionEvent actionEvent) {
        Member member = membersTableView.getSelectionModel().getSelectedItem();
        if (member == null) {
            unselectedMemberAlert("delete");
            return;
        }

        database.deleteMember(member);
        members = FXCollections.observableArrayList(database.getMembers());
        membersTableView.setItems(members);
    }

    private void unselectedMemberAlert(String purpose) {
        Alert unselectedItemAlert = new Alert(Alert.AlertType.ERROR, "No member was selected! Please select a member to " + purpose + ".");
        unselectedItemAlert.setHeaderText("No member selected!");
        unselectedItemAlert.showAndWait();
    }

    public MembersController(MainController mainController, Database database) {
        this.mainController = mainController;
        this.database = database;
        this.members = FXCollections.observableList(database.getMembers());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //based on the code in the initialize method of CollectionController which is based on the following stackoverflow answer:
        //https://stackoverflow.com/questions/36436169/boolean-to-string-in-tableview-javafx
        birthDateColumn.setCellFactory(column -> new TableCell<Member, LocalDate>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty) ;
                setText(empty ? "" : date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            }
        });
        membersTableView.setItems(FXCollections.observableList(members));
    }
}
