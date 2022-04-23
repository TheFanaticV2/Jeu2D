package application.controleur;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.HBox;

public class ListenerPv implements ChangeListener {

    private HBox hBoxPv;

    public ListenerPv(HBox hBoxPv) {
        this.hBoxPv = hBoxPv;
    }

    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        System.out.println(oldValue);
        if (true)
            retirerCoeur();
        else
            ajouterCoeur();
    }

    private void ajouterCoeur() {
    }

    private void retirerCoeur() {

    }
}
