package application.controleur;

import application.modele.Dir;
import application.modele.Grille;
import application.modele.ObstacleException;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;


public class KeyPressed implements EventHandler<KeyEvent> {

    private Grille grille;

    public KeyPressed(Grille grille) {
        this.grille = grille;
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case Z:
                grille.getPerso().setDirection(Dir.haut);
                if (grille.getPerso().memeDirection())
                    grille.getPerso().setSeDeplace(true);
                else
                    grille.getPerso().setaChangeDeDirection(true);
                break;
            case S:
                grille.getPerso().setDirection(Dir.bas);
                if (grille.getPerso().memeDirection())
                    grille.getPerso().setSeDeplace(true);
                else
                    grille.getPerso().setaChangeDeDirection(true);
                break;
            case Q:
                grille.getPerso().setDirection(Dir.gauche);
                if (grille.getPerso().memeDirection())
                    grille.getPerso().setSeDeplace(true);
                else
                    grille.getPerso().setaChangeDeDirection(true);
                break;
            case D:
                grille.getPerso().setDirection(Dir.droite);
                if (grille.getPerso().memeDirection())
                    grille.getPerso().setSeDeplace(true);
                else
                    grille.getPerso().setaChangeDeDirection(true);
                break;
            case P: grille.getPerso().setInteragitBois(true);
                break;
            default: break;
        }
    }
}
