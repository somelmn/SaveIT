package com.example.saveit;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity{

    DrawerLayout drawerLayout;
    MeowBottomNavigation bottomNavigation;
    TextView tname,temail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        bottomNavigation.show(2,true);
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
    public void ClickMore(View view){
        PopupMenu pm = new PopupMenu(MainActivity.this, view);
        pm.getMenuInflater().inflate(R.menu.more, pm.getMenu());
        pm.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item1:
                        ClickLogout(view);
                        return true;

                }

                return true;
            }
        });
        pm.show(); }
    public void ClickMenu(View view){
        openDrawer(drawerLayout);
  }
  public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }
    public static void closeDrawer(DrawerLayout drawerLayout){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public void ClickHome(View view){
        recreate();
    }
    public void ClickDashboard(View view){

        redirectActivity( this,Dashboard.class);
    }
    public void ClickAboutUs(View view){
        redirectActivity( this,AboutUs.class);
    }
    public void ClickSettings(View view){
        redirectActivity( this,Preference.class);
    }
    public void ClickLogout(View view){ logout(this); }

    public static void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity,aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);

    }
    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);

    }


}