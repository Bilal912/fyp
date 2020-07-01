package com.fyp.job_clover.Employer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;

public class EmpUpdatePostActivity extends AppCompatActivity {
    private TextInputLayout title_lay,name_lay,vity_lay,address_lay,salaryFrom_lay,salaryTo_lay,email_lay;
    private TextInputEditText job_title,company_name,address,salary_from,salary_to,email,com_city,educat,no_of_positions,job_radio;
    private String title,name,city,c_address,salaryFrom,salaryTo,c_email,phone,p_code,description,edu,no_position,emp_id;
    private ElasticButton post_update_btn;
    private EditText company_phone,job_description;
     private String  jobtype = null;

    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private FirebaseUser firebaseUser;
    private DocumentReference docRef,docf;
    private SharedPreferences emp_pref;
    private Query query;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_update_post);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        emp_pref = getApplicationContext().getSharedPreferences("Emp_Pref",  0);
        final SharedPreferences.Editor editor = emp_pref.edit();




        Initialize();





        emp_id = auth.getCurrentUser().getUid();
        docRef = firestore.collection("Emp_Post_Data").document(emp_id).collection("posts").document();
//        docf = (DocumentReference) firestore.collection("Emp_Post_Data").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
//                if (e != null){
//                    Toast.makeText(EmpUpdatePostActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
//                }
//                if (queryDocumentSnapshots != null){
//                    for (DocumentSnapshot doc : queryDocumentSnapshots){
//                        Emp_Post_Data epd = doc.toObject(Emp_Post_Data.class);
//                        epd.setSpecific_key(doc.getSpecific_key());
//                    }
//                }
//
//            }
//        });



        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {

                        title = documentSnapshot.getData().get("job_title").toString();
                        name = documentSnapshot.getData().get("company_name").toString();
                        c_email = documentSnapshot.getData().get("company_email").toString();
                        city = documentSnapshot.getData().get("company_city").toString();
                        c_address = documentSnapshot.getData().get("company_address").toString();
                        phone = documentSnapshot.getData().get("company_phone").toString();
                        edu = documentSnapshot.getData().get("req_education").toString();
                        no_position = documentSnapshot.getData().get("company_position").toString();
                        salaryFrom = documentSnapshot.getData().get("salary_from").toString();
                        salaryTo = documentSnapshot.getData().get("salary_to").toString();
                        jobtype = documentSnapshot.getData().get("job_type").toString();
                        description = documentSnapshot.getData().get("description").toString();


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
                }
            }
        });


    }





    private void Initialize()
    {


        educat = findViewById(R.id.update_post_spinner_career_id);
        no_of_positions = findViewById(R.id.update_post_spinner_position_id);
        company_phone=findViewById(R.id.update_post_emp_post_phone);
        job_radio=findViewById(R.id.update_post_job_type_radio);
        job_description = findViewById(R.id.update_post_post_Describion);
        job_title = findViewById(R.id.update_post_job_title_id);
        company_name = findViewById(R.id.update_post_company_name_id);
        address = findViewById(R.id.update_post_company_address_txt);
        com_city = findViewById(R.id.update_post_company_locat_txt);
        salary_from = findViewById(R.id.update_post_post_salaryFrom);
        salary_to = findViewById(R.id.update_post_post_salaryto);
        job_description = findViewById(R.id.update_post_post_Describion);
        post_update_btn = findViewById(R.id.post_update_btn);
        email = findViewById(R.id.update_post_company_email_id);




    }


}
