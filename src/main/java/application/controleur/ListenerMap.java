package application.controleur;

import application.modele.Jeu;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ListenerMap implements ChangeListener<Boolean> {

    private GameLoop gameLoop;
    private Jeu jeu;

    public ListenerMap(GameLoop gameLoop, Jeu jeu) {
        this.gameLoop = gameLoop;
        this.jeu = jeu;
    }

    @Override
    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            gameLoop.contruireMap();
            gameLoop.construireObjet();
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
