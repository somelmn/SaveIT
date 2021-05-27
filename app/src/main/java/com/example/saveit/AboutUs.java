package com.example.saveit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

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

public class AboutUs extends AppCompatActivity {
    DrawerLayout drawerLayout;
    MeowBottomNavigation bottomNavigation;
    TextView tname,temail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        drawerLayout= findViewById(R.id.drawer_layout);

        tname= findViewById(R.id.nav_name);
        temail= findViewById(R.id.nav_email);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getEmail();
        temail.setText(uid);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.orderByChild("email").equalTo(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    String name=snapshot.child("fullName").getValue().toString();
                    tname.setText(name);
                }
            }        @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_usage));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_home2));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_shop));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
        @Override
        public void onShowItem(MeowBottomNavigation.Model item) {
            Fragment fragment= null;
            switch (item.getId()){
                case 1:
                    fragment= new DashboardFragment();
                    break;
                case 2:
                    fragment= new TipsFragment();
                    break;
                case 3:
                    fragment= new HomeFragment();
                    break;

            }
            loadFragment(fragment);
        }
    });
        bottomNavigation.setCount(1,"10");
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
        @Override
        public void onClickItem(MeowBottomNavigation.Model item) {
                /* Toast.makeText(getApplicationContext()
                        ,"You Clicked" + item.getId()
                        ,Toast.LENGTH_SHORT).show(); */
        }
    });

        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
        @Override
        public void onReselectItem(MeowBottomNavigation.Model item) {
               /*  Toast.makeText(getApplicationContext()
                ,"You Reselected" + item.getId()
                ,Toast.LENGTH_SHORT).show(); */
        }
    });

    }


    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();
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
    public void ClickDashboard(View view){
        MainActivity.redirectActivity(this,Dashboard.class);
    }
    public void ClickAboutUs(View view){
        recreate();

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