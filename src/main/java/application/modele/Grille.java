package application.modele;

import java.util.*;

public class Grille {
    private int width;
    private int height;
    private Map<Sommet, Set<Sommet>> listeAdj;
    private ArrayList<Bois> listeBois;

    private Personnage perso;

    public Grille(int width, int height) {
        this.width = width;
        this.height = height;
        listeAdj = new HashMap<>();
        listeBois = new ArrayList<>();
        ajouterObstacles();
        construire();
        perso = new Personnage(this,width/2, height/2);
    }

    private void construire() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                listeAdj.put(new Sommet(i, j), new HashSet<>());
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

    public ArrayList<Bois> getListeBois() {
        return listeBois;
    }

    public boolean estUnObstacle(int x, int y) {
        int i = 0;
        while (i < listeBois.size() && (listeBois.get(i).getX() != x || listeBois.get(i).getY() != y)) i++;
        return i != listeBois.size();
    }

    public void ajouterObstacles() {
        listeBois.add(new Bois(width/2 - 2,height/2 - 2));
        listeBois.add(new Bois(width/2 - 1,height/2 - 2));
        listeBois.add(new Bois(width/2,height/2 - 2));
        listeBois.add(new Bois(width/2 + 1,height/2 - 2));
        listeBois.add(new Bois(width/2 + 2,height/2 - 2));
        listeBois.add(new Bois(width/2 - 2,height/2 - 1));
        listeBois.add(new Bois(width/2 - 2,height/2));
        listeBois.add(new Bois(width/2 - 2,height/2 + 1));
        listeBois.add(new Bois(width/2 - 2,height/2 + 2));
        listeBois.add(new Bois(width/2 - 1,height/2 + 2));
        listeBois.add(new Bois(width/2 + 1,height/2 + 2));
        listeBois.add(new Bois(width/2 + 2,height/2 + 2));
        listeBois.add(new Bois(width/2 + 2,height/2 + 1));
        listeBois.add(new Bois(width/2 + 2,height/2));
        listeBois.add(new Bois(width/2 + 2,height/2 - 1));
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
}
