package application.controleur;

import application.modele.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class GameLoop implements Runnable {

    private boolean running;
    private Thread gameThread;

    private Jeu jeu;
    private Camera camera;

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
        jeu = new Jeu();
        camera = new Camera(jeu, root, tuilesFond, tuilesObjet, hBoxPv, gameOver);
        jeu.getPerso().setAnimationSpritePerso(new AnimationSpritePerso(jeu, spritesPerso, camera));
        inventaire.textProperty().bind(jeu.getPerso().getInventaire().getStockageTotalProperty().asString());
        bois.textProperty().bind(jeu.getPerso().getInventaire().getNbBoisProperty().asString());
        root.addEventHandler(KeyEvent.KEY_PRESSED, new KeyPressed(jeu));
        root.addEventHandler(KeyEvent.KEY_RELEASED, new KeyReleased(jeu.getPerso()));
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
        final double amountOfTicks = 15;
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
                //System.out.println(updates + "Ticks, Fps " + frames);
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
}
