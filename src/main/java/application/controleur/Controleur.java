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
    @FXML private Pane tuilesFond, tuilesObjet, tuilesPerso;
    @FXML private StackPane spritesPerso;
    @FXML private Label bois;
    @FXML private Label inventaire;
    @FXML private HBox hBoxPv;
    @FXML private Label gameOver;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GameLoop gameLoop = new GameLoop(root, tuilesFond, tuilesObjet, tuilesPerso, spritesPerso, bois, inventaire, hBoxPv, gameOver);
        gameLoop.star();
    }
}
