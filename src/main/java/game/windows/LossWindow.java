package game.windows;

import com.sun.javafx.binding.StringFormatter;
import game.Preferences;
import game.tools.ButtonConstructor;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

@SuppressWarnings("ALL")
public class LossWindow extends Scene implements WindowController {
    private StackPane root;
    private Button replay, toMainMenu;
    private Label score, failed, prePress, afterPress;
    private AnimationTimer animationTimer;

    public LossWindow(Parent root, double width, double height) {
        super(root, width, height, Color.rgb(0, 0, 0));
        this.root = (StackPane) root;
    }

    @Override
    public void display(Stage primaryStage, List<Scene> scenes) {

        int level = 1;
        if(Preferences.CURRENT_KILLS >= 10 && Preferences.CURRENT_KILLS < 18){
            level = 2;
        } else if(Preferences.CURRENT_KILLS >= 18){
            level = 3;
        }
        score = new Label();
        score.setText("ROUND SURVIVED: " + String.valueOf(Preferences.CURRENT_ROUND + 1) + "\n" + "ALIENS KILLED: " + Preferences.CURRENT_KILLS + "\n" + "LEVEL ACQUIRED: " + level);
        score.setFont(Preferences.FONT);
        score.setBackground(
                new Background(
                        new BackgroundFill(
                                Color.GOLD,
                                CornerRadii.EMPTY,
                                Insets.EMPTY
                        )
                )
        );

        Preferences.IS_ROUND_WON = false;
        Preferences.IS_GAME_WON = false;
        Preferences.CURRENT_KILLS  = 0;
        Preferences.CURRENT_ROUND  = 0;

        replay = ButtonConstructor.construct(
                "Replay",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y
        );
        replay.setOnAction(event -> {
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
        });

        toMainMenu = ButtonConstructor.construct(
                "Main menu",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y + Preferences.MAIN_BUTTON_DIFFERENCE);
        toMainMenu.setOnAction(event -> {
            scenes.set(
                    2,
                    new MainWindow(
                            new Group(),
                            Preferences.WINDOW_WIDTH,
                            Preferences.WINDOW_HEIGHT
                    )
            );
            ((MainWindow) scenes.get(2)).display(primaryStage, scenes);
            ((MainWindow) scenes.get(2)).initKeyActions(primaryStage, scenes);
            primaryStage.setScene(scenes.get(2));
        });

        BackgroundFill backgroundFill
                = new BackgroundFill(
                Color.rgb(0, 255, 255),
                CornerRadii.EMPTY,
                Insets.EMPTY
        );

        prePress = new Label("Press TAB to start a new game");
        prePress.setFont(new Font("Arial", 24));
        prePress.setBackground(new Background(backgroundFill));

        afterPress = new Label("Press TAB to start a new game");
        afterPress.setFont(new Font("Arial", 24));
        afterPress.setBackground(new Background(backgroundFill));

        root.setAlignment(Pos.CENTER);

        score.setTranslateY(score.getTranslateY() - 1.5 * Preferences.MAIN_BUTTON_DIFFERENCE);

        toMainMenu.setTranslateY(toMainMenu.getTranslateY() + Preferences.MAIN_BUTTON_DIFFERENCE);

        prePress.setTranslateX(0);
        prePress.setTranslateY(Preferences.WINDOW_HEIGHT - 50);

        afterPress.setTranslateX(-Preferences.WINDOW_WIDTH);
        afterPress.setTranslateY(Preferences.WINDOW_HEIGHT - 50);

        root.getChildren().addAll(
                score,
                replay,
                toMainMenu,
                prePress,
                afterPress
        );

        run();
    }

    @Override
    public void initKeyActions(Stage primaryStage, List<Scene> scenes) {
        setOnKeyPressed(event -> {
            switch (event.getCode()){
                case TAB:
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
                    break;
                case ESCAPE:
                    scenes.set(
                            2,
                            new MainWindow(
                                    new Group(),
                                    Preferences.WINDOW_WIDTH,
                                    Preferences.WINDOW_HEIGHT
                            )
                    );
                    ((MainWindow) scenes.get(2)).display(primaryStage, scenes);
                    ((MainWindow) scenes.get(2)).initKeyActions(primaryStage, scenes);
                    primaryStage.setScene(scenes.get(2));
                    break;
                default:
                    break;
            }
        });
    }

    public void run(){
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
    }

    private void update() {
//        if(prePress.getTranslateX() >= Preferences.WINDOW_WIDTH + 200){
//            prePress.setTranslateX(-Preferences.WINDOW_WIDTH);
//        }
//        if(afterPress.getTranslateX() >= Preferences.WINDOW_WIDTH + 200){
//            afterPress.setTranslateX(-Preferences.WINDOW_WIDTH);
//        }
        prePress.setTranslateX(prePress.getTranslateX() + 1);
        afterPress.setTranslateX(afterPress.getTranslateX() + 1);
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
