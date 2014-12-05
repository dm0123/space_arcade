package com.mygdx.sparcade;

/**
 * Created by Polomin on 17.11.2014.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainMenuScreen implements Screen{
    final Space game;
    OrthographicCamera camera;

    public MainMenuScreen(final Space gam)
    {
        game = gam;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Space CAT! ",150, 450);
        game.font.draw(game.batch, "Tap anywhere to begin!", 150, 400);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
    }

    @Override
    public void dispose()
    {
    }

    @Override
    public void show()
    {

    }

    @Override
    public void hide()
    {

    }

    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void resize(int x, int y)
    {

    }
}
