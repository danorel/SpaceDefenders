package game.sprite;

import game.Preferences;
import javafx.scene.layout.BorderPane;

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
                            Preferences.SpriteType.ALIEN.toString(),
                            "file:resources/models/plane_models/alien_models/ufo-1.png"
                    )
            );
        }
    }
    /*
        Initializing the second aliens strategy
    */
    private void secondRound(List<Sprite> units) {
        Preferences.METEOR_VELOCITY = 6;
        Preferences.METEOR_APPEAR_CHANCE = 10;
        Preferences.ROCKET_LAUNCH_CHANCE = 6;
        for (int alien = 0, STEP = Preferences.WINDOW_WIDTH / 6; alien < Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND] / 2; alien++, STEP += Preferences.WINDOW_WIDTH / 6) {
            units.add(
                    new Sprite(
                            STEP - Preferences.ALIEN_WIDTH,
                            10 + Preferences.ALIEN_HEIGHT,
                            Preferences.SpriteType.ALIEN.toString(), "file:resources/models/plane_models/alien_models/ufo-2.png"
                    )
            );
        }
        for (int alien = Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND] / 2, STEP = Preferences.WINDOW_WIDTH / 5; alien < Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND] - 1; alien++, STEP += Preferences.WINDOW_WIDTH / 5) {
            units.add(
                    new Sprite(
                            STEP - Preferences.ALIEN_WIDTH,
                            10 + 2 * Preferences.ALIEN_HEIGHT,
                            Preferences.SpriteType.ALIEN.toString(),
                            "file:resources/models/plane_models/alien_models/ufo-2.png"
                    )
            );
        }
        units.add(
                new Sprite(
                        Preferences.PLAYER_START_X,
                        10 + 3 * Preferences.ALIEN_HEIGHT,
                        Preferences.SpriteType.ALIEN.toString(),
                        "file:resources/models/plane_models/alien_models/ufo-2.png"
                )
        );
    }
    /*
        Initializing the third aliens strategy
    */
    private void thirdRound(List<Sprite> units) {
        Preferences.ROCKET_LAUNCH_CHANCE = 7;
        Preferences.METEOR_APPEAR_CHANCE = 15;
        for (int alien = 0, STEP = Preferences.WINDOW_WIDTH / 5; alien < Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND] / 2; alien++, STEP += Preferences.WINDOW_WIDTH / 5) {
            units.add(
                    new Sprite(
                            STEP - Preferences.ALIEN_WIDTH / 2,
                            10 + Preferences.ALIEN_HEIGHT,
                            Preferences.SpriteType.ALIEN.toString(), "file:resources/models/plane_models/alien_models/ufo-3.png"
                    )
            );
        }
        for (int alien = Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND] / 2, STEP = Preferences.WINDOW_WIDTH / 4; alien < Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND] - 1; alien++, STEP += Preferences.WINDOW_WIDTH / 4) {
            units.add(
                    new Sprite(
                            STEP - Preferences.ALIEN_WIDTH / 2,
                            10 + 2 * Preferences.ALIEN_HEIGHT,
                            Preferences.SpriteType.ALIEN.toString(),
                            "file:resources/models/plane_models/alien_models/ufo-3.png"
                    )
            );
        }
        units.add(
                new Sprite(
                        Preferences.WINDOW_WIDTH / 2  - Preferences.ALIEN_WIDTH / 2,
                        10 + 3 * Preferences.ALIEN_HEIGHT,
                        Preferences.SpriteType.ALIEN.toString(),
                        "file:resources/models/plane_models/alien_models/ufo-3.png"
                )
        );
    }
    /*
        Initializing the fourth aliens strategy
     */
    private void fourthRound(List<Sprite> units) {
        Preferences.ROCKET_LAUNCH_CHANCE = 8;
        Preferences.ROCKET_VELOCITY = 6;
        for (int alien = 0, STEP = Preferences.WINDOW_WIDTH / 4; alien < Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND]; alien++, STEP += Preferences.WINDOW_WIDTH / 4) {
            units.add(
                    new Sprite(
                            (STEP - Preferences.ALIEN_WIDTH),
                            alien % 2 == 0 ? 10 + Preferences.ALIEN_HEIGHT : 10 + 2 * Preferences.ALIEN_HEIGHT,
                            Preferences.SpriteType.ALIEN.toString(),
                            "file:resources/models/plane_models/alien_models/ufo-4.png"
                    )
            );
        }
    }
    /*
        Initializing the fifth aliens strategy
     */
    private void fifthRound(List<Sprite> units) {
        Preferences.METEOR_APPEAR_CHANCE = 20;
        Preferences.ROCKET_LAUNCH_CHANCE = 10;
        units.add(
                new Sprite(
                        Preferences.ALIEN_START_X - Preferences.ALIEN_WIDTH / 2,
                        2 * Preferences.ALIEN_HEIGHT,
                        Preferences.SpriteType.ALIEN.toString(),                           "file:resources/models/plane_models/alien_models/boss.png"
                )
        );
    }
}
