package application.controleur;

import application.modele.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controleur implements Initializable {
//    public final static int TUILE_TAILLE = 48;
//
//    private Jeu jeu;
//    private AnimationSpritePerso animationSpritePerso;
//    private ListenerBois listenerBois;

    @FXML private StackPane root;
    @FXML private Pane tuilesFond, tuilesObjet;
    @FXML private StackPane spritesPerso;
    @FXML private Label bois;
    @FXML private Label inventaire;
    @FXML private HBox hBoxPv;
    @FXML private Pane gameOver;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameLoop gameLoop = new GameLoop(this.root, this.tuilesFond, this.tuilesObjet, this.spritesPerso, this.bois, this.inventaire, this.hBoxPv, this.gameOver);
        gameLoop.star();
    }

//    private void construireGUI() {
//        contruireMap();
//        construirePerso();
//        construireCoeur();
//        construireObjet();
//    }
//
//    public void construireObjet() {
//        tuilesObjet.getChildren().clear();
//        construireBois();
//        construireArbre();
//    }
//
//    private void construireCoeur() {
//        ImageView coeur;
//        for (int i = 1; i < jeu.getPerso().getPv() + 1; i++) {
//            coeur = new ImageView(new Image("file:src/main/resources/application/sprite/interface/coeur.png"));
//            coeur.setId(String.valueOf(i));
//            coeur.setFitWidth(30);
//            coeur.setFitHeight(30);
//            hBoxPv.getChildren().add(coeur);
//        }
//    }
//
//    public void contruireMap() {
//        tuilesFond.getChildren().clear();
//        ImageView img;
//        for (Sommet s : jeu.getGrilleActuelle().getListeAdj().keySet()) {
//            switch (s.getGroundType()) {
//                case 0: img = new ImageView(new Image("file:src/main/resources/application/sprite/decor/LGrass5.png")); break;
//                case 1: img = new ImageView(new Image("file:src/main/resources/application/sprite/decor/Sand1.png")); break;
//                case 4: img = new ImageView(new Image("file:src/main/resources/application/sprite/decor/water.png")); break;
//                default: img = null; break;
//            }
//            img.setFitWidth(TUILE_TAILLE);
//            img.setFitHeight(TUILE_TAILLE);
//            img.setX(s.getX() * TUILE_TAILLE);
//            img.setY(s.getY() * TUILE_TAILLE);
//            tuilesFond.getChildren().add(img);
//        }
//    }
//
//    private void construirePerso() {
//        spritesPerso.setTranslateX(jeu.getPerso().getX() * (TUILE_TAILLE));
//        spritesPerso.setTranslateY(jeu.getPerso().getY() * (TUILE_TAILLE));
//        for (int i = 0; i < spritesPerso.getChildren().size(); i++)
//            spritesPerso.getChildren().get(i).setVisible(false);
//        spritesPerso.getChildren().get(3).setVisible(true);
//    }
//
//    private void construireBois() {
//        for (Bois bois : jeu.getGrilleActuelle().getListeBois()) {
//            listenerBois.ajouterBois(bois);
//        }
//    }
//
//    private void construireArbre() {
//        for (Arbre arbre : jeu.getGrilleActuelle().getListeArbre()) {
//            ImageView img = new ImageView(new Image("file:src/main/resources/application/sprite/decor/tree.png"));
//            img.setFitWidth(TUILE_TAILLE);
//            img.setFitHeight(TUILE_TAILLE);
//            img.setX(arbre.getX() * TUILE_TAILLE);
//            img.setY(arbre.getY() * TUILE_TAILLE);
//            tuilesObjet.getChildren().add(img);
//        }
//    }

}
