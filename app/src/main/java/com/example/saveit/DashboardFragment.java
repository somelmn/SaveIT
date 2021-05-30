package com.example.saveit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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



}