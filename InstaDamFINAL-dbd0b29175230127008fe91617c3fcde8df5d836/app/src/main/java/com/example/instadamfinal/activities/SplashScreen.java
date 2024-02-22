package com.example.instadamfinal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.instadamfinal.R;

public class SplashScreen extends AppCompatActivity {
    private ImageView imagenLogo;
    private TextView textoMensajeBienvenida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        cargarRecursosVista();
        cargarImagenLogo();
        aplicarEfectoImagenLogo();
        cargarTextoBienvenidaVisibilidad();
        cargarActivityInicioApp();
    }
    private void cargarRecursosVista() {
        textoMensajeBienvenida = findViewById(R.id.textViewMensajeBienvenida);
        imagenLogo = findViewById(R.id.logoInstaDam);

    }
    private void cargarImagenLogo() {
        imagenLogo.setImageResource(R.drawable.logo_instadam);
    }

    private void aplicarEfectoImagenLogo() {
        //Primero declaramos las variables que serán los puntos en el eje
        //x e y
        float fromScale = 0.5f,
                toScale = 1.0f,
                pivotX = imagenLogo.getWidth() / 2f,
                pivotY = imagenLogo.getHeight() / 2f;
        //Luego instanciamos el objeto ScaleAnimation con los valores.
        ScaleAnimation escalaAnimacion = new ScaleAnimation(
                fromScale, toScale,
                fromScale, toScale,
                pivotX, pivotY
        );
        animarImagenLogo(escalaAnimacion);
    }

    private void animarImagenLogo(ScaleAnimation escalaAnimacion) {
        escalaAnimacion.setDuration(1500);
        escalaAnimacion.setFillAfter(true);
        imagenLogo.startAnimation(escalaAnimacion);
    }

    private void cargarTextoBienvenidaVisibilidad() {
        new Handler().postDelayed(() ->
                textoMensajeBienvenida.setText("Bienvenido a instaDAM!"), 1500);
    }

    private void cargarActivityInicioApp() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this, SignIn.class);
            startActivity(intent);
            finish();
        }, 1650);
    }
}
