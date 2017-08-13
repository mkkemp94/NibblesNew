package com.mkemp.nibbles.scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.mkemp.nibbles.Nibbles.SCREEN_HEIGHT;
import static com.mkemp.nibbles.Nibbles.SCREEN_WIDTH;

/**
 * Created by mkemp on 7/28/17.
 */

public class GameOverHud implements Disposable {

    public Stage stage;
    private Viewport viewport;
    private Label gameOverLabel;

    public GameOverHud(SpriteBatch sb) {

        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table = new Table(); // defaults in center
        table.setFillParent(true);

        gameOverLabel = new Label("GAME OVER", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(gameOverLabel).expandX();

        stage.addActor(table);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
