package com.sem.e_health;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private ImageView imgApp;
    private TextView txtSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //changeStatusBarColor(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imgApp = findViewById(R.id.img_app_splash);
        txtSplash = findViewById(R.id.txt_splash);
        Animator setAnim = AnimatorInflater.loadAnimator(this, R.animator.set);
        setAnim.setTarget(imgApp);
        setAnim.start();

        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.voice);

        new Handler().postDelayed(() ->{
            txtSplash.setVisibility(View.VISIBLE);
            Animator txtAnim = AnimatorInflater.loadAnimator(this, R.animator.txt_anim);
            txtAnim.setTarget(txtSplash);
            txtAnim.start();
            mediaPlayer.start();

        },1500);

        new Handler().postDelayed(() -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, 5000);
    }

    private void changeStatusBarColor(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //  activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            // edited here
            activity.getWindow().setStatusBarColor(Color.rgb(0,131,174));

        }
    }
}
