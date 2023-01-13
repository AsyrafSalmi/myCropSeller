package com.example.mycropseller.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mycropseller.R;
import com.example.mycropseller.activities.ViewAllActivity;
import com.example.mycropseller.adapters.BestSellerAdapters;
import com.example.mycropseller.adapters.HomeCategoryAdapters;
import com.example.mycropseller.adapters.HomeRecommendedAdapter;
import com.example.mycropseller.adapters.ViewAllAdapter;
import com.example.mycropseller.databinding.FragmentHomeBinding;
import com.example.mycropseller.models.BestSellerModel;
import com.example.mycropseller.models.HomeCategory;
import com.example.mycropseller.models.HomeRecommendedModel;
import com.example.mycropseller.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
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

    //Search Bar
    EditText search_box;
    private List<ViewAllModel> viewAllModelList;
    private RecyclerView recyclerViewSearch;
    private ViewAllAdapter viewAllAdapter;


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

        //search bar
        recyclerViewSearch = root.findViewById(R.id.search_rec);
        search_box = root.findViewById(R.id.search_box);
        viewAllModelList = new ArrayList<>();
        viewAllAdapter = new ViewAllAdapter(getContext(),viewAllModelList);
        recyclerViewSearch.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewSearch.setAdapter(viewAllAdapter);
        recyclerViewSearch.setHasFixedSize(true);
        search_box.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().isEmpty())
                {
                    viewAllModelList.clear();
                    viewAllAdapter.notifyDataSetChanged();
                }else{
                    searchProduct(s.toString());
                }

            }
        });


        return root;
    }

    private void searchProduct(String type) {
        if(!type.isEmpty()){
            db.collection("AllProducts").whereEqualTo("type",type).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if(task.isSuccessful() && task.getResult() != null){
                        viewAllModelList.clear();
                        viewAllAdapter.notifyDataSetChanged();
                        for(DocumentSnapshot doc :task.getResult().getDocuments()){
                            ViewAllModel viewAllModel = doc.toObject(ViewAllModel.class);
                            viewAllModelList.add(viewAllModel);
                            viewAllAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}