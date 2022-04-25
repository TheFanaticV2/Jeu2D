package application.modele;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Grille {
    private int width;
    private int height;
    private Map<Sommet, Set<Sommet>> listeAdj;
    private ObservableList<Bois> listeBois;
    private ArrayList<Arbre> listeArbre;
    private Personnage perso;
    private BooleanProperty changementDeMapProperty;
    public Grille(int width, int height) {
        changementDeMapProperty = new SimpleBooleanProperty(false);
        this.width = width;
        this.height = height;
        perso = new Personnage(this,width/2, height/2 - 1);
        construire("/application/map/map1.txt");
    }

    private void construire(String urlMap) {
        listeAdj = new HashMap<>();
        listeBois = FXCollections.observableArrayList();
        listeArbre = new ArrayList<>();
        InputStream is = getClass().getResourceAsStream(urlMap);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        char c;
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                try {
                    c = (char) br.read();
                    switch (c) {
                        case '0' : listeAdj.put(new Sommet(i, j, 0), new HashSet<>()); break;
                        case '1' : listeAdj.put(new Sommet(i, j, 1), new HashSet<>()); break;
                        case '2' :
                            listeArbre.add(new Arbre(i,j));
                            listeAdj.put(new Sommet(i, j,0), new HashSet<>()); break;
                        case '3' :
                            listeBois.add(new Bois(i,j));
                            listeAdj.put(new Sommet(i, j,0), new HashSet<>()); break;
                        case '4' :
                            listeAdj.put(new Sommet(i, j, 4), new HashSet<>()); break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                br.read(); br.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Sommet s = getSommet(i, j);
                if (dansGrille(i - 1, j))
                    listeAdj.get(s).add(getSommet(i - 1, j));
                if (dansGrille(i + 1, j))
                    listeAdj.get(s).add(getSommet(i + 1, j));
                if (dansGrille(i, j + 1))
                    listeAdj.get(s).add(getSommet(i, j + 1));
                if (dansGrille(i, j - 1))
                    listeAdj.get(s).add(getSommet(i, j - 1));
                // les suivants pour autoriser les diagonales
				/*if (dansGrille(i + 1, j + 1))
					listeAdj.get(s).add(getSommet(i + 1, j + 1));
				if (dansGrille(i - 1, j + 1))
					listeAdj.get(s).add(getSommet(i - 1, j + 1));
				if (dansGrille(i + 1, j - 1))
					listeAdj.get(s).add(getSommet(i + 1, j - 1));
				if (dansGrille(i - 1, j - 1))
					listeAdj.get(s).add(getSommet(i - 1, j - 1));*/
            }
        }
    }

    public Sommet getSommet(int x, int y) {
        for (Sommet s : listeAdj.keySet())
            if (s.getX() == x && s.getY() == y)
                return s;
        return null;
    }

    public boolean estUnObstacle(int x, int y) {
        if (trouverBois(x,y) == -1)
            if (!estUnArbre(x,y))
                return estEau(x,y);
        return true;
    }

    public boolean dansGrille(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Map<Sommet, Set<Sommet>> getListeAdj() {
        return listeAdj;
    }

    public Personnage getPerso() {
        return perso;
    }

    public ObservableList<Bois> getListeBois() {
        return listeBois;
    }

    public int trouverBois(int x, int y) {
        int i = 0;
        while (i < listeBois.size() && (listeBois.get(i).getX() != x || listeBois.get(i).getY() != y)) i++;
        if (i < listeBois.size())
            return i;
        else
            return -1;
    }

    public void placerBois(int x, int y) {
        if (perso.getInventaire().possedeBois() && !estUnObstacle(x,y)) {
            listeBois.add(new Bois(x,y));
            perso.getInventaire().retirerBois();
        }
    }

    public boolean retirerBois(int x, int y) {
        if (!perso.getInventaire().plein()) {
            int i = trouverBois(x,y);
            if (i != -1) {
                listeBois.remove(i);
                perso.getInventaire().ajouterBois();
                return true;
            }
        }
        return false;

    }

    public ArrayList<Arbre> getListeArbre() {
        return listeArbre;
    }

    private boolean estUnArbre(int x, int y) {
        int i = 0;
        while (i < listeArbre.size() && (listeArbre.get(i).getX() != x || listeArbre.get(i).getY() != y)) i++;
        return i != listeArbre.size();
    }

    private boolean estEau(int x, int y) {
        for (Sommet s : listeAdj.keySet())
            if (s.getX() == x && s.getY() == y)
                if (s.getGroundType() == 4)
                    return true;
                else
                    return false;
        return false;
    }

    public Set<Sommet> adjacents(Sommet s) {
        for (Sommet sommet : listeAdj.keySet()) {
            if (sommet.getX() == s.getX() && sommet.getY() == s.getY())
                return listeAdj.get(sommet);
        }
        return null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void changementDeMap(String urlMap) {
        construire(urlMap);
        if (perso.getX() >= width)
            perso.setX(0);
        else if (perso.getX() < 0)
            perso.setX(width-1);
        else if (perso.getY() >= height)
            perso.setY(0);
        else if (perso.getY() < 0)
            perso.setY(height-1);
        changementDeMapProperty.setValue(true);
    }

    public final boolean getChangementDeMapProperty() {
        return changementDeMapProperty.get();
    }

    public final BooleanProperty changementDeMapPropertyProperty() {
        return changementDeMapProperty;
    }

    public final void setChangementDeMapProperty(boolean changementDeMapProperty) {
        this.changementDeMapProperty.set(changementDeMapProperty);
    }
}
