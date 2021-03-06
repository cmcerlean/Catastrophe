package com.kit10.csci448.catastrophe;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Activity that host the GameFragment
 */
public class GameActivity extends SingleFragmentActivity {

    public static final String MUSIC_ON_ID =
            "com.kit10.csci448.catastrophe.music_on_id";
    public static final String SOUND_ON_ID =
            "com.kit10.csci448.catastrophe.sound_on_id";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(0xFFFFFFFF, WindowManager.LayoutParams.FLAG_FULLSCREEN| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
    }

    /**
     * Creates an explicit intent to start a GameActivity
     */
    public static Intent newIntent(Context packageContext, boolean sound, boolean music) {
        Log.d(WelcomeActivity.LOG_TAG, "GameActivity: new intent");
        Intent intent = new Intent(packageContext, GameActivity.class);
        intent.putExtra(MUSIC_ON_ID, music);
        intent.putExtra(SOUND_ON_ID, sound);
        return intent;
    }

    /**
     * Creates the GameFragment
     */
    @Override
    protected Fragment createFragment() {
        boolean sound = (boolean) getIntent().getBooleanExtra(SOUND_ON_ID, true);
        boolean music = (boolean) getIntent().getBooleanExtra(MUSIC_ON_ID, true);
        return GameFragment.newInstance(sound, music);
    }
}
