package application.controleur;

import application.modele.Jeu;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ListenerMap implements ChangeListener<Boolean> {

    private Controleur controleur;
    private Jeu jeu;

    public ListenerMap(Controleur controleur, Jeu jeu) {
        this.controleur = controleur;
        this.jeu = jeu;
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            controleur.contruireMap();
            controleur.construireObjet();
            jeu.setChangementDeMap(false);
        }
    }
}

//public class ListenerMap implements ListChangeListener<Grille> {
//
//    private Controleur controleur;
//
//    public ListenerMap(Controleur controleur) {
//        this.controleur = controleur;
//    }
//
//    @Override
//    public void onChanged(Change<? extends Grille> c) {
//        while (c.next()) {
//            controleur.contruireMap();
//            controleur.construireObjet();
//        }
//
//    }
//}
