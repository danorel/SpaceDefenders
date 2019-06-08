package game.windows;

import com.sun.javafx.binding.StringFormatter;
import game.Preferences;
import game.tools.ButtonConstructor;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

@SuppressWarnings("ALL")
public class LossWindow extends Scene implements WindowController {
    private StackPane root;
    private Button replay, back;
    private Label score, failed, passsedMessage, commingMessage;
    private AnimationTimer animationTimer;

    public LossWindow(Parent root, double width, double height) {
        super(root, width, height, Preferences.MODE_WINDOW_COLOR);
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

        back = ButtonConstructor.construct(
                "Main menu",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y + Preferences.MAIN_BUTTON_DIFFERENCE);
        back.setOnAction(event -> {
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

        BackgroundFill fill
                = new BackgroundFill(
                    Color.rgb(0, 0, 0),
                    CornerRadii.EMPTY,
                    Insets.EMPTY
        );

        passsedMessage = new Label("Press TAB to start a new game");
        passsedMessage.setTextFill(Color.BLACK);
        passsedMessage.setFont(Preferences.FONT);
        passsedMessage.setBackground(new Background(fill));

        commingMessage = new Label("Press TAB to start a new game");
        commingMessage.setTextFill(Color.BLACK);
        commingMessage.setFont(Preferences.FONT);
        commingMessage.setBackground(new Background(fill));

        score.setTranslateY(score.getTranslateY() - 1.5 * Preferences.MAIN_BUTTON_DIFFERENCE);

        back.setTranslateY(back.getTranslateY() + Preferences.MAIN_BUTTON_DIFFERENCE);

        passsedMessage.setTranslateX(0);
        passsedMessage.setTranslateY(Preferences.WINDOW_HEIGHT - 50);

        commingMessage.setTranslateX(-Preferences.WINDOW_WIDTH);
        commingMessage.setTranslateY(Preferences.WINDOW_HEIGHT - 50);

        root.getChildren().addAll(
                score,
                replay,
                back,
                passsedMessage,
                commingMessage
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
                case ENTER:
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
//        if(passsedMessage.getTranslateX() >= Preferences.WINDOW_WIDTH + 200){
//            passsedMessage.setTranslateX(-Preferences.WINDOW_WIDTH);
//        }
//        if(commingMessage.getTranslateX() >= Preferences.WINDOW_WIDTH + 200){
//            commingMessage.setTranslateX(-Preferences.WINDOW_WIDTH);
//        }
        passsedMessage.setTranslateX(passsedMessage.getTranslateX() + 1);
        commingMessage.setTranslateX(commingMessage.getTranslateX() + 1);
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
