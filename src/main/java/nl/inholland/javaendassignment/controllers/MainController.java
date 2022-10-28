package nl.inholland.javaendassignment.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import nl.inholland.javaendassignment.MainApplication;
import nl.inholland.javaendassignment.data.Database;
import nl.inholland.javaendassignment.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private final Database database;
    private final User user;

    @FXML
    private VBox containerVBox;
    @FXML
    private HBox buttonHBox;

    public MainController(Database database, User user) {
        this.database = database;
        this.user = user;
    }

    @FXML // open lend receive view
    private void onLendingReceivingButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        loadScene("/fxml/lend-receive-view.fxml", new LendReceiveController(database, user));
        disableNavigationButton(0);
    }

    @FXML // open item collection view
    private void onCollectionButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        loadScene("/fxml/collection-view.fxml", new CollectionController(this, database));
        disableNavigationButton(1);
    }

    @FXML // open member's collection view
    private void onMembersButtonClick(ActionEvent actionEvent) {
        actionEvent.consume();
        loadScene("/fxml/members-view.fxml", new MembersController(this, database));
        disableNavigationButton(2);
        // set focus on first navigation button so the member's collection doesn't get an ugly blue border around it
        // and so that you can loop through the pages by just clicking enter repeatedly
        buttonHBox.getChildren().get(0).requestFocus();
    }

    // load scene based on the code in https://github.com/ahrnuld/UniversityManager2Broken from my teacher
    public void loadScene(String name, Object controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(name));
            fxmlLoader.setController(controller);
            Scene scene = new Scene(fxmlLoader.load());
            if (containerVBox.getChildren().size() > 1)
                containerVBox.getChildren().remove(1);
            containerVBox.getChildren().add(scene.getRoot());
        } catch (IOException ioex) {
            throw new RuntimeException(ioex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadScene("/fxml/lend-receive-view.fxml", new LendReceiveController(database, user));
        disableNavigationButton(0);
    }

    // disable navigation button of current page
    private void disableNavigationButton(int index) {
        for (Node node : buttonHBox.getChildren()) {
            if (node instanceof Button) {
                node.setDisable(false);
                if (node == buttonHBox.getChildren().get(index))
                    node.setDisable(true);
            }
        }
    }
}
