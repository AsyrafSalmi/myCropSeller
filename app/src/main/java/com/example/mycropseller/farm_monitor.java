package com.example.mycropseller;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mycropseller.R;
import com.example.mycropseller.models.MonitorModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.database.DataSnapshot;
public class farm_monitor extends Fragment {

    private TextView tempValue, humpValue,EcValue;
    FirebaseDatabase database;
    Switch autoSwitch;


    public farm_monitor() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_farm_monitor,container,false);

        database = FirebaseDatabase.getInstance();
        tempValue = root.findViewById(R.id.monitor_temp_value);
        humpValue = root.findViewById(R.id.monitor_hump_value);
        EcValue = root.findViewById(R.id.monitor_EC_value);
        autoSwitch = root.findViewById(R.id.farm_AutoSwitch);

        autoSwitch.setTextOn("On");
        autoSwitch.setTextOff("Off");

        database.getReference().child("Temperature").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Tempvalue = snapshot.getValue().toString();
                tempValue.setText(Tempvalue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        database.getReference().child("Humidity").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Humpvalue = snapshot.getValue().toString();
                humpValue.setText(Humpvalue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        database.getReference().child("ECcurrent").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String ECvalue = snapshot.getValue().toString();
                EcValue.setText(ECvalue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        autoSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(autoSwitch.isChecked())
                {
                    database.child("Switch").setValue(1);
                    Toast.makeText(getContext(),"Farm is in Fully Automation Mode",Toast.LENGTH_LONG).show();

                }else
                {
                    database.child("Switch").setValue(0);
                    Toast.makeText(getContext(),"Farm is in Semi Automation Mode",Toast.LENGTH_LONG).show();
                }
            }
        });

        return root;

    }
}