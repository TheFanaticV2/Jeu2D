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
    public static int WIDTH = 27;
    public static int HEIGHT = 15;
    private Map<Sommet, Set<Sommet>> listeAdj;
    private ObservableList<Bois> listeBois;
    private ArrayList<Arbre> listeArbre;
    private String urlMap;

    public Grille(String urlMap) {
        this.urlMap = urlMap;
        listeAdj = new HashMap<>();
        listeBois = FXCollections.observableArrayList();
        listeArbre = new ArrayList<>();
        construire();
    }

    private void construire() {
        InputStream is = getClass().getResourceAsStream(urlMap);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        char c;
        for (int j = 0; j < HEIGHT; j++) {
            for (int i = 0; i < WIDTH; i++) {
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

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
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
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }

    public Map<Sommet, Set<Sommet>> getListeAdj() {
        return listeAdj;
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

    public boolean placerBois(int x, int y) {
        if (!estUnObstacle(x,y)) {
            listeBois.add(new Bois(x,y));
            return true;
        }
        return false;
    }

    public boolean retirerBois(int x, int y) {
        int i = trouverBois(x, y);
        if (i != -1) {
            listeBois.remove(i);
            return true;
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

    public String getUrlMap() {
        return urlMap;
    }
}
