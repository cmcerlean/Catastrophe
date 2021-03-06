package com.kit10.csci448.catastrophe;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.kit10.csci448.catastrophe.model.Home;
import com.kit10.csci448.catastrophe.model.Kitten;
import com.kit10.csci448.catastrophe.model.ScoreSplash;

import java.util.List;

/**
 * Used to draw the game
 */
public class GameView extends View {
    private static final String TAG = "GameView";

    private Context context;
    public int width;
    public int height;
    public static final int UPPER_BORDER = 0;
    private GestureDetectorCompat mDetector;

    private Bitmap mBitmap;
    private Canvas mCanvas;

    private List<Kitten> mKitties;
    private ScoreSplash mScoreSplash;

    private Home mHome;

    public GameView(Context c, AttributeSet attrs) {
        super(c, attrs);
        context = c;
        mDetector = new GestureDetectorCompat(c,new GameGestureListener());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w;
        height = w;
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    /**
     * Draws all of the game elements
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mScoreSplash.drawing()) {
            mScoreSplash.draw(canvas);
        }
        mHome.draw(canvas);
        for (Kitten k : mKitties) {
            k.draw(canvas);
        }
    }

    /**
     * Forces the screen to update
     */
    public void update() {
        invalidate();
    }

    /**
     * Handles touch events
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                for (Kitten k : mKitties) {
                    k.handleActionUp(x, y);
                }
                break;
        }
        invalidate();
        return true;
    }

    /**
     * Sets pointers for the game pieces
     */
    public void setGameResources(List<Kitten> kitties, ScoreSplash scoreSplash, Home home) {
        mKitties = kitties;
        mHome = home;
        mScoreSplash = scoreSplash;
    }

    public Home getHome() {
        return mHome;
    }

    public void setHome(Home mHome) {
        this.mHome = mHome;
    }

    /**
     * Used to interpret more complex touch movements
     */
    class GameGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String GESTURE_TAG = "GameView.Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(GESTURE_TAG,"onDown: " + event.toString());
            for (Kitten k : mKitties) {
                k.handleActionDown(event.getX(), event.getY());
                if (k.getState() == Kitten.State.HELD) { // only hold one kitten at a time
                    break;
                }
            }
            return true;
        }

        /**
         * Interprets drags
         */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.d(GESTURE_TAG,"onScroll");
            for (Kitten k : mKitties) {
                if (k.getState() == Kitten.State.HELD) {
                    // kitten is being moved
                    k.setCoordinates(e2.getX(), e2.getY());
                }
            }
            return true;
        }


        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {
            Log.d(GESTURE_TAG, "FLUNG" +
                    " X POS: " + Float.toString(e1.getY()) +
                    " Y POS: " + Float.toString(e1.getX()) +
                    " X VEL: " + Float.toString(velocityX) +
                    " Y VEL: " + Float.toString(velocityY));
            Log.d(GESTURE_TAG, "Nice downswipe");
            for (Kitten k : mKitties) {
                if (k.getState() == Kitten.State.HELD) {
                    k.handleActionFlung(e2.getX(), e2.getY(), velocityX, velocityY);
                }
            }
            return true;
        }
    }


}
