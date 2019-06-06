package game.tools;

import javafx.scene.control.Button;

public class ButtonConstructor {
    public static Button construct(String title, int width, int height, int x, int y){
        Button button = new Button(title);
        button.setDefaultButton(true);
        button.setPrefSize(width, height);
        button.setStyle("-fx-font: 26 arial; -fx-base: #b6a7c9;");
        button.setLayoutX(x);
        button.setLayoutY(y);
        return button;
    }
}
