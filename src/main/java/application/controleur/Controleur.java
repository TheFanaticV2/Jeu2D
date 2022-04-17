package application.controleur;


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
    public final static int TUILE_TAILLE = 48;

    private Grille grille;
    private AnimationSpritePerso animationSpritePerso;
    private Image imageBois;

    @FXML private StackPane root;
    @FXML private Pane tuilesFond, tuilesObjet;
    @FXML private StackPane spritesPerso;
    @FXML private Label bois;
    @FXML private Label inventaire;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        grille = new Grille(27, 15);
        inventaire.textProperty().bind(grille.getPerso().getInventaire().getStockageTotalProperty().asString());
        bois.textProperty().bind(grille.getPerso().getInventaire().getNbBoisProperty().asString());
        animationSpritePerso = new AnimationSpritePerso(grille, spritesPerso);
        imageBois = new Image("file:src/main/resources/application/sprite/decor/cutted_tree.png");
        contruireMap();
        construirePerso();
        construireBois();
        root.addEventHandler(KeyEvent.KEY_PRESSED, new KeyPressed(this, grille, animationSpritePerso));
        root.addEventHandler(KeyEvent.KEY_RELEASED, new KeyReleased(animationSpritePerso));
    }

    private void contruireMap() {
        ImageView img;
        for (int i = 0; i < grille.getWidth(); i++)
            for (int j = 0; j < grille.getHeight(); j++) {
                img = new ImageView(new Image("file:src/main/resources/application/sprite/decor/LGrass5.png"));
                img.setFitWidth(TUILE_TAILLE);
                img.setFitHeight(TUILE_TAILLE);
                img.setX(i * TUILE_TAILLE);
                img.setY(j * TUILE_TAILLE);
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
        for (Bois bois : grille.getListeBois()) {
            affichageBois(bois);
        }
    }

    public void affichageBois(Sommet s) {
        if (s instanceof Bois) { //poser bois
            ImageView img = new ImageView(imageBois);
            img.setFitWidth(TUILE_TAILLE);
            img.setFitHeight(TUILE_TAILLE);
            img.setX(s.getX() * TUILE_TAILLE);
            img.setY(s.getY() * TUILE_TAILLE);
            tuilesObjet.getChildren().add(img);
        } else if (s != null) { //retirer bois
            int i = 0;
            while (((ImageView) tuilesObjet.getChildren().get(i)).getX() != s.getX() * TUILE_TAILLE || ((ImageView) tuilesObjet.getChildren().get(i)).getY() != s.getY() * TUILE_TAILLE) i++;
            tuilesObjet.getChildren().remove(i);
        }
    }
}
