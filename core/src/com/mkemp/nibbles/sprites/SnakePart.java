package com.mkemp.nibbles.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mkemp.nibbles.screens.PlayScreen;

import static com.mkemp.nibbles.Nibbles.FRUIT_BIT;
import static com.mkemp.nibbles.Nibbles.PPM;
import static com.mkemp.nibbles.Nibbles.SNAKE_BIT;
import static com.mkemp.nibbles.Nibbles.WALL_BIT;

/**
 * Created by mkemp on 7/31/17.
 * Creates a part of the snake at the specified position and rotation.
 */

public class SnakePart {

    private PlayScreen screen;
    public World world;
    private Sprite sprite;
    private Body body;
    private boolean isFlipped;

    public SnakePart(PlayScreen screen, float x, float y, float rotation) {
        this.screen = screen;
        this.world = screen.getWorld();

        // Create this body piece
        createBody(x, y);

        // Get the texture for this piece.
        // TODO : Texture should be egg if part is in egg state -- always true, on create.
        Texture texture = screen.getAssetManager().get("yoshi.png", Texture.class);

        sprite = new Sprite(texture);
        sprite.setBounds(0, 0, 16 / PPM, 16 / PPM);
        sprite.setRegion(texture);
        sprite.setOrigin(sprite.getWidth()/2,sprite.getHeight()/2);
        sprite.setRotation(rotation);
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                body.getPosition().y - sprite.getHeight() / 2);
    }

    /**
     * Get the position of this snake part.
     * @return : a Vector2 representing the current body position
     */
    public Vector2 getPosition() {
        return body.getPosition();
    }

    /**
     * Transform this snake part to a new x, y coordinate.
     * @param x : x-coordinate
     * @param y : y-coordinate
     * @param direction : the direction it should be facing
     */
    public void moveTo(float x, float y, float direction) {
        body.setTransform(x, y, 0);
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                body.getPosition().y - sprite.getHeight() / 2);

        if (direction == 180) {
            sprite.setRotation(0);
            if (!isFlipped) {
                sprite.flip(true, false);
                isFlipped = true;
            }
        }
        else {
            if (isFlipped) {
                sprite.flip(true, false);
                isFlipped = false;
            }
            sprite.setRotation(direction);

        }
    }

    /**
     * Get the rotation this snake part's sprite is currently at.
     * @return : a float representing the degrees of rotation
     */
    public float getSpriteRotation() {
        return sprite.getRotation();
    }

    /**
     * Create the snake piece's body.
     * Assign it a fixture so that it can do things.
     */
    private void createBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        body.setUserData(this);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(6 / PPM);
        fixtureDef.filter.categoryBits = SNAKE_BIT;
        fixtureDef.filter.maskBits = FRUIT_BIT | SNAKE_BIT | WALL_BIT;

        fixtureDef.shape = circleShape;
        body.createFixture(fixtureDef).setUserData(this);
    }

    /**
     * Draw the sprite for this snake part.
     * @param batch : the sprite batch
     */
    public void draw(Batch batch) {
        sprite.draw(batch);
    }

    /**
     * Something bad happened to snake. Set to game over.
     */
    public void gameOver() {
        screen.setSnakeIsDead();
    }
}
