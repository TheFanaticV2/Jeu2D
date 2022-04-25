package application.modele;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Jeu {
    private ArrayList<Grille> grilles;
    private Grille grilleActuelle;
    private Personnage perso;
    private BooleanProperty changementDeMapProperty;

    public Jeu() {
        this.grilles = new ArrayList<>();
        perso = new Personnage(this);
        grilles.add(new Grille(perso, "/application/map/map01.txt"));
        grilleActuelle = grilles.get(0);
        changementDeMapProperty = new SimpleBooleanProperty(false);
    }

    public ArrayList<Grille> getGrilles() {
        return grilles;
    }

    public Grille getGrilleActuelle() {
        return grilleActuelle;
    }

    public Personnage getPerso() {
        return perso;
    }

    public boolean getChangementDeMap() {
        return changementDeMapProperty.get();
    }

    public BooleanProperty getChangementDeMapProperty() {
        return changementDeMapProperty;
    }

    public void setChangementDeMap(boolean changementDeMap) {
        this.changementDeMapProperty.set(changementDeMap);
    }

    public void changementDeMap() {
        InputStream is = getClass().getResourceAsStream("/application/map/mapInfo.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuffer sb = new StringBuffer();
        sb.append(grilleActuelle.getUrlMap().charAt(20));
        sb.append(grilleActuelle.getUrlMap().charAt(21));
        int idMap = Integer.parseInt(sb.toString());
        for (int i = 1; i < idMap; i++) {
            try {
                br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            String txt = br.readLine();
            int cpt = 1;
            int nbSorties = Integer.parseInt(String.valueOf(txt.charAt(0)));
            boolean sortieTrouve = false;
            for (int j = 0; j < nbSorties && !sortieTrouve; j++) {
                sb.setLength(0);
                sb.append(txt.charAt(cpt += 6));
                sb.append(txt.charAt(++cpt));
                int x = Integer.parseInt(sb.toString());
                System.out.println(x);
                sb.setLength(0);
                sb.append(txt.charAt(cpt += 6));
                sb.append(txt.charAt(++cpt));
                int y = Integer.parseInt(sb.toString());
                System.out.println(y);

                if (perso.getX() == x && perso.getY() == y) {
                    sb.setLength(0);
                    for (int i = 1; i <= 26; i++)
                        sb.append(txt.charAt(16 + i));
                    int i = 0;
                    while (i < grilles.size() && grilles.get(i).getUrlMap() != sb.toString()) i++;
                    if (i == grilles.size())
                        grilles.add(new Grille(perso, sb.toString()));
                    grilleActuelle = grilles.get(grilles.size() -1);
                    setChangementDeMap(true);
                    sortieTrouve = true;
                }
                cpt+=29;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
