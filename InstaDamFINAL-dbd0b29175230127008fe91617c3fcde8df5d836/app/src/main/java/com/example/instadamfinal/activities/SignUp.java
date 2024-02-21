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
import com.google.firebase.Firebase;

public class SignUp extends AppCompatActivity {

    private EditText editTextUserName;
    private EditText editTextEmailAddress;
    private EditText editTextPassword;
    private Button sendButton;
    private Button returnButton;
    private TextView messageAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);

        initializeViews();
        setOnClickListeners();
    }

    private void initializeViews() {
        editTextUserName = findViewById(R.id.editTextUserName);
        editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextPassword);
        sendButton = findViewById(R.id.sendButton);
        returnButton = findViewById(R.id.signUpButton);
        messageAlert = findViewById(R.id.messageAlert);
    }

    private void setOnClickListeners() {
        sendButton.setOnClickListener(this::sendForm);
        returnButton.setOnClickListener(this::openSignInActivity);
    }

    private void sendForm(View v) {
        String userName = editTextUserName.getText().toString();
        String email = editTextEmailAddress.getText().toString();
        String password = editTextPassword.getText().toString();

        EmailController emailController = new EmailController();
        PasswordController passwordController = new PasswordController();

        if (!userName.isEmpty() && emailController.checkEmail(email) && passwordController.checkPassword(password)) {
            registerUser(userName, email, password);
            openSignInActivity(v);
        } else {
            showMessageAlert("Datos incorrectos");
        }
    }

    private void registerUser(String userName, String email, String password) {
        DBController dbController = new DBController();
        boolean create = dbController.registerUser(this.getBaseContext(), userName, email, password);

        if (create) {
            showMessageAlert("User created, welcome");

        } else {
            showMessageAlert("Can't create the user, log in please");
        }
    }

    private void showMessageAlert(String message) {
        messageAlert.setText(message);
    }

    public void openSignInActivity(View view) {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, SignIn.class);
            startActivity(intent);
            finish();
        }, 1000);
    }
}
