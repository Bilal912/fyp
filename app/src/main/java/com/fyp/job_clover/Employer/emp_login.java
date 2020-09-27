package com.fyp.job_clover.Employer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fyp.job_clover.Data_Classes.Employer_Reg_Data;
import com.fyp.job_clover.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.iid.FirebaseInstanceId;
import com.skydoves.elasticviews.ElasticButton;
import com.squareup.okhttp.internal.DiskLruCache;

import org.w3c.dom.Text;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class emp_login extends AppCompatActivity {
TextView forget,back;
ElasticButton Login,Register;
TextInputEditText Email,Password;
public static final String MY_PREFS_NAME = "MyPrefsFile";

private FirebaseAuth auth;
private DatabaseReference reference;
private FirebaseUser firebaseUser;

private String emName,emEmail,emCity,emAddress,emp_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_login);

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Employer_Data");

        back=findViewById(R.id.back);
        forget=findViewById(R.id.forget);
        Login=findViewById(R.id.login);
        Register=findViewById(R.id.register);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(emp_login.this,emp_register.class));
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(Email.getText().toString())){
                    Toast.makeText(emp_login.this, "Email is required", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Password.getText().toString())){
                    Toast.makeText(emp_login.this, "Password is required", Toast.LENGTH_SHORT).show();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()){
                    Email.setError("Email is not Valid");
                    Email.requestFocus();
                }
                else {
                    final String email = Email.getText().toString();
                    final String password = Password.getText().toString();

                    final SweetAlertDialog dialog = new SweetAlertDialog(emp_login.this,SweetAlertDialog.PROGRESS_TYPE);
                    dialog.setTitleText("Loading...");
                    dialog.setCancelable(false);
                    dialog.show();

                    auth.signInWithEmailAndPassword(email,password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    //  fetch employer data

                                   emp_id = auth.getCurrentUser().getUid();

                                   reference.child(emp_id).addValueEventListener(new ValueEventListener() {
                                       @Override
                                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                           Employer_Reg_Data srd = dataSnapshot.getValue(Employer_Reg_Data.class);
                                                   emName = srd.employer_name;
                                                   emEmail = srd.employer_email;
                                       }

                                       @Override
                                       public void onCancelled(@NonNull DatabaseError databaseError) {
                                           dialog.dismiss();
                                       }
                                   });



                                    String token = FirebaseInstanceId.getInstance().getToken();
                                    String uid = auth.getCurrentUser().getUid();

                                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Employer_Token")
                                            .child(uid);
                                    reference.child("token").setValue(token).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                        }
                                    });

                                    dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                    dialog.setTitleText("Login Successfully");
                                    dialog.setConfirmText("OK");
                                    dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            dialog.dismiss();
                                            SharedPreferences.Editor editors = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                                            editors.putString("Type", "Employer");
                                            editors.putString("emp_name",emName);
                                            editors.putString("emp_email",emEmail);
                                            editors.apply();
                                            Intent intent = new Intent(emp_login.this,emp_menu.class);
                                            startActivity(intent);
                                        }
                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    dialog.changeAlertType(SweetAlertDialog.WARNING_TYPE);
                                    dialog.setTitleText("Email and Password does not Exist");
                                    dialog.setConfirmText("Register");
                                    dialog.show();
                                    dialog.setCancelable(true);
                                    dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            startActivity(new Intent(emp_login.this,emp_register.class));
                                        }
                                    });
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
        startActivity(new Intent(emp_login.this,emp_forget.class));
    }

//    protected void onStart() {
//        super.onStart();
//
//        if (firebaseUser != null){
//            sendUserToMainActivity();
//            finish();
//        }
//    }

    private void sendUserToMainActivity() {
        startActivity(new Intent(getApplicationContext(), emp_menu.class));
        finish();
    }

}
