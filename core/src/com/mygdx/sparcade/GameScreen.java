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
        game.ship = new Starship(new Vector2(800 - 170, 480/2 - 50));

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
        if (Gdx.input.isTouched() || Gdx.input.isTouched(1)) { // Управление кораблем, не хорошо что весь код в  одной куче
            Vector3 touch = new Vector3();
            Vector3 touch2 = new Vector3();
            touch.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touch);
            game.font.draw(game.batch, "Touch 1 x - "+Gdx.input.getX()+", y - "+Gdx.input.getX(), 0, 60);
            if (Gdx.input.isTouched(1)) {
                touch2.set(Gdx.input.getX(1), Gdx.input.getY(1), 0);
                camera.unproject(touch2);
                game.font.draw(game.batch, "Touch 2 x - "+Gdx.input.getX(1)+", y - "+Gdx.input.getX(1), 0, 20);
                if (!Gdx.input.isTouched()) // Отпустили палец, удалили(ну как удалили) кор-ы тапа
                    touch = touch2;
            }
            if (((int) (touch2.x) < 600)) {    // Если второй тап не связан с управлением, то все норм
                if ((int) (touch.x) > 600 && ((int) (touch.y) < 240)) {  // Влево
                    game.ship.bounds.y -= 3;
                    game.batch.draw(game.ship.shipPicLeft, game.ship.bounds.x, game.ship.bounds.y);
                } else if ((int) (touch.x) > 600 && ((int) (touch.y) > 240)) {  // Вправо
                    game.ship.bounds.y += 3;
                    game.batch.draw(game.ship.shipPicRight, game.ship.bounds.x, game.ship.bounds.y);
                } else
                    game.batch.draw(game.ship.shipPic, game.ship.bounds.x, game.ship.bounds.y);
            }
            else {  // Иначе выполнить только второй тап, но третий(второй раз первый) тап к сожалению не сработает
                if ((int) (touch2.x) > 600 && ((int) (touch2.y) < 240)) {  // Влево
                    game.ship.bounds.y -= 3;
                    game.batch.draw(game.ship.shipPicLeft, game.ship.bounds.x, game.ship.bounds.y);
                } else if ((int) (touch2.x) > 600 && ((int) (touch2.y) > 240)) {  // Вправо
                    game.ship.bounds.y += 3;
                    game.batch.draw(game.ship.shipPicRight, game.ship.bounds.x, game.ship.bounds.y);
                } else
                    game.batch.draw(game.ship.shipPic, game.ship.bounds.x, game.ship.bounds.y);
            }  // Ну и отдельно обработка выстрелов
            if (((int) (touch.x) < 600) && (int) (touch.x) > 400 && ((int) (touch.y) < 240) ||
               ((int) (touch2.x) < 600) && (int) (touch2.x) > 400 && ((int) (touch2.y) < 240))
                    game.font.draw(game.batch, "Fire the first gun!!!", 400, 220);
            if (((int) (touch.x) < 600) && (int) (touch.x) > 400 && ((int) (touch.y) > 240) ||
               ((int) (touch2.x) < 600) && (int) (touch2.x) > 400 && ((int) (touch2.y) > 240))
                    game.font.draw(game.batch, "Fire the second gun!!!", 400, 260);
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
