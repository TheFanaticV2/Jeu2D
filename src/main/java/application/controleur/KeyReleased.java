package application.controleur;

import application.modele.Grille;
import application.modele.Personnage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyReleased implements EventHandler<KeyEvent> {

    private Personnage perso;

    public KeyReleased(Personnage perso) {
        this.perso = perso;
    }

    @Override
    public void handle(KeyEvent event) {
        perso.setSeDeplace(false);
    }
}