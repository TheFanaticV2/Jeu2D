package application.modele;

import application.modele.Exception.ObstacleException;
import application.modele.Exception.PvMaxException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Personnage {

    private IntegerProperty pvProperty;
    private Jeu jeu;
    private int x;
    private int y;
    private Dir direction;
    private Inventaire inventaire;

    public Personnage(Jeu jeu) {
        this.jeu = jeu;
        direction = Dir.bas;
        inventaire = new Inventaire();
        pvProperty = new SimpleIntegerProperty(5);
        x = Grille.WIDTH/2;
        y = Grille.HEIGHT/2 - 1;
    }

    public void seDeplacer() throws ObstacleException {
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
            x+=dX;
            y+=dY;
        }
        if (!jeu.getGrilleActuelle().dansGrille(x,y)) {
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

    public void interactionBois() {
        int bx, by;
        switch (direction) {
            case haut : bx = x; by = y-1; break;
            case bas : bx = x; by = y+1; break;
            case gauche : bx = x-1; by = y; break;
            case droite : bx = x+1; by = y; break;
            default: bx = 0; by = 0; break;
        }
        if (!inventaire.plein() && jeu.getGrilleActuelle().retirerBois(bx,by)) inventaire.ajouterBois();
        else if (inventaire.possedeBois() && jeu.getGrilleActuelle().placerBois(bx,by)) inventaire.retirerBois();


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
            pvProperty.setValue(pvProperty.getValue()+1);
    }

    public void decrementerPv() {
        pvProperty.setValue(pvProperty.getValue()-1);
    }

    public boolean pvMaxAtteint() {
        return pvProperty.getValue() == 5;
    }

    public boolean pvMinAtteint() {
        return pvProperty.getValue() == 0;
    }

}
