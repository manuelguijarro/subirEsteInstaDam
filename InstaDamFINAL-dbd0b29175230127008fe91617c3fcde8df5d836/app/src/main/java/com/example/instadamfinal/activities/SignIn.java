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
    private EditText editTextEmailAddress;
    private EditText editTextPassword;
    private Button sendButton;
    private Button signUpButton;
    private TextView messageAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initializeViews();
        setOnClickListeners();
    }

    private void initializeViews() {
        editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextPassword);
        sendButton = findViewById(R.id.sendButton);
        signUpButton = findViewById(R.id.signUpButton);
        messageAlert = findViewById(R.id.messageAlert);
    }

    private void setOnClickListeners() {
        sendButton.setOnClickListener(this::sendForm);
        signUpButton.setOnClickListener(this::openSignUpActivity);
    }

    private void sendForm(View v) {
        String email = editTextEmailAddress.getText().toString();
        String password = editTextPassword.getText().toString();

        EmailController emailController = new EmailController();
        PasswordController passwordController = new PasswordController();

        if (emailController.checkEmail(email) && passwordController.checkPassword(password)) {
            loginUser(email, password);
        } else {
            showMessageAlert("Datos incorrectos");
        }
    }

    private void loginUser(String email, String password) {
        DBController dbController = new DBController();
        String userName = dbController.loginUser(this.getBaseContext(), email, password);

        if (userName != null) {
            showMessageAlert("Welcome to instaDAM");
            openMainActivity(userName);
        } else {
            showMessageAlert("Can't login, create new account or edit the input.");
        }
    }

    private void showMessageAlert(String message) {
        messageAlert.setText(message);
    }

    public void openSignUpActivity(View view) {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, SignUp.class);
            startActivity(intent);
            finish();
        }, 1000);
    }

    public void openMainActivity(String userName) {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("key", userName);
            startActivity(intent);
            finish();
        }, 1000);
    }
}
