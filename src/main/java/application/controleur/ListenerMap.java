package application.controleur;

import application.modele.Grille;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class ListenerMap implements ChangeListener<Boolean> {

    private Controleur controleur;
    private Grille grille;

    public ListenerMap(Controleur controleur, Grille grille) {
        this.controleur = controleur;
        this.grille = grille;
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            controleur.contruireMap();
            controleur.construireObjet();
            grille.setChangementDeMapProperty(false);
        }
    }
}
