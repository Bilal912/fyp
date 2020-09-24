package com.fyp.job_clover.Employer;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import cn.pedant.SweetAlert.SweetAlertDialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fyp.job_clover.Data_Classes.Employer_Reg_Data;
import com.fyp.job_clover.Data_Classes.Seeker_Reg_Data;
import com.fyp.job_clover.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.skydoves.elasticviews.ElasticButton;

public class emp_edit_profile extends Fragment {
    private SharedPreferences emp_pref;
    private String empName,empEmail,empPassword,empCity,empAddress,emp_id;
    TextInputEditText Company_name,Email,Password,City,Address;
    ElasticButton Update;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private DatabaseReference reference,databaseReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emp_edit_profile, container, false);
        final SweetAlertDialog dialog = new SweetAlertDialog(getContext(),SweetAlertDialog.PROGRESS_TYPE);
        dialog.setTitleText("Loading...");
        dialog.setCancelable(false);
        dialog.show();

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        emp_pref =  getActivity().getSharedPreferences("Emp_Pref", 0);

        Company_name = view.findViewById(R.id.company_name);
        Email = view.findViewById(R.id.email);
        Password = view.findViewById(R.id.password);
        City = view.findViewById(R.id.company_city);
        Address = view.findViewById(R.id.company_address);
        Update = view.findViewById(R.id.register);

        final String uid = auth.getCurrentUser().getUid();
        reference = FirebaseDatabase.getInstance().getReference("Employer_Data").child(uid);
        databaseReference = FirebaseDatabase.getInstance().getReference("Employer_Data").child(uid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Employer_Reg_Data srd = dataSnapshot.getValue(Employer_Reg_Data.class);
                Company_name.setText(srd.employer_name);
                Email.setText(srd.employer_email);
                Email.setEnabled(false);
                City.setText(srd.employer_city);
                Address.setText(srd.employer_address);
                Password.setText(srd.employer_password);
                Password.setEnabled(false);

                dialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                dialog.dismiss();
            }
        });

//        Company_name.setText(empName);
//        Email.setText(empEmail);
//        Email.setEnabled(false);
//        City.setText(empCity);
//        Address.setText(empAddress);
//        Password.setText(empPassword);
//        Password.setEnabled(false);

        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(Company_name.getText().toString())){
                    Toast.makeText(getContext(), "Company Name is required", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(City.getText().toString())){
                    Toast.makeText(getContext(), "City is required", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(Address.getText().toString())){
                    Toast.makeText(getContext(), "Address is required", Toast.LENGTH_SHORT).show();
                }

                else {
                    String company_name = Company_name.getText().toString();
                    String city = City.getText().toString();
                    String email = Email.getText().toString();
                    String password = Password.getText().toString();
                    String address = Address.getText().toString();

                    final SweetAlertDialog dialog = new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
                    dialog.setTitleText("Updating...");
                    dialog.setCancelable(false);
                    dialog.show();

                    Employer_Reg_Data erd = new Employer_Reg_Data(company_name,email,city,address,password);

                    databaseReference.setValue(erd).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialog.dismiss();
                            Toast.makeText(getContext(), "Profile Updated", Toast.LENGTH_SHORT).show();
                        }
                    });

//                    firestore.collection("Employer_Data").document(uid)
//                            .set(erd).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//
//                            dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
//                            dialog.setTitleText("Updated Successfully");
//                            dialog.setConfirmText("OK");
//                            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                                @Override
//                                public void onClick(SweetAlertDialog sweetAlertDialog) {
//                                    dialog.dismiss();
//                                    startActivity(new Intent( getContext(),emp_login.class));
//                                }
//                            });
//                        }
//                    })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    dialog.dismiss();
//                                    Toast.makeText(getActivity(), e.toString(),Toast.LENGTH_SHORT).show();
//                                }
//                            });

                }
            }
        });

        return view;
    }

}
