package application.modele;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.StackPane;

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

    public Jeu(StackPane spritesPerso) {
        this.grilles = new ArrayList<>();
        perso = new Personnage(this, spritesPerso);
        grilles.add(new Grille("/application/map/map01.txt"));
        initGrilles();
        grilleActuelle = grilles.get(0);
        changementDeMapProperty = new SimpleBooleanProperty(false);
    }

    private void initGrilles() {
        for (int i = 1; i < 3; i++) {
            grilles.add(new Grille("/application/map/map0" + i + ".txt"));
        }
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
                    while (i < grilles.size() && !grilles.get(i).getUrlMap().equals(sb.toString())) i++;
                    grilleActuelle = grilles.get(i);
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
