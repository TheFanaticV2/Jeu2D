package application.modele;

import application.controleur.AnimationSpritePerso;
import application.modele.Exception.ObstacleException;
import application.modele.Exception.PvMaxException;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Personnage {

    private IntegerProperty pvProperty;
    private Jeu jeu;
    private IntegerProperty xProperty;
    private IntegerProperty yProperty;
    private Dir direction, dirPrecedente;
    private boolean seDeplace, changeDirection, interagitBois, changeMap;
    private Inventaire inventaire;
    private AnimationSpritePerso animationSpritePerso;

    public Personnage(Jeu jeu) {
        seDeplace = false; changeDirection = false; interagitBois = false; changeMap = true;
        this.jeu = jeu;
        direction = Dir.bas;
        dirPrecedente = direction;
        inventaire = new Inventaire();
        pvProperty = new SimpleIntegerProperty(5);
        xProperty = new SimpleIntegerProperty(Grille.WIDTH / 2);
        yProperty = new SimpleIntegerProperty(Grille.HEIGHT / 2 - 1);
    }

    public void udpate() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                seDeplacer();
                interactionBois();
            }
        });
    }

    public void render() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                animationPerso();
            }
        });
    }

    private void seDeplacer() {
        try {
            if (memeDirection() && seDeplace && !animationSpritePerso.isRunning()) {
                int dX, dY;
                switch (direction) {
                    case haut: dX = 0; dY = -1; break;
                    case bas: dX = 0; dY = 1; break;
                    case gauche: dX = -1; dY = 0; break;
                    case droite: dX = 1; dY = 0; break;
                    default: dX = 0; dY = 0; break;
                }

                if (jeu.getGrilleActuelle().estUnObstacle(xProperty.getValue() + dX, yProperty.getValue() + dY))
                    throw new ObstacleException();
                else {
                    xProperty.setValue(xProperty.getValue() + dX); yProperty.setValue(yProperty.getValue() + dY);
                }

                if (!jeu.getGrilleActuelle().dansGrille(xProperty.getValue(), yProperty.getValue())) {
                    jeu.changementDeMap();
                    System.out.println(xProperty + "\t" + yProperty);
                    if (xProperty.getValue() >= Grille.WIDTH)
                        xProperty.setValue(0);
                    else if (xProperty.getValue() < 0)
                        xProperty.setValue(Grille.WIDTH - 1);
                    else if (yProperty.getValue() >= Grille.HEIGHT)
                        yProperty.setValue(0);
                    else if (yProperty.getValue() < 0)
                        yProperty.setValue(Grille.HEIGHT - 1);
                    changeMap = true;
                }
            }
        } catch (Exception e) {
            seDeplace = false;
        }
    }

    public boolean memeDirection() {
        return direction == dirPrecedente;
    }

    private void animationPerso() {
        if (!animationSpritePerso.isRunning()) {
            if (seDeplace && memeDirection()) {
                animationSpritePerso.start();
            } else if (changeDirection) {
                animationSpritePerso.immobile();
                changeDirection = false;
            } else if (changeMap) {
                animationSpritePerso.changementMap();
                changeMap = false;
            }
        }
    }

    public void interactionBois() {
        if (interagitBois) {
            if (!seDeplace) {
                int bx, by;
                switch (direction) {
                    case haut: bx = xProperty.getValue(); by = yProperty.getValue() - 1; break;
                    case bas: bx = xProperty.getValue(); by = yProperty.getValue() + 1; break;
                    case gauche: bx = xProperty.getValue() - 1; by = yProperty.getValue(); break;
                    case droite: bx = xProperty.getValue() + 1; by = yProperty.getValue(); break;
                    default: bx = 0; by = 0; break;
                }
                if (!inventaire.plein() && jeu.getGrilleActuelle().retirerBois(bx, by)) inventaire.ajouterBois();
                else if (inventaire.possedeBois() && jeu.getGrilleActuelle().placerBois(bx, by))
                    inventaire.retirerBois();
            }
            interagitBois = false;
        }
    }

    //region Getter & Setter
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

    public final IntegerProperty getXProperty() {
        return xProperty;
    }

    public final IntegerProperty getYProperty() {
        return yProperty;
    }

    public Dir getDirection() {
        return direction;
    }

    public void setDirection(Dir direction) {
        dirPrecedente = this.direction;
        this.direction = direction;
    }

    public Dir getDirPrecedente() {
        return dirPrecedente;
    }

    public Inventaire getInventaire() {
        return inventaire;
    }

    public final int getPv() {
        return pvProperty.getValue();
    }

    public final IntegerProperty getPvProperty() {
        return pvProperty;
    }

    public void incrementerPv() throws PvMaxException {
        if (pvMaxAtteint())
            throw new PvMaxException();
        else
            pvProperty.setValue(pvProperty.getValue() + 1);
    }

    public void decrementerPv() {
        pvProperty.setValue(pvProperty.getValue() - 1);
    }

    public boolean pvMaxAtteint() {
        return pvProperty.getValue() == 5;
    }

    public boolean pvMinAtteint() {
        return pvProperty.getValue() == 0;
    }

    public void setSeDeplace(boolean seDeplace) {
        this.seDeplace = seDeplace;
    }

    public void setChangeDirection(boolean changeDirection) {
        this.changeDirection = changeDirection;
    }

    public void setInteragitBois(boolean interagitBois) {
        this.interagitBois = interagitBois;
    }

    public void setAnimationSpritePerso(AnimationSpritePerso animationSpritePerso) {
        this.animationSpritePerso = animationSpritePerso;
    }

    //endregion
}