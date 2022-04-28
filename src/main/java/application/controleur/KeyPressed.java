package application.controleur;

import application.modele.Dir;
import application.modele.Exception.ObstacleException;
import application.modele.Jeu;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;


public class KeyPressed implements EventHandler<KeyEvent> {

    private Jeu jeu;
    private AnimationSpritePerso animationSpritePerso;
    private Controleur controleur;

    public KeyPressed(Jeu jeu, AnimationSpritePerso animationSpritePerso, Controleur controleur) {
        this.jeu = jeu;
        this.animationSpritePerso = animationSpritePerso;
        this.controleur = controleur;
    }

    @Override
    public void handle(KeyEvent event) {
        if (!animationSpritePerso.isRunning())
            switch (event.getCode()) {
                case Z:
                    if (jeu.getPerso().getDirection() == Dir.haut) {
                        try {
                            jeu.getPerso().seDeplacer();
                            animationSpritePerso.start();
                        } catch (ObstacleException e) {}
                    } else {
                        jeu.getPerso().setDirection(Dir.haut);
                        animationSpritePerso.immobile();
                    }
                    break;
                case S:
                    if (jeu.getPerso().getDirection() == Dir.bas) {
                        try {
                            jeu.getPerso().seDeplacer();
                            animationSpritePerso.start();
                        } catch (ObstacleException e) {}
                    } else {
                        jeu.getPerso().setDirection(Dir.bas);
                        animationSpritePerso.immobile();
                    }
                    break;
                case Q:
                    if (jeu.getPerso().getDirection() == Dir.gauche) {
                        try {
                            jeu.getPerso().seDeplacer();
                            animationSpritePerso.start();
                        } catch (ObstacleException e) {}
                    } else {
                        jeu.getPerso().setDirection(Dir.gauche);
                        animationSpritePerso.immobile();
                    }
                    break;
                case D:
                    if (jeu.getPerso().getDirection() == Dir.droite) {
                        try {
                            jeu.getPerso().seDeplacer();
                            animationSpritePerso.start();
                        } catch (ObstacleException e) {}
                    } else {
                        jeu.getPerso().setDirection(Dir.droite);
                        animationSpritePerso.immobile();
                    }
                    break;
                case P:
                    jeu.getPerso().interactionBois();
                    break;
                default:
                    break;
            }
    }
}
