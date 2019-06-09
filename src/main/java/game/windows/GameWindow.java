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
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
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

    private Label kills, round, level;
    private int aliensAmount = 0;

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

        kills = new Label();
        kills.setText("KILLS: " + Preferences.CURRENT_KILLS);
        kills.setFont(Preferences.FONT);
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
        round.setFont(Preferences.FONT);
        round.setBackground(
                new Background(
                        new BackgroundFill(
                                Color.GOLD,
                                CornerRadii.EMPTY,
                                Insets.EMPTY
                        )
                )
        );

        level = new Label();
        level.setText("LEVEL: 1");
        level.setFont(Preferences.FONT);
        level.setBackground(
                new Background(
                        new BackgroundFill(
                                Color.GREEN,
                                CornerRadii.EMPTY,
                                Insets.EMPTY
                        )
                )
        );

        root.setTop(round);
        root.setBottom(level);
        root.setRight(kills);
        kills.setTranslateY(kills.getTranslateY() - 40);

        initSprites();
        initKeyActions(primaryStage, scenes);
        run(primaryStage, scenes);
    }

    private void initSprites() {
        units = new ArrayList<>();
        units.add(
                new Sprite(
                        Preferences.PLAYER_START_X,
                        Preferences.PLAYER_START_Y,
                        Preferences.SpriteType.PLAYER.toString(),
                        Preferences.PLAYER_IMAGE.impl_getUrl()
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
                                    if (Preferences.PLAYER_HORIZONTAL_VELOCITY > 0) {
                                        Preferences.PLAYER_HORIZONTAL_VELOCITY = -Preferences.PLAYER_HORIZONTAL_VELOCITY;
                                    }
                                }
                            });
                    break;
                case D:
                    units
                            .forEach(unit -> {
                                if (unit.getType().equals(Preferences.SpriteType.PLAYER.toString())
                                ) {
                                    Preferences.PLAYER_VERTICAL_VELOCITY = 0.5;
                                    if (Preferences.PLAYER_HORIZONTAL_VELOCITY < 0) {
                                        Preferences.PLAYER_HORIZONTAL_VELOCITY = -Preferences.PLAYER_HORIZONTAL_VELOCITY;
                                    }
                                    Preferences.PLAYER_VERTICAL_VELOCITY = 0;
                                }
                            });
                    break;
                case W:
                    units
                            .forEach(unit -> {
                                if(unit.getType().equals(Preferences.SpriteType.PLAYER.toString())){
                                    if(Preferences.PLAYER_VERTICAL_VELOCITY >= 0){
                                        Preferences.PLAYER_VERTICAL_VELOCITY = -3;
                                    }
                                }
                            });
                    break;
                case S:
                    units
                            .forEach(unit -> {
                                if(unit.getType().equals(Preferences.SpriteType.PLAYER.toString())){
                                    if(Preferences.PLAYER_VERTICAL_VELOCITY < 0){
                                        Preferences.PLAYER_VERTICAL_VELOCITY = 3;
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
                                        if(Preferences.CURRENT_KILLS >= 0 && Preferences.CURRENT_KILLS < 10){
                                            Sprite shot = unit.fire();
                                            units.add(shot);
                                            root.getChildren().add(shot);
                                        } else if(Preferences.CURRENT_KILLS >= 10 && Preferences.CURRENT_KILLS < 18){
                                            if(Preferences.CURRENT_KILLS == 10){
                                                level.setText("LEVEL: 2");
                                                level.setBackground(
                                                        new Background(
                                                                new BackgroundFill(
                                                                        Color.SILVER,
                                                                        CornerRadii.EMPTY,
                                                                        Insets.EMPTY
                                                                )
                                                        )
                                                );
                                            }
                                            Sprite center = unit.fire();
                                            Sprite left = unit.fire();
                                            center.setTranslateX(center.getTranslateX() + 15);
                                            left.setTranslateX(left.getTranslateX() - 15);
                                            units.add(center);
                                            units.add(left);
                                            root.getChildren().addAll(center, left);
                                        } else if(Preferences.CURRENT_KILLS >= 18){
                                            if(Preferences.CURRENT_KILLS == 18){
                                                level.setText("LEVEL: 3");
                                                level.setBackground(
                                                        new Background(
                                                                new BackgroundFill(
                                                                        Color.GOLD,
                                                                        CornerRadii.EMPTY,
                                                                        Insets.EMPTY
                                                                )
                                                        )
                                                );
                                            }
                                            level.setText("LEVEL:3");
                                            Sprite left = unit.fire();
                                            Sprite center = unit.fire();
                                            Sprite right = unit.fire();
                                            left.setTranslateX(left.getTranslateX() - 15);
                                            center.setTranslateY(right.getTranslateY() - 10);
                                            right.setTranslateX(center.getTranslateX() + 15);
                                            units.add(left);
                                            units.add(center);
                                            units.add(right);
                                            root.getChildren().addAll(left, center, right);
                                        }

                                    } catch (AttributeInUseException exception) {
                                        exception.printStackTrace();
                                    }
                                }
                            });
                    break;
                case ESCAPE:
                    scenes.set(
                            5,
                            new PauseWindow(
                                    new Group(),
                                    Preferences.WINDOW_WIDTH,
                                    Preferences.WINDOW_HEIGHT
                            )
                    );
                    ((PauseWindow) scenes.get(5)).display(primaryStage, scenes);
                    ((PauseWindow) scenes.get(5)).initKeyActions(primaryStage, scenes);
                    primaryStage.setScene(scenes.get(5));
                    break;
                default:
                    break;
            }
        });
    }

    private void run(Stage primaryStage, List<Scene> scenes) {
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(primaryStage, scenes);
            }
        };
        animationTimer.start();
    }

    private void update(Stage primaryStage, List<Scene> scenes) {
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
        int alienShootingChance = (int) (Math.random() * aliensAmount);
        /*
            Make the alien fire, generated above
         */
        try {
            int chance = (int) (Math.random() * Preferences.ROCKET_LAUNCH_CHANCE_RANGE);
            if (chance <= Preferences.ROCKET_LAUNCH_CHANCE) {
                if(Preferences.CURRENT_ROUND <= 1){
                    Sprite shot = units
                            .stream()
                            .filter(unit -> unit.getType().equals(Preferences.SpriteType.ALIEN.toString()))
                            .collect(Collectors.toList())
                            .get(alienShootingChance)
                            .fire();
                    units.add(shot);
                    root.getChildren().add(shot);
                } else if(Preferences.CURRENT_ROUND <= 3){
                    Sprite left = units
                            .stream()
                            .filter(unit -> unit.getType().equals(Preferences.SpriteType.ALIEN.toString()))
                            .collect(Collectors.toList())
                            .get(alienShootingChance)
                            .fire();
                    Sprite right = units
                            .stream()
                            .filter(unit -> unit.getType().equals(Preferences.SpriteType.ALIEN.toString()))
                            .collect(Collectors.toList())
                            .get(alienShootingChance)
                            .fire();
                    left.setTranslateX(left.getTranslateX() - 15);
                    right.setTranslateX(right.getTranslateX() + 15);
                    units.add(left);
                    units.add(right);
                    root.getChildren().addAll(
                            left,
                            right
                    );
                } else {
                    Sprite left = units
                            .stream()
                            .filter(unit -> unit.getType().equals(Preferences.SpriteType.ALIEN.toString()))
                            .collect(Collectors.toList())
                            .get(alienShootingChance)
                            .fire();
                    Sprite center = units
                            .stream()
                            .filter(unit -> unit.getType().equals(Preferences.SpriteType.ALIEN.toString()))
                            .collect(Collectors.toList())
                            .get(alienShootingChance)
                            .fire();
                    Sprite right = units
                            .stream()
                            .filter(unit -> unit.getType().equals(Preferences.SpriteType.ALIEN.toString()))
                            .collect(Collectors.toList())
                            .get(alienShootingChance)
                            .fire();

                    left.setTranslateX(left.getTranslateX() - 15);
                    center.setTranslateY(center.getTranslateY() + 10);
                    right.setTranslateX(right.getTranslateX() + 15);

                    units.add(left);
                    units.add(center);
                    units.add(right);

                    root.getChildren().addAll(
                            left,
                            center,
                            right
                    );
                }
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
                                                            new StackPane(),
                                                            Preferences.WINDOW_WIDTH,
                                                            Preferences.WINDOW_HEIGHT
                                                    )
                                            );
                                            ((LossWindow) scenes.get(1)).display(primaryStage, scenes);
                                            ((LossWindow) scenes.get(1)).initKeyActions(primaryStage, scenes);
                                            primaryStage.setScene(scenes.get(1));
                                            animationTimer.stop();
                                        }
                                    }
                                });
                    }
                    if (unit.getType().equals(Preferences.SpriteType.PLAYER_ROCKET.toString())) {
                        unit.moveUp();
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
                                            aliensAmount = (int) units
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
                                                    new StackPane(),
                                                    Preferences.WINDOW_WIDTH,
                                                    Preferences.WINDOW_HEIGHT
                                            )
                                    );
                                    ((LossWindow) scenes.get(1)).display(primaryStage, scenes);
                                    ((LossWindow) scenes.get(1)).initKeyActions(primaryStage, scenes);
                                    primaryStage.setScene(scenes.get(1));
                                    animationTimer.stop();
                                }
                            }
                        });
                    }
                });

//        units
//                .forEach(unit -> {
//                    if(unit.getType().equals(Preferences.SpriteType.PLAYER.toString())){
//                        if(!unit.isAlive()){
//                            scenes.set(
//                                    1,
//                                    new LossWindow(
//                                            new Group(),
//                                            Preferences.WINDOW_WIDTH,
//                                            Preferences.WINDOW_HEIGHT
//                                    )
//                            );
//                            ((LossWindow) scenes.get(1)).display(primaryStage, scenes);
//                            ((LossWindow) scenes.get(1)).initKeyActions(primaryStage, scenes);
//                            primaryStage.setScene(scenes.get(1));
//                        }
//                    }
//                });

        if(Preferences.CURRENT_ROUND >= Preferences.MAX_ROUND - 1){
            Preferences.IS_ROUND_WON = true;
        }

        if (Preferences.IS_ROUND_WON) {
            if (Preferences.CURRENT_ROUND >= Preferences.MAX_ROUND - 1) {
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
                ((GameWindow) scenes.get(0)).initKeyActions(primaryStage, scenes);
                primaryStage.setScene(scenes.get(0));
                animationTimer.stop();
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
            ((WinWindow) scenes.get(7)).initKeyActions(primaryStage, scenes);
            primaryStage.setScene(scenes.get(7));
            animationTimer.stop();
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
