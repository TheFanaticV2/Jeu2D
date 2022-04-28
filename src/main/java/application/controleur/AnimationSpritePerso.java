package application.controleur;

import application.modele.Jeu;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.StackPane;

import static application.controleur.Camera.TUILE_TAILLE;

public class AnimationSpritePerso extends AnimationTimer {

    private Jeu jeu;
    private StackPane spritesPerso;
    private long lastUpdate;
    private long latence;
    private double decalage;
    private boolean running;

    public AnimationSpritePerso(Jeu jeu, StackPane SpritesPerso) {
        this.jeu = jeu;
        this.spritesPerso = SpritesPerso;
        this.lastUpdate = 0;
        this.latence = 75_000_000;
        this.decalage = TUILE_TAILLE - TUILE_TAILLE/3;
        this.running = false;
    }

    @Override
    public void start() {
        super.start();
        running = true;
    }

    @Override
    public void stop() {
        super.stop();
        running = false;
    }

    @Override
    public void handle(long now) {
        if (now - lastUpdate >= latence) {
            animation();
            lastUpdate = now;
            //System.out.println(now);
        } else if (decalage < 0) {
            immobile();
            decalage = TUILE_TAILLE - TUILE_TAILLE/3;
            stop();
        }

    }

    private void animation() {
        int i = 0;
        while (!spritesPerso.getChildren().get(i).isVisible()) i++;
        spritesPerso.getChildren().get(i).setVisible(false);

        switch (jeu.getPerso().getDirPrecedente()) {
            case haut:
                spritesPerso.setTranslateX(jeu.getPerso().getX() * (TUILE_TAILLE));
                spritesPerso.setTranslateY(jeu.getPerso().getY() * (TUILE_TAILLE) + decalage);
                if (i == 1) spritesPerso.getChildren().get(2).setVisible(true);
                else spritesPerso.getChildren().get(1).setVisible(true);
                break;
            case bas:
                spritesPerso.setTranslateX(jeu.getPerso().getX() * (TUILE_TAILLE));
                spritesPerso.setTranslateY(jeu.getPerso().getY() * (TUILE_TAILLE) - decalage);
                if (i == 4) spritesPerso.getChildren().get(5).setVisible(true);
                else spritesPerso.getChildren().get(4).setVisible(true);
                break;
            case gauche:
                spritesPerso.setTranslateX(jeu.getPerso().getX() * (TUILE_TAILLE) + decalage);
                spritesPerso.setTranslateY(jeu.getPerso().getY() * (TUILE_TAILLE));
                if (i == 7) spritesPerso.getChildren().get(8).setVisible(true);
                else spritesPerso.getChildren().get(7).setVisible(true);
                break;
            case droite:
                spritesPerso.setTranslateX(jeu.getPerso().getX() * (TUILE_TAILLE) - decalage);
                spritesPerso.setTranslateY(jeu.getPerso().getY() * (TUILE_TAILLE));
                if (i == 10) spritesPerso.getChildren().get(11).setVisible(true);
                else spritesPerso.getChildren().get(10).setVisible(true);
                break;
            default: break;
        }
        decalage-=TUILE_TAILLE/3;
    }

    public void immobile() {
        for (int i = 0; i  < spritesPerso.getChildren().size(); i++)
            spritesPerso.getChildren().get(i).setVisible(false);

        switch (jeu.getPerso().getDirection()) {
            case haut : spritesPerso.getChildren().get(0).setVisible(true); break;
            case bas : spritesPerso.getChildren().get(3).setVisible(true); break;
            case gauche : spritesPerso.getChildren().get(6).setVisible(true); break;
            case droite : spritesPerso.getChildren().get(9).setVisible(true); break;
            default: break;
        }
    }

    public void changementMap() {
        immobile();
        spritesPerso.setTranslateX(jeu.getPerso().getX() * (TUILE_TAILLE));
        spritesPerso.setTranslateY(jeu.getPerso().getY() * (TUILE_TAILLE));
    }

    public boolean isRunning() {
        return running;
    }
}
