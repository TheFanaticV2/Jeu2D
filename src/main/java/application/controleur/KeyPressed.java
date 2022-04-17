package application.controleur;

import application.modele.Dir;
import application.modele.Grille;
import application.modele.ObstacleException;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;


public class KeyPressed implements EventHandler<KeyEvent> {

    private Grille grille;
    private GameLoop gameLoop;

    public KeyPressed(Grille grille, GameLoop gameLoop) {
        this.grille = grille;
        this.gameLoop = gameLoop;
    }

    @Override
    public void handle(KeyEvent event) {
        switch (event.getCode()) {
            case Z: grille.getPerso().setDirection(Dir.haut); gameLoop.setSeDeplace(true); break;
            case S: grille.getPerso().setDirection(Dir.bas); gameLoop.setSeDeplace(true); break;
            case Q: grille.getPerso().setDirection(Dir.gauche); gameLoop.setSeDeplace(true); break;
            case D: grille.getPerso().setDirection(Dir.droite); gameLoop.setSeDeplace(true); break;
            case P: //poser bois
                //controleur.affichageBois(grille.getPerso().interactionBois());
                break;
                default: break;
            }
    }
}
