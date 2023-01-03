package com.example.mycropseller.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycropseller.R;
import com.example.mycropseller.adapters.BestSellerAdapters;
import com.example.mycropseller.adapters.HomeCategoryAdapters;
import com.example.mycropseller.adapters.HomeRecommendedAdapter;
import com.example.mycropseller.databinding.FragmentHomeBinding;
import com.example.mycropseller.models.BestSellerModel;
import com.example.mycropseller.models.HomeCategory;
import com.example.mycropseller.models.HomeRecommendedModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    ScrollView scrollView;
    ProgressBar progressBar;
    RecyclerView BestSellRec,CatRec,RecomRec;
    FirebaseFirestore db;

    //Best Seller item
    List<BestSellerModel> bestSellerModelList;
    BestSellerAdapters bestSellerAdapters;

    //Category
    List<HomeCategory> categoryList;
    HomeCategoryAdapters homeCategoryAdapters;

    //Home recommended
    List<HomeRecommendedModel> homeRecommendedModelList;
    HomeRecommendedAdapter homeRecommendedAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = FirebaseFirestore.getInstance();

        //ScrollView
        scrollView = root.findViewById(R.id.home_scrollview);
        scrollView.setVisibility(View.GONE);

        //Progress Bar
        progressBar = root.findViewById(R.id.home_progressbar);
        progressBar.setVisibility(View.VISIBLE);

        //Best Seller
        BestSellRec = root.findViewById(R.id.bestrec);
        BestSellRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        bestSellerModelList = new ArrayList<>();
        bestSellerAdapters = new BestSellerAdapters(getActivity(),bestSellerModelList);
        BestSellRec.setAdapter(bestSellerAdapters);

        //Best Seller Start
        db.collection("BestSellingProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                BestSellerModel bestSellerModel = document.toObject(BestSellerModel.class);
                                bestSellerModelList.add(bestSellerModel);
                                bestSellerAdapters.notifyDataSetChanged();


                                scrollView.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);

                            }
                        } else {
                            Toast.makeText(getActivity(),"Error" + task.getException(),Toast.LENGTH_SHORT);
                        }
                    }
                });
        // Best Seller End

        //Category
        CatRec = root.findViewById(R.id.catrec);
        CatRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryList = new ArrayList<>();
        homeCategoryAdapters = new HomeCategoryAdapters(getActivity(),categoryList);
        CatRec.setAdapter(homeCategoryAdapters);


        //Category Start
        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeCategory homeCategory = document.toObject(HomeCategory.class);
                                categoryList.add(homeCategory);
                                homeCategoryAdapters.notifyDataSetChanged();

                            }
                        } else {
                            Toast.makeText(getActivity(),"Error" + task.getException(),Toast.LENGTH_SHORT);
                        }
                    }
                });
        // Category End


        //Recommended
        RecomRec = root.findViewById(R.id.recrec);
        RecomRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        homeRecommendedModelList = new ArrayList<>();
        homeRecommendedAdapter = new HomeRecommendedAdapter(getActivity(),homeRecommendedModelList);
        RecomRec.setAdapter(homeRecommendedAdapter);

        //Recommended Start
        db.collection("Recommended")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeRecommendedModel homeRecommendedModel = document.toObject(HomeRecommendedModel.class);
                                homeRecommendedModelList.add(homeRecommendedModel);
                                homeRecommendedAdapter.notifyDataSetChanged();

                            }
                        } else {
                            Toast.makeText(getActivity(),"Error" + task.getException(),Toast.LENGTH_SHORT);
                        }
                    }
                });
        // Recommended End


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}