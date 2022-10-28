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
import nl.inholland.javaendassignment.model.Member;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MembersController implements Initializable {
    private final MainController mainController;
    private final Database database;
    private ObservableList<Member> members;

    @FXML
    private TableView<Member> membersTableView;
    @FXML
    private TableColumn<Member, LocalDate> birthDateColumn;
    @FXML
    private Label errorLabel;

    public MembersController(MainController mainController, Database database) {
        this.mainController = mainController;
        this.database = database;
        this.members = FXCollections.observableList(database.getMembers());
    }

    @FXML // open add view
    private void onAddMemberButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        mainController.loadScene("/fxml/add-edit-member-view.fxml", new AddMemberController(mainController, database));
    }

    @FXML // open edit view if a member is selected
    private void onEditMemberButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        Member member = membersTableView.getSelectionModel().getSelectedItem();
        if (member == null) {
            errorLabel.setText("Please select a member to edit!");
            return;
        }
        mainController.loadScene("/fxml/add-edit-member-view.fxml", new EditMemberController(mainController, database, member));
    }

    @FXML // delete member if one is selected
    private void onDeleteMemberButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        Member member = membersTableView.getSelectionModel().getSelectedItem();
        if (member == null) {
            errorLabel.setText("Please select a member to delete!");
            return;
        }

        database.deleteMember(member);
        members = FXCollections.observableArrayList(database.getMembers());
        membersTableView.setItems(members);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // based on the code in the initialize method of CollectionController which is based on the following stackoverflow answer:
        // https://stackoverflow.com/questions/36436169/boolean-to-string-in-tableview-javafx
        birthDateColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setText(empty ? "" : date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            }
        });
        membersTableView.setItems(FXCollections.observableList(members));
    }
}
