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

    public void logic()
    {
        int r =  (int)(Math.random()*5) - 2;  // Рабочий рандом от -2 до 2
        //int r =  (int)(Math.random()*3) -1;
        this.speed += r;   // Все что я понял - не возможно работать со скоростью и координатами в целочисленном типе
        if (this.speed > 20)
            this.speed = 20;
        this.speed = 4;
        this.angle += 3;
        if (this.angle >= 360)
            this.angle = 0;

        if (this.angle >= 338 || this.angle < 23)
            this.bounds.y += this.speed;
        if (this.angle >= 23 && this.angle < 68) {
            this.bounds.x += this.speed;
            this.bounds.y += this.speed;
        }
        if (this.angle >= 68 && this.angle < 113)
            this.bounds.x += this.speed;
        if (this.angle >= 113 && this.angle < 158) {
            this.bounds.x += this.speed;
            this.bounds.y -= this.speed;
        }

        if (this.angle >= 158 && this.angle < 203)
            this.bounds.y -= this.speed;
        if (this.angle >= 203 && this.angle < 248){
            this.bounds.y -= this.speed;
            this.bounds.x -= this.speed;
        }
        if (this.angle >= 248 && this.angle < 293)
            this.bounds.x -= this.speed;
        if (this.angle >= 293 && this.angle < 338) {
            this.bounds.x -= this.speed;
            this.bounds.y += this.speed;
        }

        if (this.bounds.x < 0)
            this.bounds.x = 0;
        if (this.bounds.y < 0)
            this.bounds.y = 0;
        if (this.bounds.x > 800 - 64)
            this.bounds.x = 800 - 64;
        if (this.bounds.y > 480 - 64)
            this.bounds.y = 480 - 64;
    }
}
