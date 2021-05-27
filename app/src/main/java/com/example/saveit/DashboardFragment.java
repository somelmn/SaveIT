package com.example.saveit;

import android.content.Intent;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Random;


public class DashboardFragment extends Fragment {

    View dashboardView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dashboardView = inflater.inflate(R.layout.fragment_dashboard, null, false);

        Button button = dashboardView.findViewById(R.id.sendbutton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast
                        .makeText(getActivity().getApplicationContext(), LocalDate.now().toString(), Toast.LENGTH_SHORT).show();
            }
        });

        String day = LocalDate.now().getDayOfWeek().name();


        GraphView graph = (GraphView) dashboardView.findViewById(R.id.graph);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, getDailyUsage()),
                new DataPoint(1, getDailyUsage()),
                new DataPoint(2, getDailyUsage()),
                new DataPoint(3, getDailyUsage()),
                new DataPoint(4, getDailyUsage())
        });

        graph.addSeries(series);

        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        staticLabelsFormatter.setHorizontalLabels(new String[] {dayEarlier(4),dayEarlier(3),dayEarlier(2),dayEarlier(1),dayEarlier(0)});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);



        // Inflate the layout for this fragment
        return dashboardView;
    }

    public double getDailyUsage() {
        double rangeMin = 6.3;
        double rangeMax = 10.3;

        Random r = new Random();
        double randomValue = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
        return randomValue;
    }

    public String dayEarlier(int i) {
        String day = LocalDate.now().getDayOfWeek().minus(i).name().substring(0, 3);

        return day;
    }

}