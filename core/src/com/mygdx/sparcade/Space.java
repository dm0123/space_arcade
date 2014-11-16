package com.mygdx.sparcade;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Rectangle;

//import java.awt.Rectangle;

public class Space extends ApplicationAdapter {
	Texture img;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Music wind;
    private Rectangle player;
	
	@Override
	public void create () {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

        //  load wind sound
        wind = Gdx.audio.newMusic(Gdx.files.internal("wind.wav"));

        // play sound
        wind.setLooping(true);
        wind.play();

        // create player
        player = new Rectangle();
        player.x = 800/2-64/2;
        player.y = 20;
        player.width = 64;
        player.height = 64;
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(img, player.x, player.y);
        batch.end();

        // moving player if touched
        if(Gdx.input.isTouched())
        {
            Vector3 position = new Vector3();
            position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(position);
            player.x = (int)(position.x - 64/2);
            player.y = (int)(position.y - 64/2);
        }

        // don't let player go off the screen
        if(player.x < 0)
            player.x = 0;
        if(player.y < 0)
            player.y = 0;
        if(player.x > 800-64)
            player.x = 800-64;
        if(player.y > 480-64)
            player.y = 480-64;
	}
}
