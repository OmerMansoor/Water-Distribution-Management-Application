package com.example.water_distribution_final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.water_distribution_final_project.utils.LocalPreferance;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class AddStock extends AppCompatActivity
{
    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Water_Distribution").child("Bottle");
    RadioGroup radioGroup;
    String type;
    EditText Price,StockQuantity;
    LocalPreferance localPreferance;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);
        localPreferance = new LocalPreferance(getApplicationContext());
        StockQuantity=findViewById(R.id.tvStockQuantity);
        Price=findViewById(R.id.TvUnitPrice);
        radioGroup=findViewById(R.id.radioGroup);
        btnSubmit = findViewById(R.id.btn_submit);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId)
                {
                    case R.id.RadSmall:
                        type="small";
                        break;
                    case R.id.RadMedium:
                        type="medium";
                        break;
                    case R.id.RadLarge:
                        type="large";
                        break;
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.isEmpty() || StockQuantity.getText().toString().isEmpty() || Price.getText().toString().isEmpty()){

                }else{
                    addStockOnFireBase(type, StockQuantity.getText().toString(),Price.getText().toString());
                }
            }
        });
    }

    private void addStockOnFireBase(String type, String quant, String price) {
        final Date date = new Date();
        final String ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
        final SimpleDateFormat sdf = new SimpleDateFormat(ISO_FORMAT);

        final TimeZone utc = TimeZone.getTimeZone("UTC");
        sdf.setTimeZone(utc);
        System.out.println(sdf.format(date));
        Log.d("**DateTime",sdf.format(date));

        myRef.child(sdf.format(date)).setValue(new BottleMode(Integer.parseInt(price),type, localPreferance.getString("userID"),localPreferance.getString("userName"), Integer.parseInt(quant),sdf.format(date)));
        Toast.makeText(getApplicationContext(), "Stock Added Successfully", Toast.LENGTH_SHORT).show();
        finish();
    }
}