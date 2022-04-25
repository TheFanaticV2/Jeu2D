package application.controleur;

import application.modele.Grille;
import javafx.animation.AnimationTimer;
import javafx.scene.layout.StackPane;

public class AnimationSpritePerso extends AnimationTimer {
    private Grille grille;
    private StackPane spritesPerso;
    private long lastUpdate;
    private long latence;
    private double decalage;
    private boolean running;

    public AnimationSpritePerso(Grille grille, StackPane SpritesPerso) {
        this.grille = grille;
        this.spritesPerso = SpritesPerso;
        this.lastUpdate = 0;
        this.latence = 75_000_000;
        this.decalage = Controleur.TUILE_TAILLE - Controleur.TUILE_TAILLE/3;
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
            decalage = Controleur.TUILE_TAILLE - Controleur.TUILE_TAILLE/3;
            stop();
        }

    }

    private void animation() {
        int i = 0;
        while (!spritesPerso.getChildren().get(i).isVisible()) i++;
        spritesPerso.getChildren().get(i).setVisible(false);

        switch (grille.getPerso().getDirection()) {
            case haut:
                spritesPerso.setTranslateX(grille.getPerso().getX() * (Controleur.TUILE_TAILLE));
                spritesPerso.setTranslateY(grille.getPerso().getY() * (Controleur.TUILE_TAILLE) + decalage);
                if (i == 1) spritesPerso.getChildren().get(2).setVisible(true);
                else spritesPerso.getChildren().get(1).setVisible(true);
                break;
            case bas:
                spritesPerso.setTranslateX(grille.getPerso().getX() * (Controleur.TUILE_TAILLE));
                spritesPerso.setTranslateY(grille.getPerso().getY() * (Controleur.TUILE_TAILLE) - decalage);
                if (i == 4) spritesPerso.getChildren().get(5).setVisible(true);
                else spritesPerso.getChildren().get(4).setVisible(true);
                break;
            case gauche:
                spritesPerso.setTranslateX(grille.getPerso().getX() * (Controleur.TUILE_TAILLE) + decalage);
                spritesPerso.setTranslateY(grille.getPerso().getY() * (Controleur.TUILE_TAILLE));
                if (i == 7) spritesPerso.getChildren().get(8).setVisible(true);
                else spritesPerso.getChildren().get(7).setVisible(true);
                break;
            case droite:
                spritesPerso.setTranslateX(grille.getPerso().getX() * (Controleur.TUILE_TAILLE) - decalage);
                spritesPerso.setTranslateY(grille.getPerso().getY() * (Controleur.TUILE_TAILLE));
                if (i == 10) spritesPerso.getChildren().get(11).setVisible(true);
                else spritesPerso.getChildren().get(10).setVisible(true);
                break;
            default: break;
        }
        decalage-=Controleur.TUILE_TAILLE/3;
    }

    public void immobile() {
        for (int i = 0; i  < spritesPerso.getChildren().size(); i++)
            spritesPerso.getChildren().get(i).setVisible(false);

        switch (grille.getPerso().getDirection()) {
            case haut : spritesPerso.getChildren().get(0).setVisible(true); break;
            case bas : spritesPerso.getChildren().get(3).setVisible(true); break;
            case gauche : spritesPerso.getChildren().get(6).setVisible(true); break;
            case droite : spritesPerso.getChildren().get(9).setVisible(true); break;
            default: break;
        }
    }

    public boolean isRunning() {
        return running;
    }
}
