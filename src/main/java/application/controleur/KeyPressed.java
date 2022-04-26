package application.controleur;

import application.modele.Dir;
import application.modele.jeu;
import application.modele.Jeu;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;


public class KeyPressed implements EventHandler<KeyEvent> {

    private Jeu jeu;

    public KeyPressed(Jeu jeu) {
        this.jeu = jeu;
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case Z:
                jeu.getPerso().setDirection(Dir.haut);
                if (jeu.getPerso().memeDirection())
                    jeu.getPerso().setSeDeplace(true);
                else
                    jeu.getPerso().setaChangeDeDirection(true);
                break;
            case S:
                jeu.getPerso().setDirection(Dir.bas);
                if (jeu.getPerso().memeDirection())
                    jeu.getPerso().setSeDeplace(true);
                else
                    jeu.getPerso().setaChangeDeDirection(true);
                break;
            case Q:
                jeu.getPerso().setDirection(Dir.gauche);
                if (jeu.getPerso().memeDirection())
                    jeu.getPerso().setSeDeplace(true);
                else
                    jeu.getPerso().setaChangeDeDirection(true);
                break;
            case D:
                jeu.getPerso().setDirection(Dir.droite);
                if (jeu.getPerso().memeDirection())
                    jeu.getPerso().setSeDeplace(true);
                else
                    jeu.getPerso().setaChangeDeDirection(true);
                break;
            case P: jeu.getPerso().setInteragitBois(true);
                break;
            default: break;
        }
    }
}