package com.mkemp.nibbles.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.World;
import com.mkemp.nibbles.screens.PlayScreen;

import java.util.ArrayList;

import static com.mkemp.nibbles.Nibbles.PPM;

/**
 * Created by mkemp on 7/25/17.
 */

public class Player {

    // Directions for the snake to move in.
    public static final int RIGHT = 0;
    public static final int UP = 90;
    public static final int LEFT = 180;
    public static final int DOWN = 270;

    public World world;
    public PlayScreen screen;
    private float moveTimer;
    private int direction;

    // The snake body.
    public ArrayList<SnakePart> snakeBody;

    private boolean snakeIsDead;
    private boolean addToTail;

    private float snakeSpeed;
    private int speedCounter;

    private double xMinEdge = 0.1;
    private double xMaxEdge = 2.3;
    private double yMinEdge = 0.1;
    private double yMaxEdge = 4.6;

    public Player(PlayScreen screen) {
        this.world = screen.getWorld();
        this.screen = screen;

        // This gets updated every render cycle.
        // Snake moves when it reaches a certain time.
        moveTimer = 0;

        snakeSpeed = 0.3f;
        speedCounter = 2;

        snakeIsDead = false;

        // Position for snake to start at.
        float x = 72 / PPM;
        float y = 40 / PPM;

        direction = 0;

        // Add each body piece to the list
        snakeBody = new ArrayList<SnakePart>();

        // Start snake at a length of 1.
        for (int i = 0; i < 3; i++)
            snakeBody.add(new SnakePart(screen, x - (i * 16 / PPM), y, 0));
    }

    /**
     * Move body and attach sprite.
     */
    public void update(float dt) {

        // Move snake.
        moveSnake(dt);

        // Check for game over.
        // TODO : Use world contact listener for this?
        float headXPos = snakeBody.get(0).getPosition().x;
        float headYPos = snakeBody.get(0).getPosition().y;
        if (headXPos <= xMinEdge || headXPos >= xMaxEdge ||
                headYPos <= yMinEdge || headYPos >= yMaxEdge) {
            Gdx.app.log("Game", "Over");
            snakeIsDead = true;
        }
    }

    /**
     * Move tail to front of snake.
     */
    private void moveSnake(float dt) {

        // Update move timer.
        moveTimer += dt;

        float lastRotation = getTail().getSpriteRotation();

        // Current position of head before moving.
        float headXPos = getHead().getPosition().x;
        float headYPos = getHead().getPosition().y;

        // Current position of tail before moving.
        // Add a new piece here is fruit is eaten.
        float tailXPos = getTail().getPosition().x;
        float tailYPos = getTail().getPosition().y;

        // Move the snake to this time.
        if (moveTimer >= snakeSpeed) {

            Gdx.app.log("Y pos", headYPos+"");

            // Move tail to front.
            // TODO : Rotating left shouldn't be upside down.
            // TODO : Try moving with velocity instead of transforming.
            switch (direction) {
                case RIGHT:
                    getTail().moveTo(headXPos + 16 / PPM, headYPos, direction);
                    break;
                case LEFT:
                    getTail().moveTo(headXPos - 16 / PPM, headYPos, direction);
                    break;
                case UP:
                    getTail().moveTo(headXPos, headYPos + 16 / PPM, direction);
                    break;
                case DOWN:
                    getTail().moveTo(headXPos, headYPos - 16 / PPM, direction);
                    break;
            }

            // Update snake list.
            updateList();

            // If it's time to add a new piece to the tail, do so.
            if (addToTail) {
                snakeBody.add(new SnakePart(screen, tailXPos, tailYPos, lastRotation));
                addToTail = false;
            }

            // Reset move timer.
            moveTimer = 0;
        }
    }

    /**
     * Get current head piece.
     */
    private SnakePart getHead() {
        return snakeBody.get(0);
    }

    /**
     * Get current tail piece.
     */
    private SnakePart getTail() {
        return snakeBody.get(snakeBody.size() - 1);
    }

    /**
     * Update array list --
     * Add a copy of the last element to front,
     * and then remove the last element.
     */
    private void updateList() {
        snakeBody.add(0, getTail());
        snakeBody.remove(snakeBody.size() - 1);
    }

    /**
     * Gets whether or not the snake is dead.
     * @return : true or false
     */
    public boolean snakeIsDead() {
        return snakeIsDead;
    }

    /**
     * Called by handleInput() in PlayScreen.
     * This sets the direction for the snake to move in.
     * @param degrees
     */
    public void setDirection(int degrees) {

        // Don't move in the opposite direction.
        if (direction == 0 && degrees == 180 ||
                direction == 90 && degrees == 270 ||
                direction == 180 && degrees == 0 ||
                direction == 270 && degrees == 90)
            return;

        direction = degrees;
    }

    /**
     * Draw each body part's sprite.
     */
    public void draw(Batch batch) {
        for (SnakePart snakeBody : this.snakeBody)
            snakeBody.draw(batch);
    }

    /**
     * Snake has just eaten a fruit.
     * Add a new snake part to the tail.
     */
    public void addToTail() {
        Gdx.app.log("Player", "Adding to tail");
        addToTail = true;
        speedCounter--;
        if (speedCounter <= 0) {
            snakeSpeed -= 0.02f;
            speedCounter = 2;
        }
    }

    /**
     * Set snake to dead state.
     */
    public void setSnakeIsDead() {
        snakeIsDead = true;
    }
}
