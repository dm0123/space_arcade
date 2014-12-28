package com.mygdx.sparcade.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Polomin on 06.12.2014.
 */
public class Bullet implements Pool.Poolable {
    public Vector2 position;
    public boolean alive;

    public Bullet()
    {
        this.position = new Vector2();
        this.alive = false;
    }
    public void init(float posX, float posY) {
        position.set(posX,  posY);
        alive = true;
    }

    @Override
    public void reset() {
        position.set(0,0);
        alive = false;
    }

    public void update (float delta) {

        position.add(0, 10*delta*60);
    }
}
