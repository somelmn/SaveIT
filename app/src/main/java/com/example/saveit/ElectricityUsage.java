package com.example.saveit;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ElectricityUsage extends AppCompatActivity {
    DrawerLayout drawerLayout;
    TextView tname,temail;

    private static String TAG ="ElectricityUsage";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electricity_usage);
        drawerLayout = findViewById(R.id.drawer_layout);

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


        BarChart barChart = findViewById(R.id.graph);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        List<String> xAxisValues = new ArrayList<>(Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat","Sun"));
        List<BarEntry> usageEntries = getUsageEntries();

        dataSets = new ArrayList<>();
        BarDataSet set1;

        set1 = new BarDataSet(usageEntries, "Energy Usage");
        set1.setColors(ColorTemplate.MATERIAL_COLORS);
        set1.setValueTextColor(Color.BLACK);
        set1.setValueTextSize(16f);
        dataSets.add(set1);


        //String setter in x-Axis
        barChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));

        BarData data = new BarData(dataSets);
        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.getDescription().setText("Energy Usage per day!");
        barChart.animateY(2000);
    }

    private List<BarEntry> getUsageEntries() {
        ArrayList<BarEntry> energyUsage = new ArrayList<>();

        energyUsage.add(new BarEntry(0, (float) getDailyUsage()));
        energyUsage.add(new BarEntry(1, (float) getDailyUsage()));
        energyUsage.add(new BarEntry(2, (float) getDailyUsage()));
        energyUsage.add(new BarEntry(3, (float) getDailyUsage()));
        energyUsage.add(new BarEntry(4, (float) getDailyUsage()));
        energyUsage.add(new BarEntry(5, (float) getDailyUsage()));
        energyUsage.add(new BarEntry(6, (float) getDailyUsage()));

        return energyUsage.subList(0, 6);
    }

    public double getDailyUsage() {
        double rangeMin = 6.3;
        double rangeMax = 9.3;

        Random r = new Random();
        double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        return randomValue;
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
    public void ClickSavings(View view){
        MainActivity.redirectActivity( this,Savings.class);
    }
    public void ClickAboutUs(View view){
        MainActivity.redirectActivity( this,AboutUs.class);

    }
    public void ClickSettings(View view){
        MainActivity.redirectActivity( this,Settings.class);
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