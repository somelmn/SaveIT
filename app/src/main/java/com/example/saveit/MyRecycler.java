
package com.example.saveit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class MyRecycler extends RecyclerView.Adapter<MyRecycler.MyViewHolder> {

    String data1[],data2[],data3[],data4[];
    int img[];
    Context context;

    public MyRecycler(Context ct, String first_title[], String first_desc[], String first_category[], int first_img[], String first_done[]){
        context=ct;
        data1=first_title;
        data2=first_desc;
        data3=first_category;
        img=first_img;
        data4=first_done;

    }
    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view= inflater.inflate(R.layout.myrecycler,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.myText1.setText(data1[position]);
        holder.myText2.setText(data2[position]);
        holder.myText3.setText(data3[position]);
        holder.myText4.setText(data4[position]);
        holder.myImage.setImageResource(img[position]);

        if(data4[position]==null){
            holder.myText4.setBackgroundResource(R.color.white);
        }

        holder.myText4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Done",Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return 1;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView myText1,myText2,myText3,myText4;
        ImageView myImage;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            myText1=itemView.findViewById(R.id.title1);
            myText2=itemView.findViewById(R.id.desc1);
            myText3=itemView.findViewById(R.id.category1);
            myText4=itemView.findViewById(R.id.done1);
            myImage=itemView.findViewById(R.id.img1);

        }
    }
}


