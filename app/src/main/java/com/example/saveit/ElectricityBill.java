package com.example.saveit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;

public class ElectricityBill extends AppCompatActivity {
    DrawerLayout drawerLayout;
    TextView tname,temail,today;
    String s1[], s2[];
    int images[] ={R.drawable.bills,R.drawable.bills,R.drawable.bills,R.drawable.bills,R.drawable.bills,R.drawable.bills,R.drawable.bills,R.drawable.bills,R.drawable.bills,
            R.drawable.bills,R.drawable.bills,R.drawable.bills};
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity_bill);
        drawerLayout = findViewById(R.id.drawer_layout);

        today= findViewById(R.id.today_date);
        String date= LocalDate.now().toString();
        today.setText(date);
        tname = findViewById(R.id.nav_name);
        temail = findViewById(R.id.nav_email);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getEmail();
        temail.setText(uid);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.orderByChild("email").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String name = snapshot.child("fullName").getValue().toString();
                    tname.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        recyclerView=findViewById(R.id.recycler_view);

        s1=getResources().getStringArray(R.array.electricity_bills);
        s2=getResources().getStringArray(R.array.electricity_bills_description);

        RecyclerViewAdapter myAdapter=new RecyclerViewAdapter(this,s1,s2,images);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    public void ClickElectricity(View view){
        recreate();
    }
    public void ClickWater(View view){
        MainActivity.redirectActivity(this,WaterBill.class);
    }
    public void ClickGas(View view){
        MainActivity.redirectActivity(this,GasBill.class);
    }

    public void ClickMenu(View view){
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view){
        MainActivity.closeDrawer(drawerLayout);
    }
    public void ClickHome(View view){
        MainActivity.redirectActivity(this,MainActivity.class);
    }
    public void ClickBills(View view){
        MainActivity.redirectActivity(this,Bills.class);
    }
    public void ClickAboutUs(View view){
        MainActivity.redirectActivity(this,AboutUs.class);

    }
    public void ClickSettings(View view){
        MainActivity.redirectActivity( this,Preference.class);
    }
    public void ClickLogout(View view){
        MainActivity.logout(this);
    }

    @Override
    protected void onPause(){
        super.onPause();
        MainActivity.closeDrawer(drawerLayout);
    }
}