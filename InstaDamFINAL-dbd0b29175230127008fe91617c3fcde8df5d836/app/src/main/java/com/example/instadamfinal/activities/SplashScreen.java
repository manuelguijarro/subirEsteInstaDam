package com.example.instadamfinal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.instadamfinal.R;

public class SplashScreen extends AppCompatActivity {
    private ImageView imageLogo;
    private TextView textLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        loadImageLogo();
        loadTextLogo();
        applyEffectImageLogo();
        loadTextVisibility();
    }

    private void loadImageLogo() {
        imageLogo = findViewById(R.id.logoInstaDam);
        imageLogo.setImageResource(R.drawable.logo_instadam);
    }

    private void loadTextLogo() {
        textLogo = findViewById(R.id.textViewLogo);
        textLogo.setText("Welcome to instaDAM!");
        textLogo.setVisibility(View.INVISIBLE);
    }

    private void applyEffectImageLogo() {
        float fromScale = 0.5f,
                toScale = 1.0f,
                pivotX = imageLogo.getWidth() / 2f,
                pivotY = imageLogo.getHeight() / 2f;

        ScaleAnimation scaleAnimation = createScaleAnimation(fromScale, toScale, pivotX, pivotY);

        animateImageLogo(scaleAnimation);
    }

    private ScaleAnimation createScaleAnimation(float fromScale, float toScale, float pivotX, float pivotY) {
        return new ScaleAnimation(
                fromScale, toScale,
                fromScale, toScale,
                pivotX, pivotY
        );
    }

    private void animateImageLogo(ScaleAnimation scaleAnimation) {
        scaleAnimation.setDuration(1500);
        scaleAnimation.setFillAfter(true);
        imageLogo.startAnimation(scaleAnimation);
    }

    private void loadTextVisibility() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textLogo.setVisibility(View.VISIBLE);
                loadIntent();
            }
        }, 2100);
    }

    private void loadIntent() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {//CAMBIAR ESTA LINEA CUANDO TERMINE PRUBAS
                Intent intent = new Intent(SplashScreen.this, SignIn.class);
                startActivity(intent);
                finish();
            }
        }, 1650);
    }
}
