package com.fyp.job_clover.Employer;

import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fyp.job_clover.R;
import com.google.android.material.textfield.TextInputEditText;
import com.skydoves.elasticviews.ElasticButton;

public class emp_edit_profile extends Fragment {
    private SharedPreferences emp_pref;
    private String empName,empEmail,empPassword,empCity,empAddress,emp_id;
    TextInputEditText Company_name,Email,Password,City,Address;
    ElasticButton Update;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emp_edit_profile, container, false);

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
                else if (TextUtils.isEmpty(Password.getText().toString())){
                    Toast.makeText(getContext(), "Password is required", Toast.LENGTH_SHORT).show();
                }
                else {

                    //hafuz yaha profile update krni ha

                    Toast.makeText(getContext(), "Working...", Toast.LENGTH_SHORT).show();

                }
            }
        });

        return view;
    }

}
