package com.example.mycropseller.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.mycropseller.R;
import com.example.mycropseller.models.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetailedActivity extends AppCompatActivity {

    ImageView detailedImg,addItem,removeItem;
    TextView name,price,rating,description,quantity;
    Button addtocart;
    int totalitemquantity = 1;
    int totalprice =0;
    ViewAllModel viewAllModel = null;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();


        final Object object = getIntent().getSerializableExtra("detail");
        if (object instanceof ViewAllModel)
        {
            viewAllModel = (ViewAllModel) object;
        }

        detailedImg = findViewById(R.id.detailed_img);
        addItem = findViewById(R.id.detailed_add_button);
        removeItem = findViewById(R.id.detailed_remove_button);
        name = findViewById(R.id.detailed_name);
        price = findViewById(R.id.detailed_price);
        rating = findViewById(R.id.detailed_rating);
        description = findViewById(R.id.detailed_desctiption);
        addtocart = findViewById(R.id.detailed_addtocart);
        quantity = findViewById(R.id.detailed_quantity_item_cart);

        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalitemquantity>0)
                {
                    totalitemquantity++;
                    quantity.setText(String.valueOf(totalitemquantity));
                    totalprice = viewAllModel.getPrice() * totalitemquantity;
                }
            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (totalitemquantity>1)
                {
                    totalitemquantity--;
                    quantity.setText(String.valueOf(totalitemquantity));
                    totalprice = viewAllModel.getPrice() * totalitemquantity;
                }

            }
        });

        if (viewAllModel != null)
        {
            Glide.with(getApplicationContext()).load(viewAllModel.getImg_url()).into(detailedImg);
            rating.setText(viewAllModel.getRating());
            name.setText(viewAllModel.getName());
            description.setText(viewAllModel.getDescription());
            price.setText(viewAllModel.getPrice() + "/kg");

            if (viewAllModel.getType().equals("egg"))
            {
                price.setText(viewAllModel.getPrice() + "/Tray");
            }
            if (viewAllModel.getType().equals("dairy"))
            {
                price.setText(viewAllModel.getPrice() + "/Litre");
            }

            totalprice = viewAllModel.getPrice() * totalitemquantity;

        }

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedtocart();
            }
        });
    }

    private void addedtocart() {
        String saveCurrentDate,saveCurrentTime;
        Calendar calendarDate = Calendar.getInstance();

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendarDate.getTime());

        SimpleDateFormat currentDate = new SimpleDateFormat("dd MM, yyyy");
        saveCurrentDate = currentDate.format(calendarDate.getTime());

        final HashMap<String,Object> cartMap = new HashMap<>();

        cartMap.put("productName",viewAllModel.getName());
        cartMap.put("productPrice",price.getText().toString());
        cartMap.put("currentTime",saveCurrentTime);
        cartMap.put("currentDate",saveCurrentDate);
        cartMap.put("totalQuantity",quantity.getText().toString());
        cartMap.put("totalPrice",totalprice);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid()).collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                Toast.makeText(DetailedActivity.this,"Successfully Added to Cart", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

}