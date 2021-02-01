package com.example.servicehub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.servicehub.R.drawable.service_provider;



public class SignupActivity extends AppCompatActivity {

    EditText firstNameEdt, lastNameEdt, emailEdt, passwordEdt, confirmPasswordEdt, phoneNumberEdt, aptEdt, addressEdt, cityEdt, provinceEdt, postalCodeEdt;
    ImageView titleImageView,serviceListSpinnerImageView;
    Spinner serviceListSpinner;
    ConstraintLayout lineView2;
    Button nextButton;
    String[] serviceList = {"Select Service","Plumber","Mechanic","painter"};
    ArrayAdapter<String> serviceAdapter;
    Intent intent;
    String role;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String[] daysData = {};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        titleImageView = findViewById(R.id.titleImageView);
        serviceListSpinner = findViewById(R.id.serviceListSpinner);
        serviceListSpinnerImageView = findViewById(R.id.serviceListSpinnerImageView);
        lineView2 = findViewById(R.id.lineView2);
        firstNameEdt = findViewById(R.id.firstNameTextField);
        lastNameEdt = findViewById(R.id.lastNameTextField);
        emailEdt = findViewById(R.id.emailTextField);
        passwordEdt = findViewById(R.id.passwordTextField);
        confirmPasswordEdt = findViewById(R.id.confirmPasswordTextField);
        phoneNumberEdt = findViewById(R.id.phoneNumberTextField);
        aptEdt = findViewById(R.id.aptNumTextField);
        addressEdt = findViewById(R.id.addressLineTextField);
        cityEdt = findViewById(R.id.cityTextField);
        provinceEdt = findViewById(R.id.provinceTextField);
        postalCodeEdt = findViewById(R.id.postalCodeTextField);
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

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(firstNameEdt.getText().toString().matches(""))){
                    if(!(lastNameEdt.getText().toString().matches(""))){
                        if(!(emailEdt.getText().toString().matches(""))){
                            if(!(passwordEdt.getText().toString().matches(""))){
                                if(!(confirmPasswordEdt.getText().toString().matches(""))){
                                    if(!(phoneNumberEdt.getText().toString().matches(""))){
                                        if(!(aptEdt.getText().toString().matches(""))){
                                            if(!(addressEdt.getText().toString().matches(""))){
                                                if(!(cityEdt.getText().toString().matches(""))){
                                                    if(!(provinceEdt.getText().toString().matches(""))){
                                                        if(!(postalCodeEdt.getText().toString().matches(""))){
                                                            if(emailEdt.getText().toString().matches(emailPattern)){
                                                                if(passwordEdt.getText().toString().length()>=8 &&isValidPassword(passwordEdt.getText().toString())){
                                                                    if(confirmPasswordEdt.getText().toString().length()>=8 &&isValidPassword(passwordEdt.getText().toString())){
                                                                        if(passwordEdt.getText().toString().equals(confirmPasswordEdt.getText().toString())){
                                                                            if(phoneNumberEdt.getText().toString().length()==10){
                                                                                if(role.equals("Client")){
                                                                                    //                    Intent i = new Intent(SignupActivity.this, AddAvailabilityActivity.class);
                                                                                    //                    startActivity(i);
                                                                                    String address = aptEdt.getText().toString() + " " + addressEdt.getText().toString() + " " + cityEdt.getText().toString() + ", " + provinceEdt.getText().toString() + " " + postalCodeEdt.getText().toString();
                                                                                    registerUser(firstNameEdt.getText().toString(),lastNameEdt.getText().toString(),phoneNumberEdt.getText().toString(),address,emailEdt.getText().toString(),passwordEdt.getText().toString());
                                                                                }else{
                                                                                    if(serviceListSpinner.getSelectedItem().toString().equals("Select Service")){
//                                                                                        Toast.makeText(getApplicationContext(),"Select appropriate service",Toast.LENGTH_SHORT).show();
//                                                                                        Log.e("TAG", "Select appropriate service");
                                                                                        serviceListSpinner.performClick();
                                                                                    }else {
                                                                                        Intent i = new Intent(SignupActivity.this, AddAvailabilityActivity.class);
                                                                                        startActivity(i);
                                                                                    }
                                                                                }
                                                                            }else{
                                                                                phoneNumberEdt.setError("Enter valid phone number");
                                                                            }
                                                                        }else{
                                                                            confirmPasswordEdt.setError("Both password should be same");
                                                                        }
                                                                    }else{
                                                                        confirmPasswordEdt.setError("Confirm Password should be at least 8 character and must contain alpha (at least on capital character) numeric and special character");
                                                                    }
                                                                }else{
                                                                    passwordEdt.setError("Password should be at least 8 character and must contain alpha (at least on capital character) numeric and special character");
                                                                }
                                                            }else{
                                                                emailEdt.setError("Please enter valid email");
                                                            }
                                                        }else{
                                                            postalCodeEdt.setError("Please enter postal code");
                                                        }
                                                    }else{
                                                        provinceEdt.setError("Please enter province");
                                                    }
                                                }else{
                                                    cityEdt.setError("Please enter city");
                                                }
                                            }else{
                                                addressEdt.setError("Please enter address line");
                                            }
                                        }else{
                                            aptEdt.setError("Please enter apartment or house number");
                                        }
                                    }else{
                                        phoneNumberEdt.setError("Please enter phone number");
                                    }
                                }else{
                                    confirmPasswordEdt.setError("Please enter confirm password");
                                }
                            }else{
                                passwordEdt.setError("Please enter password");
                            }
                        }else{
                            emailEdt.setError("Please enter email address");
                        }
                    }else{
                        lastNameEdt.setError("Please enter last name");
                    }
                }else{
                    firstNameEdt.setError("Please enter first name");
                }



//                if(role.equals("Client")){
////                    Intent i = new Intent(SignupActivity.this, AddAvailabilityActivity.class);
////                    startActivity(i);
//                }else{
//                    Intent i = new Intent(SignupActivity.this, AddAvailabilityActivity.class);
//                    startActivity(i);
//                }
            }
        });

    }

    private void registerUser(String firstname, String lastname, String contactnumber, String address, String email, String password) {
        String URL = "http://servicehub.kunjtechs.com/api/v1/auth/login";
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("role", "Customer");
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("contactNumber", contactnumber);
        params.put("address", address);
        params.put("email", email);
        params.put("password", password);
        params.put("serviceName", "");
        params.put("serviceid", null);
        params.put("daysdata", String.valueOf(daysData));
        params.put("basePrice", String.valueOf(0.0));
        Log.d("Params", String.valueOf(params));
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, URL, new JSONObject(params), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", String.valueOf(response));
                try {
                    Log.d("Token", response.getString("token"));
                    Log.d("Token", String.valueOf(response.getBoolean("success")));
                    if(response.getBoolean("success") == true){
                        Intent i = new Intent(SignupActivity.this, HomeScreenActivity.class);
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