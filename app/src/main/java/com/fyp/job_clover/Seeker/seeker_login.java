package com.fyp.job_clover.Seeker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.job_clover.Employer.emp_login;
import com.fyp.job_clover.Employer.emp_register;
import com.fyp.job_clover.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.skydoves.elasticviews.ElasticButton;

public class seeker_login extends AppCompatActivity {
    TextView forget,back;
    ElasticButton Login,Register;
    TextInputEditText Email,Password;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_login);

        auth = FirebaseAuth.getInstance();

        back=findViewById(R.id.back);
        forget=findViewById(R.id.forget);
        Login=findViewById(R.id.login);
        Register=findViewById(R.id.register);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(seeker_login.this, seeker_register.class));
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(Email.getText().toString())){
                    Toast.makeText(seeker_login.this, "Email is required", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Password.getText().toString())){
                    Toast.makeText(seeker_login.this, "Password is required", Toast.LENGTH_SHORT).show();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()){
                    Email.setError("Email is not Valid");
                    Email.requestFocus();
                }
                else {
                    //Hafiz yaha to check kara ga firebase sa
                    //aur jab correct hu ga to data sharedprefrence ma store kr wa kr aga intent laga dai

                    String email = Email.getText().toString();
                    String password = Password.getText().toString();


                    auth.signInWithEmailAndPassword(email,password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Toast.makeText(seeker_login.this,"Successful",Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(seeker_login.this, e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            });







                }
            }
        });


    }
    public void back(View view) {
        finish();
    }

    public void forget(View view) {
        startActivity(new Intent(seeker_login.this,seeker_forget.class));
    }
}
