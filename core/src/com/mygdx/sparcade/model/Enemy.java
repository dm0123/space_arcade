package com.mygdx.sparcade.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Дмитрий on 27.11.2014.
 */
public class Enemy {
    static final int SIZE = 64;
    public enum State { LIVE, DEAD } // тут нужно подумать еще
    public State state = State.LIVE;
    public Rectangle bounds = new Rectangle();
    public String filename = new String("zloy_kot.png");
    public int speed = 1;
    public int angle = 360; // 360=0 - Вверх, 90 - вправо, 180 - вниз
    public int size = 64;


    public Enemy(Vector2 position)
    {
        bounds = new Rectangle(position.x, position.y, SIZE, SIZE);
    }
}
