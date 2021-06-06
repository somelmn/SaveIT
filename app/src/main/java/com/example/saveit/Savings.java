package com.example.saveit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

public class Savings extends AppCompatActivity {
    DrawerLayout drawerLayout;
    MeowBottomNavigation bottomNavigation;
    TextView tname,temail,yourgoal,month1,month2,month3,month4,month1name,month2name,month3name,month4name,average,total,goal1,goal2,goal3,goal4,date1,date2,date3,date4,status1,status2,status3,status4;
    EditText setgoal;
    Button set;

    private static String TAG ="Savings";
    private float yData[] = {50, 20, 68, 32, 44, 10, 40, 30, 28, 22, 24, 30};
    private int goals[]={50, 40, 50, 50, 30, 40, 30};
    float tot,ave,tota;
    private String dates[] = {"15.01.2021", "15.02.2021", "15.03.2021", "15.04.2021", "15.05.2021", "15.06.2021","15.07.2021","15.08.2021","15.09.2021","15.10.2021","15.11.2021","15.12.2021"};
    private String xData[] = {"January", "February", "March", "April", "May", "June","July","August","September","October","November","December"};
    String status[]={"" , "" , "" , "" , "" , "" , "" , "", "", "", "", ""};
    PieChart pieChart;
    String comment1;
    Date date = new Date();
    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    int monthint = localDate.getMonthValue();
    float a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);
        Log.d(TAG, "onCreate: starting to create chart");


        pieChart = (PieChart) findViewById(R.id.IdPieChart);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Savings");
        pieChart.setCenterTextSize(10);
        pieChart.setDrawEntryLabels(true);
        addDataSet();


        month1name = findViewById(R.id.lastmonthname);
        month2name = findViewById(R.id.last2monthname);
        month3name = findViewById(R.id.last3monthname);
        month4name = findViewById(R.id.last4monthname);
        date1=findViewById(R.id.date1);
        date2=findViewById(R.id.date2);
        date3=findViewById(R.id.date3);
        date4=findViewById(R.id.date4);
        month1 = findViewById(R.id.lastmonth);
        month2 = findViewById(R.id.last2month);
        month3 = findViewById(R.id.last3month);
        month4 = findViewById(R.id.last4month);
        goal1 = findViewById(R.id.goal1);
        goal2 = findViewById(R.id.goal2);
        goal3 = findViewById(R.id.goal3);
        goal4 = findViewById(R.id.goal4);
        status1 = findViewById(R.id.status1);
        status2 = findViewById(R.id.status2);
        status3 = findViewById(R.id.status3);
        status4 = findViewById(R.id.status4);
        yourgoal = findViewById(R.id.yourgoal);

        date1.setText(dates[monthint-3]);
        date2.setText(dates[monthint-2]);
        date3.setText(dates[monthint-1]);
        date4.setText(dates[monthint]);
        month1name.setText(xData[monthint-3]);
        month2name.setText(xData[monthint-2]);
        month3name.setText(xData[monthint-1]);
        month4name.setText(xData[monthint]);
        month1.setText(String.valueOf(yData[monthint-3])+"₺");
        month2.setText(String.valueOf(yData[monthint-2])+"₺");
        month3.setText(String.valueOf(yData[monthint-1])+"₺");
        month4.setText("0.0 ₺");
        goal1.setText(String.valueOf(goals[monthint-3])+"₺");
        goal2.setText(String.valueOf(goals[monthint-2])+"₺");
        goal3.setText(String.valueOf(goals[monthint-1])+"₺");

        tota=goals[monthint-1]+goals[monthint-2];
        a= tota/2;

        goal4.setText(String.valueOf(a)+" ₺");
        yourgoal.setText(String.valueOf(a)+" ₺");


        for(int j=0; j<monthint; j++){
            if(yData[j]>goals[j]){
                status[j]="Goal reached!";
            }else{
                status[j]="Goal not reached!";
            }
        }

        status1.setText(status[monthint-3]);
        status2.setText(status[monthint-2]);
        status3.setText(status[monthint-1]);
        status4.setText(status[monthint]);


        for(int i=0; i<monthint; i++){
            tot = tot + yData[i];
        }
        ave = tot / monthint;

        total=findViewById(R.id.total);
        total.setText(String.valueOf(tot)+" ₺");
        average= findViewById(R.id.average);
        average.setText(String.valueOf(ave)+" ₺");


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
        int defaultNightMode = AppCompatDelegate.getDefaultNightMode();
        if(defaultNightMode == AppCompatDelegate.MODE_NIGHT_YES){
            LinearLayout li=(LinearLayout)findViewById(R.id.nav_drawer);
            li.setBackgroundResource(R.color.colorTextPrimary);
            LinearLayout toolbar=(LinearLayout)findViewById(R.id.toolbar);
            toolbar.setBackgroundResource(R.color.colorTextPrimary);
            TableLayout table =(TableLayout)findViewById(R.id.table);
            table.setBackgroundResource(R.color.colorTextPrimary);
        }
        else{
            LinearLayout li=(LinearLayout)findViewById(R.id.nav_drawer);
            li.setBackgroundResource(R.color.white);
        }

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d(TAG, "onValueSelected: Value selected from Chart");
                Log.d(TAG, "onValueSelected: " + e.toString() );
                Log.d(TAG, "onValueSelected: " + h.toString());
                int pos1 = e.toString().indexOf("y: ");
                String savings = e.toString().substring(pos1 + 3);

                for(int i = 0; i< monthint; i++) {
                    if (yData[i] == Float.parseFloat(savings)) {
                        pos1 = i;
                        break;
                    }

                }
                String month = xData[pos1];
                Toast.makeText(Savings.this, "Month: " + month + "\n" + "Savings: " + savings + "TL", Toast.LENGTH_LONG).show();



            }

            @Override
            public void onNothingSelected() {

            }
        });

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

        recreate();
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

    private void addDataSet() {
        Log.d(TAG, "addDataSet: started");
        ArrayList<PieEntry> yEntries = new ArrayList<>();
        ArrayList<String> xEntries = new ArrayList<>();

        for(int i = 0; i< monthint; i++){
            yEntries.add(new PieEntry(yData[i], i));

        }
        for(int i = 0; i< monthint; i++){
            xEntries.add(xData[i]);

        }

        PieDataSet pieDataSet = new PieDataSet(yEntries, "Savings");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);



        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);


        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}