package com.fyp.job_clover;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fyp.job_clover.Employer.emp_login;
import com.fyp.job_clover.Seeker.seeker_login;
import com.skydoves.elasticviews.ElasticButton;

public class Login extends AppCompatActivity {
ElasticButton Seeker,Employer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Seeker= (ElasticButton) findViewById(R.id.seeker);
        Employer = (ElasticButton) findViewById(R.id.employer);

        Seeker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, seeker_login.class));
            }
        });
        Employer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, emp_login.class));
            }
        });
    }
}