
package com.example.water_distribution_final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.water_distribution_final_project.dialog.OrderCustomDialog;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;


public class VendorDashBoard extends AppCompatActivity
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference myRef = database.getReference("Water_Distribution").child("Bottle");

    Button AddStock;
    private RecyclerView courseRV;

    BottleListAdapter mAdapter;

    // Arraylist for storing data
    private ArrayList<BottleMode> mDataBottles;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor_dash_board);


        AddStock=findViewById(R.id.AddStock);
        AddStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send=new Intent(VendorDashBoard.this, AddStock.class);
                startActivity(send);
            }
        });
        courseRV = findViewById(R.id.idRVCourse);

        mDataBottles = new ArrayList<>();

        mAdapter = new BottleListAdapter(this, mDataBottles);
        mAdapter.setOnItemClickListener(new BottleListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getApplicationContext(),mDataBottles.get(position).getOwner_name(),Toast.LENGTH_SHORT).show();
                //Alert Dialog with two buttons (order now and Cancel)
                OrderCustomDialog dialog = new OrderCustomDialog(VendorDashBoard.this,mDataBottles.get(position));
                dialog.show();

            }
        });

        getDataFromFirebase();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        courseRV.setLayoutManager(linearLayoutManager);
        courseRV.setAdapter(mAdapter);
    }
    private void getDataFromFirebase()
    {

        myRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            mDataBottles.add(new BottleMode(Integer.parseInt(dsp.child("price").getValue().toString()), dsp.child("type").getValue().toString(),dsp.child("owner_id").getValue().toString(),dsp.child("owner_name").getValue().toString(), Integer.parseInt(dsp.child("stockQuantity").getValue().toString()),dsp.child("created_at").getValue().toString())); //add result into array list
                        }
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {

                    }
                });

    }
}