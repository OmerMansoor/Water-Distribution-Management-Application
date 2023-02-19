package com.example.water_distribution_final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.water_distribution_final_project.utils.LocalPreferance;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Water_Distribution");
    Button Login;
    EditText Email,Password;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    ProgressDialog progressDialog;

    LocalPreferance localPreferance;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        localPreferance = new LocalPreferance(getApplicationContext());

        mAuth=FirebaseAuth.getInstance();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button Sign_UP=findViewById(R.id.signup);

        Email=findViewById(R.id.m_email);
        Password=findViewById(R.id.m_password);
        Login=findViewById(R.id.login);
        progressDialog=new ProgressDialog(this);


        Sign_UP.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(MainActivity.this,Register_Activity.class);
                startActivity(i);
            }
        });
        Login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String e_mail,password;
                e_mail=Email.getText().toString();
                password=Password.getText().toString();



                if(!e_mail.matches(emailPattern))
                {
                    Email.setError("Invalid Email");
                }
                else if(password.isEmpty())//password lenght to be added
                {
                    Password.setError("Please Enter Password");
                }
                else
                {
                    progressDialog.setMessage("Wait till we Log you in");
                    progressDialog.setTitle("Login");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    mAuth.signInWithEmailAndPassword(e_mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {

                                //check user in db
                                mUser=mAuth.getCurrentUser();
                                myRef.child("User").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener()
                                {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if (snapshot.exists())
                                        {
                                            UserModel userModel = new UserModel(snapshot.child("id").getValue().toString(),snapshot.child("name").getValue().toString(),snapshot.child("type").getValue().toString(),snapshot.child("email").getValue().toString(),snapshot.child("password").getValue().toString(), snapshot.child("latitude").getValue().toString(), snapshot.child("longitude").getValue().toString());
                                            boolean test = userModel.getType().equals("customer");
                                            Log.d("*****","::"+userModel.getType()+"::");

                                            localPreferance.putString("userID",userModel.getId());
                                            localPreferance.putString("userName",userModel.getName());

                                            if (userModel.getType().equalsIgnoreCase("customer")==true) {
                                                //go for customer screen
                                                Intent send_to_customer=new Intent(MainActivity.this,UserDashBoard.class);
                                               // send_to_customer.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(send_to_customer);
                                            } else {
                                                //go for vendor screen
                                                Intent send_to_vendor=new Intent(MainActivity.this,VendorDashBoard.class);
                                               // send_to_vendor.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(send_to_vendor);
                                            }

                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error)
                                    {

                                    }
                                });


                                progressDialog.dismiss();
                              //  Intent send=new Intent(MainActivity.this,Home_Activity.class);
                                //send.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                //startActivity(send);
                                Toast.makeText(MainActivity.this,"Login Complete! Welcome to Paani",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(MainActivity.this,""+task.getException(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });


    }
}