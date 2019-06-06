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

public class PauseWindow extends Scene implements WindowController{
    private Group root;
    private Button resume, options, toMainMenu;

    public PauseWindow(Parent root, double width, double height) {
        super(root, width, height);
        this.root   = (Group) root;
    }

    @Override
    public void display(Stage primaryStage, List<Scene> scenes) {
        resume = ButtonConstructor.construct(
                "Resume",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y);
        resume.setOnAction(event -> primaryStage.setScene(scenes.get(0)));

        options = ButtonConstructor.construct(
                "Options",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y + Preferences.MAIN_BUTTON_DIFFERENCE);
        options.setOnAction(event -> primaryStage.setScene(scenes.get(4)));

        toMainMenu = ButtonConstructor.construct(
                "Main Menu",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y + 2 * Preferences.MAIN_BUTTON_DIFFERENCE);
        toMainMenu.setOnAction(event -> primaryStage.setScene(scenes.get(2)));
        root.getChildren().addAll(
                resume,
                options,
                toMainMenu
        );
    }

    @Override
    public void initKeyActions(Stage primaryStage, List<Scene> scenes) {
        setOnKeyPressed(event -> {
            switch (event.getCode()){
                case ESCAPE:
                    primaryStage.setScene(scenes.get(0));
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
