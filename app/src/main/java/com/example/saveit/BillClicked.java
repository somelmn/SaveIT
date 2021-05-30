package com.example.saveit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BillClicked extends AppCompatActivity {
    ImageView imageView;
    int billImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_clicked);
        imageView=findViewById(R.id.billImage);
        getData();
        setData();
    }
    public void getData(){
        if(getIntent().hasExtra("bill_img")){
            billImage=getIntent().getIntExtra("bill_img",1);

        }else{
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();

        }
    }

    public void setData(){
        imageView.setImageResource(billImage);

    }
}