package nl.inholland.javaendassignment.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import nl.inholland.javaendassignment.MainApplication;
import nl.inholland.javaendassignment.data.Database;
import nl.inholland.javaendassignment.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    private Database database;

    @FXML private VBox containerVBox;
    @FXML private HBox buttonHBox;

    @FXML
    protected void onLendingReceivingButtonClick(ActionEvent actionEvent) {
        loadScene("/fxml/lend-receive-view.fxml", new LendReceiveController(database, new User("Sem", "-")));
        disableNavigationButton(0);
    }

    @FXML
    protected void onCollectionButtonClick(ActionEvent actionEvent) {
        loadScene("/fxml/collection-view.fxml", new CollectionController(this, database));
        disableNavigationButton(1);
    }

    @FXML
    protected void onMembersButtonClick(ActionEvent actionEvent) {
        loadScene("/fxml/members-view.fxml", new MembersController(this, database));
        disableNavigationButton(2);
        buttonHBox.getChildren().get(0).requestFocus();
    }

    public void loadScene(String name, Object controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(name));
            fxmlLoader.setController(controller);
            //fxmlLoader.setControllerFactory(x -> { return controller; });
            Scene scene = new Scene(fxmlLoader.load());
            if (containerVBox.getChildren().size() > 1)
                containerVBox.getChildren().remove(1);
            containerVBox.getChildren().add(scene.getRoot());
        } catch (IOException ioex) {
            throw new RuntimeException(ioex);
        }
    }

    public MainController(Database database) {
        this.database = database;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadScene("/fxml/lend-receive-view.fxml", new LendReceiveController(database, new User("Sem", "-")));
        disableNavigationButton(0);
    }

    private void disableNavigationButton(int index) {
        for (Node node : buttonHBox.getChildren()) {
            if (node instanceof Button){
                node.setDisable(false);
                if (node == buttonHBox.getChildren().get(index))
                    node.setDisable(true);
            }
        }
    }
}
