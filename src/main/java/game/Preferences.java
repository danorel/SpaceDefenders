package game;

import javafx.scene.image.Image;
import javafx.scene.text.Font;

public class Preferences {
    /*  VS COMPUTER OPTIONS  */
    /*
        Main window parameters
    */
    static final String TITLE = "Space Defenders";
    public static final int WINDOW_WIDTH = 640;
    public static final int WINDOW_HEIGHT = 480;

    /*
        Sprite options
     */
    public enum SpriteType {
        PLAYER, ALIEN, PLAYER_ROCKET, ALIEN_ROCKET, METEOR
    }

    /*
        Aliens options
     */
    public static final double ALIEN_WIDTH = 25;
    public static final double ALIEN_HEIGHT = 25;
    public static double ALIEN_VELOCITY = 2;
    public static final int[] ALIEN_AMOUNT_PER_LEVEL = {5, 10, 8, 3, 1};
    public static final int ALIEN_START_X = (int) (WINDOW_WIDTH / 2 - ALIEN_WIDTH / 2);
    /*
        Player options
     */
    public static final double PLAYER_WIDTH = 35;
    public static final double PLAYER_HEIGHT = 35;
    public static double PLAYER_HORIZONTAL_VELOCITY = 2;
    public static double PLAYER_VERTICAL_VELOCITY = 0;
    /*
        Rocket options
     */
    public static final int ROCKET_WIDTH = 5;
    public static final int ROCKET_HEIGHT = 20;
    public static double ROCKET_VELOCITY = 5;
    public static int ROCKET_LAUNCH_CHANCE = 5;
    public static final int ROCKET_LAUNCH_CHANCE_RANGE = 100;
    /*
        Meteor parameters
     */
    public static final double METEOR_WIDTH = 50;
    public static final int METEOR_HEIGHT = 50;
    public static double METEOR_VELOCITY = 5;
    /*
       The chance of meteor flying (5/1000)
    */
    public static int METEOR_APPEAR_CHANCE = 5;
    public static final int METEOR_APPEAR_RANGE = 1000;
    /*
        Main panel buttons and location
     */
    public static final int MAIN_BUTTON_WIDTH = 250;
    public static final int MAIN_BUTTON_HEIGHT = 30;
    public static final int MAIN_BUTTON_DIFFERENCE = WINDOW_HEIGHT / 6;
    public static final int MAIN_BUTTON_X = (WINDOW_WIDTH - MAIN_BUTTON_WIDTH) / 2;
    public static final int MAIN_BUTTON_Y = (WINDOW_WIDTH / 5);
    /*
        Player start position on the desk (the middle point)
     */
    public static final int PLAYER_START_X = (int) (WINDOW_WIDTH / 2 - PLAYER_WIDTH / 2);
    public static final int PLAYER_START_Y = (int) (WINDOW_HEIGHT - 2.5 * PLAYER_HEIGHT);
    /*
        Hero's skin
        Default skin:
        Locates on: resources/models/plane_models/player-model.png
     */
    public static Image PLAYER_IMAGE = new Image("file:resources/models/plane_models/player_models/spaceship-1.png");
    /*
        Game options
     */
    public static boolean IS_VERSUS_HUMAN = false;
    public static boolean iS_FIRST_PLAYED = false;
    public static final int MAX_ROUND = 5;
    public static int CURRENT_ROUND = 4;
    public static boolean IS_ROUND_WON = false;
    public static boolean IS_GAME_WON = false;
    public static int CURRENT_KILLS = 0;

    public static Font FONT = Font.loadFont(("file:FONT/15211.ttf"), 30);
}
