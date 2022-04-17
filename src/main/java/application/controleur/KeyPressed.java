package application.controleur;

import application.modele.Dir;
import application.modele.Grille;
import application.modele.ObstacleException;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;


public class KeyPressed implements EventHandler<KeyEvent> {

    private Grille grille;
    private AnimationSpritePerso animationSpritePerso;
    private Controleur controleur;

    public KeyPressed(Controleur controleur, Grille grille, AnimationSpritePerso animationSpritePerso) {
        this.controleur = controleur;
        this.grille = grille;
        this.animationSpritePerso = animationSpritePerso;
    }

    @Override
    public void handle(KeyEvent event) {
        if (!animationSpritePerso.isRunning())
            switch (event.getCode()) {
                case Z:
                    if (grille.getPerso().getDirection() == Dir.haut) {
                        try {
                            grille.getPerso().seDeplacer(0,-1);
                            animationSpritePerso.start();
                        } catch (ObstacleException e) {}
                    } else grille.getPerso().setDirection(Dir.haut);
                    break;
                case S:
                    if (grille.getPerso().getDirection() == Dir.bas) {
                        try {
                            grille.getPerso().seDeplacer(0,1);
                            animationSpritePerso.start();
                        } catch (ObstacleException e) {}
                    } else grille.getPerso().setDirection(Dir.bas);
                    break;
                case Q:
                    if (grille.getPerso().getDirection() == Dir.gauche) {
                        try {
                            grille.getPerso().seDeplacer(-1,0);
                            animationSpritePerso.start();
                        } catch (ObstacleException e) {}
                    } else grille.getPerso().setDirection(Dir.gauche);
                    break;
                case D:
                    if (grille.getPerso().getDirection() == Dir.droite) {
                        try {
                            grille.getPerso().seDeplacer(1,0);
                            animationSpritePerso.start();
                        } catch (ObstacleException e) {}
                    } else grille.getPerso().setDirection(Dir.droite);
                    break;
                case P:
                    controleur.affichageBois(grille.getPerso().interactionBois());
                    break;
                default:
                    break;
            }
    }
}
