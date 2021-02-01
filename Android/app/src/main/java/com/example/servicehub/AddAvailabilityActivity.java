package com.example.servicehub;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

class availability {
    String dayName, startTime, endTime;

    public availability(String dayName, String startTime, String endTime) {
        this.dayName = dayName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "availability{" +
                "dayName='" + dayName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}

class userData{
    String role, firstname, lastname, contactNumber, address, email, password, basePrice, serviceid, serviceName;
    Object daysdata;

    public userData(String role, String firstname, String lastname, String contactNumber, String address, String email, String password, String basePrice, String serviceid, String serviceName, Object daysdata) {
        this.role = role;
        this.firstname = firstname;
        this.lastname = lastname;
        this.contactNumber = contactNumber;
        this.address = address;
        this.email = email;
        this.password = password;
        this.basePrice = basePrice;
        this.serviceid = serviceid;
        this.serviceName = serviceName;
        this.daysdata = daysdata;
    }

    @Override
    public String toString() {
        return "{" +
                "role='" + role + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", basePrice='" + basePrice + '\'' +
                ", serviceid='" + serviceid + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", daysdata=" + daysdata +
                '}';
    }

    //    {
//        "role" : "ServiceProvider",
//            "firstname":"kunj",
//            "lastname":"patel",
//            "contactNumber":"12344567089",
//            "address":"4555 Bonavista Avenue, Montréal, QC H3W 2C7",
//            "email":"kunjd96@gmail.com",
//            "password":"kunj123",
//            "basePrice" : "14.5",
//            "serviceid" : "6011c68dd4a0f85390bdada6",
//            "serviceName" : "Computer Technician",
//            "daysdata": [{
//        "dayName" : "Monday",
//                "startTime" : "2021-01-26T22:47:19.406+00:00",
//                "endTime" : "2021-01-26T06:47:19.406+00:00"
//    },
//        {
//            "dayName" : "Tuesday",
//                "startTime" : "2021-01-26T22:47:19.406+00:00",
//                "endTime" : "2021-01-26T06:47:19.406+00:00"
//        }]
//    }
}

public class AddAvailabilityActivity extends AppCompatActivity {

    EditText mondayStartTextField, mondayEndTextField, tuesdayStartTextField, tuesdayEndTextField, wednesdayStartTextField, wednesdayEndTextField, thursdayStartTextField, thursdayEndTextField, fridayStartTextField, fridayEndTextField, saturdayStartTextField, saturdayEndTextField, sundayStartTextField, sundayEndTextField;
    Button signUpButton;

//    availability[] availabilities = {new availability("Monday","12:00","14:00")};
    availability[] availabilities = new availability[7];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_availability);

        mondayStartTextField = findViewById(R.id.mondayStartTextField);
        mondayEndTextField = findViewById(R.id.mondayEndTextField);
        tuesdayStartTextField = findViewById(R.id.tuesdayStartTextField);
        tuesdayEndTextField = findViewById(R.id.tuesdayEndTextField);
        wednesdayStartTextField = findViewById(R.id.wednesdayStartTextField);
        wednesdayEndTextField = findViewById(R.id.wednesdayEndTextField);
        thursdayStartTextField = findViewById(R.id.thursdayStartTextField);
        thursdayEndTextField = findViewById(R.id.thursdayEndTextField);
        fridayStartTextField = findViewById(R.id.fridayStartTextField);
        fridayEndTextField = findViewById(R.id.fridayEndTextField);
        saturdayStartTextField = findViewById(R.id.saturdayStartTextField);
        saturdayEndTextField = findViewById(R.id.saturdayEndTextField);
        sundayStartTextField = findViewById(R.id.sundayStartTextField);
        sundayEndTextField = findViewById(R.id.sundayEndTextField);
        signUpButton = findViewById(R.id.signUpButton);


        mondayStartTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mondayStartTextField.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        mondayEndTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        mondayEndTextField.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        tuesdayStartTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tuesdayStartTextField.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        tuesdayEndTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        tuesdayEndTextField.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        wednesdayStartTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        wednesdayStartTextField.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        wednesdayEndTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        wednesdayEndTextField.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        thursdayStartTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        thursdayStartTextField.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        thursdayEndTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        thursdayEndTextField.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        fridayStartTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        fridayStartTextField.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        fridayEndTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        fridayEndTextField.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        saturdayStartTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        saturdayStartTextField.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        saturdayEndTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        saturdayEndTextField.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        sundayStartTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        sundayStartTextField.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        sundayEndTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddAvailabilityActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        sundayEndTextField.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!(mondayStartTextField.getText().toString().matches(""))){
//                    if(!(mondayEndTextField.getText().toString().matches(""))){
//                        if(!(tuesdayStartTextField.getText().toString().matches(""))){
//                            if(!(tuesdayEndTextField.getText().toString().matches(""))){
//                                if(!(wednesdayStartTextField.getText().toString().matches(""))){
//                                    if(!(wednesdayEndTextField.getText().toString().matches(""))){
//                                        if(!(thursdayStartTextField.getText().toString().matches(""))){
//                                            if(!(thursdayEndTextField.getText().toString().matches(""))){
//                                                if(!(fridayStartTextField.getText().toString().matches(""))){
//                                                    if(!(fridayEndTextField.getText().toString().matches(""))){
//                                                        if(!(saturdayStartTextField.getText().toString().matches(""))){
//                                                            if(!(saturdayEndTextField.getText().toString().matches(""))){
//                                                                if(!(sundayStartTextField.getText().toString().matches(""))){
//                                                                    if(!(sundayEndTextField.getText().toString().matches(""))){
//                                                                        Log.d("availabilities", String.valueOf(availabilities[0]));
//                                                                    }else{
//                                                                        sundayEndTextField.setError("Please enter availability for Sunday");
//                                                                    }
//                                                                }else{
//                                                                    sundayStartTextField.setError("Please enter availability for Sunday");
//                                                                }
//                                                            }else{
//                                                                saturdayEndTextField.setError("Please enter availability for Saturday");
//                                                            }
//                                                        }else{
//                                                            saturdayStartTextField.setError("Please enter availability for Saturday");
//                                                        }
//                                                    }else{
//                                                        fridayEndTextField.setError("Please enter availability for Friday");
//                                                    }
//                                                }else{
//                                                    fridayStartTextField.setError("Please enter availability for Friday");
//                                                }
//                                            }else{
//                                                thursdayEndTextField.setError("Please enter availability for Thursday");
//                                            }
//                                        }else{
//                                            thursdayStartTextField.setError("Please enter availability for Thursday");
//                                        }
//                                    }else{
//                                        wednesdayEndTextField.setError("Please enter availability for Wednesday");
//                                    }
//                                }else{
//                                    wednesdayStartTextField.setError("Please enter availability for Wednesday");
//                                }
//                            }else{
//                                tuesdayEndTextField.setError("Please enter availability for Tuesday");
//                            }
//                        }else{
//                            tuesdayStartTextField.setError("Please enter availability for Tuesday");
//                        }
//                    }else{
//                        mondayEndTextField.setError("Please enter availability for Monday");
//                    }
//                }else{
//                    mondayStartTextField.setError("Please enter availability for Monday");
//                }



//                if(mondayStartTextField.getText().toString().matches("") || mondayEndTextField.getText().toString().matches("") || tuesdayStartTextField.getText().toString().matches("") || tuesdayEndTextField.getText().toString().matches("") || wednesdayStartTextField.getText().toString().matches("") || wednesdayEndTextField.getText().toString().matches("") || thursdayStartTextField.getText().toString().matches("") || thursdayEndTextField.getText().toString().matches("") || fridayStartTextField.getText().toString().matches("") || fridayEndTextField.getText().toString().matches("") || saturdayStartTextField.getText().toString().matches("") || saturdayEndTextField.getText().toString().matches("") || sundayStartTextField.getText().toString().matches("") || sundayEndTextField.getText().toString().matches("")){
//                    mondayStartTextField.setError("Please enter availability for one day at least");
//                }else{
//
//                }



                availabilities[0] = new availability("Monday",mondayStartTextField.getText().toString(),mondayEndTextField.getText().toString());
                availabilities[1] = new availability("Tuesday",tuesdayStartTextField.getText().toString(),tuesdayEndTextField.getText().toString());
                availabilities[2] = new availability("Wednesday",wednesdayStartTextField.getText().toString(),wednesdayEndTextField.getText().toString());
                availabilities[3] = new availability("Thursday",thursdayStartTextField.getText().toString(),thursdayEndTextField.getText().toString());
                availabilities[4] = new availability("Friday",fridayStartTextField.getText().toString(),fridayEndTextField.getText().toString());
                availabilities[5] = new availability("Saturday",saturdayStartTextField.getText().toString(),saturdayEndTextField.getText().toString());
                availabilities[6] = new availability("Sunday",sundayStartTextField.getText().toString(),sundayEndTextField.getText().toString());

                userData newUser = new userData("ServiceProvider","Arpit", "Mendpara", "1231231231", "406 6525 cote saint luc, Montreal QC H4V1G5", "arpit@gmail.com","Arpit@123", "14.0", "6011c68dd4a0f85390bdada6", "Computer Technician", availabilities);
                Log.d("New User", String.valueOf(newUser));
//                registerUser(newUser);

            }
        });
    }

    private void registerUser(Object newUser) {
        String URL = "http://servicehub.kunjtechs.com/api/v1/auth/login";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.POST, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", String.valueOf(response));
                try {
                    Log.d("Token", response.getString("token"));
                    Log.d("Token", String.valueOf(response.getBoolean("success")));
                    if(response.getBoolean("success") == true){
                        Intent i = new Intent(AddAvailabilityActivity.this, MainActivity.class);
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

}