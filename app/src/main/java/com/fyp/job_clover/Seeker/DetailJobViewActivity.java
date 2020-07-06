package com.fyp.job_clover.Seeker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fyp.job_clover.R;

public class DetailJobViewActivity extends AppCompatActivity {
    private TextView job_title,company_name_city,salary,job_type,description,job_position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job_view);

        job_title = findViewById(R.id.detail_job_title_view_id);
        job_type = findViewById(R.id.detail_job_type_view_id);
        company_name_city = findViewById(R.id.detail_company_name_view_id);
        salary = findViewById(R.id.detail_salary_view_id);
        description = findViewById(R.id.detail_description_view_id);
        job_position = findViewById(R.id.detail_position_view_id);


        Bundle extras = getIntent().getExtras();
        job_title.setText(extras.getString("title"));
        job_type.setText(extras.getString("jobtype"));
        company_name_city.setText(extras.getString("name-city"));
        salary.setText(extras.getString("salary"));
        description.setText(extras.getString("description"));
        job_position.setText(extras.getString("position"));
    }

    public void goMakeCV(View view) {
        Intent intent = new Intent(getApplicationContext(),SeekerCVMakingActivity.class);
        startActivity(intent);
    }
}
