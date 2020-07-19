package com.fyp.job_clover.Employer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.job_clover.Data_Classes.Emp_Post_Data;
import com.fyp.job_clover.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.skydoves.elasticviews.ElasticButton;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EmpUpdatePostActivity extends AppCompatActivity {
    private TextInputLayout title_lay,name_lay,vity_lay,address_lay,salaryFrom_lay,salaryTo_lay,email_lay;
    private TextInputEditText job_title,company_name,address,salary_from,salary_to,email,com_city,educat,no_of_positions,job_radio
            ,company_phone;
    private String title,name,city,c_address,salaryFrom,salaryTo,c_email,phone,description,edu,no_position,key;
    private ElasticButton post_update_btn;
      private String  jobtype ;
    private EditText job_description;
    private FirebaseAuth auth;
     private DatabaseReference reference;
    private FirebaseUser firebaseUser;

    private SharedPreferences emp_pref;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_update_post);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Post_Data");
        emp_pref = getApplicationContext().getSharedPreferences("Emp_Pref",  0);
        final SharedPreferences.Editor editor = emp_pref.edit();

        Bundle bundle = getIntent().getExtras();
          title = bundle.getString("title");
          jobtype = bundle.getString("jobtype");
          name = bundle.getString("name");
          c_email = bundle.getString("email");
          c_address = bundle.getString("address");
          phone = bundle.getString("phone");
          edu = bundle.getString("edu");
          city = bundle.getString("city") ;
          salaryFrom = bundle.getString("salaryfrom");
          salaryTo =  bundle.getString("salaryto");
          description = bundle.getString("description");
          no_position = bundle.getString("position");
          key = bundle.getString("key");




        Initialize();







    }






    @SuppressLint("WrongViewCast")
    private void Initialize()
    {


        educat = findViewById(R.id.update_post_spinner_career_id);
        no_of_positions = findViewById(R.id.update_post_spinner_position_id);
        company_phone=findViewById(R.id.update_post_emp_post_phone);
        job_radio=findViewById(R.id.update_post_job_type_radio);
        job_description = (EditText) findViewById(R.id.update_post_post_Describion);
        job_title = findViewById(R.id.update_post_job_title_id);
        company_name = findViewById(R.id.update_post_company_name_id);
        address = findViewById(R.id.update_post_company_address_txt);
        com_city = findViewById(R.id.update_post_company_locat_txt);
        salary_from = findViewById(R.id.update_post_post_salaryFrom);
        salary_to = findViewById(R.id.update_post_post_salaryto);
        job_description = findViewById(R.id.update_post_post_Describion);
        post_update_btn = findViewById(R.id.post_update_btn);
        email = findViewById(R.id.update_post_company_email_id);


        job_title.setText(title);
        company_name.setText(name);
        email.setText(c_email);
        com_city.setText(city);
        address.setText(c_address);
        company_phone.setText(phone);
        educat.setText(edu);
        no_of_positions.setText(no_position);
        salary_from.setText(salaryFrom);
        salary_to.setText(salaryTo);
        job_radio.setText(jobtype);
        job_description.setText(description);


    }


    public void updatePost(View view) {

        String  titles = job_title.getText().toString();
        String  names = company_name.getText().toString();
        String c_emails = email.getText().toString();
        String citys = com_city.getText().toString();
        String c_addresses = address.getText().toString();
        String phones = company_phone.getText().toString();
        String salaryFroms = salary_from.getText().toString();
        String salaryTos = salary_to.getText().toString();
        String descriptions = job_description.getText().toString();
        String edua = educat.getText().toString();
        String post_postion = no_of_positions.getText().toString();
        String jobTy = job_radio.getText().toString();


        final SweetAlertDialog dialog = new SweetAlertDialog(getApplicationContext(),SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText("Posting...");
        dialog.setCancelable(false);
        dialog.show();

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String strDate = dateFormat.format(date);
        Emp_Post_Data epd = new Emp_Post_Data(titles,names,c_emails,citys,c_addresses,phones,edua,
                post_postion,salaryFroms,salaryTos,jobTy,descriptions,strDate,auth.getCurrentUser().getUid());


        reference.child(auth.getCurrentUser().getUid()).child(key)
                .setValue(epd).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                dialog.setTitleText("Post Update Successfully");
                dialog.setConfirmText("OK");
                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }
}
