package com.example.mycropseller.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mycropseller.R;
import com.example.mycropseller.models.CategoryDetailedModel;

import java.util.List;

public class CategoryDetailedAdapter extends RecyclerView.Adapter<CategoryDetailedAdapter.ViewHolder> {

    Context context;
    List<CategoryDetailedModel> list;

    public CategoryDetailedAdapter(Context context, List<CategoryDetailedModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cate_nav_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getImg_url()).into(holder.imageView);
        holder.name.setText(list.get(position).getName());
        holder.price.setText("RM " +list.get(position).getPrice() + "/kg");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView name,price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.categoryitemnav_name);
            price = itemView.findViewById(R.id.categoryitemnav_price);
            imageView = itemView.findViewById(R.id.categoryitemnav_img);

        }
    }
}
