package application.controleur;

import application.controleur.listener.ListenerBois;
import application.controleur.listener.ListenerCamera;
import application.controleur.listener.ListenerMap;
import application.controleur.listener.ListenerPv;
import application.modele.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Camera {

    public final static int TUILE_TAILLE = 48;

    public final static int WIDTH = 11;
    public final static int HEIGHT = 9;

    private IntegerProperty xProperty;
    private IntegerProperty yProperty;

    private Jeu jeu;

    private StackPane root;
    private Pane tuilesFond, tuilesObjet, tuilesPerso;
    private StackPane spritesPerso;
    private Label inventaire, bois;
    private HBox hBoxPv;
    private Label gameOver;

    public Camera(Jeu jeu, StackPane root, Pane tuilesFond, Pane tuilesObjet, Pane tuilesPerso, StackPane spritesPerso, Label bois, Label inventaire, HBox hBoxPv, Label gameOver) {
        this.jeu = jeu;
        this.root = root;
        this.tuilesFond = tuilesFond;
        this.tuilesFond.setMaxSize(WIDTH*TUILE_TAILLE, HEIGHT*TUILE_TAILLE);
        this.tuilesObjet = tuilesObjet;
        this.tuilesObjet.setMaxSize(WIDTH*TUILE_TAILLE, HEIGHT*TUILE_TAILLE);
        this.spritesPerso = spritesPerso;
        this.tuilesPerso = tuilesPerso;
        this.tuilesPerso.setMaxSize(WIDTH*TUILE_TAILLE, HEIGHT*TUILE_TAILLE);
        this.bois = bois;
        this.inventaire = inventaire;
        this.hBoxPv = hBoxPv;
        this.gameOver = gameOver;
        this.gameOver.setMaxSize(WIDTH*TUILE_TAILLE, HEIGHT*TUILE_TAILLE);
        initialiser();
    }

    private void initialiser() {
        xProperty = new SimpleIntegerProperty(this.jeu.getPerso().getX() - WIDTH / 2);
        this.jeu.getPerso().getXProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() >= WIDTH / 2 && newValue.intValue() <= Grille.WIDTH - WIDTH / 2 - 1) {
                    setX(newValue.intValue() - WIDTH / 2);
                    contruireMap();
                    construireObjet();
                } else if (Math.abs(oldValue.intValue() - newValue.intValue()) == Grille.WIDTH) {
                    if (oldValue.intValue() - newValue.intValue() > 0)
                        setX(0);
                    else
                        setX(Grille.WIDTH - WIDTH);
                    contruireMap();
                    construireObjet();
                }
            }
        });
        yProperty = new SimpleIntegerProperty(this.jeu.getPerso().getY() - HEIGHT / 2);
        this.jeu.getPerso().getYProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue() >= HEIGHT / 2 && newValue.intValue() <= Grille.HEIGHT - HEIGHT / 2 - 1) {
                    setY(newValue.intValue() - HEIGHT / 2);
                    contruireMap();
                    construireObjet();
                } else if (Math.abs(oldValue.intValue() - newValue.intValue()) == Grille.HEIGHT) {
                    if (oldValue.intValue() - newValue.intValue() > 0)
                        setY(0);
                    else
                        setY(Grille.HEIGHT - HEIGHT);
                    contruireMap();
                    construireObjet();
                }
            }
        });
        jeu.getChangementDeMapProperty().addListener(new ListenerMap(this, jeu));
        jeu.getPerso().getPvProperty().addListener(new ListenerPv(hBoxPv, gameOver));
        contruireMap(); construireCoeur(); construireObjet();
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
        tuilesFond.getChildren().clear();
        ImageView img;
        for (Sommet s : jeu.getGrilleActuelle().getListeAdj().keySet()) {
            if (s.getX() >= xProperty.getValue() && s.getX() < xProperty.getValue() + WIDTH && s.getY() >= yProperty.getValue() && s.getY() < yProperty.getValue() + HEIGHT) {
                switch (s.getGroundType()) {
                    case 0: img = new ImageView(new Image("file:src/main/resources/application/sprite/decor/LGrass5.png")); break;
                    case 1: img = new ImageView(new Image("file:src/main/resources/application/sprite/decor/Sand1.png")); break;
                    case 4: img = new ImageView(new Image("file:src/main/resources/application/sprite/decor/water.png")); break;
                    default: img = null; break;
                }
                img.setFitWidth(TUILE_TAILLE);
                img.setFitHeight(TUILE_TAILLE);
                img.setX((s.getX()- xProperty.getValue()) * TUILE_TAILLE);
                img.setY((s.getY()- yProperty.getValue()) * TUILE_TAILLE);
                tuilesFond.getChildren().add(img);
            }
        }
    }

    private void construireBois() {
        ListenerBois listenerBois = new ListenerBois(tuilesObjet, this);
        jeu.getGrilleActuelle().getListeBois().addListener(listenerBois);
        for (Bois bois : jeu.getGrilleActuelle().getListeBois()) {
            if (bois.getX() >= xProperty.getValue() && bois.getX() < xProperty.getValue() + WIDTH && bois.getY() >= yProperty.getValue() && bois.getY() < yProperty.getValue() + HEIGHT)
                listenerBois.ajouterBois(bois);
        }
    }

    private void construireArbre() {
        for (Arbre arbre : jeu.getGrilleActuelle().getListeArbre()) {
            if (arbre.getX() >= xProperty.getValue() && arbre.getX() < xProperty.getValue() + WIDTH && arbre.getY() >= yProperty.getValue() && arbre.getY() < yProperty.getValue() + HEIGHT) {
                ImageView img = new ImageView(new Image("file:src/main/resources/application/sprite/decor/tree.png"));
                img.setFitWidth(TUILE_TAILLE);
                img.setFitHeight(TUILE_TAILLE);
                img.setX((arbre.getX()- xProperty.getValue()) * TUILE_TAILLE);
                img.setY((arbre.getY()- yProperty.getValue()) * TUILE_TAILLE);
                tuilesObjet.getChildren().add(img);
            }
        }
    }

    public final int getX() {
        return xProperty.getValue();
    }

    public final int getY() {
        return yProperty.getValue();
    }

    public final void setX(int x) {
        this.xProperty.setValue(x);
    }

    public final void setY(int y) {
        this.yProperty.setValue(y);
    }

    public final IntegerProperty xProperty() {
        return xProperty;
    }

    public final IntegerProperty yProperty() {
        return yProperty;
    }
}
