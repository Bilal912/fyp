package com.fyp.job_clover.Employer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.job_clover.Data_Classes.Emp_Post_Data;
import com.fyp.job_clover.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.skydoves.elasticviews.ElasticButton;
import com.trenzlr.firebasenotificationhelper.FirebaseNotiCallBack;
import com.trenzlr.firebasenotificationhelper.FirebaseNotificationHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class EmpViewPostActivity extends AppCompatActivity implements FirebaseNotiCallBack {
    private TextView job_title,company_name,company_city,address,salary_from,salary_to,education,positions,email,phone,description,jobType;
    private String title,name,city,c_address,salaryFrom,salaryTo,
            c_email,c_phone,p_code,c_description,c_education,c_positions,jobTy,contact,emp_token,seeker_token;
    private ElasticButton post_upload_btn;
    private FirebaseAuth auth;
    private DatabaseReference reference,reference2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_view_post);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Post_Data");
         final String id = reference.getKey();

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("empasrecid",auth.getCurrentUser().getUid());
        editor.apply();

        Initialize();

        getvalueFromIntent();

        setIntentValueToTextView();

        post_upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final SweetAlertDialog dialog = new SweetAlertDialog(EmpViewPostActivity.this,SweetAlertDialog.PROGRESS_TYPE);
                dialog.setTitleText("Posting...");
                dialog.setCancelable(false);
                dialog.show();

                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                String strDate = dateFormat.format(date);
                Emp_Post_Data epd = new Emp_Post_Data(title,name,c_email,city,c_address,contact,c_education,
                        c_positions,salaryFrom,salaryTo,jobTy,c_description,strDate,auth.getCurrentUser().getUid());


                reference.child(auth.getCurrentUser().getUid()).push()
                .setValue(epd).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        dialog.setTitleText("Job Post Successfully");
                        dialog.setConfirmText("OK");
                        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                dialog.dismiss();
                            }
                        });

                          reference2 = FirebaseDatabase.getInstance().getReference("Seeker_Token");
                         reference2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    for (DataSnapshot snapshot1 : snapshot.getChildren()){
                                        seeker_token = snapshot1.getValue().toString();
                                        Toast.makeText(EmpViewPostActivity.this, seeker_token, Toast.LENGTH_SHORT).show();
                                        if (seeker_token != null) {
                                            FirebaseNotificationHelper.initialize("AAAAXZtjQRc:APA91bELwafwrp4EcCTtsTOEjgQSkxvRkx01BYL2n02gEOMWMsiV5dcVzoutoCL_zUcH0kVaiGhd8dfkJ7AINuMWdr34YC-ZsOqXuikVcPvT2o1isG-WimIFpdcXvgiz6Wr6Q-Dzuei0")
                                                    .defaultJson(true,null)
                                                    .title(name + " "+"Require"+" "+title)
                                                    .message(c_description)
                                                    .setCallBack(EmpViewPostActivity.this)
                                                    .receiverFirebaseToken(seeker_token)
                                                    .send();
                                        }
                                    }

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                });
            }
        });



    }

    private void getvalueFromIntent()
    {

        title = getIntent().getStringExtra("jobtitle");
        name = getIntent().getStringExtra("comname");
        c_email = getIntent().getStringExtra("comemail");
        city = getIntent().getStringExtra("comcity");
        c_address = getIntent().getStringExtra("comaddress");
        p_code = getIntent().getStringExtra("compcode");
        c_phone = getIntent().getStringExtra("comphone");
        c_education = getIntent().getStringExtra("comedu");
        c_positions = getIntent().getStringExtra("comhire");
        salaryFrom = getIntent().getStringExtra("comsalfr");
        salaryTo = getIntent().getStringExtra("comsalto");
        jobTy = getIntent().getStringExtra("comjobtyp");
        c_description = getIntent().getStringExtra("comjobdes");


    }

    private void setIntentValueToTextView()
    {


        job_title.setText(title);
        company_name.setText(name);
        email.setText(c_email);
        company_city.setText(city);
        address.setText(c_address);
        contact = p_code+c_phone;
        phone.setText(contact);
        education.setText(c_education);
        positions.setText(c_positions);
        salary_from.setText(salaryFrom);
        salary_to.setText(salaryTo);
        jobType.setText(jobTy);
        description.setText(c_description);



    }

    private void Initialize()
    {

         job_title = findViewById(R.id.emp_view_post_title);
         company_name = findViewById(R.id.emp_view_post_comp_name);
         company_city = findViewById(R.id.emp_view_post_city);
         address = findViewById(R.id.emp_view_post_address);
         salary_from = findViewById(R.id.emp_view_post_salary_from);
         salary_to = findViewById(R.id.emp_view_post_salary_to);
         education = findViewById(R.id.emp_view_post_educat);
         positions = findViewById(R.id.emp_view_post_position);
         email = findViewById(R.id.emp_view_post_email);
         phone = findViewById(R.id.emp_view_post_phone);
         description = findViewById(R.id.emp_view_post_description);
         jobType = findViewById(R.id.emp_view_post_gender);
         post_upload_btn = findViewById(R.id.post_upload);


    }

    public void goUpdatebtn(View view) {
        Intent intent = new Intent(getApplicationContext(),EmpUpdatePostActivity.class);
        startActivity(intent);
    }


    @Override
    public void success(String s) {
        Toast.makeText(getApplicationContext(), "Yes", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fail(String s) {
        Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT).show();

    }
}
