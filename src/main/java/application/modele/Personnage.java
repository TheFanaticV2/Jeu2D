package application.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Personnage {

    private Grille grille;
    private IntegerProperty xProperty;
    private IntegerProperty yProperty;

    public Personnage(Grille grille, int x, int y) {
        this.grille = grille;
        xProperty = new SimpleIntegerProperty(x);
        yProperty = new SimpleIntegerProperty(y);
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
        if (!grille.getListeBois().contains(new Bois(getX(), getY() - 1))) {
            grille.placerBois(getX(), getY() - 1);
            return new Bois(getX(), getY() - 1);
        }
        else {
            grille.retirerBois(getX(), getY() - 1);
            return new Sommet(getX(), getY() - 1);
        }
    }

}
