package com.example.servicehub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

public class UserRoleActivity extends AppCompatActivity {


    ImageView clientImageView, serviceProviderImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_role);

        clientImageView = findViewById(R.id.clientImageView);
        serviceProviderImageView = findViewById(R.id.serviceProviderImageView);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        clientImageView.getLayoutParams().height = 400;
        clientImageView.getLayoutParams().width = width/2-120;
        serviceProviderImageView.getLayoutParams().height = 400;
        serviceProviderImageView.getLayoutParams().width = width/2-120;

        clientImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserRoleActivity.this, SignupActivity.class);
                i.putExtra("role","Client");
                startActivity(i);
            }
        });

        serviceProviderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserRoleActivity.this, SignupActivity.class);
                i.putExtra("role","Service Provider");
                startActivity(i);
            }
        });
    }
}