package com.example.water_distribution_final_project.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.water_distribution_final_project.BottleMode;
import com.example.water_distribution_final_project.R;

public class OrderCustomDialog extends Dialog implements
        android.view.View.OnClickListener {

    public Activity c;
    public Dialog d;
    public Button yes, no, btn_add, btn_minus;
    public TextView tvTotalPrice, tvQuantity;
    public BottleMode item;

    public OrderCustomDialog(Activity a, BottleMode item) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.item = item;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.order_dialog);
        //textview

        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        btn_add = findViewById(R.id.btn_add);
        btn_minus = findViewById(R.id.btnMinus);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvQuantity = findViewById(R.id.tvQuantity);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        btn_add.setOnClickListener(this);
        btn_minus.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_yes:
                c.finish();
                break;
            case R.id.btn_no:
                dismiss();
                break;
            case R.id.btn_add:
                incrementCount();
                break;
            case R.id.btnMinus:
                decrementCount();
                break;
            default:
                break;
        }
    }

    private void decrementCount() {
        String quant = tvQuantity.getText().toString();
        int quantity = Integer.parseInt(quant);
        if (quantity== 0){

        }else {
            quantity = quantity-1;
        }
        int totalPrice = quantity*item.getPrice();
        tvQuantity.setText(String.valueOf(quantity));
        tvTotalPrice.setText("Price: "+ String.valueOf(totalPrice));
    }

    private void incrementCount() {
        String quant = tvQuantity.getText().toString();
        int quantity = Integer.parseInt(quant);
        if (quantity== item.getStockQuantity()){

        }else {
            quantity = quantity+1;
        }
        int totalPrice = quantity*item.getPrice();
        tvQuantity.setText(String.valueOf(quantity));
        tvTotalPrice.setText("Price: "+ String.valueOf(totalPrice));
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        setCancelable(false);
    }
}
