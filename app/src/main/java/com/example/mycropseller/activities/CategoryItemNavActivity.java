package com.example.mycropseller.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mycropseller.R;
import com.example.mycropseller.adapters.CategoryDetailedAdapter;
import com.example.mycropseller.models.CategoryDetailedModel;
import com.example.mycropseller.models.HomeCategory;
import com.example.mycropseller.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryItemNavActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<CategoryDetailedModel> list;
    CategoryDetailedAdapter adapter;

    FirebaseFirestore db;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item_nav);

        db = FirebaseFirestore.getInstance();
        String type = getIntent().getStringExtra("type");


        progressBar = findViewById(R.id.viewAll_progressbar);
        progressBar.setVisibility(View.VISIBLE);

        recyclerView = findViewById(R.id.rec_categoryItemNav);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.GONE);

        list = new ArrayList<>();
        adapter = new CategoryDetailedAdapter(this,list);
        recyclerView.setAdapter(adapter);

        // Filter Fruit start
        if(type != null && type.equalsIgnoreCase("fruit")) {
            db.collection("CategoryDetailed").whereEqualTo("type", "fruit").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        CategoryDetailedModel categoryDetailedModel = documentSnapshot.toObject(CategoryDetailedModel.class);
                        list.add(categoryDetailedModel);
                        adapter.notifyDataSetChanged();

                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
            /// Filter Fruit end

            // Filter vegetables start
            if(type != null && type.equalsIgnoreCase("vegetables")) {
                db.collection("CategoryDetailed").whereEqualTo("type", "vegetables").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                            CategoryDetailedModel categoryDetailedModel = documentSnapshot.toObject(CategoryDetailedModel.class);
                            list.add(categoryDetailedModel);
                            adapter.notifyDataSetChanged();

                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
                /// Filter vegetables end



    }
}