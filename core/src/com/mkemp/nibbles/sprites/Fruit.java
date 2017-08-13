package com.mkemp.nibbles.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mkemp.nibbles.scenes.Hud;
import com.mkemp.nibbles.screens.PlayScreen;

import static com.mkemp.nibbles.Nibbles.FRUIT_BIT;
import static com.mkemp.nibbles.Nibbles.PPM;
import static com.mkemp.nibbles.Nibbles.SNAKE_BIT;

/**
 * Created by mkemp on 7/25/17.
 */

public class Fruit extends Sprite {

    private PlayScreen screen;
    private World world;
    private Body body;
    private Hud scoreHud;
    private boolean setToMove;

    // TODO : Make stage larger than screen?
    private int stageWidth = 13;
    private int stageHeight = 28;

    private int tileWidth = 16;
    private int tileHeight = 16;

    public Fruit(PlayScreen screen, Hud scoreHud, float x, float y) {
        this.world = screen.getWorld();
        this.screen = screen;
        this.scoreHud = scoreHud;

        // Create this body piece
        createFruit(x, y);

        setBounds(0, 0, 16 / PPM, 16 / PPM);
        moveFruitToRandomSpot();

        Texture texture = screen.getAssetManager().get("yoshi.png", Texture.class);
        setRegion(texture);
    }

    /**
     * This gets called every time the screen's render() method updates.
     */
    public void update() {
        if (setToMove)
            moveFruitToRandomSpot();
    }

    /**
     * Moves the fruit to a random spot on the board.
     */
    private void moveFruitToRandomSpot() {
        int randomX = (int) (Math.random() * stageWidth + 1);
        int randomY = (int) (Math.random() * stageHeight + 1);

        float newXCoord = (float) (randomX * tileWidth);
        float newYCoord = (float) (randomY * tileHeight);

        body.setTransform((newXCoord + 8) / PPM, (newYCoord + 8) / PPM, 0);
        setPosition(newXCoord / PPM, newYCoord / PPM);

        setToMove = false;
    }

    /**
     * Move the fruit to a new position.
     * Invoke the screen's addToTail() method.
     */
    public void eatFruit() {
        scoreHud.addScore(10);
        setToMove = true;
        screen.addToTail();
    }

    /**
     * Create this fruit.
     */
    private void createFruit(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x, y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);
        body.setUserData(this);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(6 / PPM);
        fixtureDef.filter.categoryBits = FRUIT_BIT;
        fixtureDef.filter.maskBits = SNAKE_BIT;

        fixtureDef.shape = circleShape;
        body.createFixture(fixtureDef).setUserData(this);
    }
}
