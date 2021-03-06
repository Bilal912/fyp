package com.fyp.job_clover.Seeker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.job_clover.Adapter.AllCandidateAdapter;
import com.fyp.job_clover.Data_Classes.AppliedJobs;
import com.fyp.job_clover.Data_Classes.Constants;
import com.fyp.job_clover.Data_Classes.FileUpload;
import com.fyp.job_clover.Employer.EmpViewPostActivity;
import com.fyp.job_clover.Employer.ViewCVActivity;
import com.fyp.job_clover.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.skydoves.elasticviews.ElasticButton;
import com.trenzlr.firebasenotificationhelper.FirebaseNotiCallBack;
import com.trenzlr.firebasenotificationhelper.FirebaseNotificationHelper;

import java.io.File;
import java.security.KeyFactory;

import static com.fyp.job_clover.Seeker.SeekerCVMakingActivity.MY_PREFS_NAME;

public class CV_Upload_Activity extends AppCompatActivity implements FirebaseNotiCallBack {
    final static int PICK_PDF_CODE = 2342;
    TextView textViewStatus;
    EditText editTextFilename;
    ElasticButton cvupload;
    private FirebaseAuth auth;
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference,matabaseReference,databaseReference;
    private  String key,title,jobtype,namecity,salary,description,position,emp_id,cv_email, uid,emp_token,seeker_token,name;
    private  FileUpload upload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_v__upload_);

        Bundle extras = getIntent().getExtras();
        key = extras.getString("s_key");
        title = extras.getString("title");
        jobtype = extras.getString("jobtype");
        namecity = extras.getString("name-city");
        salary = extras.getString("salary");
        description = extras.getString("description");
        position = extras.getString("position");
        emp_id = extras.getString("emp_id");
//        Toast.makeText(this, key, Toast.LENGTH_SHORT).show();

        //getting firebase objects
        mStorageReference = FirebaseStorage.getInstance().getReference();
        matabaseReference = FirebaseDatabase.getInstance().getReference("Applied_Jobs");
        databaseReference = FirebaseDatabase.getInstance().getReference("CV_Data");
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("CV_Data");


        auth = FirebaseAuth.getInstance();


//        databaseReference.child(auth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    FileUpload upload = snapshot.getValue(FileUpload.class);
//                    cv_email =   upload.getName();
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        //getting the views

        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        editTextFilename = (EditText) findViewById(R.id.editTextFileName);
        cvupload = findViewById(R.id.buttonUploadFile);
        //attaching listeners to views

            cvupload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = editTextFilename.getText().toString();

                    if (name.equals(""))
                    {

                        Toast.makeText(CV_Upload_Activity.this, "Enter Your Name", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        getPDF();
                    }
                }
            });


        findViewById(R.id.makeCV).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SeekerCVMakingActivity.class));

            }
        });
    }

    //this function will get the pdf from the storage
    private void getPDF() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
//                Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
//                    Uri.parse("package:" + getPackageName()));
//            startActivity(intent);
//            return;
//        }

        //creating an intent for file chooser
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/*");
        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_PDF_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file

                uploadFile(data.getData());

            } else {
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void uploadFile(Uri data) {

        final android.app.AlertDialog loading = new ProgressDialog(CV_Upload_Activity.this);
        loading.setMessage("Uploading...");
        loading.show();
        final StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + editTextFilename.getText().toString()
                +System.currentTimeMillis() + ".pdf");
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                            SharedPreferences.Editor editors = getSharedPreferences(seeker_login.MY_PREFS_NAME, MODE_PRIVATE).edit();
                            editors.putString("cv_path",String.valueOf(uri));
                            editors.apply();

                                uid =auth.getCurrentUser().getUid();
                                upload = new FileUpload(editTextFilename.getText().toString(), String.valueOf(uri),
                                        auth.getCurrentUser().getUid());
                                mDatabaseReference.child(key).child(uid).setValue(upload);

                                AppliedJobs aj = new AppliedJobs(title,jobtype,namecity,salary,description, position,emp_id);
                                matabaseReference.child(uid).child(key).setValue(aj);

                            Toast.makeText(CV_Upload_Activity.this, "Applied Successfully", Toast.LENGTH_SHORT).show();
                            loading.dismiss();
                            finish();


                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CV_Upload_Activity.this, String.valueOf(e), Toast.LENGTH_SHORT).show();
                            }
                        });


//                        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference();
//                        reference1.child("Employer_Token").child(emp_id).child("token").addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                                emp_token = dataSnapshot.getValue().toString();
//                                Toast.makeText(CV_Upload_Activity.this, emp_token, Toast.LENGTH_SHORT).show();
//                                if(emp_token != null)
//                                {
//                                    FirebaseNotificationHelper.initialize("AAAAXZtjQRc:APA91bELwafwrp4EcCTtsTOEjgQSkxvRkx01BYL2n02gEOMWMsiV5dcVzoutoCL_zUcH0kVaiGhd8dfkJ7AINuMWdr34YC-ZsOqXuikVcPvT2o1isG-WimIFpdcXvgiz6Wr6Q-Dzuei0")
//                                            .defaultJson(true, null)
//                                            .title(title+"Candidate CV ")
//                                            .message("")
//                                            .setCallBack(CV_Upload_Activity.this)
//                                            .receiverFirebaseToken(emp_token)
//                                            .send();
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
//                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
//                    @SuppressWarnings("VisibleForTests")
//                    @Override
//                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//                        final android.app.AlertDialog loading = new ProgressDialog(CV_Upload_Activity.this);
//                        loading.setMessage((int) progress + "% Uploading...");
//                        loading.show();
//                        //textViewStatus.setText((int) progress + "% Uploading...");
//                        if (progress == 100){
//                            Toast.makeText(CV_Upload_Activity.this, "File Uploaded Successfully", Toast.LENGTH_SHORT).show();
//                            loading.dismiss();
////                            finish();
//                        }
//                    }
//                })
        ;

    }

    @Override
    public void success(String s) {
        Toast.makeText(getApplicationContext(), "YES", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void fail(String s) {
        Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT).show();
    }

}