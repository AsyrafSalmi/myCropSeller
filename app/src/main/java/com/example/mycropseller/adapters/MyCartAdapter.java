package com.example.mycropseller.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycropseller.R;
import com.example.mycropseller.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    Context context;
    List<MyCartModel> cartModelList;
    double totalCartPrice = 0.00;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public MyCartAdapter(Context context, List<MyCartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.Pname.setText(cartModelList.get(position).getProductName());
        holder.Pprice.setText(cartModelList.get(position).getProductPrice());
        holder.Pdate.setText(cartModelList.get(position).getCurrentDate());
        holder.Ptime.setText(cartModelList.get(position).getCurrentTime());
        holder.Pquantity.setText(cartModelList.get(position).getTotalQuantity());
        /*holder.Ptotalprice.setText("RM" + "%.2f",String.valueOf(cartModelList.get(position).getTotalPrice()));

        String pricebuffer1 = String.valueOf(cartModelList.get(position).getTotalPrice());*/
        String pricebuffer2 = String.format("%.2f",cartModelList.get(position).getTotalPrice());

        holder.Ptotalprice.setText("RM " + pricebuffer2);

        holder.deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("AddToCart").document(cartModelList.get(holder.getAdapterPosition()).getDocumentId()).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful())
                                {
                                    cartModelList.remove(cartModelList.get(holder.getAdapterPosition()));
                                    notifyDataSetChanged();
                                    Toast.makeText(context,"Item Removed",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        totalCartPrice = totalCartPrice + cartModelList.get(position).getTotalPrice();
        Intent intent = new Intent("CustTotalAmount");
        intent.putExtra("totalAmount",totalCartPrice);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Pname, Pprice,Pdate,Ptime,Pquantity,Ptotalprice;
        ImageView deleteItemButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            Pname = itemView.findViewById(R.id.cartItem_ProductName);
            Pprice = itemView.findViewById(R.id.cartItem_ProductPrice);
            Pdate = itemView.findViewById(R.id.cartItem_ProductDate);
            Ptime = itemView.findViewById(R.id.cartItem_ProductTime);
            Pquantity = itemView.findViewById(R.id.cartItem_ProductItemquantity);
            Ptotalprice = itemView.findViewById(R.id.cartItem_ProductTotalprice);
            deleteItemButton = itemView.findViewById(R.id.cartItem_delete);

        }
    }
}
