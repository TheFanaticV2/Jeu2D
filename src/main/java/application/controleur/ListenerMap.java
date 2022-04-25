package application.controleur;

import application.modele.Grille;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

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
