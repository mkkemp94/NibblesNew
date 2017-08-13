package com.mkemp.nibbles.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
 * Created by mkemp on 7/25/17.
 */

public class Hud implements Disposable {

    public Stage stage;
    private Viewport viewport;

    private Label scoreText;
    private Label highScoreText;
    static Label scoreLabel;
    static Label highScoreLabel;
    private Integer score;
    private Integer highScore;
    private Preferences prefs;

    public Hud(SpriteBatch batch) {

        if (prefs == null) {
            prefs = Gdx.app.getPreferences("Preferences");
        }

        score = 0;
        highScore = getHighScore();

        viewport = new FitViewport(SCREEN_WIDTH, SCREEN_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, batch);

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        highScoreText = new Label("HIGH SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreText = new Label("CURRENT SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        highScoreLabel = new Label(String.format("%05d", highScore), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%05d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(highScoreText).expandX();
        table.add(scoreText).expandX();
        table.row();
        table.add(highScoreLabel).expandX();
        table.add(scoreLabel).expandX();

        stage.addActor(table);
    }

    public void addScore(int value) {
        score += value;
        scoreLabel.setText(String.format("%05d", score));

        if (score > highScore) {
            highScore = score;
            setHighScoreToPrefs();
            highScoreLabel.setText(String.format("%05d", highScore));
        }
    }

    public Integer getHighScore() {
        Gdx.app.log("score", "Prefs high score is " + prefs.getInteger("highscore"));
        return prefs.getInteger("highscore");
    }

    public void setHighScoreToPrefs() {
        Gdx.app.log("score", "Saving score to prefs : " + score);
        prefs.putInteger("highscore", score);
        prefs.flush();
        Gdx.app.log("score", "Now high score is " + prefs.getInteger("highscore"));

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
