package com.example.servicehub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
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
//                            if(passwordEdt.getText().toString().length()>=8 &&isValidPassword(passwordEdt.getText().toString())){
                            loginUser(emailEdt.getText().toString(),passwordEdt.getText().toString());
//                            }else{
//                                passwordEdt.setError("Password should be at least 8 character and must contain alpha (at least on capital character) numeric and special character");
//                            }
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
                Intent i = new Intent(LoginActivity.this, UserRoleActivity.class);
                startActivity(i);
            }
        });

        forgotPasswordTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void loginUser(String email, String password) {
        String URL = "http://servicehub.kunjtechs.com/api/v1/auth/login";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", String.valueOf(response));
                try {
                    Log.d("Token", response.getString("token"));
                    Log.d("Token", String.valueOf(response.getBoolean("success")));
                    if(response.getBoolean("success") == true){
                        Intent i = new Intent(LoginActivity.this, HomeScreenActivity.class);
                        finish();
                        startActivity(i);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", error.getMessage());

            }
        });

        requestQueue.add(objectRequest);
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