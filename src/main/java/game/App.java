package game;

import game.windows.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private Stage primaryStage;
    private List<Scene> scenes;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(Preferences.TITLE);
        init();
        ((MainWindow) scenes.get(2))
                .display(
                        primaryStage,
                        scenes
                );
        primaryStage.setScene(scenes.get(2));
        primaryStage.show();
    }

    @Override
    public void init() {
        scenes = new ArrayList<>();

        scenes.add(
                new GameWindow(
                        new BorderPane(),
                        Preferences.WINDOW_WIDTH,
                        Preferences.WINDOW_HEIGHT
                )
        );

        scenes.add(
                new LossWindow(
                        new Group(),
                        Preferences.WINDOW_WIDTH,
                        Preferences.WINDOW_HEIGHT
                )
        );

        scenes.add(
                new MainWindow(
                        new Group(),
                        Preferences.WINDOW_WIDTH,
                        Preferences.WINDOW_HEIGHT
                )
        );

        scenes.add(
                new ModeWindow(
                        new Group(),
                        Preferences.WINDOW_WIDTH,
                        Preferences.WINDOW_HEIGHT
                )
        );

        scenes.add(
                new OptionsWindow(
                        new Group(),
                        Preferences.WINDOW_WIDTH,
                        Preferences.WINDOW_HEIGHT
                )
        );

        scenes.add(
                new PauseWindow(
                        new Group(),
                        Preferences.WINDOW_WIDTH,
                        Preferences.WINDOW_HEIGHT
                )
        );

        scenes.add(
                new StatsWindow(
                        new Group(),
                        Preferences.WINDOW_WIDTH,
                        Preferences.WINDOW_HEIGHT
                )
        );

        scenes.add(
                new WinWindow(
                        new Group(),
                        Preferences.WINDOW_WIDTH,
                        Preferences.WINDOW_HEIGHT
                )
        );
    }

    public static void main(String[] args) {
        launch(args);
    }
}
