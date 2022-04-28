package application.controleur;

import application.modele.Bois;
import application.modele.Grille;
import javafx.collections.ListChangeListener;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import static application.controleur.Camera.TUILE_TAILLE;

public class ListenerBois implements ListChangeListener<Bois> {

    private Pane tuilesObjet;

    public ListenerBois(Pane tuilesObjet) {
        this.tuilesObjet = tuilesObjet;
    }

    @Override
    public void onChanged(Change<? extends Bois> c) {
        while (c.next()) {
            for (Bois ajoute : c.getAddedSubList())
                ajouterBois(ajoute);

            for (Bois retire : c.getRemoved())
                retirerBois(retire);
        }
    }

    public void ajouterBois(Bois bois) {
        ImageView img = new ImageView(new Image("file:src/main/resources/application/sprite/decor/cutted_tree.png"));
        img.setId(bois.getId());
        img.setFitWidth(TUILE_TAILLE);
        img.setFitHeight(TUILE_TAILLE);
        img.setX(bois.getX() * TUILE_TAILLE);
        img.setY(bois.getY() * TUILE_TAILLE);
        tuilesObjet.getChildren().add(img);
    }

    private void retirerBois(Bois bois) {
        tuilesObjet.getChildren().remove(tuilesObjet.lookup("#" + bois.getId()));
    }
}
