package application.controleur;

import application.modele.Arbre;
import application.modele.Bois;
import application.modele.Jeu;
import application.modele.Sommet;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Camera {

    public final static int TUILE_TAILLE = 48;

    private int x;
    private int y;
    private int width;
    private int height;

    private Jeu jeu;

    private StackPane root;
    private Pane tuilesFond, tuilesObjet;
    private HBox hBoxPv;
    private Pane gameOver;


    public Camera(Jeu jeu, StackPane root, Pane tuilesFond, Pane tuilesObjet, HBox hBoxPv, Pane gameOver) {
        this.jeu = jeu;
        this.root = root;
        this.tuilesFond = tuilesFond;
        this.tuilesObjet = tuilesObjet;
        this.hBoxPv = hBoxPv;
        this.gameOver = gameOver;
        width = 11; height = 9;
        x = this.jeu.getPerso().getX() - width/2;
        y = this.jeu.getPerso().getY() - height/2;
        jeu.getChangementDeMapProperty().addListener(new ListenerMap(this, jeu));
        jeu.getPerso().getPvProperty().addListener(new ListenerPv(hBoxPv, gameOver));
        construireGUI();
    }

    private void construireGUI() {
        contruireMap();
        construireCoeur();
        construireObjet();
    }

    public void construireObjet() {
        tuilesObjet.getChildren().clear();
        construireBois();
        construireArbre();
    }

    private void construireCoeur() {
        ImageView coeur;
        for (int i = 1; i < jeu.getPerso().getPv() + 1; i++) {
            coeur = new ImageView(new Image("file:src/main/resources/application/sprite/interface/coeur.png"));
            coeur.setId(String.valueOf(i));
            coeur.setFitWidth(30);
            coeur.setFitHeight(30);
            hBoxPv.getChildren().add(coeur);
        }
    }

    public void contruireMap() {
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
        tuilesFond.getChildren().clear();
        ImageView img;
        for (Sommet s : jeu.getGrilleActuelle().getListeAdj().keySet()) {
            if (s.getX() >= x && s.getX() < x+width && s.getY() >= y && s.getY() < y+height) {
                switch (s.getGroundType()) {
                    case 0: img = new ImageView(new Image("file:src/main/resources/application/sprite/decor/LGrass5.png")); break;
                    case 1: img = new ImageView(new Image("file:src/main/resources/application/sprite/decor/Sand1.png")); break;
                    case 4: img = new ImageView(new Image("file:src/main/resources/application/sprite/decor/water.png")); break;
                    default: img = null; break;
                }
                img.setFitWidth(TUILE_TAILLE);
                img.setFitHeight(TUILE_TAILLE);
                img.setX((s.getX()-x) * TUILE_TAILLE);
                img.setY((s.getY()-y) * TUILE_TAILLE);
                tuilesFond.getChildren().add(img);
            }
        }
    }

    private void construireBois() {
        ListenerBois listenerBois = new ListenerBois(tuilesObjet, x, y);
        jeu.getGrilleActuelle().getListeBois().addListener(listenerBois);
        for (Bois bois : jeu.getGrilleActuelle().getListeBois()) {
            if (bois.getX() >= x && bois.getX() < x+width && bois.getY() >= y && bois.getY() < y+height)
                listenerBois.ajouterBois(bois);
        }
    }

    private void construireArbre() {
        for (Arbre arbre : jeu.getGrilleActuelle().getListeArbre()) {
            if (arbre.getX() >= x && arbre.getX() < x+width && arbre.getY() >= y && arbre.getY() < y+height) {
                ImageView img = new ImageView(new Image("file:src/main/resources/application/sprite/decor/tree.png"));
                img.setFitWidth(TUILE_TAILLE);
                img.setFitHeight(TUILE_TAILLE);
                img.setX((arbre.getX()-x) * TUILE_TAILLE);
                img.setY((arbre.getY()-y) * TUILE_TAILLE);
                tuilesObjet.getChildren().add(img);
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
