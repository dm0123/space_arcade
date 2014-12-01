package com.mygdx.sparcade;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.sparcade.model.Enemy;
import com.mygdx.sparcade.model.Starship;
import com.mygdx.sparcade.model.Bullet;

public class Space extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public Texture fon;
    Enemy enemy;
    Starship ship;
    Bullet bullet1;
    Bullet bullet2;
    Bullet bullet3;
    Bullet bullet4;
    Bullet bullet5;
    Bullet bullet6;

    @Override
	public void create () {
        batch = new SpriteBatch();
        font = new BitmapFont();

        // set game screen
        this.setScreen(new MainMenuScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}

    @Override
    public void dispose() {
        batch.dispose();
    }
}
