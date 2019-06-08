package game.windows;

import com.sun.javafx.binding.StringFormatter;
import game.Preferences;
import game.tools.ButtonConstructor;
import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@SuppressWarnings("deprecation")
public class MainWindow extends Scene implements WindowController{
    private AnimationTimer timer;
    private Group root;
    private Button gotoMode, options, exit;

    private Label title;
    private double titleWidth = 200;
    private double titleSpeed = 1.0;

    private Label madeByBefore;
    private Label madeByAfter;
    private double madeBySpeed = 1.0;

    public MainWindow(Parent root, double width, double height) {
        super(root, width, height, Preferences.MAIN_WINDOW_COLOR);
        this.root = (Group) root;
    }

    @Override
    public void display(Stage primaryStage, List<Scene> scenes){

        try {
            this.getStylesheets().addAll(
                    new URL("file:css/styles.css")
                            .toExternalForm()
            );
        } catch (MalformedURLException exception) {
            exception.printStackTrace();
        }

        BackgroundFill fill
                = new BackgroundFill(
                Color.rgb(50,50,100),
                CornerRadii.EMPTY,
                Insets.EMPTY
        );

        title = new Label(
                "Space Defenders"
        );
        title.setFont(Preferences.TITLE_FONT);
        title.setTextFill(Color.WHITE);
        title.setBackground(new Background(fill));
        title.setLayoutX((Preferences.WINDOW_WIDTH) / 2 - titleWidth);
        title.setLayoutY(50);

        gotoMode = ButtonConstructor.construct(
                "Choose mode",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y
        );
        gotoMode.setOnAction(event -> {
            scenes.set(
                    3,
                    new ModeWindow(
                            new Group(),
                            Preferences.WINDOW_WIDTH,
                            Preferences.WINDOW_HEIGHT
                    )
            );
            ((ModeWindow) scenes.get(3)).display(primaryStage, scenes);
            ((ModeWindow) scenes.get(3)).initKeyActions(primaryStage, scenes);
            primaryStage.setScene(scenes.get(3));
        });

        options = ButtonConstructor.construct(
                "Options",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y + Preferences.MAIN_BUTTON_DIFFERENCE);
        options.setOnAction(event -> {
            scenes.set(
                    4,
                    new OptionsWindow(
                            new Group(),
                            Preferences.WINDOW_WIDTH,
                            Preferences.WINDOW_HEIGHT
                    )
            );
            ((OptionsWindow) scenes.get(4)).display(primaryStage, scenes);
            ((OptionsWindow) scenes.get(4)).initKeyActions(primaryStage, scenes);
            primaryStage.setScene(scenes.get(4));
        });

        exit = ButtonConstructor.construct(
                "Exit",
                Preferences.MAIN_BUTTON_WIDTH,
                Preferences.MAIN_BUTTON_HEIGHT,
                Preferences.MAIN_BUTTON_X,
                Preferences.MAIN_BUTTON_Y + 2 * Preferences.MAIN_BUTTON_DIFFERENCE
        );
        exit.setOnAction(event -> System.exit(0));

        madeByBefore = new Label(
            "Made by Dan Orel & Olia Perch"
        );
        madeByBefore.setFont(Preferences.FONT);
        madeByBefore.setTextFill(Color.WHITE);
        madeByBefore.setBackground(new Background(fill));
        madeByBefore.setLayoutX(0);
        madeByBefore.setLayoutY(Preferences.WINDOW_HEIGHT - 50);


        madeByAfter = new Label(
                "Made by Dan Orel & Olia Perch"
        );
        madeByAfter.setFont(Preferences.FONT);
        madeByAfter.setTextFill(Color.WHITE);
        madeByAfter.setBackground(new Background(fill));
        madeByAfter.setLayoutX(-Preferences.WINDOW_WIDTH);
        madeByAfter.setLayoutY(Preferences.WINDOW_HEIGHT - 50);

        root.getChildren().addAll(
                title,
                gotoMode,
                options,
                exit,
                madeByBefore,
                madeByAfter
        );

        run();
    }

    @Override
    public void initKeyActions(Stage primaryStage, List<Scene> scenes) {
        setOnKeyPressed(event -> {
            switch (event.getCode()){
                case ENTER:
                    scenes.set(
                            3,
                            new ModeWindow(
                                    new Group(),
                                    Preferences.WINDOW_WIDTH,
                                    Preferences.WINDOW_HEIGHT
                            )
                    );
                    ((ModeWindow) scenes.get(3)).display(primaryStage, scenes);
                    ((ModeWindow) scenes.get(3)).initKeyActions(primaryStage, scenes);
                    primaryStage.setScene(scenes.get(3));
                    break;
                case ESCAPE:
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Quiting from Space Defenders");
                    alert.setHeaderText("Do you really want to exit?");
                    alert.setContentText("Type: Yes or No");
                    alert.showAndWait();
                    break;
                default:
                    break;
            }
        });
    }

    private void run() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
    }

    private void update(){
        if(title.getLayoutX() == (Preferences.WINDOW_WIDTH - titleWidth - 320) || title.getLayoutX() == 0){
            titleSpeed = -titleSpeed;
        }
        title.setLayoutX(title.getLayoutX() + titleSpeed);

        if(madeByBefore.getLayoutX() == Preferences.WINDOW_WIDTH){
            madeByBefore.setLayoutX(-Preferences.WINDOW_WIDTH);
        }
        if(madeByAfter.getLayoutX() == Preferences.WINDOW_WIDTH){
            madeByAfter.setLayoutX(-Preferences.WINDOW_WIDTH);
        }
        madeByBefore.setLayoutX(madeByBefore.getLayoutX() + madeBySpeed);
        madeByAfter.setLayoutX(madeByAfter.getLayoutX() + madeBySpeed);
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
