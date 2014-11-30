package com.mygdx.sparcade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.sparcade.model.Enemy;
import com.mygdx.sparcade.model.Starship;

/**
 * Created by Polomin on 17.11.2014.
 */
public class GameScreen implements Screen
{  // Лох
    final Space game;

    private OrthographicCamera camera;
    private Music wind;
    private Vector3 position = new Vector3();

    public GameScreen(final Space gam)
    {
        this.game = gam;

        // create starship
        game.enemy = new Enemy(new Vector2(800/2-64/2, 200));
        game.ship = new Starship(new Vector2(0, 480/2 - 50));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        game.enemy.enemypic = new Texture(Gdx.files.internal(game.enemy.filename));

        game.ship.shipPic = new Texture(Gdx.files.internal(game.ship.pic));
        game.ship.shipPicLeft = new Texture(Gdx.files.internal(game.ship.pic2));
        game.ship.shipPicRight = new Texture(Gdx.files.internal(game.ship.pic3));

        game.fon = new Texture(Gdx.files.internal("space_fon.jpg"));

        //  load wind sound
        wind = Gdx.audio.newMusic(Gdx.files.internal("wind.wav"));
    }

    @Override
    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(game.fon, 0, 0);
        game.font.draw(game.batch, "Enemy class test, bounds: x=" + (game.enemy.bounds.x) + ", y= " + (game.enemy.bounds.y)+", game.game.enemy speed= " + (game.enemy.speed)+" game.enemy state: "+game.enemy.state, 0, 470);
        game.batch.draw(game.enemy.enemypic, game.enemy.bounds.x, game.enemy.bounds.y);
        if (Gdx.input.isTouched()) {       // Управление кораблем, не хорошо что весь код в  одной куче
            this.position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(position);
            if ((int) (this.position.x) < 200 && ((int) (this.position.y) < 200)) {
                game.ship.bounds.y -= 2;
                game.batch.draw(game.ship.shipPicLeft, game.ship.bounds.x, game.ship.bounds.y);
            }
            else
                if ((int) (this.position.x) < 200 && ((int) (this.position.y) > 200)) {
                    game.ship.bounds.y += 2;
                    game.batch.draw(game.ship.shipPicRight, game.ship.bounds.x, game.ship.bounds.y);
                }
                else
                    game.batch.draw(game.ship.shipPic, game.ship.bounds.x, game.ship.bounds.y);
        }
        else
            game.batch.draw(game.ship.shipPic, game.ship.bounds.x, game.ship.bounds.y);
        game.batch.end();

        game.enemy.logic();

        if (game.ship.bounds.y < 0)
            game.ship.bounds.y = 0;
        if (game.ship.bounds.y > 480 - 100)
            game.ship.bounds.y = 480 - 100;
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        wind.setLooping(true);
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
        game.enemy.enemypic.dispose();
        wind.dispose();
    }
}
