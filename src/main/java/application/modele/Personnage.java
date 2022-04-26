package application.modele;

import application.controleur.AnimationSpritePerso;
import application.modele.Exception.ObstacleException;
import application.modele.Exception.PvMaxException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.StackPane;

public class Personnage {

    private IntegerProperty pvProperty;
    private Jeu jeu;
    private int x;
    private int y;
    private Dir direction, dirPrecedente;
    private boolean seDeplace, changeDirection, interagitBois;
    private Inventaire inventaire;
    private AnimationSpritePerso animationSpritePerso;

    public Personnage(Jeu jeu, StackPane spritesPerso) {
        seDeplace = false; changeDirection = false; interagitBois = false;
        this.jeu = jeu;
        direction = Dir.bas;
        dirPrecedente = direction;
        inventaire = new Inventaire();
        pvProperty = new SimpleIntegerProperty(5);
        x = Grille.WIDTH / 2;
        y = Grille.HEIGHT / 2 - 1;
        animationSpritePerso = new AnimationSpritePerso(jeu, spritesPerso);
    }

    public void udpate() {
        seDeplacer();
        interactionBois();
    }

    public void render() {
        animationPerso();
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

                if (jeu.getGrilleActuelle().estUnObstacle(x + dX, y + dY))
                    throw new ObstacleException();
                else {
                    x += dX; y += dY;
                    //if (!memeDirection()) aChangeDeDirection = true;
                }

                if (!jeu.getGrilleActuelle().dansGrille(x, y)) {
                    jeu.changementDeMap();
                    System.out.println(x + "\t" + y);
                    if (x >= Grille.WIDTH)
                        x = 0;
                    else if (x < 0)
                        x = Grille.WIDTH - 1;
                    else if (y >= Grille.HEIGHT)
                        y = 0;
                    else if (y < 0)
                        y = Grille.HEIGHT - 1;
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
            if (seDeplace) {
                animationSpritePerso.start();
                seDeplace = false;
            } else if (changeDirection) {
                animationSpritePerso.immobile();
                changeDirection = false;
            }
        }
    }

    public void interactionBois() {
        if (interagitBois) {
            if (seDeplace)
                interagitBois = false;
            else {
                int bx, by;
                switch (direction) {
                    case haut:
                        bx = x;
                        by = y - 1;
                        break;
                    case bas:
                        bx = x;
                        by = y + 1;
                        break;
                    case gauche:
                        bx = x - 1;
                        by = y;
                        break;
                    case droite:
                        bx = x + 1;
                        by = y;
                        break;
                    default:
                        bx = 0;
                        by = 0;
                        break;
                }
                if (!inventaire.plein() && jeu.getGrilleActuelle().retirerBois(bx, by)) inventaire.ajouterBois();
                else if (inventaire.possedeBois() && jeu.getGrilleActuelle().placerBois(bx, by))
                    inventaire.retirerBois();
            }
        }
    }

    //region Getter & Setter
    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Dir getDirection() {
        return direction;
    }

    public void setDirection(Dir direction) {
        this.direction = direction;
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

    //endregion
}
/*
package application.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Personnage {

    private Grille grille;
    private int x;
    private int y;
    private Dir direction;
    private Dir dirPrecedente;
    private boolean seDeplace, sEstDeplace, aChangeDeDirection, interagitBois;
    private Inventaire inventaire;
    private Sommet boisInteraction;

    public Personnage(Grille grille, int x, int y) {
        this.grille = grille;
        this.x = x;
        this.y = y;
        direction = Dir.bas;
        dirPrecedente = Dir.bas;
        seDeplace = false;
        sEstDeplace = false;
        aChangeDeDirection = false;
        interagitBois = false;
        inventaire = new Inventaire();
        boisInteraction = null;
    }

    public void seDeplacer() {
        int dX, dY;
        if (grille.getPerso().memeDirection() && seDeplace) {
            try {
                switch (direction) {
                    case haut: dX = 0; dY = -1; break;
                    case bas: dX = 0; dY = 1; break;
                    case gauche: dX = -1; dY = 0; break;
                    case droite: dX = 1; dY = 0; break;
                    default: dX = 0; dY = 0; break;
                }

                if (grille.estUnObstacle(x + dX, y + dY)) throw new ObstacleException();
                else if (!grille.dansGrille(x + dX, y + dY)) throw new LimiteException();
                else {
                    x += dX; y += dY;
                    sEstDeplace = true;
                    if (!memeDirection()) aChangeDeDirection = true;
                }
            } catch (Exception e) {
                seDeplace = false;
            }
        }
    }

    public void interactionBois() {
        Bois bois;
        switch (direction) {
            case haut: bois = new Bois(x, y - 1); break;
            case bas: bois = new Bois(x, y + 1); break;
            case gauche: bois = new Bois(x - 1, y); break;
            case droite: bois = new Bois(x + 1, y); break;
            default: bois = null; break;
        }
        if (grille.getListeBois().contains(bois) && !inventaire.plein()) {
            inventaire.ajouterBois();
            grille.retirerBois(bois);
            boisInteraction =  new Sommet(bois.getX(), bois.getY());
            interagitBois = true;
            System.out.println("bois posé");
        } else if (!grille.getListeBois().contains(bois) && inventaire.possedeBois()) {
            inventaire.retirerBois();
            grille.placerBois(bois);
            boisInteraction = bois;
            interagitBois = true;
            System.out.println("bois retiré");
        }
    }

    //region Getter & Setter
    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
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

    public boolean memeDirection() {
        return direction == dirPrecedente;
    }

    public void setSeDeplace(boolean seDeplace) {
        this.seDeplace = seDeplace;
    }

    public boolean issEstDeplace() {
        return sEstDeplace;
    }

    public void setsEstDeplace(boolean sEstDeplace) {
        this.sEstDeplace = sEstDeplace;
    }

    public boolean isaChangeDeDirection() {
        return aChangeDeDirection;
    }

    public void setaChangeDeDirection(boolean aChangeDeDirection) {
        this.aChangeDeDirection = aChangeDeDirection;
    }

    public Inventaire getInventaire() {
        return inventaire;
    }

    public Sommet getBoisInteraction() {
        return boisInteraction;
    }

    public void setBoisInteraction(Sommet boisInteraction) {
        this.boisInteraction = boisInteraction;
    }

    public boolean isInteragitBois() {
        return interagitBois;
    }

    public void setInteragitBois(boolean interagitBois) {
        this.interagitBois = interagitBois;
    }

    //endregion
}
 */
