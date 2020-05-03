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
import com.fyp.job_clover.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.skydoves.elasticviews.ElasticButton;

public class emp_edit_profile extends Fragment {
    private SharedPreferences emp_pref;
    private String empName,empEmail,empPassword,empCity,empAddress,emp_id;
    TextInputEditText Company_name,Email,Password,City,Address;
    ElasticButton Update;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emp_edit_profile, container, false);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        emp_pref =  getActivity().getSharedPreferences("Emp_Pref", 0);

        Company_name = view.findViewById(R.id.company_name);
        Email = view.findViewById(R.id.email);
        Password = view.findViewById(R.id.password);
        City = view.findViewById(R.id.company_city);
        Address = view.findViewById(R.id.company_address);
        Update = view.findViewById(R.id.register);

        emp_id = emp_pref.getString("emp_id",null);
        empName = emp_pref.getString("emp_name",null);
        empEmail = emp_pref.getString("emp_email",null);
        empCity = emp_pref.getString("emp_city",null);
        empAddress = emp_pref.getString("emp_address",null);
        empPassword = emp_pref.getString("emp_password",null);

        Company_name.setText(empName);
        Email.setText(empEmail);
        Email.setEnabled(false);
        City.setText(empCity);
        Address.setText(empAddress);
        Password.setText(empPassword);
        Password.setEnabled(false);

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
                    final String company_name = Company_name.getText().toString();
                    final String city = City.getText().toString();
                    final String email = Email.getText().toString();
                    final String password = Password.getText().toString();
                    final String address = Address.getText().toString();

                    final SweetAlertDialog dialog = new SweetAlertDialog(getActivity(),SweetAlertDialog.PROGRESS_TYPE);
                    dialog.setTitleText("Updating...");
                    dialog.setCancelable(false);
                    dialog.show();

                    Employer_Reg_Data erd = new Employer_Reg_Data(company_name,email,city,address,password);
                    firestore.collection("Employer_Data").document(auth.getCurrentUser().getUid())
                            .set(erd).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            dialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            dialog.setTitleText("Updated Successfully");
                            dialog.setConfirmText("OK");
                            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    dialog.dismiss();
                                    startActivity(new Intent( getContext(),emp_login.class));
                                }
                            });
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dialog.dismiss();
                                    Toast.makeText(getActivity(), e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            });

                }
            }
        });

        return view;
    }

}
