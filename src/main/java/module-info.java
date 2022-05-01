module deplacement {
    requires javafx.controls;
    requires javafx.fxml;


    opens application to javafx.fxml;
    exports application;
    opens application.controleur to javafx.fxml;
    exports application.controleur;
    exports application.controleur.listener;
    opens application.controleur.listener to javafx.fxml;
}