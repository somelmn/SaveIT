package com.example.saveit;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class AboutUs extends AppCompatActivity {
    DrawerLayout drawerLayout;
    MeowBottomNavigation bottomNavigation;
    TextView tname,temail;
    RecyclerView recyclerView;

    String title,desc,category,done;
    int img,position;
    String data1[]={"",""};
    String data2[]={"",""};
    String data3[]={"",""};
    String data4[]={"",""};
    int pos[]={0,0};
    int images[]={0,0};
    int c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        drawerLayout = findViewById(R.id.drawer_layout);

        tname = findViewById(R.id.nav_name);
        temail = findViewById(R.id.nav_email);

        c=0;



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
                MainActivity.redirectActivity(AboutUs.this,AllChallenges.class);
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

        getData();
        setData();

        recyclerView = findViewById(R.id.recycler2);
        MyRecycler myAdapter=new MyRecycler(this,data1,data2,data3,images,data4);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

    public void getData(){
        if(getIntent().hasExtra("data1")){
            title=getIntent().getStringExtra("data1");
            desc=getIntent().getStringExtra("data2");
            category=getIntent().getStringExtra("data3");
            img=getIntent().getIntExtra("img",1);
            position=getIntent().getIntExtra("position",0);
            done="Done";

            PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putString("title", title).commit();
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putString("desc", desc).commit();
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putString("category", category).commit();
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putInt("img",img).commit();
            PreferenceManager.getDefaultSharedPreferences(this).edit()
                    .putString("done", done).commit();

        }else{
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        }
    }

    public void setData(){
        title = PreferenceManager.getDefaultSharedPreferences(this)
                .getString("title", "");
        desc = PreferenceManager.getDefaultSharedPreferences(this)
                .getString("desc", "");
        category = PreferenceManager.getDefaultSharedPreferences(this)
                .getString("category", "");
        img = PreferenceManager.getDefaultSharedPreferences(this)
                .getInt("img", 0);
        done = PreferenceManager.getDefaultSharedPreferences(this)
                .getString("done", "");

        data1[c]=title;
        data2[c]=desc;
        data3[c]=category;
        images[c]=img;
        data4[c]=done;
        c++;
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