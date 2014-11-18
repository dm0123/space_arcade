package com.mygdx.sparcade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.sparcade.model.Player;

/**
 * Created by Polomin on 17.11.2014.
 */
public class GameScreen implements Screen
{
    final Space game;

    Texture img;
    private int frames = 0;

    private OrthographicCamera camera;
    private Music wind;
    private Player player;

    public GameScreen(final Space gam)
    {
        this.game = gam;

        // create player
        player = new Player(new Vector2(800/2-64/2, 20));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        img = new Texture(Gdx.files.internal(player.filename));

        //  load wind sound
        wind = Gdx.audio.newMusic(Gdx.files.internal("wind.wav"));
    }

    @Override
    public void render(float delta)
    {
        if(player.state == Player.State.LIVE) {
            Gdx.gl.glClearColor(0, 0, 0.2f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            camera.update();
            game.batch.setProjectionMatrix(camera.combined);
            game.batch.begin();
            game.font.draw(game.batch, "Player class test, bounds: x=" + (player.bounds.x) + ", y= " + (player.bounds.y)+" player state: "+player.state, 0, 480);
            game.batch.draw(img, player.bounds.x, player.bounds.y);
            game.batch.end();

            // moving player if touched
            if (Gdx.input.isTouched()) {
                Vector3 position = new Vector3();
                position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(position);
                player.bounds.x = (int) (position.x - 64 / 2);
                player.bounds.y = (int) (position.y - 64 / 2);
            }

            // don't let player go off the screen
            if (player.bounds.x < 0)
                player.bounds.x = 0;
            if (player.bounds.y < 0)
                player.bounds.y = 0;
            if (player.bounds.x > 800 - 64)
                player.bounds.x = 800 - 64;
            if (player.bounds.y > 480 - 64)
                player.bounds.y = 480 - 64;
            frames++;
            if(frames > 1000)
                player.state = Player.State.DEAD;
        }
        else dispose();
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
