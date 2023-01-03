package com.example.mycropseller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.mycropseller.adapters.MyCartAdapter;
import com.example.mycropseller.models.MyCartModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class MyCartsFragment extends Fragment {

    RecyclerView recyclerView;
    ConstraintLayout mycartDefault;
    MyCartAdapter cartAdapter;
    List<MyCartModel> cartModelList;

    FirebaseAuth auth;
    FirebaseFirestore db;
    TextView totalCartAmount;
    ProgressBar progressBar;

    Button buyNow;


    public MyCartsFragment() {
        // Required empty public constructor
    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            double totalPriceItem = intent.getDoubleExtra("totalAmount",0);
            String pricebuffer1 = String.format("%.2f",totalPriceItem);
            totalCartAmount.setText("Total: RM" + pricebuffer1);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_my_carts, container, false);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        progressBar = root.findViewById(R.id.myCart_progressbar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = root.findViewById(R.id.myCart_rec);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setVisibility(View.GONE);

        mycartDefault = root.findViewById(R.id.myCart_default);
        mycartDefault.setVisibility(View.GONE);

        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(getActivity(),cartModelList);
        recyclerView.setAdapter(cartAdapter);

        totalCartAmount = root.findViewById(R.id.myCart_totalprice);

        buyNow = root.findViewById(R.id.myCart_buynowButton);

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,new IntentFilter("CustTotalAmount"));


            db.collection("CurrentUser").document(auth.getCurrentUser().getUid()).collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful())
                    {
                        for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){

                            String documentId = documentSnapshot.getId();

                            MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);
                            cartModelList.add(cartModel);
                            cartAdapter.notifyDataSetChanged();
                            cartModel.setDocumentId(documentId);

                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });


        buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),PlacedOrderActivity.class);
                intent.putExtra("itemlist", (Serializable) cartModelList);
                startActivity(intent);
            }
        });


    return root;
    }

}