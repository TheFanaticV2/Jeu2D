package application.controleur;

import application.modele.*;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class GameLoop implements Runnable {

    public final static int TUILE_TAILLE = 48;

    private boolean running;
    private Thread gameThread;
    private Grille grille;
    private AnimationSpritePerso animationSpritePerso;
    private Image imageBois;

    private StackPane root;
    private Pane tuilesFond, tuilesObjet;
    private StackPane spritesPerso;
    private Label bois;
    private Label inventaire;

    public GameLoop(StackPane root, Pane tuilesFond, Pane tuilesObjet, StackPane spritesPerso, Label bois, Label inventaire) {
        running = false;
        this.root = root;
        this.tuilesFond = tuilesFond;
        this.tuilesObjet = tuilesObjet;
        this.spritesPerso = spritesPerso;
        this.bois = bois;
        this.inventaire = inventaire;
        initialiser();
    }

    public void initialiser() {
        grille = new Grille(27, 15);
        //inventaire.textProperty().bind(grille.getPerso().getInventaire().getStockageTotalProperty().asString());
        //bois.textProperty().bind(grille.getPerso().getInventaire().getNbBoisProperty().asString());
        animationSpritePerso = new AnimationSpritePerso(grille, spritesPerso);
        imageBois = new Image("file:src/main/resources/application/sprite/decor/cutted_tree.png");
        contruireMap(); construirePerso(); construireBois();
        root.addEventHandler(KeyEvent.KEY_PRESSED, new KeyPressed(grille));
        root.addEventHandler(KeyEvent.KEY_RELEASED, new KeyReleased(grille));
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
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates + "Ticks, Fps " + frames);
                updates = 0;
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        //deplacement
        if (!animationSpritePerso.isRunning())
            grille.getPerso().seDeplacer();
        //bois
        if (grille.getPerso().isInteragitBois()) {
            grille.getPerso().interactionBois();
            grille.getPerso().setInteragitBois(false);
        }
    }


    private void render() {
        //animation deplacement
        affichagePerso();
        affichageBois(grille.getPerso().getBoisInteraction());
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
            grille.getPerso().setBoisInteraction(null);
        } else if (grille.getPerso().getBoisInteraction() != null) { //retirer bois
            int i = 0;
            while (((ImageView) tuilesObjet.getChildren().get(i)).getX() != s.getX() * TUILE_TAILLE || ((ImageView) tuilesObjet.getChildren().get(i)).getY() != s.getY() * TUILE_TAILLE) i++;
            tuilesObjet.getChildren().remove(i);
            grille.getPerso().setBoisInteraction(null);
        }
        inventaire.setText(String.valueOf(grille.getPerso().getInventaire().getStockageTotal()));
        bois.setText(String.valueOf(grille.getPerso().getInventaire().getNbBois()));
    }

    public void affichagePerso() {
        if (!animationSpritePerso.isRunning()) {
            if (grille.getPerso().issEstDeplace()) {
                animationSpritePerso.start();
                grille.getPerso().setsEstDeplace(false);
            } else if (grille.getPerso().isaChangeDeDirection()) {
                animationSpritePerso.immobile();
                grille.getPerso().setaChangeDeDirection(false);
            }
        }

    }
}
