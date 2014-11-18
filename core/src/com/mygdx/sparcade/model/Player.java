package com.mygdx.sparcade.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Polomin on 18.11.2014.
 */
public class Player {
    static final int SIZE = 64;
    public enum State { LIVE, DEAD } // тут нужно подумать еще
    public State state = State.LIVE;
    public Rectangle bounds = new Rectangle();
    public String filename = new String("cat_5.png");


    public Player(Vector2 position)
    {
        bounds = new Rectangle(position.x, position.y, SIZE, SIZE);
    }
}
