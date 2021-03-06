package com.kit10.csci448.catastrophe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

/**
 * Hosts the WelcomeFragment
 */
public class WelcomeActivity extends SingleFragmentActivity {
    public static final String LOG_TAG = "Catastrophe";
    public static final String MUSIC_ON_ID =
            "com.kit10.csci448.catastrophe.music_on_id";
    public static final String SOUND_ON_ID =
            "com.kit10.csci448.catastrophe.sound_on_id";

    public static final int REQUEST_CODE_GAME = 0;
    public static final int REQUEST_CODE_OPTIONS = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(0xFFFFFFFF, WindowManager.LayoutParams.FLAG_FULLSCREEN| WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        super.onCreate(savedInstanceState);
    }

    /**
     * Used to create an explicit intent to launch the welcome activitiy
     * @param sound : whether the user wants sound
     * @param music : whether the user wants music
     * @return
     */
    public static Intent newIntent(Context packageContext, boolean sound, boolean music) {
        Log.d(WelcomeActivity.LOG_TAG, "WelcomeActivity: new intent");
        Intent intent = new Intent(packageContext, WelcomeActivity.class);
        intent.putExtra(MUSIC_ON_ID, music);
        intent.putExtra(SOUND_ON_ID, sound);
        return intent;
    }

    /**
     * Creates a welcome fragment
     */
    @Override
    protected Fragment createFragment() {
        boolean sound = (boolean) getIntent().getBooleanExtra(SOUND_ON_ID, true);
        boolean music = (boolean) getIntent().getBooleanExtra(MUSIC_ON_ID, true);
        return WelcomeFragment.newInstance(sound, music);
    }
}
