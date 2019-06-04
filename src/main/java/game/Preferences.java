package game;

public class Preferences {
    /*
        Main window parameters
     */
    static final String TITLE = "Space Defenders";
    public static final int WINDOW_WIDTH = 1024;
    public static final int WINDOW_HEIGHT = 720;
    /*
        Sprite options
     */
    public enum SpriteType {
        PLAYER, ALIEN, PLAYER_ROCKET, ALIEN_ROCKET, METEOR
    }
    /*
        Aliens options
     */
    public static final double ALIEN_WIDTH     = 50;
    public static final double ALIEN_HEIGHT    = 50;
    public static double ALIEN_VELOCITY        = 30;
    static final int[] ALIEN_AMOUNT_PER_LEVEL  = {5, 10, 12, 3, 1};
    static final int ALIEN_START_X = (int) (WINDOW_WIDTH / 2 - ALIEN_WIDTH / 2);
    static final int ALIEN_START_Y = (int) (WINDOW_HEIGHT - 1.5 * ALIEN_HEIGHT);
    /*
        The chance of alien shooting (3/100)
     */
    static final int ALIEN_SHOOT_CHANCE = 3;
    /*
        Player options
     */
    public static final double PLAYER_WIDTH    = 75;
    public static final double PLAYER_HEIGHT   = 75;
    public static final double PLAYER_VELOCITY = 25;
    public static boolean isAlive              = true;
    /*
        Rocket options
     */
    public static final int ROCKET_WIDTH       = 5;
    public static final int ROCKET_HEIGHT      = 20;
    public static final double ROCKET_VELOCITY = 10;
    /*
        Main panel buttons and location
     */
    static final int MAIN_BUTTON_WIDTH         = 160;
    static final int MAIN_BUTTON_HEIGHT        = 60;
    static final int MAIN_BUTTON_DIFFERENCE = WINDOW_HEIGHT / 8;
    static final int MAIN_BUTTON_X = (WINDOW_WIDTH - MAIN_BUTTON_HEIGHT) / 2 - MAIN_BUTTON_WIDTH / 4;
    static final int MAIN_BUTTON_Y = (WINDOW_WIDTH / 5);
    /*
        Player start position on the desk (the middle point)
     */
    static final int PLAYER_START_X = (int) (WINDOW_WIDTH / 2 - PLAYER_WIDTH / 2);
    static final int PLAYER_START_Y = (int) (WINDOW_HEIGHT - 1.5 * PLAYER_HEIGHT);
    /*
        The chance of meteor flying (5/1000)
     */
    static final int METEOR_APPEAR_CHANCE = 5;
    /*
        Game options
     */
    static final int MAX_ROUND = 4;
    static int CURRENT_ROUND   = 2;
    static boolean isRoundWon  = false;
    static boolean isGameWon   = false;
}
