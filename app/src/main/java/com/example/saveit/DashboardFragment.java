package com.example.saveit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;


public class DashboardFragment extends Fragment {

    View dashboardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dashboardView = inflater.inflate(R.layout.fragment_dashboard, null, false);

        Button Electricity = dashboardView.findViewById(R.id.electricity);
        Button water = dashboardView.findViewById(R.id.Water);
        Button gas = dashboardView.findViewById(R.id.Gas);

        Electricity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(DashboardFragment.this.getActivity(), ElectricityUsage.class);
                startActivity(myIntent);

            }
        });

        water.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(DashboardFragment.this.getActivity(), WaterUsage.class);
                startActivity(myIntent);

            }
        });

        gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(DashboardFragment.this.getActivity(), GasUsage.class);
                startActivity(myIntent);

            }
        });





        // Inflate the layout for this fragment
        return dashboardView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int defaultNightMode = AppCompatDelegate.getDefaultNightMode();
        if(defaultNightMode == AppCompatDelegate.MODE_NIGHT_YES){
            LinearLayout li=(LinearLayout)view.findViewById(R.id.nav_drawer);
            li.setBackgroundResource(R.color.colorTextPrimary);
            LinearLayout toolbar=(LinearLayout)view.findViewById(R.id.toolbar);
            toolbar.setBackgroundResource(R.color.colorTextPrimary);
        }
        else{
            LinearLayout li=(LinearLayout)view.findViewById(R.id.nav_drawer);
            li.setBackgroundResource(R.color.white);
        }
    }


}