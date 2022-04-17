package application.controleur;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class KeyReleased implements EventHandler<KeyEvent> {

    private AnimationSpritePerso animationSpritePerso;

    public KeyReleased(AnimationSpritePerso animationSpritePerso) {
        this.animationSpritePerso = animationSpritePerso;
    }

    @Override
    public void handle(KeyEvent event) {
        animationSpritePerso.immobile();
    }
}
