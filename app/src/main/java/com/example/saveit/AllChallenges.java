package com.example.saveit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllChallenges extends AppCompatActivity {
    private ArrayList<ChallengeItem> ChallengeList;
    DrawerLayout drawerLayout;
    MeowBottomNavigation bottomNavigation;
    TextView tname,temail;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_challenges);
        drawerLayout = findViewById(R.id.drawer_layout);

        tname = findViewById(R.id.nav_name);
        temail = findViewById(R.id.nav_email);

        createExampleList();
        buildRecyclerView();

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
        }
        else{
            LinearLayout li=(LinearLayout)findViewById(R.id.nav_drawer);
            li.setBackgroundResource(R.color.white);
        }

    }

    public void createExampleList() {
        ChallengeList = new ArrayList<>();
        ChallengeList.add(new ChallengeItem(R.drawable.water, "Reduce Shower Time", "Reduce your shower time to 5 minutes to save 12.5 Gallons of Water","50 Liters"));
        ChallengeList.add(new ChallengeItem(R.drawable.tree, "Recycle Plastic Bottles", "Recycle at least 6 plastic bottles","100 gr Carbon"));
        ChallengeList.add(new ChallengeItem(R.drawable.tree, "Turn Down the Heater", "Reduce your heater degree at least 13 degrees for 6 months to save Gas","1.5 kg Carbon"));
        ChallengeList.add(new ChallengeItem(R.drawable.electricity, "Turn off Unnecessary Lights", "Turn off unnecessary lights for 2 hours everyday to save Electricity","2250 Watts"));
        ChallengeList.add(new ChallengeItem(R.drawable.water, "Plan Watering Time", "Water your plants during low light times (before 10 a.m or after 6 p.m","40 Liters"));
        ChallengeList.add(new ChallengeItem(R.drawable.tree, "Take the Stairs", "Take the stairs instead of elevators","200 gr Carbon"));
        ChallengeList.add(new ChallengeItem(R.drawable.tree, "Efficient Light Bulbs", "Use incandescent light bulbs e.g. CFL Bulbs","500 gr Carbon"));
        ChallengeList.add(new ChallengeItem(R.drawable.water, "Fix that Leak", "A faucet leaking 60 drops/minute wastes 25 liters/day","25 Liters"));
        ChallengeList.add(new ChallengeItem(R.drawable.tree, "Unplug Chargers", "Unplug unnecessary chargers","100 gr Carbon"));
        ChallengeList.add(new ChallengeItem(R.drawable.water, "Let the Dishwasher Do the Work", "Avoid driving for at least 30 km","40 Liters"));
        ChallengeList.add(new ChallengeItem(R.drawable.tree, "Reduce Television Time", "Reduce TV usage by 8 hours","200 gr Carbon"));
        ChallengeList.add(new ChallengeItem(R.drawable.tree, "Recycle Glass Bottles", "Recycle at least 6 glass bottles","2 kg Carbon"));
        ChallengeList.add(new ChallengeItem(R.drawable.water, "Defrost Smartly", "Defrost with the microwave or in the fridge overnight to avoid running you water a long time","25 Liters"));
        ChallengeList.add(new ChallengeItem(R.drawable.tree, "Recycle Aluminum Cans", "Recycle at least 6 aluminum cans","60 gr Carbon"));
        ChallengeList.add(new ChallengeItem(R.drawable.water, "Fix that Leak", "A faucet leaking 60 drops/minute wastes 25 liters/day","25 Liters"));
        ChallengeList.add(new ChallengeItem(R.drawable.tree, "Stop Plastic Bag Usage", "Bring your own bag to the grocery store","100 gr Carbon"));
        ChallengeList.add(new ChallengeItem(R.drawable.tree, "Avoid Driving", "Avoid driving for at least 30 km","3 kg Carbon"));

    }
    public void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler1);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ChallangesRecyclerAdapter(ChallengeList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
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
        recreate();

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