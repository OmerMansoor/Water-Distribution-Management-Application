package com.example.water_distribution_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.water_distribution_final_project.utils.LocalPreferance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class Register_Activity extends AppCompatActivity
{


    LocationManager lm;
    LocationProvider lp;

    String mlatitude, mLongitude;


    FirebaseDatabase database = FirebaseDatabase.getInstance();

    UserModel userModel;

    Button Register,Sign_In;
    EditText Email,Password,ConfirmPassword,Name;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ProgressDialog progressDialog;

    DatabaseReference myRef = database.getReference("Water_Distribution");

    LocalPreferance localPreferance;

    int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        localPreferance = new LocalPreferance(getApplicationContext());

        int p = ContextCompat.checkSelfPermission(
                getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (p != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    1
            );
        } else
            {
            lm = (LocationManager) getSystemService(LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 1, new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    double lat = location.getLatitude();
                    double lng = location.getLongitude();
                    double spe = location.getSpeed();
                    double altitude = location.getAltitude();
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(lat, lng, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            Location loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            lp = lm.getProvider(LocationManager.NETWORK_PROVIDER);
            if (loc == null) {
                Toast.makeText(getApplicationContext(), "Location not found.", Toast.LENGTH_LONG).show();
            } else {
                final double lat = loc.getLatitude();
                final double lng = loc.getLongitude();
                mlatitude = String.valueOf(lat);
                mLongitude = String.valueOf(lng);
                lp.supportsAltitude();
                double spe = loc.getSpeed();
                double altitude = loc.getAltitude();

                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(lat, lng, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String cityName = addresses.get(0).getLocality();


            }
       }

            ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    flag=1;
                } else
                {
                    flag=0;
                }
            }
        });

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        //getting all ids
        Sign_In=findViewById(R.id.signin);
        Email=findViewById(R.id.emailtxt);
        Password=findViewById(R.id.passtxt);
        ConfirmPassword=findViewById(R.id.conpasstxt);
        Register=findViewById(R.id.Register_Button);
        Sign_In=findViewById(R.id.signin);
        Name=findViewById(R.id.name);

        progressDialog=new ProgressDialog(this);

        Sign_In.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                Intent i=new Intent(Register_Activity.this,MainActivity.class);
                startActivity(i);
            }
        });
        Register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PerformAuth();
            }

            private void PerformAuth()
            {
                String e_mail,password,confirmpass,UserName;
                e_mail=Email.getText().toString();
                password=Password.getText().toString();
                UserName=Name.getText().toString();
                confirmpass=ConfirmPassword.getText().toString();


                if(!e_mail.matches(emailPattern))
                {
                    Email.setError("Invalid Email");
                }
                else if(password.isEmpty())//password lenght to be added
                {
                    Password.setError("Please Enter Password");
                }
                else if(!password.equals(confirmpass))
                {
                    ConfirmPassword.setError("Passwords don't match");
                }
                else
                {
                    progressDialog.setMessage("Hang out there we are registering");
                    progressDialog.setTitle("Register");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    mAuth.createUserWithEmailAndPassword(e_mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                String Type;
                                if(flag==1)
                                {
                                    Type="vendor";
                                }
                                else
                                {
                                    Type="customer";
                                }
                                FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
                                userModel = new UserModel(fUser.getUid(),UserName,Type,e_mail,password,mlatitude,mLongitude );

                                myRef.child("User").child(fUser.getUid()).setValue(userModel);

                                progressDialog.dismiss();

                                localPreferance.putString("userID",userModel.getId());
                                localPreferance.putString("userName",userModel.getName());

                                if (userModel.type.equals("customer"))
                                {
                                    //go for customer screen
                                    Intent send_to_customer = new Intent(Register_Activity.this,UserDashBoard.class);
                                    send_to_customer.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(send_to_customer);
                                }
                                else
                                {
                                    //go for vendor screen
                                    Intent send_to_vendor=new Intent(Register_Activity.this,VendorDashBoard.class);
                                    send_to_vendor.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(send_to_vendor);
                                }

                                Toast.makeText(Register_Activity.this,"Registration Complete! Welcome to Paani",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(Register_Activity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
