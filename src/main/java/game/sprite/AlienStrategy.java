package game.sprite;

import game.Preferences;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class AlienStrategy {
    public void build(int level, List<Sprite> units, BorderPane root){
        List<Sprite> aliens = new ArrayList<>();
        if (Preferences.CURRENT_ROUND + 1 == 1) {
            firstRound(aliens);
        }
        if (Preferences.CURRENT_ROUND + 1 == 2) {
            secondRound(aliens);
        }
        if (Preferences.CURRENT_ROUND + 1 == 3) {
            thirdRound(aliens);
        }
        if (Preferences.CURRENT_ROUND + 1 == 4) {
            fourthRound(aliens);
        }
        if (Preferences.CURRENT_ROUND + 1 == 5) {
            fifthRound(aliens);
        }
        root.getChildren().addAll(aliens);
        units.addAll(aliens);
    }

    /*
        Initializing the firstRound aliens strategy
    */
    private void firstRound(List<Sprite> units) {
        for (int alien = 0, STEP = Preferences.WINDOW_WIDTH / 6; alien < Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND]; alien++, STEP += Preferences.WINDOW_WIDTH / 6) {
            units.add(
                    new Sprite(
                            STEP - Preferences.ALIEN_WIDTH,
                            10 + Preferences.ALIEN_HEIGHT,
                            Preferences.ALIEN_WIDTH,
                            Preferences.ALIEN_HEIGHT,
                            Preferences.SpriteType.ALIEN.toString(),
                            Color.DARKRED)
            );
        }
    }
    /*
        Initializing the second aliens strategy
    */
    private void secondRound(List<Sprite> units) {
        for (int alien = 0, STEP = Preferences.WINDOW_WIDTH / 6; alien < Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND] / 2; alien++, STEP += Preferences.WINDOW_WIDTH / 6) {
            units.add(
                    new Sprite(
                            STEP - Preferences.ALIEN_WIDTH,
                            10 + Preferences.ALIEN_HEIGHT,
                            Preferences.ALIEN_WIDTH,
                            Preferences.ALIEN_HEIGHT,
                            Preferences.SpriteType.ALIEN.toString(),
                            Color.DARKRED)
            );
        }
        for (int alien = Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND] / 2, STEP = Preferences.WINDOW_WIDTH / 5; alien < Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND] - 1; alien++, STEP += Preferences.WINDOW_WIDTH / 5) {
            units.add(
                    new Sprite(
                            STEP - Preferences.ALIEN_WIDTH,
                            10 + 2 * Preferences.ALIEN_HEIGHT,
                            Preferences.ALIEN_WIDTH,
                            Preferences.ALIEN_HEIGHT,
                            Preferences.SpriteType.ALIEN.toString(),
                            Color.DARKRED)
            );
        }
        units.add(
                new Sprite(
                        Preferences.PLAYER_START_X,
                        10 + 3 * Preferences.ALIEN_HEIGHT,
                        Preferences.ALIEN_WIDTH,
                        Preferences.ALIEN_HEIGHT,
                        Preferences.SpriteType.ALIEN.toString(),
                        Color.DARKRED)
        );
    }
    /*
        Initializing the third aliens strategy
    */
    private void thirdRound(List<Sprite> units) {
        for (int alien = 0, STEP = Preferences.WINDOW_WIDTH / 6; alien < Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND] / 2 - 1; alien++, STEP += Preferences.WINDOW_WIDTH / 6) {
            units.add(
                    new Sprite(
                            STEP - Preferences.ALIEN_WIDTH / 2,
                            10 + Preferences.ALIEN_HEIGHT,
                            Preferences.ALIEN_WIDTH,
                            Preferences.ALIEN_HEIGHT,
                            Preferences.SpriteType.ALIEN.toString(),
                            Color.DARKRED)
            );
        }
        for (int alien = Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND] / 2 - 1, STEP = Preferences.WINDOW_WIDTH / 5; alien < Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND] - 2; alien++, STEP += Preferences.WINDOW_WIDTH / 5) {
            units.add(
                    new Sprite(
                            STEP - Preferences.ALIEN_WIDTH / 2,
                            10 + 2 * Preferences.ALIEN_HEIGHT,
                            Preferences.ALIEN_WIDTH,
                            Preferences.ALIEN_HEIGHT,
                            Preferences.SpriteType.ALIEN.toString(),
                            Color.DARKRED)
            );
        }
        for (int alien = Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND] - 2, STEP = Preferences.WINDOW_WIDTH / 4; alien < Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND]; alien++, STEP += Preferences.WINDOW_WIDTH / 4) {
            units.add(
                    new Sprite(
                            STEP - Preferences.ALIEN_WIDTH / 2,
                            10 + 3 * Preferences.ALIEN_HEIGHT,
                            Preferences.ALIEN_WIDTH,
                            Preferences.ALIEN_HEIGHT,
                            Preferences.SpriteType.ALIEN.toString(),
                            Color.DARKRED)
            );
        }
    }
    /*
        Initializing the fourth aliens strategy
     */
    private void fourthRound(List<Sprite> units) {
        for (int alien = 0, STEP = Preferences.WINDOW_WIDTH / 4; alien < Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND]; alien++, STEP += Preferences.WINDOW_WIDTH / 4) {
            units.add(
                    new Sprite(
                            (STEP - Preferences.ALIEN_WIDTH),
                            alien % 2 == 0 ? 10 + Preferences.ALIEN_HEIGHT : 10 + 2 * Preferences.ALIEN_HEIGHT,
                            Preferences.ALIEN_WIDTH,
                            Preferences.ALIEN_HEIGHT,
                            Preferences.SpriteType.ALIEN.toString(),
                            Color.DARKRED)
            );
        }
    }
    /*
        Initializing the fifth aliens strategy
     */
    private void fifthRound(List<Sprite> units) {
        units.add(
                new Sprite(
                        Preferences.ALIEN_START_X - Preferences.ALIEN_WIDTH / 2,
                        2 * Preferences.ALIEN_HEIGHT,
                        Preferences.ALIEN_WIDTH,
                        Preferences.ALIEN_HEIGHT,
                        Preferences.SpriteType.ALIEN.toString(),
                        Color.DARKRED
                )
        );
    }
}
