package com.fyp.job_clover.Seeker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.job_clover.Employer.emp_login;
import com.fyp.job_clover.Employer.emp_menu;
import com.fyp.job_clover.Employer.emp_register;
import com.fyp.job_clover.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.skydoves.elasticviews.ElasticButton;

public class seeker_login extends AppCompatActivity {
    TextView forget,back;
    ElasticButton Login,Register;
    TextInputEditText Email,Password;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private FirebaseUser firebaseUser;
    private DocumentReference docRef;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    private SharedPreferences seeker_pref;
    private String seek_name,seek_email,seek_qualification,seek_address,seek_phone,seek_gender,seeker_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seeker_login);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        firestore = FirebaseFirestore.getInstance();
        seeker_pref = getApplicationContext().getSharedPreferences("Seeker_Pref",  0);
        final SharedPreferences.Editor editor = seeker_pref.edit();

        back=findViewById(R.id.back);
        forget=findViewById(R.id.forget);
        Login=findViewById(R.id.login);
        Register=findViewById(R.id.register);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(seeker_login.this, seeker_register.class));
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(Email.getText().toString())){
                    Toast.makeText(seeker_login.this, "Email is required", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Password.getText().toString())){
                    Toast.makeText(seeker_login.this, "Password is required", Toast.LENGTH_SHORT).show();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()){
                    Email.setError("Email is not Valid");
                    Email.requestFocus();
                }
                else {
                    //Hafiz yaha to check kara ga firebase sa
                    //aur jab correct hu ga to data sharedprefrence ma store kr wa kr aga intent laga dai

                    String email = Email.getText().toString();
                    String password = Password.getText().toString();

                    final SweetAlertDialog dialog = new SweetAlertDialog(seeker_login.this,SweetAlertDialog.PROGRESS_TYPE);
                    dialog.setTitleText("Loading...");
                    dialog.setCancelable(false);
                    dialog.show();

                    auth.signInWithEmailAndPassword(email,password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    //Toast.makeText(seeker_login.this,"Successful",Toast.LENGTH_LONG).show();


                                    seeker_id = auth.getCurrentUser().getUid();
                                    docRef = firestore.collection("Employer_Data").document(seeker_id);

//                                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//
//                                            if (task.isSuccessful()){
//                                                DocumentSnapshot documentSnapshot = task.getResult();
//                                                if (documentSnapshot.exists()){
//
//                                                    seek_name =  documentSnapshot.getData().get("seeker_name").toString();
//                                                    seek_email   =   documentSnapshot.getData().get("seeker_email").toString();
//                                                    seek_qualification =  documentSnapshot.getData().get("seeker_qualification").toString();
//                                                    seek_address =  documentSnapshot.getData().get("seeker_address").toString();
//                                                    seek_phone  =  documentSnapshot.getData().get("seeker_phone").toString();
//                                                    seek_gender  =  documentSnapshot.getData().get("seeker_gender").toString();
//
//                                                    // save in Sharedfreferences
//
//                                                    editor.putString("seek_id",seeker_id);
//                                                    editor.putString("seek_name",seek_name);
//                                                    editor.putString("seek_email",seek_email);
//                                                    editor.putString("seek_qualification", seek_qualification);
//                                                    editor.putString("seek_address",seek_address);
//                                                    editor.putString("seek_phone",seek_phone);
//                                                    editor.putString("seek_gender",seek_gender);
//                                                    editor.commit();
//
//                                                }
//                                            }
//                                        }
//                                    });

                                    dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    dialog.setTitleText("Login Successfully");
                                    dialog.setConfirmText("OK");
                                    dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            dialog.dismiss();
                                            SharedPreferences.Editor editors = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                            editors.putString("Type", "Seeker");
                                            editors.apply();
                                            startActivity(new Intent(seeker_login.this, Seeker_Menu.class));
                                        }
                                    });
                                }

                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(seeker_login.this, e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            });


                }
            }
        });


    }
    public void back(View view) {
        finish();
    }

    public void forget(View view) {
        startActivity(new Intent(seeker_login.this,SeekerCVMakingActivity.class));
    }

    protected void onStart() {
        super.onStart();

        if (firebaseUser != null){
            startActivity(new Intent(seeker_login.this,Seeker_Menu.class));
             finish();
        }
    }

}
