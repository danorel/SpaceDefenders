package game.sprite;

import game.Preferences;
import javafx.scene.image.ImageView;

import javax.naming.directory.AttributeInUseException;

public class Sprite extends ImageView
{
    private boolean isAlive  = true;
    private double positionX = 0;
    private double positionY = 0;
    private StringBuilder type;

    public Sprite(double positionX, double positionY, String type, String url){
        super(url);
        this.type       = new StringBuilder(type);
        this.positionX  = positionX;
        this.positionY  = positionY;
        setTranslateX(this.positionX);
        setTranslateY(this.positionY);
    }

    public void move(){
        if(type.toString().equals(Preferences.SpriteType.ALIEN.toString())) {
            setTranslateX(getTranslateX() + Preferences.ALIEN_VELOCITY);
            if (getTranslateX() > Preferences.WINDOW_WIDTH - Preferences.ALIEN_WIDTH) {
                setTranslateY(getTranslateY() + Preferences.ALIEN_HEIGHT + 10);
                Preferences.ALIEN_VELOCITY = -Preferences.ALIEN_VELOCITY;
            }
            if(getTranslateX() < -Preferences.ALIEN_WIDTH){
                setTranslateY(getTranslateY() + Preferences.ALIEN_HEIGHT + 10);
                Preferences.ALIEN_VELOCITY = - Preferences.ALIEN_VELOCITY;
            }
        }

        if(type.toString().equals(Preferences.SpriteType.PLAYER.toString())){
            setTranslateX(getTranslateX() + Preferences.PLAYER_HORIZONTAL_VELOCITY);
            if(getTranslateX() > Preferences.WINDOW_WIDTH - Preferences.PLAYER_WIDTH){
                setTranslateX(-Preferences.PLAYER_WIDTH);
            }
            if(getTranslateX() < -Preferences.PLAYER_WIDTH){
                setTranslateX(Preferences.WINDOW_WIDTH - Preferences.PLAYER_WIDTH);
            }

            setTranslateY(getTranslateY() + Preferences.PLAYER_VERTICAL_VELOCITY);
            if(getTranslateY() <= 0 || getTranslateY() >= Preferences.WINDOW_HEIGHT - Preferences.PLAYER_HEIGHT){
                setTranslateY(Preferences.WINDOW_HEIGHT - Preferences.PLAYER_HEIGHT);
            }
        }
    }

    public void moveUp() {
        if(type.toString().equals(Preferences.SpriteType.PLAYER_ROCKET.toString())) {
            setTranslateY(getTranslateY() - Preferences.ROCKET_VELOCITY);
            if (getTranslateY() < 0) {
                isAlive = false;
            }
        }
    }

    public void moveDown() {
        if(type.toString().equals(Preferences.SpriteType.ALIEN_ROCKET.toString())) {
            setTranslateY(getTranslateY() + Preferences.ROCKET_VELOCITY);
            if (getTranslateY() > Preferences.WINDOW_HEIGHT - Preferences.ROCKET_HEIGHT) {
                isAlive = false;
            }
        }

        if(type.toString().equals(Preferences.SpriteType.METEOR.toString())){
            setTranslateY(getTranslateY() + Preferences.METEOR_VELOCITY);
            if (getTranslateY() > Preferences.WINDOW_HEIGHT - Preferences.METEOR_HEIGHT) {
                isAlive = false;
            }
        }
    }

    public Sprite fire() throws AttributeInUseException {
        if(type.toString().equals(Preferences.SpriteType.ALIEN.toString())){
            return new Sprite(
                    getTranslateX() + Preferences.ALIEN_WIDTH / 2 - Preferences.ROCKET_WIDTH / 2,
                    getTranslateY() + Preferences.ALIEN_HEIGHT + Preferences.ROCKET_HEIGHT,
                    Preferences.SpriteType.ALIEN_ROCKET.toString(),
                    "file:resources/models/missles/rocket-1.png"
            );
        } else if(type.toString().equals(Preferences.SpriteType.PLAYER.toString())){
            return new Sprite(
                    getTranslateX() + Preferences.PLAYER_WIDTH / 2 - Preferences.ROCKET_WIDTH / 2,
                    getTranslateY() - Preferences.ROCKET_HEIGHT,
                    Preferences.SpriteType.PLAYER_ROCKET.toString(),
                    "file:resources/models/missles/rocket-2.png"
            );
        } else {
            throw new AttributeInUseException(
                    "Error! This unit cannot use the fire function. Try with another one."
            );
        }
    }

    public void die(){
        isAlive = false;
    }

    public boolean intersects(Sprite another){
        return another.getBoundsInParent().intersects( this.getBoundsInParent());
    }

    public boolean isAlive() {
        return isAlive;
    }

    public String getType(){
        return type.toString();
    }

    @Override
    public String toString() {
        return type.toString();
    }
}