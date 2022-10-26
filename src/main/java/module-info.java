module nl.inholland.javaendassignment {
    requires javafx.controls;
    requires javafx.fxml;


    opens nl.inholland.javaendassignment to javafx.fxml;
    exports nl.inholland.javaendassignment;
    exports nl.inholland.javaendassignment.controllers;
    opens nl.inholland.javaendassignment.controllers to javafx.fxml;
    opens nl.inholland.javaendassignment.model to javafx.base;
}