package game.windows;

import com.sun.javafx.binding.StringFormatter;
import game.Preferences;
import game.tools.ButtonConstructor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.List;

public class StatsWindow extends Scene implements WindowController {
    private Group root;
    private Button back;

    public StatsWindow(Parent root, double width, double height) {
        super(root, width, height);
        this.root   = (Group) root;
    }

    @Override
    public void display(Stage primaryStage, List<Scene> scenes){
        back = ButtonConstructor.construct(
                "Back",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y + 3 * Preferences.MAIN_BUTTON_DIFFERENCE);
        back.setOnAction(event -> primaryStage.setScene(scenes.get(0)));

        root.getChildren().addAll(
                back
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
