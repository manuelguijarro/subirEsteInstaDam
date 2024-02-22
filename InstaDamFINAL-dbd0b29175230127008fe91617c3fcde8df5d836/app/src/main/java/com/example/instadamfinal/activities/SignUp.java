package com.example.instadamfinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.instadamfinal.R;
import com.example.instadamfinal.controllers.DBController;
import com.example.instadamfinal.controllers.EmailController;
import com.example.instadamfinal.controllers.PasswordController;

public class SignUp extends AppCompatActivity {

    private EditText editTextNombreUsuario;
    private EditText editTextEmailUsuario;
    private EditText editTextPasswordUsuario;
    private TextView textViewMensajeAlertaRegistro;
    private Button botonEnviar;
    private Button botonVolverInicioSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        cargarRecursosVista();
        cargarEventosOnClickBotones();
    }

    private void cargarRecursosVista() {
        //input
        editTextNombreUsuario = findViewById(R.id.editTextUserName);
        editTextEmailUsuario = findViewById(R.id.editTextEmailAddress);
        editTextPasswordUsuario = findViewById(R.id.editTextPassword);
        //Texto
        textViewMensajeAlertaRegistro = findViewById(R.id.textViewMensajeAlerta);
        //Botones
        botonVolverInicioSesion = findViewById(R.id.signUpButton);
        botonEnviar = findViewById(R.id.sendButton);
    }

    private void cargarEventosOnClickBotones() {
        botonEnviar.setOnClickListener(this::enviarFormulario);
        botonVolverInicioSesion.setOnClickListener(this::cargarActivityInicioSesion);
    }

    private void enviarFormulario(View v) {
        String nombreUsuario = editTextNombreUsuario.getText().toString();
        String emailUsuario = editTextEmailUsuario.getText().toString();
        String passwordUsuario = editTextPasswordUsuario.getText().toString();

        //Accedemos a los metodos de las clases de manera estatica.
        if (!nombreUsuario.isEmpty() &&
                EmailController.comprobarEmail(emailUsuario) &&
                PasswordController.comprobarPassword(passwordUsuario)) {

            registrarNuevoUsuario(nombreUsuario, emailUsuario, passwordUsuario);
            cargarActivityInicioSesion(v);
        } else
            mostrarMensajeAlerta("Datos incorrectos, vuelve a introducir los datos correctamente");
    }

    private void registrarNuevoUsuario(String nombreUsuario, String emailUsuario, String passwordUsuario) {
        DBController dbController = new DBController();
        boolean usuarioCreado = dbController.registrarUsuario(this.getBaseContext(), nombreUsuario, emailUsuario, passwordUsuario);

        if (usuarioCreado)
            mostrarMensajeAlerta("Usuario creado, bienvenido a InstaDAM " + nombreUsuario);
        else
            mostrarMensajeAlerta("Error en la creacion del usuario, el usuario ya existe");
    }

    private void mostrarMensajeAlerta(String mensaje) {
        textViewMensajeAlertaRegistro.setText(mensaje);
    }

    public void cargarActivityInicioSesion(View view) {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);
            finish();
        }, 1000);
    }
}
