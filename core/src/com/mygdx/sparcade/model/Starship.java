package com.mygdx.sparcade.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Дмитрий on 28.11.2014.
 */
public class Starship {
    static final int SIZEX = 170, SIZEY = 100;
    public enum State { LIVE, DEAD } // тут нужно подумать еще
    public State state = State.LIVE;
    public enum Direct { UP, LEFT, RIGHT } // тут нужно подумать еще
    public Direct direct = Direct.UP;
    public Rectangle bounds = new Rectangle();
    public String pic = new String("starship.png");
    public String pic2 = new String("starship_left.png");
    public String pic3 = new String("starship_right.png");
    public int sizex = 170, sizey = 100;


    public Starship(Vector2 position)
    {
        bounds = new Rectangle(position.x, position.y, SIZEX, SIZEY);
    }
}
