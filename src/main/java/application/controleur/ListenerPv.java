package application.controleur;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ListenerPv implements ChangeListener {

    private HBox hBoxPv;

    public ListenerPv(HBox hBoxPv) {
        this.hBoxPv = hBoxPv;
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        System.out.println(oldValue.getClass());
        if (((Integer) oldValue).compareTo((Integer) newValue) < 0)
            ajouterCoeur(newValue);
        else
            retirerCoeur(oldValue);
    }

    private void ajouterCoeur(Object newValue) {
        ImageView coeur = new ImageView(new Image("file:src/main/resources/application/sprite/interface/coeur.png"));
        coeur.setId(String.valueOf(newValue));
        coeur.setFitWidth(30);
        coeur.setFitHeight(30);
        hBoxPv.getChildren().add(coeur);
    }

    private void retirerCoeur(Object oldValue) {
        hBoxPv.getChildren().remove(hBoxPv.lookup("#" + oldValue));
    }
}
