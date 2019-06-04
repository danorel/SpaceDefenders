package game.sprite;

import game.Preferences;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javax.naming.directory.AttributeInUseException;

public class Sprite extends Rectangle
{
    private boolean isAlive  = true;
    private double positionX = 0;
    private double positionY = 0;
    private double width     = 0;
    private double height    = 0;
    private StringBuilder type;

    public Sprite(double positionX, double positionY, double width, double height, String type, Color color){
        super(width, height, color);
        this.type       = new StringBuilder(type);
        this.positionX  = positionX;
        this.positionY  = positionY;
        this.width      = width;
        this.height     = height;
        setTranslateX(this.positionX);
        setTranslateY(this.positionY);
    }

    public void move(){
        setTranslateX(positionX + Preferences.ALIEN_VELOCITY);
        if(getTranslateX() > Preferences.WINDOW_WIDTH - Preferences.ALIEN_WIDTH || getTranslateX() < 0){
            setTranslateY(getTranslateY() + Preferences.ALIEN_HEIGHT + 10);
            Preferences.ALIEN_VELOCITY = -Preferences.ALIEN_VELOCITY;
        }
    }

    public void moveLeft() throws AttributeInUseException {
        if(type.toString().equals(Preferences.SpriteType.PLAYER.toString())) {
            setTranslateX(getTranslateX() - Preferences.PLAYER_VELOCITY);
            if (getTranslateX() < 0) {
                setTranslateX(Preferences.WINDOW_WIDTH - Preferences.PLAYER_WIDTH);
            }
        } else {
            throw new AttributeInUseException(
                    "Error! This unit cannot use move left function. Try another one!"
            );
        }
    }

    public void moveRight() throws AttributeInUseException {
        if(type.toString().equals(Preferences.SpriteType.PLAYER.toString())){
            setTranslateX(getTranslateX() + Preferences.PLAYER_VELOCITY);
            if(getTranslateX() > Preferences.WINDOW_WIDTH - Preferences.PLAYER_WIDTH){
                setTranslateX(0);
            }
        } else {
            throw new AttributeInUseException(
                    "Error! This unit cannot use move right function. Try another one!"
            );
        }
    }

    public void moveUp() throws AttributeInUseException {
        if(type.toString().equals(Preferences.SpriteType.PLAYER_ROCKET.toString())) {
            setTranslateY(getTranslateY() - Preferences.ROCKET_VELOCITY);
            if (getTranslateY() < 0) {
                isAlive = false;
            }
        } else {
            throw new AttributeInUseException(
                    "Error! This unit cannot use move up function. Try another one!"
            );
        }
    }

    public void moveDown() throws AttributeInUseException {
        if(type.toString().equals(Preferences.SpriteType.ALIEN_ROCKET.toString()) || type.toString().equals(Preferences.SpriteType.METEOR.toString())) {
            setTranslateY(getTranslateY() + Preferences.ROCKET_VELOCITY);
            if (getTranslateY() > Preferences.WINDOW_HEIGHT - Preferences.ROCKET_HEIGHT) {
                isAlive = false;
            }
        } else {
            throw new AttributeInUseException(
                    "Error! This unit cannot use move up function. Try another one!"
            );
        }
    }

    public Sprite shoot() throws AttributeInUseException {
        if(type.toString().equals(Preferences.SpriteType.ALIEN.toString())){
            return new Sprite(
                    getTranslateX() + Preferences.ALIEN_WIDTH / 2 - Preferences.ROCKET_WIDTH / 2,
                    getTranslateY() + Preferences.ALIEN_HEIGHT + Preferences.ROCKET_HEIGHT,
                    Preferences.ROCKET_WIDTH,
                    Preferences.ROCKET_HEIGHT,
                    Preferences.SpriteType.ALIEN_ROCKET.toString(),
                    Color.DARKRED
            );
        } else if(type.toString().equals(Preferences.SpriteType.PLAYER.toString())){
            return new Sprite(
                    getTranslateX() + Preferences.PLAYER_WIDTH / 2 - Preferences.ROCKET_WIDTH / 2,
                    getTranslateY() - Preferences.ROCKET_HEIGHT,
                    Preferences.ROCKET_WIDTH,
                    Preferences.ROCKET_HEIGHT,
                    Preferences.SpriteType.PLAYER_ROCKET.toString(),
                    Color.BLACK
            );
        } else {
            throw new AttributeInUseException(
                    "Error! This unit cannot use the shoot function. Try with another one."
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

    @Override
    public String toString() {
        return type.toString();
    }
}