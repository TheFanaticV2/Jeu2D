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
    public final static int TUILE_TAILLE = 48;

    private Grille grille;
    private AnimationSpritePerso animationSpritePerso;

    @FXML private StackPane root;
    @FXML private Pane tuilesFond, tuilesObjet;
    @FXML private StackPane spritesPerso;
    @FXML private Label bois;
    @FXML private Label inventaire;
    @FXML private HBox hBoxPv;
    @FXML private Pane gameOver;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        grille = new Grille(27, 15);
        inventaire.textProperty().bind(grille.getPerso().getInventaire().getStockageTotalProperty().asString());
        bois.textProperty().bind(grille.getPerso().getInventaire().getNbBoisProperty().asString());
        animationSpritePerso = new AnimationSpritePerso(grille, spritesPerso);
        root.addEventHandler(KeyEvent.KEY_PRESSED, new KeyPressed(this, grille, animationSpritePerso));
        root.addEventHandler(KeyEvent.KEY_RELEASED, new KeyReleased(animationSpritePerso));
        grille.getPerso().getPvProperty().addListener(new ListenerPv(hBoxPv, gameOver));
        grille.getListeBois().addListener(new ListenerBois(tuilesObjet, grille));
        construireGUI();
    }

    private void construireGUI() {
        contruireMap();
        construirePerso();
        construireCoeur();
        construireBois();
    }

    private void construireCoeur() {
        ImageView coeur;
        for (int i = 1; i < grille.getPerso().getPv() + 1; i++) {
            coeur = new ImageView(new Image("file:src/main/resources/application/sprite/interface/coeur.png"));
            coeur.setId(String.valueOf(i));
            coeur.setFitWidth(30);
            coeur.setFitHeight(30);
            hBoxPv.getChildren().add(coeur);
        }
    }

    private void contruireMap() {
        ImageView img;
        for (Sommet s : grille.getListeAdj().keySet()) {
            if (s.getGroundType() == 0)
                img = new ImageView(new Image("file:src/main/resources/application/sprite/decor/LGrass5.png"));
            else
                img = new ImageView(new Image("file:src/main/resources/application/sprite/decor/Sand1.png"));
            img.setFitWidth(TUILE_TAILLE);
            img.setFitHeight(TUILE_TAILLE);
            img.setX(s.getX() * TUILE_TAILLE);
            img.setY(s.getY() * TUILE_TAILLE);
            tuilesFond.getChildren().add(img);
        }
    }

    private void construirePerso() {
        spritesPerso.setTranslateX(grille.getPerso().getX() * (TUILE_TAILLE));
        spritesPerso.setTranslateY(grille.getPerso().getY() * (TUILE_TAILLE));
        for (int i = 0; i < spritesPerso.getChildren().size(); i++)
            spritesPerso.getChildren().get(i).setVisible(false);
        spritesPerso.getChildren().get(3).setVisible(true);
    }

    private void construireBois() {

    }
}
