package com.fyp.job_clover.Seeker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.fyp.job_clover.Data_Classes.AppliedJobs;
import com.fyp.job_clover.Data_Classes.FileUpload;
import com.fyp.job_clover.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.skydoves.elasticviews.ElasticButton;

public class Previuos_cv extends AppCompatActivity {
ElasticButton Send,Upload;
String key,title,jobtype,namecity,salary,description,position,emp_id,cv_email, uid,emp_token,seeker_token,name;

private FirebaseAuth auth;
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference,matabaseReference,databaseReference;
    private  FileUpload upload;
    String cv_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previuos_cv);
        Send = findViewById(R.id.continu);
        Upload = findViewById(R.id.upload);

        SharedPreferences editors = getSharedPreferences(seeker_login.MY_PREFS_NAME, MODE_PRIVATE);
        cv_url = editors.getString("cv_path",null);
//        Toast.makeText(Previuos_cv.this, cv_url, Toast.LENGTH_SHORT).show();

        Bundle extras = getIntent().getExtras();
        key = extras.getString("s_key");
        title = extras.getString("title");
        jobtype = extras.getString("jobtype");
        namecity = extras.getString("name-city");
        salary = extras.getString("salary");
        description = extras.getString("description");
        position = extras.getString("position");
        emp_id = extras.getString("emp_id");

        mStorageReference = FirebaseStorage.getInstance().getReference();
        matabaseReference = FirebaseDatabase.getInstance().getReference("Applied_Jobs");
        databaseReference = FirebaseDatabase.getInstance().getReference("CV_Data");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("CV_Data");
        auth = FirebaseAuth.getInstance();


        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),CV_Upload_Activity.class);
                intent.putExtra("title",title);
                intent.putExtra("jobtype",jobtype);
                intent.putExtra("name-city",namecity);
                intent.putExtra("salary",salary);
                intent.putExtra("description",description);
                intent.putExtra("position",position);
                intent.putExtra("emp_id",emp_id);
                intent.putExtra("s_key",key);
                startActivity(intent);
                finish();
            }
        });

        Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final android.app.AlertDialog loading = new ProgressDialog(Previuos_cv.this);
                loading.setMessage("Uploading...");
                loading.show();

                uid =auth.getCurrentUser().getUid();
                upload = new FileUpload("Resume", cv_url,
                        auth.getCurrentUser().getUid());
                mDatabaseReference.child(key).child(uid).setValue(upload);
                AppliedJobs aj = new AppliedJobs(title,jobtype,namecity,salary,description, position,emp_id);
                matabaseReference.child(uid).child(key).setValue(aj);

                Toast.makeText(Previuos_cv.this, "Applied Successfully", Toast.LENGTH_SHORT).show();
                loading.dismiss();
                finish();

            }
        });

    }
}