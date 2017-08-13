package com.mkemp.nibbles;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mkemp.nibbles.screens.PlayScreen;

public class Nibbles extends Game {

	public static final int SCREEN_WIDTH = 240;
	public static final int SCREEN_HEIGHT = 480;
	public static final float PPM = 100;

	public static final int SNAKE_BIT = 1;
	public static final int FRUIT_BIT = 2;
	public static final int WALL_BIT = 4;

	public SpriteBatch batch;

	private AssetManager assetManager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		assetManager.load("yoshi.png", Texture.class);
		assetManager.finishLoading();

		setScreen(new PlayScreen(this, assetManager));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		assetManager.dispose();
		batch.dispose();
	}
}
