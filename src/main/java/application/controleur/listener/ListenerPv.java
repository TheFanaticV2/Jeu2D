package application.controleur.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ListenerPv implements ChangeListener<Number> {

    private HBox hBoxPv;
    private Label gameOver;

    public ListenerPv(HBox hBoxPv, Label gameOver) {
        this.hBoxPv = hBoxPv;
        this.gameOver = gameOver;
    }

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        if ((Integer) newValue == 0)
            gameOver.toFront();
        else if (((Integer) oldValue).compareTo((Integer) newValue) < 0)
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
