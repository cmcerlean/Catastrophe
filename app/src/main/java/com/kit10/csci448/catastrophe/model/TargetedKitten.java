package com.kit10.csci448.catastrophe.model;

import android.graphics.Bitmap;

/**
 * Models a kitten that moves towards a specified point
 */
public class TargetedKitten extends Kitten {
    protected int targetX;
    protected int targetY;

    /**
     * @param sweetCatPic : bitmat defining the kitten's texture
     * @param x           : x start location
     * @param y           : y start location
     * @param targetX     : x target location
     * @param targetY     : y target location
     * @param home        : defines the cat's home
     * @param stepSize       : cat's move stepSize
     * @param stepSizeGrowth : cat's move stepSize growth per game loop iteration
     */
    public TargetedKitten(Bitmap sweetCatPic, int x, int y, int targetX, int targetY, Home home, double stepSize, double stepSizeGrowth) {
        super(sweetCatPic, x, y, home, stepSize, stepSizeGrowth);
        this.targetX = targetX;
        this.targetY = targetY;
    }

    /**
     * Overrides parent method to provide special movement
     */
    @Override
    protected void setVelocities() {
        super.setVelocities();
        if (state == State.FLEEING) {
            double hyp = Math.sqrt(Math.pow((targetX - x), 2.0) + Math.pow((targetY - y), 2.0)); // determine length of hypotenuse
            double stepsRequired = hyp / stepSize;
            velocityX = (targetX - x) / stepsRequired;
            velocityY = (targetY - y) / stepsRequired;
        }
    }

    public void setTargetCoordinates(int targetX, int targetY) {
        setTargetX(targetX);
        setTargetY(targetY);
    }

    public int getTargetX() {
        return targetX;
    }

    public int getTargetY() {
        return targetY;
    }

    public void setTargetX(int x) {
        targetX = x;
    }

    public void setTargetY(int y) {
        targetY = y;
    }
}
