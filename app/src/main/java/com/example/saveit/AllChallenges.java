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
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Take me to Challenges Page", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
        ChallengeList.add(new ChallengeItem(R.drawable.water, "Reduce Shower Time", "Reduce your shower time to 5 minutes to save 12.5 Gallons of Water","Water"));
        ChallengeList.add(new ChallengeItem(R.drawable.gas2, "Reduce Heater Time", "Reduce your heater time to 5 minutes to save Gas","Gas"));

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