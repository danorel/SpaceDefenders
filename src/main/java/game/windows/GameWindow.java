package game.windows;

import com.sun.javafx.binding.StringFormatter;
import game.Preferences;
import game.sprite.AlienStrategy;
import game.sprite.Sprite;
import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sun.dc.pr.PRError;

import javax.naming.directory.AttributeInUseException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameWindow extends Scene implements WindowController {
    private BorderPane root;
    private List<Sprite> units;
    private AnimationTimer timer;

    private double stopwatch;

    public GameWindow(Parent root, double width, double height) {
        super(root, width, height);
        this.root = (BorderPane) root;
    }

    @Override
    public void display(Stage primaryStage, List<Scene> scenes){

        try {
            this.getStylesheets().addAll(
                    new URL("file:css/styles.css")
                            .toExternalForm()
            );
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        }

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
                        Preferences.PLAYER_WIDTH,
                        Preferences.PLAYER_HEIGHT,
                        Preferences.SpriteType.PLAYER.toString(),
                        Preferences.IMAGE.impl_getUrl()
                )
        );
        root.getChildren().add(units.get(0));
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
                                ){
                                    if(Preferences.PLAYER_VELOCITY > 0) {
                                        Preferences.PLAYER_VELOCITY = -Preferences.PLAYER_VELOCITY;
                                    }
                                }
                            });
                    break;
                case D:
                    units
                            .forEach(unit -> {
                                if (unit.getType().equals(Preferences.SpriteType.PLAYER.toString())
                                ){
                                    if(Preferences.PLAYER_VELOCITY < 0){
                                        Preferences.PLAYER_VELOCITY = - Preferences.PLAYER_VELOCITY;
                                    }
                                }
                            });
                    break;
                case ENTER:
                    units
                            .forEach(unit -> {
                                if (unit.getType().equals(Preferences.SpriteType.PLAYER.toString())
                                ){
                                    try {
                                        Sprite shot = unit.shoot();
                                        units.add(shot);
                                        root.getChildren().add(shot);
                                    } catch (AttributeInUseException exception) {
                                        exception.printStackTrace();
                                    }
                                }
                            });
                    break;
                case ESCAPE:
                    timer.stop();
                    ((PauseWindow) scenes.get(5)).display(primaryStage, scenes);
                    primaryStage.setScene(scenes.get(5));
                    break;
            }
        });
    }

    private void run(Stage primaryStage, List<Scene> scenes) {
        stopwatch = System.currentTimeMillis();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update(primaryStage, scenes);
            }
        };
        timer.start();
    }

    private void update(Stage primaryStage, List<Scene> scenes) {
        /*
            Generating the index of the alien, who will make a shot
         */
        int shooting = (int) (Math.random() * Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND]);
        /*
            Moving all the aliens line
         */
        units
                .forEach(unit -> {
                    if(unit.getType().equals(Preferences.SpriteType.ALIEN.toString())){
                        unit.move();
                    }
                });

        units
                .forEach(unit -> {
                    if(unit.getType().equals(Preferences.SpriteType.PLAYER.toString())){
                        unit.move();
                    }
                });
        /*
            Make the alien shoot, generated above
         */
        try {
            int chance = (int) (Math.random() * 100);
            if (chance <= Preferences.ALIEN_SHOOT_CHANCE) {
                Sprite shot = units
                        .stream()
                        .filter(unit -> unit.getType().equals(Preferences.SpriteType.ALIEN.toString()))
                        .collect(Collectors.toList())
                        .get(shooting)
                        .shoot();
                units.add(shot);
                root.getChildren().add(shot);
            }
        } catch (AttributeInUseException exception) {
            exception.printStackTrace();
        }
        units
                .forEach(unit -> {
                    if(unit.getType().equals(Preferences.SpriteType.ALIEN_ROCKET.toString())){
                        try {
                            unit.moveDown();
                        } catch (AttributeInUseException exception) {
                            exception.printStackTrace();
                        }
                        units
                                .forEach(hero -> {
                                    if(hero.getType().equals(Preferences.SpriteType.PLAYER.toString())){
                                        if(unit.intersects(hero)){
                                            hero.die();
                                            unit.die();
                                            units.remove(hero);
                                            units.remove(unit);
                                            clearGameWindow();
                                            timer.stop();
                                            ((LossWindow) scenes.get(1)).display(primaryStage, scenes);
                                            primaryStage.setScene(scenes.get(1));
                                        }
                                    }
                                });
                    }
                    if(unit.getType().equals(Preferences.SpriteType.PLAYER_ROCKET.toString())){
                        try {
                            unit.moveUp();
                        } catch (AttributeInUseException exception) {
                            exception.printStackTrace();
                        }
                        units
                                .forEach(alien -> {
                                    if(alien.getType().equals(Preferences.SpriteType.ALIEN.toString()) || alien.getType().equals(Preferences.SpriteType.ALIEN_ROCKET.toString())){
                                        if(unit.intersects(alien)){
                                            alien.die();
                                            unit.die();
                                            units.remove(alien);
                                            units.remove(unit);
                                            int aliensAmount = (int) units
                                                    .stream()
                                                    .filter(specie -> specie.getType().equals(Preferences.SpriteType.ALIEN.toString()))
                                                    .count();
                                            if(aliensAmount == 0){
                                                Preferences.isRoundWon = true;
                                            }
                                        }
                                    }
                                });
                    }
                });

        if(Preferences.isRoundWon){
            if(Preferences.CURRENT_ROUND == Preferences.MAX_ROUND){
                Preferences.isGameWon = true;

            } else {
                Preferences.CURRENT_ROUND += 1;
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
            Preferences.isRoundWon = false;
        }

        if(Preferences.isGameWon){
            ((WinWindow) scenes.get(7)).display(primaryStage, scenes);
            primaryStage.setScene(scenes.get(7));
            Preferences.isGameWon = false;
        }

        root.getChildren().removeIf(predicate -> {
            Sprite sprite = (Sprite) predicate;
            return !sprite.isAlive();
        });
    }


//    private void checkIntersection(Sprite sprite, ArrayList<Sprite> aliens) {
//        aliens.forEach(alien -> {
//            if (sprite.intersects(alien)) {
//                alien.die();
//                aliens.remove(alien);
//                sprite.die();
//                playerRockets.remove(sprite);
//            }
//        });
//    }

    private void clearGameWindow() {
        root.getChildren().removeIf(predicate -> {
            Sprite sprite = (Sprite) predicate;
            return !sprite.isAlive() || sprite.isAlive();
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
