package application.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Inventaire {
    private final static int STOCKAGE_MAX = 20;
    private IntegerProperty stockageTotalProperty;
    private IntegerProperty nbBoisProperty;

    public Inventaire() {
        stockageTotalProperty = new SimpleIntegerProperty(0);
        nbBoisProperty = new SimpleIntegerProperty(0);
    }

    public void ajouterBois() {
        if (stockageTotalProperty.getValue() < STOCKAGE_MAX) {
            nbBoisProperty.setValue(nbBoisProperty.getValue() + 1);
            stockageTotalProperty.setValue(stockageTotalProperty.getValue() + 1);
        }
    }

    public void retirerBois() {
        if (nbBoisProperty.getValue() > 0) {
            nbBoisProperty.setValue(nbBoisProperty.getValue() - 1);
            stockageTotalProperty.setValue(stockageTotalProperty.getValue() - 1);
        }
    }

    public boolean plein() {
        return stockageTotalProperty.getValue() == STOCKAGE_MAX;
    }

    public boolean possedeBois() {
        return nbBoisProperty.getValue() > 0;
    }

    public final int getStockageTotal() {
        return stockageTotalProperty.getValue();
    }

    public final void setStockageTotal(int stockageTotal) {
        stockageTotalProperty.setValue(stockageTotal);
    }

    public final IntegerProperty getStockageTotalProperty() {
        return stockageTotalProperty;
    }

    public final int getNbBois() {
        return nbBoisProperty.getValue();
    }

    public final void setNbBois(int bois) {
        nbBoisProperty.setValue(bois);
    }

    public final IntegerProperty getNbBoisProperty() {
        return nbBoisProperty;
    }
}


