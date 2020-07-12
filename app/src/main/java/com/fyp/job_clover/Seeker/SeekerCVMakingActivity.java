package com.fyp.job_clover.Seeker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import cn.pedant.SweetAlert.SweetAlertDialog;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.skydoves.elasticviews.ElasticButton;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static androidx.core.app.ActivityCompat.requestPermissions;

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

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(SeekerCVMakingActivity.this, new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(SeekerCVMakingActivity.this, new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }

            }

            auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("CV_Data");


        initializedata();

        setSpiner();

        countrypickerVal();

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {

                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions, STORAGE_CODE);
            }
        }

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

        createandDisplayPdf();

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


        // Method for opening a pdf file
//        private void viewPdf(String file, String directory) {
//
//            File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + directory + "/" + file);
//            Uri path = Uri.fromFile(pdfFile);
//
//            // Setting the intent for pdf reader
//            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
//            pdfIntent.setDataAndType(path, "application/pdf");
//            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            try {
//                startActivity(pdfIntent);
//            } catch (ActivityNotFoundException e) {
//                Toast.makeText(TableActivity.this, "Can't read pdf file", Toast.LENGTH_SHORT).show();
//            }
//        }
    }


    private void viewPdf(String file, String directory) {

        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/" + directory + "/" + file);
        Uri path = Uri.fromFile(pdfFile);

        // Setting the intent for pdf reader
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        try {
            startActivity(pdfIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(SeekerCVMakingActivity.this, "Can't read pdf file", Toast.LENGTH_SHORT).show();
        }
    }


    public void createandDisplayPdf() {

        Document doc = new Document();

        try {
            File storageDir = null;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                storageDir = new File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
                                + "/JOB Clover");
            }

            boolean success = true;
            if (!storageDir.exists()) {
                success = storageDir.mkdirs();
            }
            if (success) {

//                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Dir";
//
//                File dir = new File(path);
//                if (!dir.exists())
//                    dir.mkdirs();
//
//                File file = new File(dir, "newFile.pdf");

                File file = new File(storageDir, "CV.pdf");
                String savedImagePath = file.getAbsolutePath();

                FileOutputStream fOut = new FileOutputStream(file);

                PdfWriter.getInstance(doc, fOut);

                //open the document
                doc.open();

                Paragraph p1 = new Paragraph("Bilal");

                p1.setAlignment(Paragraph.ALIGN_CENTER);
                //p1.setFont(paraFont);

                //add paragraph to document
                doc.add(p1);
                doc.close();

                Toast.makeText(SeekerCVMakingActivity.this,"Success",Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(SeekerCVMakingActivity.this,"String.valueOf(de)",Toast.LENGTH_LONG).show();
            }
        } catch (DocumentException de) {
            Toast.makeText(SeekerCVMakingActivity.this,String.valueOf(de),Toast.LENGTH_LONG).show();
            Log.e("PDFCreator", "DocumentException:" + de);
        } catch (IOException e) {
            Toast.makeText(SeekerCVMakingActivity.this,String.valueOf(e),Toast.LENGTH_LONG).show();

            Log.e("PDFCreator", "ioException:" + e);
        }
        finally {
            doc.close();
        }

//        viewPdf("newFile.pdf", "Dir");
    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    private void createPdf(){
//        String sometext = "Bilal";
//        // create a new document
//        PdfDocument document = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//            document = new PdfDocument();
//        }
//
//        // crate a page description
//        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
//
//        // start a page
//        PdfDocument.Page page = document.startPage(pageInfo);
//        Canvas canvas = page.getCanvas();
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        canvas.drawCircle(50, 50, 30, paint);
//        paint.setColor(Color.BLACK);
//        canvas.drawText(sometext, 80, 50, paint);
//        //canvas.drawt
//        // finish the page
//        document.finishPage(page);
//// draw text on the graphics object of the page
//
//        // Create Page 2
//        pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 2).create();
//        page = document.startPage(pageInfo);
//        canvas = page.getCanvas();
//        paint = new Paint();
//        paint.setColor(Color.BLUE);
//        canvas.drawCircle(100, 100, 100, paint);
//        document.finishPage(page);
//
//        // write the document content
//        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/Job Clover/";
//        File file = new File(directory_path);
//
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//        String targetPdf = directory_path+"test-2.pdf";
//        File filePath = new File(targetPdf);
//        try {
//            document.writeTo(new FileOutputStream(filePath));
//            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
//        } catch (IOException e) {
//            Log.e("main", "error "+e.toString());
//            Toast.makeText(this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
//        }
//
//        // close the document
//        document.close();
//    }

}
