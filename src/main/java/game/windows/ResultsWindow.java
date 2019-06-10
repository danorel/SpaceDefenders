package game.windows;

import com.sun.javafx.binding.StringFormatter;
import game.Preferences;
import game.tools.ButtonConstructor;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

public class ResultsWindow extends Scene implements WindowController {
    private Group root;
    private Button playOn, back;
    private Label score;

    public ResultsWindow(Parent root, double width, double height) {
        super(root, width, height);
        this.root   = (Group) root;
    }

    @Override
    public void display(Stage primaryStage, List<Scene> scenes){
        playOn = ButtonConstructor.construct(
                "Continue",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y + Preferences.MAIN_BUTTON_DIFFERENCE);
        playOn.setOnAction(event -> {
            Preferences.CONTINUE = true;
            Preferences.CURRENT_KILLS = 0;
            Preferences.CURRENT_ROUND = 0;
            Preferences.IS_VERSUS_HUMAN = true;
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
                "Back",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y + 2 * Preferences.MAIN_BUTTON_DIFFERENCE);
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

        int level = 1;
        if(Preferences.CURRENT_KILLS >= 10 && Preferences.CURRENT_KILLS < 18){
            level = 2;
        } else if(Preferences.CURRENT_KILLS >= 18){
            level = 3;
        }
        score = new Label();
        score.setText("FIRST PLAYER STATS\nROUND SURVIVED: " + String.valueOf(Preferences.CURRENT_ROUND + 1) + "\n" + "ALIENS KILLED: " + Preferences.CURRENT_KILLS + "\n" + "LEVEL ACQUIRED: " + level);
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
        score.setTranslateX(Preferences.WINDOW_WIDTH / 2 - 170);
        score.setTranslateY(40);

        root.getChildren().addAll(
                score,
                playOn,
                back
        );
    }

    @Override
    public void initKeyActions(Stage primaryStage, List<Scene> scenes) {
        setOnKeyPressed(event -> {
            switch (event.getCode()){
                case ENTER:
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
                default:
                    break;
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
