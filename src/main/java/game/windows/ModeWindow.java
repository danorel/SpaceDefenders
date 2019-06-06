package game.windows;

import com.sun.javafx.binding.StringFormatter;
import game.Preferences;
import game.tools.ButtonConstructor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.List;

public class ModeWindow extends Scene implements WindowController{
    private Group root;
    private Button start;

    public ModeWindow(Parent root, double width, double height) {
        super(root, width, height);
        this.root = (Group) root;
    }

    @Override
    public void display(Stage primaryStage, List<Scene> scenes) {
        start = ButtonConstructor.construct(
                "Start",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y
        );
        start.setOnAction(event -> {
            GameWindow GW = new GameWindow(
                    new BorderPane(),
                    Preferences.WINDOW_WIDTH,
                    Preferences.WINDOW_HEIGHT
            );
            scenes.set(0, GW);
            GW.display(
                    primaryStage,
                    scenes
            );
            primaryStage.setScene(GW);
        });

        root.getChildren().addAll(
            start
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
