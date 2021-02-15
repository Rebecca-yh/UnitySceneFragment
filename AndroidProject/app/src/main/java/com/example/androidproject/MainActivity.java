package com.example.androidproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.androidproject.unity.UnityFragment;
import com.example.androidproject.unity.UnityScene;
import com.unity3d.player.UnityPlayer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UnityScene.mUnityPlayer = new UnityPlayer(getApplicationContext());
        getWindow().setFormat(PixelFormat.RGBX_8888);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, UnityFragment.class, null)
                    .commit();
        }
    }
    @Override
    protected void onDestroy() {
        UnityScene.mUnityPlayer.quit();
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        super.onPause();
        UnityScene.mUnityPlayer.pause();
    }


    @Override
    protected void onResume() {
        super.onResume();
        UnityScene.mUnityPlayer.resume();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        UnityScene.mUnityPlayer.configurationChanged(newConfig);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        UnityScene.mUnityPlayer.windowFocusChanged(hasFocus);
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_MULTIPLE)
            return UnityScene.mUnityPlayer.injectEvent(event);
        return super.dispatchKeyEvent(event);
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return UnityScene.mUnityPlayer.injectEvent(event);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return UnityScene.mUnityPlayer.injectEvent(event);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return UnityScene.mUnityPlayer.injectEvent(event);
    }


    public boolean onGenericMotionEvent(MotionEvent event) {
        return UnityScene.mUnityPlayer.injectEvent(event);
    }
}