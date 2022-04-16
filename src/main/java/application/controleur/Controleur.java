package application.controleur;

import application.Param;
import application.modele.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controleur implements Initializable {

    private Grille grille;
    private AnimationSpritePerso animationSpritePerso;
    private Image imageBois;

    @FXML private Pane tuilesFond, tuilesPerso, tuilesObjet;
    @FXML private StackPane spritesPerso;
    @FXML private Label bois;
    @FXML private Label inventaire;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        grille = new Grille(Param.WIDTH, Param.HEIGHT);
        inventaire.textProperty().bind(grille.getPerso().getInventaire().getStockageTotalProperty().asString());
        bois.textProperty().bind(grille.getPerso().getInventaire().getNbBoisProperty().asString());
        animationSpritePerso = new AnimationSpritePerso(grille, spritesPerso);
        imageBois = new Image("file:src/main/resources/application/sprite/decor/cutted_tree.png");
        contruireMap();
        construirePerso();
        construireBois();
    }

//    private void contruireMap() {
//        Rectangle rectangle;
//        for (int i = 0; i < Param.WIDTH; i++)
//            for (int j = 0; j < Param.HEIGHT; j++) {
//                rectangle = new Rectangle(i * Param.TUILE_TAILLE, j * Param.TUILE_TAILLE, Param.TUILE_TAILLE, Param.TUILE_TAILLE);
//                rectangle.setFill(Param.TUILE_NORMAL_COULEUR);
//                rectangle.setStrokeType(StrokeType.INSIDE);
//                rectangle.setStroke(Param.TUILE_BORDURE_COULEUR);
//                rectangle.setStrokeWidth(Param.TUILE_TAILLE_BORDURE);
//                tuiles.getChildren().add(rectangle);
//            }
//    }

    private void contruireMap() {
        ImageView img;
        for (int i = 0; i < Param.WIDTH; i++)
            for (int j = 0; j < Param.HEIGHT; j++) {
                img = new ImageView(new Image("file:src/main/resources/application/sprite/decor/LGrass5.png"));
                img.setFitWidth(Param.TUILE_TAILLE);
                img.setFitHeight(Param.TUILE_TAILLE);
                img.setX(i * Param.TUILE_TAILLE);
                img.setY(j * Param.TUILE_TAILLE);
                tuilesFond.getChildren().add(img);
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
            affichageBois(bois);
        }
    }

    private void affichageBois(Sommet s) {
        if (s instanceof Bois) { //poser bois
            ImageView img = new ImageView(imageBois);
            img.setFitWidth(Param.TUILE_TAILLE);
            img.setFitHeight(Param.TUILE_TAILLE);
            img.setX(s.getX() * Param.TUILE_TAILLE);
            img.setY(s.getY() * Param.TUILE_TAILLE);
            tuilesObjet.getChildren().add(img);
        } else if (s != null) { //retirer bois
            int i = 0;
            while (((ImageView) tuilesObjet.getChildren().get(i)).getX() != s.getX() * Param.TUILE_TAILLE || ((ImageView) tuilesObjet.getChildren().get(i)).getY() != s.getY() * Param.TUILE_TAILLE) i++;
            tuilesObjet.getChildren().remove(i);
        }
    }

//    private void affichageTuile(Sommet s) {
//        ((Rectangle) tuiles.getChildren().get(s.getX() * Param.HEIGHT + s.getY())).setFill(Param.TUILE_NORMAL_COULEUR);
//        ((Rectangle) tuiles.getChildren().get(s.getX() * Param.HEIGHT + s.getY())).setStrokeWidth(Param.TUILE_TAILLE_BORDURE);
//        ((Rectangle) tuiles.getChildren().get(s.getX() * Param.HEIGHT + s.getY())).setStroke(Param.TUILE_BORDURE_COULEUR);
//    }

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
