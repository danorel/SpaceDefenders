package game.windows;

import com.sun.javafx.binding.StringFormatter;
import game.Preferences;
import game.sprite.AlienStrategy;
import game.sprite.Sprite;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;

import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import javax.naming.directory.AttributeInUseException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameWindow extends Scene implements WindowController {
    private BorderPane root;
    private List<Sprite> units;
    private AnimationTimer animationTimer;

    private Label kills, round;

    public GameWindow(Parent root, double width, double height) {
        super(root, width, height);
        this.root = (BorderPane) root;
    }

    @Override
    public void display(Stage primaryStage, List<Scene> scenes) {
        Preferences.IS_ROUND_WON = false;
        Preferences.IS_GAME_WON = false;

        try {
            this.getStylesheets().addAll(
                    new URL("file:css/styles.css")
                            .toExternalForm()
            );
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        }

        Font font = Font.loadFont(("file:font/15211.ttf"), 30);

        kills = new Label();
        kills.setText("KILLS: " + Preferences.CURRENT_KILLS);
        kills.setFont(font);
        kills.setBackground(
                new Background(
                        new BackgroundFill(
                                Color.AQUA,
                                CornerRadii.EMPTY,
                                Insets.EMPTY
                        )
                )
        );

        round = new Label();
        round.setText("ROUND: " + String.valueOf(Preferences.CURRENT_ROUND + 1));
        round.setFont(font);
        round.setBackground(
                new Background(
                        new BackgroundFill(
                                Color.GOLD,
                                CornerRadii.EMPTY,
                                Insets.EMPTY
                        )
                )
        );

        root.setTop(round);
        root.setBottom(kills);

        initSprites();
        initKeyActions(primaryStage, scenes);
        if(Preferences.IS_VERSUS_HUMAN){
            runVsHuman(primaryStage, scenes);
        } else {
            runVsComputer(primaryStage, scenes);
        }
    }

    private void initSprites() {
        units = new ArrayList<>();
        units.add(
                new Sprite(
                        Preferences.PLAYER_START_X,
                        Preferences.PLAYER_START_Y,
                        Preferences.PLAYER_WIDTH,
                        Preferences.PLAYER_HEIGHT,
                        Preferences.SpriteType.PLAYER.toString(),
                        Preferences.IMAGE.impl_getUrl()
                )
        );
        root.getChildren().add(
                units.get(0)
        );
        AlienStrategy strategy = new AlienStrategy();
        strategy.build(Preferences.CURRENT_ROUND, units, root);
    }

    @Override
    public void initKeyActions(Stage primaryStage, List<Scene> scenes) {
        setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A:
                    units
                            .forEach(unit -> {
                                if (unit.getType().equals(Preferences.SpriteType.PLAYER.toString())
                                ) {
                                    if (Preferences.PLAYER_VELOCITY > 0) {
                                        Preferences.PLAYER_VELOCITY = -Preferences.PLAYER_VELOCITY;
                                    }
                                }
                            });
                    break;
                case D:
                    units
                            .forEach(unit -> {
                                if (unit.getType().equals(Preferences.SpriteType.PLAYER.toString())
                                ) {
                                    if (Preferences.PLAYER_VELOCITY < 0) {
                                        Preferences.PLAYER_VELOCITY = -Preferences.PLAYER_VELOCITY;
                                    }
                                }
                            });
                    break;
                case ENTER:
                    units
                            .forEach(unit -> {
                                if (unit.getType().equals(Preferences.SpriteType.PLAYER.toString())
                                ) {
                                    try {
                                        Sprite shot = unit.fire();
                                        units.add(shot);
                                        root.getChildren().add(shot);
                                    } catch (AttributeInUseException exception) {
                                        exception.printStackTrace();
                                    }
                                }
                            });
                    break;
                case ESCAPE:
                    animationTimer.handle(0);
                    ((PauseWindow) scenes.get(5)).display(primaryStage, scenes);
                    primaryStage.setScene(scenes.get(5));
                    break;
            }
        });
    }

    private void runVsComputer(Stage primaryStage, List<Scene> scenes) {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateComputerVersion(primaryStage, scenes);
            }
        };
        animationTimer.start();
    }

    private void runVsHuman(Stage primaryStage, List<Scene> scenes) {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateHumanVersion(primaryStage, scenes);
            }
        };
        animationTimer.start();
    }

    private void updateComputerVersion(Stage primaryStage, List<Scene> scenes) {
        /*
            Moving all the aliens line
         */
        units
                .forEach(unit -> {
                    if (unit.getType().equals(Preferences.SpriteType.ALIEN.toString())) {
                        unit.move();
                    }
                });

        units
                .forEach(unit -> {
                    if (unit.getType().equals(Preferences.SpriteType.PLAYER.toString())) {
                        unit.move();
                    }
                });
        /*
            Meteor appearing chance
         */
        int meteorAppearingChance = (int) (Math.random() * Preferences.METEOR_APPEAR_RANGE);
        if (meteorAppearingChance <= Preferences.METEOR_APPEAR_CHANCE) {
            Sprite meteor = new Sprite(
                    (int) (Math.random() * Preferences.WINDOW_WIDTH),
                    -Preferences.METEOR_HEIGHT,
                    Preferences.METEOR_WIDTH,
                    Preferences.METEOR_HEIGHT,
                    Preferences.SpriteType.METEOR.toString(),
                    "file:resources/models/effects/meteor.png"
            );
            units.add(meteor);
            root.getChildren().add(meteor);
        }
        units
                .forEach(unit -> {
                    if(unit.getType().equals(Preferences.SpriteType.METEOR.toString())){
                        unit.moveDown();
                    }
                });
        /*
            Generating the index of the alien, who will make a shot
         */
        int alienShootingChance = (int) (Math.random() * Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND]);
        /*
            Make the alien fire, generated above
         */
        try {
            int chance = (int) (Math.random() * Preferences.ROCKET_LAUNCH_CHANCE_RANGE);
            if (chance <= Preferences.ROCKET_LAUNCH_CHANCE) {
                Sprite shot = units
                        .stream()
                        .filter(unit -> unit.getType().equals(Preferences.SpriteType.ALIEN.toString()))
                        .collect(Collectors.toList())
                        .get(alienShootingChance)
                        .fire();
                units.add(shot);
                root.getChildren().add(shot);
            }
        } catch (AttributeInUseException exception) {
            exception.printStackTrace();
        }
        units
                .forEach(unit -> {
                    if (unit.getType().equals(Preferences.SpriteType.ALIEN_ROCKET.toString())) {
                        unit.moveDown();
                        units
                                .forEach(hero -> {
                                    if (hero.getType().equals(Preferences.SpriteType.PLAYER.toString())) {
                                        if (unit.intersects(hero)) {
                                            hero.die();
                                            unit.die();
                                            units.remove(hero);
                                            units.remove(unit);
                                            animationTimer.stop();
                                            scenes.set(
                                                    1,
                                                    new LossWindow(
                                                            new Group(),
                                                            Preferences.WINDOW_WIDTH,
                                                            Preferences.WINDOW_HEIGHT
                                                    )
                                            );
                                            ((LossWindow) scenes.get(1)).display(primaryStage, scenes);
                                            primaryStage.setScene(scenes.get(1));
                                        }
                                    }
                                });
                    }
                    if (unit.getType().equals(Preferences.SpriteType.PLAYER_ROCKET.toString())) {
                        try {
                            unit.moveUp();
                        } catch (AttributeInUseException exception) {
                            exception.printStackTrace();
                        }
                        units
                                .forEach(alien -> {
                                    if (alien.getType().equals(Preferences.SpriteType.ALIEN.toString())) {
                                        if (unit.intersects(alien)) {
                                            alien.die();
                                            unit.die();
                                            units.remove(alien);
                                            units.remove(unit);
                                            Preferences.CURRENT_KILLS += 1;
                                            kills.setText("KILLS: " + Preferences.CURRENT_KILLS);
                                            int aliensAmount = (int) units
                                                    .stream()
                                                    .filter(specie -> specie.getType().equals(Preferences.SpriteType.ALIEN.toString()))
                                                    .count();
                                            if (aliensAmount == 0) {
                                                Preferences.IS_ROUND_WON = true;
                                            }
                                        }
                                    }
                                    if(alien.getType().equals(Preferences.SpriteType.ALIEN_ROCKET.toString())){
                                        if (unit.intersects(alien)) {
                                            alien.die();
                                            unit.die();
                                            units.remove(alien);
                                            units.remove(unit);
                                        }
                                    }
                                });
                    }
                });

        units
                .forEach(unit -> {
                    if(unit.getType().equals(Preferences.SpriteType.METEOR.toString())){
                        units.forEach(sprite -> {
                            if(sprite.getType().equals(Preferences.SpriteType.PLAYER.toString())){
                                if(unit.intersects(sprite)){
                                    sprite.die();
                                    unit.die();
                                    units.remove(sprite);
                                    units.remove(unit);
                                    animationTimer.stop();
                                    scenes.set(
                                            1,
                                            new LossWindow(
                                                    new Group(),
                                                    Preferences.WINDOW_WIDTH,
                                                    Preferences.WINDOW_HEIGHT
                                            )
                                    );
                                    ((LossWindow) scenes.get(1)).display(primaryStage, scenes);
                                    primaryStage.setScene(scenes.get(1));
                                }
                            }
                        });
                    }
                });

        if (Preferences.IS_ROUND_WON) {
            if (Preferences.CURRENT_ROUND == Preferences.MAX_ROUND) {
                Preferences.IS_GAME_WON = true;
            } else {
                Preferences.CURRENT_ROUND += 1;
                Preferences.IS_ROUND_WON = false;
                scenes.set(
                        0,
                        new GameWindow(
                                new BorderPane(),
                                Preferences.WINDOW_WIDTH,
                                Preferences.WINDOW_HEIGHT
                        )
                );
                ((GameWindow) scenes.get(0)).display(primaryStage, scenes);
                primaryStage.setScene(scenes.get(0));
            }
        }

        if (Preferences.IS_GAME_WON) {
            scenes.set(
                    7,
                    new WinWindow(
                            new Group(),
                            Preferences.WINDOW_WIDTH,
                            Preferences.WINDOW_HEIGHT
                    )
            );
            ((WinWindow) scenes.get(7)).display(primaryStage, scenes);
            primaryStage.setScene(scenes.get(7));
        }

        root.getChildren().removeIf(predicate -> {
            if(predicate instanceof Sprite){
                Sprite sprite = (Sprite) predicate;
                return !sprite.isAlive();
            } else {
                return false;
            }
        });
    }

    private void updateHumanVersion(Stage primaryStage, List<Scene> scenes) {
        /*
            Moving all the aliens line
         */
        units
                .forEach(unit -> {
                    if (unit.getType().equals(Preferences.SpriteType.ALIEN.toString())) {
                        unit.move();
                    }
                });

        units
                .forEach(unit -> {
                    if (unit.getType().equals(Preferences.SpriteType.PLAYER.toString())) {
                        unit.move();
                    }
                });
        /*
            Meteor appearing chance
         */
        int meteorAppearingChance = (int) (Math.random() * Preferences.METEOR_APPEAR_RANGE);
        if (meteorAppearingChance <= Preferences.METEOR_APPEAR_CHANCE) {
            Sprite meteor = new Sprite(
                    (int) (Math.random() * Preferences.WINDOW_WIDTH),
                    -Preferences.METEOR_HEIGHT,
                    Preferences.METEOR_WIDTH,
                    Preferences.METEOR_HEIGHT,
                    Preferences.SpriteType.METEOR.toString(),
                    "file:resources/models/effects/meteor.png"
            );
            units.add(meteor);
            root.getChildren().add(meteor);
        }
        units
                .forEach(unit -> {
                    if(unit.getType().equals(Preferences.SpriteType.METEOR.toString())){
                        unit.moveDown();
                    }
                });
        /*
            Generating the index of the alien, who will make a shot
         */
        int alienShootingChance = (int) (Math.random() * Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND]);
        /*
            Make the alien fire, generated above
         */
        try {
            int chance = (int) (Math.random() * Preferences.ROCKET_LAUNCH_CHANCE_RANGE);
            if (chance <= Preferences.ROCKET_LAUNCH_CHANCE) {
                Sprite shot = units
                        .stream()
                        .filter(unit -> unit.getType().equals(Preferences.SpriteType.ALIEN.toString()))
                        .collect(Collectors.toList())
                        .get(alienShootingChance)
                        .fire();
                units.add(shot);
                root.getChildren().add(shot);
            }
        } catch (AttributeInUseException exception) {
            exception.printStackTrace();
        }
        units
                .forEach(unit -> {
                    if (unit.getType().equals(Preferences.SpriteType.ALIEN_ROCKET.toString())) {
                        unit.moveDown();
                        units
                                .forEach(hero -> {
                                    if (hero.getType().equals(Preferences.SpriteType.PLAYER.toString())) {
                                        if (unit.intersects(hero)) {
                                            hero.die();
                                            unit.die();
                                            units.remove(hero);
                                            units.remove(unit);
                                            animationTimer.stop();
                                            scenes.set(
                                                    1,
                                                    new LossWindow(
                                                            new Group(),
                                                            Preferences.WINDOW_WIDTH,
                                                            Preferences.WINDOW_HEIGHT
                                                    )
                                            );
                                            ((LossWindow) scenes.get(1)).display(primaryStage, scenes);
                                            primaryStage.setScene(scenes.get(1));
                                        }
                                    }
                                });
                    }
                    if (unit.getType().equals(Preferences.SpriteType.PLAYER_ROCKET.toString())) {
                        try {
                            unit.moveUp();
                        } catch (AttributeInUseException exception) {
                            exception.printStackTrace();
                        }
                        units
                                .forEach(alien -> {
                                    if (alien.getType().equals(Preferences.SpriteType.ALIEN.toString())) {
                                        if (unit.intersects(alien)) {
                                            alien.die();
                                            unit.die();
                                            units.remove(alien);
                                            units.remove(unit);
                                            Preferences.CURRENT_KILLS += 1;
                                            kills.setText("KILLS: " + Preferences.CURRENT_KILLS);
                                            int aliensAmount = (int) units
                                                    .stream()
                                                    .filter(specie -> specie.getType().equals(Preferences.SpriteType.ALIEN.toString()))
                                                    .count();
                                            if (aliensAmount == 0) {
                                                Preferences.IS_ROUND_WON = true;
                                            }
                                        }
                                    }
                                    if(alien.getType().equals(Preferences.SpriteType.ALIEN_ROCKET.toString())){
                                        if (unit.intersects(alien)) {
                                            alien.die();
                                            unit.die();
                                            units.remove(alien);
                                            units.remove(unit);
                                        }
                                    }
                                });
                    }
                });

        units
                .forEach(unit -> {
                    if(unit.getType().equals(Preferences.SpriteType.METEOR.toString())){
                        units.forEach(sprite -> {
                            if(sprite.getType().equals(Preferences.SpriteType.PLAYER.toString())){
                                if(unit.intersects(sprite)){
                                    sprite.die();
                                    unit.die();
                                    units.remove(sprite);
                                    units.remove(unit);
                                    animationTimer.stop();
                                    scenes.set(
                                            1,
                                            new LossWindow(
                                                    new Group(),
                                                    Preferences.WINDOW_WIDTH,
                                                    Preferences.WINDOW_HEIGHT
                                            )
                                    );
                                    ((LossWindow) scenes.get(1)).display(primaryStage, scenes);
                                    primaryStage.setScene(scenes.get(1));
                                }
                            }
                        });
                    }
                });

        if (Preferences.IS_ROUND_WON) {
            if (Preferences.CURRENT_ROUND == Preferences.MAX_ROUND) {
                Preferences.IS_GAME_WON = true;
            } else {
                Preferences.CURRENT_ROUND += 1;
                Preferences.IS_ROUND_WON = false;
                scenes.set(
                        0,
                        new GameWindow(
                                new BorderPane(),
                                Preferences.WINDOW_WIDTH,
                                Preferences.WINDOW_HEIGHT
                        )
                );
                ((GameWindow) scenes.get(0)).display(primaryStage, scenes);
                primaryStage.setScene(scenes.get(0));
            }
        }

        if (Preferences.IS_GAME_WON) {
            scenes.set(
                    7,
                    new WinWindow(
                            new Group(),
                            Preferences.WINDOW_WIDTH,
                            Preferences.WINDOW_HEIGHT
                    )
            );
            ((WinWindow) scenes.get(7)).display(primaryStage, scenes);
            primaryStage.setScene(scenes.get(7));
        }

        root.getChildren().removeIf(predicate -> {
            if(predicate instanceof Sprite){
                Sprite sprite = (Sprite) predicate;
                return !sprite.isAlive();
            } else {
                return false;
            }
        });
    }

    @Override
    public String toString() {
        return StringFormatter.format(
                "%s[%dx%d]",
                getClass().getSimpleName(),
                this.getWidth(),
                this.getHeight()
        ).getValue();
    }
}
