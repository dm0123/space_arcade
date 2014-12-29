package com.mygdx.sparcade.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Дмитрий on 28.11.2014.
 */
public class Starship {
    private  int sizex = 170, sizey = 100;
    public final  int velocity = 150;
    public enum State { LIVE, DEAD } // тут нужно подумать еще
    public enum Move {LEFT, RIGHT, IDLE}
    public State state = State.LIVE;
    public Move move = Move.IDLE;
    public boolean fire = false;
    public Rectangle bounds = new Rectangle();
    public String pic = new String("starship.png");
    public String pic2 = new String("starship_left.png");
    public String pic3 = new String("starship_right.png");
    public Texture shipPic, shipPicLeft, shipPicRight;
    public int life = 100;

    public Starship(Vector2 position)
    {
        bounds = new Rectangle(position.x, position.y, sizex, sizey);
    }

    public void setSize(int w, int h)
    {
        this.sizex = w;
        this.sizey = h;
    }
}
