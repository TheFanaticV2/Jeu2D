package application.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Personnage {

    private Grille grille;
    private int x;
    private int y;
    private Dir direction;
    private Dir dirPrecedente;
    private boolean seDeplace;
    private Inventaire inventaire;

    public Personnage(Grille grille, int x, int y) {
        this.grille = grille;
        this.x = x;
        this.y = y;
        direction = Dir.bas;
        dirPrecedente = Dir.bas;
        seDeplace = false;
        inventaire = new Inventaire();
    }

    public void seDeplacer() {
        int dX, dY;
        if (memeDirection() && seDeplace) {
                try {
                    switch (direction) {
                        case haut: dX = 0; dY = -1; break;
                        case bas: dX = 0; dY = 1; break;
                        case gauche: dX = -1; dY = 0; break;
                        case droite: dX = 1; dY = 0; break;
                        default: dX = 0; dY = 0; break;
                    }

                    if (grille.estUnObstacle(x + dX, y + dY))
                        throw new ObstacleException();
                    else if (!grille.dansGrille(x + dX, y + dY))
                        throw new LimiteException();
                    else {
                        x+=dX;
                        y+=dY;
                    }
                } catch (Exception e) {
                    seDeplace = false;
                }
        }

    }

//    public void seDeplacerHaut() throws ObstacleException {
//        if (grille.estUnObstacle(x, y - 1)) throw new ObstacleException();
//        else if(grille.dansGrille(x, y - 1)) y--;
//    }
//
//    public void seDeplacerBas() throws ObstacleException {
//        if (grille.estUnObstacle(x, y + 1)) throw new ObstacleException();
//        else if(grille.dansGrille(x, y + 1)) y++;
//    }
//
//    public void seDeplacerGauche() throws ObstacleException {
//        if (grille.estUnObstacle(x - 1, y)) throw new ObstacleException();
//        else if(grille.dansGrille(x - 1, y)) x--;
//    }
//
//    public void seDeplacerDroite() throws ObstacleException {
//        if (grille.estUnObstacle(x + 1, y)) throw new ObstacleException();
//        else if(grille.dansGrille(x + 1, y)) x++;
//    }

    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public Sommet interactionBois() {
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
            return new Sommet(bois.getX(), bois.getY());
        }
        else if (!grille.getListeBois().contains(bois) && inventaire.possedeBois()){
            inventaire.retirerBois();
            grille.placerBois(bois);
            return bois;
        }
        return null;
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

    public Inventaire getInventaire() {
        return inventaire;
    }

    public boolean isSeDeplace() {
        return seDeplace;
    }

    public void setSeDeplace(boolean seDeplace) {
        this.seDeplace = seDeplace;
    }
}
