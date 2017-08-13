package com.mkemp.nibbles.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mkemp.nibbles.Nibbles;

import static com.mkemp.nibbles.Nibbles.SCREEN_HEIGHT;
import static com.mkemp.nibbles.Nibbles.SCREEN_WIDTH;

/**
 * Created by mkemp on 7/19/17.
 */

public class Controller {
    Viewport viewport;
    Stage stage;
    boolean upPressed, downPressed, leftPressed, rightPressed;
    OrthographicCamera cam;
    Nibbles game;
    private int controllerDimens = 25;

    public Controller(Nibbles game) {
        this.game = game;
        cam = new OrthographicCamera();
        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, cam);
        stage = new Stage(viewport, game.batch);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.center().bottom();

        Image upImage = new Image(new Texture("up2.png"));
        upImage.setSize(controllerDimens, controllerDimens);
        CircleShape upCircle = new CircleShape();
        upImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                upPressed = false;
            }
        });

        Image downImage = new Image(new Texture("down2.png"));
        downImage.setSize(controllerDimens, controllerDimens);
        downImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                downPressed = false;
            }
        });

        Image leftImage = new Image(new Texture("left2.png"));
        leftImage.setSize(controllerDimens, controllerDimens);
        leftImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                leftPressed = false;
            }
        });

        Image rightImage = new Image(new Texture("right2.png"));
        rightImage.setSize(controllerDimens, controllerDimens);
        rightImage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = true;
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                rightPressed = false;
            }
        });

        table.setFillParent(true);

        table.add();
        table.add(upImage).size(upImage.getWidth()*2, upImage.getHeight());
        table.add();
        table.row();
        table.add(leftImage).size(leftImage.getWidth(), leftImage.getHeight()*2);
        table.add();
        table.add(rightImage).size(rightImage.getWidth(), rightImage.getHeight()*2);
        table.row().padBottom(5);
        table.add();
        table.add(downImage).size(downImage.getWidth()*2, downImage.getHeight());
        table.add();

        stage.addActor(table);
    }

    public void draw() {
        stage.draw();
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public void releaseUpPressed() {
        upPressed = false;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
