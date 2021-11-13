package com.damienbose.calorieoverload;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.damienbose.calorieoverload.screens.MenuScreen;

public class CalorieOverload extends Game {
	public static final int V_WIDTH = 1280;
	public static final int V_HEIGHT = 720;
	public SpriteBatch batch;

	//filter
	public static final short PLAYER_BIT = 2;
	public static final short INVISIBLE_WALL_BIT = 4;
	public static final short WALL_BIT = 8;
	public static final short PROJECTILE_BIT = 16;
	public static final short ENEMY_BIT = 32;


	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
