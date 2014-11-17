package com.mygdx.sparcade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Polomin on 17.11.2014.
 */
public class GameScreen implements Screen
{
    final Space game;

    Texture img;

    private OrthographicCamera camera;
    private Music wind;
    private Rectangle player;

    public GameScreen(final Space gam)
    {
        this.game = gam;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        img = new Texture(Gdx.files.internal("cat_5.png"));

        //  load wind sound
        wind = Gdx.audio.newMusic(Gdx.files.internal("wind.wav"));

        // create player
        player = new Rectangle();
        player.x = 800/2-64/2;
        player.y = 20;
        player.width = 64;
        player.height = 64;
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.draw(game.batch, "Font test, position: x="+(player.x)+", y= "+(player.y), 0, 480);
        game.batch.draw(img, player.x, player.y);
        game.batch.end();

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

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        wind.play();
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose()
    {
        img.dispose();
        wind.dispose();
    }
}
