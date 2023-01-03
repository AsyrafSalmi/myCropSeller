package com.example.mycropseller.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycropseller.R;
import com.example.mycropseller.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    Button signUp;
    EditText name,email,password;
    TextView signIn;
    FirebaseAuth auth;
    FirebaseDatabase database;
    Switch userType;
    int choices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        signUp = findViewById(R.id.signup_btn);
        name = findViewById(R.id.reg_name);
        email = findViewById(R.id.reg_email);
        password = findViewById(R.id.reg_password);
        signIn = findViewById(R.id.signin_click);
        userType = findViewById(R.id.reg_type);


    }



    public void StartSignUp(View view) {
        createUser();
    }

    public void StartSignInActivities(View view) {
        startActivities(new Intent[]{new Intent(RegistrationActivity.this, LoginActivity.class)});


    }

    private void createUser() {
        String username = name.getText().toString();
        String useremail = email.getText().toString();
        String userpassword = password.getText().toString();

        if(userType.isChecked())
        {
            choices = 0;
        }else
        {
            choices = 1;
        }



        if(TextUtils.isEmpty(username))
        {
            Toast.makeText(this, "Name Cannot Be Empty!", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(useremail))
        {
            Toast.makeText(this, "Email Cannot Be Empty!", Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(userpassword))
        {
            Toast.makeText(this, "Password Cannot Be Empty!", Toast.LENGTH_LONG).show();
            return;
        }
        if(userpassword.length() < 8)
        {
            Toast.makeText(this, "Password Cannot Be Less Than 8!", Toast.LENGTH_LONG).show();
            return;
        }

        auth.createUserWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    UserModel userModel = new UserModel(username,userpassword,useremail,choices);
                    String id = task.getResult().getUser().getUid();
                    database.getReference().child("Users").child(id).setValue(userModel);

                    Toast.makeText(RegistrationActivity.this, "Registration Complete",Toast.LENGTH_LONG).show();
                    startActivities(new Intent[]{new Intent(RegistrationActivity.this, LoginActivity.class)});
                }
                else{
                    Toast.makeText(RegistrationActivity.this, "Error" + task.getException(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }


}