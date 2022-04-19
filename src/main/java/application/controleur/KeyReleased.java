package application.controleur;

import application.modele.Grille;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyReleased implements EventHandler<KeyEvent> {

    private Grille grille;

    public KeyReleased(Grille grille) {
        this.grille = grille;
    }

    @Override
    public void handle(KeyEvent event) {
        grille.getPerso().setSeDeplace(false);
    }
}
