package game.windows;

import com.sun.javafx.binding.StringFormatter;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.List;

public class OptionsWindow extends Scene implements WindowController{
    private Group root;
    private Button back;

    public OptionsWindow(Parent root, double width, double height) {
        super(root, width, height);
    }

    @Override
    public void display(Stage primaryStage, List<Scene> scenes) {

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
