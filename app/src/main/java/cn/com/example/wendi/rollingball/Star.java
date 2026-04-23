package cn.com.example.wendi.rollingball;

import android.graphics.Bitmap;

/**
 * Name: Wendi Du
 * NetID: wxd140330
 * Date started: April 8, 2015
 * For Assignment 5, CS6301.001
 * This class is the Star
 * It contains the property of stars
 */

public class Star {
    public float X, Y;
    private int H, W;
    Bitmap bt;
    int gTime;

    public Star(float posX, float posY, int t, Bitmap star){
        X = posX;
        Y = posY;
        bt = star;
        H = bt.getHeight();
        W = bt.getWidth();
        gTime = t;
    }

    /**
     * Check if the ball has hit the bomb
     * @param bX the ball's location
     * @param bY the ball's location
     * @return true if the ball hits the bomb
     */
    public boolean checkBall(float bX, float bY){

        if (X + W > bX && Y+H > bY && X < bX + 30 && Y < bY + 30)
            return true;
        else
            return false;
    }



}
