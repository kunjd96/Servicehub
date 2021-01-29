package com.example.servicehub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    EditText emailEdt, passwordEdt;
    Button loginButton;
    TextView forgotPasswordTV, createAccountTV;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEdt = findViewById(R.id.emailTextField);
        passwordEdt = findViewById(R.id.passwordTextField);
        loginButton = findViewById(R.id.loginButton);
        createAccountTV = findViewById(R.id.createAccountTextView);
        forgotPasswordTV = findViewById(R.id.forgotPasswordTextView);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(emailEdt.getText().toString().matches(""))) {
                    if(!(passwordEdt.getText().toString().matches(""))) {
                        if(emailEdt.getText().toString().matches(emailPattern)){
                            if(passwordEdt.getText().toString().length()>=8 &&isValidPassword(passwordEdt.getText().toString())){

                            }else{
                                passwordEdt.setError("Password should be at least 8 character and must contain alpha (at least on capital character) numeric and special character");
                            }
                        }else{
                            emailEdt.setError("Please enter valid email");
                        }
                    }else {
                        passwordEdt.setError("Please enter password");
                    }
                }else {
                    emailEdt.setError("Please enter email address");
                }
            }
        });

        createAccountTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
            }
        });

        forgotPasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}