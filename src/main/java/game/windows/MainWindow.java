package game.windows;

import com.sun.javafx.binding.StringFormatter;
import game.Preferences;
import game.tools.ButtonConstructor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.List;

public class MainWindow extends Scene implements WindowController{
    private Group root;
    private Button gotoMode, options, exit;

    public MainWindow(Parent root, double width, double height) {
        super(root, width, height);
        this.root = (Group) root;
    }

    @Override
    public void display(Stage primaryStage, List<Scene> scenes){
        gotoMode = ButtonConstructor.construct(
                "Choose mode",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y
        );
        gotoMode.setOnAction(event -> {
            ((ModeWindow) scenes.get(3)).display(primaryStage, scenes);
            primaryStage.setScene(scenes.get(3));
        });

        options = ButtonConstructor.construct(
                "Options",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y + Preferences.MAIN_BUTTON_DIFFERENCE);
        options.setOnAction(event -> primaryStage.setScene(scenes.get(4)));

        exit = ButtonConstructor.construct(
                "Exit",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y + 2 * Preferences.MAIN_BUTTON_DIFFERENCE
        );
        exit.setOnAction(event -> System.exit(0));

        root.getChildren().addAll(
                gotoMode,
                options,
                exit
        );
    }

    @Override
    public void initKeyActions(Stage primaryStage, List<Scene> scenes) {
        setOnKeyPressed(event -> {
            switch (event.getCode()){
                case ENTER:
                    primaryStage.setScene(scenes.get(3));
                    break;
                case ESCAPE:
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Quiting from Space Defenders");
                    alert.setHeaderText("Do you really want to exit?");
                    alert.setContentText("Type: Yes or No");
                    alert.showAndWait();
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
