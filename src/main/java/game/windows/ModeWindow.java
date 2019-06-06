package game.windows;

import com.sun.javafx.binding.StringFormatter;
import game.Preferences;
import game.tools.ButtonConstructor;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class ModeWindow extends Scene implements WindowController{
    private Group root;
    private Button play, skin;
    private FileChooser skinChooser;

    public ModeWindow(Parent root, double width, double height) {
        super(root, width, height, Color.rgb(50,50,100));
        this.root = (Group) root;
    }

    @Override
    public void display(Stage primaryStage, List<Scene> scenes) {
        play = ButtonConstructor.construct(
                "Play",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y
        );
        play.setOnAction(event -> {
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

        skin =  ButtonConstructor.construct(
                "Choose skin",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y + Preferences.MAIN_BUTTON_DIFFERENCE
        );
        skin.setOnAction(event -> {
            skinChooser = new FileChooser();
            skinChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PNG Files", "*.png")
                    ,new FileChooser.ExtensionFilter("JPEG Files", "*.jpg")
            );
            File file = skinChooser.showOpenDialog(primaryStage);
            Preferences.IMAGE = new Image(file.getAbsolutePath());
        });

        root.getChildren().addAll(
                play,
                skin
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
