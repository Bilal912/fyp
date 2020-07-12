package com.fyp.job_clover.Seeker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.job_clover.Data_Classes.CV_Making_Data;
import com.fyp.job_clover.Employer.EmpViewPostActivity;
import com.fyp.job_clover.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.skydoves.elasticviews.ElasticButton;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class SeekerCVMakingActivity extends AppCompatActivity {
    private static final int STORAGE_CODE = 1000;
    private EditText skills,exper,phone;
    private TextInputEditText title,name,email,city,address;
    private String cv_job_title,cv_name,cv_email,cv_phone,cv_p_code,cv_req_education,cv_city,cv_address,
            cv_skills,cv_experience,edu;

    private LinearLayout countrypicker,country_name_picker;
    private TextView phoneCode,country;
    private ImageView flg;
    private Spinner educat;
    private TextInputLayout title_lay,name_lay,vity_lay,address_lay,salaryFrom_lay,salaryTo_lay,email_lay;
    private ElasticButton cv_save_btn;
    private ArrayList<String> education;
    private ArrayList<String> countrylist;


    private DatabaseReference reference;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_c_v_making);

        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("CV_Data");


        initializedata();

        setSpiner();

        countrypickerVal();

        cv_save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getValueFromEditText();
            }
        });


    }

    private void getValueFromEditText()
    {
        if (TextUtils.isEmpty(title.getText().toString())){
            Toast.makeText(getApplicationContext(), "Job Title is required", Toast.LENGTH_SHORT).show();
        }
//        else if (TextUtils.isEmpty(name.getText().toString())) {
//            Toast.makeText(getApplicationContext(), " Name is required", Toast.LENGTH_SHORT).show();
//
//        }
//        else if (TextUtils.isEmpty(email.getText().toString())){
//            Toast.makeText(getApplicationContext(), "Email is required", Toast.LENGTH_SHORT).show();
//        }
//        else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
//            email.setError("Email is not Valid");
//            email.requestFocus();
//        }
//        else if (TextUtils.isEmpty(city.getText().toString())){
//            Toast.makeText(getApplicationContext(), "City is required", Toast.LENGTH_SHORT).show();
//        }
//        else if (TextUtils.isEmpty(address.getText().toString())){
//            Toast.makeText(getApplicationContext(), "Address is required", Toast.LENGTH_SHORT).show();
//        }
//        else if (TextUtils.isEmpty(phoneCode.getText().toString())){
//            Toast.makeText(getApplicationContext(), "phone code is required", Toast.LENGTH_SHORT).show();
//        }
//        else if (TextUtils.isEmpty(phone.getText().toString())){
//            Toast.makeText(getApplicationContext(), " phone is required", Toast.LENGTH_SHORT).show();
//        }
//        else if (edu == null){
//            Toast.makeText(getApplicationContext(), "Education is required", Toast.LENGTH_SHORT).show();
//            educat.requestFocus();
//        }
//
//        else if (TextUtils.isEmpty(skills.getText().toString())){
//            Toast.makeText(getApplicationContext(), "Skills is  required", Toast.LENGTH_SHORT).show();
//        }
//        else if (TextUtils.isEmpty(exper.getText().toString())){
//            Toast.makeText(getApplicationContext(), "Experience  is required", Toast.LENGTH_SHORT).show();
//        }

        else {
            cv_job_title = title.getText().toString();
//            cv_name = name.getText().toString();
//            cv_email = email.getText().toString();
//            cv_city = city.getText().toString();
//            cv_address = address.getText().toString();
//            cv_p_code = phoneCode.getText().toString();
//            cv_phone = phone.getText().toString();
//            cv_skills = skills.getText().toString();
//            cv_experience = exper.getText().toString();
//
//            String cv_contact = cv_p_code + cv_phone;


//            final SweetAlertDialog dialog = new SweetAlertDialog(getApplicationContext(),SweetAlertDialog.PROGRESS_TYPE);
//            dialog.setTitleText("Posting...");
//            dialog.setCancelable(false);
//            dialog.show();


//            CV_Making_Data cv = new CV_Making_Data(cv_job_title,cv_name,cv_email,cv_contact,
//                    edu,cv_city,cv_address,cv_skills,cv_experience);
//
//            reference.child(auth.getCurrentUser().getUid())
//                    .setValue(cv).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//
//                    dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//                    dialog.setTitleText("CV Save Successfully");
//                    dialog.setConfirmText("OK");
//                    dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                        @Override
//                        public void onClick(SweetAlertDialog sweetAlertDialog) {
//                            dialog.dismiss();
//                        }
//                    });
//                }
//            });
//

            //   text convert to pdf file simple open

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED){

                    String[] permissions =  {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permissions, STORAGE_CODE);
                }
                else {
                    savePdf();
                }
            }
            else {
                savePdf();

            }


        }

    }

    private void savePdf()
    {

        Document document = new Document();
        String mFileName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        String filePath = Environment.getExternalStorageDirectory() + "/" + mFileName + ".pdf";

        try {

            PdfWriter.getInstance(document,new FileOutputStream(filePath));

            document.addAuthor("Ghiyas");
            document.add(new Paragraph(cv_job_title));

            document.close();

            Toast.makeText(this, mFileName +".pdf \n is save to \n" + filePath, Toast.LENGTH_SHORT).show();

        }
        catch (Exception e){

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         switch (requestCode){
             case STORAGE_CODE:
                 if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        savePdf();
                 }
                 else {
                     Toast.makeText(this, "Permission Denied ....!", Toast.LENGTH_SHORT).show();
                 }
         }
    }

    private void countrypickerVal()
    {
        countrypicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CountryPicker picker = CountryPicker.newInstance("Select Country");  // dialog title
                picker.setListener(new CountryPickerListener() {
                    @Override
                    public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                        phoneCode.setText(dialCode);
                        flg.setImageResource(flagDrawableResID);
                        picker.dismiss();
                    }
                });
                picker.show(getSupportFragmentManager(), "COUNTRY_PICKER");
            }
        });

    }

    private void setSpiner()
    {
        education.add("Require Education");
        education.add("Matric");
        education.add("Inter");
        education.add("Graduation");
        education.add("Masters");
        education.add("Other");

        educat.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, education));

        educat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        edu = null ;
                        break;
                    case 1:
                        edu = "Matric";
                        break;
                    case 2:
                        edu = "Inter" ;
                        break;
                    case 3:
                        edu = "Graduation" ;
                        break;
                    case 4:
                        edu = "Masters" ;
                        break;
                    case 5:
                        edu = "Other" ;
                        break;


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void initializedata()
    {
        exper = findViewById(R.id.cv_mak_exp);
        skills = findViewById(R.id.cv_mak_skils);
        education = new ArrayList<String>();
        countrylist = new ArrayList<>();
        educat = findViewById(R.id.cv_mak_qual);
        countrypicker=findViewById(R.id.cv_mak_countrypicker);
        phoneCode=findViewById(R.id.cv_mak_phone_code);
        flg=findViewById(R.id.cv_mak_flag);
        phone=findViewById(R.id.cv_mak_phone);
        title = findViewById(R.id.cv_mak_title);
        name = findViewById(R.id.cv_mak_name_text);
        address = findViewById(R.id.cv_mak_address);
        city = findViewById(R.id.cv_mak_city);
        cv_save_btn = findViewById(R.id.cv_save);
        email = findViewById(R.id.cv_mak_email_text);


    }

    public void back(View view) {
    }


    public void bold(View view) {

        skills.setTypeface(null, Typeface.BOLD);
    }

    public void bullet(View view) {

          skills.append("•");

        skills.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String st=skills.getText().toString();
                if(st.length()>0 && st.charAt(st.length()-1)=='\n'){
                    skills.append("•");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}
