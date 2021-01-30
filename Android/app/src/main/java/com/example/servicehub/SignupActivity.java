package com.example.servicehub;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

import static com.example.servicehub.R.drawable.service_provider;

public class SignupActivity extends AppCompatActivity {

    ImageView titleImageView,serviceListSpinnerImageView;
    Spinner serviceListSpinner;
    ConstraintLayout lineView2;
    Button nextButton;
    String[] serviceList = {"Select Service","Plumber","Mechanic","painter"};
    ArrayAdapter<String> serviceAdapter;
    Intent intent;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        titleImageView = findViewById(R.id.titleImageView);
        serviceListSpinner = findViewById(R.id.serviceListSpinner);
        serviceListSpinnerImageView = findViewById(R.id.serviceListSpinnerImageView);
        lineView2 = findViewById(R.id.lineView2);
        nextButton = findViewById(R.id.nextButton);
        intent = getIntent();
        role = intent.getStringExtra("role");

        if(role.equals("Client")){
            serviceListSpinner.setVisibility(View.GONE);
            serviceListSpinnerImageView.setVisibility(View.GONE);
            lineView2.setVisibility(View.GONE);
            titleImageView.setImageResource(R.drawable.client);
            nextButton.setText("Sign up");
        }else{
            serviceListSpinner.setVisibility(View.VISIBLE);
            serviceListSpinnerImageView.setVisibility(View.VISIBLE);
            lineView2.setVisibility(View.VISIBLE);
            titleImageView.setImageResource(service_provider);
            nextButton.setText("Next");
            serviceListSpinner.setDropDownWidth(serviceListSpinner.getLayoutParams().width);
            serviceAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,serviceList);
            serviceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            serviceListSpinner.setAdapter(serviceAdapter);
        }


    }
}