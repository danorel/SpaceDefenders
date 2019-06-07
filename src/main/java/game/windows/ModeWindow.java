package game.windows;

import com.sun.javafx.binding.StringFormatter;
import game.Preferences;
import game.tools.ButtonConstructor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

public class ModeWindow extends Scene implements WindowController{
    private Group root;
    private Button vsComputer, vsHuman, back;

    public ModeWindow(Parent root, double width, double height) {
        super(root, width, height, Color.rgb(50,50,100));
        this.root = (Group) root;
    }

    @Override
    public void display(Stage primaryStage, List<Scene> scenes) {
        vsComputer = ButtonConstructor.construct(
                "Versus Computer",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y
        );
        vsComputer.setOnAction(event -> {
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

        vsHuman = ButtonConstructor.construct(
                "Versus Human",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y + Preferences.MAIN_BUTTON_DIFFERENCE
        );
        vsHuman.setOnAction(event -> {

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

        root.getChildren().addAll(
                vsComputer,
                vsHuman,
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
