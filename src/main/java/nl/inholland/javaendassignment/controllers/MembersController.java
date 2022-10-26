package nl.inholland.javaendassignment.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import nl.inholland.javaendassignment.data.Database;

public class MembersController {
    private MainController mainController;

    @FXML private TableView membersTableView;

    @FXML
    public void onAddMemberButtonClick(ActionEvent actionEvent) {
        mainController.loadScene("/fxml/add-edit-member-view.fxml", new AddMemberController(mainController));
    }

    @FXML
    public void onEditMemberButtonClick(ActionEvent actionEvent) {
        mainController.loadScene("/fxml/add-edit-member-view.fxml", new EditMemberController(mainController));
    }

    @FXML
    public void onDeleteMemberButtonClick(ActionEvent actionEvent) {

    }

    public MembersController(MainController mainController) {
        this.mainController = mainController;
    }
}
