package com.example.saveit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    String data1[], data2[],data3[];
    int img[],pay[];
    Context context;


    public RecyclerViewAdapter(Context ct, String s1[], String s2[],String s3[], int images[],int paid[]){
        context=ct;
        data1=s1;
        data2=s2;
        data3=s3;
        img=images;
        pay=paid;
    }
    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.recycler_row,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.myText1.setText(data1[position]);
        holder.myText2.setText(data2[position]);
        holder.myText3.setText(data3[position]);
        holder.myImage.setImageResource(img[position]);
        holder.payImage.setImageResource(pay[position]);

    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView myText1,myText2,myText3;
        ImageView myImage,payImage;
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            myText1=itemView.findViewById(R.id.bills);
            myText2=itemView.findViewById(R.id.desc);
            myText3=itemView.findViewById(R.id.cost);
            myImage=itemView.findViewById(R.id.myimage);
            payImage=itemView.findViewById(R.id.paid);
        }
    }
}
