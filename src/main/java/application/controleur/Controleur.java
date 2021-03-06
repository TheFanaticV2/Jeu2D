package application.controleur;

import application.Param;
import application.modele.Bois;
import application.modele.Grille;
import application.modele.Sommet;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

import java.net.URL;
import java.util.ResourceBundle;

public class Controleur implements Initializable {

    private Grille grille;
    private Rectangle ennemi;

    @FXML
    private Pane tuiles;
    @FXML
    private Pane mapPerso;

    @FXML
    private ImageView perso;

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
        //perso = new Rectangle(Param.TUILE_TAILLE, Param.TUILE_TAILLE);
        //perso.setFill(Param.PERSO_COULEUR);
        perso.translateXProperty().bind(grille.getPerso().getXProperty().multiply(Param.TUILE_TAILLE));
        perso.translateYProperty().bind(grille.getPerso().getYProperty().multiply(Param.TUILE_TAILLE));
        //mapPerso.getChildren().add(perso);
        mapPerso.toFront();
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

    @FXML
    public void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case Z : grille.getPerso().seDeplacerHaut(); break;
            case S : grille.getPerso().seDeplacerBas(); break;
            case Q : grille.getPerso().seDeplacerGauche(); break;
            case D : grille.getPerso().seDeplacerDroite(); break;
            case P : affichageBois(grille.getPerso().interactionBois()); break;
            default: break;
        }
    }
}
