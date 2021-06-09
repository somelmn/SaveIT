package com.example.saveit;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
    public class ChallangesRecyclerAdapter extends RecyclerView.Adapter<ChallangesRecyclerAdapter.ExampleViewHolder> {
        private ArrayList<ChallengeItem> mExampleList;
        public static class ExampleViewHolder extends RecyclerView.ViewHolder {
            public ImageView mImageView;
            public TextView mTextView1;
            public TextView mTextView2;
            public TextView mTextView3;
            public TextView click;
            public ExampleViewHolder(View itemView) {
                super(itemView);
                mImageView = itemView.findViewById(R.id.img);
                mTextView1 = itemView.findViewById(R.id.title);
                mTextView2 = itemView.findViewById(R.id.desc);
                mTextView3 = itemView.findViewById(R.id.savings);
                click = itemView.findViewById(R.id.add);
            }
        }
        public ChallangesRecyclerAdapter(ArrayList<ChallengeItem> exampleList) {
            mExampleList = exampleList;
        }
        @Override
        public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_challenges_recycler, parent, false);
            ExampleViewHolder evh = new ExampleViewHolder(v);
            return evh;
        }
        @Override
        public void onBindViewHolder(ExampleViewHolder holder, int position) {
            ChallengeItem currentItem = mExampleList.get(position);
            holder.mImageView.setImageResource(currentItem.getImageResource());
            holder.mTextView1.setText(currentItem.getText1());
            holder.mTextView2.setText(currentItem.getText2());
            holder.mTextView3.setText(currentItem.getText3());

            holder.click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(view.getContext(),"Challenge Added",Toast.LENGTH_SHORT).show();

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = user.getEmail();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                    reference.child(user.getUid()).child("Item").setValue(currentItem.getText1());
                    reference.child(user.getUid()).child("Description").setValue(currentItem.getText2());
                    reference.child(user.getUid()).child("Savings").setValue(currentItem.getText3());
                    reference.child(user.getUid()).child("Image").setValue(currentItem.getImageResource());
                    reference.child(user.getUid()).child("Done").setValue("Goal not reached yet.");

                }
            });
        }
        @Override
        public int getItemCount() {
            return mExampleList.size();
        }
    }