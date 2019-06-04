package game;

import game.sprite.Sprite;
import game.tools.ButtonConstructor;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.naming.directory.AttributeInUseException;
import java.util.ArrayList;

public class App extends Application {

    private AnimationTimer timer;
    private Stage stage;
    private Scene game, menu, main, loss, win;
    private Group menuPane, mainPane, lossPane, winPane;
    private BorderPane canvas;

    private Sprite player;
    private ArrayList<Sprite> aliens;
    private ArrayList<Sprite> aliensRockets;
    private ArrayList<Sprite> playerRockets;
    private ArrayList<Sprite> meteors;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        this.stage.setTitle(Preferences.TITLE);
        try {
            init();
            initKeyEvents();
            initSprites();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        displayMainMenu();
        stage.setScene(main);
        stage.show();
    }

    @Override
    public void init() throws Exception {
        mainPane = new Group();
        main = new Scene(
                mainPane,
                Preferences.WINDOW_WIDTH,
                Preferences.WINDOW_HEIGHT
        );

        menuPane = new Group();
        menu = new Scene(
                menuPane,
                Preferences.WINDOW_WIDTH,
                Preferences.WINDOW_HEIGHT
        );

        lossPane = new Group();
        loss = new Scene(
                lossPane,
                Preferences.WINDOW_WIDTH,
                Preferences.WINDOW_HEIGHT
        );

        winPane = new Group();
        win = new Scene(
                winPane,
                Preferences.WINDOW_WIDTH,
                Preferences.WINDOW_HEIGHT
        );

        canvas = new BorderPane();
        canvas.setPrefSize(
                Preferences.WINDOW_WIDTH,
                Preferences.WINDOW_HEIGHT
        );

        game = new Scene(
                canvas
        );
    }

    private void initSprites() {
        player = new Sprite(
                Preferences.PLAYER_START_X,
                Preferences.PLAYER_START_Y,
                Preferences.PLAYER_WIDTH,
                Preferences.PLAYER_HEIGHT,
                Preferences.SpriteType.PLAYER.toString(),
                Color.GREEN
        );
        canvas.getChildren().add(player);
        playerRockets   = new ArrayList<>();
        aliens          = new ArrayList<>();
        aliensRockets   = new ArrayList<>();
        meteors         = new ArrayList<>();
    }

    private void initKeyEvents() {
        game.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case A:
                    try {
                        player.moveLeft();
                    } catch (AttributeInUseException exception) {
                        exception.printStackTrace();
                    }
                    break;
                case D:
                    try {
                        player.moveRight();
                    } catch (AttributeInUseException exception) {
                        exception.printStackTrace();
                    }
                    break;
                case ENTER:
                    try {
                        Sprite shot = player.shoot();
                        playerRockets.add(shot);
                        canvas
                                .getChildren()
                                .add(
                                        shot
                                );
                    } catch (AttributeInUseException exception) {
                        exception.printStackTrace();
                    }
                    break;
                case ESCAPE:
                    timer.stop();
                    stage.setScene(menu);
                    displayPauseMenu();
                    break;
            }
        });

        menu.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ESCAPE:
                    stage.setScene(game);
                    break;
            }
        });
    }

    private void displayMainMenu() {
        Button run = ButtonConstructor.construct("Run", Preferences.MAIN_BUTTON_WIDTH, Preferences.MAIN_BUTTON_HEIGHT, Preferences.MAIN_BUTTON_X, Preferences.MAIN_BUTTON_Y);
        run.setOnAction(event -> {
            stage.setScene(game);
            run();
            /*
            Label round = new Label(
                    "Round " + Preferences.CURRENT_ROUND
            );
            round.setFont(
                    new Font("Arial", 30)
            );
            canvas.getChildren().add(round);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
            */
            /*
            while (!Preferences.isGameWon) {
                if (!run()) {
                    displayLossWindow();
                } else {
                    if (Preferences.CURRENT_ROUND == Preferences.MAX_ROUND) {
                        Preferences.isGameWon = true;
                    } else {
                        /*
                        round = new Label(
                                "Round " + Preferences.CURRENT_ROUND + 1
                        );
                        round.setFont(
                                new Font("Arial", 30)
                        );
                        canvas.setCenter(round);
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException exception) {
                            exception.printStackTrace();
                        }
                        *}
                   */
        });
        Button options = ButtonConstructor.construct("Options", Preferences.MAIN_BUTTON_WIDTH, Preferences.MAIN_BUTTON_HEIGHT, Preferences.MAIN_BUTTON_X, Preferences.MAIN_BUTTON_Y + Preferences.MAIN_BUTTON_DIFFERENCE);
        Button exit = ButtonConstructor.construct("Exit", Preferences.MAIN_BUTTON_WIDTH, Preferences.MAIN_BUTTON_HEIGHT, Preferences.MAIN_BUTTON_X, Preferences.MAIN_BUTTON_Y + 2 * Preferences.MAIN_BUTTON_DIFFERENCE);
        exit.setOnAction(event -> System.exit(0));
        mainPane.getChildren().addAll(run, options, exit);
    }

    private boolean run() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                if(Preferences.isRoundWon){
                    displayWinWindow();
                }
            }
        };
        timer.start();
        startNextLevel();
        return Preferences.isRoundWon;
    }

    private void update() {
        /*
            Generating the index of the alien, who will make a shot
         */
        int shooting = (int) (Math.random() * Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND]);
        /*
            Moving all the aliens line
         */
        aliens.forEach(Sprite::move);
        /*
            Make the alien shoot, generated above
         */
        try {
            int chance = (int) (Math.random() * 100);
            if (chance <= Preferences.ALIEN_SHOOT_CHANCE) {
                Sprite shot = aliens.get(shooting).shoot();
                aliensRockets.add(shot);
                canvas.getChildren().add(shot);
            }
        } catch (AttributeInUseException exception) {
            exception.printStackTrace();
        }
        aliensRockets.forEach(sprite -> {
            try {
                sprite.moveDown();
                if (sprite.intersects(player)) {
                    player.die();
                    sprite.die();
                    aliensRockets.remove(sprite);
                    clear();
                    displayLossWindow();
                }
            } catch (AttributeInUseException exception) {
                exception.printStackTrace();
            }
        });
        playerRockets.forEach(sprite -> {
            try {
                sprite.moveUp();
                checkIntersection(sprite, aliens);
                if (aliens.size() == 0) {
                    Preferences.isRoundWon = true;
                }
                checkIntersection(sprite, aliensRockets);
            } catch (AttributeInUseException exception) {
                exception.printStackTrace();
            }
        });
        canvas.getChildren().removeIf(predicate -> {
            Sprite sprite = (Sprite) predicate;
            return !sprite.isAlive();
        });
    }

    private void checkIntersection(Sprite sprite, ArrayList<Sprite> aliens) {
        aliens.forEach(alien -> {
            if (sprite.intersects(alien)) {
                alien.die();
                aliens.remove(alien);
                sprite.die();
                playerRockets.remove(sprite);
            }
        });
    }

    private void clear() {
        canvas.getChildren().removeAll(playerRockets);
        canvas.getChildren().removeAll(player);
        canvas.getChildren().removeAll(aliensRockets);
        canvas.getChildren().removeAll(aliens);
        playerRockets = new ArrayList<>();
        aliens = new ArrayList<>();
        aliensRockets = new ArrayList<>();
        player = new Sprite(
                Preferences.PLAYER_START_X,
                Preferences.PLAYER_START_Y,
                Preferences.PLAYER_WIDTH,
                Preferences.PLAYER_HEIGHT,
                Preferences.SpriteType.PLAYER.toString(),
                Color.GREEN
        );
    }

    private void startNextLevel() {
        if (Preferences.CURRENT_ROUND + 1 == 1) {
            addRoundOneAliensLocation();
        }
        if (Preferences.CURRENT_ROUND + 1 == 2) {
            addRoundTwoAliensLocation();
        }
        if (Preferences.CURRENT_ROUND + 1 == 3) {
            addRoundThreeAliensLocation();
        }
        if (Preferences.CURRENT_ROUND + 1 == 4) {
            addRoundFourAliensLocation();
        }
        if (Preferences.CURRENT_ROUND + 1 == 5) {
            addRoundFiveAliensLocation();
        }
        canvas.getChildren().addAll(aliens);
    }

    private void addRoundOneAliensLocation() {
        for (int alien = 0, STEP = Preferences.WINDOW_WIDTH / 6; alien < Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND]; alien++, STEP += Preferences.WINDOW_WIDTH / 6) {
            aliens.add(
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

    private void addRoundTwoAliensLocation() {
        for (int alien = 0, STEP = Preferences.WINDOW_WIDTH / 6; alien < Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND] / 2; alien++, STEP += Preferences.WINDOW_WIDTH / 6) {
            aliens.add(
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
            aliens.add(
                    new Sprite(
                            STEP - Preferences.ALIEN_WIDTH,
                            10 + 2 * Preferences.ALIEN_HEIGHT,
                            Preferences.ALIEN_WIDTH,
                            Preferences.ALIEN_HEIGHT,
                            Preferences.SpriteType.ALIEN.toString(),
                            Color.DARKRED)
            );
        }
        aliens.add(
                new Sprite(
                        Preferences.PLAYER_START_X,
                        10 + 3 * Preferences.ALIEN_HEIGHT,
                        Preferences.ALIEN_WIDTH,
                        Preferences.ALIEN_HEIGHT,
                        Preferences.SpriteType.ALIEN.toString(),
                        Color.DARKRED)
        );
    }

    private void addRoundThreeAliensLocation() {
        for (int alien = 0, STEP = Preferences.WINDOW_WIDTH / 6; alien < Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND] / 2 - 1; alien++, STEP += Preferences.WINDOW_WIDTH / 6) {
            aliens.add(
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
            aliens.add(
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
            aliens.add(
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

    private void addRoundFourAliensLocation() {
        for (int alien = 0, STEP = Preferences.WINDOW_WIDTH / 4; alien < Preferences.ALIEN_AMOUNT_PER_LEVEL[Preferences.CURRENT_ROUND]; alien++, STEP += Preferences.WINDOW_WIDTH / 4) {
            aliens.add(
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

    private void addRoundFiveAliensLocation() {
        aliens.add(
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

    private void displayWinWindow() {
        Button replay = ButtonConstructor.construct(
                "Replay",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y
        );
        replay.setOnAction(event -> {
            initSprites();
            stage.setScene(game);
            run();
        });
        winPane.getChildren().add(replay);
        stage.setScene(loss);
    }

    private void displayLossWindow() {
        Button replay = ButtonConstructor.construct(
                "Replay",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y
        );
        replay.setOnAction(event -> {
            initSprites();
            stage.setScene(game);
            run();
        });
        winPane.getChildren().add(replay);
        stage.setScene(win);
    }

    private void displayPauseMenu() {
        Button resume = ButtonConstructor.construct("Resume", Preferences.MAIN_BUTTON_WIDTH, Preferences.MAIN_BUTTON_HEIGHT, Preferences.MAIN_BUTTON_X, Preferences.MAIN_BUTTON_Y);
        resume.setOnAction(event -> stage.setScene(game));
        Button options = ButtonConstructor.construct("Options", Preferences.MAIN_BUTTON_WIDTH, Preferences.MAIN_BUTTON_HEIGHT, Preferences.MAIN_BUTTON_X, Preferences.MAIN_BUTTON_Y + Preferences.MAIN_BUTTON_DIFFERENCE);
        Button toMenu = ButtonConstructor.construct("Back to Menu", Preferences.MAIN_BUTTON_WIDTH, Preferences.MAIN_BUTTON_HEIGHT, Preferences.MAIN_BUTTON_X, Preferences.MAIN_BUTTON_Y + 2 * Preferences.MAIN_BUTTON_DIFFERENCE);
        toMenu.setOnAction(event -> stage.setScene(main));
        menuPane.getChildren().addAll(resume, options, toMenu);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
