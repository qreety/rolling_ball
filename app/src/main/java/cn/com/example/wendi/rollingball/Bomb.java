package cn.com.example.wendi.rollingball;

import android.graphics.Bitmap;

/**
 * Name: Wendi Du
 * NetID: wxd140330
 * Date started: April 8, 2015
 * For Assignment 5, CS6301.001
 * This class is the Bomb
 * It contains the property of bombs
 */
public class Bomb {
    float X,Y;
    Bitmap pic;
    int gTime;


    public Bomb(float posX, float posY, int t, Bitmap S){
        X = posX;
        Y = posY;
        pic = S;
        gTime = t;
    }

    /**
     * Check if the ball has hit the star
     * @param bX the ball's location
     * @param bY the ball's location
     * @return true if the ball hits the star
     */
    public boolean checkBall(float bX, float bY){

        if (X + pic.getWidth() > bX && Y+pic.getWidth() > bY && X < bX + 30 && Y < bY + 30)
            return true;
        else
            return false;
    }

}