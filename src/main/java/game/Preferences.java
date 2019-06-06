package game;

import javafx.scene.image.Image;

public class Preferences {
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
    public static final double ALIEN_WIDTH     = 25;
    public static final double ALIEN_HEIGHT    = 25;
    public static double ALIEN_VELOCITY        = 10;
    public static final int[] ALIEN_AMOUNT_PER_LEVEL  = {5, 10, 12, 3, 1};
    public static final int ALIEN_START_X = (int) (WINDOW_WIDTH / 2 - ALIEN_WIDTH / 2);
    public static final int ALIEN_START_Y = (int) (WINDOW_HEIGHT - 1.5 * ALIEN_HEIGHT);
    /*
        The chance of alien shooting (3/100)
     */
    public static final int ALIEN_SHOOT_CHANCE = 3;
    /*
        Player options
     */
    public static final double PLAYER_WIDTH    = 35;
    public static final double PLAYER_HEIGHT   = 35;
    public static final double PLAYER_VELOCITY = 15;
    /*
        Rocket options
     */
    public static final int ROCKET_WIDTH       = 5;
    public static final int ROCKET_HEIGHT      = 20;
    public static final double ROCKET_VELOCITY = 5;
    /*
        Main panel buttons and location
     */
    public static final int MAIN_BUTTON_WIDTH         = 220;
    public static final int MAIN_BUTTON_HEIGHT        = 30;
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
    public static Image IMAGE = new Image("file:resources/models/plane_models/player_models/spaceship-1.png");
    /*
        The chance of meteor flying (5/1000)
     */
    public static final int METEOR_APPEAR_CHANCE = 5;
    /*
        Game options
     */
    public static final int MAX_ROUND = 4;
    public static int CURRENT_ROUND   = 0;
    public static boolean isRoundWon  = false;
    public static boolean isGameWon   = false;
}
