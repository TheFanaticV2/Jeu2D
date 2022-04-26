package application.controleur;

import application.modele.Dir;
import application.modele.Jeu;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class KeyPressed implements EventHandler<KeyEvent> {

    private Jeu jeu;

    public KeyPressed(Jeu jeu) {
        this.jeu = jeu;
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case Z: jeu.getPerso().setDirection(Dir.haut); break;
            case S: jeu.getPerso().setDirection(Dir.bas); break;
            case Q: jeu.getPerso().setDirection(Dir.gauche); break;
            case D: jeu.getPerso().setDirection(Dir.droite); break;
            case P: jeu.getPerso().setInteragitBois(true); break;
            default: break;
        }

        if (event.getCode() != KeyCode.P) {
            if (jeu.getPerso().memeDirection())
                jeu.getPerso().setSeDeplace(true);
            else
                jeu.getPerso().setChangeDirection(true);
        }
    }
}