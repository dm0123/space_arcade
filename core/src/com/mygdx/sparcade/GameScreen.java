package com.mygdx.sparcade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.sparcade.model.Enemy;
import com.mygdx.sparcade.model.Starship;
import com.mygdx.sparcade.model.Bullet;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/**
 * Created by Polomin on 17.11.2014.
 */
public class GameScreen implements Screen
{
    final Space game;

    private OrthographicCamera camera;
    private Music wind;
    private Sound bang, meow;
    private int fraps = 0;
    private int enemyDeadTime = 0;
    private int enemyAfterDetahPouse = 150;
    Texture rect = new Texture("rect.png");

    private int bullnum = 0, laststrike1 = 0, laststrike2 = 0;
    public GameScreen(final Space gam)
    {
        this.game = gam;


        // create starship
        game.enemy = new Enemy(new Vector2(100, 200));
        game.ship = new Starship(new Vector2(800 - 170, 480/2 - 50));

        game.bullet1 = new Bullet(new Vector2(game.ship.gun1x + game.ship.bounds.x, game.ship.gun1y + game.ship.bounds.y));
        game.bullet1.bullpic = new Texture(Gdx.files.internal(game.bullet1.pic));
        game.bullet2 = new Bullet(new Vector2(game.ship.gun1x + game.ship.bounds.x, game.ship.gun1y + game.ship.bounds.y));
        game.bullet2.bullpic = new Texture(Gdx.files.internal(game.bullet2.pic));
        game.bullet3 = new Bullet(new Vector2(game.ship.gun1x + game.ship.bounds.x, game.ship.gun1y + game.ship.bounds.y));
        game.bullet3.bullpic = new Texture(Gdx.files.internal(game.bullet3.pic));
        game.bullet4 = new Bullet(new Vector2(game.ship.gun1x + game.ship.bounds.x, game.ship.gun1y + game.ship.bounds.y));
        game.bullet4.bullpic = new Texture(Gdx.files.internal(game.bullet4.pic));
        game.bullet5 = new Bullet(new Vector2(game.ship.gun1x + game.ship.bounds.x, game.ship.gun1y + game.ship.bounds.y));
        game.bullet5.bullpic = new Texture(Gdx.files.internal(game.bullet5.pic));
        game.bullet6 = new Bullet(new Vector2(game.ship.gun1x + game.ship.bounds.x, game.ship.gun1y + game.ship.bounds.y));
        game.bullet6.bullpic = new Texture(Gdx.files.internal(game.bullet6.pic));



        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        game.enemy.enemypic = new Texture(Gdx.files.internal(game.enemy.filename));

        game.ship.shipPic = new Texture(Gdx.files.internal(game.ship.pic));
        game.ship.shipPicLeft = new Texture(Gdx.files.internal(game.ship.pic2));
        game.ship.shipPicRight = new Texture(Gdx.files.internal(game.ship.pic3));


        game.fon = new Texture(Gdx.files.internal("space_fon.jpg"));

        //  load wind sound
        wind = Gdx.audio.newMusic(Gdx.files.internal("wind.wav"));
        bang = Gdx.audio.newSound(Gdx.files.internal("bang.wav"));
        meow = Gdx.audio.newSound(Gdx.files.internal("meow.wav"));
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
        game.font.draw(game.batch, "Enemy class test, bounds: x=" + (game.enemy.bounds.x) + ", y= " + (game.enemy.bounds.y)+", speed= " + (game.enemy.speed)+", Fraps - "+fraps+", EnemyDeadTime - "+enemyDeadTime, 0, 470);
        if (enemyDeadTime == enemyAfterDetahPouse)
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
            if (fraps - laststrike1 > 22 &&(((int) (touch.x) < 600) && (int) (touch.x) > 400 && ((int) (touch.y) < 240) ||
                ((int) (touch2.x) < 600) && (int) (touch2.x) > 400 && ((int) (touch2.y) < 240 ))) {
                //game.font.draw(game.batch, "Fire the first gun!!!", 400, 220);
                if (bullnum == 0) {
                    game.bullet1.bounds.x = game.ship.gun1x + game.ship.bounds.x;
                    game.bullet1.bounds.y = game.ship.gun1y + game.ship.bounds.y;
                }
                if (bullnum == 1) {
                    game.bullet2.bounds.x = game.ship.gun1x + game.ship.bounds.x;
                    game.bullet2.bounds.y = game.ship.gun1y + game.ship.bounds.y;
                }
                if (bullnum == 2) {
                    game.bullet3.bounds.x = game.ship.gun1x + game.ship.bounds.x;
                    game.bullet3.bounds.y = game.ship.gun1y + game.ship.bounds.y;
                }
                if (bullnum == 3) {
                    game.bullet4.bounds.x = game.ship.gun1x + game.ship.bounds.x;
                    game.bullet4.bounds.y = game.ship.gun1y + game.ship.bounds.y;
                }
                if (bullnum == 4) {
                    game.bullet5.bounds.x = game.ship.gun1x + game.ship.bounds.x;
                    game.bullet5.bounds.y = game.ship.gun1y + game.ship.bounds.y;
                }
                if (bullnum == 5) {
                    game.bullet6.bounds.x = game.ship.gun1x + game.ship.bounds.x;
                    game.bullet6.bounds.y = game.ship.gun1y + game.ship.bounds.y;
                }
                bullnum++;  // Максимум на экране отображается 6 пуль, поэтому мы создаем 6 экземпляров класса
                if (bullnum == 6)
                    bullnum = 0;
                bang.play();
                laststrike1 = fraps;
            }
            if (fraps - laststrike2 > 22 &&(((int) (touch.x) < 600) && (int) (touch.x) > 400 && ((int) (touch.y) > 240) ||
               ((int) (touch2.x) < 600) && (int) (touch2.x) > 400 && ((int) (touch2.y) > 240))) {
                    //game.font.draw(game.batch, "Fire the second gun!!!", 400, 260);
                if (bullnum == 0) {
                    game.bullet1.bounds.x = game.ship.gun2x + game.ship.bounds.x;
                    game.bullet1.bounds.y = game.ship.gun2y + game.ship.bounds.y;
                }
                if (bullnum == 1) {
                    game.bullet2.bounds.x = game.ship.gun2x + game.ship.bounds.x;
                    game.bullet2.bounds.y = game.ship.gun2y + game.ship.bounds.y;
                }
                if (bullnum == 2) {
                    game.bullet3.bounds.x = game.ship.gun2x + game.ship.bounds.x;
                    game.bullet3.bounds.y = game.ship.gun2y + game.ship.bounds.y;
                }
                if (bullnum == 3) {
                    game.bullet4.bounds.x = game.ship.gun2x + game.ship.bounds.x;
                    game.bullet4.bounds.y = game.ship.gun2y + game.ship.bounds.y;
                }
                if (bullnum == 4) {
                    game.bullet5.bounds.x = game.ship.gun2x + game.ship.bounds.x;
                    game.bullet5.bounds.y = game.ship.gun2y + game.ship.bounds.y;
                }
                if (bullnum == 5) {
                    game.bullet6.bounds.x = game.ship.gun2x + game.ship.bounds.x;
                    game.bullet6.bounds.y = game.ship.gun2y + game.ship.bounds.y;
                }
                bullnum++;
                if (bullnum == 6)
                    bullnum = 0;
                bang.play();
                laststrike2 = fraps;
            }
            }
        else
            game.batch.draw(game.ship.shipPic, game.ship.bounds.x, game.ship.bounds.y);
        game.batch.draw(game.bullet1.bullpic, game.bullet1.bounds.x, game.bullet1.bounds.y);
        game.batch.draw(game.bullet2.bullpic, game.bullet2.bounds.x, game.bullet2.bounds.y);
        game.batch.draw(game.bullet3.bullpic, game.bullet3.bounds.x, game.bullet3.bounds.y);
        game.batch.draw(game.bullet4.bullpic, game.bullet4.bounds.x, game.bullet4.bounds.y);
        game.batch.draw(game.bullet5.bullpic, game.bullet5.bounds.x, game.bullet5.bounds.y);
        game.batch.draw(game.bullet6.bullpic, game.bullet6.bounds.x, game.bullet6.bounds.y);

        game.batch.draw(rect, 600, 0);
        game.batch.draw(rect, 600, 240);
        game.batch.draw(rect, 400, 0);
        game.batch.draw(rect, 400, 240);

        //game.font.draw(game.batch, "Bull x - "+game.ship.gun1x + game.ship.bounds.x+"Bull y - "+game.ship.gun1y + game.ship.bounds.y, 200, 160)
        game.batch.end();

        if (enemyDeadTime == enemyAfterDetahPouse && game.bullet1.bounds.x + 5 > game.enemy.bounds.x && game.bullet1.bounds.x < game.enemy.bounds.x + 64 &&
                game.bullet1.bounds.y + 5 > game.enemy.bounds.y && game.bullet1.bounds.y < game.enemy.bounds.y + 64) {
            meow.play();
            enemyDeadTime = 0;
            game.enemy.bounds.y = (int)(Math.random()*420) + 20; // Рабочий рандом от -2 до 2
            game.enemy.bounds.x = (int)(Math.random()*440) + 20;
        }
        if (enemyDeadTime == enemyAfterDetahPouse && game.bullet2.bounds.x + 5 > game.enemy.bounds.x && game.bullet2.bounds.x < game.enemy.bounds.x + 64 &&
                game.bullet2.bounds.y + 5 > game.enemy.bounds.y && game.bullet2.bounds.y < game.enemy.bounds.y + 64) {
            meow.play();
            enemyDeadTime = 0;
            game.enemy.bounds.y = (int)(Math.random()*420) + 20; // Рабочий рандом от -2 до 2
            game.enemy.bounds.x = (int)(Math.random()*440) + 20;
        }
        if (enemyDeadTime == enemyAfterDetahPouse && game.bullet3.bounds.x + 5 > game.enemy.bounds.x && game.bullet3.bounds.x < game.enemy.bounds.x + 64 &&
                game.bullet3.bounds.y + 5 > game.enemy.bounds.y && game.bullet3.bounds.y < game.enemy.bounds.y + 64) {
            meow.play();
            meow.play();
            enemyDeadTime = 0;
            game.enemy.bounds.y = (int)(Math.random()*420) + 20; // Рабочий рандом от -2 до 2
            game.enemy.bounds.x = (int)(Math.random()*440) + 20;
        }
        if (enemyDeadTime == enemyAfterDetahPouse && game.bullet4.bounds.x + 5 > game.enemy.bounds.x && game.bullet4.bounds.x < game.enemy.bounds.x + 64 &&
                game.bullet4.bounds.y + 5 > game.enemy.bounds.y && game.bullet4.bounds.y < game.enemy.bounds.y + 64) {
            meow.play();
            enemyDeadTime = 0;
            game.enemy.bounds.y = (int)(Math.random()*420) + 20; // Рабочий рандом от -2 до 2
            game.enemy.bounds.x = (int)(Math.random()*440) + 20;
        }
        if (enemyDeadTime == enemyAfterDetahPouse && game.bullet5.bounds.x + 5 > game.enemy.bounds.x && game.bullet5.bounds.x < game.enemy.bounds.x + 64 &&
                game.bullet5.bounds.y + 5 > game.enemy.bounds.y && game.bullet5.bounds.y < game.enemy.bounds.y + 64) {
            meow.play();
            enemyDeadTime = 0;
            game.enemy.bounds.y = (int)(Math.random()*420) + 20; // Рабочий рандом от -2 до 2
            game.enemy.bounds.x = (int)(Math.random()*440) + 20;
        }
        if (enemyDeadTime == enemyAfterDetahPouse && game.bullet6.bounds.x + 5 > game.enemy.bounds.x && game.bullet6.bounds.x < game.enemy.bounds.x + 64 &&
                game.bullet6.bounds.y + 5 > game.enemy.bounds.y && game.bullet6.bounds.y < game.enemy.bounds.y + 64) {
            meow.play();
            enemyDeadTime = 0;
            game.enemy.bounds.y = (int)(Math.random()*420) + 20;
            game.enemy.bounds.x = (int)(Math.random()*440) + 20;
        }
        if (enemyDeadTime < enemyAfterDetahPouse)
            enemyDeadTime++;
        game.enemy.logic();
        game.bullet1.logic();
        game.bullet2.logic();
        game.bullet3.logic();
        game.bullet4.logic();
        game.bullet5.logic();
        game.bullet6.logic();

        if (game.ship.bounds.y < 0)
            game.ship.bounds.y = 0;
        if (game.ship.bounds.y > 480 - 100)
            game.ship.bounds.y = 480 - 100;
        fraps++;
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
        // start the playback of the background music
        // when the screen is shown
        wind.setLooping(true);
        //wind.play();
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
