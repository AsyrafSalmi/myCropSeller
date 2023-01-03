package com.example.mycropseller.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mycropseller.R;
import com.example.mycropseller.activities.ViewAllActivity;
import com.example.mycropseller.models.BestSellerModel;

import java.util.List;

public class BestSellerAdapters extends RecyclerView.Adapter<BestSellerAdapters.ViewHolder>{

    private Context context;
    private List<BestSellerModel> bestSellerModelList;

    public BestSellerAdapters(Context context, List<BestSellerModel> bestSellerModelList) {
        this.context = context;
        this.bestSellerModelList = bestSellerModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.best_selling,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(bestSellerModelList.get(position).getImg_url()).into(holder.BestSellImg);
        holder.name.setText(bestSellerModelList.get(position).getName());
        holder.description.setText(bestSellerModelList.get(position).getDescription());
        holder.rating.setText(bestSellerModelList.get(position).getRating());
        holder.discount.setText(bestSellerModelList.get(position).getDiscount());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewAllActivity.class);
                intent.putExtra("type", bestSellerModelList.get(holder.getAdapterPosition()).getType());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return bestSellerModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView BestSellImg;
        TextView name,description,rating,discount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            BestSellImg = itemView.findViewById(R.id.bestsell_img);
            name = itemView.findViewById(R.id.bestsell_name);
            description = itemView.findViewById(R.id.bestsell_des);
            rating = itemView.findViewById(R.id.bestsell_rating);
            discount = itemView.findViewById(R.id.bestsell_disc);


        }
    }
}
