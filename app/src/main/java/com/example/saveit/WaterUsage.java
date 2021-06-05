package com.example.saveit;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WaterUsage extends AppCompatActivity {
    DrawerLayout drawerLayout;
    TextView tname,temail;

    private static String TAG ="WaterUsage";

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    private List<Double> monthlyUsage = new ArrayList<>(Arrays.asList(0.57, 0.61, 0.61, 0.58, 0.59,0.61, 0.57, 0.60, 0.57, 0.58, 0.60, 0.58, 0.60, 0.62, 0.59, 0.59, 0.59, 0.57, 0.59, 0.59, 0.57, 0.57, 0.59, 0.57, 0.60, 0.59, 0.56, 0.59, 0.61, 0.593));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_usage);
        drawerLayout = findViewById(R.id.water_drawer_layout);

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

        BarChart barChart = findViewById(R.id.waterGraph);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        List<String> xAxisValues = new ArrayList<>(Arrays.asList("Mon", "Tue", "Wed", "Thu", "Fri", "Sat","Sun"));
        List<BarEntry> usageEntries = getUsageEntries();

        dataSets = new ArrayList<>();
        BarDataSet set1;

        set1 = new BarDataSet(usageEntries, "Water Usage");
        set1.setColors(ColorTemplate.MATERIAL_COLORS);
        set1.setValueTextSize(16f);
        dataSets.add(set1);

        //String setter in x-Axis
        barChart.getXAxis().setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));

        BarData data = new BarData(dataSets);
        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.getDescription().setText("Water Usage per day!");
        barChart.animateY(2000);

        updateWeeklyTextView();
        updateMonthlyTextView();
        updateCostTextView();

        updateEstimatedCostTextView();
        updateEstimatedTextView();

        int defaultNightMode = AppCompatDelegate.getDefaultNightMode();
        if(defaultNightMode == AppCompatDelegate.MODE_NIGHT_YES){
            LinearLayout li=(LinearLayout)findViewById(R.id.nav_drawer);
            li.setBackgroundResource(R.color.grey);
            set1.setValueTextColor(Color.WHITE);
            barChart.getXAxis().setTextColor(Color.WHITE);
            set1.setBarBorderColor(Color.WHITE);
            barChart.getDescription().setTextColor(Color.WHITE);
            LinearLayout toolbar=(LinearLayout)findViewById(R.id.toolbar);
            toolbar.setBackgroundResource(R.color.colorTextPrimary);

        }
        else{
            LinearLayout li=(LinearLayout)findViewById(R.id.nav_drawer);
            li.setBackgroundResource(R.color.white);
            set1.setValueTextColor(Color.BLACK);
            barChart.getXAxis().setTextColor(Color.BLACK);
            set1.setBarBorderColor(Color.BLACK);
            barChart.getDescription().setTextColor(Color.BLACK);
        }

    }

    private List<BarEntry> getUsageEntries() {
        ArrayList<BarEntry> energyUsage = new ArrayList<>();

        energyUsage.add(new BarEntry(0, (float) getDailyUsage(7)));
        energyUsage.add(new BarEntry(1, (float) getDailyUsage(6)));
        energyUsage.add(new BarEntry(2, (float) getDailyUsage(5)));
        energyUsage.add(new BarEntry(3, (float) getDailyUsage(4)));
        energyUsage.add(new BarEntry(4, (float) getDailyUsage(3)));
        energyUsage.add(new BarEntry(5, (float) getDailyUsage(2)));
        energyUsage.add(new BarEntry(6, (float) getDailyUsage(1)));

        return energyUsage.subList(0, 7);
    }

    public double getDailyUsage(int i) {

        double daily = monthlyUsage.get(monthlyUsage.size() - i);

        return daily;

    }

    public double getWeeklyUsage() {

        double weekly = 0;
        for (int i=1; i<=7; i++) {
            weekly = weekly + monthlyUsage.get(monthlyUsage.size() - i);
        }

        return weekly;

    }

    public double getMonthlyUsage() {

        double monthly = 0;
        for (int i=1; i<=30; i++) {
            monthly = monthly + monthlyUsage.get(monthlyUsage.size() - i);
        }

        return monthly;

    }

    public String getEstimation() {

        double val = getWeeklyUsage() * 4.28;

        return df2.format(val);

    }

    public String getEstimatedCost() {

        double val = getWeeklyUsage() * 4.28 * 5.88;

        return df2.format(val);

    }

    public String getCost() {

        double val = getMonthlyUsage() * 5.88;

        return df2.format(val);
    }

    public void updateCostTextView() {
        TextView textView = (TextView) findViewById(R.id.waterCost);
        textView.setText(getCost() + " ₺ ▲");
    }

    public void updateWeeklyTextView() {
        TextView textView = (TextView) findViewById(R.id.waterWeeklyTotal);

        String val = df2.format(getWeeklyUsage());

        textView.setText(val + " m\u00B3 ▼");
    }

    public void updateMonthlyTextView() {
        TextView textView = (TextView) findViewById(R.id.waterMonthlyTotal);
        textView.setText(getMonthlyUsage() + " m\u00B3 ▲");
    }

    public void updateEstimatedTextView() {
        TextView textView = (TextView) findViewById(R.id.waterEstimatedUsage);
        textView.setText(getEstimation() + " m\u00B3 ▼");
    }

    public void updateEstimatedCostTextView() {
        TextView textView = (TextView) findViewById(R.id.waterEstimatedCost);
        textView.setText(getEstimatedCost() + " ₺ ▼");
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