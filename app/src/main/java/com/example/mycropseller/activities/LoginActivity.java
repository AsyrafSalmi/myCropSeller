package com.example.mycropseller.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mycropseller.MainActivity;
import com.example.mycropseller.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    Button signIn;
    EditText email,password;
    TextView signUp;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();

        signIn = findViewById(R.id.signIn_btn);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_password);
        signUp = findViewById(R.id.signUp_click);;



    }

    public void StartSignUpActivities(View view) {
        startActivities(new Intent[]{new Intent(LoginActivity.this, RegistrationActivity.class)});
    }

    public void StartSignIn(View view) {
        loginUser();
    }

    private void loginUser() {
        String useremail = email.getText().toString();
        String userpassword = password.getText().toString();


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

        auth.signInWithEmailAndPassword(useremail,userpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(LoginActivity.this, "Login Successful",Toast.LENGTH_LONG).show();
                    startActivities(new Intent[]{new Intent(LoginActivity.this, MainActivity.class)});
                }else
                {
                    Toast.makeText(LoginActivity.this, "Error" + task.getException(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}