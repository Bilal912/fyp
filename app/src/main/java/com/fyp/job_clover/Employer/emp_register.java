package com.fyp.job_clover.Employer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.fyp.job_clover.Data_Classes.Employer_Reg_Data;
import com.fyp.job_clover.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.skydoves.elasticviews.ElasticButton;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class emp_register extends AppCompatActivity {
private TextInputEditText Company_name,Email,Password,City,Address;
private ElasticButton Register;
private FirebaseAuth auth;
private DatabaseReference reference;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emp_register);

        auth = FirebaseAuth.getInstance();
       reference = FirebaseDatabase.getInstance().getReference("Employer_Data");

        Company_name = findViewById(R.id.company_name);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        City = findViewById(R.id.company_city);
        Address = findViewById(R.id.company_address);
        Register = findViewById(R.id.register);

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(Company_name.getText().toString())){
                    Toast.makeText(emp_register.this, "Company Name is required", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Email.getText().toString())){
                    Toast.makeText(emp_register.this, "Email is required", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(City.getText().toString())){
                    Toast.makeText(emp_register.this, "City is required", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Address.getText().toString())){
                    Toast.makeText(emp_register.this, "Address is required", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Password.getText().toString())){
                    Toast.makeText(emp_register.this, "Password is required", Toast.LENGTH_SHORT).show();
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()){
                    Email.setError("Email is not Valid");
                    Email.requestFocus();
                }
                else {
                    final String company_name = Company_name.getText().toString();
                    final String city = City.getText().toString();
                    final String email = Email.getText().toString();
                    final String password = Password.getText().toString();
                    final String address = Address.getText().toString();

                    final SweetAlertDialog dialog = new SweetAlertDialog(emp_register.this,SweetAlertDialog.PROGRESS_TYPE);
                    dialog.setTitleText("Registration...");
                    dialog.setCancelable(false);
                    dialog.show();

                    auth.createUserWithEmailAndPassword(email,password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Employer_Reg_Data erd = new Employer_Reg_Data(company_name,email,city,address,password);
                                    reference.child(auth.getCurrentUser().getUid())
                                            .setValue(erd).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                            dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                            dialog.setTitleText("Register Successfully");
                                            dialog.setConfirmText("OK");
                                            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                    dialog.dismiss();
                                                    startActivity(new Intent(emp_register.this,emp_login.class));
                                                }
                                            });
                                        }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    dialog.dismiss();
                                                    Toast.makeText(emp_register.this, e.toString(),Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dialog.dismiss();
                                    Toast.makeText(emp_register.this, e.toString(),Toast.LENGTH_SHORT).show();

                                }
                            });

                }

            }
        });
    }

    public void back(View view) {
        finish();
    }
}
