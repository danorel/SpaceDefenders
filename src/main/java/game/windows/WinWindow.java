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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class WinWindow extends Scene implements WindowController{
    private Group root;
    private Button replay, toMainMenu;
    private Label won;
    private Label prePress, afterPress;

    private AnimationTimer animationTimer;

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
            scenes.set(
                    0,
                    new GameWindow(
                            new BorderPane(),
                            Preferences.WINDOW_WIDTH,
                            Preferences.WINDOW_HEIGHT
                    ));
            ((GameWindow) scenes.get(0)).display(primaryStage, scenes);
            primaryStage.setScene(scenes.get(0));
        });

        toMainMenu = ButtonConstructor.construct(
                "Main Menu",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y + Preferences.MAIN_BUTTON_DIFFERENCE);
        toMainMenu.setOnAction(event -> {
            scenes.set(
                    2,
                    new MainWindow(
                            new Group(),
                            Preferences.WINDOW_WIDTH,
                            Preferences.WINDOW_HEIGHT
                    )
            );
            ((MainWindow) scenes.get(2)).display(primaryStage, scenes);
            primaryStage.setScene(scenes.get(2));
        });

        BackgroundFill backgroundFill
                = new BackgroundFill(
                Color.rgb(255, 255, 225),
                CornerRadii.EMPTY,
                Insets.EMPTY
        );

        prePress = new Label("Press TAB to start a new game");
        prePress.setFont(new Font("Arial", 24));
        prePress.setBackground(new Background(backgroundFill));
        prePress.setTranslateX(0);
        prePress.setTranslateY(Preferences.WINDOW_HEIGHT - 50);

        afterPress = new Label("Press TAB to start a new game");
        afterPress.setFont(new Font("Arial", 24));
        afterPress.setBackground(new Background(backgroundFill));
        afterPress.setTranslateX(-Preferences.WINDOW_WIDTH);
        afterPress.setTranslateY(Preferences.WINDOW_HEIGHT - 50);

        run();

        Image image = new Image(
                "file:resources/messages/game-won.png"
        );
        won = new Label(
                "", new ImageView(image)
        );

        root.getChildren().addAll(
                won,
                replay,
                toMainMenu,
                prePress
        );
    }

    @Override
    public void initKeyActions(Stage primaryStage, List<Scene> scenes) {
        setOnKeyPressed(event -> {
            switch (event.getCode()){
                case TAB:
                    scenes.set(
                            0,
                            new MainWindow(
                                    new Group(),
                                    Preferences.WINDOW_WIDTH,
                                    Preferences.WINDOW_HEIGHT
                            )
                    );
                    ((MainWindow) scenes.get(0)).display(primaryStage, scenes);
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
                    ((WinWindow) scenes.get(2)).display(primaryStage, scenes);
                    primaryStage.setScene(scenes.get(2));
                    break;
                default:
                    break;
            }
        });
    }

    private void run(){
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
    }

    private void update() {
        if(prePress.getTranslateX() >= Preferences.WINDOW_WIDTH + 200){
            prePress.setTranslateX(-Preferences.WINDOW_WIDTH);
        }
        if(afterPress.getTranslateX() >= Preferences.WINDOW_WIDTH + 200){
            afterPress.setTranslateX(-Preferences.WINDOW_WIDTH);
        }
        prePress.setTranslateX(prePress.getTranslateX() + 1);
        afterPress.setTranslateX(afterPress.getTranslateX() + 1);
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
