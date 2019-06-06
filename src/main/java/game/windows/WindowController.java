package game.windows;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public interface WindowController {
    void display(Stage primaryStage, List<Scene> scenes);
    void initKeyActions(Stage primaryStage, List<Scene> scenes);
}
