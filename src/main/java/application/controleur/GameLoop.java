package application.controleur;

import application.modele.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameLoop implements Runnable {

    public final static int TUILE_TAILLE = 48;

    private boolean running;
    private Thread gameThread;

    private Jeu jeu;
    private ListenerBois listenerBois;


    private StackPane root;
    private Pane tuilesFond, tuilesObjet;
    private StackPane spritesPerso;
    private Label bois;
    private Label inventaire;
    private HBox hBoxPv;
    private Pane gameOver;


    public GameLoop(StackPane root, Pane tuilesFond, Pane tuilesObjet, StackPane spritesPerso, Label bois, Label inventaire, HBox hBoxPv, Pane gameOver) {
        this.root = root;
        this.tuilesFond = tuilesFond;
        this.tuilesObjet = tuilesObjet;
        this.spritesPerso = spritesPerso;
        this.bois = bois;
        this.inventaire = inventaire;
        this.hBoxPv = hBoxPv;
        this.gameOver = gameOver;
        initialiser();
    }

    public void initialiser() {
        jeu = new Jeu(spritesPerso);
        inventaire.textProperty().bind(jeu.getPerso().getInventaire().getStockageTotalProperty().asString());
        bois.textProperty().bind(jeu.getPerso().getInventaire().getNbBoisProperty().asString());
        root.addEventHandler(KeyEvent.KEY_PRESSED, new KeyPressed(jeu));
        root.addEventHandler(KeyEvent.KEY_RELEASED, new KeyReleased(jeu.getPerso()));
        jeu.getPerso().getPvProperty().addListener(new ListenerPv(hBoxPv, gameOver));
        listenerBois = new ListenerBois(tuilesObjet, jeu.getGrilleActuelle());
        jeu.getGrilleActuelle().getListeBois().addListener(listenerBois);
        construireGUI();
        jeu.getChangementDeMapProperty().addListener(new ListenerMap(this, jeu));
    }

    public synchronized void star() {
        if (!running) {
            running = true;
            gameThread = new Thread(this);
            gameThread.start();
        }
    }

    public synchronized void stop() {
        if (running) {
            running = false;
            try {
                gameThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        final double amountOfTicks = 30;
        double nsBetweenTicks = 1_000_000_000 / amountOfTicks;
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsBetweenTicks;
            lastTime = now;
            if (delta >= 1) {
                update();
                updates++;
                delta--;
                render();
                frames++;
            }
//            render();
//            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates + "Ticks, Fps " + frames);
                updates = 0;
                frames = 0;
            }
        }
        stop();
    }

    private void update() {
        jeu.getPerso().udpate();
    }

    private void render() {
        jeu.getPerso().render();
    }

    private void construireGUI() {
        contruireMap();
        construirePerso();
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
        tuilesFond.getChildren().clear();
        ImageView img;
        for (Sommet s : jeu.getGrilleActuelle().getListeAdj().keySet()) {
            switch (s.getGroundType()) {
                case 0: img = new ImageView(new Image("file:src/main/resources/application/sprite/decor/LGrass5.png")); break;
                case 1: img = new ImageView(new Image("file:src/main/resources/application/sprite/decor/Sand1.png")); break;
                case 4: img = new ImageView(new Image("file:src/main/resources/application/sprite/decor/water.png")); break;
                default: img = null; break;
            }
            img.setFitWidth(TUILE_TAILLE);
            img.setFitHeight(TUILE_TAILLE);
            img.setX(s.getX() * TUILE_TAILLE);
            img.setY(s.getY() * TUILE_TAILLE);
            tuilesFond.getChildren().add(img);
        }
    }

    private void construirePerso() {
        spritesPerso.setTranslateX(jeu.getPerso().getX() * (TUILE_TAILLE));
        spritesPerso.setTranslateY(jeu.getPerso().getY() * (TUILE_TAILLE));
        for (int i = 0; i < spritesPerso.getChildren().size(); i++)
            spritesPerso.getChildren().get(i).setVisible(false);
        spritesPerso.getChildren().get(3).setVisible(true);
    }

    private void construireBois() {
        for (Bois bois : jeu.getGrilleActuelle().getListeBois()) {
            listenerBois.ajouterBois(bois);
        }
    }

    private void construireArbre() {
        for (Arbre arbre : jeu.getGrilleActuelle().getListeArbre()) {
            ImageView img = new ImageView(new Image("file:src/main/resources/application/sprite/decor/tree.png"));
            img.setFitWidth(TUILE_TAILLE);
            img.setFitHeight(TUILE_TAILLE);
            img.setX(arbre.getX() * TUILE_TAILLE);
            img.setY(arbre.getY() * TUILE_TAILLE);
            tuilesObjet.getChildren().add(img);
        }
    }

}