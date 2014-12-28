package com.mygdx.sparcade;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.mygdx.sparcade.model.Bullet;
import com.mygdx.sparcade.model.Enemy;
import com.mygdx.sparcade.model.Starship;

public class Space extends Game {

    public SpriteBatch batch;
    public BitmapFont font;
    public Texture fon;
    Enemy enemy;
    Starship ship;
    final Array<Bullet> activeBullets = new Array<Bullet>();

    // bullet pool.
    final Pool<Bullet> bulletPool = new Pool<Bullet>() {
        @Override
        protected Bullet newObject() {
            return new Bullet();
        }
    };

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
