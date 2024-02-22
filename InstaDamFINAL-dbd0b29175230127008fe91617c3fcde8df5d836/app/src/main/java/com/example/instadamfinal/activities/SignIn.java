package com.example.instadamfinal.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.instadamfinal.R;
import com.example.instadamfinal.controllers.DBController;
import com.example.instadamfinal.controllers.EmailController;
import com.example.instadamfinal.controllers.PasswordController;

public class SignIn extends AppCompatActivity {
    private EditText editTextEmailUsuario;
    private EditText editTextPasswordUsuario;
    private Button botonEnviar;
    private Button botonCargarRegistroUsuario;
    private TextView textViewMensajeAlertaRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        cargarRecursosVista();
        cargarEventosOnClickBotones();
    }

    private void cargarRecursosVista() {
        //inputs
        editTextEmailUsuario = findViewById(R.id.editTextEmailAddress);
        editTextPasswordUsuario = findViewById(R.id.editTextPassword);
        //botones
        botonEnviar = findViewById(R.id.sendButton);
        botonCargarRegistroUsuario = findViewById(R.id.signUpButton);
        //texto
        textViewMensajeAlertaRegistro = findViewById(R.id.messageAlert);
    }

    private void cargarEventosOnClickBotones() {
        botonEnviar.setOnClickListener(this::enviarFormulario);
        botonCargarRegistroUsuario.setOnClickListener(this::cargarActivityRegistro);
    }

    private void enviarFormulario(View v) {
        String emailUsuario = editTextEmailUsuario.getText().toString();
        String passwordUsuario = editTextPasswordUsuario.getText().toString();

        if (EmailController.comprobarEmail(emailUsuario) &&
                PasswordController.comprobarPassword(passwordUsuario)) {
            logearUsuario(emailUsuario, passwordUsuario);
        } else {
            mostrarMensajeAlerta("Datos incorrectos");
        }
    }

    private void logearUsuario(String emailUsuario, String passwordUsuario) {
        DBController dbController = new DBController();
        boolean existeUsuario = dbController.logearUsuarioController(this.getBaseContext(), emailUsuario, passwordUsuario);

        if (existeUsuario) {
            mostrarMensajeAlerta("Bienvenido a InstaDAM " + emailUsuario);
            cargarActivityMain(emailUsuario);
        } else {
            mostrarMensajeAlerta("Error en inicio de sesion, registra un nuevo usuario o edita los inputs");
        }
    }

    private void mostrarMensajeAlerta(String mensaje) {
        textViewMensajeAlertaRegistro.setText(mensaje);
    }

    public void cargarActivityRegistro(View view) {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
            finish();
        }, 1000);
    }

    public void cargarActivityMain(String emailUsuario) {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("key", emailUsuario);
            startActivity(intent);
            finish();
        }, 1000);
    }
}
