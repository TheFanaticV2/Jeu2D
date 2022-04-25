package application.modele;

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

    public Grille(int width, int height) {
        this.width = width;
        this.height = height;
        listeAdj = new HashMap<>();
        listeBois = FXCollections.observableArrayList();
        listeArbre = new ArrayList<>();
        perso = new Personnage(this,width/2, height/2);
        construire();
    }

    private void construire() {
        InputStream is = getClass().getResourceAsStream("/application/map/map1.txt");
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

    public boolean estUnBois(int x, int y) {
        int i = 0;
        while (i < listeBois.size() && (listeBois.get(i).getX() != x || listeBois.get(i).getY() != y)) i++;
        return i != listeBois.size();
    }

    public void ajouterBois() {

    }

    public void placerBois(Bois bois) {
        listeBois.add(bois);
    }

    public void retirerBois(Bois bois) {
        listeBois.remove(bois);
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
}
