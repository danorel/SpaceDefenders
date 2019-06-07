package game;

import game.windows.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
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
        ((MainWindow) scenes.get(2)).display(primaryStage, scenes);
        primaryStage.setScene(scenes.get(2));
        primaryStage.show();
    }

    @Override
    public void init() {
        scenes = new ArrayList<>();

        GameWindow GW = new GameWindow(
                new BorderPane(),
                Preferences.WINDOW_WIDTH,
                Preferences.WINDOW_HEIGHT
        );
        scenes.add(GW);

        LossWindow LW = new LossWindow(
                new StackPane(),
                Preferences.WINDOW_WIDTH,
                Preferences.WINDOW_HEIGHT
        );
        scenes.add(LW);

        MainWindow MaW = new MainWindow(
                new Group(),
                Preferences.WINDOW_WIDTH,
                Preferences.WINDOW_HEIGHT
        );
        scenes.add(MaW);

        ModeWindow MW = new ModeWindow(
                new Group(),
                Preferences.WINDOW_WIDTH,
                Preferences.WINDOW_HEIGHT
        );
        scenes.add(MW);

        OptionsWindow OW = new OptionsWindow(
                new Group(),
                Preferences.WINDOW_WIDTH,
                Preferences.WINDOW_HEIGHT
        );
        scenes.add(OW);

        PauseWindow PW = new PauseWindow(
                new Group(),
                Preferences.WINDOW_WIDTH,
                Preferences.WINDOW_HEIGHT
        );
        scenes.add(PW);

        StatsWindow SW = new StatsWindow(
                new Group(),
                Preferences.WINDOW_WIDTH,
                Preferences.WINDOW_HEIGHT
        );
        scenes.add(SW);

        WinWindow WW = new WinWindow(
                new Group(),
                Preferences.WINDOW_WIDTH,
                Preferences.WINDOW_HEIGHT
        );
        scenes.add(WW);

        GW.initKeyActions(primaryStage, scenes);
        LW.initKeyActions(primaryStage, scenes);
        MaW.initKeyActions(primaryStage, scenes);
        MW.initKeyActions(primaryStage, scenes);
        OW.initKeyActions(primaryStage, scenes);
        PW.initKeyActions(primaryStage, scenes);
        SW.initKeyActions(primaryStage, scenes);
        WW.initKeyActions(primaryStage, scenes);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
