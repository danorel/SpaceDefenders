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
import javafx.stage.Stage;

import java.util.List;

public class WinWindow extends Scene implements WindowController{
    private Group root;
    private Button replay, back;
    private Label won;
    private Label passedMessaged, commingMessage;

    public WinWindow(Parent root, double width, double height) {
        super(root, width, height, Preferences.WIN_WINDOW_COLOR);
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
            ((GameWindow) scenes.get(0)).initKeyActions(primaryStage, scenes);
            primaryStage.setScene(scenes.get(0));
        });

        back = ButtonConstructor.construct(
                "Main Menu",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y + Preferences.MAIN_BUTTON_DIFFERENCE);
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

        BackgroundFill fill
                = new BackgroundFill(
                Color.rgb(255, 255, 225),
                CornerRadii.EMPTY,
                Insets.EMPTY
        );

        passedMessaged = new Label("Press TAB to start a new game");
        passedMessaged.setTextFill(Color.BLACK);
        passedMessaged.setFont(Preferences.FONT);
        passedMessaged.setBackground(new Background(fill));
        passedMessaged.setTranslateX(0);
        passedMessaged.setTranslateY(Preferences.WINDOW_HEIGHT - 50);

        commingMessage = new Label("Press TAB to start a new game");
        commingMessage.setTextFill(Color.BLACK);
        commingMessage.setFont(Preferences.FONT);
        commingMessage.setBackground(new Background(fill));
        commingMessage.setTranslateX(-Preferences.WINDOW_WIDTH);
        commingMessage.setTranslateY(Preferences.WINDOW_HEIGHT - 50);

        Image image = new Image(
                "file:resources/messages/game-won.png"
        );
        won = new Label(
                "", new ImageView(image)
        );

        root.getChildren().addAll(
                won,
                replay,
                back,
                passedMessaged,
                commingMessage
        );

        Preferences.IS_ROUND_WON = false;
        Preferences.IS_GAME_WON = false;
        Preferences.CURRENT_KILLS  = 0;
        Preferences.CURRENT_ROUND  = 0;

        run();
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
                    ((MainWindow) scenes.get(0)).initKeyActions(primaryStage, scenes);
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
                case ENTER:
                    break;
                default:
                    break;
            }
        });
    }

    private void run(){
        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
    }

    private void update() {
        if(passedMessaged.getTranslateX() >= Preferences.WINDOW_WIDTH + 200){
            passedMessaged.setTranslateX(-Preferences.WINDOW_WIDTH);
        }
        if(commingMessage.getTranslateX() >= Preferences.WINDOW_WIDTH + 200){
            commingMessage.setTranslateX(-Preferences.WINDOW_WIDTH);
        }
        passedMessaged.setTranslateX(passedMessaged.getTranslateX() + 1);
        commingMessage.setTranslateX(commingMessage.getTranslateX() + 1);
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
