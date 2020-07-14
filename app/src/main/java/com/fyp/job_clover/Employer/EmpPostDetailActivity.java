package com.fyp.job_clover.Employer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fyp.job_clover.R;

public class EmpPostDetailActivity extends AppCompatActivity {
    private TextView job_title,company_name_city,salary,job_type,description,job_position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_post_detail);


        job_title = findViewById(R.id.emp_detail_job_title_view_id);
        job_type = findViewById(R.id.emp_detail_job_type_view_id);
        company_name_city = findViewById(R.id.emp_detail_company_name_view_id);
        salary = findViewById(R.id.emp_detail_salary_view_id);
        description = findViewById(R.id.emp_detail_description_view_id);
        job_position = findViewById(R.id.emp_detail_position_view_id);


        Bundle bundle = getIntent().getExtras();
        job_title.setText(bundle.getString("title"));
        job_type.setText(bundle.getString("jobtype"));
        company_name_city.setText( bundle.getString("name")+ " - " + bundle.getString("city"));
        salary.setText(bundle.getString("salaryfrom")+ " - " + bundle.getString("salaryto"));
        description.setText(bundle.getString("description"));
        job_position.setText(bundle.getString("position"));













    }

    public void Updatepostpoage(View view) {

        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        String job_type = bundle.getString("jobtype");
        String company_name = bundle.getString("com_name");
        String email = bundle.getString("email");
        String address = bundle.getString("address");
        String phone = bundle.getString("phone");
        String edu = bundle.getString("education");
        String city = bundle.getString("city") ;
        String salaryfrom = bundle.getString("salaryfrom");
        String salaryto =  bundle.getString("salaryto");
        String description = bundle.getString("description");
        String job_position = bundle.getString("position");
        String key = bundle.getString("p_key");



        Intent intent = new Intent(getApplicationContext(), EmpUpdatePostActivity.class);
        intent.putExtra("title",title);
        intent.putExtra("jobtype",job_type);
        intent.putExtra("name",company_name);
        intent.putExtra("city",city);
        intent.putExtra("salaryfrom",salaryfrom);
        intent.putExtra("salaryto",salaryto);
        intent.putExtra("description",description);
        intent.putExtra("position",job_position);
        intent.putExtra("email",email);
        intent.putExtra("address",address);
        intent.putExtra("phone",phone);
        intent.putExtra("edu",edu);
        intent.putExtra("key",key);
        startActivity(intent);
    }
}
