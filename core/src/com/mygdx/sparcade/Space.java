package com.mygdx.sparcade;
// ЛОХ!
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
public class Space extends Game {

    public SpriteBatch batch;
    public BitmapFont font;

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
