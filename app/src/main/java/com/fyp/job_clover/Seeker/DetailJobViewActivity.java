package com.fyp.job_clover.Seeker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.fyp.job_clover.Data_Classes.Seeker_Reg_Data;
import com.fyp.job_clover.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skydoves.elasticviews.ElasticButton;

import static com.fyp.job_clover.Seeker.seeker_login.MY_PREFS_NAME;

public class DetailJobViewActivity extends AppCompatActivity {
    private TextView job_title,company_name_city,salary,job_type,description,job_position;
    private ElasticButton gomake_cv;
    private  String key,emp_id;
    private DatabaseReference reference,databaseReference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_job_view);

        auth = FirebaseAuth.getInstance();
        String uid = auth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference("CV_Data");


        job_title = findViewById(R.id.detail_job_title_view_id);
        job_type = findViewById(R.id.detail_job_type_view_id);
        company_name_city = findViewById(R.id.detail_company_name_view_id);
        salary = findViewById(R.id.detail_salary_view_id);
        description = findViewById(R.id.detail_description_view_id);
        job_position = findViewById(R.id.detail_position_view_id);
        gomake_cv = findViewById(R.id.detail_job_apply_btn);


        Bundle extras = getIntent().getExtras();
        job_title.setText(extras.getString("title"));
        job_type.setText(extras.getString("jobtype"));
        company_name_city.setText(extras.getString("name-city"));
        salary.setText(extras.getString("salary"));
        description.setText(extras.getString("description"));
        job_position.setText(extras.getString("position"));
        emp_id = extras.getString("emp_id");
        key = extras.getString("hkey");

        reference.child(key).child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){
                    gomake_cv.setEnabled(false);
                    gomake_cv.setText("Applied");
//                    gomake_cv.setVisibility(View.GONE);
                }
                else {
                    gomake_cv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            goMakeCV();
                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

    public void goMakeCV() {
        Bundle extras = getIntent().getExtras();
        SharedPreferences editors = getSharedPreferences(seeker_login.MY_PREFS_NAME, MODE_PRIVATE);
        String url = editors.getString("cv_path",null);

        if (url == null){
            Intent intent = new Intent(getApplicationContext(),CV_Upload_Activity.class);
            intent.putExtra("title",extras.getString("title"));
            intent.putExtra("jobtype",extras.getString("jobtype"));
            intent.putExtra("name-city",extras.getString("name-city"));
            intent.putExtra("salary",extras.getString("salary"));
            intent.putExtra("description",extras.getString("description"));
            intent.putExtra("position",extras.getString("position"));
            intent.putExtra("emp_id",emp_id);
            intent.putExtra("s_key",key);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(getApplicationContext(),Previuos_cv.class);
            intent.putExtra("title",extras.getString("title"));
            intent.putExtra("jobtype",extras.getString("jobtype"));
            intent.putExtra("name-city",extras.getString("name-city"));
            intent.putExtra("salary",extras.getString("salary"));
            intent.putExtra("description",extras.getString("description"));
            intent.putExtra("position",extras.getString("position"));
            intent.putExtra("emp_id",emp_id);
            intent.putExtra("s_key",key);
            startActivity(intent);
        }


    }
}
