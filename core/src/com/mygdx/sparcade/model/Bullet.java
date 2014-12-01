package com.mygdx.sparcade.model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Дмитрий on 01.12.2014.
 */
public class Bullet {
    static final int SIZEX = 20, SIZEY = 40;
    public Rectangle bounds = new Rectangle();
    public String pic = new String("bull.png");
    public Texture bullpic;

    public int actSizex1 = 6;
    public int actSizex2 = 14;
    public int actSizey1 = 3;
    public int actSizey2 = 12;
    public int speed = 10;


    public Bullet(Vector2 position) {
        bounds = new Rectangle(position.x, position.y, SIZEX, SIZEY);
    }

    public void logic() {
        this.bounds.x -= speed;
    }
}