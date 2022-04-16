package application.controleur;

import application.Param;
import application.modele.*;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.net.URL;
import java.util.ResourceBundle;

public class Controleur implements Initializable {

    private Grille grille;
    private AnimationSpritePerso animationSpritePerso;

    @FXML
    private Pane tuiles;

    @FXML
    private StackPane spritesPerso;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        grille = new Grille(Param.WIDTH, Param.HEIGHT);
        contruireMap();
        construirePerso();
        construireBois();
        animationSpritePerso = new AnimationSpritePerso(grille, spritesPerso);
    }

    private void contruireMap() {
        Rectangle rectangle;
        for (int i = 0; i < Param.WIDTH; i++)
            for (int j = 0; j < Param.HEIGHT; j++) {
                rectangle = new Rectangle(i* Param.TUILE_TAILLE, j* Param.TUILE_TAILLE, Param.TUILE_TAILLE, Param.TUILE_TAILLE);
                rectangle.setFill(Param.TUILE_NORMAL_COULEUR);
                rectangle.setStrokeType(StrokeType.INSIDE);
                rectangle.setStroke(Param.TUILE_BORDURE_COULEUR);
                rectangle.setStrokeWidth(Param.TUILE_TAILLE_BORDURE);
                tuiles.getChildren().add(rectangle);
            }
    }

    private void construirePerso() {
        //spritesPerso.translateXProperty().bind(grille.getPerso().getXProperty().multiply(Param.TUILE_TAILLE));
        //spritesPerso.translateYProperty().bind(grille.getPerso().getYProperty().multiply(Param.TUILE_TAILLE));
        spritesPerso.setTranslateX(grille.getPerso().getX() * (Param.TUILE_TAILLE));
        spritesPerso.setTranslateY(grille.getPerso().getY() * (Param.TUILE_TAILLE));
        for (int i = 0; i < spritesPerso.getChildren().size(); i++)
            spritesPerso.getChildren().get(i).setVisible(false);
        spritesPerso.getChildren().get(3).setVisible(true);
    }

    private void construireBois() {
        for (Bois bois : grille.getListeBois()) {
            affichageBois(bois );
        }
    }

    private void affichageBois(Sommet s) {
        if (s instanceof Bois) {
            ((Rectangle) tuiles.getChildren().get(s.getX() * Param.HEIGHT + s.getY())).setFill(Param.TUILE_BOIS_COULEUR);
            ((Rectangle) tuiles.getChildren().get(s.getX() * Param.HEIGHT + s.getY())).setStrokeWidth(Param.TUILE_BOIS_TAILLE_BORDURE);
            ((Rectangle) tuiles.getChildren().get(s.getX() * Param.HEIGHT + s.getY())).setStroke(Param.TUILE_BOIS_BORDURE_COULEUR);
        } else
            affichageTuile(s);
    }

    private void affichageTuile(Sommet s) {
        ((Rectangle) tuiles.getChildren().get(s.getX() * Param.HEIGHT + s.getY())).setFill(Param.TUILE_NORMAL_COULEUR);
        ((Rectangle) tuiles.getChildren().get(s.getX() * Param.HEIGHT + s.getY())).setStrokeWidth(Param.TUILE_TAILLE_BORDURE);
        ((Rectangle) tuiles.getChildren().get(s.getX() * Param.HEIGHT + s.getY())).setStroke(Param.TUILE_BORDURE_COULEUR);
    }

    private void affichagePerso() {
            animationSpritePerso.start();
    }

    /*@FXML
    public void keyPressed(KeyEvent event) {
        if (!animationSpritePerso.isRunning())
            switch (event.getCode()) {
                case Z:
                    if (grille.getPerso().getDirection() == Dir.haut) {
                        try {
                            grille.getPerso().seDeplacerHaut();
                            affichagePerso();
                        } catch (ObstacleException e) {
                        }
                    } else grille.getPerso().setDirection(Dir.haut);
                    break;
                case S:
                    if (grille.getPerso().getDirection() == Dir.bas) {
                        try {
                            grille.getPerso().seDeplacerBas();
                            affichagePerso();
                        } catch (ObstacleException e) {
                        }
                    } else grille.getPerso().setDirection(Dir.bas);
                    break;
                case Q:
                    if (grille.getPerso().getDirection() == Dir.gauche) {
                        try {
                            grille.getPerso().seDeplacerGauche();
                            affichagePerso();
                        } catch (ObstacleException e) {
                        }
                    } else grille.getPerso().setDirection(Dir.gauche);
                    break;
                case D:
                    if (grille.getPerso().getDirection() == Dir.droite) {
                        try {
                            grille.getPerso().seDeplacerDroite();
                            affichagePerso();
                        } catch (ObstacleException e) {
                        }
                    } else grille.getPerso().setDirection(Dir.droite);
                    break;
                case P:
                    affichageBois(grille.getPerso().interactionBois());
                    break;
                default:
                    break;
            }
    }*/

    public void keyPressed(KeyEvent event) {
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
                    affichageBois(grille.getPerso().interactionBois());
                    break;
                default:
                    break;
            }
    }
    @FXML
    public void keyReleased(KeyEvent event) {
        animationSpritePerso.immobile();
    }
}
