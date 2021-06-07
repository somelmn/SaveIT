
package com.example.saveit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
public class MyRecycler extends RecyclerView.Adapter<com.example.saveit.MyRecycler.ExampleViewHolder> {
        private ArrayList<ChallengeItem> ExampleList;
        public static class ExampleViewHolder extends RecyclerView.ViewHolder {
            public ImageView img1;
            public TextView data1;
            public TextView data2;
            public TextView data3;
            public TextView done;
            public ConstraintLayout layout;
            public ExampleViewHolder(View itemView) {
                super(itemView);
                img1 = itemView.findViewById(R.id.img1);
                data1 = itemView.findViewById(R.id.title1);
                data2 = itemView.findViewById(R.id.desc1);
                data3 = itemView.findViewById(R.id.category1);
                done = itemView.findViewById(R.id.done1);
                layout = itemView.findViewById(R.id.bck);
            }
        }
        public MyRecycler(ArrayList<ChallengeItem> exampleList) {
            ExampleList = exampleList;
        }
        @Override
        public com.example.saveit.MyRecycler.ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.myrecycler, parent, false);
            com.example.saveit.MyRecycler.ExampleViewHolder evh = new com.example.saveit.MyRecycler.ExampleViewHolder(v);
            return evh;
        }
        @Override
        public void onBindViewHolder(com.example.saveit.MyRecycler.ExampleViewHolder holder, int position) {
            ChallengeItem currentItem = ExampleList.get(position);
            holder.img1.setImageResource(currentItem.getImageResource());
            holder.data1.setText(currentItem.getText1());
            holder.data2.setText(currentItem.getText2());
            holder.data3.setText(currentItem.getText3());

            if(currentItem.getText1()==null){
                holder.done.setBackgroundResource(R.color.white);
            }

            holder.done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"Challenge Done",Toast.LENGTH_SHORT).show();
                }
            });
        }
        @Override
        public int getItemCount() {
            return ExampleList.size();
        }
    }

