package game;

import game.tools.ButtonConstructor;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.naming.directory.AttributeInUseException;
import java.util.ArrayList;

public class App extends Application {

    private Stage stage;
    private Scene game, menu, main;
    private Group menuGroup, mainGroup;
    private Pane root;

    private Sprite player;
    private ArrayList<Sprite> aliens;
    private ArrayList<Sprite> aliensRockets;
    private ArrayList<Sprite> playerRockets;

    @Override
    public void start(Stage stage){
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
        mainGroup = new Group();
        main = new Scene(
                mainGroup,
                Preferences.WINDOW_WIDTH,
                Preferences.WINDOW_HEIGHT
        );

        menuGroup = new Group();
        menu = new Scene(
                menuGroup,
                Preferences.WINDOW_WIDTH,
                Preferences.WINDOW_HEIGHT
        );

        root = new Pane();
        root.setPrefSize(
                Preferences.WINDOW_WIDTH,
                Preferences.WINDOW_HEIGHT
        );

        game = new Scene(
               root
        );
    }

    private void initSprites(){
        player = new Sprite(
                Preferences.PLAYER_START_X,
                Preferences.PLAYER_START_Y,
                Preferences.PLAYER_WIDTH,
                Preferences.PLAYER_HEIGHT,
                Preferences.SpriteType.PLAYER.toString(),
                Color.GREEN
        );
        root.getChildren().add(player);
        playerRockets = new ArrayList<>();
        aliens        = new ArrayList<>();
        aliensRockets = new ArrayList<>();
    }

    private void initKeyEvents(){
        game.setOnKeyPressed(event -> {
            switch(event.getCode()){
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
                        root
                                .getChildren()
                                .add(
                                      shot
                                );
                    } catch (AttributeInUseException exception) {
                        exception.printStackTrace();
                    }
                    break;
                case ESCAPE:
                    stage.setScene(menu);
                    displayPauseMenu();
                    break;
            }
        });

        menu.setOnKeyPressed(event -> {
            switch(event.getCode()){
                case ESCAPE:
                    stage.setScene(game);
                    break;
            }
        });
    }

    private void displayMainMenu(){
        Button run = ButtonConstructor.construct("Run", Preferences.MAIN_BUTTON_WIDTH, Preferences.MAIN_BUTTON_HEIGHT, Preferences.MAIN_BUTTON_X, Preferences.MAIN_BUTTON_Y);
        run.setOnAction(event -> {
            stage.setScene(game);
            run();
        });
        Button options = ButtonConstructor.construct("Options", Preferences.MAIN_BUTTON_WIDTH, Preferences.MAIN_BUTTON_HEIGHT, Preferences.MAIN_BUTTON_X, Preferences.MAIN_BUTTON_Y + Preferences.MAIN_BUTTON_DIFFERENCE);
        Button exit = ButtonConstructor.construct("Exit", Preferences.MAIN_BUTTON_WIDTH, Preferences.MAIN_BUTTON_HEIGHT, Preferences.MAIN_BUTTON_X, Preferences.MAIN_BUTTON_Y + 2 * Preferences.MAIN_BUTTON_DIFFERENCE);
        exit.setOnAction(event -> System.exit(0));
        mainGroup.getChildren().addAll(run, options, exit);
    }

    private void run(){
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();

        startNextLevel();
//        while(!Preferences.isGameWon){
//            /*
//                Clearing up the array lists with game data before starting each round
//             */
//            aliens        = new ArrayList<>();
//            aliensRockets = new ArrayList<>();
//            playerRockets = new ArrayList<>();
//            /*
//                Defining the position of the enemies on the board and adding them there
//             */
//
//            /*
//                Starting the game loop
//             */
//            while(!Preferences.isRoundWon){
//                update();
//                if(aliens.size() == 0){
//                    Preferences.isRoundWon = true;
//                }
//                if(!player.isAlive()){
//                    break;
//                }
//            }
//            if(Preferences.isRoundWon && Preferences.CURRENT_ROUND == Preferences.MAX_ROUND){
//                Preferences.isGameWon = true;
//            }
//        }
    }

    private void update(){
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
            if(chance > 97){
                Sprite shot = aliens.get(shooting).shoot();
                aliensRockets.add(shot);
                root.getChildren().add(shot);
            }
        } catch (AttributeInUseException exception) {
            exception.printStackTrace();
        }
        aliensRockets.forEach(sprite -> {
            try {
                sprite.moveDown();
                if(sprite.intersects(player)){
                    player.die();
                    sprite.die();
                    aliensRockets.remove(sprite);
                }
            } catch (AttributeInUseException exception) {
                exception.printStackTrace();
            }
        });
        playerRockets.forEach(sprite -> {
            try {
                sprite.moveUp();
                aliens.forEach(alien -> {
                    if(sprite.intersects(alien)){
                        alien.die();
                        aliens.remove(alien);
                        sprite.die();
                        playerRockets.remove(sprite);
                    }
                });
                aliensRockets.forEach(rocket -> {
                    if(sprite.intersects(rocket)){
                        rocket.die();
                        aliensRockets.remove(rocket);
                        sprite.die();
                        playerRockets.remove(sprite);
                    }
                });
            } catch (AttributeInUseException exception) {
                exception.printStackTrace();
            }
        });
        root.getChildren().removeIf(n -> {
            Sprite sprite = (Sprite) n;
            return !sprite.isAlive();
        });
    }

    private void startNextLevel(){
        if(Preferences.CURRENT_ROUND + 1 == 1){
            aliens.add(
                    new Sprite(
                        Preferences.PLAYER_START_X,
                        10 + Preferences.ALIEN_HEIGHT,
                        Preferences.ALIEN_WIDTH,
                        Preferences.ALIEN_HEIGHT,
                        Preferences.SpriteType.ALIEN.toString(),
                        Color.DARKRED)
            );
            root.getChildren().addAll(aliens);
        }
        if(Preferences.CURRENT_ROUND + 1 == 2){

        }
        if(Preferences.CURRENT_ROUND + 1 == 3){

        }
        if(Preferences.CURRENT_ROUND + 1 == 4){

        }
        if(Preferences.CURRENT_ROUND + 1 == 5){

        }
    }

//    private void display

    private void displayPauseMenu(){
        Button resume = ButtonConstructor.construct("Resume", Preferences.MAIN_BUTTON_WIDTH, Preferences.MAIN_BUTTON_HEIGHT, Preferences.MAIN_BUTTON_X, Preferences.MAIN_BUTTON_Y);
        resume.setOnAction(event -> stage.setScene(game));
        Button options = ButtonConstructor.construct("Options", Preferences.MAIN_BUTTON_WIDTH, Preferences.MAIN_BUTTON_HEIGHT, Preferences.MAIN_BUTTON_X, Preferences.MAIN_BUTTON_Y + Preferences.MAIN_BUTTON_DIFFERENCE);
        Button toMenu = ButtonConstructor.construct("Back to Menu", Preferences.MAIN_BUTTON_WIDTH, Preferences.MAIN_BUTTON_HEIGHT, Preferences.MAIN_BUTTON_X, Preferences.MAIN_BUTTON_Y + 2 * Preferences.MAIN_BUTTON_DIFFERENCE);
        toMenu.setOnAction(event -> stage.setScene(main));
        menuGroup.getChildren().addAll(resume, options, toMenu);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
