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
import com.mygdx.sparcade.model.Enemy;
import com.mygdx.sparcade.model.Starship;

/**
 * Created by Polomin on 17.11.2014.
 */
public class GameScreen implements Screen
{
    final Space game;

    Texture playerpic, enemypic, shipPic, shipPicLeft, shipPicRight, fon;
    private int frames = 0;

    private OrthographicCamera camera;
    private Music wind;
    private Player player;
    private Enemy enemy;
    private Starship ship;

    public GameScreen(final Space gam)
    {
        this.game = gam;

        // create player
        player = new Player(new Vector2(200, 20));
        enemy = new Enemy(new Vector2(800/2-64/2, 200));
        ship = new Starship(new Vector2(800 - 170 - 10, 480/2 - 50));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        playerpic = new Texture(Gdx.files.internal(player.filename));
        enemypic = new Texture(Gdx.files.internal(enemy.filename));

        shipPic = new Texture(Gdx.files.internal(ship.pic));
        shipPicLeft = new Texture(Gdx.files.internal(ship.pic2));
        shipPicRight = new Texture(Gdx.files.internal(ship.pic3));

        fon = new Texture(Gdx.files.internal("space_fon.jpg"));

        //  load wind sound
        wind = Gdx.audio.newMusic(Gdx.files.internal("wind.wav"));
    }

    @Override
    public void render(float delta)
    {
        if(true) {  // player.state == Player.State.LIVE
            Gdx.gl.glClearColor(0, 0, 0.2f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            camera.update();
            game.batch.setProjectionMatrix(camera.combined);
            game.batch.begin();
            game.batch.draw(fon, 0, 0);
            game.font.draw(game.batch, "Player class test, bounds: x=" + (player.bounds.x) + ", y= " + (player.bounds.y)+" player state: "+player.state, 0, 480);
            game.font.draw(game.batch, "Enemy class test, bounds: x=" + (enemy.bounds.x) + ", y= " + (enemy.bounds.y)+", enemy speed= " + (enemy.speed)+" enemy state: "+enemy.state, 0, 460);
            if (player.state == Player.State.LIVE)
                game.batch.draw(playerpic, player.bounds.x, player.bounds.y);
            game.batch.draw(enemypic, enemy.bounds.x, enemy.bounds.y);
            if (Gdx.input.isTouched()) {       // Управление кораблем, не хорошо что весь код в  одной куче
                Vector3 position = new Vector3();
                position.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(position);
                if ((int) (position.x) > 600 && ((int) (position.y) < 200)) {
                    ship.bounds.y -= 2;
                    game.batch.draw(shipPicLeft, ship.bounds.x, ship.bounds.y);
                }
                else
                    if ((int) (position.x) > 600 && ((int) (position.y) > 200)) {
                        ship.bounds.y += 2;
                        game.batch.draw(shipPicRight, ship.bounds.x, ship.bounds.y);
                    }
                    else
                        game.batch.draw(shipPic, ship.bounds.x, ship.bounds.y);
            }
            else
                game.batch.draw(shipPic, ship.bounds.x, ship.bounds.y);
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

            int r =  (int)(Math.random()*5) - 2;  // Рабочий рандом от -2 до 2
            //int r =  (int)(Math.random()*3) -1;
            enemy.speed += r;   // Все что я понял - не возможно работать со скоростью и координатами в целочисленном типе
            if (enemy.speed > 20)
                enemy.speed = 20;
            enemy.speed = 4;
            enemy.angle += 3;
            if (enemy.angle >= 360)
                enemy.angle = 0;

            if (enemy.angle >= 338 || enemy.angle < 23)
                enemy.bounds.y += enemy.speed;
            if (enemy.angle >= 23 && enemy.angle < 68) {
                enemy.bounds.x += enemy.speed;
                enemy.bounds.y += enemy.speed;
            }
            if (enemy.angle >= 68 && enemy.angle < 113)
                enemy.bounds.x += enemy.speed;
            if (enemy.angle >= 113 && enemy.angle < 158) {
                enemy.bounds.x += enemy.speed;
                enemy.bounds.y -= enemy.speed;
            }

            if (enemy.angle >= 158 && enemy.angle < 203)
                enemy.bounds.y -= enemy.speed;
            if (enemy.angle >= 203 && enemy.angle < 248){
                enemy.bounds.y -= enemy.speed;
                enemy.bounds.x -= enemy.speed;
            }
            if (enemy.angle >= 248 && enemy.angle < 293)
                enemy.bounds.x -= enemy.speed;
            if (enemy.angle >= 293 && enemy.angle < 338) {
                enemy.bounds.x -= enemy.speed;
                enemy.bounds.y += enemy.speed;
            }


//            enemy.bounds.x += ((int) Math.asin(enemy.angle)) * 3;
//            enemy.bounds.y += ((int) Math.acos(enemy.angle)) * 3;
            if (enemy.bounds.x < 0)
                enemy.bounds.x = 0;
            if (enemy.bounds.y < 0)
                enemy.bounds.y = 0;
            if (enemy.bounds.x > 800 - 64)
                enemy.bounds.x = 800 - 64;
            if (enemy.bounds.y > 480 - 64)
                enemy.bounds.y = 480 - 64;

            if (ship.bounds.y < 0)
                ship.bounds.y = 0;
            if (ship.bounds.y > 480 - 100)
                ship.bounds.y = 480 - 100;

            if (player.bounds.x > enemy.bounds.x - player.size && player.bounds.x < enemy.bounds.x + enemy.size &&
                player.bounds.y > enemy.bounds.y - player.size && player.bounds.y < enemy.bounds.y + enemy.size)  // Сверка координат врага и игрока
                player.state = player.state.DEAD;
            //else
                //player.state = player.state.LIVE;
            frames++;
//            if(frames > 1000)
//                player.state = Player.State.DEAD;
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
        playerpic.dispose();
        enemypic.dispose();
        shipPic.dispose();
        wind.dispose();
    }
}
