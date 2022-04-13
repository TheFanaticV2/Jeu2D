package application.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Personnage {

    private Grille grille;
    private IntegerProperty xProperty;
    private IntegerProperty yProperty;
    private Dir direction;

    public Personnage(Grille grille, int x, int y) {
        this.grille = grille;
        xProperty = new SimpleIntegerProperty(x);
        yProperty = new SimpleIntegerProperty(y);
        direction = Dir.bas;
    }

    public void seDeplacerHaut() {
        if(grille.dansGrille(xProperty.getValue(), yProperty.getValue() - 1) && !grille.estUnObstacle(xProperty.getValue(), yProperty.getValue() - 1) )
            yProperty.setValue(yProperty.getValue() - 1);
    }

    public void seDeplacerBas() {
        if(grille.dansGrille(xProperty.getValue(), yProperty.getValue() + 1) && !grille.estUnObstacle(xProperty.getValue(), yProperty.getValue() + 1))
            yProperty.setValue(yProperty.getValue() + 1);
    }

    public void seDeplacerGauche() {
        if(grille.dansGrille(xProperty.getValue() - 1, yProperty.getValue()) && !grille.estUnObstacle(xProperty.getValue() - 1, yProperty.getValue()))
            xProperty.setValue(xProperty.getValue() - 1);
    }

    public void seDeplacerDroite() {
        if(grille.dansGrille(xProperty.getValue() + 1, yProperty.getValue()) && !grille.estUnObstacle(xProperty.getValue() + 1, yProperty.getValue()))
            xProperty.setValue(xProperty.getValue() + 1);
    }

    public final int getX() {
        return xProperty.getValue();
    }

    public final void setX(int x) {xProperty.setValue(x);}

    public final IntegerProperty getXProperty() {
        return xProperty;
    }

    public final int getY() {
        return yProperty.getValue();
    }

    public final void setY(int y) {xProperty.setValue(y);}

    public final IntegerProperty getYProperty() {
        return yProperty;
    }

    public Sommet interactionBois() {
        Bois bois;
        switch (direction) {
            case haut : bois = new Bois(getX(), getY() - 1); break;
            case bas : bois = new Bois(getX(), getY() + 1); break;
            case gauche : bois = new Bois(getX() - 1, getY()); break;
            case droite : bois = new Bois(getX() + 1, getY()); break;
            default : bois = null; break;
        }
        if (!grille.getListeBois().contains(bois)) {
            grille.placerBois(bois);
            return bois;
        }
        else {
            grille.retirerBois(bois);
            return new Sommet(bois.getX(), bois.getY());
        }
    }

    public Dir getDirection() {
        return direction;
    }

    public void setDirection(Dir direction) {
        this.direction = direction;
    }
}
