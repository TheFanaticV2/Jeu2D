package application.controleur;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyReleased implements EventHandler<KeyEvent> {

    private GameLoop gameLoop;

    public KeyReleased(GameLoop gameLoop) {
        this.gameLoop = gameLoop;
    }

    @Override
    public void handle(KeyEvent event) {
        gameLoop.setSeDeplace(false);
    }
}
