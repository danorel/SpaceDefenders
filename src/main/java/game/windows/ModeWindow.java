package game.windows;

import com.sun.javafx.binding.StringFormatter;
import game.Preferences;
import game.tools.ButtonConstructor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

public class ModeWindow extends Scene implements WindowController{
    private Group root;
    private Button vsComputer, vsHuman;

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
                Preferences.MAIN_BUTTON_Y + Preferences.MAIN_BUTTON_DIFFERENCE
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
            primaryStage.setScene(scenes.get(0));
        });

        vsHuman = ButtonConstructor.construct(
                "Versus Human",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y + 2 * Preferences.MAIN_BUTTON_DIFFERENCE
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
            primaryStage.setScene(scenes.get(0));
        });

        root.getChildren().addAll(
                vsComputer,
                vsHuman
        );
    }

    @Override
    public void initKeyActions(Stage primaryStage, List<Scene> scenes) {

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
