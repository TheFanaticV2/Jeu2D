package application.controleur.listener;

import application.controleur.Camera;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class ListenerCamera implements ChangeListener<Number> {

    private Camera camera;
    private int max;

    public ListenerCamera(Camera camera, int max) {
        this.camera = camera;
        this.max = max;
    }

    @Override
    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
        if (newValue.intValue() > 0 && newValue.intValue() < max) {
            camera.contruireMap();
            camera.construireObjet();
        }
    }
}
