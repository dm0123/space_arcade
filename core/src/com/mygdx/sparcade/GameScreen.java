package com.mygdx.sparcade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.sparcade.model.Enemy;
import com.mygdx.sparcade.model.Starship;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Polomin on 17.11.2014.
 */
public class GameScreen extends InputAdapter implements Screen
{
    class TouchInfo {
        public float touchX = 0;
        public float touchY = 0;
        public boolean touched = false;
    }

    private Map<Integer,TouchInfo> touches = new HashMap<Integer,TouchInfo>();

    final Space game;

    private OrthographicCamera camera;
    private Music wind;
    private Texture fireTexture = new Texture(Gdx.files.internal("fire-button.png"));
    private int shipPic;

    public GameScreen(final Space gam)
    {
        this.game = gam;

        // create starship
        game.enemy = new Enemy(new Vector2(200, 800/2-64/2));
        game.ship = new Starship(new Vector2(480/2 - 50, 0));

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 480, 800);
        game.enemy.enemypic = new Texture(Gdx.files.internal(game.enemy.filename));

        game.ship.shipPic = new Texture(Gdx.files.internal(game.ship.pic));
        game.ship.shipPicLeft = new Texture(Gdx.files.internal(game.ship.pic2));
        game.ship.shipPicRight = new Texture(Gdx.files.internal(game.ship.pic3));

        game.fon = new Texture(Gdx.files.internal("space_fon.jpg"));

        //  load wind sound
        wind = Gdx.audio.newMusic(Gdx.files.internal("wind.wav"));
        Gdx.input.setInputProcessor(this);
        for(int i = 0; i < 5; i++){
            touches.put(i, new TouchInfo());
        }
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
        game.font.drawMultiLine(game.batch, "Enemy class test, bounds: x=" + (game.enemy.bounds.x) + ", y= " + (game.enemy.bounds.y)+" game.enemy state: "+game.enemy.state, 0, 790);
        game.batch.draw(game.enemy.enemypic, game.enemy.bounds.x, game.enemy.bounds.y);
        game.batch.draw(fireTexture, 480-64, 0);

        for(int i = 0; i < 5; i++){
            if(touches.get(i).touched) {
                if(touches.get(i).touchX < game.ship.bounds.x-1 && touches.get(i).touchY > 600)
                {
                    game.ship.move = Starship.Move.LEFT;
                }
                if(touches.get(i).touchX > game.ship.bounds.x+1 && touches.get(i).touchY > 600)
                {
                    game.ship.move = Starship.Move.RIGHT;
                }
                if(touches.get(i).touchX > 480-64 && touches.get(i).touchX < 64)
                {
                    game.ship.fire = true;
                }
            }
        }
        game.font.draw(game.batch, "game.ship.move: "+game.ship.move, 0, 300);
        game.font.draw(game.batch, "game.ship.fire: "+game.ship.fire, 0, 310);
        switch(game.ship.move){
            case LEFT:
                if(game.ship.bounds.x > 0)
                    game.ship.bounds.x -= game.ship.velocity*Gdx.graphics.getDeltaTime();
                shipPic = 2;
                break;
            case RIGHT:
                if(game.ship.bounds.x < 480-64)
                    game.ship.bounds.x += game.ship.velocity*Gdx.graphics.getDeltaTime();
                shipPic = 3;
                break;
        }
        switch (shipPic) {
            case 1:
                game.batch.draw(game.ship.shipPic, game.ship.bounds.x, game.ship.bounds.y);
                break;
            case 2:
                game.batch.draw(game.ship.shipPicLeft, game.ship.bounds.x, game.ship.bounds.y);
                break;
            case 3:
                game.batch.draw(game.ship.shipPicRight, game.ship.bounds.x, game.ship.bounds.y);
        }
        game.batch.end();
        game.enemy.logic();
        game.ship.move = Starship.Move.IDLE;
        shipPic = 1;
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

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(pointer < 5){
            touches.get(pointer).touchX = screenX;
            touches.get(pointer).touchY = screenY;
            touches.get(pointer).touched = true;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if(pointer < 5){
            touches.get(pointer).touchX = 0;
            touches.get(pointer).touchY = 0;
            touches.get(pointer).touched = false;
        }
        return true;
    }
}
