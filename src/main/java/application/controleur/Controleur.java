package application.controleur;

import application.Param;
import application.modele.Bois;
import application.modele.Dir;
import application.modele.Grille;
import application.modele.Sommet;
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

    @FXML
    private Pane tuiles;

    @FXML
    private StackPane perso;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        grille = new Grille(Param.WIDTH, Param.HEIGHT);
        contruireMap();
        construirePerso();
        construireBois();
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
        perso.translateXProperty().bind(grille.getPerso().getXProperty().multiply(Param.TUILE_TAILLE));
        perso.translateYProperty().bind(grille.getPerso().getYProperty().multiply(Param.TUILE_TAILLE));
        for (int i = 0; i < perso.getChildren().size(); i++)
            perso.getChildren().get(i).setVisible(false);
        for (int i = 0; i < perso.getChildren().size(); i++) {
            perso.getChildren().get(i);
        }
        perso.getChildren().get(3).setVisible(true);
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
        int i = 0;
        while (!perso.getChildren().get(i).isVisible()) i++;
        perso.getChildren().get(i).setVisible(false);

        switch (grille.getPerso().getDirection()) {
            case haut:
                if (i == 1)
                    perso.getChildren().get(2).setVisible(true);
                else
                    perso.getChildren().get(1).setVisible(true);
                break;
            case bas:
                if (i == 4)
                    perso.getChildren().get(5).setVisible(true);
                else
                    perso.getChildren().get(4).setVisible(true);
                break;
            case gauche:
                if (i == 7)
                    perso.getChildren().get(8).setVisible(true);
                else
                    perso.getChildren().get(7).setVisible(true);
                break;
            case droite:
                if (i == 10)
                    perso.getChildren().get(11).setVisible(true);
                else
                    perso.getChildren().get(10).setVisible(true);
                break;
            default:
                break;
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case Z:
                if (grille.getPerso().getDirection() == Dir.haut)
                    grille.getPerso().seDeplacerHaut();
                grille.getPerso().setDirection(Dir.haut);
                affichagePerso(); break;
            case S:
                if (grille.getPerso().getDirection() == Dir.bas)
                    grille.getPerso().seDeplacerBas();
                grille.getPerso().setDirection(Dir.bas);
                affichagePerso(); break;
            case Q:
                if (grille.getPerso().getDirection() == Dir.gauche)
                    grille.getPerso().seDeplacerGauche();
                grille.getPerso().setDirection(Dir.gauche);
                affichagePerso(); break;
            case D:
                if (grille.getPerso().getDirection() == Dir.droite)
                    grille.getPerso().seDeplacerDroite();
                grille.getPerso().setDirection(Dir.droite);
                affichagePerso(); break;
            case P:
                affichageBois(grille.getPerso().interactionBois()); break;
            default: break;
        }
    }

    @FXML
    public void keyReleased(KeyEvent event) {
        for (int i = 0; i  < perso.getChildren().size(); i++)
            perso.getChildren().get(i).setVisible(false);

        switch (grille.getPerso().getDirection()) {
            case haut : perso.getChildren().get(0).setVisible(true); break;
            case bas : perso.getChildren().get(3).setVisible(true); break;
            case gauche : perso.getChildren().get(6).setVisible(true); break;
            case droite : perso.getChildren().get(9).setVisible(true); break;
            default: break;
        }
    }
}
