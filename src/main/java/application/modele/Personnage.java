package application.modele;

import application.modele.Exception.ObstacleException;
import application.modele.Exception.PvMaxException;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Personnage {

    private IntegerProperty pvProperty;
    private Grille grille;
    private int x;
    private int y;
    private Dir direction;
    private Inventaire inventaire;

    public Personnage(Grille grille, int x, int y) {
        this.grille = grille;
        this.x = x;
        this.y = y;
        direction = Dir.bas;
        inventaire = new Inventaire();
        pvProperty = new SimpleIntegerProperty(5);
    }

    public void seDeplacer(int dX, int dY) throws ObstacleException {
        if (grille.estUnObstacle(x + dX, y + dY))
            throw new ObstacleException();
        else {
            x+=dX;
            y+=dY;
        }
    }

    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public Objet interactionBois() {
        Bois bois;
        switch (direction) {
            case haut : bois = new Bois(x, y - 1); break;
            case bas : bois = new Bois(x, y + 1); break;
            case gauche : bois = new Bois(x - 1, y); break;
            case droite : bois = new Bois(x + 1, y); break;
            default : bois = null; break;
        }
        if (grille.getListeBois().contains(bois) && !inventaire.plein()) {
            inventaire.ajouterBois();
            grille.retirerBois(bois);
        }
        else if (!grille.getListeBois().contains(bois) && inventaire.possedeBois()){
            inventaire.retirerBois();
            grille.placerBois(bois);
        }
        return null;
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
