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
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;

public class WinWindow extends Scene implements WindowController{
    private Group root;
    private Button replay, toMainMenu;
    private Label won;

    public WinWindow(Parent root, double width, double height) {
        super(root, width, height, Color.rgb(255, 255, 225));
        this.root = (Group) root;
    }

    @Override
    public void display(Stage primaryStage, List<Scene> scenes) {

        replay = ButtonConstructor.construct(
                "Replay",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y
        );
        replay.setOnAction(event -> {
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

        toMainMenu = ButtonConstructor.construct(
                "Main Menu",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y + Preferences.MAIN_BUTTON_DIFFERENCE);
        toMainMenu.setOnAction(event -> {
            ((MainWindow) scenes.get(2)).display(primaryStage, scenes);
            primaryStage.setScene(scenes.get(2));
        });

        Image image = new Image(
                "file:resources/messages/game-won.png"
        );
        won = new Label(
                "", new ImageView(image)
        );

        root.getChildren().addAll(
                won,
                replay,
                toMainMenu
        );
    }

    @Override
    public void initKeyActions(Stage primaryStage, List<Scene> scenes) {
        setOnKeyPressed(event -> {
            switch (event.getCode()){
                case ENTER:
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
                    break;
                case ESCAPE:
                    primaryStage.setScene(scenes.get(2));
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
