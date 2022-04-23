package application.controleur;

import application.modele.Bois;
import application.modele.Grille;
import javafx.collections.ListChangeListener;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import static application.controleur.Controleur.TUILE_TAILLE;

public class ListenerBois implements ListChangeListener {

    private Pane tuilesObjet;
    private Grille grille;

    public ListenerBois(Pane tuilesObjet, Grille grille) {
        this.tuilesObjet = tuilesObjet;
        this.grille = grille;
    }

    @Override
    public void onChanged(Change c) {
        int i = 0;
        while (c.next()) {
            if (c.wasAdded()) {
                ajouterBois(grille.getListeBois().get(c.getFrom() + i));
                i++;
            }
            else if (c.wasRemoved())
                retirerBois(grille.getListeBois().get(c.getFrom()));
        }
        System.out.println(i);
    }

    private void ajouterBois(Bois bois) {
        ImageView img = new ImageView(new Image("file:src/main/resources/application/sprite/decor/cutted_tree.png"));
        img.setFitWidth(TUILE_TAILLE);
        img.setFitHeight(TUILE_TAILLE);
        img.setX(bois.getX() * TUILE_TAILLE);
        img.setY(bois.getY() * TUILE_TAILLE);
        tuilesObjet.getChildren().add(img);
    }

    private void retirerBois(Bois bois) {
        int i = 0;
        while (((ImageView) tuilesObjet.getChildren().get(i)).getX() != bois.getX() * TUILE_TAILLE
                || ((ImageView) tuilesObjet.getChildren().get(i)).getY() != bois.getY() * TUILE_TAILLE) i++;
        tuilesObjet.getChildren().remove(i);
    }
}
