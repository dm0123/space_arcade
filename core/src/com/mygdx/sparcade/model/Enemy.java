package com.mygdx.sparcade.model;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Дмитрий on 27.11.2014.
 */
public class Enemy  extends Starship{
    public String filename = new String("zloy_kot.png");
    public int speed = 1;
    public int angle = 360; // 360=0 - Вверх, 90 - вправо, 180 - вниз


    public Enemy(Vector2 position)
    {
       super(position);
    }
}
